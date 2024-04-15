package pages;

import io.cucumber.java.pt.Então;
import org.openqa.selenium.WebElement;
import support.DriverQA;
import org.junit.Assert;

import java.io.IOException;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pages.ComumPage.urlAmbiente;
import static support.RestAPI.checkCpfDiretrix;
import static support.RestAPI.getCpf;

public class CarrinhoPage {
    private final DriverQA driverQA;

    public CarrinhoPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private WebElement fluxoMigracao;
    private WebElement fluxoPortabilidade;
    private WebElement fluxoAquisicao;
    private WebElement telefoneMigracao;
    private WebElement cpfMigracao;
    private WebElement telefonePortabilidade;
    private WebElement cpfPortabilidade;
    private WebElement telefoneContatoAquisicao;
    private WebElement cpfAquisicao;


    private String getCpfForPlanFlow(boolean isApproved, boolean isDiretrix) throws IOException, InterruptedException {
        String cpf;
        String clearSaleRule = isApproved ? ".*[1348]$" : ".*5$"; //Regra do final do CPF da clearSale.

        do {
            do {
                cpf = getCpf();
            } while (!cpf.matches(clearSaleRule));
        } while (checkCpfDiretrix(cpf) != isDiretrix);

        return cpf;
    }

    private void validarCamposMigracao() {
        telefoneMigracao = driverQA.findElement("txt-telefone-migracao", "id");
        cpfMigracao = driverQA.findElement("txt-cpf-migracao", "id");

        Assert.assertTrue(telefoneMigracao.isDisplayed());
        Assert.assertTrue(cpfMigracao.isDisplayed());

        Assert.assertEquals(telefoneMigracao.getAttribute("value"), "");
        Assert.assertEquals(cpfMigracao.getAttribute("value"), "");
    }

    private void validarCamposPortabilidade() {
        telefonePortabilidade = driverQA.findElement("txt-telefone-portabilidade", "id");
        cpfPortabilidade = driverQA.findElement("txt-cpf-portabilidade", "id");

        Assert.assertTrue(telefonePortabilidade.isDisplayed());
        Assert.assertTrue(cpfPortabilidade.isDisplayed());

        Assert.assertEquals(telefonePortabilidade.getAttribute("value"), "");
        Assert.assertEquals(cpfPortabilidade.getAttribute("value"), "");
    }

    private void validarCamposAquisicao() {
        WebElement dddAquisicao = driverQA.findElement("txt-ddd", "id");
        telefoneContatoAquisicao = driverQA.findElement("txt-telefone-aquisicao", "id");
        cpfAquisicao = driverQA.findElement("txt-cpf-aquisicao", "id");

        Assert.assertTrue(dddAquisicao.isDisplayed());
        Assert.assertTrue(telefoneContatoAquisicao.isDisplayed());
        Assert.assertTrue(cpfAquisicao.isDisplayed());

        Assert.assertEquals(telefoneContatoAquisicao.getAttribute("value"), "");
        Assert.assertEquals(cpfAquisicao.getAttribute("value"), "");
    }

    public void validarPaginaCarrinho() {
        driverQA.waitPageLoad("/cart", 10);
        String url = driverQA.getDriver().getCurrentUrl();

        fluxoMigracao = driverQA.findElement("rdn-migracao", "id");
        fluxoPortabilidade = driverQA.findElement("rdn-portabilidade", "id");
        fluxoAquisicao = driverQA.findElement("rdn-aquisicao", "id");

        if (url.endsWith("cart")) {                         //cart normal
            Assert.assertNotNull(fluxoMigracao);
            Assert.assertNotNull(fluxoPortabilidade);
            Assert.assertNotNull(fluxoAquisicao);
        } else if (url.contains("targetCampaign=migra")) { //cart rentab base
            Assert.assertNotNull(fluxoMigracao);
            Assert.assertNull(fluxoPortabilidade);
            Assert.assertNull(fluxoAquisicao);
            validarCamposMigracao();
        } else if (url.contains("targetCampaign=portin")) { //cart rentab port
            Assert.assertNull(fluxoMigracao);
            Assert.assertNotNull(fluxoPortabilidade);
            Assert.assertNull(fluxoAquisicao);
            validarCamposPortabilidade();
        } else {                                            //cart rentab aquisição (targetCampaign=gross)
            Assert.assertNull(fluxoMigracao);
            Assert.assertNull(fluxoPortabilidade);
            Assert.assertNotNull(fluxoAquisicao);
            validarCamposAquisicao();
        }
    }

    public void selecionarFluxo(String fluxo) {
        switch (fluxo) {
            case "Migração/Troca":
                driverQA.JavaScriptClick(fluxoMigracao);
                validarCamposMigracao();
                break;
            case "Portabilidade":
                driverQA.JavaScriptClick(fluxoPortabilidade);
                validarCamposPortabilidade();
                break;
            case "Aquisição":
                driverQA.JavaScriptClick(fluxoAquisicao);
                validarCamposAquisicao();
        }
    }

    public void acessarUrlRentabCarrinho(String url) {
        driverQA.getDriver().get(urlAmbiente + url);
    }

    public void inserirDadosBase(String telefone, String cpf) {
        driverQA.actionSendKeys(telefoneMigracao, telefone);
        driverQA.actionSendKeys(cpfMigracao, cpf);
    }

    public void inserirDadosPortabilidade(String telefone, boolean cpfAprovado, boolean cpfDiretrix) throws IOException, InterruptedException {
        driverQA.actionSendKeys(telefonePortabilidade, telefone);
        driverQA.actionSendKeys(cpfPortabilidade, getCpfForPlanFlow(cpfAprovado, cpfDiretrix));
    }

    public void inserirDadosAquisicao(String telefoneContato, boolean cpfAprovado, boolean cpfDiretrix) throws IOException, InterruptedException {
        driverQA.actionSendKeys(telefoneContatoAquisicao, telefoneContato);
        driverQA.actionSendKeys(cpfAquisicao, getCpfForPlanFlow(cpfAprovado, cpfDiretrix));
    }

    public void inserirEmail(String email) {
        driverQA.actionSendKeys("txt-email", "id", email);
    }

    public void clicarEuQuero() {
        driverQA.JavaScriptClick("btn-eu-quero", "id");
    }

    public void clicarEuQueroTHAB() {
        driverQA.JavaScriptClick("buttonCheckoutThab", "id");
    }

    public void validaMsgBloqueioDependente() {
        Assert.assertNotNull(driverQA.findElement("cboxLoadedContent", "id"));
    }
}
