package steps;

import cucumber.api.PendingException;
import cucumber.api.java.pt.*;
import org.json.JSONException;
import org.junit.Assert;
import pages.APIPage;
import pages.CarrinhoPage;
import pages.PedidoTokenPage;
import support.BaseSteps;

public class CarrinhoSteps extends BaseSteps {

    CarrinhoPage carrinhoPage = new CarrinhoPage(driver);
    APIPage apiPage = new APIPage();
    PedidoTokenPage pedidoTokenPage = new PedidoTokenPage();

    @Dado("^validar que não há alterações no valor e/ou informações do Plano$")
    public void validarQueNãoHáAlteraçõesNoValorEOuInformaçõesDoPlano() throws Throwable {
        carrinhoPage.validarCarrinho();
    }

    @Dado("^selecionar a opção \"([^\"]*)\"$")
    public void selecionarAOpção(String opcao) throws Throwable {
        carrinhoPage.selecionarOpcaoForm(opcao);
    }

    @Dado("^preencho os campos ddd \"([^\"]*)\", telefone \"([^\"]*)\", email \"([^\"]*)\" e cpf \"([^\"]*)\" no fluxo de \"([^\"]*)\"$")
    public void preenchoOsCamposDddTelefoneEmailECpfNoFluxoDe(String ddd, String telefone, String email, String cpf, String fluxo) throws Throwable {
        carrinhoPage.preencherDadosLinhaForm(ddd, telefone, email, cpf, fluxo);
    }

    @Quando("^clicar no botão \"([^\"]*)\"$")
    public void clicarNoBotão(String botao) throws Throwable {
        carrinhoPage.euQueroCarrinho(botao);
    }
    @Então("^validar que foi exibida uma mensagem de erro \"([^\"]*)\"$")
    public void validarQueFoiExibidaUmaMensagemDeErro(String mensagem) throws Throwable {
        carrinhoPage.validarMensagemBloqueioClienteDependente(mensagem);
    }

    @Entao("^validar que é direcionado para pagina de \"([^\"]*)\"$")
    public void validarQueEDirecionadoParaPaginaDe(String Pagina) throws Throwable {
        switch (Pagina) {
            case "dados pessoais":
                carrinhoPage.paginaDadosPessoaisEExibida();
            break;
            case "dados de endereco":
                carrinhoPage.paginaDadosEnderecoEExibida();
            break;
            case "dados de pagamento":
                carrinhoPage.paginaDadosPagamentoEExibida();
            break;
            case "Controle antecipado":
                carrinhoPage.paginaControleAntecipadoEExibida();
            break;
            case "Customizar fatura":
                carrinhoPage.paginaCustomizarFaturaTHABEExibida();
            break;
            case "token":
                carrinhoPage.secaoTokenEExibida();
            break;
//            case "conclusao":
//                pedidoConcluidoPage.secaoPedidoConcluidoEExibida();
//                break;
        }
    }

    @E("^preencho os campos Nome Completo \"([^\"]*)\", Data De Nascimento \"([^\"]*)\" e Nome da Mãe \"([^\"]*)\"$")
    public void preenchoOsCamposNomeCompletoDataDeNascimentoENomeDaMae(String nomeCompleto, String dataNascimento, String nomeDaMae) throws Throwable {
        carrinhoPage.camposDadosPessoais(nomeCompleto, dataNascimento, nomeDaMae);
    }

    @E("^preencho os campos \"([^\"]*)\", \"([^\"]*)\" e \"([^\"]*)\" no endereço$")
    public void preenchoOsCamposENoEndereço(String cep, String numero, String complemento) throws Throwable {
        carrinhoPage.camposEndereco(cep, numero, complemento);
    }

    @Quando("^seleciono a forma de pagamento \"([^\"]*)\"$")
    public void selecionoAFormaDePagamento(String formaPagamento) throws Throwable {
        carrinhoPage.clicarFormaDePagamento(formaPagamento);
    }
    @E("^selecionar a data de vencimento \"([^\"]*)\"$")
    public void selecionarADataDeVencimento(String data) throws Throwable {
        carrinhoPage.selecionarDataVencimento(data);
    }

    @E("^marco o checkbox de termos de aceite$")
    public void marcoOCheckboxDeTermosDeAceite() {
        carrinhoPage.marcarCheckboxTermo();
    }

    @E("^marco o checkbox de termos de aceite thab$")
    public void marcoOCheckboxDeTermosDeAceiteThab() {
        carrinhoPage.marcarCheckboxTermoTHAB();
    }

    @Quando("^valido que foi ofertado plano de Controle Antecipado$")
    public void validoQueFoiOfertadoPlanoDe() {
        Assert.assertTrue(carrinhoPage.PlanoControleAntecipadoExiste());
    }
    @E("^selecionar a fatura \"([^\"]*)\"$")
    public void selecionarAFatura(String fatura) throws Throwable {
        carrinhoPage.selecionarTipoFatura(fatura);
    }

}
