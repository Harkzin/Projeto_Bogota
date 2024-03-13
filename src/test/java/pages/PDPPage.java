package pages;

import org.junit.Assert;
import support.DriverQA;
import support.Hooks;

public class PDPPage {
    private final DriverQA driver;

    public PDPPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public void EuQueroPDP() {
        String xpathPDP = "//p[@class='product-page-product-description h4']";
        Assert.assertEquals("Plano Claro Controle - O básico para o dia a dia", driver.getText(xpathPDP, "xpath"));
        driver.JavaScriptClick("(//button[@data-automation='eu-quero'])[2]", "xpath");
    }
}