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

    public void validarRedirecionamentoProduto() {
        driverQA.waitPageLoad("/000000000000018061", 5);
    }

    public void selecionarCorAparelho() {
        imgCorProduto = driverQA.findElement("img-cor-do-produto-1", "id");
        driverQA.JavaScriptClick(imgCorProduto.findElement(By.tagName("img")));
    }

    public void validarProdutoEstoque() {
        produtoEstoque = driverQA.findElement("//h2[contains(text(),\"Produto Esgotado\")]", "xpath");
        driverQA.waitElementVisibility(produtoEstoque, 15);
    }
}
