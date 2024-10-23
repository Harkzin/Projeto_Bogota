package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.product.PlanProduct;
import web.support.utils.DriverWeb;

import java.util.List;

import static org.junit.Assert.*;
import static web.pages.ComumPage.*;
import static web.support.utils.Constants.*;

@Component
@ScenarioScope
public class HomePage {

    private final DriverWeb driverWeb;

    @Autowired
    public HomePage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void acessarLojaHome() {
        driverWeb.getDriver().get(urlAmbiente);
        driverWeb.waitPageLoad(urlAmbiente, 20);

        if (driverWeb.isMobile()) {
            WebElement btnFecharModal = driverWeb.findElement("//*[@id='modal-onleave'][1]/div/a","xpath");
            driverWeb.waitElementVisible(btnFecharModal, 10);
            driverWeb.javaScriptClick(btnFecharModal);
        }
    }

    public void validarHomePage() {
        driverWeb.waitPageLoad(urlAmbiente, 20);
    }

    public void validarCardPlano(PlanProduct plan, boolean isDebit) {
        //TODO refactor
        //Puxa carrossel, caso seja mobile
        if (driverWeb.isMobile()) {
            WebElement nextCarrossel = driverWeb.findElement("(//i[@class='mdn-Icon-direita mdn-Icon--lg'])[1]", "xpath");
            if (plan.getCode().equals("17536")) {
                driverWeb.javaScriptClick(nextCarrossel);
            } else if (plan.getCode().equals("17558")) {
                driverWeb.javaScriptClick(nextCarrossel);
                driverWeb.javaScriptClick(nextCarrossel);
            }
        }

        //TODO Atualizar seletores quando forem criados
        String cardParent = String.format("//*[@id='addToCartForm%s']/../preceding-sibling::div[contains(@class, 'top-card')]/div", plan.getCode());

        //Valida nome
        assertNotNull(plan.getName());
        WebElement planName = driverWeb.findByXpath(cardParent + "/h3");
        validateElementText(plan.getName(), planName);

        //Valida preço
        WebElement price = driverWeb.findByXpath(cardParent + "/div[@data-price-for]/div/div[@class='preco-home bestPrice']/div");
        String priceText = String.format("R$ %s /mês", plan.getFormattedPrice(isDebit, true));
        validateElementText(priceText, price);

        //Valida apps ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = driverWeb.findElements(cardParent + "/div[@class='characteristics']/div[@class='component-apps-ilimitados apps-ilimitados']//img", "xpath");
            validatePlanMedias(plan.getPlanApps(), planApps, driverWeb);
        }

        //Valida título extraPlay
        if (plan.hasExtraPlayTitle()) {
            WebElement extraPlayTitle = driverWeb.findByXpath(cardParent + "/div[@class='characteristics']/div[contains(@class, 'title-extra-play')][1]/p");
            validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
        }

        //Valida apps extraPlay
        if (plan.hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = driverWeb.findElements(cardParent + "//*[@data-plan-content='extraplayapps']//img", "xpath");
            validatePlanMedias(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
        }

        //Valida passaporte(s)
        if (plan.hasPassport()) {
            List<WebElement> passports = driverWeb.findElements(cardParent + "//*[@data-plan-content='passport']", "xpath");
            validatePlanPassport(plan.getPassports(), passports, driverWeb);
        }

        //Valida planPortability (GB e bônus - antigo)
        if (plan.hasPlanPortability()) {
            List<WebElement> planPortability = driverWeb.findElements(cardParent + "/div[@class='characteristics']/div[contains(@class, 'title-extra-play')]", "xpath");
            validatePlanPortability(plan, planPortability);
        }
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        driverWeb.sendKeys("txt-telefone", "id", msisdn);
    }

    public void acessarPdpPlano(String id) {
        driverWeb.javaScriptClick("lnk-mais-detalhes-" + id, "id");
    }

    public void selecionarPlano(String id) {
        driverWeb.javaScriptClick("btn-eu-quero-" + id, "id");
    }

    public void clicaBotaoEntrar() {
        driverWeb.javaScriptClick("btn-entrar", "id");
    }

    public void acessarPlpAparelhos() {
        abrirMenuMobile();
        driverWeb.javaScriptClick("//*[@id='tab-aparelhos']/a", "xpath");
    }

    private void abrirMenuMobile() {
        if (driverWeb.isMobile()) {
            driverWeb.javaScriptClick("//*[@id='navigation-menu']/preceding-sibling::button", "xpath");
        }
    }

    public void acessarMenuAcessorios() {
        abrirMenuMobile();
        driverWeb.javaScriptClick("//*[@id='tab-acessorios']/a","xpath");
    }
}