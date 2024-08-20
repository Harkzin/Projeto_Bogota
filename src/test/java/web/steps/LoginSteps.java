package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.LoginPage;
import web.support.CartOrder;

public class LoginSteps {

    private final LoginPage loginPage;
    private final CartOrder cartOrder;

    @Autowired
    public LoginSteps(LoginPage loginPage, CartOrder cartOrder) {
        this.loginPage = loginPage;
        this.cartOrder = cartOrder;
    }

    @Entao("é direcionado para a tela de opções da área logada")
    public void eDirecionadoParaATelaDeLogin() {
        loginPage.validarPaginaLogin();
    }

    @Entao("é direcionado para a tela de opções minha conta")
    public void eDirecionadoParaATelaDeMinhaConta() {
        loginPage.validarPaginaMinhaConta();
    }

    @Então("será exibida a mensagem {string}")
    public void seraExibidaAMensagem(String mensagemClube) {
        loginPage.validarMensagemSaldoClaroClube(mensagemClube);
    }

    @Quando("o usuário clicar na opção [Acompanhar pedidos]")
    public void oUsuarioClicarNaOpcaoAcompanharPedidos() {
        loginPage.clicarAcompanharPedidos();
    }

    @Quando("o usuário clicar na opção [Claro clube]")
    public void oUsuarioClicarNaOpcaoClaroClube() {
        loginPage.clicarClaroClube();
    }

    @Entao("é direcionado para a tela de login com CPF")
    public void eDirecionadoParaATelaDeAcompanhamentoDoUsuario() {
        loginPage.validarPaginaLoginCpf();

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

    @E("selecionar a opção [Receber código por e-mail] no e-mail {string}")
    public void selecionaAOpcaoReceberCodigoPorEMail(String email) {
        cartOrder.essential.user.email = email;
        loginPage.selecionaReceberCodigoEmail();
    }

    @E("é direcionado para a tela de token por email")
    public void eDirecionadoParaATelaDeEMail() {
        loginPage.validarPaginaLoginEmail();
    }

    @E("preenche o campo [Digite o código recebido] com o token")
    public void preencherToken() {
        loginPage.inserirTokenEmail();
    }

    @Quando("o usuário clicar no botão [Confirmar]")
    public void oUsuarioClicarNoBotaoConfirmar() {
        loginPage.clicarBotaoConfirmar();
    }

    @Então("é direcionado para a tela Meus Pedidos")
    public void eDirecionadoParaATelaMeusPedidos() {
        loginPage.validarPaginaMeusPedidos();
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