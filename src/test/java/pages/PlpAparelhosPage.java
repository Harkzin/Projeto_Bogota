package pages;


import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
@ScenarioScope
public class PlpAparelhosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public PlpAparelhosPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void validarPlpAparelhos() {
        driverQA.waitPageLoad("/smartphone", 5);
    }

    public void clicaBotaoEuQuero(String id) {
        driverQA.javaScriptClick("btn-eu-quero-" + id, "id");
    }
}