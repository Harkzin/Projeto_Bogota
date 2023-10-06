package steps;

import cucumber.api.Scenario;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import pages.DadosPessoaisPage;
import support.BaseSteps;

public class DadosPessoaisSteps extends BaseSteps {
    DadosPessoaisPage dadosPessoaisPage = new DadosPessoaisPage(driver);

    @E("^preencher os dados pessoais: \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void preencherDadosPessoais(String nomeCompleto, String dataNascimento, String nomeDaMae) throws Throwable {
        dadosPessoaisPage.preencherNomeCompleto(nomeCompleto);
        dadosPessoaisPage.preencherDataNascimento(dataNascimento);
        dadosPessoaisPage.preencherNomeDaMae(nomeDaMae);
    }
    @Então("^validar que foi exibida uma mensagem de erro do cep \"([^\"]*)\"$")
    public void validarQueFoiExibidaUmaMensagemDeErroDoCep(String mensagem) throws Throwable {
        dadosPessoaisPage.validarMensagemBloqueiocep(mensagem);
    }

    @E("^preencho os campos Nome Completo \"([^\"]*)\", Data De Nascimento \"([^\"]*)\" e Nome da Mãe \"([^\"]*)\"$")
    public void preenchoOsCamposNomeCompletoDataDeNascimentoENomeDaMae(String nomeCompleto, String dataNascimento, String nomeDaMae) throws Throwable {
        dadosPessoaisPage.camposDadosPessoais(nomeCompleto, dataNascimento, nomeDaMae);
    }

    @E("^preencho os campos \"([^\"]*)\", \"([^\"]*)\" e \"([^\"]*)\" no endereço")
    public void preenchoOsCamposENoEndereço(String cep, String numero, String complemento) throws Throwable {
        dadosPessoaisPage.camposEndereco(cep, numero, complemento);
    }
}
