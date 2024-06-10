package steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.ReadequacaoPage;
import support.CartOrder;

public class ReadequacaoSteps {
    private final ReadequacaoPage readequacaoPage;
    private final CartOrder cartOrder;

    public ReadequacaoSteps(ReadequacaoPage readequacaoPage, CartOrder cartOrder) { //Spring Autowired
        this.readequacaoPage = readequacaoPage;
        this.cartOrder = cartOrder;
    }

    @Entao("é direcionado para a tela de readequação THAB")
    public void eDirecionadoParaATelaDeTHAB() {
        readequacaoPage.validarPaginaReadequacaoTHAB();
    }
    @Quando("seleciona o plano de controle antecipado ofertado")
    public void selecionaOPlanoDeControleAntecipadoOfertadoClicandoNoBotaoEuQueroDele() {
        readequacaoPage.clicarEuQuero();
    }
}
