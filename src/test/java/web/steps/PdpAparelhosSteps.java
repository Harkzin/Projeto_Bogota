package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PdpAparelhosPage;
import web.models.CartOrder;

import static web.support.utils.Constants.ProcessType.*;
import static web.support.utils.Constants.focusPlan;

public class PdpAparelhosSteps  {

    private final PdpAparelhosPage pdpAparelhosPage;
    private final CartOrder cart;

    @Autowired
    public PdpAparelhosSteps(PdpAparelhosPage pdpAparelhosPage, CartOrder cart) {
        this.pdpAparelhosPage = pdpAparelhosPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a PDP do Aparelho selecionado")
    public void validarTelaPDPAparelho() {
        pdpAparelhosPage.validarPdpAparelho(cart.getDevice(), cart.getEntry(focusPlan));
    }

    @Quando("o usuário selecionar a cor variante do modelo {string}")
    public void selecionoACordoAparelho(String id) {
        cart.setDeviceWithFocusPlan(id);
        pdpAparelhosPage.selecionarCorAparelho(id);
    }

    @E("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoSemEstoque();
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Troca de Plano + Aparelho")
    public void selecionarFluxoBaseTroca() {
        cart.setProcessType(EXCHANGE);
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        pdpAparelhosPage.selecionarFluxo(EXCHANGE);
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho")
    public void selecionarFluxoBaseMigra() {
        cart.setProcessType(MIGRATE);
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        pdpAparelhosPage.selecionarFluxo(MIGRATE);
    }

    @E("seleciona a opção [Manter meu número Claro], para o fluxo de Manter o Plano com fidelidade + Aparelho")
    public void selecionarFluxoBaseManter() {
        cart.setProcessType(APARELHO_TROCA_APARELHO);
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        pdpAparelhosPage.selecionarFluxo(APARELHO_TROCA_APARELHO);
    }

    @E("seleciona a opção [Trazer meu número para Claro]")
    public void selecionarFluxoPortabilidade() {
        cart.setProcessType(PORTABILITY);
        cart.getEntry(cart.getDevice().getCode()).setBpo("APV");
        pdpAparelhosPage.selecionarFluxo(PORTABILITY);
    }

    @E("seleciona a opção [Quero uma linha nova da Claro]")
    public void selecionarFluxoAquisicao() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("APV");
        cart.setProcessType(ACQUISITION);
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
        cart.updatePlanForDevice(plan);
        pdpAparelhosPage.selecionarPlano(cart.getEntry(plan), cart.getDevice());
    }

    @E("seleciona o chip [Comum]")
    public void selecionarChipComum() {
        cart.setEsimChip(false);
        pdpAparelhosPage.selecionarSIM(false, cart.getDevice());
    }

    @E("seleciona o chip [eSIM]")
    public void selecionarEsim() {
        cart.setEsimChip(true);
        pdpAparelhosPage.selecionarSIM(true, cart.getDevice());
    }

    @Quando("o usuário clicar no botão [Comprar] da PDP do Aparelho")
    public void clicarComprar() {
        pdpAparelhosPage.clicarComprar(cart.getDevice().getCode());
    }
}