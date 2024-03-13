package pages;

import support.DriverQA;

public class ControleAntecipadoPage {
    private DriverQA driver;

    public ControleAntecipadoPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String idTituloControleAntecipado = "controle-antecipado";
    public static String xpathEuQueroTHAB = "//a[@data-automation='eu-quero']";
    private String xpathChkTermosTHAB = "(//*[@class='mdn-Checkbox-label'])";

    public void PlanoControleAntecipadoExiste() {
        driver.waitElement(idTituloControleAntecipado, "id");
    }

    public void marcarCheckboxTermoTHAB() {
        driver.JavaScriptClick(xpathChkTermosTHAB, "xpath");
    }
}
