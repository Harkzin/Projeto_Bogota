package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PdpAparelhosPage;
import support.CartOrder;

public class PdpAparelhosSteps  {

    private final PdpAparelhosPage pdpAparelhosPage;
    private final CartOrder cartOrder;

    public PdpAparelhosSteps(PdpAparelhosPage pdpAparelhosPage, CartOrder cartOrder) { //Spring Autowired
        this.pdpAparelhosPage = pdpAparelhosPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a PDP do Aparelho selecionado")
    public void validarTelaPDPAparelho() {
        pdpAparelhosPage.validarPaginaPDPAparelho(cartOrder.getDevice().getCode());
    }

    @Quando("o usuário selecionar a cor variante do modelo {string}")
    public void selecionoACordoAparelho(String id) {
        cartOrder.setDevice(id);
        pdpAparelhosPage.selecionarCorAparelho(id);
    }

    @E("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoSemEstoque();
    }
}