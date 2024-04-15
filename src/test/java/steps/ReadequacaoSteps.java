package steps;

import io.cucumber.java.pt.Entao;
import pages.ReadequacaoPage;
import support.BaseSteps;

public class ReadequacaoSteps extends BaseSteps {
    ReadequacaoPage readequacaoPage = new ReadequacaoPage(driverQA);

    @Entao("é direcionado para a tela de readequação THAB")
    public void eDirecionadoParaATelaDeTHAB() {
        readequacaoPage.validarPaginaReadequacaoTHAB();
    }
}
