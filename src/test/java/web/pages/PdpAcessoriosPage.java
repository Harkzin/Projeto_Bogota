package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

@Component
@ScenarioScope
public class PdpAcessoriosPage {

    private final DriverWeb driverWeb;

    @Autowired
    public PdpAcessoriosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPaginaPdpAcessorios(){
        driverWeb.waitPageLoad("accessories", 10);
    }

    public void clicarBotaoComprar(){
        driverWeb.javaScriptClick("btn-eu-quero-000000000000062125", "id");
    }

}
