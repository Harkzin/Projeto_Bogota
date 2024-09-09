package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.Product;
import web.support.utils.DriverQA;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.IntStream;

@Component
@ScenarioScope
public class ComumPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public ComumPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private void validateElementText(String ref, String xpath) {
        Assert.assertEquals("Texto do elemento igual ao esperado", ref, StringUtils.normalizeSpace(driverQA.findByXpath(xpath).getText()));
        Assert.assertTrue("Elemento exibido", driverQA.findByXpath(xpath).isDisplayed());
    }

    public static void validateElementText(String ref, WebElement element) {
        Assert.assertEquals("Texto do elemento igual ao esperado", ref, StringUtils.normalizeSpace(element.getText()));
        Assert.assertTrue("Elemento exibido", element.isDisplayed());
    }

    public static void validateElementActiveVisible(WebElement element) {
        Assert.assertTrue("Elemento ativo", element.isEnabled());
        Assert.assertTrue("Elemento exibido", element.isDisplayed());
    }

    //Valida os ícones dos Apps e Países da composição do Plano
    public static void validarMidiasPlano(List<String> appRef, List<WebElement> appFront, DriverQA driverQA) {
        Assert.assertEquals("Mesma quantidade de [apps/países configurados] e [presentes no Front]", appRef.size(), appFront.size());

        driverQA.waitElementVisible(appFront.get(0), 2); //Lazy loading Front
        IntStream.range(0, appRef.size()).forEachOrdered(i -> {
            Assert.assertTrue("App/País do Front deve ser o mesmo da API - Front: <" + appFront.get(i)
                            .getAttribute("src")
                            .replace("https://mondrian.claro.com.br/brands/app/72px-default/", "") + ">, API: <" + appRef.get(i) + ">",
                    appFront.get(i).getAttribute("src").contains(appRef.get(i)));

            if (i < 5) { //Até 5 ícones são exibidos diretamente, o restante fica oculto no tooltip (+X).
                Assert.assertTrue("App/País deve estar visível", appFront.get(i).isDisplayed());
            } else {
                Assert.assertFalse("App/País deve estar oculto", appFront.get(i).isDisplayed());
            }
        });
    }

    public static void validarAppsIlimitados(DriverQA driverQA, Product plan, WebElement planAppsTitle, List<WebElement> planApps) {
        //Valida título
        validateElementText(plan.getPlanAppsTitle(), planAppsTitle);

        //Valida Apps
        validarMidiasPlano(plan.getPlanApps(), planApps, driverQA);
    }

    public static void validarServicosClaro(DriverQA driverQA, Product plan, WebElement claroServicesTitle, List<WebElement> claroServicesApps) {
        //Valida título
        validateElementText(plan.getClaroServicesTitle(), claroServicesTitle);

        //Valida Apps
        validarMidiasPlano(plan.getClaroServices(), claroServicesApps, driverQA);
    }

    public static void validarPlanPortability(List<WebElement> planPortability, Product plan) {
        //Remove o elemento do [título extraPlay] que vem junto na lista, planportability e clarotitleextraplay usam as mesmas classes css.
        //A posição entre eles pode mudar, não servindo como referência.
        if (plan.hasExtraPlayTitle()) {
            planPortability.remove(planPortability
                    .stream()
                    .filter(webElement -> webElement.getText().equals(plan.getExtraPlayTitle()))
                    .findFirst().orElseThrow());
        }

        IntStream.range(0, planPortability.size()).forEachOrdered(i -> {
            Assert.assertEquals("Texto planPortability igual ao configurado", plan.getPlanPortability().get(i), planPortability.get(i).getText());

            Assert.assertTrue("Texto planPortability visível", planPortability.get(i).isDisplayed());
        });
    }

    public void validarResumoCompraPlano(CartOrder cartOrder) {
        String planContentParent;

        if (!cartOrder.isDeviceCart()) {
            planContentParent = "//*[contains(@class, 'col-layout-plan') and not(contains(@class, 'visible-mobile'))]/div/div";
            driverQA.actionPause(1500);
        } else {
            //TODO Tela de Parabens tem outra classe. Remover este bloco e atualizar os xpath apos ter sido implementado os atributos seletores no front
            ///////////////////////
            if (driverQA.findElement("//*[@class='mdn-Container-fluid']", "xpath") != null) {
                planContentParent = "//*[@class='mdn-Container-fluid']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
            } else {
                planContentParent = "//*[@class='mdn-Container']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
            }
            ///////////////////////

            //TODO Atualizar seletor
            //planContentParent = "//*[@class='mdn-Container-fluid']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
        }

        //Valida nome, caso configurado
        if (!(cartOrder.getPlan().getName() == null)) {
            //TODO Desativado até correção do front 04/09/24
            // HTML está diferente para cada plano
            // validateElementText(cartOrder.getPlan().getName(), planContentParent + "//span[contains(@class, 'product-fullname')]");
        }

        //Valida app ilimitados, caso configurado
        if (cartOrder.getPlan().hasPlanApps() && cartOrder.hasLoyalty) {
            //Título
            WebElement planAppsTitle = driverQA.findElement(planContentParent + "//div[contains(@class, ' apps-ilimitados')]/div[1]/div", "xpath");

            //Apps
            List<WebElement> planApps = driverQA.findElements(planContentParent + "//div[contains(@class, ' apps-ilimitados')]//img", "xpath");

            validarAppsIlimitados(driverQA, cartOrder.getPlan(), planAppsTitle, planApps);
        }

        //Valida título extraPlay, caso configurado
        if (cartOrder.getPlan().hasExtraPlayTitle()) {
            validateElementText(cartOrder.getPlan().getExtraPlayTitle(), planContentParent + "//div[contains(@class, 'product-card-content')]/p");
        }

        //Valida apps extraPlay, caso configurado
        if (cartOrder.getPlan().hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = driverQA.findElements(planContentParent + "//div[contains(@class, 'extra-play')]//img", "xpath");

            validarMidiasPlano(cartOrder.getPlan().getExtraPlayApps(), extraPlayApps, driverQA);
        }

        //Valida serviços Claro, caso configurado
        if (cartOrder.getPlan().hasClaroServices()) {
            //Título
            WebElement claroServicesTitle = driverQA.findElement(planContentParent + "//div[contains(@class, 'claro-services')]/p", "xpath");

            //Apps
            List<WebElement> claroServicesApps = driverQA.findElements(planContentParent + "//div[contains(@class, 'claro-services')]//img", "xpath");

            validarServicosClaro(driverQA, cartOrder.getPlan(), claroServicesTitle, claroServicesApps);
        }

        //Valida preço
        String priceRef = cartOrder.getPlan().getFormattedPlanPrice(cartOrder.isDebitPaymentFlow, cartOrder.hasLoyalty);
        validateElementText(priceRef, planContentParent + "//span[contains(@class, 'js-entry-price-plan')]");

        //Valida método de pagamento
        String paymentModeRef = cartOrder.isDebitPaymentFlow ? "Débito automático" : "Boleto";
        validateElementText(paymentModeRef, planContentParent + "//dt[@class='mdn-Price-suffix']");

        //Valida fidelização
        String loyaltyRef = cartOrder.hasLoyalty ? "Fidelizado por 12 meses" : "Sem fidelização";
        validateElementText(loyaltyRef, planContentParent + "//dt[@class='mdn-Price-suffix hidden-xs hidden-sm']");
    }

    public void validarResumoCompraAparelho(CartOrder cart, boolean eSimFlow) {
        driverQA.actionPause(2000);

        String deviceContentParent;

        //TODO Tela de Parabens tem outra classe. Remover este bloco e atualizar os xpath apos ter sido implementado os atributos seletores no front
        ///////////////////////
        if (driverQA.findElement("//*[@class='mdn-Container-fluid']", "xpath") != null) {
            deviceContentParent = "//*[@class='mdn-Container-fluid']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
        } else {
            deviceContentParent = "//*[@class='mdn-Container']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
        }
        ///////////////////////

        //TODO Atualizar seletor
        //deviceContentParent = "//*[@class='mdn-Container-fluid']/div/div[contains(@class, 'mdn-u-my-sm-3')]";

        Function<Double, String> formatPrice = price -> "R$ " + String.format(Locale.GERMAN, "%,.2f", price);

        double basePrice = cart.getEntryBasePrice(cart.getDevice());

        //Subtotal
        double subTotal = eSimFlow ? basePrice : basePrice + 10D;
        String subtotalSelector = deviceContentParent + "//*[@id='sidebar-resume']/div/div[1]/div[1]";
        driverQA.javaScriptClick(deviceContentParent + "//*[@id='sidebar-resume']/div/a", "xpath");
        driverQA.waitElementVisible(driverQA.findElement(subtotalSelector, "xpath"), 5);
        Assert.assertEquals("Subtotal " + formatPrice.apply(subTotal), StringUtils.normalizeSpace(driverQA.findElement(subtotalSelector, "xpath").getText()));

        //Desconto Cupom
        if (cart.getAppliedCoupon() != null) {
            validateElementText("Desconto Cupom -" + formatPrice.apply(cart.getEntryDiscount(cart.getDevice())), deviceContentParent + "//*[@id='sidebar-resume']/div/div[1]/div[2]");
        }

        //Valor Total a Pagar
        double totalPrice = cart.getEntryTotalPrice(cart.getDevice());
        String totalPricea = eSimFlow ? formatPrice.apply(totalPrice) : formatPrice.apply(totalPrice + 10D);
        validateElementText(totalPricea, deviceContentParent + "//*[@id='sidebar-resume']/div/div[2]/p[2]");

        //Nome Aparelho
        if (!(cartOrder.getDevice().getName() == null)) {
            validateElementText(cart.getDevice().getName(), deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[1]/div[2]/p");
        }

        //Valor Aparelho
        validateElementText(formatPrice.apply(basePrice), deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[1]/div[2]/div[2]/p[2]");

        //Tipo Chip
        String simType = eSimFlow ? "eSIM" : "Chip Comum";
        validateElementText(simType, deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[2]/div[2]/div[1]/p");

        //Valor Chip
        String chipPrice = eSimFlow ? "Grátis" : "R$ 10,00";
        validateElementText(chipPrice, deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[2]/div[2]/div[3]/p[2]");
    }
}