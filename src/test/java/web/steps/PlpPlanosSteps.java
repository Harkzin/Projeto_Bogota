package web.steps;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import web.models.CartOrder;
import web.pages.PlpPlanosPage;

public class PlpPlanosSteps {

    private final PlpPlanosPage plpPlanosPage;
    private final CartOrder cart;

    @Autowired
    public PlpPlanosSteps(PlpPlanosPage plpPlanosPage, CartOrder cart) {
        this.plpPlanosPage = plpPlanosPage;
        this.cart = cart;
    }

    @Quando("selecionar o plano de id: {string}")
    public void selecionarPlano(String id) {
        cart.setPlan(id);
        plpPlanosPage.validarCardPlano(cart.getPlan(), cart.isDebitPaymentFlow);
        plpPlanosPage.selecionarPlano(id);
    }
    @Quando("o usuário clicar no botão [Eu quero!] do card do plano {string} na PLP Controle")
    public void selecionarPlanoControle(String id) {
        plpPlanosPage.selecionarPlano(id);
    }
    @Quando("o usuário clicar no botão [Eu quero!] do card do plano {string} na PLP POS")
    public void selecionarPlanoPos(String id) {
        plpPlanosPage.selecionarPlano(id);
    }
    @Entao("deve ser exibido um pop-up ofertando planos Controle Fácil no cartão de crédito")
    public void validarPopUpControleFacil() {
        plpPlanosPage.validarPopUpControleFacil();
    }
    @Então("clicar no botão [Eu quero!] do pop-up")
    public void clicarNoPopUpEuQuero() {
        plpPlanosPage.clicarNoPopUp();
    }
    @Quando("o usuário clicar no botão [Boleto] da pagina Costumizar Fatura")
    public void clicarNoBotãoBoleto() {
        plpPlanosPage.clicarNoBotãoBoleto();
    }
    @Quando("o usuário clicar no botão [Débito] da pagina Costumizar Fatura")
    public void clicarNoBotãoDebitoAlt() {
        plpPlanosPage.clicarNoBotãoDebitoAlt();
        cart.isDebitPaymentFlow = true;
    }
}

