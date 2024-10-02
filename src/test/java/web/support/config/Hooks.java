package web.support.config;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import web.support.utils.DriverWeb;

public class Hooks {

    private final DriverWeb driverWeb;

    @Autowired
    public Hooks(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    @After(order = 2)
    public void printScreen(Scenario scenario) {
        if (System.getProperty("api", "false").equals("false")) {
            //Platform
            scenario.attach(driverWeb.getPlatformName().toString(), "text/plain", "Platform");

            //Print
            byte[] screenshot = (((TakesScreenshot) driverWeb.getDriver()).getScreenshotAs(OutputType.BYTES));
            scenario.attach(screenshot, "image/png", "screenshot");
        }
    }

    @After(order = 1)
    public void closeBrowser() {
        if (System.getProperty("api", "false").equals("false")) {
            driverWeb.getDriver().quit();
        }
    }
}