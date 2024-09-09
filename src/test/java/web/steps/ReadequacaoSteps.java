package web.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.ReadequacaoPage;
import web.support.CartOrder;

public class ReadequacaoSteps {
    private final ReadequacaoPage readequacaoPage;
    private final CartOrder cartOrder;

    @Autowired
    public ReadequacaoSteps(ReadequacaoPage readequacaoPage, CartOrder cartOrder) {
        this.readequacaoPage = readequacaoPage;
        this.cartOrder = cartOrder;
    }

    @Entao("é direcionado para a tela de readequação THAB")
    public void eDirecionadoParaATelaDeTHAB() {
        readequacaoPage.validarPaginaReadequacaoTHAB(cartOrder.getPlan());
    }
    @Quando("o usuário selecionar o plano de controle antecipado ofertado")
    public void selecionaOPlanoDeControleAntecipadoOfertadoClicandoNoBotaoEuQueroDele() {
        readequacaoPage.clicarEuQuero();
    }
}
