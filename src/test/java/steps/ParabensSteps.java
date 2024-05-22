package steps;

import io.cucumber.java.pt.Então;
import pages.ParabensPage;
import support.BaseSteps;

public class ParabensSteps extends BaseSteps {

    ParabensPage parabensPage = new ParabensPage(driverQA, cartOrder);

    @Então("é direcionado para a tela de Parabéns")
    public void validarPaginaParabens() {
        parabensPage.validarPaginaParabens();
    }

    @Então("os dados do pedido estão corretos")
    public void validarDadosPedido() {
        parabensPage.validarCamposPedido();
    }
}
