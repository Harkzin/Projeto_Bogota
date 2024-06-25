package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
@ScenarioScope
public class DependentesPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public DependentesPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private WebElement btnConfirmarDependente;
    private WebElement btnExcluir;
    private WebElement abaNumeroNovo;
    private WebElement abaPortabilidade;
    private WebElement btnAdicionarDependente;
    private WebElement btnSeguirSemDependente;

    public void validarPaginaDependentes() {
        driverQA.waitPageLoad("/dependents/claroDependents", 15);
        btnAdicionarDependente = driverQA.findElement("//button[contains(text(),'Adicionar Dependente')]","xpath");
        btnSeguirSemDependente = driverQA.findElement("//button[contains(text(),'Seguir sem dependentes')]","xpath");

        Assert.assertTrue(btnAdicionarDependente.isDisplayed());
        Assert.assertTrue(btnSeguirSemDependente.isDisplayed());
    }

    public void clicarAdicionarDependente() {
        driverQA.javaScriptClick("//div[@class='mdn-Row']//div//button", "xpath");
        validarBotoesDependente();
    }


    private void validarBotoesDependente() {
        abaPortabilidade = driverQA.findElement("//ul[@id='js-dependentList']//li[contains(text(),'Portabilidade')]","xpath");
        btnConfirmarDependente = driverQA.findElement("//ul[@id='js-dependentList']//button//span[contains(text(),'Confirmar')]", "xpath");
        abaNumeroNovo = driverQA.findElement("//ul[@id='js-dependentList']//li[contains(text(),'Novo número')]","xpath");
        btnExcluir = driverQA.findElement("//ul[@id='js-dependentList']//button//span[contains(text(),'Excluir')]", "xpath");

        driverQA.waitElementVisibility(abaPortabilidade,10);

        Assert.assertTrue(abaNumeroNovo.isDisplayed());
        Assert.assertTrue(abaPortabilidade.isDisplayed());
        Assert.assertTrue(abaPortabilidade.getAttribute("class").contains("active"));
        Assert.assertTrue(btnConfirmarDependente.isDisplayed());
        Assert.assertTrue(btnExcluir.isDisplayed());
    }

    public void inserirNumeroDependentes(String numero) {
        driverQA.actionSendKeys("phone_0", "id", numero);
    }

    public void clicarConfirmarDependente() {
        driverQA.javaScriptClick("//ul[@id='js-dependentList']//li//div//div//div//button[2]", "xpath");
        driverQA.waitElementInvisibility(btnConfirmarDependente,5);
        //TODO validação de valores finais, adicionais e unitário de dependentes (conversar com Gustavo)
    }

    public void clicarContinuar() {
        driverQA.javaScriptClick("//div[@class='js-dependentContent-items']//button[contains(text(),'Continuar')]","xpath");
    }

    public void adicionarNovoNumeroDependente() {
        driverQA.javaScriptClick(abaNumeroNovo);
    }

}
