package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
@ScenarioScope
public class PdpAparelhosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public PdpAparelhosPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void validarPaginaPDPAparelho(String id) {
        driverQA.waitPageLoad(id, 5);
    }

    public void selecionarCorAparelho(String id) {
        driverQA.javaScriptClick("//a[contains(@href, '" + id + "')]", "xpath");
    }

    public void validarProdutoSemEstoque() {
        Assert.assertEquals("Produto Esgotado", driverQA.findElement("produto-esgotado", "id").getText().trim());
    }
}