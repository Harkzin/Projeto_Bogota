package web.steps;

import io.cucumber.java.pt.*;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.CustomizarFaturaPage;
import web.models.CartOrder;

import static web.support.utils.Constants.InvoiceType.*;
import static web.support.utils.Constants.StandardPaymentMode.*;

public class CustomizarFaturaSteps {

    private final CustomizarFaturaPage customizarFaturaPage;
    private final CartOrder cart;

    @Autowired
    public CustomizarFaturaSteps(CustomizarFaturaPage customizarFaturaPage, CartOrder cart) {
        this.customizarFaturaPage = customizarFaturaPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a tela de Customizar Fatura")
    public void validarPagiaCustomizarFatura() {
        customizarFaturaPage.validarPaginaCustomizarFatura();
    }

    @Entao("é direcionado para a tela de Termos")
    public void validarPagiaCustomizarFaturaTermos() {
        customizarFaturaPage.validarPaginaTermos();
    }

    @Entao("é direcionado para a tela de Termos Combo")
    public void eDirecionadoParaATelaDeTermosCombo() {
        customizarFaturaPage.validarPaginaTermosCombo();
    }

    @E("deve ser exibido as opções de pagamento, com a opção [Débito] selecionada")
    public void exibePagamento() {
        customizarFaturaPage.validarExibeMeiosPagamento(DEBITCARD);
    }

    @E("deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada")
    public void exibePagamentoBoleto() {
        customizarFaturaPage.validarExibeMeiosPagamento(TICKET);
    }

    @Mas("não deve ser exibido as opções de pagamento")
    public void naoExibePagamento() {
        cart.isDebitPaymentFlow = customizarFaturaPage.validarNaoExibeMeiosPagamento(cart.getProcessType()); //Valida e já atualiza o isDebitPaymentFlow
    }

    @E("deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada")
    public void exibeRecebimentoFatura() {
        customizarFaturaPage.validarExibeTiposFatura(cart.isDebitPaymentFlow, cart.isThab());
    }

    @Mas("não deve ser exibido os meios de recebimento da fatura")
    public void naoExibeRecebimentoFatura() {
        customizarFaturaPage.validarNaoExibeTiposFatura(cart.getProcessType());
    }

    @E("deve ser exibido as datas de vencimento")
    public void exibeDatasVencimento() {
        customizarFaturaPage.validarExibeDatas(cart.isDebitPaymentFlow);
    }

    @E("não deve ser exibido as datas de vencimento")
    public void naoExibeDatasVencimento() {
        customizarFaturaPage.validarNaoExibeDatas();
    }

    @Quando("o usuário selecionar a forma de pagamento [Débito]")
    public void selecionarPagamentoDebito() {
        cart.isDebitPaymentFlow = true;
        customizarFaturaPage.selecionarDebito();
        cart.updatePlanCartPromotion();
    }

    @Quando("o usuário selecionar a forma de pagamento [Boleto]")
    public void selecionarPagamentoBoleto() {
        cart.isDebitPaymentFlow = false;
        customizarFaturaPage.selecionarBoleto();
        cart.updatePlanCartPromotion();
    }

    @E("preenche os dados bancários")
    public void preencherDadosBancarios() {
        customizarFaturaPage.preencherDadosBancarios();
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [WhatsApp]")
    public void selecionarFaturaWhatsApp() {
        customizarFaturaPage.clearSessionInvoiceWhatsapp();
        customizarFaturaPage.selecionarTipoFatura(WHATSAPP, cart.isDebitPaymentFlow);
        cart.setSelectedInvoiceType(WHATSAPP);
    }

    @Entao("o usuário selecionar o método de recebimento da fatura [App Minha Claro]")
    public void selecionarFaturaAppMinhaClaro() {
        customizarFaturaPage.selecionarTipoFatura(APP, cart.isDebitPaymentFlow);
        cart.setSelectedInvoiceType(APP);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [E-mail]")
    public void selecionarFaturaEmail() {
        customizarFaturaPage.selecionarTipoFatura(DIGITAL, cart.isDebitPaymentFlow);
        cart.setSelectedInvoiceType(DIGITAL);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [Correios]")
    public void selecionarFaturaCorreios() {
        customizarFaturaPage.selecionarTipoFatura(PRINTED, cart.isDebitPaymentFlow);
        cart.setSelectedInvoiceType(PRINTED);
    }

    @E("seleciona a data de vencimento {string}")
    public void selecionarDataDeVencimento(String data) {
        customizarFaturaPage.selecionarDataVencimento(data);
    }

    @E("marca o checkbox de termos de aceite")
    public void marcarTermosDeAceite() {
        customizarFaturaPage.aceitarTermos(cart.isDebitPaymentFlow);
    }

    @Quando("o usuário clicar no botão [Continuar] da tela de Customizar Fatura - Termos")
    public void clicarContinuar() {
        customizarFaturaPage.clicarContinuar();
    }

    @Entao("é exibiba a mensagem de erro: {string}")
    public void validarMensagemDeErro(String mensagemExibida) {
        customizarFaturaPage.validarMensagemDeErro(mensagemExibida);
    }

    @E("clicar no botão [Ok, entendi]")
    public void oUsuarioClicaNoBotaoOkEntendi() {
        customizarFaturaPage.clickOkEntendi();
    }

    @E("clicar no botão [Não concordo]")
    public void oUsuarioClicaNoBotaoNaoConcordo() {
        customizarFaturaPage.clickNaoConcordo();
    }

    @Entao("é direcionado pra tela de Customizar Fatura, com alerta de multa")
    public void direcionadoPraTelaDeMulta() {
        customizarFaturaPage.validarPaginaMulta();
    }

    @Entao("é direcionado para a tela de Customizar Fatura THAB")
    public void validarPagiaCustomizarFaturaTHAB() {
        cart.setThab();
        customizarFaturaPage.validarPagiaCustomizarFaturaThab();
    }

    @Quando("o usuário clicar no botão Continuar da tela de Cliente Combo")
    public void oUsuarioClicarNoBotaoContinuarDaTelaDeClienteCombo() {
        customizarFaturaPage.clicarContinuar();
    }

    @Quando("o usuário clicar no botão [Concordo] da tela de multa")
    public void clicarConcordo() {
        customizarFaturaPage.clicarConcordo();
    }
}