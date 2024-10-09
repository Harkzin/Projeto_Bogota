package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.support.utils.DriverWeb;
import org.junit.Assert;

import java.util.UUID;

import static web.support.utils.Constants.*;
import static web.support.utils.Constants.ProcessType.ACQUISITION;
import static web.support.utils.Constants.ProcessType.PORTABILITY;
import static web.support.api.RestAPI.checkCpfDiretrix;
import static web.support.api.RestAPI.getCpf;

@Component
@ScenarioScope
public class CarrinhoPage {

    private final DriverWeb driverWeb;
    private final CartOrder cartOrder;

    @Autowired
    public CarrinhoPage(DriverWeb driverWeb, CartOrder cartOrder) {
        this.driverWeb = driverWeb;
        this.cartOrder = cartOrder;
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
    private WebElement celularAcessorios;
    private WebElement cpfAcessorios;
    private WebElement emailAcessorios;
    private WebElement confirma;
    private WebElement cancelar;

    private String getCpfForPlanFlow(boolean isApproved, boolean isDiretrix) {
        String cpf;
        String clearSaleRule = isApproved ? ".*[1348]$" : ".*5$"; //Regra do final do CPF da clearSale.

        do {
            do {
                cpf = getCpf();
            } while (!cpf.matches(clearSaleRule));
        } while (checkCpfDiretrix(cpf) != isDiretrix);

        return cpf;
    }

    private void validarCamposBase(boolean isDeviceCart) {
        telefoneMigracao = driverWeb.findElement("txt-telefone-migracao", "id");
        cpfMigracao = driverWeb.findElement("txt-cpf-migracao", "id");

        driverWeb.waitElementVisible(telefoneMigracao, 1);
        Assert.assertTrue(cpfMigracao.isDisplayed());

        if (!isDeviceCart) {
            Assert.assertEquals(telefoneMigracao.getAttribute("value"), "");
            Assert.assertEquals(cpfMigracao.getAttribute("value"), "");
        } else {
            //TODO - Nos fluxos de Base com Aparelho, os campos são preenchidos com os dados do cliente
            Assert.assertFalse(telefoneMigracao.isEnabled());
        }
    }

    private void validarCamposPortabilidade() {
        telefonePortabilidade = driverWeb.findElement("txt-telefone-portabilidade", "id");
        cpfPortabilidade = driverWeb.findElement("txt-cpf-portabilidade", "id");

        driverWeb.waitElementVisible(telefonePortabilidade, 1);
        Assert.assertTrue(cpfPortabilidade.isDisplayed());

        Assert.assertEquals(telefonePortabilidade.getAttribute("value"), "");
        Assert.assertEquals(cpfPortabilidade.getAttribute("value"), "");
    }

    private void validarCamposAquisicao() {
        WebElement dddAquisicao = driverWeb.findElement("txt-ddd", "id");
        telefoneContatoAquisicao = driverWeb.findElement("txt-telefone-aquisicao", "id");
        cpfAquisicao = driverWeb.findElement("txt-cpf-aquisicao", "id");

        driverWeb.waitElementVisible(dddAquisicao, 1);
        Assert.assertTrue(telefoneContatoAquisicao.isDisplayed());
        Assert.assertTrue(cpfAquisicao.isDisplayed());

        Assert.assertEquals(telefoneContatoAquisicao.getAttribute("value"), "");
        Assert.assertEquals(cpfAquisicao.getAttribute("value"), "");
    }

    private void validarCampoEmail(boolean isDeviceCart) {
        email = driverWeb.findElement("txt-email", "id");

        driverWeb.waitElementVisible(email, 1);

        if (!isDeviceCart) {
            Assert.assertEquals(email.getAttribute("value"), "");
        } else {
            //TODO - Caso esteja preenchido, validar que o email é igual ao do cadastro do cliente (backoffice).
        }
    }

    public void validarPaginaCarrinho() {
        driverWeb.waitPageLoad("/cart", 10);

        if (!cartOrder.isDeviceCart()) {
            String url = driverWeb.getDriver().getCurrentUrl();

            fluxoBase = driverWeb.findElement("rdn-migracao", "id");
            fluxoPortabilidade = driverWeb.findElement("rdn-portabilidade", "id");
            fluxoAquisicao = driverWeb.findElement("rdn-aquisicao", "id");

            if (url.endsWith("cart")) { //cart planos normal
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
                cartOrder.setProcessType(PORTABILITY);
                Assert.assertNull(fluxoBase);
                Assert.assertNotNull(fluxoPortabilidade);
                Assert.assertNull(fluxoAquisicao);
                validarCamposPortabilidade();
                validarCampoEmail(false);
            } else { //cart rentab aquisição (targetCampaign=gross)
                cartOrder.setProcessType(ACQUISITION);
                Assert.assertNull(fluxoBase);
                Assert.assertNull(fluxoPortabilidade);
                Assert.assertNotNull(fluxoAquisicao);
                validarCamposAquisicao();
                validarCampoEmail(false);
            }
        } else { //aparelhos
            switch (cartOrder.getProcessType()) {
                case ACQUISITION -> validarCamposAquisicao();
                case APARELHO_TROCA_APARELHO, EXCHANGE, MIGRATE -> validarCamposBase(true);
                case PORTABILITY -> validarCamposPortabilidade();
            }
            validarCampoEmail(true);
        }
    }

    public void validarPaginaCarrinhoAcessorios() {
        driverWeb.waitPageLoad("/cart", 10);

        celularAcessorios = driverWeb.findElement("txt-celular", "id");
        cpfAcessorios = driverWeb.findElement("txt-cpf", "id");
        emailAcessorios = driverWeb.findElement("txt-email", "id");
    }

    public void inserirDadosCarrinhoAcessorios(String celular, String cpf, String email) {
        driverWeb.sendKeys(celularAcessorios, celular);
        driverWeb.sendKeys(cpfAcessorios, cpf);
        driverWeb.sendKeys(emailAcessorios, email);
    }

    public void clicaBotaoContinuarAcessorios() {
        driverWeb.javaScriptClick("btn-continuar", "id");
    }

    public void selecionarFluxo(ProcessType processType) {
        switch (processType) {
            case EXCHANGE, EXCHANGE_PROMO, MIGRATE -> {
                driverWeb.javaScriptClick(fluxoBase);
                validarCamposBase(false);
            }
            case PORTABILITY -> {
                driverWeb.javaScriptClick(fluxoPortabilidade);
                validarCamposPortabilidade();
            }
            case ACQUISITION -> {
                driverWeb.javaScriptClick(fluxoAquisicao);
                validarCamposAquisicao();
            }
        }
        validarCampoEmail(false);
    }

    public void acessarUrlRentabCarrinho(String url) {
        driverWeb.getDriver().get(urlAmbiente + url);
    }

    public void inserirDadosBase(String telefone, String cpf) {
        driverWeb.sendKeys(telefoneMigracao, telefone);
        driverWeb.sendKeys(cpfMigracao, cpf);
    }

    public void inserirDadosBase(String cpf) {
        driverWeb.sendKeys(cpfMigracao, cpf);
    }

    public void inserirDadosPortabilidade(String telefone, boolean cpfAprovado, boolean cpfDiretrix) {
        driverWeb.sendKeys(telefonePortabilidade, telefone);
        driverWeb.sendKeys(cpfPortabilidade, getCpfForPlanFlow(cpfAprovado, cpfDiretrix));
    }

    public void inserirDadosAquisicao(String telefoneContato, boolean cpfAprovado, boolean cpfDiretrix) {
        driverWeb.sendKeys(telefoneContatoAquisicao, telefoneContato);
        driverWeb.sendKeys(cpfAquisicao, getCpfForPlanFlow(cpfAprovado, cpfDiretrix));
    }

    public void inserirEmail() {
        String userEmail = UUID.randomUUID().toString().replace("-", "") + "@mailsac.com";
        cartOrder.setUserEmail(userEmail);
        driverWeb.sendKeys(email, userEmail);
    }

    public void clicarEuQuero() {
        driverWeb.javaScriptClick("btn-eu-quero", "id");
    }

    public void clicarContinuar() {
        driverWeb.javaScriptClick("btn-continuar", "id");
    }

    public void validaMsgErro(String msgExibida) {
        driverWeb.waitElementPresence("//*[@id='cboxLoadedContent']", 60);
        WebElement contentMessageError =  driverWeb.findElement("//*[@id='cboxLoadedContent']/p", "xpath");

        driverWeb.waitElementVisible(contentMessageError, 10);
        Assert.assertTrue(contentMessageError.getText().contains(msgExibida));
    }

    public void validarModalAvisoTrocaPlano() {
        driverWeb.waitElementPresence("//div[@class='mdn-Modal mdn-Modal--warning mdn-Modal--sm mdn-isOpen mdn-isInitialized']", 10);

        confirma = driverWeb.findElement("//button[contains(.,'Confirma')]", "xpath"); //TODO Atualizar para id
        cancelar = driverWeb.findElement("//button[contains(.,'Cancela')]", "xpath"); //TODO Atualizar para id

        Assert.assertTrue(confirma.isDisplayed());
        Assert.assertTrue(cancelar.isDisplayed());
    }

    public void clicarAvisoTrocaPlano() {
        driverWeb.javaScriptClick(confirma);
    }
}