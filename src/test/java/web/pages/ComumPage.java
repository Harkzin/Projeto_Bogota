package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.models.product.DeviceProduct;
import web.models.product.PlanProduct;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static web.models.product.PlanProduct.*;
import static web.models.product.PlanProduct.Passport.*;
import static web.models.product.PlanProduct.Passport.PassportTraffic.*;
import static web.support.utils.Constants.ChipType.ESIM;
import static web.support.utils.Constants.ChipType.SIM;
import static web.support.utils.Constants.DEPENDENT_PRICE;
import static web.support.utils.Constants.ProcessType.*;

@Component
@ScenarioScope
public class ComumPage {

    private final DriverWeb driverWeb;

    @Autowired
    public ComumPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;

        //PageFactory.initElements(driverWeb.getDriver(), this);
    }

    /*//Resumo Planos
    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='name']")
    private WebElement planNameDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='name']")
    private WebElement planNameMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='bonus']")
    private List<WebElement> dataBonusDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='bonus']")
    private List<WebElement> dataBonusMob;

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

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='passport']")
    private List<WebElement> passportDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='passport']")
    private List<WebElement> passportMob;

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

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='fullprice']")
    private WebElement planFullPriceDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='fullprice']")
    private WebElement planFullPriceMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='price']")
    private WebElement planPriceDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='price']")
    private WebElement planPriceMob;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='price-mobile']")
    private WebElement planPriceMobHeader;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='paymentmode']")
    private WebElement paymentModeDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='paymentmode']")
    private WebElement paymentModeMob;

    @FindBy(xpath = "//*[@id='cart-summary']//*[@data-plan-content='loyalty']")
    private WebElement loyaltyDesk;

    @FindBy(xpath = "//*[@id='cart-summary-mobile']//*[@data-plan-content='loyalty-mobile']")
    private WebElement loyaltyMob;*/

    public static void validateElementText(String ref, WebElement element) {
        assertEquals("Texto do elemento diferente do esperado", StringUtils.normalizeSpace(ref), StringUtils.normalizeSpace(element.getText()));
        assertTrue("Elemento nao exibido", element.isDisplayed());
    }

    public static void validateElementActiveVisible(WebElement element) {
        assertTrue("Elemento desabilitado", element.isEnabled());
        assertTrue("Elemento nao exibido", element.isDisplayed());
    }

    public static void validatePlanMedias(List<String> mediaRef, List<WebElement> mediaFront, DriverWeb driverWeb) {
        //Valida os ícones dos Apps e Países da composição do Plano

        assertEquals("Quantidade de apps/paises [configurados] diferente dos [exibidos] no Front", mediaRef.size(), mediaFront.size());

        driverWeb.waitElementVisible(mediaFront.get(0), 2); //Lazy loading Front
        IntStream.range(0, mediaRef.size()).forEachOrdered(i -> {
            String mediaFrontSrc = mediaFront.get(i).getAttribute("src");
            String mediaFrontName = mediaFrontSrc.replace("https://mondrian.claro.com.br/brands/app/72px-default/", "");

            assertTrue(String.format("Imagem do Front deve ser a mesma da API - Front: <%s>, API: <%s>", mediaFrontName, mediaRef.get(i)), mediaFrontSrc.contains(mediaRef.get(i)));

            if (i < 5) { //Até 5 ícones são exibidos, o restante fica oculto no tooltip (+N).
                assertTrue("Imagem deve estar visivel", mediaFront.get(i).isDisplayed());
            } else {
                assertFalse("Imagem deve estar oculta", mediaFront.get(i).isDisplayed());
            }
        });
    }

    public static void validatePlanApps(PlanProduct plan, WebElement planAppsTitle, List<WebElement> planApps, DriverWeb driverWeb) {
        //Valida título
        validateElementText(plan.getPlanAppsTitle(), planAppsTitle);

        //Valida Apps
        validatePlanMedias(plan.getPlanApps(), planApps, driverWeb);
    }

    public static void validatePlanPassport(List<Passport> passportRef, List<WebElement> passportFront, DriverWeb driverWeb) {
        IntStream.range(0, passportRef.size()).forEachOrdered(i -> {
            //Titulo passaporte
            WebElement passportName = passportFront.get(i).findElement(By.tagName("p"));
            assertTrue("Nome do passaporte nao exibido", passportName.isDisplayed());
            assertTrue("Nome do passaporte diferente", passportRef.get(i).getDescription().contains(passportName.getText()));

            //Paises
            List<WebElement> countriesFront = passportFront.get(i).findElements(By.tagName("img"));
            List<Country> countriesRef = passportRef.get(i)
                    .getPassportTraffics().stream()
                    .map(PassportTraffic::getCountry)
                    .toList();

            List<String> countriesRefUrl = countriesRef.stream().map(Country::getUrl).toList();
            validatePlanMedias(countriesRefUrl, countriesFront, driverWeb);

            //AltText
            IntStream.range(0, countriesRef.size()).forEachOrdered(c ->
                    assertEquals("AltText nome pais", countriesRef.get(c).getAltText(), countriesFront.get(c).getAttribute("alt"))
            );
        });
    }

    public static void validateClaroServices(PlanProduct plan, WebElement claroServicesTitle, List<WebElement> claroServicesApps, DriverWeb driverWeb) {
        //Valida título
        validateElementText(plan.getClaroServicesTitle(), claroServicesTitle);

        //Valida Apps
        validatePlanMedias(plan.getClaroServices(), claroServicesApps, driverWeb);
    }

    public static void validatePlanPortability(PlanProduct plan, List<WebElement> planPortability) {
        if (plan.hasExtraPlayTitle()) {
            //TODO Refactor quando houver seletor
            //Remove o elemento do [título extraPlay] que vem junto na lista, planportability e clarotitleextraplay usam as mesmas classes css.
            //A posição entre eles pode mudar, não servindo como referência.
            planPortability.remove(planPortability
                    .stream()
                    .filter(webElement -> webElement.getText().equals(plan.getExtraPlayTitle()))
                    .findFirst().orElseThrow());
        }

        IntStream.range(0, planPortability.size()).forEachOrdered(i ->
                validateElementText(plan.getPlanPortability().get(i), planPortability.get(i))
        );
    }

    public static String formatPrice(double price) {
        return String.format(Locale.GERMAN, "%,.2f", price);
    }

    public void validarResumoCompraPlano(CartOrder cart) {
        driverWeb.actionPause(2000);

        PlanProduct plan = cart.getPlan();
        boolean hasLoyalty = cart.hasLoyalty();
        int depQtt = cart.hasDependent();

        String planContentParent;
        if (cart.isDeviceCart()) {
            planContentParent = "//*[@id='sidebar-resume' and not(ancestor::*[@id='cart-summary-mobile'])]//*[@class='render-claro-cart-entry']";
        } else {
            planContentParent = driverWeb.isMobile() ? "//*[@id='cart-summary-mobile']" : "//*[@id='cart-summary']";
        }

        //Valida nome
        String name = cart.getPromotion().isRentabilization() ? cart.getPromotion().getName() : plan.getName();
        WebElement planName = driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='name']");
        driverWeb.javaScriptScrollTo(planName);
        validateElementText(name, planName);

        //Abre Resumo mobile
        if (driverWeb.isMobile() && !cart.isDeviceCart()) {
            driverWeb.javaScriptClick(driverWeb.findById("cart-info-toggle"));
            driverWeb.waitElementVisible(planName, 2); //Usa o nome como referência para aguardar o Resumo mobile abrir
        }

        if (plan.getCategories().stream().noneMatch(c -> c.getCode().equals("prepago"))) { //Não valida caso seja carrinho com Plano Pré
            //Valida bônus, caso configurado
            List<WebElement> dataBonus = driverWeb.findElements(planContentParent + "//*[@data-plan-content='bonus']", "xpath");

            if (plan.hasBonus() && !cart.getPromotion().isRentabilization()) {
                assertEquals("Quantidade de items configurados vs exibidos no Front diferente", plan.getDataBonus().size(), dataBonus.size());

                IntStream.range(0, plan.getDataBonus().size()).forEachOrdered(i ->
                        validateElementText(plan.getDataBonus().get(i), dataBonus.get(i))
                );
            }

            //Valida app ilimitados, caso configurado
            if (plan.hasPlanApps() && hasLoyalty) {
                //Título
                WebElement planAppsTitle = driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='planappstitle']");

                //Apps
                List<WebElement> planApps = driverWeb.findElements(planContentParent + "//*[@data-plan-content='planapps']//img", "xpath");

                validatePlanApps(plan, planAppsTitle, planApps, driverWeb);
            }

            //Valida título extraPlay, caso configurado
            if (plan.hasExtraPlayTitle()) {
                validateElementText(plan.getExtraPlayTitle(), driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='extraplaytitle']"));
            }

            //Valida apps extraPlay, caso configurado
            if (plan.hasExtraPlayApps()) {
                List<WebElement> extraPlayApps = driverWeb.findElements(planContentParent + "//*[@data-plan-content='extraplayapps']//img", "xpath");
                validatePlanMedias(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
            }

            //Valida passaporte(s), caso configurado
            if (plan.hasPassport()) {
                List<WebElement> passport = driverWeb.findElements(planContentParent + "//*[@data-plan-content='passport']", "xpath");
                validatePlanPassport(plan.getPassports(), passport, driverWeb);
            }

            //Valida serviços Claro, caso configurado
            if (plan.hasClaroServices()) {
                //Título
                WebElement claroServicesTitle = driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='services']/p");

                //Apps
                List<WebElement> claroServicesApps = driverWeb.findElements(planContentParent + "//*[@data-plan-content='services']//img", "xpath");

                validateClaroServices(plan, claroServicesTitle, claroServicesApps, driverWeb);
            }

            //Valida dependentes adicionados
            if (depQtt > 0) {
                String depQuantityRef = String.format("+ %d %s", depQtt, depQtt == 1 ? "dependente" : "dependentes");
                validateElementText(depQuantityRef, driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='dependentquantity']"));

                String depPriceAndChipRef = String.format("R$ %s ( + %d %s)", formatPrice(depQtt * DEPENDENT_PRICE), depQtt, depQtt == 1 ? "chip para dependente" : "chips para dependentes");
                validateElementText(depPriceAndChipRef, driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='dependentprice']"));
            }

            //Valida método de pagamento
            String paymentModeRef = "";
            switch (cart.getEntry(cart.getPlan().getCode()).getPaymentMode()) {
                case TICKET -> paymentModeRef = "Boleto";
                case DEBITCARD -> paymentModeRef = "Débito automático";
                //TODO cartão de crédito (controle fácil)
            }
            validateElementText(paymentModeRef, driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='paymentmode']"));

            //Valida fidelização
            String loyaltyRef = hasLoyalty ? "Fidelizado por 12 meses" : "Sem fidelização";
            String loyaltyPlatform = driverWeb.isMobile() ? "//*[@data-plan-content='loyalty-mobile']" : "//*[@data-plan-content='loyalty']";
            validateElementText(loyaltyRef, driverWeb.findByXpath(planContentParent + loyaltyPlatform));
        }

        //Valida preço
        double priceRef = cart.getEntry(cart.getPlan().getCode()).getTotalPrice();
        if (depQtt > 0) { //Com dep
            priceRef += cart.getEntry("dependente").getTotalPrice();
        }

        String basePriceFormattedRef = cart.getPlan().getFormattedPrice();
        String totalPriceFormattedRef = formatPrice(priceRef);
        String mobilePriceSummaryHeaderRef;
        WebElement planPrice = driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='price']");

        if (cart.getPromotion().isRentabilization()) { //Cart rentab
            //Preço "De"
            String fullPriceRef = String.format("De %s", basePriceFormattedRef);
            validateElementText(fullPriceRef, driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='fullprice']/.."));

            //Preço "por"
            String rentabilizationPriceRef = String.format("por R$ %s /mês", totalPriceFormattedRef);
            validateElementText(rentabilizationPriceRef, planPrice.findElement(By.xpath("..")));

            mobilePriceSummaryHeaderRef = String.format("De %s | R$ %s", basePriceFormattedRef, totalPriceFormattedRef);
        } else { //Cart comum
            validateElementText(totalPriceFormattedRef, planPrice);
            mobilePriceSummaryHeaderRef = String.format(" | %s", totalPriceFormattedRef);
        }

        //Valida preço no header do Resumo mobile
        if (driverWeb.isMobile() && !cart.isDeviceCart()) {
            //TODO ECCMAUT-1408 validateElementText(mobilePriceSummaryHeaderRef, driverWeb.findByXpath(planContentParent + "//*[@data-plan-content='price-mobile']"));
        }

        driverWeb.actionPause(500);
    }

    public void validarResumoCompraAparelho(CartOrder cart) {
        driverWeb.actionPause(2000);

        DeviceProduct device = cart.getDevice();
        boolean isGrossFlow = cart.getProcessType() == ACQUISITION || cart.getProcessType() == PORTABILITY;
        boolean commonChipFlow = cart.getClaroChip().getChipType() == SIM;
        String deviceContentParent = "//*[@id='sidebar-resume' and not(ancestor::*[@id='cart-summary-mobile'])]";

        //Subtotal
        double subtotalPrice = device.getCampaignPrice(!commonChipFlow);
        WebElement subtotal = driverWeb.findByXpath(deviceContentParent + "//*[@id='txt-valor-subtotal']/..");
        driverWeb.javaScriptClick(deviceContentParent + "/div/a", "xpath");
        driverWeb.waitElementVisible(subtotal, 5);
        validateElementText("Subtotal: R$ " + formatPrice(subtotalPrice), subtotal);

        //Envio
        validateElementText("Envio: Grátis", driverWeb.findByXpath(deviceContentParent + "//*[@id='txt-envio']/.."));

        //Desconto Cupom
        if (cart.getAppliedCoupon() != null) {
            String voucherDiscountPriceRef = formatPrice(cart.getEntry(device.getCode()).getDiscount());
            validateElementText("Desconto Cupom -R$ " + voucherDiscountPriceRef, driverWeb.findByXpath(deviceContentParent + "/div/div[1]/div/div[3]"));
        }

        //Total
        double totalPrice = cart.getEntry(device.getCode()).getTotalPrice();
        if (commonChipFlow) {
            totalPrice += 10D;
        }
        validateElementText("Total: R$ " + formatPrice(totalPrice), driverWeb.findByXpath(deviceContentParent + "//*[@id='txt-valor-total']/.."));

        //Nome Aparelho
        validateElementText(device.getName(), driverWeb.findByXpath(deviceContentParent + "//*[@id='render-claro-cart-entry-content']/div/div/div/div[2]/div/p"));

        //Cor
        validateElementText("Cor: " + device.getColor(), driverWeb.findByXpath(deviceContentParent + "//*[@id='txt-descricao-cor']/.."));

        //Valor Campanha
        validateElementText("Valor: " + device.getFormattedCampaignPrice(true), driverWeb.findByXpath(String.format("(%s//*[@id='txt-valor'])[1]/..", deviceContentParent)));

        //Chip
        if (isGrossFlow) {
            boolean eSimFlow = cart.getClaroChip().getChipType() == ESIM;

            //Tipo
            String simType = eSimFlow ? "eSIM" : "Chip Comum";
            validateElementText(simType, driverWeb.findByXpath(deviceContentParent + "//*[@id='render-claro-cart-entry-content']//div[not(contains(@class, 'modalEsim'))][2]/div/div/div[2]/div[1]/p"));

            //Valor
            String chipPrice = "Valor: " + (eSimFlow ? "Grátis" : "R$ 10,00");
            validateElementText(chipPrice, driverWeb.findByXpath(String.format("(%s//*[@id='txt-valor'])[2]/..", deviceContentParent)));
        }
    }
}