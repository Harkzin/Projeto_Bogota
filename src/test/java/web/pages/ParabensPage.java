package web.pages;

import io.cucumber.spring.ScenarioScope;
import massasController.ConsultaCPFMSISDN;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

import static org.junit.Assert.assertTrue;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverWeb driverWeb;

    @Autowired
    public ParabensPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPaginaParabens() {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
    }

    public void validarPaginaParabensPix() {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
        //TODO Validar valor do pix é o mesmo valor do aparelho
        WebElement qrCodePix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[2]/img");
        WebElement temporizadorPix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[1]/ul/li[1]/div[3]/div[1]/p");
        WebElement copiarCodigoPix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[1]/ul/li[1]/button");

        driverWeb.waitElementVisible(temporizadorPix, 10);
        assertTrue(qrCodePix.isDisplayed());
        assertTrue(copiarCodigoPix.isDisplayed());
    }

    public void clicarOkEntendi() {
        driverWeb.javaScriptClick("btn-entendi-modal-abr", "id");
    }

    public void marcarMassaQueimada() {
        ConsultaCPFMSISDN.marcarComoQueimada();
    }

    public void validarCamposPedido() {
        //TODO ECCMAUT-351
    }
}