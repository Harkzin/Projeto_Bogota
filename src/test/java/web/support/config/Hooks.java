package web.support.config;

<<<<<<< HEAD
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
=======
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import massasController.ConsultaCPFMSISDN;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import web.models.CartOrder;
>>>>>>> Projeto_Bogota_o/stage-bogota
import web.support.utils.DriverWeb;

public class Hooks {

    private final DriverWeb driverWeb;
    private final CartOrder cartOrder;

    @Autowired
    public Hooks(DriverWeb driverWeb, CartOrder cartOrder) {
        this.driverWeb = driverWeb;
        this.cartOrder = cartOrder;
    }

    @After(order = 3, value = "@Portabilidade or @Migracao or @Troca or @DepPort")
    public void atualizarStatusMassa(Scenario scenario) {
        ConsultaCPFMSISDN.restaurarStatusPosCenario(scenario, driverWeb.getDriver().getCurrentUrl(), cartOrder.hasErrorPasso1);
    }

    @After(order = 2, value = "@Web")
    public void printScreen(Scenario scenario) {
        //Platform
        scenario.attach(driverWeb.getPlatformName().toString(), "text/plain", "Platform");

        //Print
        byte[] screenshot = (((TakesScreenshot) driverWeb.getDriver()).getScreenshotAs(OutputType.BYTES));
        scenario.attach(screenshot, "image/png", "Screenshot");
    }

<<<<<<< HEAD
    // @After(order = 1)
    // public void closeBrowser() {
    //     if (System.getProperty("api", "false").equals("false")) {
    //         driverWeb.getDriver().quit();
    //     }
    // }
}
=======
    @After(order = 1, value = "@Web")
    public void closeBrowser() {
        driverWeb.getDriver().quit();
    }
}
>>>>>>> Projeto_Bogota_o/stage-bogota
