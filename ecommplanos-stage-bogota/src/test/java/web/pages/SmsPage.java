package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

import static org.junit.Assert.*;

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
    private WebElement resendTokenButton;

    public void validarPaginaSms() {
        driverWeb.waitPageLoad("/checkout/multi/summary/view", 60);

        resendTokenButton = driverWeb.findById("btn-renviar-codigo");
        tokenField1 = driverWeb.findById("txt-token1");
        tokenField2 = driverWeb.findById("txt-token2");
        tokenField3 = driverWeb.findById("txt-token3");
        tokenField4 = driverWeb.findById("txt-token4");

        assertEquals(tokenField1.getAttribute("value"), "");
        assertEquals(tokenField2.getAttribute("value"), "");
        assertEquals(tokenField3.getAttribute("value"), "");
        assertEquals(tokenField4.getAttribute("value"), "");
        assertTrue(resendTokenButton.isDisplayed());
        assertFalse(resendTokenButton.isEnabled());
    }

    public void inserirToken() {
        String htmlComment = ((JavascriptExecutor) driverWeb.getDriver()).executeScript("return document.documentElement.lastChild").toString();
        int tokenStartIndex = htmlComment.indexOf("TOKEN_SEND_TO_CLIENT") + 25;
        String token = htmlComment.substring(tokenStartIndex, tokenStartIndex + 4);

        driverWeb.sendKeys(tokenField1, String.valueOf(token.charAt(0)));
        driverWeb.sendKeys(tokenField2, String.valueOf(token.charAt(1)));
        driverWeb.sendKeys(tokenField3, String.valueOf(token.charAt(2)));
        driverWeb.sendKeys(tokenField4, String.valueOf(token.charAt(3)));
    }

    public void clicarFinalizar() {
        driverWeb.javaScriptClick("btn-confirmar", "id");
    }
}