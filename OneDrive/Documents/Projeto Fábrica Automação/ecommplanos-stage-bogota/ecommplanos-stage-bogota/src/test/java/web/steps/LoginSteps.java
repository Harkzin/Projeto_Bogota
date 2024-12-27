package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.LoginPage;
import web.models.CartOrder;

public class LoginSteps {

    private final LoginPage loginPage;
    private final CartOrder cart;

    @Autowired
    public LoginSteps(LoginPage loginPage, CartOrder cart) {
        this.loginPage = loginPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a tela de opções da área logada")
    public void eDirecionadoParaATelaDeLogin() {
        loginPage.validarPaginaLogin();
    }

    @Entao("é direcionado para a tela Minha Conta")
    public void validarPaginaMinhaConta() {
        loginPage.validarPaginaMinhaConta();
    }

    @Entao("será exibida a mensagem {string}")
    public void seraExibidaAMensagem(String mensagemClube) {
        loginPage.validarMensagemSaldoClaroClube(mensagemClube);
    }

    @Quando("o usuário clicar na opção [Acompanhar pedidos]")
    public void oUsuarioClicarNaOpcaoAcompanharPedidos() {
        loginPage.clicarAcompanharPedidos();
    }

    @Quando("o usuário clicar na opção [Acompanhar pedidos eSIM]")
    public void oUsuarioClicarNaOpcaoAcompanharPedidosEsim(){
        loginPage.clicarAcompanharPedidosEsim();
    }

    @Quando("o usuário clicar na opção [Claro clube]")
    public void oUsuarioClicarNaOpcaoClaroClube() {
        loginPage.clicarClaroClube();
    }

    @Entao("é direcionado para a tela de login com CPF")
    public void eDirecionadoParaATelaDeAcompanhamentoDoUsuario() {
        loginPage.validarPaginaLoginCpf();
    }

    @Entao("é direcionado para a tela de login eSIM com CPF")
    public void eDirecionadoParaATelaDeAcompanharPedido(){
        loginPage.validarPaginaLoginEsimCPF();
    }

    @E("preenche o campo [CPF] {string}")
    public void preencheOCampoCPFComOCPF(String cpf) {
        loginPage.preencheCPF(cpf);
    }

    @Quando("o usuário clicar no botão [Continuar]")
    public void oUsuarioClicarNaOpcaoContinuar() {
        loginPage.clicarBotaoContinuar();
    }

    @Entao("é direcionado para a tela de opções de token")
    public void eDirecionadoParaATelaDeToken() {
        loginPage.validarPaginaLoginToken();
    }

    @Quando("o usuário selecionar a opção [Receber código por e-mail] no e-mail {string}")
    public void selecionaAOpcaoReceberCodigoPorEMail(String email) {
        cart.getUser().setEmail(email);
        loginPage.selecionarCodigoEmail(email);
    }

    @E("é direcionado para a tela de token por email")
    public void eDirecionadoParaATelaDeEMail() {
        loginPage.validarPaginaLoginEmail();
    }

    @E("preenche o campo [Digite o código recebido] com o token")
    public void preencherToken() {
        loginPage.inserirTokenEmail(cart.getUser().getEmail());
    }

    @Quando("o usuário clicar no botão [Confirmar]")
    public void oUsuarioClicarNoBotaoConfirmar() {
        loginPage.clicarBotaoConfirmar();
    }

    @Entao("é direcionado para a tela Meus Pedidos")
    public void eDirecionadoParaATelaMeusPedidos() {
        loginPage.validarPaginaMeusPedidos();
    }

    @Entao("é direcionado para a tela Gerenciar eSIM")
    public void eDirecionadoParaATelaMinhaContaESim() {
        loginPage.validarPaginaGrenciarESim();
    }

    @Quando("o usuário clicar no botão [Ativar eSIM]")
    public void clicarGerenciarESim() {
        loginPage.clicarBotaoAtivarESim();
    }

    @Entao("é direcionado para a tela de Ativar eSIM")
    public void eDirecionadoParaATelaAtivarESim() {
        loginPage.validarPaginaAtivarESim();
    }

    @E("o usuário selecionar a opção [Gerenciar eSIM]")
    public void selecionarOpcaoGerenciarESim() {
        loginPage.clicarGerenciarESim();
    }

    @E("acessar o pedido mais recente, clicando no Número do pedido dele")
    public void acessarOPedidoMaisRecenteClicandoNoNumeroDoPedidoDele() {
        loginPage.acessarPedidoRecente();
    }

    @E("validar que foi direcionado para a página de Detalhes de pedido")
    public void validarQueFoiDirecionadoParaAPaginaDeDetalhesDePedido() {
        loginPage.validarPaginaDetalhesPedido();
    }
}