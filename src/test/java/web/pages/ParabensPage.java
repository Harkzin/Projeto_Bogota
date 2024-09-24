package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.support.utils.DriverWeb;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverWeb driverWeb;
    private final CartOrder cartOrder;

    @Autowired
    public ParabensPage(DriverWeb driverWeb, CartOrder cartOrder) {
        this.driverWeb = driverWeb;
        this.cartOrder = cartOrder;
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