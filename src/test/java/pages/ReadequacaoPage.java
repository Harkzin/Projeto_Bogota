package pages;

import org.junit.Assert;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
public class ReadequacaoPage  {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public ReadequacaoPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void validarPaginaReadequacaoTHAB() {
        driverQA.waitPageLoad("claro/pt/cart?THAB=true", 10);

        Assert.assertNotNull(driverQA.findElement("controle-antecipado", "id"));
    }

    public void clicarEuQuero() {
        driverQA.javaScriptClick("buttonCheckoutThab", "id");
    }
}
