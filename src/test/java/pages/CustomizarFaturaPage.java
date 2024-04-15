package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import support.DriverQA;

import static pages.ComumPage.isDebitPaymentFlow;

import java.util.List;
import java.util.function.Consumer;

public class CustomizarFaturaPage {
    private final DriverQA driverQA;

    public CustomizarFaturaPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private Boolean isComboFlow = false;
    private WebElement abaDebito;
    private WebElement abaBoleto;
    private Select banco;
    private WebElement agencia;
    private WebElement conta;
    private WebElement whatsappDebit;
    private WebElement emailDebit;
    private WebElement correiosDebit;
    private WebElement whatsappTicket;
    private WebElement emailTicket;
    private WebElement correiosTicket;
    public boolean isThabFlow = false;

    public void validarPaginaCustomizarFatura() {
        driverQA.waitPageLoad("/checkout/multi/payment-method", 60);
    }

    public void validarPaginaTermosCombo() {
        driverQA.waitPageLoad("checkout/multi/terms-and-conditions", 60);
        isComboFlow = true;
    }

    private void validarCamposDebito() {
        banco = new Select(driverQA.findElement("slc-banco", "id"));
        agencia = driverQA.findElement("slc-agencia", "id");
        conta = driverQA.findElement("slc-conta", "id");

        Assert.assertEquals(banco.getFirstSelectedOption().getText(), "Banco");

        Assert.assertEquals(agencia.getAttribute("value"), "");
        Assert.assertFalse(agencia.isEnabled());

        Assert.assertEquals(conta.getAttribute("value"), "");
        Assert.assertFalse(conta.isEnabled());
    }

    public void validarMeiosPagamento(Boolean exibe) {
        abaDebito = driverQA.findElement("tab-debito-automatico", "id");
        abaBoleto = driverQA.findElement("tab-boleto", "id");

        if (exibe) { //fluxo gross ou base com pagamento boleto e migra pré-ctrl
            if (abaDebito.findElement(By.tagName("input")).isSelected()) {
                Assert.assertFalse(abaBoleto.isSelected());
                validarCamposDebito();
                isDebitPaymentFlow = true;
            } else {
                Assert.assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
                isDebitPaymentFlow = false;
            }
        } else { //fluxo base - cliente já é débito / combo / THAB
            Assert.assertNull(abaDebito);
            Assert.assertNull(abaBoleto);
            isDebitPaymentFlow = !isThabFlow;
        }
    }

