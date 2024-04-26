package pages;

import org.junit.Assert;
import support.DriverQA;

import static pages.ComumPage.Cart_emailAddress;
import static pages.ComumPage.Email.CONFIRMA_TOKEN;

public class LoginPage {
    private final DriverQA driverQA;

    public LoginPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    public void validarPaginaLogin() {
        driverQA.waitPageLoad("/login", 10);

        Assert.assertNotNull(driverQA.findElement("txt-telefone", "id"));
        Assert.assertNotNull(driverQA.findElement("lnk-acompanhar-pedidos", "id"));
        Assert.assertNotNull(driverQA.findElement("lnk-claro-clube", "id"));
        Assert.assertNotNull(driverQA.findElement("btn-continuar", "id"));

        Assert.assertFalse((driverQA.findElement("btn-continuar", "id").isEnabled()));
    }

    public void clicarAcompanharPedidos() {
        driverQA.JavaScriptClick("lnk-acompanhar-pedidos", "id");
    }

    public void validarPaginaLoginCpf() {
        driverQA.waitPageLoad("/login/my-orders", 10);

        Assert.assertNotNull(driverQA.findElement("track-order-form", "id"));
        Assert.assertNotNull(driverQA.findElement("txt-cpf", "id"));

        Assert.assertFalse("O botão [Continuar] deveria estar desabilitado", (driverQA.findElement("btn-continuar", "id").isEnabled()));
    }

    public void preencheCPF(String cpf) {
        driverQA.actionSendKeys("txt-cpf", "id", cpf);
    }

    public void clicarBotaoContinuar() {
        driverQA.waitElementToBeClickable(driverQA.findElement("btn-continuar", "id"), 1);

        driverQA.JavaScriptClick("btn-continuar", "id");
    }

    public void validarPaginaLoginToken() {
        driverQA.waitPageLoad("/login/token", 10);

        Assert.assertNotNull(driverQA.findElement("token-verification-method", "id"));
        Assert.assertNotNull(driverQA.findElement("lnk-receber-codigo-email", "id"));
        Assert.assertNotNull(driverQA.findElement("lnk-receber-codigo-sms", "id"));
    }

    public void selecionaReceberCodigoEmail() {
        driverQA.JavaScriptClick("lnk-receber-codigo-email", "id");
    }

    public void validarPaginaLoginEmail() {
        driverQA.waitPageLoad("/login/token/email", 10);

        Assert.assertNotNull(driverQA.findElement("token-validation-form", "id"));
        Assert.assertNotNull(driverQA.findElement("txt-token", "id"));
    }

    public void inserirTokenEmail() {
        driverQA.actionSendKeys("txt-token", "id", driverQA.getEmail(Cart_emailAddress, CONFIRMA_TOKEN).selectXpath("/html/body/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td").first().text());
    }

    public void validaTelaMeusPedidos() {
        driverQA.waitPageLoad("/my-account/orders", 10);

        Assert.assertNotNull(driverQA.findElement("txt-lista-pedidos", "id"));
    }

    public void acessarPedidoRecente() {
        driverQA.JavaScriptClick("lnk-pedido-1", "id");
    }

    public void clicarBotaoConfirmar() {
        driverQA.JavaScriptClick("btn-confirmar", "id");
    }
}