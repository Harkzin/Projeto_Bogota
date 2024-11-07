package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.support.utils.Constants;
import web.support.utils.Constants.PaymentMode;
import web.support.utils.DriverWeb;

import static org.junit.Assert.*;
import static web.support.utils.Constants.ProcessType.MIGRATE;
import static web.support.api.RestAPI.getBankAccount;

import java.util.*;
import java.util.function.Consumer;

@Component
@ScenarioScope
public class CustomizarFaturaPage {

    private final DriverWeb driverWeb;
    private final CartOrder cartOrder;

    @Autowired
    public CustomizarFaturaPage(DriverWeb driverWeb, CartOrder cartOrder) {
        this.driverWeb = driverWeb;
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
    private Select diasVencimento;

    public void validarPaginaCustomizarFatura() {
        driverWeb.waitPageLoad("/checkout/multi/payment-method", 60);

        abaDebito = driverWeb.findElement("tab-debito", "id");
        abaBoleto = driverWeb.findElement("tab-boleto", "id");
    }

    public void validarPagiaCustomizarFaturaThab() {
        driverWeb.waitPageLoad("checkout/multi/payment-method/add", 10);
    }

    public void validarPaginaTermosCombo() {
        driverWeb.waitPageLoad("checkout/multi/terms-and-conditions", 60);
        isComboFlow = true;
    }

    private void validarCamposDebito() {
        banco = new Select(driverWeb.findElement("slc-banco", "id"));
        agencia = driverWeb.findElement("txt-agencia", "id");
        conta = driverWeb.findElement("txt-conta", "id");

        assertEquals("Banco", banco.getFirstSelectedOption().getText());

        assertEquals(agencia.getAttribute("value"), "");
        assertFalse(agencia.isEnabled());
        assertTrue(agencia.isDisplayed());

        assertEquals(conta.getAttribute("value"), "");
        assertFalse(conta.isEnabled());
        assertTrue(conta.isDisplayed());
    }

    public void validarExibeMeiosPagamento(PaymentMode payment) { //Exibe nos fluxos: gross / base - cliente pagamento boleto / migra pré-ctrl
        isDebitClient = false;

        switch (payment) {
            case DEBITCARD -> { //Fluxo está sendo débito. Default.
                assertTrue(abaDebito.findElement(By.tagName("input")).isSelected());
                assertFalse(abaBoleto.findElement(By.tagName("input")).isSelected());
                validarCamposDebito();
            }
            case TICKET -> { //Fluxo está sendo boleto. Cliente selecionou antes na PDP ou PLP.
                assertFalse(abaDebito.findElement(By.tagName("input")).isSelected());
                assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
            }
        }

        assertTrue(abaBoleto.findElement(By.tagName("div")).isDisplayed());
        assertTrue(abaDebito.findElement(By.tagName("div")).isDisplayed());
    }

    public boolean validarNaoExibeMeiosPagamento() { //Fluxos: base - cliente já é débito, combo ou THAB
        isDebitClient = true; //TODO caso combo = ?
        assertNull(abaDebito);
        assertNull(abaBoleto);
        return !cartOrder.isThab() && !isComboFlow; //TODO combo funcionará apenas boleto
    }

    public void validarTiposFatura(boolean exibe, boolean isDebitPaymentFlow, boolean isThab) {
        whatsappDebit = driverWeb.findElement("rdn-whatsapp-debit", "id");
        emailDebit = driverWeb.findElement("rdn-digital-debit", "id");
        correiosDebit = driverWeb.findElement("rdn-printed-debit", "id");

        whatsappTicket = driverWeb.findElement("rdn-whatsapp-ticket", "id");
        emailTicket = driverWeb.findElement("rdn-digital-ticket", "id");
        correiosTicket = driverWeb.findElement("rdn-printed-ticket", "id");

        Consumer<Boolean> assertDebit = isDisplayed -> {
            if (isDisplayed) {
                assertTrue("Exibe fatura WhatsApp débito", whatsappDebit.findElement(By.xpath("..")).isDisplayed());
                assertTrue("Exibe fatura E-mail débito", emailDebit.findElement(By.xpath("..")).isDisplayed());
                assertTrue("Exibe fatura Correios débito", correiosDebit.findElement(By.xpath("..")).isDisplayed());
            } else {
                assertFalse("Não exibe fatura WhatsApp débito", whatsappDebit.findElement(By.xpath("..")).isDisplayed());
                assertFalse("Não exibe fatura E-mail débito", emailDebit.findElement(By.xpath("..")).isDisplayed());
                assertFalse("Não exibe fatura Correios débito", correiosDebit.findElement(By.xpath("..")).isDisplayed());
            }
        };

        Consumer<Boolean> assertTicket = isDisplayed -> {
            if (isDisplayed) {
                assertTrue("Exibe fatura WhatsApp boleto", whatsappTicket.findElement(By.xpath("..")).isDisplayed());
                assertTrue("Exibe fatura E-mail boleto", emailTicket.findElement(By.xpath("..")).isDisplayed());
                if (isThab) {
                    assertNull("Não deve existir no html", correiosTicket);
                } else {
                    assertTrue("Exibe fatura Correios boleto", correiosTicket.findElement(By.xpath("..")).isDisplayed());
                }
            } else {
                assertFalse("Não exibe fatura WhatsApp boleto", whatsappTicket.findElement(By.xpath("..")).isDisplayed());
                assertFalse("Não exibe fatura E-mail boleto", emailTicket.findElement(By.xpath("..")).isDisplayed());
                assertFalse("Não exibe fatura Correios boleto", correiosTicket.findElement(By.xpath("..")).isDisplayed());
            }
        };

        Runnable assertDebitNull = () -> {
            assertNull("Não deve existir no html", whatsappDebit);
            assertNull("Não deve existir no html", emailDebit);
            assertNull("Não deve existir no html", correiosDebit);
        };

        Runnable assertTicketNull = () -> {
            assertNull("Não deve existir no html", whatsappTicket);
            assertNull("Não deve existir no html", emailTicket);
            assertNull("Não deve existir no html", correiosTicket);
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
            if (!isComboFlow && (cartOrder.getProcessType() == MIGRATE)) { //fluxo combo ou migração
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
        WebElement datasDebit = driverWeb.findById("datas-vencimento-debit");
        WebElement datasTicket = driverWeb.findById("datas-vencimento-ticket");

        if (exibe) { //Fluxo gross ou base em migra pré-ctrl e ctrl-pós
            WebElement datas;

            if (isDebitPaymentFlow) {
                datas = datasDebit;
                assertTrue(datasDebit.isDisplayed());
                assertFalse(datasTicket.isDisplayed());
            } else {
                datas = datasTicket;
                assertFalse(datasDebit.isDisplayed());
                assertTrue(datasTicket.isDisplayed());
            }

            diasVencimento = new Select(datas);
            List<WebElement> dias;
            dias = diasVencimento.getOptions();
            dias.remove(0); //Remove opção "Selecione"
            assertEquals("Seis datas de vencimento devem existir", 6, dias.size());

            dias.forEach(d -> {
                int dia = Integer.parseInt(d.getAttribute("textContent").trim());
                assertTrue("Número exibido deve ser umm dia do mês", dia >= 1 && dia <= 31);
            });
        } else { //Fluxo base em troca de plano mesma plataforma (ctrl-ctrl e pos-pos)
            assertNull(datasDebit);
            assertNull(datasTicket);
        }
    }

    public void selecionarDebito() {
        driverWeb.javaScriptClick(abaDebito.findElement(By.tagName("div")));
        assertTrue(abaDebito.findElement(By.tagName("input")).isSelected());
        assertFalse(abaBoleto.findElement(By.tagName("input")).isSelected());

        driverWeb.actionPause(3000);
        validarCamposDebito();
    }

    public void selecionarBoleto() {
        driverWeb.javaScriptClick(abaBoleto.findElement(By.tagName("div")));
        assertTrue(abaBoleto.findElement(By.tagName("input")).isSelected());
        assertFalse(abaDebito.findElement(By.tagName("input")).isSelected());

        driverWeb.actionPause(3000);
    }

    public void selecionarTipoFatura(Constants.InvoiceType invoiceType, boolean isDebitPaymentFlow) {
        switch (invoiceType) {
            case WHATSAPP -> driverWeb.javaScriptClick(isDebitPaymentFlow ? whatsappDebit : whatsappTicket);
            case DIGITAL -> driverWeb.javaScriptClick(isDebitPaymentFlow ? emailDebit : emailTicket);
            case PRINTED -> driverWeb.javaScriptClick(isDebitPaymentFlow ? correiosDebit : correiosTicket);
        }
        driverWeb.actionPause(3000);
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
            WebElement caixaAccountType = driverWeb.findElement("slc-tipo-conta", "id");
            Select caixaAccountTypeSelect = new Select(caixaAccountType);
            driverWeb.waitElementClickable(caixaAccountType, 1);

            String accountId = bankAccount.get(1).substring(0, 2);

            //A API retorna vários tipos de conta CAIXA diferentes, só 4 delas são aceitas no Ecomm
            while (!accountId.matches("(001|006|013|023)")) {
                bankAccount = getBankAccount("3");
                accountId = bankAccount.get(1).substring(0, 3);
            }

            bankAccount.set(1, bankAccount.get(1).substring(3));
            caixaAccountTypeSelect.selectByValue(accountId);
        }

        driverWeb.waitElementClickable(agencia, 1);
        driverWeb.sendKeys(agencia, bankAccount.get(0)); //.findElement(By.xpath("following-sibling::label"))
        assertEquals("Campo agência preenchido", bankAccount.get(0), agencia.getAttribute("value"));

        driverWeb.sendKeys(conta, bankAccount.get(1));
        assertEquals("Campo conta preenchido", bankAccount.get(1), conta.getAttribute("value"));
    }

    public void selecionarDataVencimento(String data) {
        //TODO
    }

    public void aceitarTermos(boolean isDebitPaymentFlow) {
        WebElement termos = driverWeb.findElement(isComboFlow ? "chk-termos" : isDebitPaymentFlow ? "chk-termos-clarodebitpayment" : "chk-termos-claroticketpayment", "id");
        assertFalse(termos.isSelected());

        driverWeb.javaScriptClick(termos);
        assertTrue(termos.isSelected());
    }

    public void clicarContinuar() {
        driverWeb.javaScriptClick("btn-continuar", "id");
    }

    public void clickOkEntendi() {
        driverWeb.javaScriptClick("btn-multa-entendi", "id");
    }

    public void clickNaoConcordo() {
        driverWeb.javaScriptClick(naoConcordo);
    }

    public void validarPaginaMulta() {
        driverWeb.waitPageLoad("claro/pt/checkout/multi/payment-method/add", 10);

        concordo = driverWeb.findElement("btn-multa-concordo", "id");
        naoConcordo = driverWeb.findElement("btn-multa-nao-concordo", "id");

        assertTrue(driverWeb.findElement("txt-mensagem-multa", "id").isDisplayed());

        assertTrue(concordo.isDisplayed());
        assertTrue(naoConcordo.isDisplayed());
    }

    public void clicarConcordo() {
        driverWeb.javaScriptClick(concordo);
    }
}