package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.SmsPage;
import web.support.CartOrder;

public class SmsSteps {
    private final SmsPage smsPage;
    private final CartOrder cartOrder;

    @Autowired
    public SmsSteps(SmsPage smsPage, CartOrder cartOrder) {
        this.smsPage = smsPage;
        this.cartOrder = cartOrder;
    }

    @Entao("é direcionado para a tela de SMS")
    public void validarPaginaSms() {
        smsPage.validarPaginaSms();
    }

    @E("preenche o campo [Código enviado Por SMS] com o token recebido")
    public void preencherToken() {
        smsPage.inserirToken();
    }

    @Quando("o usuário clicar no botão [Finalizar] da tela de SMS")
    public void clicarFinalizar() {
        smsPage.clicarFinalizar();
    }
}