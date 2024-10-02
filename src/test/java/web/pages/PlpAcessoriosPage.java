package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.utils.DriverQA;

@Component
@ScenarioScope
public class PlpAcessoriosPage {
    private final DriverQA driverQA;

    @Autowired
    public PlpAcessoriosPage(DriverQA driverQA) {
        this.driverQA = driverQA;
    }

    public void validarPlpAcessorios() {
        driverQA.waitPageLoad("/accessories", 5);
    }

    public void validarTodasOfertas(){
        driverQA.javaScriptClick("//*[@class='styles_item__8HZ_B styles_selected__M9eZM']", "xpath");
    }

    public void clilcarBotaoComprar(){
        driverQA.javaScriptClick("//div[@class=\"mdn-CardProduct-content\"]/button[1]", "xpath");
    }
}
