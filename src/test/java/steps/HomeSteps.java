package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.HomePage;
import support.CartOrder;

public class HomeSteps {

    private final HomePage homePage;
    private final CartOrder cartOrder;

    public HomeSteps(HomePage homePage, CartOrder cartOrder) { //Spring Autowired
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
        //TODO
    }

    @Quando("o usuário clicar na opção [Acessórios] do header")
    public void acessarPlpAcessorios() {
        //TODO
    }

    @Quando("o usuário clicar no botão [Mais detalhes] do plano {string}")
    public void acessarPdpPlano(String id) {
        cartOrder.setPlan(id);
        homePage.acessarPdpPlano(id);
    }

    @Quando("o usuário clicar no botão [Entrar] do header")
    public void clicaNoBotaoEntrar() {
        homePage.clicaBotaoEntrar();
    }

    @Então("é direcionado para a Home")
    public void validarQueFoiDirecionadoParaAHome() {
        homePage.validarHomePage();
    }
}