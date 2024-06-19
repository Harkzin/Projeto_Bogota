package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PdpAparelhosPage;
import support.BaseSteps;

public class PdpAparelhosSteps extends BaseSteps {

    PdpAparelhosPage pdpAparelhosPage = new PdpAparelhosPage(driverQA);

    @Então("é direcionado para a PDP do Aparelho selecionado")
    public void validarTelaPDPAparelho() {
        pdpAparelhosPage.validarPaginaPDPAparelho();
    }

    @Quando("o usuário selecionar a cor variante do modelo {string}")
    public void selecionoACordoAparelho(String id) {
        pdpAparelhosPage.selecionarCorAparelho(id);
    }

    @E("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoSemEstoque();
    }
}