package pages;


import support.CartOrder;
import support.utils.DriverQA;

public class PlpAparelhosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public PlpAparelhosPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void validarPlpAparelhos() {
        driverQA.waitPageLoad("/smartphone", 5);
    }

    public void clicaBotaoEuQuero(String id) {
        driverQA.javaScriptClick("btn-eu-quero-0000000000000" + id, "id");
    }
}