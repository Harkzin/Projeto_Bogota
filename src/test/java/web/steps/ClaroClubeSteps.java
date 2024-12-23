package web.steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import web.pages.ClaroClubePage;

public class ClaroClubeSteps {
    private final ClaroClubePage claroClubePage;

    @Autowired
    ClaroClubeSteps(ClaroClubePage claroClubePage) {
        this.claroClubePage = claroClubePage;
    }

    @Entao("é direcionado para a tela de Claro Clube")
    public void eDirecionadoParaATelaDeClaroClube() {
        claroClubePage.validarPaginaClaroClube();
    }

    @E("preenche o campo [Numero do seu celular Claro] {string}")
    public void preencheOCampoComONumeroDoCelularClaro(String telefone) {
        claroClubePage.preencheTelefone(telefone);
    }

    @Quando("o usuário clicar no botão [Continuar] Claro Clube")
    public void oUsuarioClicarNaOpcaoContinuarClaroClube() {
        claroClubePage.clicarBotaoContinuarClaroClube();
    }
}