package web.support.config;

import io.cucumber.core.gherkin.Feature;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import web.support.utils.DriverQA;

public class Hooks {

    @Autowired
    private DriverQA driverQA;

    @Autowired
    public Hooks(DriverQA driverQA) {
        this.driverQA = driverQA;
    }

    @After (order = 3)
    public void alterName(Scenario scenario) {
        if (System.getProperty("api", "false").equals("false")) {
            if(driverQA.getPlataformName().toString().equals("ANDROID")){
                scenario.attach("", "text/plain", "ANDROID");
            }else if(driverQA.getPlataformName().toString().equals("IOS")){
                scenario.attach("iOS", "text/plain", "IOS");
            }else{
                scenario.attach("Plataforma: Windows", "text/plain", "WINDOWS");
            }
        }
    }

    @After(order = 2)
    public void printScreen(Scenario scenario) {
        if (System.getProperty("api", "false").equals("false")) {
            byte[] screenshot = (((TakesScreenshot) driverQA.getDriver()).getScreenshotAs(OutputType.BYTES));
            scenario.attach(screenshot, "image/png", "screenshot");
        }
    }



    @After(order = 1)
    public void closeBrowser() {
        if (System.getProperty("api", "false").equals("false")) {
            driverQA.getDriver().quit();
        }
    }
}