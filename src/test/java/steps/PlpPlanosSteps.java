package steps;

import io.cucumber.java.pt.E;;
import pages.PlpPlanosPage;
import support.BaseSteps;

public class PlpPlanosSteps extends BaseSteps {

    PlpPlanosPage plpPlanosPage = new PlpPlanosPage(driverQA);

    @E("^selecionar o \"([^\"]*)\" plano do carrossel da PLP clicando no botão Eu quero! dele$")
    public void selecionarOPlanoDoCarrosselDaPLPClicandoNoBotãoEuQueroDele(String cardPLP) throws Throwable {
        plpPlanosPage.selecionarCardControle(cardPLP);

    }
}
