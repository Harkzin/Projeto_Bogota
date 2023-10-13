package steps;

import cucumber.api.java.pt.E;
import pages.CustomizarFaturaPage;
import support.BaseSteps;

public class CustomizarFaturaSteps extends BaseSteps {

    CustomizarFaturaPage customizarFaturaPage = new CustomizarFaturaPage(driver);

    @E("^seleciono a forma de pagamento")
    public void selecionoAFormaDePagamentoParaPlano() throws Throwable {
        customizarFaturaPage.clicarFormaDePagamento();
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
