package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.PdpPlanosPage;
import support.BaseSteps;

import static pages.ComumPage.Cart_hasLoyalty;
import static pages.ComumPage.Cart_isDebitPaymentFlow;

public class PdpPlanosSteps extends BaseSteps {

    PdpPlanosPage pdpPlanosPage = new PdpPlanosPage(driverQA);

    @Então("é direcionado para a PDP do plano")
    public void validarPDP() {
        pdpPlanosPage.validarPDP();
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
        pdpPlanosPage.validarValorPlano(Cart_isDebitPaymentFlow, Cart_hasLoyalty);
    }

    @Então("os aplicativos ilimitados são removidos da composição do plano")
    public void ocultaAppsIlimitados() {
        pdpPlanosPage.validarAppIlimitados(false);
    }

    @E("os aplicativos ilimitados são reexibidos na composição do plano")
    public void exibeAppsIlimitados() {
        pdpPlanosPage.validarAppIlimitados(true);
    }

    @Quando("o usuário clicar no botão Eu quero! da PDP")
    public void clicarEuQuero() {
        pdpPlanosPage.clicarEuQuero();
    }
}