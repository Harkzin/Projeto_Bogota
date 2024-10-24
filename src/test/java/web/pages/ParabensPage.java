package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverWeb driverWeb;

    @Autowired
    public ParabensPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private WebElement temporizadorPix;
    private WebElement qrCodePix;
    private WebElement copiarCodigoPix;

    public void validarPaginaParabens() {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
    }

    public void clicarOkEntendi() {
        driverWeb.javaScriptClick("btn-entendi-modal-abr", "id");
    }

    public void validarCamposPedido() {
        //TODO ECCMAUT-351
    }

    public void validarCamposPedidoPix() {
        //TODO Validar valor do pix é o mesmo valor do aparelho
        qrCodePix = driverWeb.findElement("//*[@id=\"pix-payment-instructions\"]/div[2]/div[2]/img", "xpath");
        temporizadorPix = driverWeb.findElement("//*[@id=\"pix-payment-instructions\"]/div[2]/div[1]/ul/li[1]/div[3]/div[1]/p", "xpath");
        copiarCodigoPix = driverWeb.findElement("//*[@id=\"pix-payment-instructions\"]/div[2]/div[1]/ul/li[1]/button", "xpath");

        driverWeb.waitElementVisible(qrCodePix, 10);
        driverWeb.waitElementVisible(copiarCodigoPix, 10);
        driverWeb.waitElementVisible(temporizadorPix, 10);
    }
}