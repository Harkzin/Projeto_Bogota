package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PdpPlanosPage;
import web.models.CartOrder;

public class PdpPlanosSteps {

    private final PdpPlanosPage pdpPlanosPage;
    private final CartOrder cart;

    @Autowired
    public PdpPlanosSteps(PdpPlanosPage pdpPlanosPage, CartOrder cart) {
        this.pdpPlanosPage = pdpPlanosPage;
        this.cart = cart;
    }

    @Então("é direcionado para a PDP do plano")
    public void validarPDP() {
        pdpPlanosPage.validarPdpPlanos(cart.getPlan());
    }

    @Quando("o usuário selecionar a forma de pagamento [Débito] na PDP") //"na PDP" para diferenciar com o step da Customizar Fatura
    public void selecionarPagamentoDebito() {
        cart.isDebitPaymentFlow = true;
        pdpPlanosPage.selecionarDebito();
    }

    @Quando("o usuário selecionar a forma de pagamento [Boleto] na PDP") //"na PDP" para diferenciar com o step da Customizar Fatura
    public void selecionarPagamentoBoleto() {
        cart.isDebitPaymentFlow = false;
        pdpPlanosPage.selecionarBoleto();
    }

    @E("selecionar Fidelidade de 12 meses")
    public void selecionarFidelidade() {
        cart.hasLoyalty = true;
        pdpPlanosPage.selecionarFidelidade();
    }

    @Quando("o usuário selecionar Sem fidelidade")
    public void selecionarSemFidelidade() {
        cart.hasLoyalty = false;
        pdpPlanosPage.selecionarSemFidelidade();
    }

    @Então("o valor do plano é atualizado")
    public void validarValorPlano() {
        pdpPlanosPage.validarValorPlano(cart.getPlan(), cart.isDebitPaymentFlow, cart.hasLoyalty);
    }

    @Então("os aplicativos ilimitados são removidos da composição do plano")
    public void ocultaAppsIlimitados() {
        pdpPlanosPage.validarAppsIlimitadosPdp(cart.getPlan(), false);
    }

    @E("os aplicativos ilimitados são reexibidos na composição do plano")
    public void exibeAppsIlimitados() {
        pdpPlanosPage.validarAppsIlimitadosPdp(cart.getPlan(), true);
    }

    @Quando("o usuário clicar no botão [Eu quero!] da PDP")
    public void clicarEuQuero() {
        pdpPlanosPage.clicarEuQuero(cart.getPlan().getCode());
    }
}