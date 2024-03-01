package pages;

import org.junit.Assert;
import support.DriverQA;
import support.Hooks;

public class PDPPage {
    private DriverQA driver;
    public PDPPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public void EuQueroPDP() {
        String xpathPDP = "//p[@class='product-page-product-description h4']";
        Assert.assertTrue(("Plano Claro Controle - O básico para o dia a dia".equals(driver.getText(xpathPDP, "xpath"))));
        driver.JavaScriptClick("(//button[@data-automation='eu-quero'])[2]", "xpath");
    }
    }
