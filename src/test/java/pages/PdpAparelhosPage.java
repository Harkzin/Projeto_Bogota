package pages;

import org.junit.Assert;
import support.DriverQA;

import static pages.ComumPage.Cart_deviceId;

public class PdpAparelhosPage {
    private final DriverQA driverQA;

    public PdpAparelhosPage(DriverQA stepdriver) {
        driverQA = stepdriver;
    }

    public void validarPaginaPDPAparelho() {
        driverQA.waitPageLoad("/0000000000000" + Cart_deviceId, 5);
    }

    public void selecionarCorAparelho(String id) {
        Cart_deviceId = id;
        driverQA.JavaScriptClick("//a[contains(@href, '" + id + "')]", "xpath");
    }

    public void validarProdutoSemEstoque() {
        Assert.assertEquals("Produto Esgotado", driverQA.findElement("produto-esgotado", "id").getText().trim());
    }
}