package steps;

import cucumber.api.PendingException;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import pages.HomePage;
import support.BaseSteps;
import support.Hooks;

public class HomeSteps extends BaseSteps {

    HomePage homePage = new HomePage(driver);

    @Dado("^que acesso a Loja Online$")
    public void queAcessoALojaOnline() throws Throwable {
        homePage.acessarLojaHome();
    }
    @E("^selecionar o \"([^\"]*)\" plano do carrossel da Home clicando no botão Eu quero! dele$")
    public void selecionarOPlanoDoCarrosselDaHomeClicandoNoBotãoDele(String cardHome) throws Throwable {
        if (Hooks.tagScenarios.contains("@controle")) {
            homePage.selecionarCardControle(cardHome);

        } else if (Hooks.tagScenarios.contains("@pos")) {
            homePage.selecionarCardPos(cardHome);
        }
    }
    @E("^preencher o campo “Seu telefone Claro” com o msidn \"([^\"]*)\"$")
    public void preencherOCampoSeuTelefoneClaroComOMsidn(String msisdn) throws Throwable {
        homePage.preencherCampoSeuTelefoneHeader(msisdn);
    }
    @E("^validar que o botão Entrar foi alterado para o \"([^\"]*)\"$")
    public void validarQueOBotãoEntrarFoiAlteradoParaO(String cliente) throws Throwable {
        homePage.validarClienteMeusPedidos(cliente);
    }
    @E("^preencho com o Token$")
    public void preenchoComOToken() {
        homePage.tokenTemp();}
    @Dado("^que acesso a URL parametrizada para a oferta de rentabilizacao$")
    public void queAcessoAURLParametrizadaParaAOfertaDeRentabilizacao() {
        homePage.acessarURLRentabilizacao();
    }

    @E("^clicar na PLP do plano$")
    public void clicarNaPLP() throws Throwable {
        homePage.clicarPLP();
    }

    @E("^selecionar o \"([^\"]*)\" plano do carrossel da Home clicando no botão Mais detalhes dele$")
    public void selecionarOPlanoDoCarrosselDaHomeClicandoNoBotãoMaisDetalhesDele(String cardPDP) throws Throwable {
        homePage.selecionarPDPCard(cardPDP);
    }
}
