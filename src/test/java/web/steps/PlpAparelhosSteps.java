package web.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PlpAparelhosPage;
import web.models.CartOrder;

import static web.support.utils.Constants.focusPlan;

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
        cart.setDevice(id);
        cart.setPlan(focusPlan); //Plano foco configurado via property no ambiente.
        plpAparelhosPage.validarCardAparelho(cart.getDevice(), cart.getPlan().getName());
        plpAparelhosPage.clicaBotaoEuQuero(id);
    }
}