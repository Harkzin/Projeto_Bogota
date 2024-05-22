package pages;

import support.CartOrder;
import support.DriverQA;

public class ComumPage {
    private final DriverQA driver;
    private final CartOrder cartOrder;

    public ComumPage(DriverQA stepDriver, CartOrder cartOrder) {
        driver = stepDriver;
        this.cartOrder = cartOrder;
    }

    public void validarResumoCompraPlano() {
        //TODO ECCMAUT-351
    }

    public void validarResumoCompraAparelho() {
        //TODO ECCMAUT-351
    }
}