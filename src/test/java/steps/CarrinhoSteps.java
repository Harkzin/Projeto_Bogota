package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.CarrinhoPage;
import support.BaseSteps;

import java.io.IOException;

public class CarrinhoSteps extends BaseSteps {
    CarrinhoPage carrinhoPage = new CarrinhoPage(driverQA);

    @Então("é direcionado para a tela de Carrinho")
    public void validarCarrinho() {
        carrinhoPage.validarPaginaCarrinho();
    }

    @E("seleciona o fluxo {string}")
    public void selecionaFluxo(String fluxo) {
        carrinhoPage.selecionaFluxo(fluxo);
    }

    @E("preenche os campos: Telefone com DDD {string}, E-mail {string} e CPF {string}")
    public void preencherCamposCarrinhoBase(String telefone, String email, String cpf) throws InterruptedException {
        carrinhoPage.inserirDadosBase(telefone, cpf);
        carrinhoPage.inserirEmail(email);
    }

    @E("preenche os campos: Telefone a ser portado com DDD {string}, E-mail {string} e CPF [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoPortabilidade(String telefone, String email, String cpfAprovado, String cpfDiretrix) throws IOException, InterruptedException {
        carrinhoPage.inserirDadosPortabilidade(telefone, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        carrinhoPage.inserirEmail(email);
    }

    @E("preenche os campos: Celular de contato {string}, E-mail {string} e CPF [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoAquisicao(String telefoneContato, String email, String cpfAprovado, String cpfDiretrix) throws IOException, InterruptedException {
        carrinhoPage.inserirDadosAquisicao(telefoneContato, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        carrinhoPage.inserirEmail(email);
    }



    @Então("será exibida a mensagem de erro: {string}")
    public void validaQueFoiExibidaUmaMensagemDeErro(String mensagem) {
        carrinhoPage.validarMensagemBloqueioClienteDependente(mensagem);
    }

    @Quando("o usuário clicar no botão Eu quero! do Carrinho")
    public void clicarEuQuero() {
        carrinhoPage.clicarEuQuero();
    }

    @Então("é direcionado pra tela de Customizar Fatua, com alerta de multa")
    public void direcionadoPraTelaDeMulta() { carrinhoPage.direcionadoParaMulta();}
}