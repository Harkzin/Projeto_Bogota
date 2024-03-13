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
    private String AbaBoleto = "btn-aba-boleto";
    private String AbaDebitoAutomatico = "btn-aba-debito-automatico";
    private String Banco = "slc-banco";
    private String Agencia = "slc-agencia";
    private String Conta = "slc-conta";
    private String TipoFatura = "(//*[@class='mdn-Radio tipoFatura'])";

    private String ButtonFaturaWhatsDebito = "WhatsApp11";
    private String ButtonFaturaEmailDebito = "E-mail22";
    private String ButtonFaturaCorreiosDebito = "Correios33";
    private String ButtonFaturaWhatsBoleto = "WhatsApp4";
    private String ButtonFaturaEmailBoleto = "E-mail5";
    private String ButtonFaturaCorreiosBoleto = "Correios6";
    private String DataDeVencimentoDebito = "expireDateClaroDebitPayment_";
    private String DataDeVencimentoBoleto = "expireDateClaroTicketPayment_";
    private String ChkTermosDeAdesao = "chk-termos";
    public static String ValorCarrinho = "(//*[@class='js-entry-price-plan js-revenue'])[2]";

    // Multa
    public static String NaoConcordo = "//*[@data-multa-action='goStep2']";
    public static String ClicarOKEntendi = "//*[@data-multa-action='backHome']";

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
        driver.JavaScriptClick(ChkTermosDeAdesao, "id");
    }

    public void clicarFormaDePagamento() {
        driver.waitElement(ValorCarrinho, "xpath");

        // Salva o Valor do carrinho atual (Debito)
        String valorDebito = driver.getText(ValorCarrinho, "xpath");
        List<String> elementosFatura = new ArrayList<>(Arrays.asList(Banco, Agencia, Conta));
        //elementosFatura.addAll(Arrays.asList(Banco, Agencia, Conta));

        for (String str : elementosFatura) {
            // Valida que banco, agencia, conta estao visiveis na tela para debito
            Assert.assertTrue(driver.isDisplayed(str, "xpath"));
        }

        // Valida que o botao de banco esta habilitado e a agencia e conta esta desabilitado para debito
        Assert.assertTrue(driver.isEnabled(Banco, "id"));
        Assert.assertFalse(driver.isEnabled(Agencia, "id")
                && driver.isEnabled(Conta, "id"));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para debito
        validaFaturas(ButtonFaturaWhatsDebito, ButtonFaturaEmailDebito, ButtonFaturaCorreiosDebito, TipoFatura, 1, 3);

        // Valida data de vencimento Debito
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            dataVencimentoFatura = validaDataDeVencimento(DataDeVencimentoDebito);
        }

        // Muda para boleto para posteriormente validar diferenca no valor de debito > boleto
        driver.actionClick(AbaBoleto, "id");

        // Metodo que valida que há alteracao no valor quando Debito automatico > Boleto
        // Regra de negócio: Plano controle R$5 de diferença e pós R$10
        waitAttValorBoleto(ValorCarrinho, valorDebito);

        // Refactor
        // Valida que o plano no resumo da compra foi alterado para boleto
        //Assert.assertTrue(("Boleto".equals(driver.getText(CarrinhoPage.MetodoPagamentoResumo2, "xpath"))));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para boleto
        validaFaturas(ButtonFaturaWhatsBoleto, ButtonFaturaEmailBoleto, ButtonFaturaCorreiosBoleto, TipoFatura, 4, 6);

        // Valida data de vencimento boleto
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            validaDataDeVencimento(DataDeVencimentoBoleto);
        }

        if (Hooks.tagScenarios.contains("@debitoAutomatico")) {
            driver.click(AbaDebitoAutomatico, "id");
            driver.waitElementToBeClickable(Banco, "id", 10);
            driver.sendKeys("237 - BRADESCO", Banco, "id");
            driver.waitMilliSeconds(100);
            driver.sendKeyBoard(Keys.ENTER);
            driver.waitMilliSeconds(100);
            driver.sendKeys("6620", Agencia, "id");
            driver.sendKeys("11868576", Conta, "id");
            waitAttValorDebito(ValorCarrinho, valorDebito);
        }
        valorPedidoCarrinho = driver.getText(ValorCarrinho, "xpath");
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