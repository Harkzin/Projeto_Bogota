package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

import java.util.Map;

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

    private WebElement abaPortabilidade;
    private WebElement btnConfirmarDependente;
    private WebElement abaNumeroNovo;
    private WebElement btnExcluir;



    private final Map<Integer,String> mapBtnConfirmarDependente = Map.of(
            1, "//ul[@id='js-dependentList']//button//span[contains(text(),'Confirmar')]",
            2, "//ul[@id='js-dependentList']//button//span[contains(text(),'Confirmar')]",
            3, "//ul[@id='js-dependentList']//button//span[contains(text(),'Confirmar')]"
    ) ;
    private final Map<Integer,String> mapBtnExcluir = Map.of(
            1, "//ul[@id='js-dependentList']//button//span[contains(text(),'Excluir')]",
            2, "(//ul[@id='js-dependentList']//button//span[contains(text(),'Excluir')])[2]",
            3, "(//ul[@id='js-dependentList']//button//span[contains(text(),'Excluir')])[3]"
    );
    private final Map<Integer,String> mapAbaNumeroNovo = Map.of(
            1, "//ul[@id='js-dependentList']//li[contains(text(),'Novo número')]",
            2, "(//ul[@id='js-dependentList']//li[contains(text(),'Novo número')])[2]",
            3, "(//ul[@id='js-dependentList']//li[contains(text(),'Novo número')])[3]"
    );
    private final Map<Integer,String> mapAbaPortabilidade = Map.of(
            1,"//ul[@id='js-dependentList']//li[contains(text(),'Portabilidade')]",
            2,"(//ul[@id='js-dependentList']//li[contains(text(),'Portabilidade')])[2]",
            3,"(//ul[@id='js-dependentList']//li[contains(text(),'Portabilidade')])[3]"
    );

    private WebElement btnAdicionarDependente;
    private WebElement btnSeguirSemDependente;

    public void validarPaginaDependentes() {
        driverQA.waitPageLoad("/dependents/claroDependents", 15);
        btnAdicionarDependente = driverQA.findElement("//button[contains(text(),'Adicionar Dependente')]","xpath");
        btnSeguirSemDependente = driverQA.findElement("//button[contains(text(),'Seguir sem dependentes')]","xpath");

        Assert.assertTrue(btnAdicionarDependente.isDisplayed());
        Assert.assertTrue(btnSeguirSemDependente.isDisplayed());
    }

    public void clicarSeguirSemDependentes() {
        driverQA.javaScriptClick(btnSeguirSemDependente);
    }

    public void clicarAdicionarDependente(int dependente) {
        driverQA.javaScriptClick("//div[@class='mdn-Row']//div//button", "xpath");
        validarBotoesDependente(dependente);
    }


    private void validarBotoesDependente(int dependente) {
        abaPortabilidade = driverQA.findElement(mapAbaPortabilidade.get(dependente),"xpath");
        btnConfirmarDependente = driverQA.findElement(mapBtnConfirmarDependente.get(dependente), "xpath");
        abaNumeroNovo = driverQA.findElement(mapAbaNumeroNovo.get(dependente),"xpath");
        btnExcluir = driverQA.findElement(mapBtnExcluir.get(dependente), "xpath");

        driverQA.waitElementVisible(abaPortabilidade,10);

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
        driverQA.javaScriptClick(btnConfirmarDependente);
        driverQA.waitElementInvisible(btnConfirmarDependente,5);
        //TODO validação de valores finais, adicionais e unitário de dependentes (conversar com Gustavo)
    }

    public void adicionarNovoNumeroDependente() {
        driverQA.javaScriptClick(abaNumeroNovo);
    }

    public void clicarAdicionarOutroDependente(int dependente) {
        driverQA.javaScriptClick("//div[@class='addingMoreDependent']//button","xpath");
        validarBotoesDependente(dependente);
    }

    public void clicarContinuar() {
        driverQA.javaScriptClick("//div[@class='js-dependentContent-items']//button[contains(text(),'Continuar')]","xpath");
    }
}
