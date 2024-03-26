package steps;

import io.cucumber.java.pt.Quando;
import pages.PDPPage;
import support.BaseSteps;

public class PDPSteps extends BaseSteps {

    PDPPage pdpPage = new PDPPage(driverQA);

    @Quando("o usuário clicar no botão Eu quero! da PDP")
    public void clicarEuQuero() {
        pdpPage.clicarEuQuero();
    }

}
