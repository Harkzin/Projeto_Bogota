package pages;

import support.BaseSteps;
import support.DriverQA;

import static steps.TokenSteps.token;

public class TokenPage extends BaseSteps {

    private DriverQA driver;

    public TokenPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String InputToken = "txt-token";
    public static String BotaoFinalizarCarrinho = "btn-continuar-token";

    public void preencherToken() {
        driver.waitElementAll(InputToken, "id");
        driver.sendKeys(token, InputToken, "id");
    }
}
