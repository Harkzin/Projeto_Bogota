package pages;

import org.junit.Assert;
import support.BaseSteps;
import support.DriverQA;

import static steps.TokenSteps.token;

public class TokenPage extends BaseSteps {

    private final DriverQA driver;

    public TokenPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public static String inputToken = "txt-token";

    public void validarPaginaSMS() {
        driverQA.waitPageLoad("claro/pt/checkout/multi/summary/view", 10);

        Assert.assertNotNull(driverQA.findElement(inputToken, "id"));
    }

    public void preencherToken() {
        driver.sendKeys(inputToken, "id", token);
    }

    public void clicaBtnFinalizar() {
        driverQA.JavaScriptClick("btn-continuar", "id");
    }
}