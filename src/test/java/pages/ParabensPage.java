package pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

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

    public void validarCamposPedido() {
        //TODO ECCMAUT-351
    }
}