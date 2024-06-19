package pages;

import org.junit.Assert;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
public class PdpAparelhosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public PdpAparelhosPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void validarPaginaPDPAparelho(String id) {
        driverQA.waitPageLoad("/0000000000000" + id, 5);
    }

    public void selecionarCorAparelho(String id) {
        driverQA.javaScriptClick("//a[contains(@href, '" + id + "')]", "xpath");
    }

    public void validarProdutoSemEstoque() {
        Assert.assertEquals("Produto Esgotado", driverQA.findElement("produto-esgotado", "id").getText().trim());
    }
}