package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

import java.util.List;

import static pages.ComumPage.validateElementActiveVisible;

@Component
@ScenarioScope
public class FormaPagamentoPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    FormaPagamentoPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
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
        driverQA.waitPageLoad("/payment-device-method", 60);
        driverQA.actionPause(2000);
        PageFactory.initElements(driverQA.getDriver(), this);

        Assert.assertEquals("Campo cupom deve estar vazio ao abrir a pagina", cupom.getAttribute("value"), "");
        validateElementActiveVisible(cupom);
        validateElementActiveVisible(aplicarCupom);
        validateElementActiveVisible(adicionarCartao);

        Assert.assertFalse(finalizarPix.isDisplayed());
    }

    public void preencherCupom(String voucher) {
        driverQA.actionSendKeys(cupom, voucher);
    }

    public void clicarAplicarCupom() {
        driverQA.javaScriptClick(aplicarCupom);
    }

    public void clicarAdicionarCartao() {
        driverQA.javaScriptClick(adicionarCartao);
    }

    public void validarIframe() {
        driverQA.getDriver().switchTo().frame(driverQA.findById("credit-card-payment-iframe")); //Acessa o iframe
        cardName = driverQA.waitElementPresence("//*[@id='root']//input[contains(@name, 'name')]", 30);
        cardNumber = driverQA.findById("cardNumber");
        cardExpireDate = driverQA.findById("cardExpiration");
        cardCVV = driverQA.findById("cardSecurityCode");
        cardConfirm = driverQA.findById("validateButton");

        List<WebElement> cardElements = List.of(cardName, cardNumber, cardExpireDate, cardCVV);
        cardElements.forEach(e -> {
            validateElementActiveVisible(e);
            Assert.assertTrue("Campo deve estar vazio ao carregar o iframe", e.getAttribute("value").isEmpty());
        });

        validateElementActiveVisible(cardConfirm);
    }

    public void preencherDadosCartao(String name, String number, String date, String cvv, String installments) {
        driverQA.actionSendKeys(cardName, name);
        driverQA.actionSendKeys(cardNumber, number);
        driverQA.actionSendKeys(cardExpireDate, date);
        driverQA.actionSendKeys(cardCVV, cvv);

        //Select só aparece após preencher os dados anteriores
        Select cardInstallments = new Select(driverQA.waitElementPresence("//*[@id='root']//select[contains(@name, 'comboParcelas')]", 10));
        cardInstallments.selectByValue(installments);
    }

    public void clicarConfirmarCartao() {
        driverQA.javaScriptClick(cardConfirm);
        driverQA.getDriver().switchTo().defaultContent(); //Sai do iframe
    }

    public void clicarFinalizarPix() {
        driverQA.javaScriptClick(finalizarPix);
    }
}
