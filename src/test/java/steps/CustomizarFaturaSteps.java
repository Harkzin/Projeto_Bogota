package steps;

import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Quando;
import org.junit.Assert;
import pages.CustomizarFaturaPage;
import support.BaseSteps;

public class CustomizarFaturaSteps extends BaseSteps {

    CustomizarFaturaPage customizarFaturaPage = new CustomizarFaturaPage(driver);

    @Quando("^seleciono a forma de pagamento \"([^\"]*)\" para plano \"([^\"]*)\"$")
    public void selecionoAFormaDePagamentoParaPlano(String formaPagamento, String plano) throws Throwable {
        customizarFaturaPage.clicarFormaDePagamento(formaPagamento, plano);
    }

    @E("^marco o checkbox de termos de aceite$")
    public void marcoOCheckboxDeTermosDeAceite() {
        customizarFaturaPage.marcarCheckboxTermo();
    }

    @E("^selecionar a data de vencimento \"([^\"]*)\"$")
    public void selecionarADataDeVencimento(String data) throws Throwable {
        customizarFaturaPage.selecionarDataVencimento(data);
    }

    @E("^selecionar a fatura \"([^\"]*)\"$")
    public void selecionarAFatura(String fatura) throws Throwable {
        customizarFaturaPage.selecionarTipoFatura(fatura);
    }
}
