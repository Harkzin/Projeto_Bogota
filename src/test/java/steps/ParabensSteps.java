package steps;

import io.cucumber.java.pt.Então;
import pages.ParabensPage;
import support.CartOrder;


public class ParabensSteps {

    private final ParabensPage parabensPage;
    private final CartOrder cartOrder;

    public ParabensSteps(ParabensPage parabensPage, CartOrder cartOrder) { //Spring Autowired
        this.parabensPage = parabensPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a tela de Parabéns")
    public void validarPaginaParabens() {
        parabensPage.validarPaginaParabens();
    }

    @Então("os dados do pedido estão corretos")
    public void validarDadosPedido() {
        parabensPage.validarCamposPedido();
    }
}
