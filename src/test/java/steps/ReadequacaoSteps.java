package steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.ReadequacaoPage;
import support.BaseSteps;

public class ReadequacaoSteps extends BaseSteps {
    ReadequacaoPage readequacaoPage = new ReadequacaoPage(driverQA, cartOrder);

    @Entao("é direcionado para a tela de readequação THAB")
    public void eDirecionadoParaATelaDeTHAB() {
        readequacaoPage.validarPaginaReadequacaoTHAB();
    }
    @Quando("seleciona o plano de controle antecipado ofertado")
    public void selecionaOPlanoDeControleAntecipadoOfertadoClicandoNoBotaoEuQueroDele() {
        readequacaoPage.clicarEuQuero();
    }
}
