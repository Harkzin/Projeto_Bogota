package steps;

import io.cucumber.java.pt.E;
import pages.ComumPage;
import support.BaseSteps;

public class ComumSteps extends BaseSteps {
    ComumPage comumPage = new ComumPage(driverQA);

    @E("não deve haver alterações no valor e nas informações do Plano")
    public void validarResumoDaCompra() {

    }
}
