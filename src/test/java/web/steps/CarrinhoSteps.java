package web.steps;

import io.cucumber.java.pt.*;
import massasController.ConsultaCPFMSISDN;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.CarrinhoPage;
import web.models.CartOrder;

import java.util.AbstractMap.SimpleEntry;

import static massasController.ConsultaCPFMSISDN.consultarDadosBase;
import static web.support.utils.Constants.ProcessType.*;

public class CarrinhoSteps {

    private final CarrinhoPage carrinhoPage;
    private final CartOrder cart;

    @Autowired
    public CarrinhoSteps(CarrinhoPage carrinhoPage, CartOrder cart) {
        this.carrinhoPage = carrinhoPage;
        this.cart = cart;
    }

    @Dado("que o usuário acesse a URL parametrizada para a oferta de rentabilização {string}")
    public void acessarUrlCarrinho(String url) {
        carrinhoPage.acessarUrlRentabCarrinho(url);
        cart.setGuid(carrinhoPage.getCartGuid());
        cart.setRentabilizationCart(url);
        carrinhoPage.validarPaginaCarrinho();
    }

    @Entao("é direcionado para a tela de Carrinho")
    public void validarCarrinho() {
        cart.setGuid(carrinhoPage.getCartGuid());
        cart.updatePlanCartPromotion();
        carrinhoPage.validarPaginaCarrinho();
    }

    @E("seleciona a opção [Migração], para o fluxo de troca de Plano")
    public void selecionaTrocaPlano() {
        cart.setProcessType(EXCHANGE);
        carrinhoPage.selecionarFluxo(EXCHANGE);
    }

    @E("seleciona a opção [Migração], para o fluxo de troca de promoção")
    public void selecionaTrocaPromo() {
        cart.setProcessType(EXCHANGE_PROMO);
        carrinhoPage.selecionarFluxo(EXCHANGE_PROMO);
    }

    @E("seleciona a opção [Migração], para o fluxo de migração de plataforma")
    public void selecionaMigra() {
        cart.setProcessType(MIGRATE);
        carrinhoPage.selecionarFluxo(MIGRATE);
    }

    @E("seleciona a opção [Portabilidade]")
    public void selecionaPortabilidade() {
        cart.setProcessType(PORTABILITY);
        carrinhoPage.selecionarFluxo(PORTABILITY);
    }

    @E("seleciona a opção [Aquisição]")
    public void selecionaAquisicao() {
        cart.setProcessType(ACQUISITION);
        carrinhoPage.selecionarFluxo(ACQUISITION);
    }

