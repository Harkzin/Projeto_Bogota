package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.utils.Constants;
import web.support.utils.Constants.PlanPaymentMode;
import web.support.utils.DriverQA;

import static web.pages.ComumPage.*;
import static web.support.utils.Constants.ProcessType.MIGRATE;
import static web.support.api.RestAPI.getBankAccount;

import java.util.*;
import java.util.function.Consumer;

@Component
@ScenarioScope
public class CustomizarFaturaPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public CustomizarFaturaPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private boolean isComboFlow = false;
    private boolean isDebitClient;
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

    private WebElement concordo;

    private WebElement naoConcordo;

    public void validarPaginaCustomizarFatura() {
        driverQA.waitPageLoad("/checkout/multi/payment-method", 60);

        abaDebito = driverQA.findElement("tab-debito", "id");
        abaBoleto = driverQA.findElement("tab-boleto", "id");
    }

    public void validarPagiaCustomizarFaturaThab() {
        driverQA.waitPageLoad("checkout/multi/payment-method/add", 10);
    }

    public void validarPaginaTermosCombo() {
        driverQA.waitPageLoad("checkout/multi/terms-and-conditions", 60);
        isComboFlow = true;
    }

    private void validarCamposDebito() {
        banco = new Select(driverQA.findElement("slc-banco", "id"));
        agencia = driverQA.findElement("txt-agencia", "id");
        conta = driverQA.findElement("txt-conta", "id");

        Assert.assertEquals(banco.getFirstSelectedOption().getText(), "Banco");

        Assert.assertEquals(agencia.getAttribute("value"), "");
        Assert.assertFalse(agencia.isEnabled());
        Assert.assertTrue(agencia.isDisplayed());

        Assert.assertEquals(conta.getAttribute("value"), "");
        Assert.assertFalse(conta.isEnabled());
        Assert.assertTrue(conta.isDisplayed());
    }

    public void validarExibeMeiosPagamento(PlanPaymentMode payment) { //Exibe nos fluxos: gross / base - cliente pagamento boleto / migra pré-ctrl
        isDebitClient = false;

        switch (payment) {
            case DEBIT -> { //Fluxo está sendo débito. Default.
                Assert.assertTrue(abaDebito.findElement(By.tagName("input")).isSelected());
                Assert.assertFalse(abaBoleto.findElement(By.tagName("input")).isSelected());
                validarCamposDebito();
            }
            case TICKET -> { //Fluxo está sendo boleto. Cliente selecionou antes na PDP ou PLP.
                Assert.assertFalse(abaDebito.findElement(By.tagName("input")).isSelected());
                Assert.assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
            }
        }

        Assert.assertTrue(abaBoleto.findElement(By.tagName("div")).isDisplayed());
        Assert.assertTrue(abaDebito.findElement(By.tagName("div")).isDisplayed());
    }

    public boolean validarNaoExibeMeiosPagamento() { //Fluxos: base - cliente já é débito, combo ou THAB
        isDebitClient = true; //TODO caso combo = ?
        Assert.assertNull(abaDebito);
        Assert.assertNull(abaBoleto);
        return !cartOrder.thab && !isComboFlow; //TODO combo funcionará apenas boleto
    }

    public void validarTiposFatura(boolean exibe, boolean isDebitPaymentFlow, boolean isThab) {
        whatsappDebit = driverQA.findElement("rdn-whatsapp-debit", "id");
        emailDebit = driverQA.findElement("rdn-email-debit", "id");
        correiosDebit = driverQA.findElement("rdn-correios-debit", "id");

        whatsappTicket = driverQA.findElement("rdn-whatsapp-ticket", "id");
        emailTicket = driverQA.findElement("rdn-email-ticket", "id");
        correiosTicket = driverQA.findElement("rdn-correios-ticket", "id");

        Consumer<Boolean> assertDebit = isDisplayed -> {
            if (isDisplayed) {
                Assert.assertTrue("Exibe fatura WhatsApp débito", whatsappDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertTrue("Exibe fatura E-mail débito", emailDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertTrue("Exibe fatura Correios débito", correiosDebit.findElement(By.xpath("..")).isDisplayed());
            } else {
                Assert.assertFalse("Não exibe fatura WhatsApp débito", whatsappDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse("Não exibe fatura E-mail débito", emailDebit.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse("Não exibe fatura Correios débito", correiosDebit.findElement(By.xpath("..")).isDisplayed());
            }
        };

        Consumer<Boolean> assertTicket = isDisplayed -> {
            if (isDisplayed) {
                Assert.assertTrue("Exibe fatura WhatsApp boleto", whatsappTicket.findElement(By.xpath("..")).isDisplayed());
                Assert.assertTrue("Exibe fatura E-mail boleto", emailTicket.findElement(By.xpath("..")).isDisplayed());
                if (isThab) {
                    Assert.assertNull("Não deve existir no html", correiosTicket);
                } else {
                    Assert.assertTrue("Exibe fatura Correios boleto", correiosTicket.findElement(By.xpath("..")).isDisplayed());
                }
            } else {
                Assert.assertFalse("Não exibe fatura WhatsApp boleto", whatsappTicket.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse("Não exibe fatura E-mail boleto", emailTicket.findElement(By.xpath("..")).isDisplayed());
                Assert.assertFalse("Não exibe fatura Correios boleto", correiosTicket.findElement(By.xpath("..")).isDisplayed());
            }
        };

        Runnable assertDebitNull = () -> {
            Assert.assertNull("Não deve existir no html", whatsappDebit);
            Assert.assertNull("Não deve existir no html", emailDebit);
            Assert.assertNull("Não deve existir no html", correiosDebit);
        };

        Runnable assertTicketNull = () -> {
            Assert.assertNull("Não deve existir no html", whatsappTicket);
            Assert.assertNull("Não deve existir no html", emailTicket);
            Assert.assertNull("Não deve existir no html", correiosTicket);
        };

        if (exibe) { //fluxo gross, fluxo base com fatura impressa, migra pré-ctrl e thab
            if (isDebitPaymentFlow) {
                assertDebit.accept(true);
                assertTicket.accept(false);
            } else {
                assertTicket.accept(true);
                if (!isThab) {
                    assertDebit.accept(false);
                } else {
                    assertDebitNull.run();
                }
            }
        } else { //fluxo base com fatura digital ou combo
            if (!isComboFlow && (cartOrder.essential.processType == MIGRATE)) { //fluxo combo ou migração
                assertDebit.accept(false); //opções para débito existem no html, ocultas no front

                    if (isDebitClient) { //fluxo débito com cliente débito - existe no html apenas as opções para débito, ocultas no front
                        assertTicketNull.run();
                    } else { //fluxo débito com cliente boleto - também existe no html as opções para boleto, ocultas no front
                        assertTicket.accept(false);
                    }
            } else { //EXCHANGE e EXCHANGE_PROMO - fluxo de troca de plano, troca de promo ou combo (não existem no html)
                assertDebitNull.run();
                assertTicketNull.run();
            }
        }
    }

    public void validarDatasVencimento(boolean exibe, boolean isDebitPaymentFlow) {
        WebElement datasDebit = driverQA.findElement("datas-vencimento-debit", "id");
        WebElement datasTicket = driverQA.findElement("datas-vencimento-ticket", "id");

        if (exibe) { //fluxo gross ou base em migra pré-ctrl e ctrl-pós
            List<WebElement> dias;

            if (isDebitPaymentFlow) {
                Assert.assertTrue(datasDebit.isDisplayed());
                Assert.assertFalse(datasTicket.isDisplayed());

                dias = datasDebit.findElements(By.tagName("span"));
            } else {
                Assert.assertFalse(datasDebit.isDisplayed());
                Assert.assertTrue(datasTicket.isDisplayed());

                dias = datasTicket.findElements(By.tagName("span"));
            }

            Assert.assertEquals("Seis datas de vencimento devem existir", 6, dias.size());

            int selected = 0;
            for (WebElement diaVencimento : dias) {
                WebElement input = diaVencimento.findElement(By.tagName("input"));
                WebElement label = diaVencimento.findElement(By.tagName("label"));
                int dia = Integer.parseInt(label.getText());

                Assert.assertTrue(label.isDisplayed());
                Assert.assertTrue("Número exibido deve ser umm dia do mês", dia >= 1 && dia <= 31);
                if (input.isSelected()) selected++;
            }

            Assert.assertEquals("Apenas uma data selecionada", 1, selected);
        } else { //fluxo base em troca de plano mesma plataforma (ctrl-ctrl e pos-pos)
            Assert.assertNull(datasDebit);
            Assert.assertNull(datasTicket);
        }
    }

    public void selecionarDebito() {
        driverQA.javaScriptClick(abaDebito.findElement(By.tagName("div")));
        Assert.assertTrue(abaDebito.findElement(By.tagName("input")).isSelected());
        Assert.assertFalse(abaBoleto.findElement(By.tagName("input")).isSelected());

        driverQA.actionPause(2000);
        validarCamposDebito();
    }

    public void selecionarBoleto() {
        driverQA.javaScriptClick(abaBoleto.findElement(By.tagName("div")));
        Assert.assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
        Assert.assertFalse(abaDebito.findElement(By.tagName("input")).isSelected());

        driverQA.actionPause(2000);
    }

    public void selecionarTipoFatura(Constants.InvoiceType invoiceType, boolean isDebitPaymentFlow) {
        switch (invoiceType) {
            case WHATSAPP -> driverQA.javaScriptClick(isDebitPaymentFlow ? whatsappDebit : whatsappTicket);
            case EMAIL -> driverQA.javaScriptClick(isDebitPaymentFlow ? emailDebit : emailTicket);
            case PRINTED -> driverQA.javaScriptClick(isDebitPaymentFlow ? correiosDebit : correiosTicket);
        }
        driverQA.actionPause(1500);
    }

    public void validarPrecoFaturaImpressaDebito(String expectedPrice) {
        WebElement pricePrinted = driverQA
                .findElement("//*[contains(@class, 'col-layout-plan') and not(contains(@class, 'visible-mobile'))]/div/div//span[contains(@class, 'js-entry-price-plan')]", "xpath");
        validateElementText(expectedPrice, pricePrinted); //Preço débito fatura impressa = boleto (sem desconto)
    }

    public void preencherDadosBancarios() {
        HashMap<Integer, String> banks = new HashMap<>();
        banks.put(1, "001"); //Banco do Brasil
        banks.put(2, "237"); //Bradesco
        banks.put(3, "104"); //Caixa
        banks.put(4, "341"); //Itaú
        banks.put(5, "033"); //Santander
        banks.put(6, "422"); //Safra (sem gerador de conta)
        banks.put(7, "041"); //Banrisul (sem gerador de conta)
        banks.put(8, "389"); //Mercantil (sem gerador de conta)

        int bankId = new Random().nextInt(8) + 1; //Random de 1 a 8

        List<String> bankAccount = new ArrayList<>();

        if (bankId <= 5) { //API
            bankAccount = getBankAccount(String.valueOf(bankId));
        } else { //6 ~ 8 - Dados fixos - Não tem API
            bankAccount = switch (bankId) {
                case 6 -> Arrays.asList("0340", "00252116");
                case 7 -> Arrays.asList("0131", "251134003");
                case 8 -> Arrays.asList("0103", "12345678");
                default -> bankAccount;
            };
        }

        banco.selectByValue(banks.get(bankId));

        if (bankId == 3) { //CAIXA
            WebElement caixaAccountType = driverQA.findElement("slc-tipo-conta", "id");
            Select caixaAccountTypeSelect = new Select(caixaAccountType);
            driverQA.waitElementToBeClickable(caixaAccountType, 1);

            String accountId = bankAccount.get(1).substring(0, 2);

            //A API retorna vários tipos de conta CAIXA diferentes, só 4 delas são aceitas no Ecomm
            while (!accountId.matches("(001|006|013|023)")) {
                bankAccount = getBankAccount("3");
                accountId = bankAccount.get(1).substring(0, 3);
            }

            bankAccount.set(1, bankAccount.get(1).substring(3));
            caixaAccountTypeSelect.selectByValue(accountId);
        }

        driverQA.waitElementToBeClickable(agencia, 1);
        driverQA.actionSendKeys(agencia, bankAccount.get(0)); //.findElement(By.xpath("following-sibling::label"))
        Assert.assertEquals("Campo agência preenchido", bankAccount.get(0), agencia.getAttribute("value"));

        driverQA.actionSendKeys(conta, bankAccount.get(1));
        Assert.assertEquals("Campo conta preenchido", bankAccount.get(1), conta.getAttribute("value"));
    }

    public void selecionarDataVencimento(String data) {
        //TODO
    }

    public void aceitarTermos(boolean isDebitPaymentFlow) {
        WebElement termos = driverQA.findElement(isComboFlow ? "chk-termos" : isDebitPaymentFlow ? "chk-termos-clarodebitpayment" : "chk-termos-claroticketpayment", "id");
        Assert.assertFalse(termos.isSelected());

        driverQA.javaScriptClick(termos);
        Assert.assertTrue(termos.isSelected());
    }

    public void clicarContinuar() {
        driverQA.javaScriptClick("btn-continuar", "id");
    }

    public void clickOkEntendi() {
        driverQA.javaScriptClick("btn-multa-entendi", "id");
    }

    public void clickNaoConcordo() {
        driverQA.javaScriptClick(naoConcordo);
    }

    public void validarPaginaMulta() {
        driverQA.waitPageLoad("claro/pt/checkout/multi/payment-method/add", 10);

        concordo = driverQA.findElement("btn-multa-concordo", "id");
        naoConcordo = driverQA.findElement("btn-multa-nao-concordo", "id");

        Assert.assertTrue(driverQA.findElement("txt-mensagem-multa", "id").isDisplayed());

        Assert.assertTrue(concordo.isDisplayed());
        Assert.assertTrue(naoConcordo.isDisplayed());


    }

    public void clicarConcordo() {
        driverQA.javaScriptClick(concordo);
    }
}