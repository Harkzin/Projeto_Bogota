package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.TokenPage;
import support.BaseSteps;

import static support.RestAPI.getAccessToken;

public class TokenSteps extends BaseSteps {
    TokenPage tokenPage = new TokenPage(driverQA);

    public static String token;

    @E("preenche o campo [Código enviado Por SMS] com o token recebido")
    public void preenchoOCampoCódigoEnviadoPorSMSComOTOKENRecebido() {
        token = getAccessToken("https://api.cokecxf-commercec1-" + System.getProperty("env", "S6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com");
        tokenPage.preencherToken();
    }

    @Entao("é direcionado para a tela de SMS")
    public void eDirecionadoParaATelaDeToken() {
        tokenPage.validarPaginaSMS();
    }

    @Quando("o usuário clicar no botão [Finalizar] da tela de SMS")
    public void oUsuarioClicarNoBotaoFinalizarDaTelaDeToken() {
        tokenPage.clicaBtnFinalizar();
    }
}
