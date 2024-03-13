package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.DriverQA;
import support.Hooks;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.Duration.ofSeconds;

public class CustomizarFaturaPage {
    private DriverQA driver;

    public CustomizarFaturaPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    // Dados Pagamentos
    private String xpathAbaBoleto = "//*[@id='boleto']";
    private String xpathAbaDebitoAutomatico = "//li[@data-automation='aba-debito-automatico']";
    private String xpathBanco = "//*[@id='bank']";
    private String xpathAgencia = "//*[@id='agency']";
    private String xpathConta = "//*[@id='account']";
    private String xpathTipoFatura = "(//*[@class='mdn-Radio tipoFatura'])";

    private String idButtonFaturaWhatsDebito = "WhatsApp11";
    private String idButtonFaturaEmailDebito = "E-mail22";
    private String idButtonFaturaCorreiosDebito = "Correios33";
    private String idButtonFaturaWhatsBoleto = "WhatsApp4";
    private String idButtonFaturaEmailBoleto = "E-mail5";
    private String idButtonFaturaCorreiosBoleto = "Correios6";
    private String idDataDeVencimentoDebito = "expireDateClaroDebitPayment_";
    private String idDataDeVencimentoBoleto = "expireDateClaroTicketPayment_";
    private String xpathChkTermosDeAdesao = (Hooks.tagScenarios.contains("@boleto")) ? "(//*[@class='mdn-Checkbox-label'])[2]" : "(//*[@class='mdn-Checkbox-label'])[1]";
    public static String xpathValorCarrinho = "(//*[@class='js-entry-price-plan js-revenue'])[2]";

    // Multa
    public static String xpathNaoConcordo = "//*[@data-multa-action='goStep2']";
    public static String xpathClicarOKEntendi = "//*[@data-multa-action='backHome']";

    // Variavel para validacao no pedido
    public static String valorPedidoCarrinho;
    public static String formaPagamentoPedidoCarrinho;
    public static String dataVencimentoFatura;

