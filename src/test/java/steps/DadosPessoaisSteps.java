package steps;

import cucumber.api.java.pt.E;
import pages.DadosPessoaisPage;
import support.BaseSteps;

public class DadosPessoaisSteps extends BaseSteps {
    DadosPessoaisPage pedidoDadosPessoaisPage = new DadosPessoaisPage(driver);

    @E("^preencher os dados pessoais: \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void preencherDadosPessoais(String nomeCompleto, String dataNascimento, String nomeDaMae) throws Throwable {
        pedidoDadosPessoaisPage.preencherNomeCompleto(nomeCompleto);
        pedidoDadosPessoaisPage.preencherDataNascimento(dataNascimento);
        pedidoDadosPessoaisPage.preencherNomeDaMae(nomeDaMae);
    }
}
