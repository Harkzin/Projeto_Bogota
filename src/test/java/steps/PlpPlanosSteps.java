package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import pages.PlpPlanosPage;
import support.CartOrder;

public class PlpPlanosSteps {

    private final PlpPlanosPage plpPlanosPage;
    private final CartOrder cartOrder;

    public PlpPlanosSteps(PlpPlanosPage plpPlanosPage, CartOrder cartOrder) { //Spring Autowired
        this.plpPlanosPage = plpPlanosPage;
        this.cartOrder = cartOrder;
    }

    @Quando("selecionar o plano de id: {string}")
    public void selecionarPlano(String id) {
        cartOrder.setPlan(id);
        plpPlanosPage.selecionarPlano(id);
    }
}
