package pages;

import support.BaseSteps;
import support.DriverQA;

import static steps.TokenSteps.token;

public class TokenPage extends BaseSteps {

    private final DriverQA driver;

    public TokenPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String InputToken = "txt-token";
    public static String BotaoFinalizarCarrinho = "btn-continuar-token";

    public void preencherToken() {
        //driver.waitElementVisibility(InputToken, "id");
        driver.sendKeys(InputToken, "id", token);
    }
}