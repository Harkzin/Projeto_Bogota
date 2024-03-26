package steps;

import io.cucumber.java.pt.Entao;
import pages.LoginPage;
import support.BaseSteps;


public class LoginSteps extends BaseSteps {

    LoginPage loginPage = new LoginPage(driverQA);

    @Entao("é direcionado para a tela de Login")
    public void eDirecionadoParaATelaDeLogin() {
    loginPage.validarPaginaLogin();
    }
}
