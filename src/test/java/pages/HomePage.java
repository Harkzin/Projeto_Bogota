package pages;

import support.CartOrder;
import support.Common;
import support.DriverQA;

import static support.Common.Cart_planId;

public class HomePage {
    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public HomePage(DriverQA stepDriver, CartOrder cartOrder) {
        driverQA = stepDriver;
        this.cartOrder = cartOrder;
    }

    public void acessarLojaHome() {
        driverQA.getDriver().get(Common.urlAmbiente);
        driverQA.waitPageLoad(Common.urlAmbiente, 20);
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        String campoTelefone = "txt-telefone";

        driverQA.actionSendKeys(campoTelefone, "id", msisdn);
    }

    public void acessarPdpPlano(String idPlano) {
        driverQA.JavaScriptClick("lnk-mais-detalhes-" + idPlano, "id");
        Cart_planId = idPlano;
    }

    public void selecionarPlano(String idPlano) {
        driverQA.JavaScriptClick("btn-eu-quero-" + idPlano, "id");
    }

    public void clicaBotaoEntrar() {
        driverQA.JavaScriptClick("btn-entrar", "id");
    }

    public void validarHomePage() {
        driverQA.waitPageLoad(Common.urlAmbiente, 20);
    }
}