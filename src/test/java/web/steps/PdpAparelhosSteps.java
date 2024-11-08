package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PdpAparelhosPage;
import web.models.CartOrder;

import static web.support.utils.Constants.ChipType.ESIM;
import static web.support.utils.Constants.ChipType.SIM;
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
        pdpAparelhosPage.validarPdpAparelho(cart);
    }

    @Quando("o usuário selecionar a cor variante do modelo {string}")
    public void selecionoACordoAparelho(String id) {
        cart.setDeviceWithFocusPlan(id);
        pdpAparelhosPage.selecionarCorAparelho(cart.getDevice());
    }

    @E("será informado que não há estoque")
    public void deveMostrarProdutoEsgotado() {
        pdpAparelhosPage.validarProdutoSemEstoque();
    }

    @Quando("o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Troca de Plano + Aparelho")
    public void selecionarFluxoBaseTroca() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        cart.setProcessType(EXCHANGE);
        pdpAparelhosPage.selecionarFluxo(EXCHANGE);
    }

    @Quando("o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Migração de Plano + Aparelho")
    public void selecionarFluxoBaseMigra() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        cart.setProcessType(MIGRATE);
        pdpAparelhosPage.selecionarFluxo(MIGRATE);
    }

    @Quando("o usuário selecionar a opção [Manter meu número Claro], para o fluxo de Manter o Plano + Aparelho")
    public void selecionarFluxoBaseManterFid() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        cart.setProcessType(APARELHO_TROCA_APARELHO);
        pdpAparelhosPage.selecionarFluxo(APARELHO_TROCA_APARELHO);
    }

    @Entao("é exibido o popover para login")
    public void validaQueEExibidoOPopoverParaClientesClaro() {
        pdpAparelhosPage.validarPopoverLogin();
    }

    @E("preenche o campo [Seu numero Claro] com {string}")
    public void preencheOCampoSeuNumeroClaroCom(String msisdn) {
        cart.getUser().setClaroTelephone(msisdn);
        pdpAparelhosPage.preencheCampoSeuNumero(msisdn);
    }

    @Quando("clicar no botão [Acessar] do popover")
    public void clicaEmAcessar() {
        pdpAparelhosPage.clicaAcessarLogin();
    }

    @Entao("é exibido as opções e informações para cliente claro")
    public void deveExibirInformacoesClienteClaro() throws HttpStatusException {
        cart.populateCustomerProductDetails();
        pdpAparelhosPage.validarPdpAposLogin(cart.getUser().getClaroSubscription());
    }

    @E("seleciona [Mudar meu plano]")
    public void selecionarMudarMeuPlano() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        pdpAparelhosPage.selecionarMudarMeuPlano();
    }

    @E("seleciona [Manter meu plano com fidelidade]")
    public void selecionarManterPlanoFid() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("CBA");
        cart.updatePlanAndDevicePrice(cart.getUser().getClaroSubscription().getClaroPlan());
        pdpAparelhosPage.selecionarManterPlanoFid();
    }

    @E("seleciona [Manter meu plano sem fidelidade]")
    public void selecionarManterPlanoSemFid() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("PPV");
        cart.updatePlanAndDevicePrice(cart.getUser().getClaroSubscription().getClaroPlan());
        pdpAparelhosPage.selecionarManterPlanoSemFid();
    }

    @E("seleciona a opção [Trazer meu número para Claro]")
    public void selecionarFluxoPortabilidade() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("APV");
        cart.setProcessType(PORTABILITY);
        cart.updatePlanAndDevicePrice(focusPlan);
        pdpAparelhosPage.selecionarFluxo(PORTABILITY);
    }

    @E("seleciona a opção [Quero uma linha nova da Claro]")
    public void selecionarFluxoAquisicao() {
        cart.getEntry(cart.getDevice().getCode()).setBpo("APV");
        cart.setProcessType(ACQUISITION);
        cart.updatePlanAndDevicePrice(focusPlan);
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
        cart.updatePlanAndDevicePrice(plan);
        pdpAparelhosPage.selecionarPlano(cart);
    }

    @E("seleciona o chip [Comum]")
    public void selecionarChipComum() {
        cart.getClaroChip().setChipType(SIM);
        pdpAparelhosPage.selecionarSIM(false);
    }

    @E("seleciona o chip [eSIM]")
    public void selecionarEsim() {
        cart.getClaroChip().setChipType(ESIM);
        pdpAparelhosPage.selecionarSIM(true);
    }

    @Quando("o usuário clicar no botão [Comprar] da PDP do Aparelho")
    public void clicarComprar() {
        pdpAparelhosPage.clicarComprar(cart.getDevice().getCode());
    }
}