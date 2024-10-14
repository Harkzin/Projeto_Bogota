package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

import static org.junit.Assert.*;
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
    private boolean esim;

    public void validarPaginaLogin() {
        driverWeb.waitPageLoad("/login", 10);

        continuar = driverWeb.findElement("btn-continuar", "id");

        assertTrue(driverWeb.findElement("txt-telefone", "id").isDisplayed());
        assertTrue(driverWeb.findElement("lnk-acompanhar-pedidos", "id").isDisplayed());
        assertTrue(driverWeb.findElement("lnk-claro-clube", "id").isDisplayed());
        assertTrue(continuar.isDisplayed());
        assertFalse(continuar.isEnabled());
    }

    public void validarPaginaMinhaConta() {
        driverWeb.waitPageLoad("/my-account", 10);

        assertTrue(driverWeb.findElement("lnk-acompanhar-pedidos", "id").isDisplayed());
        assertTrue(driverWeb.findElement("lnk-claro-clube", "id").isDisplayed());

        if (esim) {
            //Valida mensagem de aviso
            assertTrue(driverWeb.findByXpath("/html/body/main/div[4]/div/div/div[1]/div").isDisplayed());

            //Valida card Gerenciar eSIM
            assertTrue(driverWeb.findByXpath("//*[@href='/my-account/esim']").isDisplayed());
        }
    }

    public void validarPaginaGrenciarESim() {
        driverWeb.waitPageLoad("/my-account/esim", 10);

        assertTrue(driverWeb.findByXpath("//*[@class='mdn-Accordion-toggle  mdn-Text--body ']").isDisplayed());
    }

    public void clicarBotaoAtivarESim() {
        driverWeb.javaScriptClick("/html/body/main/div[4]/div/div/div[2]/div/div/a", "xpath");
    }

    public void validarPaginaAtivarESim() {
        driverWeb.waitPageLoad("my-account/esim/activation", 10);

        //Valida QRCode
        assertTrue(driverWeb.findByXpath("/html/body/main/div[4]/div/div/div[3]/img").isDisplayed());

        String[] validacaoMarcasCelulares = {"apple", "motorola", "samsung"};

        for (String marca : validacaoMarcasCelulares) {

            //Valida ativação eSIM por QRCode
            driverWeb.javaScriptClick("//*[@data-analytics-event-label='faq:ativar-qrcode:" + marca + "']/a", "xpath");
            assertTrue(driverWeb.findByXpath("//*[@data-analytics-event-label='faq:ativar-qrcode:" + marca + "']/div").isDisplayed());

            driverWeb.actionPause(2000);
        }

        for (String marca : validacaoMarcasCelulares) {

            //Valida ativação eSIM por código copia e cola
            driverWeb.javaScriptClick("//*[@data-analytics-event-label='faq:ativar-manual:" + marca + "']/a", "xpath");
            assertTrue(driverWeb.findByXpath("//*[@data-analytics-event-label='faq:ativar-manual:" + marca + "']/div").isDisplayed());

            driverWeb.actionPause(2000);
        }

    }

    public void validarMensagemSaldoClaroClube(String mensagemClube) {
        WebElement msgClaroClube = driverWeb.findElement("//*[@id='lnk-claro-clube']/p[2]", "xpath");

        assertEquals(msgClaroClube.getText(), mensagemClube);
        assertTrue(msgClaroClube.isDisplayed());
    }

    public void clicarAcompanharPedidos() {
        driverWeb.javaScriptClick("lnk-acompanhar-pedidos", "id");
    }

    public void clicarAcompanharPedidosEsim(){
        driverWeb.javaScriptClick("//*[@href='/login/esim']", "xpath");
    }

    public void clicarGerenciarESim() {
        driverWeb.javaScriptClick("//*[@href='/my-account/esim']", "xpath");
    }

    public void clicarClaroClube() {
        driverWeb.javaScriptClick("lnk-claro-clube", "id");
    }

    public void validarPaginaLoginCpf() {
        driverWeb.waitPageLoad("/login/my-orders", 10);

        cpf = driverWeb.findElement("txt-cpf", "id");
        continuar = driverWeb.findElement("btn-continuar", "id");

        assertTrue(driverWeb.findElement("track-order-form", "id").isDisplayed());
        assertTrue(cpf.isDisplayed());
        assertFalse(continuar.isEnabled());
    }

    public void validarPaginaLoginEsimCPF(){
        driverWeb.waitPageLoad("login/esim", 10);

        cpf = driverWeb.findElement("txt-cpf", "id");
        continuar = driverWeb.findElement("btn-continuar", "id");

        assertTrue(driverWeb.findElement("track-order-form", "id").isDisplayed());
        assertTrue(cpf.isDisplayed());
        assertFalse(continuar.isEnabled());

        esim = true;
    }

    public void preencheCPF(String cpf) {
        driverWeb.sendKeysLogin(this.cpf, cpf);
    }

    public void clicarBotaoContinuar() {
        driverWeb.javaScriptClick(continuar);
    }

    public void validarPaginaLoginToken() {
        driverWeb.waitPageLoad("/login/token", 15);

        assertTrue(driverWeb.findElement("token-verification-method", "id").isDisplayed());
        assertTrue(driverWeb.findElement("lnk-receber-codigo-email", "id").isDisplayed());
        assertTrue(driverWeb.findElement("lnk-receber-codigo-sms", "id").isDisplayed());
    }

    public void selecionarCodigoEmail(String email) {
        clearInbox(email);
        driverWeb.javaScriptClick("lnk-receber-codigo-email", "id");
    }

    public void validarPaginaLoginEmail() {
        driverWeb.waitPageLoad("/login/token/email", 10);
        token = driverWeb.findElement("txt-token", "id");

        assertTrue(token.isDisplayed());
    }

    public void inserirTokenEmail(String email) {
        clearInbox(email);
        driverWeb.sendKeys(token, driverWeb.getEmail(email, CONFIRMA_TOKEN).selectXpath("/html/body/table/tbody/tr/td/table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td/table/tbody/tr[2]/td/table/tbody/tr[3]/td/table/tbody/tr/td[2]/table/tbody/tr[1]/td").first().text());
    }

    public void validarPaginaMeusPedidos() {
        driverWeb.waitPageLoad("/my-account/orders", 10);
        assertTrue(driverWeb.findElement("txt-lista-pedidos", "id").isDisplayed());
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