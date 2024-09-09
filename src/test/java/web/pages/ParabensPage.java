package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.utils.DriverQA;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public ParabensPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void validarPaginaParabens() {
        driverQA.waitPageLoad("/checkout/orderConfirmation", 60);
    }

    public void clicarOkEntendi() {
        driverQA.javaScriptClick("btn-entendi-modal-abr", "id");
    }

    public void validarCamposPedido() {
        //TODO ECCMAUT-351
    }
}