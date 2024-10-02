package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

@Component
@ScenarioScope
public class ClaroClubePage {

    private final DriverWeb driverWeb;

    @Autowired
    public ClaroClubePage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private WebElement continuar;
    private WebElement phone;

    public void validarPaginaClaroClube() {
        driverWeb.waitPageLoad("/login/claro-clube", 10);

        phone = driverWeb.findElement("phone", "id");
        continuar = driverWeb.findElement("//*[@id='claro-clube-form']/button", "xpath");

        Assert.assertTrue(phone.isDisplayed());
        Assert.assertFalse(continuar.isEnabled());
    }

    public void preencheTelefone(String phone) {
        driverWeb.sendKeysLogin(this.phone, phone);
    }

    public void clicarBotaoContinuarClaroClube() {
        driverWeb.javaScriptClick(continuar);
    }
}