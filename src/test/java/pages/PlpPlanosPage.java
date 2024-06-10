package pages;

import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
public class PlpPlanosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public PlpPlanosPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    // Card
    public static String xpathTituloControlePLP = "//*[@class='mdn-Heading mdn-Heading--lg'][text()='O plano básico para o dia a dia!']";
    public static String xpathTituloPosHome = "//*[@class='mensagem-plano'][text()='Para quem é ultra conectado']";

    public void selecionarCardControle(String idPlano) {

        //driver.waitElementVisibility(xpathTituloControlePLP, "xpath");
        //driver.waitElementVisibility("btn-eu-quero-" + idPlano + "", "id");
        driverQA.javaScriptClick("btn-eu-quero-" + idPlano + "", "id");
    }
}
