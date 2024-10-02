package steps;

import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import pages.PlpAcessoriosPage;

public class PlpAcessoriosSteps {

    private final PlpAcessoriosPage plpAcessoriosPage;

    public PlpAcessoriosSteps(PlpAcessoriosPage plpAcessoriosPage) {
        this.plpAcessoriosPage = plpAcessoriosPage;
    }

    @Então("é direcionado para a tela PLP de Acessorios")
    public void validoQueFoiDirecionadoParaAPLPDeAcessorios() {
        plpAcessoriosPage.validarPlpAcessorios();
    }

    @E("validar que [Todas as Ofertas] está selecionado")
    public void validarTodasOfertas(){
        plpAcessoriosPage.validarTodasOfertas();
    }

    @Quando("o usuário clicar no botão [Comprar!] no produto [Repetidor Mesh Deco]")
    public void clicarComprar(){
        plpAcessoriosPage.clilcarBotaoComprar();
    }
}
