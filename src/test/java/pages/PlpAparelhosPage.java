package pages;
import io.cucumber.java.pt.Entao;
import org.junit.Assert;
import support.DriverQA;

public class PlpAparelhosPage {

    private final DriverQA driverQA;

    public PlpAparelhosPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    public void validarRedirecionamentoParaPdpAparelhos() {
        driverQA.waitPageLoad("/smartphone", 5);
    }

    public void clicaBotaoEuQuero(String aparelho) {
        driverQA.waitElementToBeClickable(driverQA.findElement("btn-eu-quero-0000000000000" + aparelho, "id"), 10);
        driverQA.JavaScriptClick("btn-eu-quero-0000000000000" + aparelho, "id");
    }
}