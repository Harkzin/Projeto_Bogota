package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.ReadequacaoPage;
import web.models.CartOrder;

public class ReadequacaoSteps {
    private final ReadequacaoPage readequacaoPage;
    private final CartOrder cart;

    @Autowired
    public ReadequacaoSteps(ReadequacaoPage readequacaoPage, CartOrder cart) {
        this.readequacaoPage = readequacaoPage;
        this.cart = cart;
    }

    @Entao("é direcionado para a tela de readequação THAB")
    public void eDirecionadoParaATelaDeTHAB() {
        readequacaoPage.validarPaginaReadequacaoTHAB(cart.getPlan());
    }

    @Entao("é direcionado para a tela de readequação Controle Fácil")
    public void eDirecionadoParaATelaDeControleFacil() {
        readequacaoPage.validarPaginaReadequacaoControleFacil(cart.getPlan());
    }

    @Quando("o usuário selecionar o plano de controle antecipado ofertado")
    public void selecionaOPlanoDeControleAntecipadoOfertadoClicandoNoBotaoEuQueroDele() {
        readequacaoPage.clicarEuQuero();
    }

    @E("clicar no [Eu quero!] no Controle Fácil")
    public void clicarEuQueroControleFacil() {
        readequacaoPage.clicarEuQueroControleFacil();
    }
}
