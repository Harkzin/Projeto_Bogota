package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.HomePage;
import support.BaseSteps;

public class HomeSteps extends BaseSteps {
    HomePage homePage = new HomePage(driverQA);

    @Dado("que o usuário acesse a Loja Online")
    public void AcessarLojaOnline() {
        homePage.acessarLojaHome();
    }

    @Quando("selecionar o plano de id {string} do carrossel da Home")
    public void selecionarPlanoHome(String id) {
        homePage.selecionarPlano(id);
    }

    @E("preencher o campo Seu telefone Claro com o msidn {string}")
    public void preencherOCampoSeuTelefoneClaroComOMsidn(String msisdn) {
        homePage.preencherCampoSeuTelefoneHeader(msisdn);
    }

    @E("clicar na PLP do plano")
    public void clicarNaPLP() {
        //homePage.clicarPLP();
    }

    @Quando("o usuário clicar no botão [Mais detalhes] do plano {string}")
    public void acessarPdpPlano(String idPlano) {
        homePage.acessarPdpPlano(idPlano);
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