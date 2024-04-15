package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.apiguardian.api.API;
import pages.LoginPage;
import support.BaseSteps;
import support.RestAPI;


public class LoginSteps extends BaseSteps {

    LoginPage loginPage = new LoginPage(driverQA);

    @Entao("é direcionado para a tela de opções da área logada")
    public void eDirecionadoParaATelaDeLogin() {
        loginPage.validarPaginaLogin();
    }

    @Quando("o usuário clicar na opção [Acompanhar pedidos]")
    public void oUsuarioClicarNaOpcaoAcompanharPedidos() {
        loginPage.clicarAcompanharPedidos();
    }

    @Entao("é direcionado para a tela de login com CPF")
    public void eDirecionadoParaATelaDeAcompanhamentoDoUsuario() {
        loginPage.validaTelaLoginCPF();

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
        loginPage.validaTelaToken();
    }

    @E("selecionar a opção [Receber código por e-mail]")
    public void selecionaAOpcaoReceberCodigoPorEMail() {
        loginPage.selecionaReceberCodigoEmail();
    }

    @E("é direcionado para a tela de token por email")
    public void eDirecionadoParaATelaDeEMail() {
        loginPage.validaTelaEmail();
    }

    @E("preenche o campo [Digite o código recebido] com o código recebido")
    public void preencheOCampoDigiteOCodigoRecebidoComOCodigoRecebido() {
        String token = RestAPI.buscarToken;
        loginPage.preencheCodigoToken(token);
    }

    @Então("é direcionado para a tela Meus Pedidos")
    public void eDirecionadoParaATelaMeusPedidos() {
        loginPage.validaTelaMeusPedidos();
    }

    @E("acessar o pedido mais recente, clicando no Número do pedido dele")
    public void acessarOPedidoMaisRecenteClicandoNoNumeroDoPedidoDele() {
        loginPage.acessarPedidoRecente();
    }

    @E("validar que foi direcionado para a página de Detalhes de pedido")
    public void validarQueFoiDirecionadoParaAPaginaDeDetalhesDePedido() {
//        loginPage.validaTelaDetalhesPedido();
    }
}
