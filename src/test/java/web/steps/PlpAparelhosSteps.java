package web.steps;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import web.models.CartOrder;
import web.pages.PlpAparelhosPage;

public class PlpAparelhosSteps {

    private final PlpAparelhosPage plpAparelhosPage;
    private final CartOrder cart;

    @Autowired
    public PlpAparelhosSteps (PlpAparelhosPage plpAparelhosPage, CartOrder cart) {
        this.plpAparelhosPage = plpAparelhosPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a tela PLP de Aparelho")
    public void validarPLpAparelhos() {
        plpAparelhosPage.validarPlpAparelhos();
    }

    @Quando("o usuário clicar no botão [Eu quero!] do card do Aparelho {string}")
    public void clicarEuQuero(String id) {
        cart.isDebitPaymentFlow = false;
        cart.setDeviceWithFocusPlan(id);
        plpAparelhosPage.validarCardAparelho(cart.getDevice(), cart.getPlan().getName());
        plpAparelhosPage.clicaBotaoEuQuero(id);
    }
}