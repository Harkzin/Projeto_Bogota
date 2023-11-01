package pages;

import support.BaseSteps;
import support.DriverQA;

import static steps.TokenSteps.token;

public class TokenPage extends BaseSteps {

    private String xpathInputToken = "//input[@data-automation='token']";

    private DriverQA driver;

    public TokenPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public void preencherToken() {
        driver.sendKeys(token, xpathInputToken, "xpath");
    }
}
