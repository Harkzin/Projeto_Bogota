package steps;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import pages.HomePage;
import support.BaseSteps;

public class HomeSteps extends BaseSteps {

    HomePage homePage = new HomePage(driver);

    @Dado("^que acesso a Loja Online$")
    public void queAcessoALojaOnline() throws Throwable {
        homePage.acessarLojaHome();
    }

    @E("^selecionar o \"([^\"]*)\" plano do carrossel da Home clicando no botão Eu quero! dele$")
    public void selecionarOPlanoDoCarrosselDaHomeClicandoNoBotãoDele(String cardHome) throws Throwable {
        homePage.selecionarCardControle(cardHome);
    }
}
