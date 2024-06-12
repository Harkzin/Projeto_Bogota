package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PdpPlanosPage;
import support.CartOrder;

public class PdpPlanosSteps {

    private final PdpPlanosPage pdpPlanosPage;
    private final CartOrder cartOrder;

    public PdpPlanosSteps(PdpPlanosPage pdpPlanosPage, CartOrder cartOrder) { //Spring Autowired
        this.pdpPlanosPage = pdpPlanosPage;
        this.cartOrder = cartOrder;
    }

    @Então("é direcionado para a PDP do plano")
    public void validarPDP() {
        pdpPlanosPage.validarPdpPlanos();
    }

    @Quando("o usuário selecionar a forma de pagamento [Débito]")
    public void selecionarPagamentoDebito() {
        pdpPlanosPage.selecionarDebito();
    }

    @Quando("o usuário selecionar a forma de pagamento [Boleto]")
    public void selecionarPagamentoBoleto() {
        pdpPlanosPage.selecionarBoleto();
    }

    @E("selecionar Fidelidade de 12 meses")
    public void selecionarFidelidade() {
        pdpPlanosPage.selecionarFidelidade();
    }

    @Quando("o usuário selecionar Sem fidelidade")
    public void selecionarSemFidelidade() {
        pdpPlanosPage.selecionarSemFidelidade();
    }

    @Então("o valor do plano é atualizado")
    public void validarValorPlano() {
        pdpPlanosPage.validarValorPlano(cartOrder.isDebitPaymentFlow);
    }

    @Então("os aplicativos ilimitados são removidos da composição do plano")
    public void ocultaAppsIlimitados() {
        pdpPlanosPage.validarAppsIlimitados(false);
    }

    @E("os aplicativos ilimitados são reexibidos na composição do plano")
    public void exibeAppsIlimitados() {
        pdpPlanosPage.validarAppsIlimitados(true);
    }

    @Quando("o usuário clicar no botão Eu quero! da PDP")
    public void clicarEuQuero() {
        pdpPlanosPage.clicarEuQuero();
    }
}