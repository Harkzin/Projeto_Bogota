package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverQA;

@Component
@ScenarioScope
public class SmsPage {

    private final DriverQA driverQA;

    @Autowired
    public SmsPage(DriverQA driverQA) {
        this.driverQA = driverQA;
    }

    private WebElement token;

    public void validarPaginaSms() {
        driverQA.waitPageLoad("/checkout/multi/summary/view", 15);

        token = driverQA.findElement("txt-token", "id");
        Assert.assertEquals(token.getAttribute("value"), "");
    }

    public void inserirToken() {
        String htmlComment = ((JavascriptExecutor) driverQA.getDriver()).executeScript("return document.documentElement.lastChild").toString();
        int tokenStartIndex = htmlComment.indexOf("TOKEN_SEND_TO_CLIENT") + 25;

        driverQA.actionSendKeys(token, htmlComment.substring(tokenStartIndex, tokenStartIndex + 4));
    }

    public void clicarFinalizar() {
        driverQA.javaScriptClick("btn-finalizar", "id");
    }
}