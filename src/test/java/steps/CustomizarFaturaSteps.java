package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Mas;
import io.cucumber.java.pt.Quando;
import pages.CustomizarFaturaPage;
import support.CartOrder;

import static support.utils.Constants.InvoiceType.*;
import static support.utils.Constants.PlanPaymentMode.*;

public class CustomizarFaturaSteps {

    private final CustomizarFaturaPage customizarFaturaPage;
    private final CartOrder cartOrder;

    public CustomizarFaturaSteps(CustomizarFaturaPage customizarFaturaPage, CartOrder cartOrder) { //Spring Autowired
        this.customizarFaturaPage = customizarFaturaPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a tela de Customizar Fatura")
    public void validarPagiaCustomizarFatura() {
        customizarFaturaPage.validarPaginaCustomizarFatura();
    }

    @E("deve ser exibido as opções de pagamento, com a opção [Débito] selecionada")
    public void exibePagamento() {
        customizarFaturaPage.validarMeiosPagamento(DEBIT);
    }

    @E("deve ser exibido as opções de pagamento, com a opção [Boleto] selecionada")
    public void exibePagamentoBoleto() {
        customizarFaturaPage.validarMeiosPagamento(TICKET);
    }

    @Mas("não deve ser exibido as opções de pagamento")
    public void naoExibePagamento() {
        customizarFaturaPage.validarNaoExibeMeiosPagamento();
    }

    @E("deve ser exibido os meios de recebimento da fatura, com a opção [WhatsApp] selecionada")
    public void exibeRecebimentoFatura() {
        customizarFaturaPage.validarTiposFatura(true);
    }

    @Mas("não deve ser exibido os meios de recebimento da fatura")
    public void naoExibeRecebimentoFatura() {
        customizarFaturaPage.validarTiposFatura(false);
    }

    @E("deve ser exibido as datas de vencimento")
    public void exibeDatasVencimento() {
        customizarFaturaPage.validarDatasVencimento(true);
    }

    @Mas("não deve ser exibido as datas de vencimento")
    public void naoExibeDatasVencimento() {
        customizarFaturaPage.validarDatasVencimento(false);
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
        customizarFaturaPage.selecionarTipoFatura(WHATSAPP);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [E-mail]")
    public void selecionarFaturaEmail() {
        customizarFaturaPage.selecionarTipoFatura(EMAIL);
    }

    @Quando("o usuário selecionar o método de recebimento da fatura [Correios]")
    public void selecionarFaturaCorreios() {
        customizarFaturaPage.selecionarTipoFatura(PRINTED);
    }

    @Então("o valor do Plano será atualizado no Resumo da compra") //Débito fatura impressa = sem desconto, preço igual de boleto.
    public void validarValorFaturaImpressa() {
        customizarFaturaPage.validarPrecoFaturaImpressaDebito();
    }

    @E("seleciona a data de vencimento {string}")
    public void selecionarDataDeVencimento(String data) {
        customizarFaturaPage.selecionarDataVencimento(data);
    }

    @E("marca o checkbox de termos de aceite")
    public void marcarTermosDeAceite() {
        customizarFaturaPage.aceitarTermos();
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
        cartOrder.thab = true;
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