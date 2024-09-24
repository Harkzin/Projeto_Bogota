package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

@Component
@ScenarioScope
public class SmsPage {

    private final DriverWeb driverWeb;

    @Autowired
    public SmsPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private WebElement token;

    public void validarPaginaSms() {
        driverWeb.waitPageLoad("/checkout/multi/summary/view", 15);

        token = driverWeb.findElement("txt-token", "id");
        Assert.assertEquals(token.getAttribute("value"), "");
    }

    public void inserirToken() {
        String htmlComment = ((JavascriptExecutor) driverWeb.getDriver()).executeScript("return document.documentElement.lastChild").toString();
        int tokenStartIndex = htmlComment.indexOf("TOKEN_SEND_TO_CLIENT") + 25;

        driverWeb.actionSendKeys(token, htmlComment.substring(tokenStartIndex, tokenStartIndex + 4));
    }

    public void clicarFinalizar() {
        driverWeb.javaScriptClick("btn-finalizar", "id");
    }
}