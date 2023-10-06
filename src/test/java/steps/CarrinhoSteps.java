package steps;

import cucumber.api.java.pt.*;
import pages.*;
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

    @Então("^validar que foi direcionado para a Home$")
    public void validarQueFoiDirecionadoParaAHome() {
        carrinhoPage.validarQueFoiDirecionadoParaAHome();
    }
}

