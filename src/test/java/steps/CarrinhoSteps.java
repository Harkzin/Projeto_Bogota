package steps;

import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Quando;
import cucumber.api.java.pt.Então;
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
    public void clicarNoBotão(String arg1) throws Throwable {
        carrinhoPage.euQueroCarrinho();
    }



    @Então("^validar que foi exibida uma mensagem de erro \"([^\"]*)\"$")
    public void validarQueFoiExibidaUmaMensagemDeErro(String mensagem) throws Throwable {
        carrinhoPage.validarMensagemBloqueioClienteDependente(mensagem);
    }


    @E("^clicar \"([^\"]*)\"$")
    public void clicarNaoConcordo(String arg0) throws Throwable {
        carrinhoPage.ClicarNaoConcordo ();

    }

   // @Quando("^clicar no botao “Ok, entendi”$")
    //public void clicarNoBotaoOkEntendi(String arg0) throws Throwable {
      //  carrinhoPage.clicarNoBotaoOkEntendi();
    }

}

