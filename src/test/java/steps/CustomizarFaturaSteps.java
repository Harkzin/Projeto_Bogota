package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Mas;
import io.cucumber.java.pt.Quando;
import pages.CustomizarFaturaPage;
import support.BaseSteps;

import static support.Common.Cart_isThabFlow;

public class CustomizarFaturaSteps extends BaseSteps {
    CustomizarFaturaPage customizarFaturaPage = new CustomizarFaturaPage(driverQA, cartOrder);

    @Então("é direcionado para a tela de Customizar Fatura")
    public void validarPagiaCustomizarFatura() {
        customizarFaturaPage.validarPaginaCustomizarFatura();
    }

    @E("deve ser exibido as opções de pagamento")
    public void exibePagamento() {
        customizarFaturaPage.validarMeiosPagamento(true);
    }

    @Mas("não deve ser exibido as opções de pagamento")
    public void naoExibePagamento() {
        customizarFaturaPage.validarMeiosPagamento(false);
    }

    @E("deve ser exibido os meios de recebimento da fatura")
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

    @E("seleciona a forma de pagamento: {string}") //"Débito" ou "Boleto"
    public void selecionarPagamentoBoleto(String pagamento) {
        customizarFaturaPage.selecionarPagamento(pagamento);
    }

    @E("preenche os dados bancários")
    public void preencherDadosBancarios() {
        customizarFaturaPage.preencherDadosBancarios();
    }

    @E("seleciona o método de recebimento da fatura: {string}") //"WhatsApp", "E-mail" ou "Correios"
    public void selecionarRecebimentoFatura(String fatura) {
        customizarFaturaPage.selecionarTipoFatura(fatura);
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
        Cart_isThabFlow = true;
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