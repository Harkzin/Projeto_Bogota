package steps;

import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import pages.PlpPlanosPage;
import support.CartOrder;

public class PlpPlanosSteps {

    private final PlpPlanosPage plpPlanosPage;
    private final CartOrder cartOrder;

    @Autowired
    public PlpPlanosSteps(PlpPlanosPage plpPlanosPage, CartOrder cartOrder) {
        this.plpPlanosPage = plpPlanosPage;
        this.cartOrder = cartOrder;
    }

    @Quando("selecionar o plano de id: {string}")
    public void selecionarPlano(String id) {
        cartOrder.setPlan(id);
        plpPlanosPage.selecionarPlano(id);
    }
}