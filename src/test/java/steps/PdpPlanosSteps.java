package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import pages.PdpPlanosPage;
import support.CartOrder;

public class PdpPlanosSteps {

    private final PdpPlanosPage pdpPlanosPage;
    private final CartOrder cartOrder;

    @Autowired
    public PdpPlanosSteps(PdpPlanosPage pdpPlanosPage, CartOrder cartOrder) {
        this.pdpPlanosPage = pdpPlanosPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a PDP do plano")
    public void validarPDP() {
        pdpPlanosPage.validarPdpPlanos();
    }

    @Quando("o usuário selecionar a forma de pagamento [Débito] na PDP") //"na PDP" para diferenciar com o step da Customizar Fatura
    public void selecionarPagamentoDebito() {
        cartOrder.isDebitPaymentFlow = true;
        pdpPlanosPage.selecionarDebito();
    }

    @Quando("o usuário selecionar a forma de pagamento [Boleto] na PDP") //"na PDP" para diferenciar com o step da Customizar Fatura
    public void selecionarPagamentoBoleto() {
        cartOrder.isDebitPaymentFlow = false;
        pdpPlanosPage.selecionarBoleto();
    }

    @E("selecionar Fidelidade de 12 meses")
    public void selecionarFidelidade() {
        cartOrder.hasLoyalty = true;
        pdpPlanosPage.selecionarFidelidade();
    }

    @Quando("o usuário selecionar Sem fidelidade")
    public void selecionarSemFidelidade() {
        cartOrder.hasLoyalty = false;
        pdpPlanosPage.selecionarSemFidelidade();
    }

    @Então("o valor do plano é atualizado")
    public void validarValorPlano() {
        pdpPlanosPage.validarValorPlano(cartOrder.isDebitPaymentFlow, cartOrder.hasLoyalty);
    }

    @Então("os aplicativos ilimitados são removidos da composição do plano")
    public void ocultaAppsIlimitados() {
        pdpPlanosPage.validarAppsIlimitados(false);
    }

    @E("os aplicativos ilimitados são reexibidos na composição do plano")
    public void exibeAppsIlimitados() {
        pdpPlanosPage.validarAppsIlimitados(true);
    }

    @Quando("o usuário clicar no botão [Eu quero!] da PDP")
    public void clicarEuQuero() {
        pdpPlanosPage.clicarEuQuero();
    }
}