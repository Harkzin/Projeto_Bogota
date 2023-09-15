package steps;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Quando;
import cucumber.api.java.pt.Então;
import pages.CarrinhoPage;
import support.BaseSteps;

public class CarrinhoSteps extends BaseSteps {

    CarrinhoPage carrinhoPage = new CarrinhoPage(driver);

    @Dado("^validar que não há alterações no valor e/ou informações do Plano$")
    public void validarQueNãoHáAlteraçõesNoValorEOuInformaçõesDoPlano() throws Throwable {
        carrinhoPage.validarCarrinho();
    }

    @Dado("^selecionar a opção \"([^\"]*)\"$")
    public void selecionarAOpção(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Dado("^preencher os campos com os dados da linha Dependente$")
    public void preencherOsCamposComOsDadosDaLinhaDependente() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Quando("^clicar no botão \"([^\"]*)\"$")
    public void clicarNoBotão(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Então("^validar que foi exibida uma mensagem de erro e que não é possível avançar com o pedido$")
    public void validarQueFoiExibidaUmaMensagemDeErroEQueNãoÉPossívelAvançarComOPedido() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }
}
