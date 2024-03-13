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
    private String FormDadosPessoais = "addressForm.personalInformation";
    private String TxtNome = "txt-nome-completo";
    private String TxtDtNascimento = "txt-nascimento";
    private String TxtNomeMae = "txt-nome-mae";
    private String SubmitContinuarDados = "//input[@data-automation='continuar']";

    // Dados Endereco
    private String TxtCep = "txt-cep-endereco-entrega";
    private String TxtNumero = "txt-numero-endereco-entrega";
    private String TxtComplemento = "txt-complemento-endereco-entrega";

    //refactor
    private String TipoDeFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? "rdn-entrega-expressa" : "rdn-convencional";
    private String ValorDoFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? "rdn-entrega-expressa" : "rdn-convencional";

    public static String BtnContinuar = "btn-continuar";
    public static String BtnContinuarPagamento = "//button[@title='Continuar']";

    // Variavel utilizada para validacao de cenario @bloqueioCEPdiferente
    private String MsgErroCEP = "//*[@id='postcode_deliveryAddress-error']";

    // Variaveis criadas para utilizacao de validacao na tela de parabens
    public static String nomeCliente;
    public static String cepCliente;
    public static String numeroEndCliente;
    public static String complementoCliente;
    public static String EnderecoCliente = "txt-endereco-endereco-entrega";
    public static String BairroCliente = "txt-bairro-endereco-entrega";
    public static String UfCliente = "txt-estado-endereco-entrega";
    public static String CidadeCliente = "txt-cidade-endereco-entrega";
    ;
    public static String enderecoCliente;
    public static String bairroCliente;
    public static String ufCliente;
    public static String cidadeCliente;
    public static String tipoDeFreteCarrinho;
    public static String valorDoFreteCarrinho;

    public void preencherNomeCompleto(String nomeCompleto) throws InterruptedException {
        if (driver.isDisplayed(TxtNome, "id")) {
            driver.actionSendKey(TxtNome, "id", nomeCompleto);
        }
    }

    public void preencherDataNascimento(String dataNascimento) throws InterruptedException {
        if (driver.isDisplayed(TxtDtNascimento, "id")) {
            driver.actionSendKey(TxtDtNascimento, "id", dataNascimento);
        }
    }

    public void preencherNomeDaMae(String nomeDaMae) throws InterruptedException {
        if (driver.isDisplayed(TxtNomeMae, "id")) {
            driver.actionSendKey(TxtNomeMae, "id", nomeDaMae);
        }
    }

    public void validarMensagemBloqueiocep(String mensagem) {
        driver.waitElement(MsgErroCEP, "xpath");
        Assert.assertEquals(mensagem, driver.getText(MsgErroCEP, "id"));
    }

    public void camposDadosPessoais(String nomeCompleto, String dataNascimento, String nomeDaMae) {
        driver.waitElement(FormDadosPessoais, "id");
        driver.sendKeys(nomeCompleto, TxtNome, "id");
        driver.sendKeysCampoMascara(dataNascimento, TxtDtNascimento, "id");
        driver.sendKeys(nomeDaMae, TxtNomeMae, "id");
        nomeCliente = nomeCompleto;
    }

    public void camposEndereco(String cep, String numero, String complemento) {
        cepCliente = cep;
        numeroEndCliente = numero;
        complementoCliente = complemento;
        driver.waitElementToBeClickable(TxtCep, "id", 10);
        driver.sendKeysCampoMascara(cep, TxtCep, "id");
        driver.sendTab(2, "");
        driver.waitElementToBeClickable(TxtNumero, "id", 15);
        driver.sendKeys(numero, TxtNumero, "id");
        driver.waitElementToBeClickable(TxtComplemento, "id", 10);
        driver.sendKeys(complemento, TxtComplemento, "id");

//        tipoDeFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? driver.getText(xpathValorDoFreteCarrinho, "xpath").substring(0, 16) : driver.getText(xpathTipoDeFreteCarrinho, "xpath");;
//        valorDoFreteCarrinho = (Hooks.tagScenarios.contains("@entregaExpressa")) ? driver.getText(xpathValorDoFreteCarrinho, "xpath").substring(32, 38) : driver.getText(xpathValorDoFreteCarrinho, "xpath");;

        if (Hooks.tagScenarios.contains("@entregaExpressa")) {
            tipoDeFreteCarrinho = driver.getText(TipoDeFreteCarrinho, "id").substring(0, 16);
        } else if (Hooks.tagScenarios.contains("@entregaConvencional")) {
            tipoDeFreteCarrinho = driver.getText(TipoDeFreteCarrinho, "id");
        }

        enderecoCliente = driver.getValue(EnderecoCliente, "id");
        bairroCliente = driver.getValue(BairroCliente, "id");
        ufCliente = driver.getValue(UfCliente, "id");
        cidadeCliente = driver.getValue(CidadeCliente, "id");
    }
}