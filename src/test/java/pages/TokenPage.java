package pages;

import support.BaseSteps;
import support.DriverQA;

import static steps.TokenSteps.token;

public class TokenPage extends BaseSteps {

    private DriverQA driver;

    public TokenPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String xpathInputToken = "//input[@data-automation='token']";
    public static String xpathBotaoFinalizarCarrinho = "//button[@data-automation='continuar']";

    public void preencherToken() {
        driver.waitElementAll(xpathInputToken, "xpath");
        driver.sendKeys(token, xpathInputToken, "xpath");
    }
}
