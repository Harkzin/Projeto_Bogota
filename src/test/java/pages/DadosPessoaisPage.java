package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import support.DriverQA;

public class DadosPessoaisPage {
    private final DriverQA driverQA;

    public DadosPessoaisPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private WebElement cep;
    private WebElement chipComumConvencional;
    private WebElement chipComumExpressa;
    private WebElement usarMesmoEnderecoCobranca;
    //public boolean showDeliveryModes = true;

    private void validarCampoCep() {
        cep = driverQA.findElement("txt-cep-endereco-entrega", "id");
        Assert.assertTrue(cep.isDisplayed());
        Assert.assertEquals(cep.getAttribute("value"), "");
    }

    public void validarPaginaDadosPessoais() {
        driverQA.waitPageLoad("/checkout/multi/delivery-address/addClaroAddress", 15);

        validarCampoCep();
    }

    public void validarPaginaDadosPessoaisBloqueioCep(String mensagem) {
        driverQA.waitPageLoad("/checkout/multi/delivery-address/addClaroAddress", 10);

        validarCampoCep();

        WebElement fraseErro = driverQA.findElement("txt-cep-endereco-entrega-error", "id");
        Assert.assertTrue(fraseErro.isDisplayed());
        Assert.assertEquals(mensagem, fraseErro.getText());

        Assert.assertNull(driverQA.findElement("txt-nome-completo", "id"));
        Assert.assertNull(driverQA.findElement("txt-nascimento", "id"));
        Assert.assertNull(driverQA.findElement("txt-nome-mae", "id"));
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

    public void inserirCep(String cepNumber) {
        driverQA.actionSendKeys(cep, cepNumber);

        WebElement endereco = driverQA.findElement("txt-endereco-endereco-entrega", "id");
        driverQA.waitElementVisibility(endereco, 12);
        Assert.assertNotEquals("Preenchimento automático", endereco.getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático", driverQA.findElement("txt-bairro-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático", driverQA.findElement("txt-estado-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático", driverQA.findElement("txt-cidade-endereco-entrega", "id").getAttribute("value"), "");
    }

    public void validarTiposEntrega(boolean showDeliveryModes, boolean isExpressDelivery) {
        chipComumConvencional = driverQA.findElement("rdn-chipTypeCommom", "id");
        chipComumExpressa = driverQA.findElement("rdn-chipTypeCommomExpress", "id");
        usarMesmoEnderecoCobranca = driverQA.findElement("endereco-cobranca_checkbox", "id");

        WebElement entregaConvencional = driverQA.findElement("rdn-convencional", "id");
        WebElement entregaExpressa = driverQA.findElement("rdn-entrega-expressa", "id");

        if (showDeliveryModes) {
            WebElement enderecoCobrancaParent = usarMesmoEnderecoCobranca.findElement(By.xpath("../../.."));  //div pai do pai do pai do checkbox
            WebElement entregaConvencionalParent = entregaConvencional.findElement(By.xpath("../../..")); //div pai do pai do pai do input
            WebElement entregaExpressaParent = entregaExpressa.findElement(By.xpath("../../../..")); //div pai do pai do pai do pai do input

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

    public void clicarContinuar() {
        driverQA.JavaScriptClick("btn-continuar", "id");
    }

    public void selecionarEsim() {
        WebElement EsimInput = driverQA.findElement("rdn-chipTypeEsimExpress", "id");
        driverQA.JavaScriptClick(EsimInput);
        //deve realizar a validação de exibição dos textos citados no documento
    }
}