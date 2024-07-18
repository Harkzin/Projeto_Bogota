package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import pages.PdpAparelhosPage;
import support.CartOrder;

import static support.utils.Constants.ProcessType.*;

public class PdpAparelhosSteps  {

    private final PdpAparelhosPage pdpAparelhosPage;
    private final CartOrder cartOrder;

    @Autowired
    public PdpAparelhosSteps(PdpAparelhosPage pdpAparelhosPage, CartOrder cartOrder) {
        this.pdpAparelhosPage = pdpAparelhosPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a PDP do Aparelho selecionado")
    public void validarTelaPDPAparelho() {
        pdpAparelhosPage.validarPdpAparelho(cartOrder.getDevice());
    }

    @Quando("o usuário selecionar a cor variante do modelo {string}")
    public void selecionoACordoAparelho(String id) {
        cartOrder.setDevice(id, cartOrder.getProductTotalPrice(cartOrder.getDevice()));
        pdpAparelhosPage.selecionarCorAparelho(id);
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Troca de Plano + Aparelho")
    public void selecionarFluxoBaseTroca() {
        cartOrder.setProcessType(EXCHANGE);
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho")
    public void selecionarFluxoBaseMigra() {
        cartOrder.setProcessType(MIGRATE);
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Manter o Plano + Aparelho")
    public void selecionarFluxoBaseManter() {
        cartOrder.setProcessType(APARELHO_TROCA_APARELHO);
    }

    @E("seleciona a opção [Trazer meu número para Claro]")
    public void selecionarFluxoPortabilidade() {
        cartOrder.setProcessType(PORTABILITY);
    }

    @E("seleciona a opção [Quero uma linha nova da Claro]")
    public void selecionarFluxoAquisicao() {
        cartOrder.setProcessType(ACQUISITION);
    }

    @E("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoSemEstoque();
    }

    @Quando("o usuário clicar no botão [Comprar] da PDP do Aparelho")
    public void clicarComprar(){
        pdpAparelhosPage.clicarComprar(cartOrder.getDevice().getCode());
    }
}