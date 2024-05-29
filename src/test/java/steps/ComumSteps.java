package steps;

import io.cucumber.java.pt.Mas;
import pages.ComumPage;
import support.BaseSteps;

public class ComumSteps extends BaseSteps {
    ComumPage comumPage = new ComumPage(driverQA, cartOrder);

    @Mas("não deve haver alterações no valor e nem nas informações do Plano")
    public void validarResumoCompraPlano() {
        comumPage.validarResumoCompraPlano();
    }

    @Mas("não deve haver alterações no valor e nem nas informações do Aparelho")
    public void validarResumoCompraAparelho() {
        comumPage.validarResumoCompraAparelho();
    }
}