package pages;

import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
public class ComumPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public ComumPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }


    public void validarResumoCompraPlano() {
        //TODO ECCMAUT-351
    }

    public void validarResumoCompraAparelho() {
        //TODO ECCMAUT-351
    }
}