package steps;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PlpAparelhosPage;
import support.CartOrder;

public class PlpAparelhosSteps {

    private final PlpAparelhosPage plpAparelhosPage;
    private final CartOrder cartOrder;

    public PlpAparelhosSteps (PlpAparelhosPage plpAparelhosPage, CartOrder cartOrder) { //Spring Autowired
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