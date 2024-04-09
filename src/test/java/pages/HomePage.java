package pages;

import support.DriverQA;

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
        String campoTelefone = "(//input[@name='telephone'])[1]";

        driverQA.waitSeconds(8);
        driverQA.sendKeys(campoTelefone, "id", msisdn);
    }

    public void validarClienteMeusPedidos(String cliente) {
        String botaoOlaEcomm = "(//button[contains(text(), '" + cliente + "')])[1]";
        driverQA.findElements(botaoOlaEcomm, "id");
    }

    public void acessarPDP(String idPlano) {
        driverQA.JavaScriptClick("txt-mais-detalhes-" + idPlano, "id");
    }

    public void selecionarPlano(String idPlano) {
        driverQA.JavaScriptClick("btn-eu-quero-" + idPlano, "id");
    }

    public void clicaBotaoEntrar() {
//        driverQA.JavaScriptClick("btn-entrar", "id");
        driverQA.JavaScriptClick("//button[@class='mdn-Menu-main-event mdn-Menu-main-action mdn-Button mdn-Button--iconText mdn-Button--primaryInverse mdn-Button--sm  gtm-link-event']", "xpath");
    }

    public void validarHomePage() {
        driverQA.waitPageLoad(ComumPage.urlAmbiente, 20);
    }
}