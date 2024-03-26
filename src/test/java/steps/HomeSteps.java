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
        homePage.selecionarPlanoHome(id);
    }

    @E("preencher o campo Seu telefone Claro com o msidn {string}")
    public void preencherOCampoSeuTelefoneClaroComOMsidn(String msisdn) {
        homePage.preencherCampoSeuTelefoneHeader(msisdn);
    }

    @E("validar que o botão Entrar foi alterado para o {string}")
    public void validarQueOBotãoEntrarFoiAlteradoParaO(String cliente) {
        homePage.validarClienteMeusPedidos(cliente);
    }

    @E("preencho com o Token")
    public void preenchoComOToken() {
//        homePage.tokenTemp();
    }

    @Dado("que acesso a URL parametrizada para a oferta de rentabilizacao")
    public void queAcessoAURLParametrizadaParaAOfertaDeRentabilizacao() {
        homePage.acessarURLRentabilizacao();
    }

    @E("clicar na PLP do plano")
    public void clicarNaPLP() {
        //homePage.clicarPLP();
    }

    @E("selecionar o plano de id {string} do carrossel da Home clicando no botão Mais detalhes dele")
    public void selecionarOPlanoDoCarrosselDaHomeClicandoNoBotãoMaisDetalhesDele(String idPlano) {
        homePage.selecionarPDPCard(idPlano);
    }

    @Quando("o usuário clicar no botão: [Entrar]")
    public void clicaNoBotaoEntrar() {
        homePage.clicaBotaoEntrar();
    }

    @Então("validar que foi direcionado para a Home")
    public void validarQueFoiDirecionadoParaAHome() {

    }
}