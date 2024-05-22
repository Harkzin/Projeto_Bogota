package pages;

import support.CartOrder;
import support.DriverQA;

public class PlpPlanosPage {
    private final DriverQA driver;
    private final CartOrder cartOrder;

    public PlpPlanosPage(DriverQA stepDriver, CartOrder cartOrder) {
        driver = stepDriver;
        this.cartOrder = cartOrder;
    }

    // Card
    public static String xpathTituloControlePLP = "//*[@class='mdn-Heading mdn-Heading--lg'][text()='O plano básico para o dia a dia!']";
    public static String xpathTituloPosHome = "//*[@class='mensagem-plano'][text()='Para quem é ultra conectado']";

    public void selecionarCardControle(String idPlano) {

        //driver.waitElementVisibility(xpathTituloControlePLP, "xpath");
        //driver.waitElementVisibility("btn-eu-quero-" + idPlano + "", "id");
        driver.JavaScriptClick("btn-eu-quero-" + idPlano + "", "id");
    }
}
