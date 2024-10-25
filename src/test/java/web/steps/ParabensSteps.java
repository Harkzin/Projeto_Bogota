package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
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

    @Entao("é direcionado para a tela de Parabéns")
    public void validarPaginaParabens() {
        parabensPage.validarPaginaParabens();
    }

    @Entao("é direcionado para a tela de Parabéns Pix")
    public void validarPaginaParabensPix() {
        parabensPage.validarPaginaParabensPix();
    }

    @E("clica no botão [Ok, Entendi] do modal de alerta de token")
    public void ClicarOkEntendi() {
        parabensPage.clicarOkEntendi();
    }

    @Entao("os dados do pedido estão corretos")
    public void validarDadosPedido() {
        parabensPage.validarCamposPedido();
    }
}
