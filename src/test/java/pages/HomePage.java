package pages;

import support.DriverQA;

public class HomePage {
    private final DriverQA driverQA;

    public HomePage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    public void acessarLojaHome() {
        driverQA.openURL(ComumPage.urlAmbiente);
        driverQA.waitPageLoad(ComumPage.urlAmbiente, 20);
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        String campoTelefone = "(//input[@name='telephone'])[1]";

        driverQA.waitSeconds(8);
        driverQA.sendKeys(campoTelefone, "id", msisdn);
    }

    public void validarClienteMeusPedidos(String cliente) {
        String botaoOlaEcomm = "(//button[contains(text(), '" + cliente + "')])[1]";
        driverQA.findListElements(botaoOlaEcomm, "id");
    }

    public void acessarURLRentabilizacao() {
        driverQA.openURL("https://accstorefront.cokecxf-commercec1-" + System.getProperty("env", "S6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com/claro/pt/offer-plan/externalUri?offerPlanId=17218&coupon=09fd42fef86f8e0ea86d085f64a3696be6b4e91307c59913b172ddb5f60d0aaa&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE");
    }

    public void selecionarPDPCard(String idPlano) {
        driverQA.JavaScriptClick("txt-mais-detalhes-" + idPlano, "id");
    }

    public void selecionarPlanoHome(String idPlano) {
        driverQA.JavaScriptClick("btn-eu-quero-" + idPlano, "id");
    }
}