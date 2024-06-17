package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.Constants;
import support.utils.Constants.PlanPaymentMode;
import support.utils.DriverQA;

import static pages.ComumPage.*;
import static support.utils.Constants.PlanPaymentMode.*;
import static support.utils.Constants.ProcessType.MIGRATE;
import static support.api.RestAPI.getBankAccount;

import java.util.*;
import java.util.function.Consumer;

@Component
public class CustomizarFaturaPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public CustomizarFaturaPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private boolean isComboFlow = false;
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

    public void validarMeiosPagamento(PlanPaymentMode payment) { //Exibe nos fluxos: gross / base - cliente pagamento boleto / migra pré-ctrl
        switch (payment) {
            case DEBIT: //Fluxo está sendo débito. Default.
                Assert.assertTrue(abaDebito.findElement(By.tagName("input")).isSelected());
                Assert.assertFalse(abaBoleto.findElement(By.tagName("input")).isSelected());
                validarCamposDebito();
                break;
            case TICKET: //Fluxo está sendo boleto. Cliente selecionou antes na PDP ou PLP.
                Assert.assertFalse(abaDebito.findElement(By.tagName("input")).isSelected());
                Assert.assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
                break;
        }

        Assert.assertTrue(abaBoleto.findElement(By.tagName("div")).isDisplayed());
        Assert.assertTrue(abaDebito.findElement(By.tagName("div")).isDisplayed());
    }

    public void validarNaoExibeMeiosPagamento() { //Fluxos: base - cliente já é débito / combo / THAB
        Assert.assertNull(abaDebito);
        Assert.assertNull(abaBoleto);
        //cartOrder.isDebitPaymentFlow = !cartOrder.thab; //TODO caso combo = ??
    }

    public void validarTiposFatura(boolean exibe) {
        whatsappDebit = driverQA.findElement("rdn-whatsapp-debit", "id");
        emailDebit = driverQA.findElement("rdn-email-debit", "id");
        correiosDebit = driverQA.findElement("rdn-correios-debit", "id");

        whatsappTicket = driverQA.findElement("rdn-whatsapp-ticket", "id");
        emailTicket = driverQA.findElement("rdn-email-ticket", "id");
        correiosTicket = driverQA.findElement("rdn-correios-ticket", "id");

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
                if (cartOrder.thab) {
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

        if (exibe) { //fluxo gross, fluxo base com fatura impressa, migra pré-ctrl e thab
            if (cartOrder.isDebitPaymentFlow) {
                assertDebit.accept(true);
                assertTicket.accept(false);
            } else {
                assertTicket.accept(true);
                if (!cartOrder.thab) {
                    assertDebit.accept(false);
                } else {
                    assertDebitNull.run();
                }
            }
        } else { //fluxo base com fatura digital ou combo
            if (!isComboFlow && (cartOrder.essential.processType == MIGRATE)) {
                if (cartOrder.isDebitPaymentFlow) { //existe (oculto) no html apenas as opções para débito
                    assertDebit.accept(false);
                    assertTicketNull.run();
                } else { //existe oculto no html as duas versões de cada
                    assertTicket.accept(false);
                    assertDebit.accept(false);
                }
            } else { //EXCHANGE e EXCHANGE_PROMO - troca e troca de promo ou combo (não existe no html)
                assertDebitNull.run();
                assertTicketNull.run();
            }
        }
    }

    public void validarDatasVencimento(boolean exibe) {
        WebElement datasDebit = driverQA.findElement("datas-vencimento-debit", "id");
        WebElement datasTicket = driverQA.findElement("datas-vencimento-ticket", "id");

        if (exibe) { //fluxo gross ou base em migra pré-ctrl e ctrl-pós
            List<WebElement> dias;

            if (cartOrder.isDebitPaymentFlow) {
                Assert.assertTrue(datasDebit.isDisplayed());
                Assert.assertFalse(datasTicket.isDisplayed());

                dias = datasDebit.findElements(By.tagName("span"));
            } else {
                Assert.assertFalse(datasDebit.isDisplayed());
                Assert.assertTrue(datasTicket.isDisplayed());

                dias = datasTicket.findElements(By.tagName("span"));
            }

            Assert.assertEquals("Seis datas de vencimento devem existir", 6, dias.size());

            int checked = 0;
            for (WebElement diaVencimento : dias) {
                WebElement input = diaVencimento.findElement(By.tagName("input"));
                WebElement label = diaVencimento.findElement(By.tagName("label"));
                int dia = Integer.parseInt(label.getText());

                Assert.assertTrue(label.isDisplayed());
                Assert.assertTrue("Número exibido deve ser umm dia do mês", dia >= 1 && dia <= 31);
                if (input.isSelected()) checked++;
            }

            Assert.assertEquals("Apenas uma data selecionada", 1, checked);
        } else { //fluxo base em troca de plano mesma plataforma (ctrl-ctrl e pos-pos)
            Assert.assertNull(datasDebit);
            Assert.assertNull(datasTicket);
        }
    }

    public void selecionarDebito() {
            driverQA.javaScriptClick(abaDebito.findElement(By.tagName("div")));
            Assert.assertTrue(abaDebito.findElement(By.tagName("input")).isSelected());
            Assert.assertFalse(abaBoleto.findElement(By.tagName("input")).isSelected());

            validarCamposDebito();
    }

    public void selecionarBoleto() {
        driverQA.javaScriptClick(abaBoleto.findElement(By.tagName("div")));
        Assert.assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
        Assert.assertFalse(abaDebito.findElement(By.tagName("input")).isSelected());
    }

    public void selecionarTipoFatura(Constants.InvoiceType invoiceType) {
        switch (invoiceType) {
            case WHATSAPP:
                driverQA.javaScriptClick(cartOrder.isDebitPaymentFlow ? whatsappDebit : whatsappTicket);
                break;
            case EMAIL:
                driverQA.javaScriptClick(cartOrder.isDebitPaymentFlow ? emailDebit : emailTicket);
                break;
            case PRINTED:
                driverQA.javaScriptClick(cartOrder.isDebitPaymentFlow ? correiosDebit : correiosTicket);
        }
    }

    public void validarPrecoFaturaImpressaDebito() {
        WebElement pricePrinted = driverQA.findElement("", "xpath");
        validateElementText(cartOrder.getPlan().getFormattedPlanPrice(false, true), pricePrinted); //Preço débito fatura impressa = boleto (sem desconto)
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
            switch (bankId) {
                case 6:
                    bankAccount = Arrays.asList("0340", "00252116");
                    break;
                case 7:
                    bankAccount = Arrays.asList("0131", "251134003");
                    break;
                case 8:
                    bankAccount = Arrays.asList("0103", "12345678");
            }
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
        driverQA.actionSendKeys(agencia, bankAccount.get(0));
        driverQA.actionSendKeys(conta, bankAccount.get(1));
    }

    public void selecionarDataVencimento(String data) {
        //TODO
    }

    public void aceitarTermos() {
        WebElement termos = driverQA.findElement(isComboFlow ? "chk-termos" : cartOrder.isDebitPaymentFlow ? "chk-termos-clarodebitpayment" : "chk-termos-claroticketpayment", "id");
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
        driverQA.javaScriptClick("btn-multa-nao-concordo", "id");
    }

    public void direcionadoParaMulta() {
        driverQA.waitPageLoad("claro/pt/checkout/multi/payment-method/add", 10);
        Assert.assertNotNull(driverQA.findElement("txt-mensagem-multa", "id"));
    }
}