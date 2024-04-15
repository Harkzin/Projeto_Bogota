package steps;

import io.cucumber.java.pt.Mas;
import io.cucumber.java.pt.Quando;
import pages.ComumPage;
import pages.DadosPessoaisPage;
import support.BaseSteps;

public class ComumSteps extends BaseSteps {
    ComumPage comumPage = new ComumPage(driverQA);
    DadosPessoaisPage dadosPessoaisPage = new DadosPessoaisPage(driverQA);

    @Mas("não deve haver alterações no valor e nas informações do Plano")
    public void validarResumoDaCompra() {

    }
}