package steps;

import io.cucumber.java.pt.Então;
import org.springframework.beans.factory.annotation.Autowired;
import pages.ParabensPage;
import support.CartOrder;


public class ParabensSteps {

    private final ParabensPage parabensPage;
    private final CartOrder cartOrder;

    @Autowired
    public ParabensSteps(ParabensPage parabensPage, CartOrder cartOrder) {
        this.parabensPage = parabensPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a tela de Parabéns")
    public void validarPaginaParabens() {
        parabensPage.validarPaginaParabens();
    }

    @Então("o usuário clicar em [Ok, Entendi]")
    public void ClicarOkEntendi() {
        parabensPage.clicarOkEntendi();
    }

    @Então("os dados do pedido estão corretos")
    public void validarDadosPedido() {
        parabensPage.validarCamposPedido();
    }
}
