package steps;

import io.cucumber.java.pt.*;
import org.springframework.beans.factory.annotation.Autowired;
import pages.CarrinhoPage;
import support.CartOrder;

import static support.utils.Constants.ProcessType.*;

public class CarrinhoSteps {

    private final CarrinhoPage carrinhoPage;
    private final CartOrder cartOrder;

    @Autowired
    public CarrinhoSteps(CarrinhoPage carrinhoPage, CartOrder cartOrder) {
        this.carrinhoPage = carrinhoPage;
        this.cartOrder = cartOrder;
    }

    @Dado("que o usuário acesse a URL parametrizada de carrinho para a oferta de rentabilização {string}")
    public void acessarUrlCarrinho(String url) {
        carrinhoPage.acessarUrlRentabCarrinho(url);
        carrinhoPage.validarPaginaCarrinho();
    }

    @Entao("é direcionado para a tela de Carrinho")
    public void validarCarrinho() {
        carrinhoPage.validarPaginaCarrinho();
    }

    @E("seleciona a opção [Migração], para o fluxo de troca de Plano")
    public void selecionaTrocaPlano() {
        carrinhoPage.selecionarFluxo(EXCHANGE);
    }

    @E("seleciona a opção [Migração], para o fluxo de troca de promoção")
    public void selecionaTrocaPromo() {
        carrinhoPage.selecionarFluxo(EXCHANGE_PROMO);
    }

    @E("seleciona a opção [Migração], para o fluxo de migração de plataforma")
    public void selecionaMigra() {
        carrinhoPage.selecionarFluxo(MIGRATE);
    }

    @E("seleciona a opção [Portabilidade]")
    public void selecionaPortabilidade() {
        carrinhoPage.selecionarFluxo(PORTABILITY);
    }

    @E("seleciona a opção [Aquisição]")
    public void selecionaAquisicao() {
        carrinhoPage.selecionarFluxo(ACQUISITION);
    }

    @E("preenche os campos: [Telefone com DDD] {string}, [E-mail] e [CPF] {string}")
    public void preencherCamposCarrinhoBase(String telefone, String cpf) {
        carrinhoPage.inserirDadosBase(telefone, cpf);
        carrinhoPage.inserirEmail();
    }

    @E("preenche os campos: [Telefone a ser portado com DDD] {string}, [E-mail] e [CPF] [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoPortabilidade(String telefone, String cpfAprovado, String cpfDiretrix) {
        carrinhoPage.inserirDadosPortabilidade(telefone, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        carrinhoPage.inserirEmail();
    }

    @E("preenche os campos: [Celular de contato] {string}, [E-mail] e [CPF] [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoAquisicao(String telefoneContato, String cpfAprovado, String cpfDiretrix) {
        carrinhoPage.inserirDadosAquisicao(telefoneContato, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        carrinhoPage.inserirEmail();
    }

    @Então("será exibida a mensagem de erro: {string}")
    public void eExibidaAMensagemDeErro(String msgExibida) {
        carrinhoPage.validaMsgErro(msgExibida);
    }

    @Quando("o usuário clicar no botão [Eu quero!] do Carrinho")
    public void clicarEuQuero() {
        carrinhoPage.clicarEuQuero();
    }

    @Quando("o usuário clicar no botão [Condordo] Aviso Troca de Plano")
    public void clicarEmAvisoTrocaPlano() {
        carrinhoPage.clicarAvisoTrocaPlano();
    }
}