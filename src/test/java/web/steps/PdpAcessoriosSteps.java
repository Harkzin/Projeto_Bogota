package web.steps;

import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.PdpAcessoriosPage;


public class PdpAcessoriosSteps {

    private final PdpAcessoriosPage pdpAcessoriosPage;

    @Autowired
    public PdpAcessoriosSteps(PdpAcessoriosPage pdpAcessoriosPage) {
        this.pdpAcessoriosPage = pdpAcessoriosPage;
    }

    @Entao("é direcionado para a PDP do Acessório selecionado")
    public void validarTelaPdpAcessorios(){
        pdpAcessoriosPage.validarPaginaPdpAcessorios();
    }

    @Quando("o usuário clicar no botão [Comprar] na PDP de Acessórios [ACESSÓRIO] {string}")
    public void clicarBotaoComprar(String acessorio){
        pdpAcessoriosPage.clicarBotaoComprar(acessorio);
    }
}
