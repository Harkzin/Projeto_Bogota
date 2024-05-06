package pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import support.DriverQA;

public class SmsPage {
    private final DriverQA driverQA;

    public SmsPage(DriverQA stepDriver) {
        driverQA = stepDriver;
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
        driverQA.JavaScriptClick("btn-finalizar", "id");
    }
}