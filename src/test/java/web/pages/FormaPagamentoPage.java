package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.support.utils.DriverWeb;

import java.util.List;

import static org.junit.Assert.*;
import static web.pages.ComumPage.*;

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

    @FindBy(xpath = "//*[@id='tab-cartao']/input")
    private WebElement tabCartao;

    @FindBy(xpath = "//*[@id='tab-pix']/input")
    private WebElement tabPix;

    @FindBy(id = "btn-adicionar-cartao")
    private WebElement adicionarCartao;

    @FindBy(xpath = "//*[@id='utilizar-claro-clube']//input")
    private WebElement usarClaroClube;

    @FindBy(id = "btn-finalizar-pix")
    private WebElement finalizarPix;

    private WebElement cardName;
    private WebElement cardNumber;
    private WebElement cardExpireDate;
    private WebElement cardCVV;
    private WebElement cardConfirm;

    public void validarPaginaFormaPagamento(CartOrder cart) {
        driverWeb.waitPageLoad("/payment-device-method", 60);
        driverWeb.actionPause(3000);

        PageFactory.initElements(driverWeb.getDriver(), this);

        //Cupom
        assertTrue("Campo cupom deve estar vazio ao abrir a pagina", cupom.getAttribute("value").isEmpty());
        validateElementActiveVisible(cupom);
        validateElementActiveVisible(aplicarCupom);
        validateElementActiveVisible(adicionarCartao);

        //Claro Clube
        double claroClubBalance = cart.getUser().getClaroClubBalance();

        if (cart.isDeviceCart() && claroClubBalance > 0D) {
            driverWeb.waitElementVisible(usarClaroClube.findElement(By.xpath("..")), 10); //Pai input (toggle)
            assertFalse(usarClaroClube.isSelected()); //Toggle na posição off

            double deviceTotalPrice = cart.getEntry(cart.getDevice().getCode()).getTotalPrice();
            double claroClubMaxDiscount = Math.min(claroClubBalance, deviceTotalPrice); //Caso possua muita pontuação, o desconto máximo será o próprio valor do Aparelho (pagamento 100% Claro Clube)
            String claroClubeRef = String.format("Utilizar meus créditos Claro Clube de R$ %s nesta compra", formatPrice(claroClubMaxDiscount));

            validateElementText(claroClubeRef, driverWeb.findByXpath("//*[@id='utilizar-claro-clube']//p"));
        }

        validateElementText("Cartão de crédito", tabCartao.findElement(By.xpath("..")));
        validateElementText("Pix", tabPix.findElement(By.xpath(".."))); //Em caso de contingência do Cart, não será exibido
        assertFalse(finalizarPix.isDisplayed());
    }

    public void preencherCupom(String voucher) {
        driverWeb.sendKeys(cupom, voucher);
    }

    public void clicarAplicarCupom() {
        driverWeb.javaScriptClick(aplicarCupom);
    }

    public void validarCupomAplicado(String voucher) {
        //Botão [Remover] é injetado no html
        validateElementActiveVisible(driverWeb.waitElementPresence("//button[@data-analytics-custom-title='aplicar_cupom']", 10));

        //Botão [Aplicar] some do html após adicionar o cupom
        assertNull(driverWeb.findById("btn-aplicar-cupom"));

        //Campo de texto do cupom é desativado
        assertFalse(cupom.isEnabled());
        assertEquals(voucher, cupom.getAttribute("value"));

        //Mensagem de sucesso é exibida
        validateElementText("Código do cupom aplicado com sucesso!", driverWeb.findByXpath("//*[contains(@class, 'js-voucher-msg')]"));
    }

    public void clicarUsarClaroClube() {
        driverWeb.javaScriptClick(usarClaroClube.findElement(By.xpath("../span"))); //Click só funciona no <span>

        driverWeb.actionPause(4000); //Página recarrega
        assertTrue(usarClaroClube.isSelected());
    }

    public void clicarAdicionarCartao() {
        driverWeb.javaScriptClick(adicionarCartao);
    }

    public void clicarAbaPix() {
        driverWeb.javaScriptClick(tabPix);
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
            assertTrue("Campo deve estar vazio ao carregar o iframe", e.getAttribute("value").isEmpty());
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

        //Valida parcelas >= 10 reais
        List<WebElement> installmentList = cardInstallments.getOptions();
        installmentList.remove(0); //Remove primeira opção "Selecionar parcelas"

        installmentList.forEach(i -> {
            String installmentPriceText = StringUtils.normalizeSpace(i.getText())
                    .split(" ")[1]
                    .replace(".", "")
                    .replace(",", ".");
            double installmentPrice = Double.parseDouble(installmentPriceText);

            assertTrue(installmentPrice >= 10D);
        });

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
