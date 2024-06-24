package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

import static support.utils.Constants.Email.CONFIRMA_TOKEN;
import static support.api.RestAPI.clearInbox;

@Component
@ScenarioScope
public class LoginPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public LoginPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private WebElement continuar;
    private WebElement cpf;
    private WebElement token;

    public void validarPaginaLogin() {
        driverQA.waitPageLoad("/login", 10);

        continuar = driverQA.findElement("btn-continuar", "id");

        Assert.assertTrue(driverQA.findElement("txt-telefone", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-acompanhar-pedidos", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-claro-clube", "id").isDisplayed());
        Assert.assertTrue(continuar.isDisplayed());
        Assert.assertFalse(continuar.isEnabled());
    }

    public void validarPaginaMinhaConta() {
        driverQA.waitPageLoad("/my-account", 10);

        Assert.assertTrue(driverQA.findElement("lnk-acompanhar-pedidos", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-claro-clube", "id").isDisplayed());
    }

    public void validarMensagemSaldoClaroClube(String mensagemClube) {
        WebElement msgClaroClube = driverQA.findElement("//*[@id='lnk-claro-clube']/p[2]", "xpath");

        Assert.assertEquals(msgClaroClube.getText(), mensagemClube);
        Assert.assertTrue(msgClaroClube.isDisplayed());
    }

    public void clicarAcompanharPedidos() {
        driverQA.javaScriptClick("lnk-acompanhar-pedidos", "id");
    }

    public void clicarClaroClube() {
        driverQA.javaScriptClick("lnk-claro-clube", "id");
    }

    public void validarPaginaLoginCpf() {
        driverQA.waitPageLoad("/login/my-orders", 10);

        cpf = driverQA.findElement("txt-cpf", "id");
        continuar = driverQA.findElement("btn-continuar", "id");

        Assert.assertTrue(driverQA.findElement("track-order-form", "id").isDisplayed());
        Assert.assertTrue(cpf.isDisplayed());
        Assert.assertFalse(continuar.isEnabled());
    }


    public void preencheCPF(String cpf) {
        driverQA.actionSendKeys(this.cpf, cpf);
    }

    public void clicarBotaoContinuar() {
        driverQA.javaScriptClick(continuar);
    }

    public void validarPaginaLoginToken() {
        driverQA.waitPageLoad("/login/token", 15);

        Assert.assertTrue(driverQA.findElement("token-verification-method", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-receber-codigo-email", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-receber-codigo-sms", "id").isDisplayed());
    }

    public void selecionaReceberCodigoEmail() {
        clearInbox(cartOrder.essential.user.email);
        driverQA.javaScriptClick("lnk-receber-codigo-email", "id");
    }

    public void validarPaginaLoginEmail() {
        driverQA.waitPageLoad("/login/token/email", 10);
        token = driverQA.findElement("txt-token", "id");

        Assert.assertTrue(token.isDisplayed());
    }

    public void inserirTokenEmail() {
        clearInbox(cartOrder.essential.user.email);
        driverQA.actionSendKeys(token, driverQA.getEmail(cartOrder.essential.user.email, CONFIRMA_TOKEN).selectXpath("/html/body/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td").first().text());
    }

    public void validarPaginaMeusPedidos() {
        driverQA.waitPageLoad("/my-account/orders", 10);

        Assert.assertTrue(driverQA.findElement("txt-lista-pedidos", "id").isDisplayed());
    }

    public void acessarPedidoRecente() {
        driverQA.javaScriptClick("lnk-pedido-1", "id");
    }

    public void clicarBotaoConfirmar() {
        driverQA.javaScriptClick("btn-confirmar", "id");
    }

    public void validarPaginaDetalhesPedido() {
        //TODO Validar Campos Pedidos ()
    }
}