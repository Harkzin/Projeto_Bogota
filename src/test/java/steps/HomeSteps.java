package steps;

import cucumber.api.java.pt.Dado;
import pages.HomePage;
import support.BaseSteps;

public class HomeSteps extends BaseSteps {

    HomePage homePage = new HomePage(driver);

    @Dado("^que acesso a Loja Online$")
    public void queAcessoALojaOnline() throws Throwable {
        homePage.acessarLojaHome();
    }

}
