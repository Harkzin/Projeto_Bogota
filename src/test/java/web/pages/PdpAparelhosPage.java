package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.models.Product;
import web.support.utils.Constants.ProcessType;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static web.pages.ComumPage.*;

@Component
@ScenarioScope
public class PdpAparelhosPage {

    private final DriverWeb driverWeb;
    private final CartOrder cartOrder;

    @Autowired
    public PdpAparelhosPage(DriverWeb driverWeb, CartOrder cartOrder) {
        this.driverWeb = driverWeb;
        this.cartOrder = cartOrder;
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

    private void validarInfosPlano(Product plan) {
        boolean hasName = !(plan.getName() == null);

        //Nome card
        if (hasName) {
            WebElement name = driverWeb.waitElementPresence("//*[@id='" + plan.getCode() + "']/div/div/h3", 5);
            validateElementText(plan.getName(), name);
        }

        //PlanPortability
        if (plan.hasPlanPortability()) {
            WebElement planPortability = driverWeb.findElement("//*[@id='" + plan.getCode() + "']/div/div/p", "xpath");
            Assert.assertEquals(String.join(" + ", plan.getPlanPortability()), planPortability.getText());
            Assert.assertTrue("PlanPortability exibido no card", planPortability.isDisplayed());
        }

        //Preço
        WebElement priceCard = driverWeb.findElement("//*[@id='" + plan.getCode() + "']/div/dl/dd", "xpath");
        validateElementText("R$ " + plan.getFormattedPlanPrice(true, true) + " / mês", priceCard);

        //Mais detalhes
        if (!prePaidPlanSelected) {
            WebElement moreDetails = driverWeb.findElement("//*[@id='lnk-mais-detalhes-" + plan.getCode() + "']/a", "xpath");
            driverWeb.javaScriptClick(moreDetails);

            WebElement modal = driverWeb.waitElementPresence("//*[@id='modal-more-details-" + plan.getCode() + "']", 2);
            driverWeb.waitElementVisible(modal, 2);

            if (hasName) {
                WebElement name = modal.findElement(By.xpath(".//h2"));
                validateElementText(plan.getName(), name);
            }

            //Valida apps ilimitados
            if (plan.hasPlanApps()) {
                List<WebElement> planApps = modal.findElements(By.xpath(".//div[contains(@class, ' apps-ilimitados')]//img"));
                validarMidiasPlano(plan.getPlanApps(), planApps, driverWeb);
            }

            //Valida título extraPlay
            if (plan.hasExtraPlayTitle()) {
                WebElement extraPlayTitle = modal.findElement(By.xpath(".//div[contains(@class, 'title-extra-play')][1]/p"));
                validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
            }

            //Valida apps extraPlay
            if (plan.hasExtraPlayApps()) {
                List<WebElement> extraPlayApps = modal.findElements(By.xpath(".//div[contains(@data-plan-content, 'extraplayapps')]//img"));
                validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
            }

            //Valida planPortability (GB e bônus - antigo)
            if (plan.hasPlanPortability()) {
                List<WebElement> planPortability = modal
                        .findElements(By.xpath(".//div[contains(@class, 'title-extra-play')]"))
                        .stream()
                        .map(webElement -> webElement.findElement(By.tagName("p")))
                        .collect(Collectors.toList());

                validarPlanPortability(planPortability, plan);
            }

            driverWeb.javaScriptClick(modal.findElement(By.xpath(".//button")));
            driverWeb.waitElementInvisible(modal, 2);
        }

        //Seção [Plano Selecionado]
        if (hasName) {
            WebElement name = driverWeb.findElement("plano-selecionado", "id");
            validateElementText(plan.getName(), name);
        }

        //Seções [Valor do plano] e [Modalidade de Pagamento]
        WebElement price = driverWeb.findElement("js-valor-plano", "id");
        WebElement paymentMode = driverWeb.findElement("js-modalidade-pagamento-debitcard", "id");

        if (prePaidPlanSelected) {
            //[Valor do plano]
            validateElementText("Grátis", price);

            //[Modalidade de Pagamento]
            Assert.assertFalse(paymentMode.isDisplayed());
        } else {
            //[Valor do plano]
            validateElementText("R$ " + plan.getFormattedPlanPrice(true, true), price);

            //[Modalidade de Pagamento]
            validateElementText("Débito em conta + fatura digital", paymentMode);
        }
    }

    public void validarPdpAparelho(Product device) {
        driverWeb.waitPageLoad(device.getCode(), 10);
        driverWeb.actionPause(1000);

        PageFactory.initElements(driverWeb.getDriver(), this);

        //Fabricante
        if (device.hasManufacturer()) {
            validateElementText(device.getBrand(), driverWeb.findElement("subtitle-marca-pdp", "id"));
        }

        //Nome Aparelho
        if (!(device.getName() == null)) {
            validateElementText(device.getName(), driverWeb.findElement("head-nome-aparelho-pdp", "id"));
        }

        //Cores
        List<WebElement> variantColors = driverWeb.findElements("//*[@id='txt-cor-do-produto']/following-sibling::div/div/div", "xpath");

        IntStream.range(0, variantColors.size()).forEachOrdered(i -> {
            WebElement variantUrl = variantColors.get(i).findElement(By.tagName("a"));
            WebElement variantName = variantColors.get(i).findElement(By.tagName("p"));

            Assert.assertTrue("Cor variante com url do modelo correto", variantUrl.getAttribute("href").contains(device.getVariants().get(i).get(0)));
            Assert.assertEquals("Nome da cor variante igual ao configurado", device.getVariants().get(i).get(1).toLowerCase(), variantName.getText().toLowerCase());

            Assert.assertTrue("Imagem com url da cor variante exibida", variantUrl.isDisplayed());
            Assert.assertTrue("Nome da cor variante exibido", variantName.isDisplayed());
        });

        //Plano
        if (device.inStock()) {
            String defaultPlan = driverWeb.findElement("//*[@id='addToCartForm" + device.getCode() + "']/input[@name='planForDevice']", "xpath").getAttribute("value");
            cartOrder.setPlan(defaultPlan);
            validarInfosPlano(cartOrder.getPlan());
        }

        //Preço base "De"
        if (device.inStock() && !prePaidPlanSelected) {
            WebElement fullPrice = driverWeb.findElement("value-total-aparelho-pdp", "id");
            driverWeb.waitElementVisible(fullPrice, 5);

            Assert.assertEquals("Valor sem desconto (De) igual ao configurado", fullPrice.getText(), device.getFormattedBaseDevicePrice());
            Assert.assertTrue("Valor sem desconto (De) é exibido", fullPrice.isDisplayed());
        }

        //Infos Técnicas
        if (device.hasDeviceFeatures()) {
            driverWeb.javaScriptClick("//*[@id='tab-info-tecnicas']/h2", "xpath");
            driverWeb.waitElementVisible(driverWeb.findElement("especificationDevice", "id"), 2);

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
        driverWeb.javaScriptClick("//a[contains(@href, '" + id + "')]", "xpath");
    }

    public void validarProdutoSemEstoque() {
        Assert.assertEquals("Produto Esgotado", driverWeb.findElement("produto-esgotado", "id").getText().trim());
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

    public void selecionarPlano(String plan) {
        driverWeb.javaScriptClick("btn-selecionar-plano-" + plan, "id");
        validarInfosPlano(cartOrder.getPlan());
    }

    public void clicarComprar(String deviceId) {
        driverWeb.javaScriptClick("btn-eu-quero-" + deviceId, "id");
    }
}