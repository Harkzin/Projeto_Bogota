package steps;

import io.cucumber.java.pt.E;
import pages.PlpPlanosPage;
import support.CartOrder;

public class PlpPlanosSteps {

    private final PlpPlanosPage plpPlanosPage;
    private final CartOrder cartOrder;

    public PlpPlanosSteps(PlpPlanosPage plpPlanosPage, CartOrder cartOrder) { //Spring Autowired
        this.plpPlanosPage = plpPlanosPage;
        this.cartOrder = cartOrder;
    }

    @E("^selecionar o \"([^\"]*)\" plano do carrossel da PLP clicando no botão Eu quero! dele$")
    public void selecionarOPlanoDoCarrosselDaPLPClicandoNoBotãoEuQueroDele(String cardPLP) {
        plpPlanosPage.selecionarCardControle(cardPLP);

    }
}
