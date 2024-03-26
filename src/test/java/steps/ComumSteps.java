package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import pages.ComumPage;
import pages.DadosPessoaisPage;
import support.BaseSteps;

public class ComumSteps extends BaseSteps {
    ComumPage comumPage = new ComumPage(driverQA);
    DadosPessoaisPage dadosPessoaisPage = new DadosPessoaisPage(driverQA);

    @E("não deve haver alterações no valor e nas informações do Plano")
    public void validarResumoDaCompra() {

    }

    @Quando("o usuário clicar no botão Continuar da tela de Cliente Combo")
    public void oUsuárioClicarNoBotãoContinuarDaTelaDeClienteCombo() {
        dadosPessoaisPage.clicarContinuar();
    }
}
