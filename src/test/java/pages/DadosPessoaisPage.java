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
    private String xpathTipoDeFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? "(//*[@for='shippingTypeOption1'])[2]" : "(//*[@for='shippingTypeOption1'])[1]";
    private String xpathValorDoFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? "(//*[@for='shippingTypeOption1'])[2]" : "(//*[@class='shipping-value'])[1]";
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
            driver.sendKeys(xpathTxtNome, "xpath", nomeCompleto);
        }
    }

    public void preencherDataNascimento(String dataNascimento) {
        if (driver.isDisplayed(xpathTxtDtNascimento, "xpath")) {
            driver.sendKeys(xpathTxtDtNascimento, "xpath", dataNascimento);
        }
    }

    public void preencherNomeDaMae(String nomeDaMae) {
        if (driver.isDisplayed(xpathTxtNomeMae, "xpath")) {
            driver.sendKeys(xpathTxtNomeMae, "xpath", nomeDaMae);
        }
    }

    public void validarMensagemBloqueiocep(String mensagem) {
        driver.waitElement(xpathMsgErroCEP, "id");
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroCEP, "xpath"));
    }

    public void camposDadosPessoais(String nomeCompleto, String dataNascimento, String nomeDaMae) {
        driver.waitElement(idFormDadosPessoais, "id");
        driver.sendKeys(xpathTxtNome, "xpath", nomeCompleto);
        driver.sendKeysCampoMascara(dataNascimento, xpathTxtDtNascimento, "xpath");
        driver.sendKeys(xpathTxtNomeMae, "xpath", nomeDaMae);
        nomeCliente = nomeCompleto;
    }

    public void camposEndereco(String cep, String numero, String complemento) {
        cepCliente = cep;
        numeroEndCliente = numero;
        complementoCliente = complemento;
        driver.waitElementToBeClickable(idTxtCep, "id", 10);
        driver.sendKeysCampoMascara(cep, idTxtCep, "id");
        driver.sendTab(2, "");
        driver.waitElementToBeClickable(xpathTxtNumero, "xpath", 15);
        driver.sendKeys(xpathTxtNumero, "xpath", numero);
        driver.waitElementToBeClickable(xpathTxtComplemento, "xpath", 10);
        driver.sendKeys(xpathTxtComplemento, "xpath", complemento);

//        tipoDeFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? driver.getText(xpathValorDoFreteCarrinho, "xpath").substring(0, 16) : driver.getText(xpathTipoDeFreteCarrinho, "xpath");;
//        valorDoFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? driver.getText(xpathValorDoFreteCarrinho, "xpath").substring(32, 38) : driver.getText(xpathValorDoFreteCarrinho, "xpath");;

        if (Hooks.tagScenarios.contains("@entregaExpressa")) {
            tipoDeFreteCarrinho = driver.getText(xpathTipoDeFreteCarrinho, "xpath").substring(0, 16);
//            valorDoFreteCarrinho = driver.getText(xpathValorDoFreteCarrinho, "xpath").substring(32, 38);
        } else if (Hooks.tagScenarios.contains("@entregaConvencional")) {
            tipoDeFreteCarrinho = driver.getText(xpathTipoDeFreteCarrinho, "xpath");
//            valorDoFreteCarrinho = driver.getText(xpathValorDoFreteCarrinho, "xpath");
        }

        enderecoCliente = driver.getValue(xpathEnderecoCliente, "xpath");
        bairroCliente = driver.getValue(xpathBairroCliente, "xpath");
        ufCliente = driver.getValue(xpathUfCliente, "xpath");
        cidadeCliente = driver.getValue(xpathCidadeCliente, "xpath");
    }
}