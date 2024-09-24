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
    private final CartOrder cartOrder;

    @Autowired
    public HomeSteps(HomePage homePage, CartOrder cartOrder) {
        this.homePage = homePage;
        this.cartOrder = cartOrder;
    }

    @Dado("que o usuário acesse a Loja Online")
    public void AcessarLojaOnline() {
        homePage.acessarLojaHome();
    }

    @Quando("selecionar o plano de id {string} do carrossel da Home")
    public void selecionarPlano(String id) {
        cartOrder.setPlan(id);
        homePage.validarCardPlano(cartOrder.getPlan(), true);
        homePage.selecionarPlano(id);
    }

    @Quando("selecionar o plano Controle de id {string} na Home")
    public void selecionarPlanoControle(String id) {
        cartOrder.isDebitPaymentFlow = false;
        cartOrder.setPlan(id);
        homePage.validarCardPlano(cartOrder.getPlan(), false);
        homePage.selecionarPlanoControle(id);
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
    }

    @Quando("o usuário clicar no botão [Mais detalhes] do plano {string}")
    public void acessarPdpPlano(String id) {
        cartOrder.setPlan(id);
        homePage.validarCardPlano(cartOrder.getPlan(), true);
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