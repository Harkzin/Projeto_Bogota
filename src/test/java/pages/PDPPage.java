package pages;

import io.cucumber.java.pt.Então;
import support.DriverQA;

public class PDPPage {
    private final DriverQA driverQA;

    public PDPPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    @Então("é direcionado para a PDP do plano")
    public void validarPDP(){

    }

    public void clicarEuQuero() {
        driverQA.JavaScriptClick("btn-eu-quero", "id");
    }
}