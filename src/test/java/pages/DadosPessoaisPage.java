package pages;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import support.DriverQA;

public class DadosPessoaisPage {
    private final DriverQA driverQA;

    public DadosPessoaisPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    public enum CEP {
        CONVECIONAL("01001001"),
        EXPRESSA_AQUISICAO("01001000"),
        EXPRESSA_PORTABILIDADE("01001010");

        private final String cep;

        private CEP(final String cep) {
            this.cep = cep;
        }

        public String getCEP() {
            return cep;
        }
    }

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

    public void inserirCep(String tipoEntrega, boolean fluxoPre) {
        final String cep;
        final boolean expressa;

        if (tipoEntrega.equals("convencional")) {
            cep = "01001001";
            expressa = false;
        } else { //expressa
            cep = ((JavascriptExecutor) driverQA.getDriver()).executeScript("return ACC.processType").toString().equals("ACQUISITION") ? "01001000" : "01001010"; //expressa aquisição ou expressa port
            expressa = true;
        }

        driverQA.actionSendKeys("txt-cep-endereco-entrega", "id", cep);

        WebElement enderecoElement = driverQA.findElement("txt-endereco-endereco-entrega", "id");
        driverQA.waitElementVisibility(enderecoElement, 10);
        Assert.assertNotEquals(enderecoElement.getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-bairro-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-estado-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-cidade-endereco-entrega", "id").getAttribute("value"), "");

        if (!fluxoPre) {
            WebElement enderecoCobrancaElement = driverQA.findElement("endereco-cobranca_checkbox", "id");
            WebElement enderecoCobrancaParentElement = enderecoCobrancaElement.findElement(By.xpath("../../.."));  //div pai do pai do pai do checkbox

            if (!expressa) {
                Assert.assertTrue(driverQA.findElement("rdn-chipTypeCommom", "id").isSelected());
                Assert.assertTrue(driverQA.findElement("rdn-convencional", "id").isSelected());
                Assert.assertFalse(enderecoCobrancaParentElement.isDisplayed());
            }
            Assert.assertTrue(driverQA.findElement("rdn-chipTypeCommomExpress", "id").isSelected());
            Assert.assertTrue(driverQA.findElement("rdn-entrega-expressa", "id").isSelected());

            Assert.assertTrue(enderecoCobrancaParentElement.isDisplayed());
            Assert.assertTrue(enderecoCobrancaElement.isSelected());
        }
    }

    public void inserirDadosEndereco(String numero, String complemento) {
        WebElement numeroElement = driverQA.findElement("txt-numero-endereco-entrega", "id");
        WebElement complementoElement = driverQA.findElement("txt-complemento-endereco-entrega", "id");

        Assert.assertEquals(numeroElement.getAttribute("value"), "");
        Assert.assertEquals(complementoElement.getAttribute("value"), "");

        driverQA.actionSendKeys(numeroElement, numero);
        driverQA.actionSendKeys(complementoElement, complemento);
    }

    public void validarMensagemBloqueioCep(String mensagem) {
        String msgErroCEP = "//*[@id='postcode_deliveryAddress-error']";

        Assert.assertEquals(mensagem, driverQA.findElement(msgErroCEP, "id").getText());
    }

    public void clicarContinuar() {
        driverQA.JavaScriptClick("btn-continuar", "id");
    }
}