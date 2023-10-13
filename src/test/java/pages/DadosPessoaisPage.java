package pages;

import org.junit.Assert;
import support.DriverQA;
import support.Hooks;

public class DadosPessoaisPage {
    private DriverQA driver;

    public DadosPessoaisPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    // Dados Pessoais
    private String idFormDadosPessoais = "addressForm.personalInformation";
    private String xpathTxtNome = "//input[@data-automation='nome-completo']";
    private String xpathTxtDtNascimento = "//input[@data-automation='nascimento']";
    private String xpathTxtNomeMae = "//input[@data-automation='nome-completo-mae']";
    private String xpathSubmitContinuarDados = "//input[@data-automation='continuar']";

    // Dados Endereco
    private String idTxtCep = "postcode_deliveryAddress";
    private String xpathTxtNumero = "//input[@data-automation='numero']";
    private String xpathTxtComplemento = "//input[@data-automation='complemento']";

    // colocar if ternario tipo de frete
    private String xpathTipoDeFreteConvencionalCarrinho = "(//*[@for='shippingTypeOption1'])[1]";
    private String xpathTipoDeFreteExpressaCarrinho = "(//*[@for='shippingTypeOption1'])[2]";
    private String xpathValorDoFreteConvencionalCarrinho = "(//*[@class='shipping-value'])[1]";
    public static String xpathBtnContinuar = "//button[@data-automation='continuar']";
    public static String xpathBtnContinuarPagamento = "//button[@title='Continuar']";

    // Variavel utilizada para validacao de cenario @bloqueioCEPdiferente
    private String xpathMsgErroCEP = "//*[@id='postcode_deliveryAddress-error']";

    // Variaveis criadas para utilizacao de validacao na tela de parabens
    public static String nomeCliente;
    public static String cepCliente;
    public static String numeroEndCliente;
    public static String complementoCliente;
    public static String xpathEnderecoCliente = "(//*[@name='deliveryAddress.streetName'])[2]";
    public static String xpathBairroCliente = "(//*[@name='deliveryAddress.neighbourhood'])[2]";
    public static String xpathUfCliente = "(//*[@name='deliveryAddress.stateCode'])[2]";
    public static String xpathCidadeCliente = "(//*[@name='deliveryAddress.townCity'])[2]";
    ;
    public static String enderecoCliente;
    public static String bairroCliente;
    public static String ufCliente;
    public static String cidadeCliente;
    public static String tipoDeFreteCarrinho;
    public static String valorDoFreteCarrinho;

    public void preencherNomeCompleto(String nomeCompleto) {
        if (driver.isDisplayed(xpathTxtNome, "xpath")) {
            driver.actionSendKey(nomeCompleto, xpathTxtNome, "xpath");
        }
    }

    public void preencherDataNascimento(String dataNascimento) {
        if (driver.isDisplayed(xpathTxtDtNascimento, "xpath")) {
            driver.actionSendKey(dataNascimento, xpathTxtDtNascimento, "xpath");
        }
    }

    public void preencherNomeDaMae(String nomeDaMae) {
        if (driver.isDisplayed(xpathTxtNomeMae, "xpath")) {
            driver.actionSendKey(nomeDaMae, xpathTxtNomeMae, "xpath");
        }
    }

    public void validarMensagemBloqueiocep(String mensagem) {
        driver.waitElementXP(xpathMsgErroCEP);
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroCEP, "xpath"));
    }

    public void camposDadosPessoais(String nomeCompleto, String dataNascimento, String nomeDaMae) {
        driver.waitElementAll(idFormDadosPessoais, "id");
        driver.sendKeys(nomeCompleto, xpathTxtNome, "xpath");
        driver.sendKeysCampoMascara(dataNascimento, xpathTxtDtNascimento, "xpath");
        driver.sendKeys(nomeDaMae, xpathTxtNomeMae, "xpath");
        nomeCliente = nomeCompleto;
    }

    public void camposEndereco(String cep, String numero, String complemento) {
        cepCliente = cep;
        numeroEndCliente = numero;
        complementoCliente = complemento;
        driver.waitElementToBeClickableAll(idTxtCep, 10, "id");
        driver.sendKeysCampoMascara(cep, idTxtCep, "id");
        driver.sendTab(2, "");
        driver.waitElementToBeClickableAll(xpathTxtNumero, 15, "xpath");
        driver.sendKeys(numero, xpathTxtNumero, "xpath");
        driver.waitElementToBeClickableAll(xpathTxtComplemento, 10, "xpath");
        driver.sendKeys(complemento, xpathTxtComplemento, "xpath");
        if (Hooks.tagScenarios.contains("@entregaExpressa")) {
            tipoDeFreteCarrinho = driver.getText(xpathTipoDeFreteExpressaCarrinho, "xpath").substring(0, 16);
            valorDoFreteCarrinho = driver.getText(xpathTipoDeFreteExpressaCarrinho, "xpath").substring(32, 38);
        } else {
            tipoDeFreteCarrinho = driver.getText(xpathTipoDeFreteConvencionalCarrinho, "xpath");
            valorDoFreteCarrinho = driver.getText(xpathValorDoFreteConvencionalCarrinho, "xpath");
        }
        enderecoCliente = driver.getValue(xpathEnderecoCliente, "xpath");
        bairroCliente = driver.getValue(xpathBairroCliente, "xpath");
        ufCliente = driver.getValue(xpathUfCliente, "xpath");
        cidadeCliente = driver.getValue(xpathCidadeCliente, "xpath");
    }
}
