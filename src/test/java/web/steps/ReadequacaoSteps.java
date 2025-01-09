package web.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.ReadequacaoPage;

import web.models.CartOrder;

import static web.support.utils.Constants.ClaroJourneyInformation.*;

public class ReadequacaoSteps {
    private final ReadequacaoPage readequacaoPage;
    private final CartOrder cart;

    @Autowired
    public ReadequacaoSteps(ReadequacaoPage readequacaoPage, CartOrder cart) {
        this.readequacaoPage = readequacaoPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a tela de readequação THAB")
    public void validarPaginaReadeqTHAB() {
        cart.setThab();
        cart.setJourneyInformation(THAB);
        readequacaoPage.validarPaginaReadequacaoTHAB(cart.getPlan());
    }

    @Entao("é direcionado para a tela de readequação Controle Fácil")
    public void eDirecionadoParaATelaDeReadCtrlFacil() {
        readequacaoPage.validarPaginaReadCtrlFacil(cart);
    }

    @Quando("o usuário selecionar o plano de controle antecipado ofertado")
    public void selecionarPlanoThab() {
        readequacaoPage.clicarEuQuero();
    }

    @Quando("o usuário selecionar o plano de controle fácil")
    public void selecionaOPlanoDeControleFacilOfertadoClicandoNoBotaoEuQueroDele() {
        readequacaoPage.clicarEuQueroControleFacil();
    }
}