    public void validarTiposFatura(Boolean exibe) {
        whatsappDebit = driverQA.findElement("rdn-whatsapp-payment-clarodebitpaymentinfo", "id");
        emailDebit = driverQA.findElement("rdn-e-mail-payment-clarodebitpaymentinfo", "id");
        correiosDebit = driverQA.findElement("rdn-correios-payment-clarodebitpaymentinfo", "id");

        whatsappTicket = driverQA.findElement("rdn-whatsapp-payment-claroticketpaymentinfo", "id");
        emailTicket = driverQA.findElement("rdn-e-mail-payment-claroticketpaymentinfo", "id");
        correiosTicket = driverQA.findElement("rdn-correios-payment-claroticketpaymentinfo", "id");

        Consumer<Boolean> assertDebit = isDisplayed -> {
            if (isDisplayed) {
                Assert.assertTrue(whatsappDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertTrue(emailDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertTrue(correiosDebit.findElement(By.xpath("..")).isDisplayed());
            } else {
                Assert.assertFalse(whatsappDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse(emailDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse(correiosDebit.findElement(By.xpath("..")).isDisplayed());
            }
        };

        Consumer<Boolean> assertTicket = isDisplayed -> {
            if (isDisplayed) {
                Assert.assertTrue(whatsappTicket.findElement(By.xpath("..")).isDisplayed());
                Assert.assertTrue(emailTicket.findElement(By.xpath("..")).isDisplayed());
                if (isThabFlow) {
                    Assert.assertNull(correiosTicket);
                } else {
                    Assert.assertTrue(correiosTicket.findElement(By.xpath("..")).isDisplayed());
                }
            } else {
                Assert.assertFalse(whatsappTicket.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse(emailTicket.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse(correiosTicket.findElement(By.xpath("..")).isDisplayed());
            }
        };

        Runnable assertDebitNull = () -> {
            Assert.assertNull(whatsappDebit);
            Assert.assertNull(emailDebit);
            Assert.assertNull(correiosDebit);
        };

        Runnable assertTicketNull = () -> {
            Assert.assertNull(whatsappTicket);
            Assert.assertNull(emailTicket);
            Assert.assertNull(correiosTicket);
        };

        if (exibe) { //fluxo gross ou base com fatura impressa e migra pré-ctrl
            if (isDebitPaymentFlow) {
                assertDebit.accept(true);
                assertTicket.accept(false);
            } else {
                assertTicket.accept(true);
                assertDebit.accept(false);
            }
        } else { //fluxo base com fatura digital ou combo
            if (!isComboFlow && ((JavascriptExecutor) driverQA.getDriver()).executeScript("return ACC.processType").toString().equals("MIGRATE")) { //existe (oculto) no html apenas as opções do método de pagamento atual
                if (isDebitPaymentFlow) {
                    assertDebit.accept(false);
                    assertTicketNull.run();
                } else {
                    assertTicket.accept(false);
                    assertDebitNull.run();
                }
            } else { //EXCHANGE e EXCHANGE_PROMO - troca e troca de promo (não existe no html)
                assertDebitNull.run();
                assertTicketNull.run();
            }
        }
    }

    public void validarDatasVencimento(Boolean exibe) {
        WebElement datas = driverQA.findElement("datas-vencimento", "id");

        if (exibe) { //fluxo gross ou base em migra pré-ctrl e ctrl-pós
            Assert.assertNotNull(datas);

            List<WebElement> dias = datas.findElements(By.tagName("span"));
            Assert.assertEquals(6, dias.size()); // seis datas devem ser exibidas

            int checked = 0;
            for (WebElement diaVencimento : dias) {
                WebElement input = diaVencimento.findElement(By.tagName("input"));
                WebElement label = diaVencimento.findElement(By.tagName("label"));
                String txtLabel = label.getText();

                Assert.assertTrue(label.isDisplayed());
                Assert.assertTrue(Integer.parseInt(txtLabel) >= 1 && Integer.parseInt(txtLabel) <= 31); //números exibidos devem ser dias do mês
                if (input.isSelected()) checked++;
            }

            Assert.assertEquals(1, checked); //apenas uma data selecionada
        } else { //fluxo base em troca de plano mesma plataforma (ctrl-ctrl e pos-pos)
            Assert.assertNull(datas);
        }
    }

    public void selecionarPagamento(String paymentType) {
        if (paymentType.equals("Débito")) {
            driverQA.JavaScriptClick(abaDebito.findElement(By.tagName("div")));
            Assert.assertTrue(abaDebito.findElement(By.tagName("input")).isSelected());
            Assert.assertFalse(abaBoleto.findElement(By.tagName("input")).isSelected());

            validarCamposDebito();
            isDebitPaymentFlow = true;
        } else {
            driverQA.JavaScriptClick(abaBoleto.findElement(By.tagName("div")));
            Assert.assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
            Assert.assertFalse(abaDebito.findElement(By.tagName("input")).isSelected());

            isDebitPaymentFlow = false;
        }
    }

    public void selecionarTipoFatura(String fatura) {
        switch (fatura) {
            case "Whatsapp":
                driverQA.JavaScriptClick(isDebitPaymentFlow ? whatsappDebit : whatsappTicket);
            case "E-mail":
                driverQA.JavaScriptClick(isDebitPaymentFlow ? emailDebit : emailTicket);
            case "Correios":
                driverQA.JavaScriptClick(isDebitPaymentFlow ? correiosDebit : correiosTicket);
        }
    }

    public void preencherDadosBancarios() {
        banco.selectByValue("001");
        Assert.assertEquals(banco.getFirstSelectedOption().getText(), "001 - Banco do Brasil");

        Assert.assertTrue(agencia.isEnabled());
        Assert.assertTrue(conta.isEnabled());
        driverQA.actionSendKeys(agencia, "6523");
        driverQA.actionSendKeys(conta, "1443038");
    }

    public void selecionarDataVencimento(String data) {
        //TODO
    }

    public void aceitarTermos() {
        WebElement termos = driverQA.findElement(isComboFlow ? "chk-termos" : isDebitPaymentFlow ? "chk-termos-clarodebitpayment" : "chk-termos-claroticketpayment", "id");
        Assert.assertFalse(termos.isSelected());
        driverQA.JavaScriptClick(termos);
        Assert.assertTrue(termos.isSelected());
    }

    public void clicarContinuar() {
        driverQA.JavaScriptClick("btn-continuar", "id");
    }

    public void clickOkEntendi() {
        driverQA.JavaScriptClick("btn-multa-entendi", "id");
    }

    public void clickNaoConcordo() {
        driverQA.JavaScriptClick("btn-multa-nao-concordo", "id");
    }

    public void direcionadoParaMulta() {
        driverQA.waitPageLoad("claro/pt/checkout/multi/payment-method/add", 10);
        Assert.assertNotNull(driverQA.findElement("txt-mensagem-multa", "id"));
    }

    public void direcionadoParaTHAB() {
        driverQA.waitPageLoad("checkout/multi/payment-method/add", 10);

        Assert.assertNotNull(driverQA.findElement("txt-controle-antecipado", "id"));
    }
}