    @E("preenche os campos: [Telefone com DDD] {string}, [E-mail] e [CPF] {string}")
    public void preencherCamposCarrinhoBase(String telefone, String cpf) {
        cart.getUser().setTelephone(telefone);
        cart.getUser().setClaroTelephone(telefone);
        cart.getUser().setCpf(cpf);
        carrinhoPage.inserirDadosBase(telefone, cpf);
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [Telefone com DDD] {string} {string} {string} comboMulti {string}, [E-mail] e [CPF] multaServico {string} multaAparelho {string} claroClube {string} crivo {string}")
    public void preencheOsCamposTelefoneComDDDEMailECPF(String segmento, String formaPagamento, String formaEnvio, String combo, String multaServico, String multaAparelho, String claroClube, String crivo) {
        SimpleEntry<String, String> dadosBase = consultarDadosBase(segmento, formaPagamento, formaEnvio, combo, multaServico, multaAparelho, claroClube, crivo);

        cart.getUser().setTelephone(dadosBase.getKey());
        cart.getUser().setClaroTelephone(dadosBase.getKey());
        cart.getUser().setCpf(dadosBase.getValue());
        carrinhoPage.inserirDadosBase(dadosBase.getKey(), dadosBase.getValue());
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [E-mail] e [CPF] {string}")
    public void preencherCamposCarrinhoBasePreAparelho(String cpf) {
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
        cart.getUser().setCpf(cpf);
        carrinhoPage.inserirDadosBase(cpf);
    }

    @E("preenche o campo [E-mail]")
    public void preencherEmailCarrinhoBaseAparelho() {
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [Telefone a ser portado com DDD] {string}, [E-mail] e [CPF] [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoPortabilidade(String telefone, String cpfAprovado, String cpfDiretrix) {
        cart.getUser().setTelephone(telefone);
        cart.getUser().setClaroTelephone(telefone);
        carrinhoPage.inserirDadosPortabilidade(telefone, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [Telefone a ser portado com DDD] Portabilidade, [E-mail] e [CPF] [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoPortabilidadeMassas(String cpfAprovado, String cpfDiretrix) {
        String telephone = ConsultaCPFMSISDN.consultarDadosPortabilidade();

        cart.getUser().setTelephone(telephone);
        cart.getUser().setClaroTelephone(telephone);
        carrinhoPage.inserirDadosPortabilidade(telephone, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix));
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [Telefone a ser portado com DDD] {string}, [E-mail] e [CPF] para Pix")
    public void preencherCamposCarrinhoPortabilidadePix(String telefone) {
        cart.getUser().setTelephone(telefone);
        cart.getUser().setClaroTelephone(telefone);
        cart.getUser().setCpf(carrinhoPage.inserirDadosPortabilidadePix(telefone));
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [Celular] {string}, [E-mail] e [CPF] para Pix")
    public void preencherCamposCarrinhoAquisicaoPix(String telefone) {
        cart.getUser().setTelephone(telefone);
        cart.getUser().setCpf(carrinhoPage.inserirDadosAquisicaoPix(telefone));
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [Celular] {string}, [E-mail] e [CPF] {string} reprovado no crivo")
    public void preencheCamposCarrinhoAquisicaoCpfReprovado(String telefone, String cpf) {
        cart.getUser().setTelephone(telefone);
        cart.getUser().setCpf(cpf);
        carrinhoPage.inserirDadosReprovacaoScore(telefone, cpf);
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @E("preenche os campos: [Celular de contato] {string}, [E-mail] e [CPF] [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}]")
    public void preencherCamposCarrinhoAquisicao(String telefoneContato, String cpfAprovado, String cpfDiretrix) {
        cart.getUser().setTelephone(telefoneContato);
        cart.getUser().setCpf(carrinhoPage.inserirDadosAquisicao(telefoneContato, Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix)));
        cart.getUser().setEmail(carrinhoPage.inserirEmail());
    }

    @Entao("será exibida a mensagem de erro: {string}")
    public void eExibidaAMensagemDeErro(String msgExibida) {
        carrinhoPage.validaMsgErro(msgExibida);
    }

    @Quando("o usuário clicar no botão [Eu quero!] do Carrinho")
    public void clicarEuQuero() {
        cart.updatePlanCartPromotion();
        carrinhoPage.clicarEuQuero();
    }

    @Quando("o usuário clicar no botão [Continuar] do Carrinho")
    public void clicarContinuar() {
        cart.updatePlanCartPromotion();
        carrinhoPage.clicarContinuar();
    }

    @Entao("será exibido o modal [Aviso Troca de Plano]")
    public void validarModalAvisoTrocaPlano() {
        carrinhoPage.validarModalAvisoTrocaPlano();
    }
    
    @Quando("o usuário clicar no botão [Confirmar] do modal [Aviso Troca de Plano]")
    public void clicarEmAvisoTrocaPlano() {
        carrinhoPage.clicarAvisoTrocaPlano();
    }

    @Entao("é direcionado para a tela de Carrinho de Acessórios")
    public void validarCarrinhoAcessorios() {
        carrinhoPage.validarPaginaCarrinhoAcessorios();
    }

    @E("preenche os campos: [Telefone com DDD] {string}, [CPF] {string} e [E-mail] {string} para acessórios")
    public void preencherCamposCarrinhoAcessorios(String telefone, String cpf, String email) {
        cart.setProcessType(ACCESSORY);
        cart.getUser().setTelephone(telefone);
        cart.getUser().setEmail(email);
        cart.getUser().setCpf(cpf);
        carrinhoPage.inserirDadosCarrinhoAcessorios(telefone, cpf, email);
    }

    @Quando("o usuário clicar no botão [Continuar] da tela de Carrinho de Acessórios")
    public void clicarContinuarAcessorios() {
        carrinhoPage.clicaBotaoContinuarAcessorios();
    }

    @Quando("clicar no botão [Continuar comprando]")
    public void clicarBotaoContinuarComprando() {
        carrinhoPage.clicaBotaoContinuarComprando();
    }
}