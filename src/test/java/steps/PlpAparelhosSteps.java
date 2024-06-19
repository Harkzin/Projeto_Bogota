package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PlpAparelhosPage;
import support.BaseSteps;

public class PlpAparelhosSteps extends BaseSteps {

    PlpAparelhosPage plpAparelhosPage = new PlpAparelhosPage(driverQA);

    @Entao("é direcionado para a tela para tela PLP de Aparelho")
    public void validoQueFoiDirecionadoParaAPDPDeAparelhos() {
        plpAparelhosPage.validarRedirecionamentoParaPdpAparelhos();
    }

    @Quando("o usuário clicar no botão [Eu quero!] do card do Aparelho {string}")
    public void clicarEuQuero(String id) {
        plpAparelhosPage.clicaBotaoEuQuero(id);
    }
}
