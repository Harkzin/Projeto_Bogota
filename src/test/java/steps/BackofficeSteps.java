package steps;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.junit.Assert;
import pages.BackofficePage;
import support.BaseSteps;

public class BackofficeSteps extends BaseSteps {
    BackofficePage backofficePage = new BackofficePage(driver);

    @Dado("^que ao acessar o backoffice")
    public void queAoAcessarBackoffice() {
        backofficePage.acessarTelaBKO();
    }

    @Quando("^preencher \"([^\"]*)\" no filtro$")
    public void preencherNoFiltro(String filtro) {
        backofficePage.preencherFiltro(filtro);
    }

    @E("^selecionar a opcao \"([^\"]*)\"$")
    public void selecionarAOpcao(String filtro) {
        backofficePage.selecionarOpcaoDoFiltro(filtro);
    }

    @Entao("^selecionar o cliente \"([^\"]*)\" na página de pedidos$")
    public void selecionarOClienteNaPáginaDePedidos(String nome) {
        backofficePage.selecionarCliente(nome);

    }

    @E("^selecionar a aba \"([^\"]*)\"$")
    public void selecionarAAba(String menu) {
        backofficePage.selecionarMenu(menu);
    }

    @E("^validar o msisdn \"([^\"]*)\"$")
    public void validarOMsisdn(String telefone) {
        backofficePage.telefonePedidoPropriedade(telefone);

    }

    @E("^validar que o campo \"([^\"]*)\" apresenta o valor \"([^\"]*)\"$")
    public void validarQueOCampoApresentaOStatus(String campo, String valor) {
        Assert.assertEquals(valor, backofficePage.valorCampoBKO(campo));
    }
}
