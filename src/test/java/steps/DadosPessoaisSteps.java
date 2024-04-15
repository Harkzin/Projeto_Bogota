package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Mas;
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

    @E("preenche os campos de endereço: [CEP] {string}, [Número] {string} e [Complemento] {string}") //CEP - "convencional", "expressa" ou Número do CEP
    public void preencheOsCamposDeEndereçoCEPNumeroEComplemento(String cep, String numero, String complemento) {
        dadosPessoaisPage.inserirCep(cep);
        dadosPessoaisPage.inserirDadosEndereco(numero, complemento);
    }

    @E("deve ser exibido os tipos de entrega")
    public void exibeEntrega() {
        dadosPessoaisPage.validarTiposEntrega(true);
    }

    @Mas("não deve ser exibido os tipos de entrega")
    public void naoExibeEntrega() {
        dadosPessoaisPage.validarTiposEntrega(false);
    }

    @Então("será exibida a mensagem de erro e não será possível continuar: {string}")
    public void validarMensagemDeErroDoCep(String mensagem) {
        dadosPessoaisPage.validarMensagemBloqueioCep(mensagem);
    }

    @Quando("o usuário clicar no botão [Continuar] da tela de Dados Pessoais")
    public void clicarContinuar() {
        dadosPessoaisPage.clicarContinuar();
    }
}