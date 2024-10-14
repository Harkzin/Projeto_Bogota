package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.ComumPage;
import web.pages.FormaPagamentoPage;
import web.models.CartOrder;

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
        formaPagamentoPage.validarPaginaFormaPagamento();
    }

    @Entao("será direcionado para a tela [Forma de Pagamento] de acessórios")
    public void validarPaginaFormaPagamentoAcessorios() {
        formaPagamentoPage.validarPaginaFormaPagamentoAcessorios();
    }

    @Quando("o usuário adicionar o cupom {string} e clicar no botão [Aplicar]")
    public void aplicarCupom(String cupom) {
        formaPagamentoPage.preencherCupom(cupom);
        formaPagamentoPage.clicarAplicarCupom();
        cart.addVoucherForDevice(cupom);
    }

    @Entao("o Aparelho receberá o desconto do cupom")
    public void validarDescontoCupom() {
        formaPagamentoPage.validarAplicarCupom(cart.getAppliedCoupon());
        //TODO ECCMAUT-351 comumPage.validarResumoCompraAparelho(cart);
    }

    @Quando("o usuário clicar no botão [Adicionar cartão de crédito]")
    public void clicarAdicionarCartao() {
        formaPagamentoPage.clicarAdicionarCartao();
    }

    @Entao("será exibido o iframe de pagamento do cartão")
    public void exibeIframe() {
        formaPagamentoPage.validarIframe();
    }

    @E("preenche os dados do cartão: [Nome] {string}, [Número] {string}, [Data de validade] {string}, [CVV] {string} e [Parcelas] {string}")
    public void preencherDadosCartao(String name, String number, String date, String cvv, String installments) {
        formaPagamentoPage.preencherDadosCartao(name, number, date, cvv, installments);
    }

    @Quando("o usuário clicar no botão [Confirmar] do iframe do cartão da tela [Forma de Pagamento]")
    public void clicarConfirmarCartao() {
        formaPagamentoPage.clicarConfirmarCartao();
    }

    @Quando("o usuário clicar no botão [Finalizar pedido com Pix] da tela [Forma de Pagamento]")
    public void clicarFinalizar() {
        formaPagamentoPage.clicarFinalizarPix();
    }
}