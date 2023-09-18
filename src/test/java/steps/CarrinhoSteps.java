package steps;

import cucumber.api.PendingException;
import cucumber.api.java.pt.*;
import pages.CarrinhoPage;
import support.BaseSteps;

public class CarrinhoSteps extends BaseSteps {

    CarrinhoPage carrinhoPage = new CarrinhoPage(driver);

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
//            case "token":
//                pedidoTokenPage.secaoTokenEExibida();
//                break;
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

    @Entao("^que sou redirecionado para a tela de \"([^\"]*)\"$")
    public void queSouRedirecionadoParaATelaDe(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Quando("^valido que foi ofertado plano de \"([^\"]*)\"$")
    public void validoQueFoiOfertadoPlanoDe(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Entao("^clico no botão \"([^\"]*)\"$")
    public void clicoNoBotão(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Quando("^preencho o campo \"([^\"]*)\" com o TOKEN recebido$")
    public void preenchoOCampoComOTOKENRecebido(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @E("^selecionar a fatura \"([^\"]*)\"$")
    public void selecionarAFatura(String fatura) throws Throwable {
        carrinhoPage.selecionarTipoFatura(fatura);
    }
}
