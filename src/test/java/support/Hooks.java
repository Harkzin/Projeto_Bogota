package support;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.ArrayList;
import java.util.Collection;

public class Hooks extends BaseSteps {

    public static Collection<String> tagScenarios = new ArrayList<>();

    @After(order = 3)
    public void printScreen(Scenario scenario) throws InterruptedException {
        Thread.sleep(1000);
        byte[] screenshot = (((TakesScreenshot) driver.getDriver()).getScreenshotAs(OutputType.BYTES));
        scenario.embed(screenshot, "image/png");
    }

//    @After(order = 1)
//    public void closeBrowser() {
//        driver.quit();
//    }

    @Before
    public void getTags(Scenario scenario){
        tagScenarios = scenario.getSourceTagNames();
    }

    @After("@fecharGuia")
    public void fecharGuia() {
        driver.close();
    }
}
