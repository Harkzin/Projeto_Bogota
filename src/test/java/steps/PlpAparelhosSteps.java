package steps;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import pages.PlpAparelhosPage;
import support.CartOrder;

public class PlpAparelhosSteps {

    private final PlpAparelhosPage plpAparelhosPage;
    private final CartOrder cartOrder;

    @Autowired
    public PlpAparelhosSteps (PlpAparelhosPage plpAparelhosPage, CartOrder cartOrder) {
        this.plpAparelhosPage = plpAparelhosPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a tela PLP de Aparelho")
    public void validarPLpAparelhos() {
        plpAparelhosPage.validarPlpAparelhos();
    }

    @Quando("o usuário clicar no botão [Eu quero!] do card do Aparelho {string}")
    public void clicarEuQuero(String id) {
        cartOrder.setDevice(id);
        plpAparelhosPage.validarCardAparelho(cartOrder.getDevice());
        plpAparelhosPage.clicaBotaoEuQuero(id);
    }
}