package support.config;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import support.utils.DriverQA;

import java.util.ArrayList;
import java.util.Collection;

public class Hooks {

    private final DriverQA driverQA;

    @Autowired
    public Hooks(DriverQA driverQA) {
        this.driverQA = driverQA;
    }

    public static Collection<String> tagScenarios = new ArrayList<>();

    @Before
    public void getTags(Scenario scenario) {
        tagScenarios = scenario.getSourceTagNames();
    }

    @After(order = 2)
    public void printScreen(Scenario scenario) {
        byte[] screenshot = (((TakesScreenshot) driverQA.getDriver()).getScreenshotAs(OutputType.BYTES));
        scenario.attach(screenshot, "image/png", "screenshot");
    }

    @After(order = 1)
    public void closeBrowser() {
        driverQA.getDriver().quit();
    }
}