package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

        PageFactory.initElements(driverWeb.getDriver(), this);
    }

    //Resumo Planos
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
    private WebElement loyaltyMob;

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

        WebElement planName;
        List<WebElement> dataBonus;
        WebElement planAppsTitle;
        List<WebElement> planApps;
        WebElement extraPlayTitle;
        List<WebElement> extraPlayApps;
        List<WebElement> passport;
        WebElement claroServicesTitle;
        List<WebElement> claroServicesApps;
        WebElement dependentQuantity;
        WebElement dependentPrice;
        WebElement planFullPrice;
        WebElement planPrice;
        WebElement planPaymentMode;
        WebElement planLoyalty;

        if (driverWeb.isMobile()) {
            planName = planNameMob;
            dataBonus = dataBonusMob;
            planAppsTitle = planAppsTitleMob;
            planApps = planAppsMob;
            extraPlayTitle = extraPlayTitleMob;
            extraPlayApps = extraPlayAppsMob;
            passport = passportMob;
            claroServicesTitle = claroServicesTitleMob;
            claroServicesApps = claroServicesAppsMob;
            dependentQuantity = dependentQuantityMob;
            dependentPrice = dependentPriceMob;
            planFullPrice = planFullPriceMob;
            planPrice = planPriceMob;
            planPaymentMode = paymentModeMob;
            planLoyalty = loyaltyMob;
        } else {
            planName = planNameDesk;
            dataBonus = dataBonusDesk;
            planAppsTitle = planAppsTitleDesk;
            planApps = planAppsDesk;
            extraPlayTitle = extraPlayTitleDesk;
            extraPlayApps = extraPlayAppsDesk;
            passport = passportDesk;
            claroServicesTitle = claroServicesTitleDesk;
            claroServicesApps = claroServicesAppsDesk;
            dependentQuantity = dependentQuantityDesk;
            dependentPrice = dependentPriceDesk;
            planFullPrice = planFullPriceDesk;
            planPrice = planPriceDesk;
            planPaymentMode = paymentModeDesk;
            planLoyalty = loyaltyDesk;
        }

        PlanProduct plan = cart.getPlan();
        boolean hasLoyalty = cart.hasLoyalty();
        int depQtt = cart.hasDependent();

        //Valida nome
        String name = cart.getPromotion().isRentabilization() ? cart.getPromotion().getName() : plan.getName();
        validateElementText(name, planName);

        //Abre Resumo mobile
        if (driverWeb.isMobile() && !cart.isDeviceCart()) {
            driverWeb.javaScriptClick(driverWeb.findById("cart-info-toggle"));
            driverWeb.waitElementVisible(planPrice, 2); //Usa o preço como referencia para aguardar o Resumo mobile abrir
        }

        if (plan.getCategories().stream().noneMatch(c -> c.getCode().equals("prepago"))) { //Não valida caso seja carrinho com Plano Pré
            //Valida bônus, caso configurado
            if (plan.hasBonus() && !cart.getPromotion().isRentabilization()) {
                assertEquals("Quantidade de items configurados vs exibidos no Front diferente", plan.getDataBonus().size(), dataBonus.size());

                IntStream.range(0, plan.getDataBonus().size()).forEachOrdered(i ->
                        validateElementText(plan.getDataBonus().get(i), dataBonus.get(i))
                );
            }

            //Valida app ilimitados, caso configurado
            if (plan.hasPlanApps() && hasLoyalty) {
                validatePlanApps(plan, planAppsTitle, planApps, driverWeb);
            }

            //Valida título extraPlay, caso configurado
            if (plan.hasExtraPlayTitle()) {
                validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
            }

            //Valida apps extraPlay, caso configurado
            if (plan.hasExtraPlayApps()) {
                validatePlanMedias(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
            }

            //Valida passaporte(s), caso configurado
            if (plan.hasPassport()) {
                validatePlanPassport(plan.getPassports(), passport, driverWeb);
            }

            //Valida serviços Claro, caso configurado
            if (plan.hasClaroServices()) {
                validateClaroServices(plan, claroServicesTitle, claroServicesApps, driverWeb);
            }

            //Valida dependentes adicionados
            if (depQtt > 0) {
                String depQuantityRef = String.format("+ %d %s", depQtt, depQtt == 1 ? "dependente" : "dependentes");
                validateElementText(depQuantityRef, dependentQuantity);

                String depPriceAndChipRef = String.format("R$ %s ( + %d %s)", formatPrice(depQtt * DEPENDENT_PRICE), depQtt, depQtt == 1 ? "chip para dependente" : "chips para dependentes");
                validateElementText(depPriceAndChipRef, dependentPrice);
            }

            //Valida método de pagamento
            String paymentModeRef = "";
            switch (cart.getEntry(cart.getPlan().getCode()).getPaymentMode()) {
                case TICKET -> paymentModeRef = "Boleto";
                case DEBITCARD -> paymentModeRef = "Débito automático";
                //TODO cartão de crédito
            }
            validateElementText(paymentModeRef, planPaymentMode);

            //Valida fidelização
            String loyaltyRef = hasLoyalty ? "Fidelizado por 12 meses" : "Sem fidelização";
            validateElementText(loyaltyRef, planLoyalty);
        }

        //Valida preço
        double priceRef = cart.getEntry(cart.getPlan().getCode()).getTotalPrice();
        if (depQtt > 0) { //Com dep
            priceRef += cart.getEntry("dependente").getTotalPrice();
        }

        String basePriceFormatted = cart.getPlan().getFormattedPrice();
        String totalPriceFormatted = formatPrice(priceRef);
        String mobilePriceSummaryHeader;

        if (cart.getPromotion().isRentabilization()) { //Cart rentab
            //Preço "De"
            String fullPrice = String.format("De %s", basePriceFormatted);
            validateElementText(fullPrice, planFullPrice.findElement(By.xpath("..")));

            //Preço "por"
            String rentabilizationPrice = String.format("por R$ %s /mês", totalPriceFormatted);
            validateElementText(rentabilizationPrice, planPrice.findElement(By.xpath("..")));

            mobilePriceSummaryHeader = String.format("De %s | R$ %s", basePriceFormatted, totalPriceFormatted);
        } else { //Cart comum
            validateElementText(totalPriceFormatted, planPrice);
            mobilePriceSummaryHeader = String.format(" | %s", totalPriceFormatted);
        }
        if (driverWeb.isMobile() && !cart.isDeviceCart()) { //Preço mobile header do Resumo
            //TODO ECCMAUT-1408 validateElementText(mobilePriceSummaryHeader, planPriceMobHeader);
        }

        driverWeb.actionPause(500);
    }

    public void validarResumoCompraAparelho(CartOrder cart) {
        driverWeb.actionPause(2000);

        DeviceProduct device = cart.getDevice();
        boolean isGrossFlow = cart.getProcessType() == ACQUISITION || cart.getProcessType() == PORTABILITY;
        boolean commonChipFlow = cart.getClaroChip().getChipType() == SIM;

        String deviceContentParent = "//*[@class='mdn-Container-fluid']/div/div[contains(@class, 'mdn-u-my-sm-3')]";

        ///////////////////////
        //TODO Tela de Parabens tem outra classe. Remover este bloco e atualizar os xpath apos ter sido implementado os atributos seletores no front
        if (driverWeb.getDriver().getCurrentUrl().contains("orderConfirmation")) {
            deviceContentParent = "//*[@class='mdn-Container']/div/div[contains(@class, 'mdn-u-my-sm-3')]";
        }
        ///////////////////////

        //Subtotal
        double subtotalPrice = device.getCampaignPrice(!commonChipFlow);
        WebElement subtotal = driverWeb.findByXpath(deviceContentParent + "//*[@id='txt-valor-subtotal']/..");
        driverWeb.javaScriptClick(deviceContentParent + "//*[@id='sidebar-resume']/div/a", "xpath");
        driverWeb.waitElementVisible(subtotal, 5);
        validateElementText("Subtotal: R$ " + formatPrice(subtotalPrice), subtotal);

        //Envio
        validateElementText("Envio: Grátis", driverWeb.findByXpath(deviceContentParent + "//*[@id='txt-envio']/.."));

        //Desconto Cupom
        if (cart.getAppliedCoupon() != null) {
            validateElementText("Desconto Cupom -R$ " + formatPrice(cart.getEntry(device.getCode()).getDiscount()), driverWeb.findByXpath(deviceContentParent + "//*[@id='sidebar-resume']/div/div[1]/div/div[3]"));
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