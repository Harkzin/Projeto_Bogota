package steps;

import io.cucumber.java.pt.E;
import pages.TokenPage;
import support.BaseSteps;

import static support.RestAPI.getAccessToken;

public class TokenSteps extends BaseSteps {
    TokenPage tokenPage = new TokenPage(driver);

    public static String token;

    @E("^preencho o campo Código enviado Por SMS com o TOKEN recebido$")
    public void preenchoOCampoCódigoEnviadoPorSMSComOTOKENRecebido() throws Throwable {
        token = getAccessToken("https://api.cokecxf-commercec1-" + System.getProperty("env", "S6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com");
        tokenPage.preencherToken();
    }
}
