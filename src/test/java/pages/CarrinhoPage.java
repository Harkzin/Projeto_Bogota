package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import support.DriverQA;
import org.junit.Assert;

import java.io.IOException;
import java.util.UUID;

import static pages.ComumPage.*;
import static pages.ComumPage.ProcessType.ACQUISITION;
import static pages.ComumPage.ProcessType.PORTABILITY;
import static support.RestAPI.checkCpfDiretrix;
import static support.RestAPI.getCpf;

public class CarrinhoPage {
    private final DriverQA driverQA;

    public CarrinhoPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private WebElement fluxoBase;
    private WebElement fluxoPortabilidade;
    private WebElement fluxoAquisicao;
    private WebElement telefoneMigracao;
    private WebElement cpfMigracao;
    private WebElement telefonePortabilidade;
    private WebElement cpfPortabilidade;
    private WebElement telefoneContatoAquisicao;
    private WebElement cpfAquisicao;
    private WebElement email;

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

    private void validarCamposBase(Boolean isDeviceCart) {
        telefoneMigracao = driverQA.findElement("txt-telefone-migracao", "id");
        cpfMigracao = driverQA.findElement("txt-cpf-migracao", "id");

        driverQA.waitElementVisibility(telefoneMigracao, 1);
        Assert.assertTrue(cpfMigracao.isDisplayed());

        if (!isDeviceCart) {
            Assert.assertEquals(telefoneMigracao.getAttribute("value"), "");
            Assert.assertEquals(cpfMigracao.getAttribute("value"), "");
        } else {
            //TODO - Nos fluxos de Base com Aparelho, os campos são preenchidos com os dados do cliente
        }
    }

    private void validarCamposPortabilidade() {
        telefonePortabilidade = driverQA.findElement("txt-telefone-portabilidade", "id");
        cpfPortabilidade = driverQA.findElement("txt-cpf-portabilidade", "id");

        driverQA.waitElementVisibility(telefonePortabilidade, 1);
        Assert.assertTrue(cpfPortabilidade.isDisplayed());

        Assert.assertEquals(telefonePortabilidade.getAttribute("value"), "");
        Assert.assertEquals(cpfPortabilidade.getAttribute("value"), "");
    }

    private void validarCamposAquisicao() {
        WebElement dddAquisicao = driverQA.findElement("txt-ddd", "id");
        telefoneContatoAquisicao = driverQA.findElement("txt-telefone-aquisicao", "id");
        cpfAquisicao = driverQA.findElement("txt-cpf-aquisicao", "id");

        driverQA.waitElementVisibility(dddAquisicao, 1);
        Assert.assertTrue(telefoneContatoAquisicao.isDisplayed());
        Assert.assertTrue(cpfAquisicao.isDisplayed());

        Assert.assertEquals(telefoneContatoAquisicao.getAttribute("value"), "");
        Assert.assertEquals(cpfAquisicao.getAttribute("value"), "");
    }

    private void validarCampoEmail(Boolean isDeviceCart) {
        email = driverQA.findElement("txt-email", "id");

        driverQA.waitElementVisibility(email, 1);

        if (!isDeviceCart) {
            Assert.assertEquals(email.getAttribute("value"), "");
        } else {
            //TODO - Caso esteja preenchido, validar que o email é igual ao do cadastro do cliente (backoffice).
        }
    }

    public void validarPaginaCarrinho() {
        driverQA.waitPageLoad("/cart", 10);

        if (!Cart_hasDevice) {
            String url = driverQA.getDriver().getCurrentUrl();

            fluxoBase = driverQA.findElement("rdn-migracao", "id");
            fluxoPortabilidade = driverQA.findElement("rdn-portabilidade", "id");
            fluxoAquisicao = driverQA.findElement("rdn-aquisicao", "id");

            if (url.endsWith("cart")) {                         //cart planos normal
                Assert.assertNotNull(fluxoBase);
                Assert.assertNotNull(fluxoPortabilidade);
                Assert.assertNotNull(fluxoAquisicao);
            } else if (url.contains("targetCampaign=migra")) { //cart rentab base
                //TODO Cart_processType = ?
                Assert.assertNotNull(fluxoBase);
                Assert.assertNull(fluxoPortabilidade);
                Assert.assertNull(fluxoAquisicao);
                validarCamposBase(false);
                validarCampoEmail(false);
            } else if (url.contains("targetCampaign=portin")) { //cart rentab port
                Cart_processType = PORTABILITY;
                Assert.assertNull(fluxoBase);
                Assert.assertNotNull(fluxoPortabilidade);
                Assert.assertNull(fluxoAquisicao);
                validarCamposPortabilidade();
                validarCampoEmail(false);
            } else {                                            //cart rentab aquisição (targetCampaign=gross)
                Cart_processType = ACQUISITION;
                Assert.assertNull(fluxoBase);
                Assert.assertNull(fluxoPortabilidade);
                Assert.assertNotNull(fluxoAquisicao);
                validarCamposAquisicao();
                validarCampoEmail(false);
            }
        } else { //aparelhos
            switch (Cart_processType) {
                case ACQUISITION: {
                    validarCamposAquisicao();
                }
                case APARELHO_TROCA_APARELHO:
                case EXCHANGE:
                case MIGRATE: {
                    validarCamposBase(true);
                }
                case PORTABILITY: {
                    validarCamposPortabilidade();
                }
            }
            validarCampoEmail(true);
        }
    }

    public void selecionarFluxo(ProcessType processType) {
        Cart_processType = processType;

        switch (processType) {
            case EXCHANGE:
            case EXCHANGE_PROMO:
            case MIGRATE:
                driverQA.JavaScriptClick(fluxoBase);
                validarCamposBase(false);
                break;
            case PORTABILITY:
                driverQA.JavaScriptClick(fluxoPortabilidade);
                validarCamposPortabilidade();
                break;
            case ACQUISITION:
                driverQA.JavaScriptClick(fluxoAquisicao);
                validarCamposAquisicao();
        }
        validarCampoEmail(false);
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

    public void inserirEmail() {
        Cart_emailAddress = UUID.randomUUID().toString().replace("-", "") + "@mailsac.com";

        driverQA.actionSendKeys(email, Cart_emailAddress);
    }

    public void clicarEuQuero() {
        driverQA.JavaScriptClick("btn-eu-quero", "id");
    }

    public void validaMsgBloqueioDependente() {
        Assert.assertNotNull(driverQA.findElement("cboxLoadedContent", "id"));
    }
}