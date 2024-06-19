package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import support.DriverQA;

import static pages.ComumPage.Cart_planId;

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
        driverQA.waitPageLoad(ComumPage.urlAmbiente, 20);
    }

    public void acessarMenuCelulares() {
        WebElement tabCelular = driverQA.findElement("tab-aparelhos", "id");
        driverQA.JavaScriptClick(tabCelular.findElement(By.tagName("p")));
    }
}