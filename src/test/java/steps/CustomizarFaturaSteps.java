package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Mas;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import pages.CustomizarFaturaPage;
import support.CartOrder;

import static support.utils.Constants.InvoiceType.*;
import static support.utils.Constants.PaymentMode.*;

public class CustomizarFaturaSteps {

    private final CustomizarFaturaPage customizarFaturaPage;
    private final CartOrder cartOrder;

    @Autowired
    public CustomizarFaturaSteps(CustomizarFaturaPage customizarFaturaPage, CartOrder cartOrder) {
        this.customizarFaturaPage = customizarFaturaPage;
        this.cartOrder = cartOrder;
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
        cartOrder.isDebitPaymentFlow = customizarFaturaPage.validarNaoExibeMeiosPagamento(); //Valida e já atualiza o isDebitPaymentFlow
    }

    @E("deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada")
    public void exibeRecebimentoFatura() {
        customizarFaturaPage.validarTiposFatura(true, cartOrder.isDebitPaymentFlow, cartOrder.isThab());
    }

    @Mas("não deve ser exibido os meios de recebimento da fatura")
    public void naoExibeRecebimentoFatura() {
        customizarFaturaPage.validarTiposFatura(false, cartOrder.isDebitPaymentFlow, cartOrder.isThab());
    }

    @E("deve ser exibido as datas de vencimento")
    public void exibeDatasVencimento() {
        customizarFaturaPage.validarDatasVencimento(true, cartOrder.isDebitPaymentFlow);
    }

    @E("não deve ser exibido as datas de vencimento")
    public void naoExibeDatasVencimento() {
        customizarFaturaPage.validarDatasVencimento(false, cartOrder.isDebitPaymentFlow);
    }

    @Quando("o usuário selecionar a forma de pagamento [Débito]")
    public void selecionarPagamentoDebito() {
        cartOrder.isDebitPaymentFlow = true;
        customizarFaturaPage.selecionarDebito();
    }

    @Quando("o usuário selecionar a forma de pagamento [Boleto]")
    public void selecionarPagamentoBoleto() {
        cartOrder.isDebitPaymentFlow = false;
        customizarFaturaPage.selecionarBoleto();
    }

    @E("preenche os dados bancários")
    public void preencherDadosBancarios() {
        customizarFaturaPage.preencherDadosBancarios();
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [WhatsApp]")
    public void selecionarFaturaWhatsApp() {
        customizarFaturaPage.selecionarTipoFatura(WHATSAPP, cartOrder.isDebitPaymentFlow);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [E-mail]")
    public void selecionarFaturaEmail() {
        customizarFaturaPage.selecionarTipoFatura(EMAIL, cartOrder.isDebitPaymentFlow);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [Correios]")
    public void selecionarFaturaCorreios() {
        customizarFaturaPage.selecionarTipoFatura(PRINTED, cartOrder.isDebitPaymentFlow);
    }

    @Então("o valor do Plano será atualizado no Resumo da compra") //Débito fatura impressa = sem desconto, preço igual de boleto.
    public void validarValorFaturaImpressa() {
        customizarFaturaPage.validarPrecoFaturaImpressaDebito(cartOrder.getPlan().getFormattedPlanPrice(false, true));
    }

    @E("seleciona a data de vencimento {string}")
    public void selecionarDataDeVencimento(String data) {
        customizarFaturaPage.selecionarDataVencimento(data);
    }

    @E("marca o checkbox de termos de aceite")
    public void marcarTermosDeAceite() {
        customizarFaturaPage.aceitarTermos(cartOrder.isDebitPaymentFlow);
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
        customizarFaturaPage.direcionadoParaMulta();
    }

    @Então("é direcionado para a tela de Customizar Fatura THAB")
    public void validarPagiaCustomizarFaturaTHAB() {
        customizarFaturaPage.validarPagiaCustomizarFaturaThab();
        cartOrder.setThab();
    }

    @Então("é direcionado para a tela de Termos Combo")
    public void eDirecionadoParaATelaDeTermosCombo() {
        customizarFaturaPage.validarPaginaTermosCombo();
    }

    @Quando("o usuário clicar no botão Continuar da tela de Cliente Combo")
    public void oUsuárioClicarNoBotaoContinuarDaTelaDeClienteCombo() {
        customizarFaturaPage.clicarContinuar();
    }
}