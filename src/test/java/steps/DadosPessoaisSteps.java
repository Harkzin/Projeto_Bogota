package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import pages.DadosPessoaisPage;
import support.BaseSteps;

public class DadosPessoaisSteps extends BaseSteps {
    DadosPessoaisPage dadosPessoaisPage = new DadosPessoaisPage(driverQA);

    @Então("é direcionado para a tela de Dados Pessoais")
    public void validarDadosPessoais(){
        dadosPessoaisPage.validarPaginaDadosPessoais();
    }

    @E("preenche os campos de informações pessoais: Nome Completo {string}, Data De Nascimento {string} e Nome da Mãe {string}")
    public void preencherDadosPessoais(String nome, String data, String nomeMae) {
        dadosPessoaisPage.inserirNome(nome);
        dadosPessoaisPage.inserirDataNascimento(data);
        dadosPessoaisPage.inserirNomeMae(nomeMae);
    }
    @Então("validar que foi exibida uma mensagem de erro do cep {string}")
    public void validarQueFoiExibidaUmaMensagemDeErroDoCep(String mensagem) {
        dadosPessoaisPage.validarMensagemBloqueiocep(mensagem);
    }

    @E("preenche os campos de endereço: CEP {string}, Número {string} e Complemento {string}")
    public void preencheCamposEndereco(String cep, String numero, String complemento) {
        dadosPessoaisPage.inserirCep(cep);
        dadosPessoaisPage.inserirDadosEndereco(numero, complemento);
    }

    @Quando("o usuário clicar no botão Continuar da tela de Dados Pessoais")
    public void clicarContinuar() {
        dadosPessoaisPage.clicarContinuar();
    }
}
