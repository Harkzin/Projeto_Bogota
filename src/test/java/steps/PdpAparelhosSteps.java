package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PdpAparelhosPage;
import support.BaseSteps;

public class PdpAparelhosSteps extends BaseSteps {

    PdpAparelhosPage pdpAparelhosPage = new PdpAparelhosPage(driverQA);

    @Entao("é direcionado para a tela para tela PDP de Aparelho selecionado")
    public void validoQueFoiDirecionadoParaAPDPDeAparelhosSelecionado() {
        pdpAparelhosPage.validarRedirecionamentoProduto();
    }

    @Quando("o úsuario seleciono a cor [Azul Safira] do aparelho")
    public void selecionoACordoAparelho() {
        pdpAparelhosPage.selecionarCorAparelho();
    }

    @Entao("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoEstoque();
    }
}
