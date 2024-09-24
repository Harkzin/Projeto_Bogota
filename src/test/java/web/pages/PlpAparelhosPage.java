package web.pages;


import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.models.Product;
import web.support.utils.DriverWeb;

import java.util.Locale;

import static web.pages.ComumPage.*;

@Component
@ScenarioScope
public class PlpAparelhosPage {

    private final DriverWeb driverWeb;
    private final CartOrder cartOrder;

    @Autowired
    public PlpAparelhosPage(DriverWeb driverWeb, CartOrder cartOrder) {
        this.driverWeb = driverWeb;
        this.cartOrder = cartOrder;
    }

    public void validarPlpAparelhos() {
        driverWeb.waitPageLoad("/celulares", 10);
    }

    public void validarCardAparelho(Product device) {
        //Valida nome
        if (!device.getName().isEmpty()) {
            validateElementText(device.getName(), driverWeb.findElement("//*[@id='btn-eu-quero-" + device.getCode() + "']/../..//h2", "xpath"));
        }

        //Valida preço base "De"
        WebElement fullPrice = driverWeb.findElement("//*[@id='btn-eu-quero-" + device.getCode() + "']/..//dt[contains(@class, 'mdn-Price-prefix')]", "xpath");
        Assert.assertTrue("Valor sem desconto (De) igual ao configurado", fullPrice.getText().contains(String.format(Locale.GERMAN, "%,d", ((int) device.getPrice()) - 10)));
        Assert.assertTrue("Valor sem desconto (De) é exibido", fullPrice.isDisplayed());
    }

    public void clicaBotaoEuQuero(String id) {
        driverWeb.javaScriptClick("btn-eu-quero-" + id, "id");
    }
}