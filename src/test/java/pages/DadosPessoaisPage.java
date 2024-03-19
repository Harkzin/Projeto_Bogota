package pages;

import org.junit.Assert;

import org.openqa.selenium.WebElement;
import support.DriverQA;
import support.Hooks;

public class DadosPessoaisPage {
    private final DriverQA driverQA;

    public DadosPessoaisPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }
    private final String numero = "txt-numero-endereco-entrega";
    private final String complemento = "txt-complemento-endereco-entrega";

    // Variaveis criadas para utilizacao de validacao na tela de parabens
    public static String nomeCliente;
    public static String cepCliente;
    public static String numeroEndCliente;
    public static String complementoCliente;
    public static String enderecoCliente;
    public static String bairroCliente;
    public static String ufCliente;
    public static String cidadeCliente;

    public void validarPaginaDadosPessoais() {
        driverQA.waitPageLoad("/checkout/multi/delivery-address/addClaroAddress", 10);
    }

    public void inserirNome(String nomeCompleto) {
        driverQA.actionSendKeys("txt-nome-completo", "id", nomeCompleto);
    }

    public void inserirDataNascimento(String dataNasc) {
        driverQA.actionSendKeys("txt-nascimento", "id", dataNasc);
    }

    public void inserirNomeMae(String nomeMae) {
        driverQA.actionSendKeys("txt-nome-mae", "id", nomeMae);
    }

    public void inserirCep(String cep) {
        driverQA.actionSendKeys("txt-cep-endereco-entrega", "id", cep);

        WebElement enderecoElement = driverQA.findElement("txt-endereco-endereco-entrega", "id");
        driverQA.waitElementVisibility(enderecoElement, 10);
        Assert.assertNotEquals(enderecoElement.getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-bairro-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-estado-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-cidade-endereco-entrega", "id").getAttribute("value"), "");

        cepCliente = cep; //Refactor
    }

    public void inserirDadosEndereco(String numero, String complemento) {
        driverQA.actionSendKeys(this.numero, "id", numero);
        driverQA.actionSendKeys(this.complemento, "id", complemento);
    }

    public void validarMensagemBloqueiocep(String mensagem) {
        String msgErroCEP = "//*[@id='postcode_deliveryAddress-error']";
        //driverQA.waitElementVisibility(msgErroCEP, "xpath");
        Assert.assertEquals(mensagem, driverQA.getText(msgErroCEP, "id"));
    }

    public void clicarContinuar() {
        driverQA.JavaScriptClick("btn-continuar", "id");
    }
}