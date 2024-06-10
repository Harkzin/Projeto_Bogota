package pages;

import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.Common;
import support.utils.DriverQA;

@Component
public class HomePage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public HomePage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void acessarLojaHome() {
        driverQA.getDriver().get(Common.urlAmbiente);
        driverQA.waitPageLoad(Common.urlAmbiente, 20);
    }

    public void validarHomePage() {
        driverQA.waitPageLoad(Common.urlAmbiente, 20);
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        driverQA.actionSendKeys("txt-telefone", "id", msisdn);
    }

    public void acessarPdpPlano(String id) {
        driverQA.javaScriptClick("lnk-mais-detalhes-" + id, "id");
    }

    public void selecionarPlano(String id) {
        driverQA.javaScriptClick("btn-eu-quero-" + id, "id");
    }

    public void clicaBotaoEntrar() {
        driverQA.javaScriptClick("btn-entrar", "id");
    }
}