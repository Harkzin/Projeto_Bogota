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

    public void marcarCheckboxTermo() {
        driver.JavaScriptClick(ChkTermosDeAdesao, "id");
    }

    public void clicarFormaDePagamento() {
        driver.waitElementXP(ValorCarrinho);

        // Salva o Valor do carrinho atual (Debito)
        String valorDebito = driver.getText(ValorCarrinho, "xpath");
        List<String> elementosFatura = new ArrayList<>();
        elementosFatura.addAll(Arrays.asList(Banco, Agencia, Conta));
        for (String str : elementosFatura) {
            // Valida que banco, agencia, conta estao visiveis na tela para debito
            Assert.assertTrue(driver.isDisplayed(str, "xpath"));
        }

        // Valida que o botao de banco esta habilitado e a agencia e conta esta desabilitado para debito
        Assert.assertTrue(driver.isEnabled(Banco, "id"));
        Assert.assertFalse(driver.isEnabled(Agencia, "id")
                && driver.isEnabled(Conta, "id"));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para debito
        driver.validaFaturas(ButtonFaturaWhatsDebito, ButtonFaturaEmailDebito, ButtonFaturaCorreiosDebito, TipoFatura, 1, 3);

        // Valida data de vencimento Debito
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            dataVencimentoFatura = driver.validaDataDeVencimento(DataDeVencimentoDebito);
        }

        // Muda para boleto para posteriormente validar diferenca no valor de debito > boleto
        driver.actionClick(AbaBoleto, "id");

        // Metodo que valida que há alteracao no valor quando Debito automatico > Boleto
        // Regra de negócio: Plano controle R$5 de diferença e pós R$10
        driver.waitAttValorBoleto(ValorCarrinho, valorDebito);

        // Refactor
        // Valida que o plano no resumo da compra foi alterado para boleto
        //Assert.assertTrue(("Boleto".equals(driver.getText(CarrinhoPage.MetodoPagamentoResumo2, "xpath"))));

        // Valida que wpp esta selecionado e se o email e correios não, valida que os 3 tipos de fatura estao visiveis e sao interagiveis para boleto
        driver.validaFaturas(ButtonFaturaWhatsBoleto, ButtonFaturaEmailBoleto, ButtonFaturaCorreiosBoleto, TipoFatura, 4, 6);

        // Valida data de vencimento boleto
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            driver.validaDataDeVencimento(DataDeVencimentoBoleto);
        }

        if (Hooks.tagScenarios.contains("@debitoAutomatico")) {
            driver.click(AbaDebitoAutomatico, "id");
            driver.waitElementToBeClickableAll(Banco, 10, "id");
            driver.sendKeys("237 - BRADESCO", Banco, "id");
            driver.waitMilliSeconds(100);
            driver.sendKeyBoard(Keys.ENTER);
            driver.waitMilliSeconds(100);
            driver.sendKeys("6620", Agencia, "id");
            driver.sendKeys("11868576", Conta, "id");
            driver.waitAttValorDebito(ValorCarrinho, valorDebito);
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
