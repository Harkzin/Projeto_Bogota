package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

import java.util.List;

import static web.pages.ComumPage.validateElementActiveVisible;
import static web.pages.ComumPage.validateElementText;

@Component
@ScenarioScope
public class FormaPagamentoPage {

    private final DriverWeb driverWeb;

    @Autowired
    FormaPagamentoPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    @FindBy(id = "txt-cupom")
    private WebElement cupom;

    @FindBy(id = "btn-aplicar-cupom")
    private WebElement aplicarCupom;

    @FindBy(id = "btn-adicionar-cartao")
    private WebElement adicionarCartao;

    @FindBy(id = "btn-finalizar-pix")
    private WebElement finalizarPix;

    private WebElement cardName;
    private WebElement cardNumber;
    private WebElement cardExpireDate;
    private WebElement cardCVV;
    private WebElement cardConfirm;

    public void validarPaginaFormaPagamento() {
        driverWeb.waitPageLoad("/payment-device-method", 60);
        driverWeb.actionPause(2000);
        PageFactory.initElements(driverWeb.getDriver(), this);

        Assert.assertTrue("Campo cupom deve estar vazio ao abrir a pagina", cupom.getAttribute("value").isEmpty());
        validateElementActiveVisible(cupom);
        validateElementActiveVisible(aplicarCupom);
        validateElementActiveVisible(adicionarCartao);

        Assert.assertFalse(finalizarPix.isDisplayed());
    }

    public void validarPaginaFormaPagamentoAcessorios() {
        driverWeb.waitPageLoad("/payment-device-method", 10);
        driverWeb.actionPause(2000);
        PageFactory.initElements(driverWeb.getDriver(), this);

        validateElementActiveVisible(adicionarCartao);

        Assert.assertFalse(finalizarPix.isDisplayed());
    }

    public void preencherCupom(String voucher) {
        driverWeb.sendKeys(cupom, voucher);
    }

    public void clicarAplicarCupom() {
        driverWeb.javaScriptClick(aplicarCupom);
    }

    public void validarAplicarCupom(String voucher) {
        //Botão Remover é injetado no html
        validateElementActiveVisible(driverWeb.waitElementPresence("//button[@data-analytics-custom-title='aplicar_cupom']", 10));

        //Botão some do html após aplicar o cupom
        Assert.assertNull(driverWeb.findById("btn-aplicar-cupom"));

        //Campo de texto
        Assert.assertFalse(cupom.isEnabled());
        Assert.assertEquals(voucher, cupom.getAttribute("value"));

        //Mensagem de sucesso
        validateElementText("Código do cupom aplicado com sucesso!", driverWeb.findByXpath("//*[contains(@class, 'js-voucher-msg')]"));
    }

    public void clicarAdicionarCartao() {
        driverWeb.javaScriptClick(adicionarCartao);
    }

    public void validarIframe() {
        driverWeb.getDriver().switchTo().frame(driverWeb.findById("credit-card-payment-iframe")); //Acessa o iframe
        cardName = driverWeb.waitElementPresence("//*[@id='root']//input[contains(@name, 'name')]", 30);
        cardNumber = driverWeb.findById("cardNumber");
        cardExpireDate = driverWeb.findById("cardExpiration");
        cardCVV = driverWeb.findById("cardSecurityCode");
        cardConfirm = driverWeb.findById("validateButton");

        List<WebElement> cardElements = List.of(cardName, cardNumber, cardExpireDate, cardCVV);
        cardElements.forEach(e -> {
            validateElementActiveVisible(e);
            Assert.assertTrue("Campo deve estar vazio ao carregar o iframe", e.getAttribute("value").isEmpty());
        });

        validateElementActiveVisible(cardConfirm);
    }

    public void preencherDadosCartao(String name, String number, String date, String cvv, String installments) {
        driverWeb.sendKeys(cardName, name);
        driverWeb.sendKeys(cardNumber, number);
        driverWeb.sendKeys(cardExpireDate, date);
        driverWeb.sendKeys(cardCVV, cvv);

        //Select só aparece após preencher os dados anteriores
        Select cardInstallments = new Select(driverWeb.waitElementPresence("//*[@id='root']//select[contains(@name, 'comboParcelas')]", 10));
        cardInstallments.selectByValue(installments);
    }

    public void clicarConfirmarCartao() {
        driverWeb.javaScriptClick(cardConfirm);
        driverWeb.getDriver().switchTo().defaultContent(); //Sai do iframe
    }

    public void clicarFinalizarPix() {
        driverWeb.javaScriptClick(finalizarPix);
    }
}
