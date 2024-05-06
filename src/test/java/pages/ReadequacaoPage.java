package pages;

import org.junit.Assert;
import support.DriverQA;

public class ReadequacaoPage  {
    private final DriverQA driverQA;

    public ReadequacaoPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    public void validarPaginaReadequacaoTHAB() {
        driverQA.waitPageLoad("claro/pt/cart?THAB=true", 10);

        Assert.assertNotNull(driverQA.findElement("controle-antecipado", "id"));
    }

    public void clicarEuQuero() {
        driverQA.JavaScriptClick("buttonCheckoutThab", "id");
    }
}
