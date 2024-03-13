package pages;

import org.junit.Assert;
import support.DriverQA;
import support.Hooks;

public class PDPPage {
    private final DriverQA driver;

    public PDPPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public void EuQueroPDP(String idPlano) {
        String xpathPDP = "//p[@class='product-page-product-description h4']";
        Assert.assertEquals("Plano Claro Controle - O básico para o dia a dia", driver.getText(xpathPDP, "xpath"));
        driver.JavaScriptClick("btn-eu-quero-" + idPlano + "", "id");
    }
}