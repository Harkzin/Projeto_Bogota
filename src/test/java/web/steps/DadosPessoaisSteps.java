package web.steps;

import io.cucumber.java.pt.*;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.DadosPessoaisPage;
import web.models.CartOrder;

import static web.support.utils.Constants.DeliveryMode.*;

public class DadosPessoaisSteps {

    private final DadosPessoaisPage dadosPessoaisPage;
    private final CartOrder cartOrder;

    @Autowired
    public DadosPessoaisSteps(DadosPessoaisPage dadosPessoaisPage, CartOrder cartOrder) {
        this.dadosPessoaisPage = dadosPessoaisPage;
        this.cartOrder = cartOrder;
    }

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
        cartOrder.setDeliveryMode(CONVENTIONAL);
        dadosPessoaisPage.inserirCep(cep);
        dadosPessoaisPage.inserirDadosEnderecoEntrega(numero, complemento);
    }

    @E("preenche os campos de endereço: [CEP] expressa {string}, [Número] {string} e [Complemento] {string}")
    public void preencherCamposEnderecoEntregaExpressa(String cep, String numero, String complemento) {
        cartOrder.setDeliveryMode(EXPRESS);
        dadosPessoaisPage.inserirCep(cep);
        dadosPessoaisPage.inserirDadosEnderecoEntrega(numero, complemento);
    }

    @E("deve ser exibido os tipos de entrega")
    public void exibirEntrega() {
        dadosPessoaisPage.validarTiposEntregaEChip(true, cartOrder.getDeliveryMode(), cartOrder.isDeviceCart());
    }

    @E("o usuário desmarcar a opção [Usar o mesmo endereço de entrega]")
    public void desmarcarOpcaoUsarMesmoEnderecoEntrega() {
        dadosPessoaisPage.clicarUsarMesmoEnderecoEntrega();
    }

    @Entao("será exibido o campo de [CEP] do endereço de cobrança")
    public void seraExibidoCampoCepEnderecoCobranca() {
        dadosPessoaisPage.exibirCepCobranca();
    }

    @E("preenche os campos de endereço de cobrança: [CEP] {string} [Número] {string} [Complemento] {string}")
    public void preencherCamposEnderecoEntrega(String cep, String numero, String complemento) {
        dadosPessoaisPage.inserirCepCobranca(cep);
        dadosPessoaisPage.inserirDadosEnderecoCobranca(numero, complemento);
    }

    @E("o usuário seleciona o tipo de sim [Esim]")
    public void selecionaEsim() {
        cartOrder.setEsimChip();
        dadosPessoaisPage.selecionarEsim(cartOrder.getDeliveryMode());
    }

    @Mas("não deve ser exibido os tipos de entrega")
    public void naoExibirEntrega() {
        dadosPessoaisPage.validarTiposEntregaEChip(false, cartOrder.getDeliveryMode(), cartOrder.isDeviceCart());
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