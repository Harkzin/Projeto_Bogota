package pages;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import support.DriverQA;
import support.Hooks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void marcarCheckboxTermo() {
        driver.JavaScriptClick(xpathChkTermosDeAdesao, "xpath");
    }

    public void clicarFormaDePagamento() {
        driver.waitElementXP(xpathValorCarrinho);

        // Salva o Valor do carrinho atual (Debito)
        String valorDebito = driver.getText(xpathValorCarrinho, "xpath");
        List<String> elementosFatura = new ArrayList<>();
        elementosFatura.addAll(Arrays.asList(xpathBanco, xpathAgencia, xpathConta));
        for (String str : elementosFatura) {
            // Valida que banco, agencia, conta estao visiveis na tela para debito
            Assert.assertTrue(driver.isDisplayed(str, "xpath"));
        }

        // Valida que o botao de banco esta habilitado e a agencia e conta esta desabilitado para debito
        Assert.assertTrue(driver.isEnabled(xpathBanco, "xpath"));
        Assert.assertFalse(driver.isEnabled(xpathAgencia, "xpath")
                && driver.isEnabled(xpathConta, "xpath"));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para debito
        driver.validaFaturas(idButtonFaturaWhatsDebito, idButtonFaturaEmailDebito, idButtonFaturaCorreiosDebito, xpathTipoFatura, 1, 3);

        // Valida data de vencimento Debito
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            dataVencimentoFatura = driver.validaDataDeVencimento(idDataDeVencimentoDebito);
        }

        // Muda para boleto para posteriormente validar diferenca no valor de debito > boleto
        driver.actionClick(xpathAbaBoleto, "xpath");

        // Metodo que valida que há alteracao no valor quando Debito automatico > Boleto
        // Regra de negócio: Plano controle R$5 de diferença e pós R$10
        driver.waitAttValorBoleto(xpathValorCarrinho, valorDebito);

        // Valida que o plano no resumo da compra foi alterado para boleto
        Assert.assertTrue(("Boleto".equals(driver.getText(CarrinhoPage.xpathMetodoPagamentoResumo2, "xpath"))));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para boleto
        driver.validaFaturas(idButtonFaturaWhatsBoleto, idButtonFaturaEmailBoleto, idButtonFaturaCorreiosBoleto, xpathTipoFatura, 4, 6);

        // Valida data de vencimento boleto
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            driver.validaDataDeVencimento(idDataDeVencimentoBoleto);
        }

        if (Hooks.tagScenarios.contains("@debitoAutomatico")) {
            driver.click(xpathAbaDebitoAutomatico, "xpath");
            driver.waitElementToBeClickableAll(xpathBanco, 10, "xpath");
            driver.sendKeys("237 - BRADESCO", xpathBanco, "xpath");
            driver.waitMilliSeconds(100);
            driver.sendKeyBoard(Keys.ENTER);
            driver.waitMilliSeconds(100);
            driver.sendKeys("6620", xpathAgencia, "xpath");
            driver.sendKeys("11868576", xpathConta, "xpath");
            driver.waitAttValorDebito(xpathValorCarrinho, valorDebito);
        }
        valorPedidoCarrinho = driver.getText(xpathValorCarrinho, "xpath");
        formaPagamentoPedidoCarrinho = Hooks.tagScenarios.contains("@debitoAutomatico") ? driver.getText(CarrinhoPage.xpathMetodoPagamentoResumo, "xpath").split(" ")[0] : driver.getText(CarrinhoPage.xpathMetodoPagamentoResumo, "xpath");
    }

    public void selecionarDataVencimento(String data) {
        driver.JavaScriptClick("//label[@data-automation='vencimento-" + data + "']", "xpath");
    }

    public void selecionarTipoFatura(String fatura) {
        driver.JavaScriptClick("div[class$=active] .tipoFatura label[for^='" + fatura + "']", "css");
    }
}
