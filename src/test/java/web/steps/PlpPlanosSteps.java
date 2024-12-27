package web.steps;

import io.cucumber.java.pt.Entao;
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

    @Entao("é direcionado para a PLP Controle")
    public void validarPlpControle() {
        plpPlanosPage.validarPlpControle();
    }

    @Quando("o usuário clicar no botão [Eu quero!] no card do plano {string} da PLP")
    public void selecionarPlano(String id) {
        cart.setPlan(id);
        plpPlanosPage.validarCardPlano(cart.getPlan());
        plpPlanosPage.selecionarPlano(id);
    }
}
