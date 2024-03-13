package pages;

import support.BaseSteps;
import support.DriverQA;

import static steps.TokenSteps.token;

public class TokenPage extends BaseSteps {

    private final DriverQA driver;

    public TokenPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String xpathInputToken = "//input[@data-automation='token']";
    public static String xpathBotaoFinalizarCarrinho = "//button[@data-automation='continuar']";

    public void preencherToken() {
        driver.waitElement(xpathInputToken, "xpath");
        driver.sendKeys(xpathInputToken, "xpath", token);
    }
}