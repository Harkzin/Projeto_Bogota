package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.logging.log4j.util.TriConsumer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.models.product.DeviceProduct;
import web.models.product.PlanProduct;
import web.support.utils.Constants.ProcessType;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static web.models.CartOrder.PositionsAndPrices.*;
import static web.pages.ComumPage.*;
import static web.support.utils.Constants.ChipType.*;
import static web.support.utils.Constants.focusPlan;

@Component
@ScenarioScope
public class PdpAparelhosPage {

    private final DriverWeb driverWeb;

    @Autowired
    public PdpAparelhosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    @FindBy(id = "rdn-migracao")
    private WebElement fluxoBase;

    @FindBy(id = "rdn-portabilidade")
    private WebElement fluxoPortabilidade;

    @FindBy(id = "rdn-aquisicao")
    private WebElement fluxoAquisicao;

    @FindBy(id = "slc-plataforma-plano")
    private WebElement plataforma;

    @FindBy(id = "rdn-chip-type-SIM")
    private WebElement chipComum;

    @FindBy(id = "rdn-chip-type-ESIM")
    private WebElement chipEsim;

    @FindBy(id = "txt-telefone-login")
    private WebElement campoTelefoneLogin;

    @FindBy(id = "rdn-mudar-plano")
    private WebElement mudarMeuPlano;

    private boolean prePaidPlanSelected;

