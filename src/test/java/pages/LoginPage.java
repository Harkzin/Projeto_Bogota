package pages;

import org.junit.Assert;
import support.DriverQA;

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

        Assert.assertFalse("O botão [Continuar] deveria estar desabilitado", (driverQA.findElement("btn-continuar", "id").isEnabled()));
    }

    public void clicarAcompanharPedidos() {
        driverQA.click("lnk-acompanhar-pedidos", "id");
    }

    public void validaTelaLoginCPF() {
        driverQA.waitPageLoad("/login/my-orders", 10);

        Assert.assertNotNull(driverQA.findElement("track-order-form", "id"));
        Assert.assertNotNull(driverQA.findElement("txt-cpf", "id"));

        Assert.assertFalse("O botão [Continuar] deveria estar desabilitado", (driverQA.findElement("btn-continuar", "id").isEnabled()));
    }

    public void preencheCPF(String cpf) {
        driverQA.sendKeysCampoMascara(cpf, "txt-cpf", "id");
    }

    public void clicarBotaoContinuar() {
        Assert.assertTrue("O botão [Continuar] está habilitado", (driverQA.findElement("btn-continuar", "id").isEnabled()));

        driverQA.click("btn-continuar", "id");
    }

    public void validaTelaToken() {
        driverQA.waitPageLoad("/login/token", 10);

        Assert.assertNotNull(driverQA.findElement("token-verification-method", "id"));
        Assert.assertNotNull(driverQA.findElement("lnk-receber-codigo-email", "id"));
        Assert.assertNotNull(driverQA.findElement("lnk-receber-codigo-sms", "id"));
    }

    public void selecionaReceberCodigoEmail() {
        driverQA.click("lnk-receber-codigo-email", "id");
    }

    public void validaTelaEmail() {
        driverQA.waitPageLoad("/login/token/email", 10);

        Assert.assertNotNull(driverQA.findElement("token-validation-form", "id"));
        Assert.assertNotNull(driverQA.findElement("txt-token", "id"));
    }

    public void preencheCodigoToken(String token) {

    }

    public void validaTelaMeusPedidos() {
        driverQA.waitPageLoad("/my-account/orders", 10);

        Assert.assertNotNull(driverQA.findElement("txt-lista-pedidos", "id"));
    }

    public void acessarPedidoRecente() {
            driverQA.JavaScriptClick("lnk-pedido-1", "id");
        }

}

