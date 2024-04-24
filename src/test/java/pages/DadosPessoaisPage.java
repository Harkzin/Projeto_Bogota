package pages;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import support.DriverQA;

import static pages.ComumPage.Cart_processType;
import static pages.ComumPage.ProcessType.ACQUISITION;

public class DadosPessoaisPage {
    private final DriverQA driverQA;

    public DadosPessoaisPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private WebElement chipComumConvencional;
    private WebElement chipComumExpressa;
    private WebElement usarMesmoEnderecoCobranca;
    public boolean showDeliveryModes = true;

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

    public void inserirCep(String tipoCep) {
        String cep;

        if (tipoCep.equals("convencional")) {
            cep = "01001001";
        } else if (tipoCep.equals("expressa")) {
            cep = (Cart_processType == ACQUISITION) ? "01001000" : "01001010"; //expressa aquisição ou expressa port
        } else {
            cep = tipoCep;
        }

        driverQA.actionSendKeys("txt-cep-endereco-entrega", "id", cep);

        WebElement enderecoElement = driverQA.findElement("txt-endereco-endereco-entrega", "id");
        driverQA.waitElementVisibility(enderecoElement, 10);
        Assert.assertNotEquals(enderecoElement.getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-bairro-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-estado-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals(driverQA.findElement("txt-cidade-endereco-entrega", "id").getAttribute("value"), "");
    }

    public void validarTiposEntrega(boolean isExpressDelivery) {
        chipComumConvencional = driverQA.findElement("rdn-chipTypeCommom", "id");
        chipComumExpressa = driverQA.findElement("rdn-chipTypeCommomExpress", "id");
        usarMesmoEnderecoCobranca = driverQA.findElement("endereco-cobranca_checkbox", "id");

        WebElement entregaConvencional = driverQA.findElement("rdn-convencional", "id");
        WebElement entregaExpressa = driverQA.findElement("rdn-entrega-expressa", "id");

        if (showDeliveryModes) {
            WebElement enderecoCobrancaParent = usarMesmoEnderecoCobranca.findElement(By.xpath("../../.."));  //div pai do pai do pai do checkbox
            WebElement entregaConvencionalParent = entregaConvencional.findElement(By.xpath("../../..")); //div pai do pai do pai do input
            WebElement entregaExpressaParent = entregaExpressa.findElement(By.xpath("../../../..")); //div pai do pai do pai do pai do input

            System.out.println(enderecoCobrancaParent.getAttribute("class"));

            if (!isExpressDelivery) {
                Assert.assertTrue(chipComumConvencional.isSelected());
                Assert.assertTrue(entregaConvencional.isSelected());
                Assert.assertTrue(entregaConvencionalParent.isDisplayed());

                Assert.assertFalse(entregaExpressaParent.isDisplayed());
                Assert.assertFalse(enderecoCobrancaParent.isDisplayed());
            } else {
                Assert.assertTrue(chipComumExpressa.isSelected());
                Assert.assertTrue(entregaExpressa.isSelected());
                Assert.assertTrue(enderecoCobrancaParent.isDisplayed());
                Assert.assertTrue(usarMesmoEnderecoCobranca.isSelected());
                Assert.assertTrue(entregaExpressaParent.isDisplayed());

                Assert.assertFalse(entregaConvencionalParent.isDisplayed());
            }
        } else {
            Assert.assertNull(chipComumConvencional);
            Assert.assertNull(chipComumExpressa);
            Assert.assertNull(usarMesmoEnderecoCobranca);
            Assert.assertNull(entregaConvencional);
            Assert.assertNull(entregaExpressa);
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