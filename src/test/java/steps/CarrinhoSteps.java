package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.CarrinhoPage;
import support.BaseSteps;

import java.io.IOException;

public class CarrinhoSteps extends BaseSteps {
    CarrinhoPage carrinhoPage = new CarrinhoPage(driverQA);

    @Dado("que o usuário acesse a URL parametrizada de carrinho para a oferta de rentabilização {string}")
    public void acessarUrlCarrinho(String url) {
        carrinhoPage.acessarUrlRentabCarrinho(url);
        carrinhoPage.validarPaginaCarrinho();
    }

    @Entao("é direcionado para a tela de Carrinho")
    public void validarCarrinho() {
        carrinhoPage.validarPaginaCarrinho();
    }

    @E("seleciona o fluxo {string}") //Migração/Troca, Portabilidade, Aquisição
    public void selecionaFluxo(String fluxo) {
        carrinhoPage.selecionarFluxo(fluxo);
    }

    @E("preenche os campos: [Telefone com DDD] {string}, [E-mail] {string} e [CPF] {string}")
    public void preencherCamposCarrinhoBase(String telefone, String email, String cpf) {
        carrinhoPage.inserirDadosBase(telefone, cpf);
        carrinhoPage.inserirEmail(email);
    }

    @E("preenche os campos: [Telefone a ser portado com DDD] {string}, [E-mail] {string} e [CPF] [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoPortabilidade(String telefone, String email, String cpfAprovado, String cpfDiretrix) throws IOException, InterruptedException {
        carrinhoPage.inserirDadosPortabilidade(telefone, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        carrinhoPage.inserirEmail(email);
    }

    @E("preenche os campos: [Celular de contato] {string}, [E-mail] {string} e [CPF] [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoAquisicao(String telefoneContato, String email, String cpfAprovado, String cpfDiretrix) throws IOException, InterruptedException {
        carrinhoPage.inserirDadosAquisicao(telefoneContato, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        carrinhoPage.inserirEmail(email);
    }

    @Entao("será exibida a mensagem de erro: {string}")
    public void validaQueFoiExibidaUmaMensagemDeErro(String mensagem) {
        carrinhoPage.validarMensagemBloqueioClienteDependente(mensagem);
    }

    @Quando("o usuário clicar no botão [Eu quero!] do Carrinho")
    public void clicarEuQuero() {
        carrinhoPage.clicarEuQuero();
    }

    @Quando("seleciona o plano de controle antecipado ofertado")
    public void selecionaOPlanoDeControleAntecipadoOfertadoClicandoNoBotaoEuQueroDele() {
        carrinhoPage.clicarEuQueroTHAB();
    }

    @Entao("será exibida a mensagem de erro Bloqueio Dependente")
    public void eExibidaAMensagemDeErroBloqueioDependente() {
        carrinhoPage.validaMsgBloqueioDependente();
    }
}