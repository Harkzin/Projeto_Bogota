package pages;

import support.DriverQA;
import support.Hooks;

public class PLPPage {
    private final DriverQA driver;

    public PLPPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    // Card
    public static String xpathTituloControlePLP = "//*[@class='mdn-Heading mdn-Heading--lg'][text()='O plano básico para o dia a dia!']";
    public static String xpathTituloPosHome = "//*[@class='mensagem-plano'][text()='Para quem é ultra conectado']";

    public void selecionarCardControle(String idPlano) {

        driver.waitElement(xpathTituloControlePLP, "xpath");
        driver.waitElement("btn-eu-quero-" + idPlano + "", "id");
        driver.JavaScriptClick("btn-eu-quero-" + idPlano + "", "id");
    }
}
