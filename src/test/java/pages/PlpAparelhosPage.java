package pages;


import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.Product;
import support.utils.DriverQA;

import java.util.Locale;

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

    public void validarCardAparelho(Product device) {
        //Valida nome
        if (!device.getName().isEmpty()) {
            ComumPage.validateElementText(device.getName(), driverQA.findElement("//*[@id='btn-eu-quero-" + device.getCode() + "']/../..//h2", "xpath"));
        }

        //Valida preço base "De"
        WebElement price = driverQA.findElement("//*[@id='btn-eu-quero-" + device.getCode() + "']/..//dt[contains(@class, 'mdn-Price-prefix')]", "xpath");
        Assert.assertTrue("Valor sem desconto (De) igual ao configurado", price.getText().contains(String.format(Locale.GERMAN, "%,d", ((int) device.getPrice()) - 10)));
        Assert.assertTrue("Valor sem desconto (De) é exibido", price.isDisplayed());
    }

    public void clicaBotaoEuQuero(String id) {
        driverQA.javaScriptClick("btn-eu-quero-" + id, "id");
    }
}