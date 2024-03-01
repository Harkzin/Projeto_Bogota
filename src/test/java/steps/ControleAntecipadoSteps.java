package steps;

import io.cucumber.java.pt.E;
import pages.ControleAntecipadoPage;
import support.BaseSteps;

public class ControleAntecipadoSteps extends BaseSteps {

    ControleAntecipadoPage controleAntecipadoPage = new ControleAntecipadoPage(driver);

    @E("^valido que foi ofertado plano de Controle Antecipado$")
    public void validoQueFoiOfertadoPlanoDe() {
        controleAntecipadoPage.PlanoControleAntecipadoExiste();
    }

    @E("^marco o checkbox de termos de aceite thab$")
    public void marcoOCheckboxDeTermosDeAceiteThab() {
        controleAntecipadoPage.marcarCheckboxTermoTHAB();
    }
}
