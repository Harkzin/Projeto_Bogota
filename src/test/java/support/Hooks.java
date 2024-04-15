package support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.ArrayList;
import java.util.Collection;

public class Hooks extends BaseSteps {
    public static Collection<String> tagScenarios = new ArrayList<>();

    @Before
    public void getTags(Scenario scenario) {
        tagScenarios = scenario.getSourceTagNames();
    }

    @After(order = 2)
    public void printScreen(Scenario scenario) throws InterruptedException {
        Thread.sleep(1000);
        byte[] screenshot = (((TakesScreenshot) driverQA.getDriver()).getScreenshotAs(OutputType.BYTES));
        scenario.attach(screenshot, "image/png", "screenshot");
    }

    @After(order = 1)
    public void closeBrowser() {
        driverQA.getDriver().quit();
    }
}