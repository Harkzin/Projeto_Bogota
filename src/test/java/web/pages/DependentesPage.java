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
            1, "//*[@data-automation='lista-dependente']//*[@data-automation='btn-confirmar-dep']",
            2, "(//*[@data-automation='lista-dependente']//button)[4][@data-automation='btn-confirmar-dep']",
            3, "(//*[@data-automation='lista-dependente']//button)[6][@data-automation='btn-confirmar-dep']"
    ) ;
    private final Map<Integer,String> mapBtnExcluir = Map.of(
            1, "//*[@data-automation='lista-dependente']//*[@data-automation='btn-excluir-dep']",
            2, "(//*[@data-automation='lista-dependente']//button)[3][@data-automation='btn-excluir-dep']",
            3, "(//*[@data-automation='lista-dependente']//button)[5][@data-automation='btn-excluir-dep']"
    );
    private final Map<Integer,String> mapAbaNumeroNovo = Map.of(
            1, "//*[@data-automation='lista-dependente']//*[@data-automation='tab-novo-numero-dep']",
            2, "(//*[@data-automation='lista-dependente']//ul)[2]//*[@data-automation='tab-novo-numero-dep']",
            3, "(//*[@data-automation='lista-dependente']//ul)[3]//*[@data-automation='tab-novo-numero-dep']"
    );
    private final Map<Integer,String> mapAbaPortabilidade = Map.of(
            1, "//*[@data-automation='lista-dependente']//*[@data-automation='tab-portabilidade-dep']",
            2, "(//*[@data-automation='lista-dependente']//ul)[2]//*[@data-automation='tab-portabilidade-dep']",
            3, "(//*[@data-automation='lista-dependente']//ul)[3]//*[@data-automation='tab-portabilidade-dep']"
    );

    private final Map<Integer,String> mapCampoTelefonePortabilidade = Map.of(
            1, "//*[@data-automation='lista-dependente']//*[@data-automation='txt-telefone-dep']",
            2, "(//*[@data-automation='lista-dependente']//input)[2][@data-automation='txt-telefone-dep']",
            3, "(//*[@data-automation='lista-dependente']//input)[3][@data-automation='txt-telefone-dep']"
    );

    public void validarPaginaDependentes() {
        driverWeb.waitPageLoad("/dependents/claroDependents", 15);
        btnAdicionarDependente = driverWeb.findElement("btn-adicionar-dep","id");
        btnSeguirSemDependente = driverWeb.findElement("btn-seguir-sem-dep","id");

        Assert.assertTrue(driverWeb.findElement("//span[@class='mdn-Icon-comunidade mdn-Icon--md steps-icon']", "xpath").isDisplayed());
        Assert.assertTrue(btnAdicionarDependente.isDisplayed());
        Assert.assertTrue(btnSeguirSemDependente.isDisplayed());
    }

    public void clicarSeguirSemDependentes() {
        driverWeb.javaScriptClick(btnSeguirSemDependente);
    }

    public void clicarAdicionarDependente(int dependente) {
        driverWeb.javaScriptClick(btnAdicionarDependente);
        validarBotoesDependente(dependente);
    }

    private void validarBotoesDependente(int dependente) {
        abaPortabilidade = driverWeb.findElement(mapAbaPortabilidade.get(dependente), "xpath");
        btnConfirmarDependente = driverWeb.findElement(mapBtnConfirmarDependente.get(dependente), "xpath");
        abaNumeroNovo = driverWeb.findElement(mapAbaNumeroNovo.get(dependente), "xpath");
        btnExcluir = driverWeb.findElement(mapBtnExcluir.get(dependente), "xpath");
        txtTelefonePortabilidade = driverWeb.findElement(mapCampoTelefonePortabilidade.get(dependente),"xpath");

        driverWeb.waitElementVisible(abaPortabilidade, 10);
        Assert.assertTrue(abaNumeroNovo.isDisplayed());
        Assert.assertTrue(abaPortabilidade.isDisplayed());
        Assert.assertTrue(abaPortabilidade.getAttribute("class").contains("active"));
        Assert.assertTrue(btnConfirmarDependente.isDisplayed());
        Assert.assertTrue(btnExcluir.isDisplayed());
    }

    public void inserirNumeroDependentes(String numero) {
        driverWeb.sendKeys(txtTelefonePortabilidade, numero);
    }

    public void clicarConfirmarDependente() {
        driverWeb.javaScriptClick(btnConfirmarDependente);
        driverWeb.waitElementInvisible(btnConfirmarDependente, 10);
        //TODO validação de valores finais, adicionais e unitário de dependentes (conversar com Gustavo)
    }

    public void adicionarNovoNumeroDependente() {
        driverWeb.javaScriptClick(abaNumeroNovo);
    }

    public void clicarAdicionarOutroDependente(int dependente) {
        driverWeb.javaScriptClick("//*[@data-automation='btn-adicionar-mais-dep']","xpath");
    }

    public void clicarContinuar() {
        driverWeb.javaScriptClick("btn-continuar","id");
    }
}