package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.DadosPessoaisPage;
import support.BaseSteps;

public class DadosPessoaisSteps extends BaseSteps {
    DadosPessoaisPage dadosPessoaisPage = new DadosPessoaisPage(driverQA);

    @Então("é direcionado para a tela de Dados Pessoais")
    public void validarPaginaDadosPessoais() {
        dadosPessoaisPage.validarPaginaDadosPessoais();
    }

    @E("preenche os campos de informações pessoais: Nome Completo {string}, Data De Nascimento {string} e Nome da Mãe {string}")
    public void preencherDadosPessoais(String nome, String data, String nomeMae) {
        dadosPessoaisPage.inserirNome(nome);
        dadosPessoaisPage.inserirDataNascimento(data);
        dadosPessoaisPage.inserirNomeMae(nomeMae);
    }

    @E("preenche os campos de endereço: [CEP] com um CEP de entrega {string}, [Número] {string} e [Complemento] {string}")
    public void preencherCamposEndereco(String cep, String numero, String complemento) {
        dadosPessoaisPage.inserirCep(cep);
        dadosPessoaisPage.inserirDadosEndereco(numero, complemento);
    }

    @Então("será exibida a mensagem de erro e não será possível continuar: {string}")
    public void validarMensagemDeErroDoCep(String mensagem) {
        dadosPessoaisPage.validarMensagemBloqueioCep(mensagem);
    }

    @Quando("o usuário clicar no botão Continuar da tela de Dados Pessoais")
    public void clicarContinuar() {
        dadosPessoaisPage.clicarContinuar();
    }
}
