package web.pages;


import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.product.DeviceProduct;
import web.support.utils.DriverWeb;

import java.util.Locale;

import static org.junit.Assert.*;
import static web.pages.ComumPage.*;

@Component
@ScenarioScope
public class PlpAparelhosPage {

    private final DriverWeb driverWeb;

    @Autowired
    public PlpAparelhosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPlpAparelhos() {
        driverWeb.waitPageLoad("/celulares", 10);
    }

    public void validarCardAparelho(DeviceProduct device, String focusPlanName) {
        //Valida nome
        assertNotNull(device.getName());
        WebElement name = driverWeb.findByXpath(String.format("//*[@id='btn-eu-quero-%s']/../..//h2", device.getCode()));
        driverWeb.javaScriptScrollTo(name);
        validateElementText(device.getName(), name);

        //Valida "no plano" (focusPlan)
        WebElement focusPlan = driverWeb.findByXpath(String.format("//*[@id='btn-eu-quero-%s']/../div/span", device.getCode()));
        assertTrue("Nome do focusPlan diferente do configurado", focusPlan.getText().contains(focusPlanName));
        assertTrue("Nome do focusPlan não vísivel no card", focusPlan.isDisplayed());

        //Valida preço base "De"
        String fullPriceFormatted = String.format(Locale.GERMAN, "R$ %,d", (int) device.getPrice());
        WebElement fullPrice = driverWeb.findByXpath(String.format("//*[@id='btn-eu-quero-%s']/..//dt[contains(@class, 'mdn-Price-prefix')]", device.getCode()));
        assertTrue("Valor sem desconto (De) diferente do configurado", StringUtils.normalizeSpace(fullPrice.getText()).contains(fullPriceFormatted));
        assertTrue("Valor sem desconto (De) não exibido no card", fullPrice.isDisplayed());

        //Valida preço "por apenas"
        WebElement campaignPrice = driverWeb.findByXpath(String.format("//*[@id='btn-eu-quero-%s']/../div[2]/dl/dd", device.getCode()));
        //TODO bug API ECCMAUT-806 validateElementText(device.getFormattedCampaignPrice(false), campaignPrice);

        //Valida parcelamento
        WebElement installments = driverWeb.findByXpath(String.format("//*[@id='btn-eu-quero-%s']/../div[2]/dl/dt[2]", device.getCode()));
        String installmentsStr = String.format("%dx de %s", device.getInstallmentQuantity(), StringUtils.normalizeSpace(device.getFormattedInstallmentPrice()));
        //TODO bug API ECCMAUT-806 assertTrue("Quantidade de parcelas e valor diferente do configurado", StringUtils.normalizeSpace(installments.getText()).contains(installmentsStr));
        assertTrue("Parcelamento não exibido no card", installments.isDisplayed());
    }

    public void clicaBotaoEuQuero(String id) {
        driverWeb.javaScriptClick("btn-eu-quero-" + id, "id");
    }
}