    public void waitAttValorBoleto(String parId, String valorDebito) {
        WebDriverWait wait = new WebDriverWait(driver.getDriver(), ofSeconds(10));
        float numeroFloat = (Hooks.tagScenarios.contains("@controle")) ? Float.parseFloat(valorDebito.replace(',', '.')) + 5.0f : Float.parseFloat(valorDebito.replace(',', '.')) + 10.0f;
        DecimalFormat df = new DecimalFormat("#.##");
        String numeroFormatado = df.format(numeroFloat);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(parId), numeroFormatado.replace('.', ',')));
    }

    public void waitAttValorDebito(String parId, String valorDebito) {
        WebDriverWait wait = new WebDriverWait(driver.getDriver(), ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(parId), valorDebito));
    }

    public String validaDataDeVencimento(String dtVencimento) {
        int dataSelecionada = 0;
        int dataNaoSelecionada = 0;
        String txtDtVencimento = "";
        driver.waitElementToBeClickable(dtVencimento + 1, "id", 10);
        for (int i = 1; i <= 6; i++) {
            // Valida que os botoes da data de vencimento estao visiveis e interagiveis
            Assert.assertTrue(driver.isEnabledDisplayed(dtVencimento + i, "id"));
            if (driver.isSelected(dtVencimento + i, "id")) {
                dataSelecionada++;
                txtDtVencimento = driver.getText("//*[@for='" + dtVencimento + i + "']", "xpath");
            } else {
                dataNaoSelecionada++;
            }
        }

        // Valida que ha 1 data selecionada e as outras 5 nao estao selecionadas
        Assert.assertEquals(dataSelecionada, 1);
        Assert.assertEquals(dataNaoSelecionada, 5);
        return txtDtVencimento;
    }

    public void validaFaturas(String whats, String email, String correios, String tipoFatura, int numInicial,
                              int numFinal) {
        for (int i = numInicial; i <= numFinal; i++) {
            // Valida que os botoes de fatura wpp, email e correios estao visiveis e interagiveis
            Assert.assertTrue(driver.isEnabledDisplayed(tipoFatura + "[" + i + "]", "xpath"));
        }
        // Valida que o wpp esta selecionado e que o email e correio nao estao
        Assert.assertTrue(driver.isSelected(whats, "id"));
        Assert.assertFalse(driver.isSelected(email, "id")
                && driver.isSelected(correios, "id"));
    }

    public void marcarCheckboxTermo() {
        driver.JavaScriptClick(xpathChkTermosDeAdesao, "xpath");
    }

    public void clicarFormaDePagamento() {
        driver.waitElement(xpathValorCarrinho, "id");

        // Salva o Valor do carrinho atual (Debito)
        String valorDebito = driver.getText(xpathValorCarrinho, "xpath");
        List<String> elementosFatura = new ArrayList<>(Arrays.asList(xpathBanco, xpathAgencia, xpathConta));
        for (String str : elementosFatura) {
            // Valida que banco, agencia, conta estao visiveis na tela para debito
            Assert.assertTrue(driver.isDisplayed(str, "xpath"));
        }

        // Valida que o botao de banco esta habilitado e a agencia e conta esta desabilitado para debito
        Assert.assertTrue(driver.isEnabled(xpathBanco, "xpath"));
        Assert.assertFalse(driver.isEnabled(xpathAgencia, "xpath")
                && driver.isEnabled(xpathConta, "xpath"));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para debito
        validaFaturas(idButtonFaturaWhatsDebito, idButtonFaturaEmailDebito, idButtonFaturaCorreiosDebito, xpathTipoFatura, 1, 3);

        // Valida data de vencimento Debito
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            dataVencimentoFatura = validaDataDeVencimento(idDataDeVencimentoDebito);
        }

        // Muda para boleto para posteriormente validar diferenca no valor de debito > boleto
        driver.actionClick(xpathAbaBoleto, "xpath");

        // Metodo que valida que há alteracao no valor quando Debito automatico > Boleto
        // Regra de negócio: Plano controle R$5 de diferença e pós R$10
        waitAttValorBoleto(xpathValorCarrinho, valorDebito);

        // Refactor
        // Valida que o plano no resumo da compra foi alterado para boleto
        //Assert.assertTrue(("Boleto".equals(driver.getText(CarrinhoPage.MetodoPagamentoResumo2, "xpath"))));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para boleto
        validaFaturas(idButtonFaturaWhatsBoleto, idButtonFaturaEmailBoleto, idButtonFaturaCorreiosBoleto, xpathTipoFatura, 4, 6);

        // Valida data de vencimento boleto
        /*if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            driver.validaDataDeVencimento(idDataDeVencimentoBoleto);
        }*/ //Refactor

        if (Hooks.tagScenarios.contains("@debitoAutomatico")) {
            driver.click(xpathAbaDebitoAutomatico, "xpath");
            driver.waitElementToBeClickable(xpathBanco, "xpath", 10);
            driver.sendKeys(xpathBanco, "xpath", "237 - BRADESCO");
            driver.waitMilliSeconds(100);
            driver.sendKeyBoard(Keys.ENTER);
            driver.waitMilliSeconds(100);
            driver.sendKeys(xpathAgencia, "xpath", "6620");
            driver.sendKeys(xpathConta, "xpath", "11868576");
            waitAttValorDebito(xpathValorCarrinho, valorDebito);
        }
        valorPedidoCarrinho = driver.getText(xpathValorCarrinho, "xpath");
        //Refactor
        //formaPagamentoPedidoCarrinho = Hooks.tagScenarios.contains("@debitoAutomatico") ? driver.getText(CarrinhoPage.MetodoPagamentoResumo, "xpath").split(" ")[0] : driver.getText(CarrinhoPage.MetodoPagamentoResumo, "xpath");
    }

    public void selecionarDataVencimento(String data) {
        driver.JavaScriptClick("//label[@data-automation='vencimento-" + data + "']", "xpath");
    }

    public void selecionarTipoFatura(String fatura) {
        driver.JavaScriptClick("div[class$=active] .tipoFatura label[for^='" + fatura + "']", "css");
    }
}