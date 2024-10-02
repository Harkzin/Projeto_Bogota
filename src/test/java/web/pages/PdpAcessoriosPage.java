package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.utils.DriverQA;

@Component
@ScenarioScope
public class PdpAcessoriosPage {

    private final DriverQA driverQA;

    @Autowired
    public PdpAcessoriosPage(DriverQA driverQA) {
        this.driverQA = driverQA;
    }

    public void validarPaginaPdpAcessorios(){
        driverQA.waitPageLoad("/000000000000062124?category=accessories", 10);
    }

    public void clicarBotaoComprar(){
        driverQA.javaScriptClick("btn-eu-quero-000000000000062124", "id");
    }

}
