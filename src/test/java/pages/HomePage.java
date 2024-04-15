package pages;

import support.DriverQA;

import static pages.ComumPage.planId;

public class HomePage {
    private final DriverQA driverQA;

    public HomePage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    public void acessarLojaHome() {
        driverQA.getDriver().get(ComumPage.urlAmbiente);
        driverQA.waitPageLoad(ComumPage.urlAmbiente, 20);
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        String campoTelefone = "txt-telefone";

        driverQA.actionSendKeys(campoTelefone, "id", msisdn);
    }

    public void acessarPDP(String idPlano) {
        driverQA.JavaScriptClick("txt-mais-detalhes-" + idPlano, "id");
        planId = idPlano;
    }

    public void selecionarPlano(String idPlano) {
        driverQA.JavaScriptClick("btn-eu-quero-" + idPlano, "id");
    }

    public void clicaBotaoEntrar() {
        driverQA.JavaScriptClick("btn-entrar", "id");
    }

    public void validarHomePage() {
        driverQA.waitPageLoad(ComumPage.urlAmbiente, 20);
    }
}