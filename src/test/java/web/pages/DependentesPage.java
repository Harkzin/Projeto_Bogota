package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

import java.util.Map;

@Component
@ScenarioScope
public class DependentesPage {

    private final DriverWeb driverWeb;

    @Autowired
    public DependentesPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private WebElement abaPortabilidade;
    private WebElement btnConfirmarDependente;
    private WebElement abaNumeroNovo;
    private WebElement btnExcluir;
    private WebElement btnAdicionarDependente;
    private WebElement btnSeguirSemDependente;
    private WebElement txtTelefonePortabilidade;


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
            1, "//ul[@id='js-dependentList']//li[contains(text(),'Portabilidade')]",
            2, "(//ul[@id='js-dependentList']//li[contains(text(),'Portabilidade')])[2]",
            3, "(//ul[@id='js-dependentList']//li[contains(text(),'Portabilidade')])[3]"
    );

    private final Map<Integer,String> mapCampoTelefonePortabilidade = Map.of(
            1, "txt-telefone-dep0",
            2, "txt-telefone-dep2",
            3, "txt-telefone-dep3"
    );

    public void validarPaginaDependentes() {
        driverWeb.waitPageLoad("/dependents/claroDependents", 15);
        btnAdicionarDependente = driverWeb.findElement("//button[contains(text(),'Adicionar Dependente')]","xpath");
        btnSeguirSemDependente = driverWeb.findElement("//button[contains(text(),'Seguir sem dependentes')]","xpath");

        Assert.assertTrue(driverWeb.findElement("//span[@class='mdn-Icon-comunidade mdn-Icon--md steps-icon']","xpath").isDisplayed());
        Assert.assertTrue(btnAdicionarDependente.isDisplayed());
        Assert.assertTrue(btnSeguirSemDependente.isDisplayed());
    }

    public void clicarSeguirSemDependentes() {
        driverWeb.javaScriptClick(btnSeguirSemDependente);
    }

    public void clicarAdicionarDependente(int dependente) {
        driverWeb.javaScriptClick("//div[@class='mdn-Row']//div//button", "xpath");
        validarBotoesDependente(dependente);
    }

    private void validarBotoesDependente(int dependente) {
        abaPortabilidade = driverWeb.findElement(mapAbaPortabilidade.get(dependente),"xpath");
        btnConfirmarDependente = driverWeb.findElement(mapBtnConfirmarDependente.get(dependente), "xpath");
        abaNumeroNovo = driverWeb.findElement(mapAbaNumeroNovo.get(dependente),"xpath");
        btnExcluir = driverWeb.findElement(mapBtnExcluir.get(dependente), "xpath");
        txtTelefonePortabilidade = driverWeb.findElement(mapCampoTelefonePortabilidade.get(dependente),"id");

        driverWeb.waitElementVisible(abaPortabilidade,10);
        Assert.assertTrue(abaNumeroNovo.isDisplayed());
        Assert.assertTrue(abaPortabilidade.isDisplayed());
        Assert.assertTrue(abaPortabilidade.getAttribute("class").contains("active"));
        Assert.assertTrue(btnConfirmarDependente.isDisplayed());
        Assert.assertTrue(btnExcluir.isDisplayed());
    }

    public void inserirNumeroDependentes(String numero) {
        driverWeb.sendKeys(txtTelefonePortabilidade,numero);
    }

    public void clicarConfirmarDependente() {
        driverWeb.javaScriptClick(btnConfirmarDependente);
        driverWeb.waitElementInvisible(btnConfirmarDependente,10);
        //TODO validação de valores finais, adicionais e unitário de dependentes (conversar com Gustavo)
    }

    public void adicionarNovoNumeroDependente() {
        driverWeb.javaScriptClick(abaNumeroNovo);
    }

    public void clicarAdicionarOutroDependente(int dependente) {
        driverWeb.javaScriptClick("//div[@class='addingMoreDependent']//button","xpath");
        validarBotoesDependente(dependente);
    }

    public void clicarContinuar() {
        driverWeb.javaScriptClick("//div[@class='js-dependentContent-items']//button[contains(text(),'Continuar')]","xpath");
    }
}