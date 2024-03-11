package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import pages.CarrinhoPage;
import support.BaseSteps;

import java.io.IOException;

public class CarrinhoSteps extends BaseSteps {

    CarrinhoPage carrinhoPage = new CarrinhoPage(driver);

    @E("validar que não há alterações no valor e nas informações do Plano")
    public void validarQueNaoHaAlteracoesNoValorEOuInformacoesDoPlano() {
        carrinhoPage.validarCarrinho();
    }

    @E("preencho os campos Telefone com DDD {string}, E-mail {string} e CPF {string}")
    public void preencherCamposCarrinhoBase(String telefone, String email, String cpf) {
        carrinhoPage.inserirDadosBase(telefone, email, cpf);
    }

    @E("preencho os campos Telefone a ser portado com DDD {string}, E-mail {string} e CPF [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoPortabilidade(String telefone, String email, boolean cpfAprovado, boolean cpfDiretrix) throws IOException, InterruptedException {
        carrinhoPage.inserirDadosPortabilidade(telefone, email, cpfAprovado, cpfDiretrix);
    }

    @E("preencho os campos Celular de contato {string}, E-mail {string} e CPF [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoAquisicao(String telefoneContato, String email, boolean cpfAprovado, boolean cpfDiretrix) throws IOException, InterruptedException {
        carrinhoPage.inserirDadosAquisicao(telefoneContato, email, cpfAprovado, cpfDiretrix);
    }

    @E("clicar no botão {string}")
    public void clicarNoBotao(String botao) {
        carrinhoPage.euQueroCarrinho(botao);
    }

    @Então("validar que foi exibida uma mensagem de erro {string}")
    public void validarQueFoiExibidaUmaMensagemDeErro(String mensagem) {
        carrinhoPage.validarMensagemBloqueioClienteDependente(mensagem);
    }

    @Então("validar que foi direcionado para a Home")
    public void validarQueFoiDirecionadoParaAHome() {
        carrinhoPage.validarQueFoiDirecionadoParaAHome();
    }

    @E("validar a mensagem de cliente combo multi")
    public void validarAMensagemDeClienteComboMulti() {
        //Refactor
        //carrinhoPage.validarClienteCombo();
    }
}