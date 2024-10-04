package web.steps;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PlpAcessoriosPage;

public class PlpAcessoriosSteps {

    private final PlpAcessoriosPage plpAcessoriosPage;

    @Autowired
    public PlpAcessoriosSteps(PlpAcessoriosPage plpAcessoriosPage) {
        this.plpAcessoriosPage = plpAcessoriosPage;
    }

    @Então("é direcionado para a tela PLP de Acessorios")
    public void validoQueFoiDirecionadoParaAPLPDeAcessorios() {
        plpAcessoriosPage.validarPlpAcessorios();
    }

    @E("deve ser exibido [Todas as Ofertas]")
    public void validarTodasOfertas() {
        plpAcessoriosPage.validarTodasOfertas();
    }

    @Quando("o usuário clicar no botão [Comprar] no produto [CARREGADOR DE PAREDE CONCEPT]")
    public void clicarComprar() {
        plpAcessoriosPage.clicarBotaoComprar();
    }
}
