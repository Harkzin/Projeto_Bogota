package web.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.HomePage;
import web.models.CartOrder;

public class HomeSteps {

    private final HomePage homePage;
    private final CartOrder cart;

    @Autowired
    public HomeSteps(HomePage homePage, CartOrder cart) {
        this.homePage = homePage;
        this.cart = cart;
    }

    @Dado("que o usuário acesse a Loja Online")
    public void AcessarLojaOnline() {
        cart.setDDD(11); //Default SP 11 para geolocation bloqueada.
        homePage.acessarLojaHome();
    }

    @Quando("selecionar o Plano Controle/Pós de id {string} na Home")
    public void selecionarPlanoPos(String id) {
        cart.setPlan(id);
        homePage.validarCardPlano(cart.getPlan(), false);
        homePage.selecionarPlano(id);
    }

    @E("preencher o campo Seu telefone Claro com o msidn {string}")
    public void preencherOCampoSeuTelefoneClaroComOMsidn(String msisdn) {
        homePage.preencherCampoSeuTelefoneHeader(msisdn);
    }

    @Quando("o usuário clicar na opção [Controle] do header")
    public void acessarPlpControle() {
        //TODO
    }

    @Quando("o usuário clicar na opção [Pós] do header")
    public void acessarPlpPos() {
        //TODO
    }

    @Quando("o usuário clicar na opção [Celulares] do header")
    public void acessarPlpCelulares() {
        homePage.acessarPlpAparelhos();
    }

    @Quando("o usuário clicar na opção [Acessórios] do header")
    public void acessarPlpAcessorios() {
        //TODO
        homePage.acessarMenuAcessorios();
    }

    @Quando("o usuário clicar no botão [Mais detalhes] do plano {string}")
    public void acessarPdpPlano(String id) {
        cart.setPlan(id);
        homePage.validarCardPlano(cart.getPlan(), true);
        homePage.acessarPdpPlano(id);
    }

    @Quando("o usuário clicar no botão [Entrar] do header")
    public void clicaNoBotaoEntrar() {
        homePage.clicaBotaoEntrar();
    }

    @Entao("é direcionado para a Home")
    public void validarQueFoiDirecionadoParaAHome() {
        homePage.validarHomePage();
    }
}