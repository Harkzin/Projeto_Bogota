package steps;

import io.cucumber.java.pt.Entao;
import pages.EmailPage;
import support.BaseSteps;

public class EmailSteps extends BaseSteps {

    EmailPage emailPage = new EmailPage(driver);

    @Entao("^validar email$")
    public void validarEmail() throws Throwable {
        emailPage.validarEmail();
    }
}
