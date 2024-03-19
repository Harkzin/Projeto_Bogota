package steps;

import io.cucumber.java.pt.E;
import pages.PDPPage;
import support.BaseSteps;

public class PDPSteps extends BaseSteps {

    PDPPage pdpPage = new PDPPage(driverQA);

    @E("validar que é direcionado para a PDP do plano de id {string} e clicar no botão Eu quero!")
    public void validarQueEDirecionadoParaAPDPDoPlanoEClicarNoBotãoEuQuero(String idPlano) {
        pdpPage.EuQueroPDP(idPlano);
    }

}
