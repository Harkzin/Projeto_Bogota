package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverWeb driverWeb;

    @Autowired
    public ParabensPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPaginaParabens() {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
    }

    public void clicarOkEntendi() {
        driverWeb.javaScriptClick("btn-entendi-modal-abr", "id");
    }

    public void validarCamposPedido() {
        //TODO ECCMAUT-351
    }
}