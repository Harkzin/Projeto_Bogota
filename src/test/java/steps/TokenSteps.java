package steps;

import cucumber.api.java.it.Quando;
import pages.TokenPage;
import support.BaseSteps;

public class TokenSteps extends BaseSteps {

    public static String token;
    TokenPage tokenPage = new TokenPage(driver);

    @Quando("^preencho o campo Código enviado Por SMS com o TOKEN recebido$")
    public void preenchoOCampoCódigoEnviadoPorSMSComOTOKENRecebido() throws Throwable {
        token = driver.getAccessToken("https://api.cokecxf-commercec1-" + System.getProperty("env", "S1").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com");
        tokenPage.preencherToken();
    }
}
