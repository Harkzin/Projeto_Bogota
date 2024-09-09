package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PdpAparelhosPage;
import web.support.CartOrder;

import static web.support.utils.Constants.ProcessType.*;

public class PdpAparelhosSteps  {

    private final PdpAparelhosPage pdpAparelhosPage;
    private final CartOrder cartOrder;

    @Autowired
    public PdpAparelhosSteps(PdpAparelhosPage pdpAparelhosPage, CartOrder cartOrder) {
        this.pdpAparelhosPage = pdpAparelhosPage;
        this.cartOrder = cartOrder;
    }

    @Entao("é direcionado para a PDP do Aparelho selecionado")
    public void validarTelaPDPAparelho() {
        pdpAparelhosPage.validarPdpAparelho(cartOrder.getDevice());
    }

    @Quando("o usuário selecionar a cor variante do modelo {string}")
    public void selecionoACordoAparelho(String id) {
        cartOrder.setDevice(id, cartOrder.getEntryTotalPrice(cartOrder.getDevice()));
        pdpAparelhosPage.selecionarCorAparelho(id);
    }

    @E("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoSemEstoque();
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Troca de Plano + Aparelho")
    public void selecionarFluxoBaseTroca() {
        cartOrder.setProcessType(EXCHANGE);
        pdpAparelhosPage.selecionarFluxo(EXCHANGE);
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho")
    public void selecionarFluxoBaseMigra() {
        cartOrder.setProcessType(MIGRATE);
        pdpAparelhosPage.selecionarFluxo(MIGRATE);
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Manter o Plano + Aparelho")
    public void selecionarFluxoBaseManter() {
        cartOrder.setProcessType(APARELHO_TROCA_APARELHO);
        pdpAparelhosPage.selecionarFluxo(APARELHO_TROCA_APARELHO);
    }

    @E("seleciona a opção [Trazer meu número para Claro]")
    public void selecionarFluxoPortabilidade() {
        cartOrder.setProcessType(PORTABILITY);
        pdpAparelhosPage.selecionarFluxo(PORTABILITY);
    }

    @E("seleciona a opção [Quero uma linha nova da Claro]")
    public void selecionarFluxoAquisicao() {
        cartOrder.setProcessType(ACQUISITION);
        pdpAparelhosPage.selecionarFluxo(ACQUISITION);
    }

    @E("seleciona a plataforma [Claro Controle]")
    public void selecionarControle() {
        pdpAparelhosPage.selecionarPlataforma("controle");
    }

    @E("seleciona a plataforma [Claro Pré]")
    public void selecionarPre() {
        pdpAparelhosPage.selecionarPlataforma("prepago");
    }

    @E("seleciona a plataforma [Claro Pós]")
    public void selecionarPos() {
        pdpAparelhosPage.selecionarPlataforma("pospago");
    }

    @E("seleciona o plano {string}")
    public void selecionarPlano(String plan) {
        cartOrder.setPlan(plan);
        pdpAparelhosPage.selecionarPlano(plan);
    }

    @Quando("o usuário clicar no botão [Comprar] da PDP do Aparelho")
    public void clicarComprar(){
        pdpAparelhosPage.clicarComprar(cartOrder.getDevice().getCode());
    }
}