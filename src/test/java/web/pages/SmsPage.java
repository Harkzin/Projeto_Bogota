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

    private WebElement tokenField1;
    private WebElement tokenField2;
    private WebElement tokenField3;
    private WebElement tokenField4;


    public void validarPaginaSms() {
        driverWeb.waitPageLoad("/checkout/multi/summary/view", 15);

        tokenField1 = driverWeb.findElement("txt-token1", "id");
        tokenField2 = driverWeb.findElement("txt-token2", "id");
        tokenField3 = driverWeb.findElement("txt-token3", "id");
        tokenField4 = driverWeb.findElement("txt-token4", "id");

        Assert.assertEquals(tokenField1.getAttribute("value"), "");
        Assert.assertEquals(tokenField2.getAttribute("value"), "");
        Assert.assertEquals(tokenField3.getAttribute("value"), "");
        Assert.assertEquals(tokenField4.getAttribute("value"), "");

    }

    public void inserirToken() {
        String htmlComment = ((JavascriptExecutor) driverWeb.getDriver()).executeScript("return document.documentElement.lastChild").toString();
        int tokenStartIndex = htmlComment.indexOf("TOKEN_SEND_TO_CLIENT") + 25;

        driverWeb.sendKeys(tokenField1, htmlComment.substring(tokenStartIndex, tokenStartIndex + 4));
    }

    public void clicarFinalizar() {
        driverWeb.javaScriptClick("btn-confirmar", "id");
    }
}