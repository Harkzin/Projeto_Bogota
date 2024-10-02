package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.product.PlanProduct;
import web.support.utils.Constants;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.stream.Collectors;

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
        WebElement cardParent = driverWeb.findByXpath(String.format("//*[@id='addToCartForm%s']/../preceding-sibling::div[contains(@class, 'top-card')]/div", plan.getCode()));

        //Valida nome
        assertNotNull(plan.getName());
        WebElement planName = cardParent.findElement(By.xpath("h3"));
        validateElementText(plan.getName(), planName);

        //Valida preço
        WebElement price = cardParent
                .findElement(By.xpath("div[@data-price-for]/div/div[@class='preco-home bestPrice']/div/p[2]"));
        assertEquals(plan.getFormattedPrice(isDebit, true), price.getText().trim());
        assertTrue(price.isDisplayed());

        //Valida apps ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[@class='component-apps-ilimitados apps-ilimitados']//img"));
            validarMidiasPlano(plan.getPlanApps(), planApps, driverWeb);
        }

        //Valida título extraPlay
        if (plan.hasExtraPlayTitle()) {
            WebElement extraPlayTitle = cardParent
                    .findElement(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')][1]/p"));
            validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
        }

        //Valida apps extraPlay
        if (plan.hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'component-apps-ilimitados extra-play')]//img"));

            validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
        }

        //Valida planPortability (GB e bônus - antigo)
        if (plan.hasPlanPortability()) {
            List<WebElement> planPortability = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')]"))
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            validarPlanPortability(planPortability, plan);
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

    public void selecionarPlanoControle(String id) {
        selecionarPlano(id);
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

    public void acessarMenuAcessorios(){
        driverQA.javaScriptClick("//*[@id='tab-acessorios']/a","xpath");
    }
}