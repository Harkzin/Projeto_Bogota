package pages;

import org.junit.Assert;
import support.CartOrder;
import support.DriverQA;

public class ReadequacaoPage  {
    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public ReadequacaoPage(DriverQA stepDriver, CartOrder cartOrder) {
        driverQA = stepDriver;
        this.cartOrder = cartOrder;
    }

    public void validarPaginaReadequacaoTHAB() {
        driverQA.waitPageLoad("claro/pt/cart?THAB=true", 10);

        Assert.assertNotNull(driverQA.findElement("controle-antecipado", "id"));
    }

    public void clicarEuQuero() {
        driverQA.JavaScriptClick("buttonCheckoutThab", "id");
    }
}
