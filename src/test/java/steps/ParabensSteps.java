package steps;

import io.cucumber.java.pt.Então;
import pages.ParabensPage;
import support.BaseSteps;

public class ParabensSteps extends BaseSteps {

    ParabensPage parabensPage = new ParabensPage(driver);

    @Então("^validar que não há alterações no valor e/ou informações do Plano na tela de parabens$")
    public void validarQueNãoHáAlteraçõesNoValorEOuInformaçõesDoPlanoNaTelaDeParabens() throws Throwable {
        parabensPage.validarCamposPedido();
    }
}
