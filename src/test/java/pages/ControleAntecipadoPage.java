package pages;

import support.DriverQA;

public class ControleAntecipadoPage {
    private DriverQA driver;

    public ControleAntecipadoPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String TituloControleAntecipado = "controle-antecipado";
    private String ChkTermos = "chk-termos";

    public void PlanoControleAntecipadoExiste() {
        driver.waitElement(TituloControleAntecipado, "id");
    }

    public void marcarCheckboxTermoTHAB() {
        driver.JavaScriptClick(ChkTermos, "id");
    }
}
