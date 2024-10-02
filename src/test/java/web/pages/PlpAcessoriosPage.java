package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;


@Component
@ScenarioScope
public class PlpAcessoriosPage {
    private final DriverWeb driverWeb;

    @Autowired
    public PlpAcessoriosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPlpAcessorios() {
        driverWeb.waitPageLoad("/accessories", 5);
    }

    public void validarTodasOfertas(){
        driverWeb.javaScriptClick("//*[@class='styles_item__8HZ_B styles_selected__M9eZM']", "xpath");
    }

    public void clilcarBotaoComprar(){
//        driverQA.javaScriptClick("//div[@class=\"mdn-CardProduct-content\"]/button[1]", "xpath");
        driverWeb.javaScriptClick("/html/body/div/div/div[2]/div[4]/div[2]/div[2]/div[2]/div/div[2]/div/div[2]/button", "xpath");
    }
}
