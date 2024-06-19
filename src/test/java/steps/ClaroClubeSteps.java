package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.ClaroClubePage;

public class ClaroClubeSteps {
    private final ClaroClubePage claroClubePage;

    ClaroClubeSteps(ClaroClubePage claroClubePage) { //Spring Autowired
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
