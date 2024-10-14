package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.models.product.PlanProduct;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static web.support.utils.Constants.DEPENDENT_PRICE;
import static web.support.utils.Constants.ProcessType.*;

@Component
@ScenarioScope
public class ComumPage {

    private final DriverWeb driverWeb;

    @Autowired
    public ComumPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;

        PageFactory.initElements(driverWeb.getDriver(), this);
    }

    //Resumo Planos
    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='name']")
    private WebElement planNameDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='name']")
    private WebElement planNameMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='planappstitle']")
    private WebElement planAppsTitleDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='planappstitle']")
    private WebElement planAppsTitleMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='planapps']//img")
    private List<WebElement> planAppsDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='planapps']//img")
    private List<WebElement> planAppsMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='extraplaytitle']")
    private WebElement extraPlayTitleDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='extraplaytitle']")
    private WebElement extraPlayTitleMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='extraplayapps']//img")
    private List<WebElement> extraPlayAppsDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='extraplayapps']//img")
    private List<WebElement> extraPlayAppsMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='services']/p")
    private WebElement claroServicesTitleDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='services']/p")
    private WebElement claroServicesTitleMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='services']//img")
    private List<WebElement> claroServicesAppsDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='services']//img")
    private List<WebElement> claroServicesAppsMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='dependentquantity']")
    private WebElement dependentQuantityDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='dependentquantity']")
    private WebElement dependentQuantityMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='dependentprice']")
    private WebElement dependentPriceDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='dependentprice']")
    private WebElement dependentPriceMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='price']")
    private WebElement planPriceDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='price']")
    private WebElement planPriceMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='paymentmode']")
    private WebElement paymentModeDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='paymentmode']")
    private WebElement paymentModeMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='loyalty']")
    private WebElement loyaltyDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='loyalty-mobile']")
    private WebElement loyaltyMob;

    private void validateElementText(String ref, String xpath) {
        assertEquals("Texto do elemento diferente do esperado", ref, StringUtils.normalizeSpace(driverWeb.findByXpath(xpath).getText()));
        assertTrue("Elemento não exibido", driverWeb.findByXpath(xpath).isDisplayed());
    }

    public static void validateElementText(String ref, WebElement element) {
        assertEquals("Texto do elemento diferente do esperado", StringUtils.normalizeSpace(ref), StringUtils.normalizeSpace(element.getText()));
        assertTrue("Elemento não exibido", element.isDisplayed());
    }

    public static void validateElementActiveVisible(WebElement element) {
        assertTrue("Elemento desabilitado", element.isEnabled());
        assertTrue("Elemento não exibido", element.isDisplayed());
    }

    //Valida os ícones dos Apps e Países da composição do Plano
    public static void validarMidiasPlano(List<String> appRef, List<WebElement> appFront, DriverWeb driverWeb) {
        assertEquals("Mesma quantidade de [apps/países configurados] e [presentes no Front]", appRef.size(), appFront.size());

        driverWeb.waitElementVisible(appFront.get(0), 2); //Lazy loading Front
        IntStream.range(0, appRef.size()).forEachOrdered(i -> {
            assertTrue("App/País do Front deve ser o mesmo da API - Front: <" + appFront.get(i)
                            .getAttribute("src")
                            .replace("https://mondrian.claro.com.br/brands/app/72px-default/", "") + ">, API: <" + appRef.get(i) + ">",
                    appFront.get(i).getAttribute("src").contains(appRef.get(i)));

            if (i < 5) { //Até 5 ícones são exibidos diretamente, o restante fica oculto no tooltip (+X).
                assertTrue("App/País deve estar visível", appFront.get(i).isDisplayed());
            } else {
                assertFalse("App/País deve estar oculto", appFront.get(i).isDisplayed());
            }
        });
    }

    public static void validarAppsIlimitados(DriverWeb driverWeb, PlanProduct plan, WebElement planAppsTitle, List<WebElement> planApps) {
        //Valida título
        validateElementText(plan.getPlanAppsTitle(), planAppsTitle);

        //Valida Apps
        validarMidiasPlano(plan.getPlanApps(), planApps, driverWeb);
    }

    public static void validarServicosClaro(DriverWeb driverWeb, PlanProduct plan, WebElement claroServicesTitle, List<WebElement> claroServicesApps) {
        //Valida título
        validateElementText(plan.getClaroServicesTitle(), claroServicesTitle);

        //Valida Apps
        validarMidiasPlano(plan.getClaroServices(), claroServicesApps, driverWeb);
    }

    public static void validarPlanPortability(List<WebElement> planPortability, PlanProduct plan) {
        //Remove o elemento do [título extraPlay] que vem junto na lista, planportability e clarotitleextraplay usam as mesmas classes css.
        //A posição entre eles pode mudar, não servindo como referência.
        if (plan.hasExtraPlayTitle()) {
            planPortability.remove(planPortability
                    .stream()
                    .filter(webElement -> webElement.getText().equals(plan.getExtraPlayTitle()))
                    .findFirst().orElseThrow());
        }

        IntStream.range(0, planPortability.size()).forEachOrdered(i -> {
            assertEquals("Texto planPortability igual ao configurado", plan.getPlanPortability().get(i), planPortability.get(i).getText());

            assertTrue("Texto planPortability visível", planPortability.get(i).isDisplayed());
        });
    }

    public static String formatPrice(double price) {
        return String.format(Locale.GERMAN, "%,.2f", price);
    }

    public void validarResumoCompraPlano(CartOrder cart) {
        driverWeb.actionPause(2000);

        WebElement planName;
        WebElement planAppsTitle;
        List<WebElement> planApps;
        WebElement extraPlayTitle;
        List<WebElement> extraPlayApps;
        WebElement claroServicesTitle;
        List<WebElement> claroServicesApps;
        WebElement dependentQuantity;
        WebElement dependentPrice;
        WebElement planPrice;
        WebElement planPaymentMode;
        WebElement planLoyalty;

        if (driverWeb.isMobile()) {
            planName = planNameMob;
            planAppsTitle = planAppsTitleMob;
            planApps = planAppsMob;
            extraPlayTitle = extraPlayTitleMob;
            extraPlayApps = extraPlayAppsMob;
            claroServicesTitle = claroServicesTitleMob;
            claroServicesApps = claroServicesAppsMob;
            dependentQuantity = dependentQuantityMob;
            dependentPrice = dependentPriceMob;
            planPrice = planPriceMob;
            planPaymentMode = paymentModeMob;
            planLoyalty = loyaltyMob;
        } else {
            planName = planNameDesk;
            planAppsTitle = planAppsTitleDesk;
            planApps = planAppsDesk;
            extraPlayTitle = extraPlayTitleDesk;
            extraPlayApps = extraPlayAppsDesk;
            claroServicesTitle = claroServicesTitleDesk;
            claroServicesApps = claroServicesAppsDesk;
            dependentQuantity = dependentQuantityDesk;
            dependentPrice = dependentPriceDesk;
            planPrice = planPriceDesk;
            planPaymentMode = paymentModeDesk;
            planLoyalty = loyaltyDesk;
        }

        PlanProduct plan = cart.getPlan();
        boolean isDebit = cart.isDebitPaymentFlow;
        boolean hasLoyalty = cart.hasLoyalty;

        //Valida nome
        validateElementText(plan.getName(), planName);

        //Valida app ilimitados, caso configurado
        if (plan.hasPlanApps() && hasLoyalty) {
            validarAppsIlimitados(driverWeb, plan, planAppsTitle, planApps);
        }

        //Valida título extraPlay, caso configurado
        if (plan.hasExtraPlayTitle()) {
            validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
        }

        //Valida apps extraPlay, caso configurado
        if (plan.hasExtraPlayApps()) {
            validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
        }

        //Valida serviços Claro, caso configurado
        if (plan.hasClaroServices()) {
            validarServicosClaro(driverWeb, plan, claroServicesTitle, claroServicesApps);
        }

        //Valida dependentes adicionados
        int depQtt = cart.hasDependent();
        if (depQtt > 0) {
            String depQuantityRef = String.format("+ %d %s", depQtt, depQtt == 1 ? "dependente" : "dependentes");
            validateElementText(depQuantityRef, dependentQuantity);

            String depPriceAndChipRef = String.format("R$ %s ( + %d %s)", formatPrice(depQtt * DEPENDENT_PRICE), depQtt, depQtt == 1 ? "chip para dependente" : "chips para dependentes");
            validateElementText(depPriceAndChipRef, dependentPrice);
        }

        //Valida preço
        double priceRef = cart.getEntry(cart.getPlan().getCode()).getTotalPrice();
        if (depQtt > 0) { //Com dep
            priceRef += cart.getEntry("dependente").getTotalPrice();
        }
        validateElementText(formatPrice(priceRef), planPrice);

        //Valida método de pagamento
        String paymentModeRef = isDebit ? "Débito automático" : "Boleto";
        validateElementText(paymentModeRef, planPaymentMode);

        //Valida fidelização
        String loyaltyRef = hasLoyalty ? "Fidelizado por 12 meses" : "Sem fidelização";
        validateElementText(loyaltyRef, planLoyalty);
    }

    public void validarResumoCompraAparelho(CartOrder cart) {
        driverWeb.actionPause(1000);

        boolean isGrossFlow = cart.getProcessType() == ACQUISITION || cart.getProcessType() == PORTABILITY;
        boolean hasCommonSIM = !cart.isEsim() && isGrossFlow;

        String deviceContentParent;
        //TODO Tela de Parabens tem outra classe. Remover este bloco e atualizar os xpath apos ter sido implementado os atributos seletores no front
        ///////////////////////
        if (driverWeb.findElement("//*[@class='mdn-Container-fluid']", "xpath") != null) {
            deviceContentParent = "//*[@class='mdn-Container-fluid']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
        } else {
            deviceContentParent = "//*[@class='mdn-Container']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
        }
        ///////////////////////

        //Subtotal
        double subtotal = cart.getDevice().getCampaignPrice(!hasCommonSIM);
        String subtotalSelector = deviceContentParent + "//*[@id='sidebar-resume']/div/div[1]/div[1]";
        driverWeb.javaScriptClick(deviceContentParent + "//*[@id='sidebar-resume']/div/a", "xpath");
        driverWeb.waitElementVisible(driverWeb.findElement(subtotalSelector, "xpath"), 5);
        //TODO Bug ECCMAUT-806 validateElementText("Subtotal R$ " + formatPrice(subtotal), subtotalSelector);

        //Desconto Cupom
        if (cart.getAppliedCoupon() != null) {
            validateElementText("Desconto Cupom -R$ " + formatPrice(cart.getEntry(cart.getDevice().getCode()).getDiscount()), deviceContentParent + "//*[@id='sidebar-resume']/div/div[1]/div[2]");
        }

        //Valor Total a Pagar
        double totalPrice = cart.getEntry(cart.getDevice().getCode()).getTotalPrice();
        if (hasCommonSIM) {
            totalPrice += 10D;
        }
        //TODO Bug ECCMAUT-806 validateElementText("R$ " + formatPrice(totalPrice), deviceContentParent + "//*[@id='sidebar-resume']/div/div[2]/p[2]");

        //Nome Aparelho
        assertNotNull(cart.getDevice().getName());
        validateElementText(cart.getDevice().getName(), deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[1]/div[2]/p");

        //Valor Campanha Aparelho
        //TODO Bug ECCMAUT-806 validateElementText(cart.getDevice().getFormattedCampaignPrice(true), deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[1]/div[2]/div[2]/p[2]");

        //Tipo Chip
        if (isGrossFlow) {
            String simType = cart.isEsim() ? "eSIM" : "Chip Comum";
            validateElementText(simType, deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[2]/div[2]/div[1]/p");

            //Valor Chip
            String chipPrice = cart.isEsim() ? "Grátis" : "R$ 10,00";
            validateElementText(chipPrice, deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div[1]/ul/li[2]/div[2]/div[3]/p[2]");
        }
    }
}