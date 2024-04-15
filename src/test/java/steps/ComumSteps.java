package steps;

import io.cucumber.java.pt.Mas;
import io.cucumber.java.pt.Quando;
import pages.ComumPage;
import pages.DadosPessoaisPage;
import support.BaseSteps;

public class ComumSteps extends BaseSteps {
    ComumPage comumPage = new ComumPage(driverQA);
    DadosPessoaisPage dadosPessoaisPage = new DadosPessoaisPage(driverQA);

    @Mas("não deve haver alterações no valor e nem nas informações do Plano")
    public void validarResumoDaCompra() {
        comumPage.validarResumoCompra();
    }

    @Quando("o usuário clicar no botão Continuar da tela de Cliente Combo")
    public void oUsuárioClicarNoBotãoContinuarDaTelaDeClienteCombo() {
        dadosPessoaisPage.clicarContinuar();
    }
}