    private void validarInfosPlano(Entry planEntry) {
        PlanProduct plan = (PlanProduct) planEntry.getProduct();

        //Nome - Card
        assertNotNull("Nome do Plano nao configurado", plan.getName());
        WebElement nameCard = driverWeb.waitElementPresence(String.format("//*[@id='%s']/div/div/h3", plan.getCode()), 5);
        validateElementText(plan.getName(), nameCard);

        //PlanPortability - Card
        if (plan.hasPlanPortability()) {
            WebElement planPortability = driverWeb.findByXpath(String.format("//*[@id='%s']/div/div/p", plan.getCode()));
            validateElementText(String.join(" + ", plan.getPlanPortability()), planPortability);
        }

        //Preço - Card
        String formattedEntryTotalPrice = "R$ " + formatPrice(planEntry.getTotalPrice());
        WebElement priceCard = driverWeb.findByXpath(String.format("//*[@id='%s']/div/dl/dd", plan.getCode()));
        validateElementText(formattedEntryTotalPrice + " / mês", priceCard);

        //Modal <Mais detalhes> do plano
        if (!prePaidPlanSelected) {
            //Abre modal
            WebElement moreDetailsLink = driverWeb.findByXpath(String.format("//*[@id='lnk-mais-detalhes-%s']/a", plan.getCode()));
            driverWeb.javaScriptClick(moreDetailsLink);

            //Aguarda exibição do modal
            WebElement moreDetails = driverWeb.waitElementPresence(String.format("//*[@id='modal-more-details-%s']", plan.getCode()), 2);
            driverWeb.waitElementVisible(moreDetails, 2);

            //Valida nome - Modal
            WebElement name = moreDetails.findElement(By.xpath(".//h2"));
            validateElementText(plan.getName(), name);

            //Valida apps ilimitados - Modal
            if (plan.hasPlanApps()) {
                List<WebElement> planApps = moreDetails.findElements(By.xpath(".//div[contains(@class, ' apps-ilimitados')]//img"));
                validatePlanMedias(plan.getPlanApps(), planApps, driverWeb);
            }

            //Valida título extraPlay - Modal
            if (plan.hasExtraPlayTitle()) {
                WebElement extraPlayTitle = moreDetails.findElement(By.xpath(".//div[contains(@class, 'title-extra-play')][1]/p"));
                validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
            }

            //Valida apps extraPlay - Modal
            if (plan.hasExtraPlayApps()) {
                List<WebElement> extraPlayApps = moreDetails.findElements(By.xpath(".//div[contains(@data-plan-content, 'extraplayapps')]//img"));
                validatePlanMedias(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
            }

            //Valida planPortability (GB e bônus - antigo)  - Modal
            if (plan.hasPlanPortability()) {
                List<WebElement> planPortability = moreDetails.findElements(By.xpath(".//div[contains(@class, 'title-extra-play')]"));
                validatePlanPortability(plan, planPortability);
            }

            //Fecha modal
            driverWeb.javaScriptClick(moreDetails.findElement(By.xpath(".//button")));
            driverWeb.waitElementInvisible(moreDetails, 2);
        }

        //Seção [Plano Selecionado]
        WebElement name = driverWeb.findById("plano-selecionado");
        validateElementText(plan.getName(), name);

        //Seções [Valor do plano] e [Modalidade de Pagamento]
        WebElement price = driverWeb.findById("js-valor-plano");
        WebElement paymentMode = driverWeb.findById("js-modalidade-pagamento-debitcard");

        if (prePaidPlanSelected) {
            //[Valor do plano]
            validateElementText("Grátis", price);

            //[Modalidade de Pagamento]
            assertFalse(paymentMode.isDisplayed());
        } else {
            //[Valor do plano]
            validateElementText(formattedEntryTotalPrice, price);

            //[Modalidade de Pagamento]
            validateElementText("Boleto + fatura digital", paymentMode);
        }
    }

    private void validarPrecoCampanhaAparelho(DeviceProduct device, boolean eSimSelected) {
        //Preço base "De"
        if (!prePaidPlanSelected) {
            WebElement fullPrice = driverWeb.findById("value-total-aparelho-pdp");
            driverWeb.waitElementVisible(fullPrice, 10);
            validateElementText(device.getFormattedPrice(), fullPrice);
        }

        //Preço de campanha "por apenas"
        WebElement campaignPrice = driverWeb.findById("value-desconto-aparelho-pdp");
        validateElementText(device.getFormattedCampaignPrice(device.getSimType().equals("ESC") || eSimSelected), campaignPrice);

        //Parcelamento
        WebElement installments = driverWeb.findById("value-parcela-aparelho-pdp");

        String installmentPrice;
        if (eSimSelected) {
            installmentPrice = device.getFormattedInstallmentPrice(); //Parcelamento sem valor do chip incluído
        } else {
            installmentPrice = "R$ " + formatPrice(device.getCampaignPrice(false) / device.getInstallmentQuantity()); //Valor com chip comum incluído
        }

        String installmentsRef = String.format("ou %dx de %s s/ juros", device.getInstallmentQuantity(), installmentPrice);
        //TODO validateElementText(installmentsRef, installments); //Bug em Prod: parcelamento com eSIM (valor não muda) e fluxo de base (parcelamento não aparece)
    }

    public void validarPdpAparelho(CartOrder cart) {
        DeviceProduct device = cart.getDevice();
        Entry planEntry = cart.getEntry(focusPlan);

        driverWeb.waitPageLoad(device.getCode(), 10);
        driverWeb.actionPause(3000);

        PageFactory.initElements(driverWeb.getDriver(), this);

        WebElement deviceBrand;
        WebElement deviceName;
        if (driverWeb.isMobile()) {
            deviceBrand = driverWeb.findByXpath("//*[@class='d-md-none d-lg-none ']/p");
            deviceName = driverWeb.findByXpath("//*[@class='d-md-none d-lg-none ']/h2");
        } else {
            deviceBrand = driverWeb.findById("subtitle-marca-pdp");
            deviceName = driverWeb.findById("head-nome-aparelho-pdp");
        }

        //Fabricante
        assertNotNull("Texto Fabricante nao configurado", device.getBrand());
        validateElementText(device.getBrand(), deviceBrand);

        //Nome Aparelho
        assertNotNull("Texto Nome do produto nao configurado", device.getName());
        validateElementText(device.getName(), deviceName);

        //Cores
        List<WebElement> variantColors = driverWeb.findElements("//*[contains(@id, 'img-cor-do-produto')]", "xpath");

        IntStream.range(0, variantColors.size()).forEachOrdered(i -> {
            WebElement variantUrl = variantColors.get(i).findElement(By.tagName("a"));
            WebElement variantName = variantColors.get(i).findElement(By.tagName("p"));

            assertTrue("Cor variante com url do modelo incorreto", variantUrl.getAttribute("href").contains(device.getVariants().get(i).get(0)));
            assertEquals("Nome da cor variante diferente do configurado", device.getVariants().get(i).get(1).toLowerCase(), variantName.getText().toLowerCase());

            assertTrue("Imagem com url da cor variante nao exibida", variantUrl.isDisplayed());
            assertTrue("Nome da cor variante nao exibido", variantName.isDisplayed());
        });

        if (device.inStock()) {
            //Plano
            validarInfosPlano(planEntry);
            validarPrecoCampanhaAparelho(device, cart.getClaroChip().getChipType() == ESIM);
        }

        //Tipos Chip
        TriConsumer<WebElement, String, Boolean> validateSimType = (simType, text, isSelected) -> {
            driverWeb.javaScriptScrollTo(simType);
            assertSame(isSelected, simType.isSelected());

            validateElementText(text, chipComum.findElement(By.xpath("..")));
        };

        switch (device.getSimType()) {
            case "NSC" -> { //NSC = NanoSim apenas
                validateSimType.accept(chipComum, "Chip Comum", true);
                assertNull(chipEsim);
            }
            case "NSE" -> { //NSE = NanoSim + Esim
                validateSimType.accept(chipComum, "Chip Comum", true);
                validateSimType.accept(chipEsim, "eSIM", false);
            }
            case "ESC" -> { //ESC = Esim apenas
                validateSimType.accept(chipEsim, "eSIM", true);
                assertNull(chipComum);
            }
        }

        //Infos Técnicas
        if (device.hasDeviceFeatures()) {
            driverWeb.javaScriptClick("//*[@id='tab-info-tecnicas']/h2", "xpath");
            driverWeb.waitElementVisible(driverWeb.findById("especificationDevice"), 2);

            List<WebElement> features = driverWeb.findElements("//*[@id='especificationDevice']/div/div", "xpath");

            IntStream.range(0, features.size()).forEachOrdered(i -> {
                WebElement featureType = features.get(i).findElement(By.xpath("div[1]/span"));
                WebElement featureValue = features.get(i).findElement(By.xpath("div[2]/span"));

                validateElementText(device.getDeviceFeatures().get(i).get(0) + ":", featureType);
                validateElementText(device.getDeviceFeatures().get(i).get(1), featureValue);
            });
        }
    }

    public void selecionarCorAparelho(String id) {
        driverWeb.javaScriptClick(String.format("//a[contains(@href, '%s')]", id), "xpath");
    }

    public void validarProdutoSemEstoque() {
        assertEquals("Produto Esgotado", driverWeb.findById("produto-esgotado").getText().trim());
    }

    public void selecionarFluxo(ProcessType processType) {
        switch (processType) {
            case EXCHANGE, MIGRATE, APARELHO_TROCA_APARELHO -> driverWeb.javaScriptClick(fluxoBase);
            case PORTABILITY -> driverWeb.javaScriptClick(fluxoPortabilidade);
            case ACQUISITION -> driverWeb.javaScriptClick(fluxoAquisicao);
        }

        driverWeb.actionPause(3000);
    }

    public void validarPopoverLogin() {
        driverWeb.waitElementPresence("//div[@class='popover fade bottom in' and @role='tooltip']", 10);
        driverWeb.waitElementVisible(campoTelefoneLogin,20);
    }

    public void preencheCampoSeuNumero(String msisdn) {
        driverWeb.sendKeys(campoTelefoneLogin, msisdn);
    }

    public void clicaAcessarLogin() {
        driverWeb.javaScriptClick("btn-acessar", "id");
    }

    public void validarInformacoesExibidasAposLogin(){
        driverWeb.waitElementVisible(mudarMeuPlano.findElement(By.xpath("..")), 20);
    }

    public void selecionarPlataforma(String category) {
        if (category.equals("prepago")) {
            prePaidPlanSelected = true;
        }

        driverWeb.waitElementPresence("//*[@id='slc-plataforma-plano']/..", 10);
        Select platform = new Select(plataforma);
        platform.selectByValue(category);
    }

    public void selecionarPlano(CartOrder cart) {
        Entry planEntry = cart.getEntry(cart.getPlan().getCode());
        DeviceProduct device = cart.getDevice();
        
        String planCode = planEntry.getProduct().getCode();
        WebElement selectPlan = driverWeb.findById("btn-selecionar-plano-" + planCode);
        assertNotNull(String.format("Plano [%s] nao disponivel na PDP do Aparelho selecionado", planEntry.getProduct().getCode()), selectPlan);

        //Scroll para o carrossel do plano selecionado
        driverWeb.javaScriptScrollTo(driverWeb.findByXpath(String.format("//*[@data-product-code='%s']/..", planCode)));

        //Movimenta o carrossel caso o card não esteja visível
        while (!selectPlan.isDisplayed()) {
            String next = String.format("//*[@data-product-code='%s']/../..//em[@id='icon-dir']", planCode);
            driverWeb.javaScriptClick(driverWeb.findByXpath(next));
            driverWeb.actionPause(1000);
        }

        driverWeb.javaScriptClick(selectPlan);
        validarInfosPlano(planEntry);
        validarPrecoCampanhaAparelho(device, cart.getClaroChip().getChipType() == ESIM);
    }

    public void selecionarSIM(DeviceProduct device, boolean isEsim) {
        driverWeb.javaScriptClick(isEsim ? "rdn-chip-type-ESIM" : "rdn-chip-type-SIM", "id" );
        driverWeb.actionPause(500);
        validarPrecoCampanhaAparelho(device, isEsim);
    }

    public void clicarComprar(String deviceId) {
        driverWeb.javaScriptClick("btn-eu-quero-" + deviceId, "id");
    }
}