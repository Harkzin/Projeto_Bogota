package steps;

import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import pages.PLPPage;
import support.BaseSteps;
import support.Hooks;

public class PLPSteps extends BaseSteps {

    PLPPage plpPage = new PLPPage(driver);

    @E("^selecionar o \"([^\"]*)\" plano do carrossel da PLP clicando no botão Eu quero! dele$")
    public void selecionarOPlanoDoCarrosselDaPLPClicandoNoBotãoEuQueroDele(String cardPLP) throws Throwable {
        plpPage.selecionarCardControle(cardPLP);

    }
}
