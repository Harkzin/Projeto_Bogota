package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import pages.*;
import support.BaseSteps;

public class CarrinhoSteps extends BaseSteps {

    CarrinhoPage carrinhoPage = new CarrinhoPage(driver);

    @E("^validar que não há alterações no valor e/ou informações do Plano$")
    public void validarQueNãoHáAlteraçõesNoValorEOuInformaçõesDoPlano() throws Throwable {
        carrinhoPage.validarCarrinho();
    }

    @E("^preencho os campos ddd \"([^\"]*)\", telefone \"([^\"]*)\", email \"([^\"]*)\" e cpf \"([^\"]*)\"")
    public void preenchoOsCamposDddTelefoneEmailECpfNoFluxoDe(String ddd, String telefone, String email, String cpf) throws Throwable {
        carrinhoPage.preencherDadosLinhaForm(ddd, telefone, email, cpf);
    }

    @E("^clicar no botão \"([^\"]*)\"$")
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

    @E("^preencho os campos telefone \"([^\"]*)\", email \"([^\"]*)\" e cpf \"([^\"]*)\"$")
    public void preenchoOsCamposTelefoneEmailECpf(String telefone, String email, String cpf) throws Throwable {
        carrinhoPage.preencherDadosLinhaRent(telefone, email, cpf);
    }

    @E("^validar a mensagem de cliente combo multi$")
    public void validarAMensagemDeClienteComboMulti() {
        carrinhoPage.validarClienteCombo();
    }
}