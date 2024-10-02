package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.ParabensPage;
import web.models.CartOrder;


public class ParabensSteps {

    private final ParabensPage parabensPage;
    private final CartOrder cart;

    @Autowired
    public ParabensSteps(ParabensPage parabensPage, CartOrder cart) {
        this.parabensPage = parabensPage;
        this.cart = cart;
    }

    @Então("é direcionado para a tela de Parabéns")
    public void validarPaginaParabens() {
        parabensPage.validarPaginaParabens();
    }

    @E("o usuário clicar em [Ok, Entendi] no modal de token")
    public void ClicarOkEntendi() {
        parabensPage.clicarOkEntendi();
    }

    @Então("os dados do pedido estão corretos")
    public void validarDadosPedido() {
        parabensPage.validarCamposPedido();
    }
}
