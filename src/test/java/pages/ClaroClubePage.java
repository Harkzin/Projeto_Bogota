package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import support.utils.DriverQA;

@Component
public class ClaroClubePage {
    private final DriverQA driverQA;

    public ClaroClubePage(DriverQA driverQA) { //Spring Autowired
        this.driverQA = driverQA;
    }

    private WebElement continuar;
    private WebElement phone;

    public void validarPaginaClaroClube() {
        driverQA.waitPageLoad("/login/claro-clube", 10);

        phone = driverQA.findElement("phone", "id");
        continuar = driverQA.findElement("//*[@id='claro-clube-form']/button", "xpath");

        Assert.assertTrue(phone.isDisplayed());
        Assert.assertFalse(continuar.isEnabled());
    }

    public void preencheTelefone(String phone) {
        driverQA.actionSendKeys(this.phone, phone);
    }

    public void clicarBotaoContinuarClaroClube() {
        driverQA.javaScriptClick(continuar);
    }
}