package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import support.CartOrder;
import support.DriverQA;

import static support.Common.Email.CONFIRMA_TOKEN;
import static support.RestAPI.clearInbox;

public class LoginPage {
    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public LoginPage(DriverQA stepDriver, CartOrder cartOrder) {
        driverQA = stepDriver;
        this.cartOrder = cartOrder;
    }
    private WebElement continuar;
    private WebElement cpf;
    private WebElement token;
    private String emailAddress;

    public void validarPaginaLogin() {
        driverQA.waitPageLoad("/login", 10);

        continuar = driverQA.findElement("btn-continuar", "id");

        Assert.assertTrue(driverQA.findElement("txt-telefone", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-acompanhar-pedidos", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-claro-clube", "id").isDisplayed());
        Assert.assertTrue(continuar.isDisplayed());

        Assert.assertFalse(continuar.isEnabled());
    }

    public void clicarAcompanharPedidos() {
        driverQA.JavaScriptClick("lnk-acompanhar-pedidos", "id");
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
        driverQA.JavaScriptClick(continuar);
    }

    public void validarPaginaLoginToken() {
        driverQA.waitPageLoad("/login/token", 10);

        Assert.assertTrue(driverQA.findElement("token-verification-method", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-receber-codigo-email", "id").isDisplayed());
        Assert.assertTrue(driverQA.findElement("lnk-receber-codigo-sms", "id").isDisplayed());
    }

    public void selecionaReceberCodigoEmail() {
        clearInbox(emailAddress);
        driverQA.JavaScriptClick("lnk-receber-codigo-email", "id");
    }

    public void validarPaginaLoginEmail() {
        driverQA.waitPageLoad("/login/token/email", 10);

        token = driverQA.findElement("txt-token", "id");

        Assert.assertTrue(token.isDisplayed());

    }

    public void inserirTokenEmail() {
        driverQA.actionSendKeys(token, driverQA.getEmail(emailAddress, CONFIRMA_TOKEN).selectXpath("/html/body/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td").first().text());
    }

    public void validarPaginaMeusPedidos() {
        driverQA.waitPageLoad("/my-account/orders", 10);

        Assert.assertTrue(driverQA.findElement("txt-lista-pedidos", "id").isDisplayed());

    }

    public void acessarPedidoRecente() {
        driverQA.JavaScriptClick("lnk-pedido-1", "id");
    }

    public void clicarBotaoConfirmar() {
        driverQA.JavaScriptClick("btn-confirmar", "id");
    }

    public void validarPaginaDetalhesPedido() {
        //TODO Validar Campos Pedidos ()
    }
}