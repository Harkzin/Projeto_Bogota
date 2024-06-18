package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import support.DriverQA;

import static pages.ComumPage.Cart_hasDevice;
import static pages.ComumPage.Cart_processType;
import static pages.ComumPage.ProcessType.ACQUISITION;

public class PdpAparelhosPage {
    private final DriverQA driverQA;

    private WebElement botaoComprar;
    private WebElement produtoEstoque;
    private WebElement imgCorProduto;

    public PdpAparelhosPage(DriverQA stepdriver){driverQA = stepdriver;}

    public void validarRedirecionamentoParaPdpAparelhos(String aparelho) {
        driverQA.waitPageLoad("/0000000000000" + aparelho, 10);
    }

    public void selecionarCorAparelho(String idCorAparelho) {
        imgCorProduto = driverQA.findElement("img-cor-do-produto-"+idCorAparelho+"", "id");
        driverQA.JavaScriptClick(imgCorProduto.findElement(By.tagName("img")));
    }

    public void validarProdutoEstoque() {
        produtoEstoque = driverQA.findElement("//h2[contains(text(),\"Produto Esgotado\")]", "xpath");
        driverQA.waitElementVisibility(produtoEstoque, 15);
    }

    public void marcarQueroUmaLinhaNovaDaClaro(){
        Cart_processType = ACQUISITION;
        driverQA.JavaScriptClick("rdn-acquisition", "id");
    }

    public void selecionarClaroControle(){
        driverQA.findElements("//select/option[contains(text(), \"Claro Controle\")]", "xpath");
    }

    public void selecionarPlano(){
        driverQA.waitElementToBeClickable(driverQA.findElement("//button[@data-analytics-event-label=\"20GB:plano-selecionado\"]", "xpath"), 10);
        driverQA.JavaScriptClick("//button[@data-analytics-event-label=\"20GB:plano-selecionado\"]","xpath");
    }

    public void clicarBotaoComprar(){
        botaoComprar = driverQA.findElement("btn-eu-quero-17216", "id");
        driverQA.waitElementToBeClickable(botaoComprar, 10);
        driverQA.JavaScriptClick(botaoComprar);
        Cart_hasDevice = true;
    }
}
