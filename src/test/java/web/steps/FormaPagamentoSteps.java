package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.ComumPage;
import web.pages.FormaPagamentoPage;
import web.models.CartOrder;

import static web.support.utils.Constants.ProcessType.*;
import static web.support.utils.Constants.StandardPaymentMode.CREDITCARD;
import static web.support.utils.Constants.StandardPaymentMode.PIX;

public class FormaPagamentoSteps {

    private final FormaPagamentoPage formaPagamentoPage;
    private final CartOrder cart;

    @Autowired
    FormaPagamentoSteps(FormaPagamentoPage formaPagamentoPage, CartOrder cart) {
        this.cart = cart;
        this.formaPagamentoPage = formaPagamentoPage;
    }

    @Autowired
    private ComumPage comumPage;

    @Entao("será direcionado para a tela [Forma de Pagamento]")
    public void validarPaginaFormaPagamento() {
        formaPagamentoPage.validarPaginaFormaPagamento(cart);
    }

    @Quando("o usuário adicionar o cupom {string} e clicar no botão [Aplicar]")
    public void aplicarCupom(String cupom) {
        formaPagamentoPage.preencherCupom(cupom);
        formaPagamentoPage.clicarAplicarCupom();
        cart.addVoucherForDevice(cupom);
    }

    @Entao("o Aparelho receberá o desconto do cupom")
    public void validarDescontoCupom() {
        formaPagamentoPage.validarCupomAplicado(cart.getAppliedCouponCodes());
        comumPage.validarResumoCompraAparelho(cart);
    }

    @Quando("o usuário habilitar o desconto Claro Clube")
    public void usarClaroClube() {
        //Caso possua muita pontuação, o desconto máximo será o próprio valor do Aparelho (pagamento 100% Claro Clube)
        cart.getClaroClube().setUsed(true);
        cart.getClaroClube().setDiscountValue(Math.min(cart.getUser().getClaroClubBalance(), cart.getEntry(cart.getDevice().getCode()).getTotalPrice()));
        formaPagamentoPage.clicarUsarClaroClube();
    }

    @Entao("o Aparelho receberá o desconto da pontuação")
    public void validarDescontoClaroClube() {
        comumPage.validarResumoCompraAparelho(cart);
    }

    @Quando("o usuário clicar no botão [Adicionar cartão de crédito]")
    public void clicarAdicionarCartao() {
        formaPagamentoPage.clicarAdicionarCartao();
    }

    @Quando("o usuário clicar na aba [Pix]")
    public void clicarAbaPix() {
        formaPagamentoPage.clicarAbaPix();
    }

    @Entao("será exibido o iframe de pagamento do cartão")
    public void exibeIframe() {
        formaPagamentoPage.validarIframe();
    }

    @E("o usuário clicar no botão [Tentar Novamente] da tela de [Forma Pagamento]")
    public void preencherDadosCartao() {
        formaPagamentoPage.clicarTentarNovamente();
    }

    @E("preenche os dados do cartão: [Nome] {string}, [Número] {string}, [Data de validade] {string}, [CVV] {string} e [Parcelas] {string}")
    public void preencherDadosCartao(String name, String number, String date, String cvv, String installments) {
        formaPagamentoPage.preencherDadosCartao(name, number, date, cvv, installments);
    }

    @Quando("o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]")
    public void clicarConfirmarCartao() {
        if (cart.getProcessType() != ACCESSORY) {
            cart.getEntry(cart.getDevice().getCode()).setPaymentMode(CREDITCARD);
        }
        formaPagamentoPage.clicarConfirmarCartao();
    }

    @Quando("será exibido a mensagem de erro {string}")
    public void ValidarMensagemErroCartao(String msg) {
        formaPagamentoPage.ValidarMensagemErroCartao(msg);
    }

    @E("clicar no botão [Finalizar pedido com Pix] da tela [Forma de Pagamento]")
    public void clicarFinalizar() {
        cart.getEntry(cart.getDevice().getCode()).setPaymentMode(PIX);
        formaPagamentoPage.clicarFinalizarPix();
    }

    // Adicionado para a estoria ECCMAUT198
    @E("clicar no botão [Continuar] da tela [Forma de Pagamento]")
    public void clicarContinuar() {formaPagamentoPage.clicarContinuar();
    }

    @E("preenche o cartão com os dados: [Número] {string}, [Data de validade] {string} e [CVV] {string}")
    public void preencherDadosCartao(String numero, String validade, String cvv) {
        formaPagamentoPage.preencheCartaoCreditoControleFacil(numero, validade, cvv);
    }

    @E("o usuario clicar em [Finalizar] na tela de pagamento [Controle Fácil]")
    public void clicarFinalizarTelaControleFacil() {
        formaPagamentoPage.clicarFinalizarTelaControleFacil();
    }
}