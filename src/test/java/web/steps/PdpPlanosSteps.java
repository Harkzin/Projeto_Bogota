package web.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PdpPlanosPage;
import web.models.CartOrder;
import web.support.utils.Constants;

import static web.support.utils.Constants.StandardPaymentMode.*;

public class PdpPlanosSteps {

    private final PdpPlanosPage pdpPlanosPage;
    private final CartOrder cart;

    @Autowired
    public PdpPlanosSteps(PdpPlanosPage pdpPlanosPage, CartOrder cart) {
        this.pdpPlanosPage = pdpPlanosPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a PDP do plano")
    public void validarPDP() {
        pdpPlanosPage.validarPdpPlanos(cart.getPlan());
    }

    @Quando("o usuário selecionar a forma de pagamento [Débito] na PDP") //"na PDP" para diferenciar com o step da Customizar Fatura
    public void selecionarPagamentoDebito() {
        cart.getEntry(cart.getPlan().getCode()).setPaymentMode(DEBITCARD);
        pdpPlanosPage.selecionarDebito();
    }

    @Quando("o usuário selecionar a forma de pagamento [Boleto] na PDP") //"na PDP" para diferenciar com o step da Customizar Fatura
    public void selecionarPagamentoBoleto() {
        cart.getEntry(cart.getPlan().getCode()).setPaymentMode(TICKET);
        pdpPlanosPage.selecionarBoleto();
    }

    @Entao("o valor do plano é atualizado")
    public void validarValorPlano() {
        pdpPlanosPage.validarValorPlano(cart.getPlan());
    }

    @Quando("o usuário clicar no botão [Eu quero!] da PDP")
    public void clicarEuQuero() {
        pdpPlanosPage.clicarEuQuero(cart.getPlan().getCode());
    }
}