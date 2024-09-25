package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

import static web.support.utils.Constants.Email.CONFIRMA_TOKEN;
import static web.support.api.RestAPI.clearInbox;

@Component
@ScenarioScope
public class LoginPage {

    private final DriverWeb driverWeb;

    @Autowired
    public LoginPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private WebElement continuar;
    private WebElement cpf;
    private WebElement token;

    public void validarPaginaLogin() {
        driverWeb.waitPageLoad("/login", 10);

        continuar = driverWeb.findElement("btn-continuar", "id");

        Assert.assertTrue(driverWeb.findElement("txt-telefone", "id").isDisplayed());
        Assert.assertTrue(driverWeb.findElement("lnk-acompanhar-pedidos", "id").isDisplayed());
        Assert.assertTrue(driverWeb.findElement("lnk-claro-clube", "id").isDisplayed());
        Assert.assertTrue(continuar.isDisplayed());
        Assert.assertFalse(continuar.isEnabled());
    }

    public void validarPaginaMinhaConta() {
        driverWeb.waitPageLoad("/my-account", 10);

        Assert.assertTrue(driverWeb.findElement("lnk-acompanhar-pedidos", "id").isDisplayed());
        Assert.assertTrue(driverWeb.findElement("lnk-claro-clube", "id").isDisplayed());
    }

    public void validarMensagemSaldoClaroClube(String mensagemClube) {
        WebElement msgClaroClube = driverWeb.findElement("//*[@id='lnk-claro-clube']/p[2]", "xpath");

        Assert.assertEquals(msgClaroClube.getText(), mensagemClube);
        Assert.assertTrue(msgClaroClube.isDisplayed());
    }

    public void clicarAcompanharPedidos() {
        driverWeb.javaScriptClick("lnk-acompanhar-pedidos", "id");
    }

    public void clicarClaroClube() {
        driverWeb.javaScriptClick("lnk-claro-clube", "id");
    }

    public void validarPaginaLoginCpf() {
        driverWeb.waitPageLoad("/login/my-orders", 10);

        cpf = driverWeb.findElement("txt-cpf", "id");
        continuar = driverWeb.findElement("btn-continuar", "id");

        Assert.assertTrue(driverWeb.findElement("track-order-form", "id").isDisplayed());
        Assert.assertTrue(cpf.isDisplayed());
        Assert.assertFalse(continuar.isEnabled());
    }


    public void preencheCPF(String cpf) {
        driverWeb.actionSendKeys(this.cpf, cpf);
    }

    public void clicarBotaoContinuar() {
        driverWeb.javaScriptClick(continuar);
    }

    public void validarPaginaLoginToken() {
        driverWeb.waitPageLoad("/login/token", 15);

        Assert.assertTrue(driverWeb.findElement("token-verification-method", "id").isDisplayed());
        Assert.assertTrue(driverWeb.findElement("lnk-receber-codigo-email", "id").isDisplayed());
        Assert.assertTrue(driverWeb.findElement("lnk-receber-codigo-sms", "id").isDisplayed());
    }

    public void selecionarCodigoEmail(String email) {
        clearInbox(email);
        driverWeb.javaScriptClick("lnk-receber-codigo-email", "id");
    }

    public void validarPaginaLoginEmail() {
        driverWeb.waitPageLoad("/login/token/email", 10);
        token = driverWeb.findElement("txt-token", "id");

        Assert.assertTrue(token.isDisplayed());
    }

    public void inserirTokenEmail(String email) {
        clearInbox(email);
        driverWeb.actionSendKeys(token, driverWeb.getEmail(email, CONFIRMA_TOKEN).selectXpath("/html/body/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td").first().text());
    }

    public void validarPaginaMeusPedidos() {
        driverWeb.waitPageLoad("/my-account/orders", 10);
        Assert.assertTrue(driverWeb.findElement("txt-lista-pedidos", "id").isDisplayed());
    }

    public void acessarPedidoRecente() {
        driverWeb.javaScriptClick("lnk-pedido-1", "id");
    }

    public void clicarBotaoConfirmar() {
        driverWeb.javaScriptClick("btn-confirmar", "id");
    }

    public void validarPaginaDetalhesPedido() {
        //TODO Validar Campos Pedidos ()
    }
}