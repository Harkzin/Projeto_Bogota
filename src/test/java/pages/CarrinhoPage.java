package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.ScreenshotException;
import support.DriverQA;

import static pages.HomePage.*;

import org.junit.Assert;

import java.io.File;

public class CarrinhoPage {

    private DriverQA driver;

    public CarrinhoPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    //Resumo da compra
    private String xpathTituloPlanoResumo = "(//*[@class='product-fullname isNotOrderConfPage'])[2]";
    private String xpathGbNoPlanoResumo = "(//*[@class='modality']//p)[3]";
    private String xpathGbDeBonusResumo = "(//*[@class='modality']//p)[4]";
    private String xpathValorTotalResumo = "(//*[@class='js-entry-price-plan js-revenue'])[2]";
    private String xpathMetodoPagamentoResumo = "(//*[@class='mdn-Price-suffix'])[2]";
    private String xpathFidelizadoResumo = "(//*[@class='mdn-Price-suffix hidden-xs hidden-sm'])[2]";

    //Form Aq/Port/MigTroc
    private String xpathMigracaoForm = "(//*[@data-automation='migracao'])";
    private String xpathPortabilidadeForm = "(//*[@data-automation='portabilidade'])";
    private String xpathAquisicaoForm = "(//*[@data-automation='aquisicao'])";
    private String idTelefoneMigracaoForm = "telephone";
    private String idEmailForm = "email";
    private String idCpfMigracaoForm = "cpf";
    private String idTelefonePortabilidadeForm = "telephonePortability";
    private String idCpfPortabilidadeForm = "cpfPortability";
    private String idDDDAquisicaoForm = "dddAquisicao";
    private String idTelefoneAquisicaoForm = "telephoneAcquisition";
    private String idCpfAquisicaoForm = "cpfAcquisition";
    private String idEuQueroForm = "buttonCheckout";

    // Mensagem erro
    private String xpathMsgErroBloqueioDependente = "(//*[@id='cboxLoadedContent'])";

    // Clicar não concordo
    private String xpathNaoConcordo = "//*[@data-multa-action='goStep2']";

    //Clicar no Checkbox ok, entendi
    private String xpathClicarOKEntendi = "//*[@data-multa-action='backHome']";

    //Validar que foi direcionado para a Home
    private String xpathValidarQueFoiDirecionadoParaAHome = "/html/body";

    public void validarCarrinho() {
        driver.waitSeconds(1);
        String titulo = driver.getText(xpathTituloPlanoResumo, "xpath");
        String gbPlano = driver.getText(xpathGbNoPlanoResumo, "xpath");
        String gbBonus = driver.getText(xpathGbDeBonusResumo, "xpath");
        String valor = driver.getText(xpathValorTotalResumo, "xpath");
        Assert.assertTrue((getTituloCardHome().equals(titulo)));
        Assert.assertTrue((getValorPlanoHome().contains(valor)));
        Assert.assertTrue((getGbPlanoCardHome().equals(gbPlano)));
        Assert.assertTrue((getGbBonusCardHome().equals(gbBonus)));

    }

    public void selecionarOpcaoForm(String opcao) {
        driver.waitElementToBeClickableAll(xpathAquisicaoForm, 5, "xpath");
        switch (opcao) {
            case "Quero uma linha nova da Claro":
                driver.click(xpathAquisicaoForm, "xpath");
                break;
            case "Trazer meu número para Claro":
                driver.click(xpathPortabilidadeForm, "xpath");
                break;
            case "Mudar meu plano da Claro":
                driver.click(xpathMigracaoForm, "xpath");
                break;
        }

    }

    public void preencherDadosLinhaForm(String ddd, String telefone, String email, String cpf, String fluxo) {
        driver.waitElementToBeClickableAll(idCpfMigracaoForm, 5, "id");
        driver.sendKeys(email, idEmailForm);
        switch (fluxo) {
            case "migracao":
                driver.waitSeconds(1);
                driver.actionSendKey(telefone, idTelefoneMigracaoForm, "id");
                driver.actionSendKey(cpf, idCpfMigracaoForm, "id");
                break;
            case "portabilidade":
                driver.waitElementToBeClickableAll(idTelefonePortabilidadeForm, 1, "id");
                driver.sendKeys(telefone, idTelefonePortabilidadeForm, "id");
                driver.sendKeys(cpf, idCpfPortabilidadeForm, "id");
                break;
            case "aquisicao":
                driver.waitElementToBeClickableAll(idDDDAquisicaoForm, 1, "id");
                driver.clear(idDDDAquisicaoForm, "id");
                driver.sendKeys(ddd, idDDDAquisicaoForm, "id");
                driver.sendKeys(telefone, idTelefoneAquisicaoForm, "id");
                driver.sendKeys(cpf, idCpfAquisicaoForm, "id");
        }
    }

    public void euQueroCarrinho() {
        driver.click(idEuQueroForm, "id");
    }

    public void validarMensagemBloqueioClienteDependente(String mensagem) {
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(0, 106));
        Assert.assertEquals("Favor informar a linha titular.", driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(108, 139));
    }

    public void ClicarNaoConcordo() {
        driver.click(xpathNaoConcordo, "xpath");

    }



    public void clicarNoCheckbox() {
        driver.click(xpathClicarOKEntendi, "xpath");
    }

    public void validarQueFoiDirecionadoParaAHome() {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

    }
}


