package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.Product;
import web.support.utils.DriverQA;

import java.util.List;
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

    public static void validateElementText(String ref, WebElement element) {
        Assert.assertEquals(ref, element.getText().trim());
        Assert.assertTrue(element.isDisplayed());
    }

    //Valida os ícones dos Apps e Países da composição do Plano
    public static void validarMidiasPlano(List<String> appRef, List<WebElement> appFront, DriverQA driverQA) {
        Assert.assertEquals("Mesma quantidade de [apps/países configurados] e [presentes no Front]", appRef.size(), appFront.size());

        driverQA.waitElementVisibility(appFront.get(0), 2); //Lazy loading Front
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

    public static void validarAppsIlimitados(DriverQA driverQA, CartOrder cartOrder, WebElement planAppsTitle, List<WebElement> planApps) {
        //Valida título
        validateElementText(cartOrder.getPlan().getPlanAppsTitle(), planAppsTitle);

        //Valida Apps
        validarMidiasPlano(cartOrder.getPlan().getPlanApps(), planApps, driverQA);
    }

    public static void validarServicosClaro(DriverQA driverQA, CartOrder cartOrder, WebElement claroServicesTitle, List<WebElement> claroServicesApps) {
        //Valida título
        validateElementText(cartOrder.getPlan().getClaroServicesTitle(), claroServicesTitle);

        //Valida Apps
        validarMidiasPlano(cartOrder.getPlan().getClaroServices(), claroServicesApps, driverQA);
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
            Assert.assertEquals(plan.getPlanPortability().get(i), planPortability.get(i).getText());

            Assert.assertTrue("Texto planPortability visível", planPortability.get(i).isDisplayed());
        });
    }

    public void validarResumoCompraPlano() {
        driverQA.actionPause(1500);
        driverQA.mobileClick("(//div[@class='js-cart-entry-update']//a)[1]", "xpath");

        String planContentParent = "//*[contains(@class, 'col-layout-plan') and not(contains(@class, 'visible-mobile'))]/div/div";

        //Valida nome, caso configurado
                WebElement planName = driverQA.findElement(planContentParent + "//span[contains(@class, 'product-fullname')]", "xpath");
                validateElementText(cartOrder.getPlan().getName(), planName);

        //Valida app ilimitados, caso configurado
        if (cartOrder.getPlan().hasPlanApps() && cartOrder.hasLoyalty) {
            //Título
            WebElement planAppsTitle = driverQA.findElement(planContentParent + "//div[contains(@class, ' apps-ilimitados')]/div[1]/div", "xpath");
            //Apps
            List<WebElement> planApps = driverQA.findElements(planContentParent + "//div[contains(@class, ' apps-ilimitados')]//img", "xpath");

            validarAppsIlimitados(driverQA, cartOrder, planAppsTitle, planApps);
        }

        //Valida título extraPlay, caso configurado
        if (cartOrder.getPlan().hasExtraPlayTitle()) {
            WebElement claroTitleExtraPlay = driverQA.findElement(planContentParent + "//div[contains(@class, 'product-card-content')]/p", "xpath");
            validateElementText(cartOrder.getPlan().getExtraPlayTitle(), claroTitleExtraPlay);
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

            validarServicosClaro(driverQA, cartOrder, claroServicesTitle, claroServicesApps);
        }

        //Valida preço
        WebElement price = driverQA.findElement(planContentParent + "//span[contains(@class, 'js-entry-price-plan')]", "xpath");

        String priceRef = null;
        String planCode = cartOrder.getPlan().getCode();
        if (planCode.equals("17536") || planCode.equals("17558") || planCode.equals(("17528"))) {
            priceRef = cartOrder.getPlan().getFormattedPlanPrice(false, cartOrder.hasLoyalty);
        } else {
            priceRef = cartOrder.getPlan().getFormattedPlanPrice(cartOrder.isDebitPaymentFlow, cartOrder.hasLoyalty);
        }

        validateElementText(priceRef, price);

        //Valida método de pagamento
        WebElement paymentMode = driverQA.findElement(planContentParent + "//dt[@class='mdn-Price-suffix']", "xpath");

        String paymentModeRef = cartOrder.isDebitPaymentFlow ? "Débito automático" : "Boleto";
        validateElementText(paymentModeRef, paymentMode);

        //Valida fidelização
        WebElement loyalty = driverQA.findElement(planContentParent + "//dt[@class='mdn-Price-suffix hidden-xs hidden-sm']", "xpath");

        String loyaltyRef = cartOrder.hasLoyalty ? "Fidelizado por 12 meses" : "Sem fidelização";
        validateElementText(loyaltyRef, loyalty);
    }

    public void validarResumoCompraAparelho() {
        //TODO ECCMAUT-351
    }
}