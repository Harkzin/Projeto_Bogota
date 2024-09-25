package web.steps;

import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PlpPlanosPage;
import web.models.CartOrder;

public class PlpPlanosSteps {

    private final PlpPlanosPage plpPlanosPage;
    private final CartOrder cart;

    @Autowired
    public PlpPlanosSteps(PlpPlanosPage plpPlanosPage, CartOrder cart) {
        this.plpPlanosPage = plpPlanosPage;
        this.cart = cart;
    }

    @Quando("selecionar o plano de id: {string}")
    public void selecionarPlano(String id) {
        cart.setPlan(id);
        plpPlanosPage.validarCardPlano(cart.getPlan(), cart.isDebitPaymentFlow);
        plpPlanosPage.selecionarPlano(id);
    }
}
