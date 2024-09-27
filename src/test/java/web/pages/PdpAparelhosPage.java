package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.product.DeviceProduct;
import web.models.product.PlanProduct;
import web.support.utils.Constants.ProcessType;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static web.pages.ComumPage.*;

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

    private boolean prePaidPlanSelected;

    private void validarInfosPlano(PlanProduct plan) {
        //Nome
        assertNotNull("Nome do Plano não configurado", plan.getName());
        WebElement nameCard = driverWeb.waitElementPresence(String.format("//*[@id='%s']/div/div/h3", plan.getCode()), 5);
        validateElementText(plan.getName(), nameCard);

        //PlanPortability
        if (plan.hasPlanPortability()) {
            WebElement planPortability = driverWeb.findByXpath(String.format("//*[@id='%s']/div/div/p", plan.getCode()));
            assertEquals(String.join(" + ", plan.getPlanPortability()), planPortability.getText().trim());
            assertTrue("PlanPortability não exibido no card", planPortability.isDisplayed());
        }

        //Preço
        WebElement priceCard = driverWeb.findByXpath(String.format("//*[@id='%s']/div/dl/dd", plan.getCode()));
        validateElementText("R$ " + plan.getFormattedPlanPrice(true, true) + " / mês", priceCard);

        //Mais detalhes
        if (!prePaidPlanSelected) {
            WebElement moreDetailsLink = driverWeb.findByXpath(String.format("//*[@id='lnk-mais-detalhes-%s']/a", plan.getCode()));
            driverWeb.javaScriptClick(moreDetailsLink);

            WebElement moreDetails = driverWeb.waitElementPresence(String.format("//*[@id='modal-more-details-%s']", plan.getCode()), 2);
            driverWeb.waitElementVisible(moreDetails, 2);

            WebElement name = moreDetails.findElement(By.xpath(".//h2"));
            validateElementText(plan.getName(), name);

            //Valida apps ilimitados
            if (plan.hasPlanApps()) {
                List<WebElement> planApps = moreDetails.findElements(By.xpath(".//div[contains(@class, ' apps-ilimitados')]//img"));
                validarMidiasPlano(plan.getPlanApps(), planApps, driverWeb);
            }

            //Valida título extraPlay
            if (plan.hasExtraPlayTitle()) {
                WebElement extraPlayTitle = moreDetails.findElement(By.xpath(".//div[contains(@class, 'title-extra-play')][1]/p"));
                validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
            }

            //Valida apps extraPlay
            if (plan.hasExtraPlayApps()) {
                List<WebElement> extraPlayApps = moreDetails.findElements(By.xpath(".//div[contains(@data-plan-content, 'extraplayapps')]//img"));
                validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
            }

            //Valida planPortability (GB e bônus - antigo)
            if (plan.hasPlanPortability()) {
                List<WebElement> planPortability = moreDetails
                        .findElements(By.xpath(".//div[contains(@class, 'title-extra-play')]"))
                        .stream()
                        .map(webElement -> webElement.findElement(By.tagName("p")))
                        .collect(Collectors.toList());

                validarPlanPortability(planPortability, plan);
            }

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
            validateElementText("R$ " + plan.getFormattedPlanPrice(true, true), price);

            //[Modalidade de Pagamento]
            validateElementText("Boleto + fatura digital", paymentMode);
        }
    }

    public void validarPdpAparelho(DeviceProduct device, PlanProduct plan) {
        driverWeb.waitPageLoad(device.getCode(), 10);
        driverWeb.actionPause(3000);

        PageFactory.initElements(driverWeb.getDriver(), this);

        //Fabricante
        assertNotNull("Texto Fabricante não configurado", device.getBrand());
        validateElementText(device.getBrand(), driverWeb.findById("subtitle-marca-pdp"));

        //Nome Aparelho
        assertNotNull("Texto Nome do produto não configurado", device.getName());
        validateElementText(device.getName(), driverWeb.findById("head-nome-aparelho-pdp"));

        //Cores
        List<WebElement> variantColors = driverWeb.findElements("//*[@id='txt-cor-do-produto']/following-sibling::div/div/div", "xpath");

        IntStream.range(0, variantColors.size()).forEachOrdered(i -> {
            WebElement variantUrl = variantColors.get(i).findElement(By.tagName("a"));
            WebElement variantName = variantColors.get(i).findElement(By.tagName("p"));

            assertTrue("Cor variante com url do modelo incorreto", variantUrl.getAttribute("href").contains(device.getVariants().get(i).get(0)));
            assertEquals("Nome da cor variante diferente do configurado", device.getVariants().get(i).get(1).toLowerCase(), variantName.getText().toLowerCase());

            assertTrue("Imagem com url da cor variante não exibida", variantUrl.isDisplayed());
            assertTrue("Nome da cor variante não exibido", variantName.isDisplayed());
        });

        if (device.inStock()) {
            //Plano
            validarInfosPlano(plan);

            //Preço base "De"
            if (!prePaidPlanSelected) {
                WebElement fullPrice = driverWeb.findById("value-total-aparelho-pdp");
                driverWeb.waitElementVisible(fullPrice, 10);
                validateElementText(device.getFormattedPrice(), fullPrice);
            }

            //Preço de campanha "por apenas"
            WebElement campaignPrice = driverWeb.findById("value-desconto-aparelho-pdp");
            //TODO Bug API ECCMAUT-806 validateElementText(device.getFormattedCampaignPrice(device.isEsimOnly()), campaignPrice);

            //Parcelamento
            WebElement installments = driverWeb.findById("value-parcela-aparelho-pdp");
            String installmetsStr = String.format("%dx de %s", device.getInstallmentQuantity(), StringUtils.normalizeSpace(device.getFormattedInstallmentPrice()));
            //TODO Bug API ECCMAUT-806 assertTrue("Quantidade de parcelas e valor diferente do configurado", StringUtils.normalizeSpace(installments.getText()).contains(installmetsStr));
            assertTrue("Parcelamento não exibido", installments.isDisplayed());
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
    }

    public void selecionarPlataforma(String category) {
        if (category.equals("prepago")) {
            prePaidPlanSelected = true;
        }

        Select platform = new Select(plataforma);
        platform.selectByValue(category);
    }

    public void selecionarPlano(PlanProduct plan) {
        driverWeb.javaScriptClick("btn-selecionar-plano-" + plan.getCode(), "id");
        validarInfosPlano(plan);
    }

    public void clicarComprar(String deviceId) {
        driverWeb.javaScriptClick("btn-eu-quero-" + deviceId, "id");
    }
}