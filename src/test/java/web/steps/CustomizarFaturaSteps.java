package web.steps;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Mas;
import io.cucumber.java.pt.Quando;
import web.models.CartOrder;
import web.pages.CustomizarFaturaPage;
import static web.support.utils.Constants.InvoiceType.DIGITAL;
import static web.support.utils.Constants.InvoiceType.PRINTED;
import static web.support.utils.Constants.InvoiceType.WHATSAPP;
import static web.support.utils.Constants.PaymentMode.DEBITCARD;
import static web.support.utils.Constants.PaymentMode.TICKET;

public class CustomizarFaturaSteps {

    private final CustomizarFaturaPage customizarFaturaPage;
    private final CartOrder cart;

    @Autowired
    public CustomizarFaturaSteps(CustomizarFaturaPage customizarFaturaPage, CartOrder cart) {
        this.customizarFaturaPage = customizarFaturaPage;
        this.cart = cart;
    }

    @Então("é direcionado para a tela de Customizar Fatura")
    public void validarPagiaCustomizarFatura() {
        customizarFaturaPage.validarPaginaCustomizarFatura();
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
        cart.isDebitPaymentFlow = customizarFaturaPage.validarNaoExibeMeiosPagamento(); //Valida e já atualiza o isDebitPaymentFlow
    }

    @E("deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada")
    public void exibeRecebimentoFatura() {
        customizarFaturaPage.validarTiposFatura(true, cart.isDebitPaymentFlow, cart.isThab());
    }

    @Mas("não deve ser exibido os meios de recebimento da fatura")
    public void naoExibeRecebimentoFatura() {
        customizarFaturaPage.validarTiposFatura(false, cart.isDebitPaymentFlow, cart.isThab());
    }

    @E("deve ser exibido as datas de vencimento")
    public void exibeDatasVencimento() {
        customizarFaturaPage.validarDatasVencimento(true, cart.isDebitPaymentFlow);
    }

    @E("não deve ser exibido as datas de vencimento")
    public void naoExibeDatasVencimento() {
        customizarFaturaPage.validarDatasVencimento(false, cart.isDebitPaymentFlow);
    }

    @Quando("o usuário selecionar a forma de pagamento [Débito]")
    public void selecionarPagamentoDebito() {
        cart.isDebitPaymentFlow = true;
        cart.updatePlanEntryPaymentMode(DEBITCARD);
        customizarFaturaPage.selecionarDebito();
    }

    @Quando("o usuário selecionar a forma de pagamento [Boleto]")
    public void selecionarPagamentoBoleto() {
        cart.isDebitPaymentFlow = false;
        cart.updatePlanEntryPaymentMode(TICKET);
        customizarFaturaPage.selecionarBoleto();
    }

    @E("preenche os dados bancários")
    public void preencherDadosBancarios() {
        customizarFaturaPage.preencherDadosBancarios();
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [WhatsApp]")
    public void selecionarFaturaWhatsApp() {
        cart.setSelectedInvoiceType(WHATSAPP);
        customizarFaturaPage.selecionarTipoFatura(WHATSAPP, cart.isDebitPaymentFlow);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [E-mail]")
    public void selecionarFaturaEmail() {
        cart.setSelectedInvoiceType(DIGITAL);
        customizarFaturaPage.selecionarTipoFatura(DIGITAL, cart.isDebitPaymentFlow);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [Correios]")
    public void selecionarFaturaCorreios() {
        cart.setSelectedInvoiceType(PRINTED);
        customizarFaturaPage.selecionarTipoFatura(PRINTED, cart.isDebitPaymentFlow);
    }

    @E("seleciona a data de vencimento {string}")
    public void selecionarDataDeVencimento(String data) {
        customizarFaturaPage.selecionarDataVencimento(data);
    }

    @E("marca o checkbox de termos de aceite")
    public void marcarTermosDeAceite() {
        customizarFaturaPage.aceitarTermos(cart.isDebitPaymentFlow);
    }

    @Quando("o usuário clicar no botão [Continuar] da tela de Customizar Fatura | Termos")
    public void clicarContinuar() {
        customizarFaturaPage.clicarContinuar();
    }

    @E("clicar no botão [Ok, entendi]")
    public void oUsuarioClicaNoBotaoOkEntendi() {
        customizarFaturaPage.clickOkEntendi();
    }

    @E("clicar no botão [Não concordo]")
    public void oUsuarioClicaNoBotaoNaoConcordo() {
        customizarFaturaPage.clickNaoConcordo();
    }

    @Então("é direcionado pra tela de Customizar Fatura, com alerta de multa")
    public void direcionadoPraTelaDeMulta() {
        customizarFaturaPage.validarPaginaMulta();
    }

    @Então("é direcionado para a tela de Customizar Fatura THAB")
    public void validarPagiaCustomizarFaturaTHAB() {
        customizarFaturaPage.validarPagiaCustomizarFaturaThab();
        cart.setThab();
    }

    @Então("é direcionado para a tela de Termos Combo")
    public void eDirecionadoParaATelaDeTermosCombo() {
        customizarFaturaPage.validarPaginaTermosCombo();
    }
    // @Então("é direcionado para a tela de Produto Controle Fácil")
    // public void eDirecionadoParaATelaDeProdutoControleFacil() {
    //     customizarFaturaPage.validarPaginaProdutoControleFacil();
    // } //Implementando

    @Quando("o usuário clicar no botão Continuar da tela de Cliente Combo")
    public void oUsuárioClicarNoBotaoContinuarDaTelaDeClienteCombo() {
        customizarFaturaPage.clicarContinuar();
    }

    @Quando("o usuário clicar no botão [Concordo] da tela de multa")
    public void clicarConcordo() {
        customizarFaturaPage.clicarConcordo();
    }
}