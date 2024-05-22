package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Mas;
import io.cucumber.java.pt.Quando;
import pages.DadosPessoaisPage;
import support.BaseSteps;

import static support.Common.Cart_isExpressDelivery;

public class DadosPessoaisSteps extends BaseSteps {
    DadosPessoaisPage dadosPessoaisPage = new DadosPessoaisPage(driverQA);

    @Então("é direcionado para a tela de Dados Pessoais")
    public void validarPaginaDadosPessoais() {
        dadosPessoaisPage.validarPaginaDadosPessoais();
    }

    @E("preenche os campos de dados pessoais: [Nome Completo] {string}, [Data de Nascimento] {string} e [Nome da Mãe] {string}")
    public void preencherDadosPessoais(String nome, String data, String nomeMae) {
        dadosPessoaisPage.inserirNome(nome);
        dadosPessoaisPage.inserirDataNascimento(data);
        dadosPessoaisPage.inserirNomeMae(nomeMae);
    }

    @E("preenche os campos de endereço: [CEP] convencional {string}, [Número] {string} e [Complemento] {string}")
    public void preencherCamposEnderecoEntregaConvencional(String cep, String numero, String complemento) {
        dadosPessoaisPage.inserirCep(cep);
        dadosPessoaisPage.inserirDadosEndereco(numero, complemento);
        Cart_isExpressDelivery = false;
    }

    @E("preenche os campos de endereço: [CEP] expressa {string}, [Número] {string} e [Complemento] {string}")
    public void preencherCamposEnderecoEntregaExpressa(String cep, String numero, String complemento) {
        dadosPessoaisPage.inserirCep(cep);
        dadosPessoaisPage.inserirDadosEndereco(numero, complemento);
        Cart_isExpressDelivery = true;
    }

    @E("deve ser exibido os tipos de entrega")
    public void exibirEntrega() {
        dadosPessoaisPage.validarTiposEntrega(true, Cart_isExpressDelivery);
    }

    @Mas("não deve ser exibido os tipos de entrega")
    public void naoExibirEntrega() {
        dadosPessoaisPage.validarTiposEntrega(false, Cart_isExpressDelivery);
    }

    @Então("será recarregada a página e exibida a mensagem de erro: {string}")
    public void exibirFraseBloqueioCep(String mensagem) {
        dadosPessoaisPage.validarPaginaDadosPessoaisBloqueioCep(mensagem);
    }

    @Quando("o usuário clicar no botão [Continuar] da tela de Dados Pessoais")
    public void clicarContinuar() {
        dadosPessoaisPage.clicarContinuar();
    }
}