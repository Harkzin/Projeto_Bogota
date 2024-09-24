package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.models.Product;
import web.support.utils.DriverQA;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.IntStream;

import static web.support.utils.Constants.DEPENDENT_PRICE;

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

    public static String formatPrice(double price) {
        return String.format(Locale.GERMAN, "%,.2f", price);
    }

    public void validarResumoCompraPlano(CartOrder cart) {
        driverQA.actionPause(2000);

        Product plan = cart.getPlan();
        boolean isDebit = cart.isDebitPaymentFlow;
        boolean hasLoyalty = cart.hasLoyalty;
        String contentParent = driverQA.isMobile() ? "//*[@id='cart-summary-mobile']" : "//*[@id='cart-summary']";

        //Valida nome, caso configurado
        if (!(plan.getName() == null)) {
            driverQA.waitElementPresence(contentParent + "//*[@data-plan-content='name']", 10);
            validateElementText(plan.getName(), contentParent + "//*[@data-plan-content='name']");
        }

        //Valida app ilimitados, caso configurado
        if (plan.hasPlanApps() && hasLoyalty) {
            //Título
            WebElement planAppsTitle = driverQA.findByXpath(contentParent + "//*[@data-plan-content='planappstitle']");

            //Apps
            List<WebElement> planApps = driverQA.findElements(contentParent + "//*[@data-plan-content='planapps']//img", "xpath");

            validarAppsIlimitados(driverQA, plan, planAppsTitle, planApps);
        }

        //Valida título extraPlay, caso configurado
        if (plan.hasExtraPlayTitle()) {
            validateElementText(plan.getExtraPlayTitle(), contentParent + "//*[@data-plan-content='extraplaytitle']");
        }

        //Valida apps extraPlay, caso configurado
        if (plan.hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = driverQA.findElements(contentParent + "//*[@data-plan-content='extraplayapps']//img", "xpath");

            validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverQA);
        }

        //Valida serviços Claro, caso configurado
        if (plan.hasClaroServices()) {
            //Título
            WebElement claroServicesTitle = driverQA.findByXpath(contentParent + "//*[@data-plan-content='services']/p");

            //Apps
            List<WebElement> claroServicesApps = driverQA.findElements(contentParent + "//*[@data-plan-content='services']//img", "xpath");

            validarServicosClaro(driverQA, plan, claroServicesTitle, claroServicesApps);
        }

        //Valida dependentes adicionados
        int depQtt = cart.hasDependent();
        if (depQtt > 0) {
            String depQuantityRef = String.format("+ %d %s", depQtt, depQtt == 1 ? "dependente" : "dependentes");
            validateElementText(depQuantityRef, contentParent + "//*[@data-plan-content='dependentquantity']");

            String depPriceAndChipRef = String.format("R$ %s ( + %d %s)", formatPrice(depQtt * DEPENDENT_PRICE), depQtt, depQtt == 1 ? "chip para dependente" : "chips para dependentes");
            validateElementText(depPriceAndChipRef, contentParent + "//*[@data-plan-content='dependentprice']");
        }

        //Valida preço
        double priceRef = cart.getEntry(cart.getPlan().getCode()).getTotalPrice();
        if (depQtt > 0) { //Com dep
            priceRef += cart.getEntry("dependente").getTotalPrice();
        }
        validateElementText(formatPrice(priceRef), contentParent + "//*[@data-plan-content='price']");

        //Valida método de pagamento
        String paymentModeRef = isDebit ? "Débito automático" : "Boleto";
        validateElementText(paymentModeRef, contentParent + "//*[@data-plan-content='paymentmode']");

        //Valida fidelização
        String loyaltyRef = hasLoyalty ? "Fidelizado por 12 meses" : "Sem fidelização";
        String loyaltyPlatform = driverQA.isMobile() ? "//*[@data-plan-content='loyalty-mobile']" : "//*[@data-plan-content='loyalty']";
        validateElementText(loyaltyRef, contentParent + loyaltyPlatform);
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