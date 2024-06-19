package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

import static support.utils.Constants.*;
import static support.utils.Constants.DeliveryMode.CONVENTIONAL;
import static support.utils.Constants.DeliveryMode.EXPRESS;

@Component
public class DadosPessoaisPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public DadosPessoaisPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private WebElement cep;
    private WebElement chipComumConvencional;
    private WebElement chipComumExpressa;
    private WebElement entregaConvencional;
    private WebElement entregaExpressa;
    private WebElement chipEsimConvencional;
    private WebElement chipEsimExpress;

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
        Assert.assertNotEquals("Preenchimento automático [endereço]", endereco.getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [bairro]", driverQA.findElement("txt-bairro-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [estado]", driverQA.findElement("txt-estado-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [cidade]", driverQA.findElement("txt-cidade-endereco-entrega", "id").getAttribute("value"), "");
        validarTiposChip();
    }

    private void validarTiposChip() {
        chipComumConvencional = driverQA.findElement("rdn-chipTypeCommom", "id");
        chipComumExpressa = driverQA.findElement("rdn-chipTypeCommomExpress", "id");
        chipEsimConvencional = driverQA.findElement("rdn-chipTypeEsim", "id");
        chipEsimExpress = driverQA.findElement("rdn-chipTypeEsimExpress", "id");

        if(cartOrder.delivery.deliveryMode == EXPRESS) {
            Assert.assertTrue(chipComumExpressa.isSelected());
            Assert.assertFalse(chipEsimExpress.isSelected());
        } else {
            Assert.assertTrue(chipComumConvencional.isSelected());
            Assert.assertFalse(chipEsimConvencional.isSelected());
        }
    }

    public void validarTiposEntrega(boolean showDeliveryModes, DeliveryMode deliveryMode) {
        entregaConvencional = driverQA.findElement("rdn-convencional", "id");
        entregaExpressa = driverQA.findElement("rdn-entrega-expressa", "id");
        WebElement usarMesmoEnderecoCobranca = driverQA.findElement("endereco-cobranca_checkbox", "id");

        if (showDeliveryModes) {
            WebElement enderecoCobrancaParent = usarMesmoEnderecoCobranca.findElement(By.xpath("../../.."));  //div pai do pai do pai do checkbox
            WebElement entregaConvencionalParent = entregaConvencional.findElement(By.xpath("../../..")); //div pai do pai do pai do input
            WebElement entregaExpressaParent = entregaExpressa.findElement(By.xpath("../../../..")); //div pai do pai do pai do pai do input

            if (deliveryMode == CONVENTIONAL) {
                Assert.assertTrue(entregaConvencional.isSelected());
                Assert.assertTrue(entregaConvencionalParent.isDisplayed());

                Assert.assertFalse(entregaExpressaParent.isDisplayed());
                Assert.assertFalse(enderecoCobrancaParent.isDisplayed());
            } else {
                Assert.assertTrue(entregaExpressa.isSelected());
                Assert.assertTrue(enderecoCobrancaParent.isDisplayed());
                Assert.assertTrue(usarMesmoEnderecoCobranca.isSelected());
                Assert.assertTrue(entregaExpressaParent.isDisplayed());

                Assert.assertFalse(entregaConvencionalParent.isDisplayed());
            }
        } else { //Fluxo migração de Pré, não existe entrega. Elementos não existem no html também.
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
        driverQA.javaScriptClick("btn-continuar", "id");
    }

    public void selecionarEsim() {
        if(cartOrder.delivery.deliveryMode == EXPRESS) {
            driverQA.javaScriptClick(chipEsimExpress);
            driverQA.waitElementInvisibility(entregaExpressa, 1);
        } else {
            driverQA.javaScriptClick(chipEsimConvencional);
            driverQA.waitElementInvisibility(entregaConvencional, 1);
        }
        //TODO ECCMAUT-940
    }
}