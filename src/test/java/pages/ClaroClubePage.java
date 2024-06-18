package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import support.DriverQA;

public class ClaroClubePage {
    private final DriverQA driverQA;

    public ClaroClubePage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private WebElement continuar;
    private WebElement phone;

    public void validarPaginaClaroClube() {
        driverQA.waitPageLoad("/login/claro-clube", 10);

        phone = driverQA.findElement("phone", "id");
        continuar = driverQA.findElement("//*[@id=\"claro-clube-form\"]/button", "xpath");

        Assert.assertTrue(phone.isDisplayed());

        Assert.assertFalse(continuar.isEnabled());
    }

    public void preencheTelefone(String phone) {
        driverQA.actionSendKeys(this.phone, phone);
    }

    public void clicarBotaoContinuarClaroClube() {
        driverQA.JavaScriptClick(continuar);
    }

}
