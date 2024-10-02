package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.utils.Constants;
import web.support.utils.DriverQA;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ScenarioScope
public class HomePage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public HomePage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private boolean isDebit = true;

    private void validarCardPlano(String code) {

        //TODO refactor
        //Puxa carrossel, caso seja mobile
        if (driverQA.getPlataformName().equals(Platform.ANDROID) || driverQA.getPlataformName().equals(Platform.IOS)) {
            WebElement nextCarrossel = driverQA.findElement("(//i[@class='mdn-Icon-direita mdn-Icon--lg'])[1]", "xpath");
            if (code.equals("17536")) {
                driverQA.javaScriptClick(nextCarrossel);
            } else if (code.equals("17558")) {
                driverQA.javaScriptClick(nextCarrossel);
                driverQA.javaScriptClick(nextCarrossel);
            }
        }

        //TODO atualizar find para id quando for criado
        WebElement cardParent = driverQA.findElement("//*[@id='addToCartForm" + code + "']/../preceding-sibling::div[contains(@class, 'top-card')]/div", "xpath");

        //Valida nome
        if (!cartOrder.getPlan().getName().isEmpty()) {
            WebElement planName = cardParent.findElement(By.xpath("h3"));
            ComumPage.validateElementText(cartOrder.getPlan().getName(), planName);
        }

        //Valida preço
        WebElement price = cardParent
                .findElement(By.xpath("div[@data-price-for]/div/div[@class='preco-home bestPrice']/div/p[2]"));
        Assert.assertEquals(cartOrder.getPlan().getFormattedPlanPrice(isDebit, true), price.getText().trim());
        Assert.assertTrue(price.isDisplayed());

        //Valida apps ilimitados
        if (cartOrder.getPlan().hasPlanApps()) {
            List<WebElement> planApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[@class='component-apps-ilimitados apps-ilimitados']//img"));
            ComumPage.validarMidiasPlano(cartOrder.getPlan().getPlanApps(), planApps, driverQA);
        }

        //Valida título extraPlay
        if (cartOrder.getPlan().hasExtraPlayTitle()) {
            WebElement extraPlayTitle = cardParent
                    .findElement(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')][1]/p"));
            ComumPage.validateElementText(cartOrder.getPlan().getExtraPlayTitle(), extraPlayTitle);
        }

        //Valida apps extraPlay
        if (cartOrder.getPlan().hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'component-apps-ilimitados extra-play')]//img"));

            ComumPage.validarMidiasPlano(cartOrder.getPlan().getExtraPlayApps(), extraPlayApps, driverQA);
    }

        //Valida planPortability (GB e bônus - antigo)
        if (cartOrder.getPlan().hasPlanPortability()) {
            List<WebElement> planPortability = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')]"))
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            ComumPage.validarPlanPortability(planPortability, cartOrder.getPlan());
        }
    }

    public void acessarLojaHome() {
        driverQA.getDriver().get(Constants.urlAmbiente);
        driverQA.waitPageLoad(Constants.urlAmbiente, 20);

        if (driverQA.getPlataformName().equals(Platform.ANDROID) || driverQA.getPlataformName().equals(Platform.IOS)) {
            WebElement btnFecharModal = driverQA.findElement("//*[@id='modal-onleave'][1]/div/a","xpath");
            driverQA.waitElementVisibility(btnFecharModal, 10);
            driverQA.javaScriptClick(btnFecharModal);
        }
    }

    public void validarHomePage() {
        driverQA.waitPageLoad(Constants.urlAmbiente, 20);
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        driverQA.actionSendKeys("txt-telefone", "id", msisdn);
    }

    public void acessarPdpPlano(String id) {
        validarCardPlano(id);
        driverQA.javaScriptClick("lnk-mais-detalhes-" + id, "id");
    }

    public void selecionarPlano(String id) {
        validarCardPlano(id);
        driverQA.javaScriptClick("btn-eu-quero-" + id, "id");
    }

    public void selecionarPlanoControle(String id) {
        isDebit = false;
        selecionarPlano(id);
    }

    public void clicaBotaoEntrar() {
        driverQA.javaScriptClick("btn-entrar", "id");
    }

    private void abrirMenuMobile(){
        if (driverQA.getPlataformName().equals(Platform.ANDROID) || driverQA.getPlataformName().equals(Platform.IOS)) {
            driverQA.javaScriptClick("//*[@id='navigation-menu']/preceding-sibling::button", "xpath");
        }
    }

    public void acessarMenuCelulares() {
        abrirMenuMobile();
        driverQA.javaScriptClick("//*[@id='tab-aparelhos']/a", "xpath");
    }
}