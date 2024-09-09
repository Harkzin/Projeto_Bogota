package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.Product;
import web.support.utils.Constants;
import web.support.utils.DriverQA;

import java.util.List;
import java.util.stream.Collectors;

import static web.pages.ComumPage.*;

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

    private boolean isDebit;

    private void validarCardPlano(String code) {
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
    }

    public void validarHomePage() {
        driverQA.waitPageLoad(Constants.urlAmbiente, 20);
    }

    public void validarCardPlano(Product plan) {
        //TODO Atualizar seletores quando forem criados
        WebElement cardParent = driverQA.findElement("//*[@id='addToCartForm" + plan.getCode() + "']/../preceding-sibling::div[contains(@class, 'top-card')]/div", "xpath");

        //Valida nome
        if (!(plan.getName() == null)) {
            WebElement planName = cardParent.findElement(By.xpath("h3"));
            validateElementText(plan.getName(), planName);
        }

        //Valida preço
        WebElement price = cardParent
                .findElement(By.xpath("div[@data-price-for]/div/div[@class='preco-home bestPrice']/div/p[2]"));
        Assert.assertEquals(plan.getFormattedPlanPrice(true, true), price.getText().trim());
        Assert.assertTrue(price.isDisplayed());

        //Valida apps ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[@class='component-apps-ilimitados apps-ilimitados']//img"));
            validarMidiasPlano(plan.getPlanApps(), planApps, driverQA);
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
            validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverQA);
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
        driverQA.actionSendKeys("txt-telefone", "id", msisdn);
    }

    public void acessarPdpPlano(String id) {
        driverQA.javaScriptClick("lnk-mais-detalhes-" + id, "id");
    }

    public void selecionarPlano(String id) {
        driverQA.javaScriptClick("btn-eu-quero-" + id, "id");
    }

    public void selecionarPlanoControle(String id) {
        isDebit = false;
        cartOrder.isDebitPaymentFlow = false;

        selecionarPlano(id);
    }

    public void clicaBotaoEntrar() {
        driverQA.javaScriptClick("btn-entrar", "id");
    }

    public void acessarPlpAparelhos() {
        driverQA.javaScriptClick("//*[@id='tab-aparelhos']/a", "xpath");
    }
}