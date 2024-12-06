package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.SmsPage;
import web.models.CartOrder;

public class SmsSteps {
    private final SmsPage smsPage;
    private final CartOrder cart;

    @Autowired
    public SmsSteps(SmsPage smsPage, CartOrder cart) {
        this.smsPage = smsPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a tela de SMS")
    public void validarPaginaSms() {
        smsPage.validarPaginaSms();
    }

    @E("preenche o campo [Código de verificação] com o token recebido")
    public void preencherToken() {
        smsPage.inserirToken();
    }

    @Quando("o usuário clicar no botão [Finalizar]/[Continuar] da tela de SMS")
    public void clicarFinalizar() {
        smsPage.clicarFinalizar();
    }
}