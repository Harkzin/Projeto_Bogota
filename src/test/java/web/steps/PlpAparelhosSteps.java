package web.steps;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PlpAparelhosPage;
import web.support.CartOrder;

public class PlpAparelhosSteps {

    private final PlpAparelhosPage plpAparelhosPage;
    private final CartOrder cartOrder;

    @Autowired
    public PlpAparelhosSteps (PlpAparelhosPage plpAparelhosPage, CartOrder cartOrder) {
        this.plpAparelhosPage = plpAparelhosPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a tela PLP de Aparelho")
    public void validoQueFoiDirecionadoParaAPDPDeAparelhos() {
        plpAparelhosPage.validarPlpAparelhos();
    }

    @Quando("o usuário clicar no botão [Eu quero!] do card do Aparelho {string}")
    public void clicarEuQuero(String id) {
        cartOrder.setDevice(id);
        plpAparelhosPage.clicaBotaoEuQuero(id);
    }
}