package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.utils.DriverQA;

import java.util.function.BiConsumer;

import static web.support.utils.Constants.*;
import static web.support.utils.Constants.DeliveryMode.CONVENTIONAL;
import static web.support.utils.Constants.DeliveryMode.EXPRESS;

@Component
@ScenarioScope
public class DadosPessoaisPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public DadosPessoaisPage(DriverQA driverQA, CartOrder cartOrder) {
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
    private WebElement cepCobranca;

    private void validarCampoCep() {
        cep = driverQA.findElement("txt-cep-endereco-entrega", "id");
        Assert.assertTrue(cep.isDisplayed());
        Assert.assertEquals(cep.getAttribute("value"), "");
    }

    private void inserirDadosEndereco(String numeroId, String complementoId, String numero, String complemento) {
        WebElement numeroElement = driverQA.findElement(numeroId, "id");
        WebElement complementoElement = driverQA.findElement(complementoId, "id");

        Assert.assertTrue(numeroElement.getAttribute("value").isEmpty());
        Assert.assertTrue(complementoElement.getAttribute("value").isEmpty());

        driverQA.actionSendKeys(numeroElement, numero);
        driverQA.actionSendKeys(complementoElement, complemento);
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
    }

    public void validarTiposEntregaEChip(boolean showDeliveryModes, DeliveryMode deliveryMode, boolean isDeviceCart) {
        String conventionalParent = "//*[@id='conventional-parent']";
        String expressParent = "//*[@id='express-parent']";

        //Chip comum
        String commonChip = "//*[@id='rdn-chipTypeCommom']";
        chipComumConvencional = driverQA.findElement(conventionalParent + commonChip, "xpath");
        chipComumExpressa = driverQA.findElement(expressParent + commonChip, "xpath");

        //eSIM
        String eSimChip = "//*[@id='rdn-chipTypeEsim']";
        chipEsimConvencional = driverQA.findElement(conventionalParent + eSimChip, "xpath");
        chipEsimExpress = driverQA.findElement(expressParent + eSimChip, "xpath");

        //Entrega
        entregaConvencional = driverQA.findElement("rdn-entrega-convencional", "id");
        entregaExpressa = driverQA.findElement("rdn-entrega-expressa", "id");

        WebElement usarMesmoEnderecoCobranca = driverQA.findElement("endereco-cobranca_checkbox", "id");

        if (showDeliveryModes) {
            //Elementos pai dos inputs para validar a exibição
            WebElement entregaConvencionalParent = driverQA.findElement(conventionalParent, "xpath");
            WebElement entregaExpressaParent = driverQA.findElement(expressParent, "xpath");
            WebElement enderecoCobrancaParent = usarMesmoEnderecoCobranca.findElement(By.xpath("../../.."));  //div pai do pai do pai do checkbox

            BiConsumer<WebElement, WebElement> validateChipTypes = (common, eSim) -> {
                if (!isDeviceCart) {
                    Assert.assertTrue("Chip comum selecionado", common.isSelected());
                    Assert.assertFalse("Chip eSim desmarcado", eSim.isSelected());
                } else {
                    Assert.assertNull("Escolha de chip para Aparelho acontece na PDP", common);
                    Assert.assertNull("Escolha de chip para Aparelho acontece na PDP", eSim);
                }
            };

            if (deliveryMode == CONVENTIONAL) {
                validateChipTypes.accept(chipComumConvencional, chipEsimConvencional);

                //Exibe entrega convencional
                Assert.assertTrue(entregaConvencional.isSelected());
                Assert.assertTrue(entregaConvencionalParent.isDisplayed());

                //Exibe apenas o bloco de entrega convencional. Ambos existem no html (convencional e expressa) mas só um é exibido
                Assert.assertFalse(entregaExpressaParent.isDisplayed());

                //Endereço de cobrança apenas em entrega expressa
                Assert.assertFalse(enderecoCobrancaParent.isDisplayed());
            } else {
                validateChipTypes.accept(chipComumExpressa, chipEsimExpress);

                //Exibe entrega expressa
                Assert.assertTrue(entregaExpressa.isSelected());
                Assert.assertTrue(entregaExpressaParent.isDisplayed());

                //Exibe a seção para endereço de cobrança com o checkbox marcado
                Assert.assertTrue(enderecoCobrancaParent.isDisplayed());
                Assert.assertTrue(usarMesmoEnderecoCobranca.isSelected());

                //Exibe apenas o bloco de entrega expressa. Ambos existem no html (convencional e expressa) mas só um é exibido
                Assert.assertFalse(entregaConvencionalParent.isDisplayed());
            }
        } else { //Fluxo migração de Pré, não existe entrega. Elementos não existem no html
            //Chip
            Assert.assertNull(chipComumConvencional);
            Assert.assertNull(chipComumExpressa);
            Assert.assertNull(chipEsimConvencional);
            Assert.assertNull(chipEsimExpress);

            //Entrega
            Assert.assertNull(entregaConvencional);
            Assert.assertNull(entregaExpressa);

            Assert.assertNull(usarMesmoEnderecoCobranca);
        }
    }

    public void inserirDadosEnderecoEntrega(String numero, String complemento) {
        inserirDadosEndereco("txt-numero-endereco-entrega", "txt-complemento-endereco-entrega", numero, complemento);
    }

    public void clicarContinuar() {
        driverQA.javaScriptClick("btn-continuar", "id");
    }

    public void clicarUsarMesmoEnderecoEntrega() {
        driverQA.javaScriptClick("endereco-cobranca_checkbox", "id");
    }

    public void exibirCepCobranca() {
        cepCobranca = driverQA.findElement("txt-cep-endereco-cobranca", "id");
        driverQA.waitElementVisibility(cepCobranca, 2);
        Assert.assertTrue(cepCobranca.getAttribute("value").isEmpty());
    }

    public void inserirCepCobranca(String cep) {
        driverQA.actionSendKeys("txt-cep-endereco-cobranca","id",cep);

        WebElement endereco = driverQA.findElement("txt-endereco-endereco-cobranca", "id");
        driverQA.waitElementVisibility(endereco, 12);
        Assert.assertNotEquals("Preenchimento automático [endereço]", endereco.getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [bairro]", driverQA.findElement("txt-cidade-endereco-cobranca", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [estado]", driverQA.findElement("txt-bairro-endereco-cobranca", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [cidade]", driverQA.findElement("txt-estado-endereco-cobranca", "id").getAttribute("value"), "");
    }

    public void inserirDadosEnderecoCobranca(String numero, String complemento) {
        inserirDadosEndereco("txt-numero-endereco-cobranca", "txt-complemento-endereco-cobranca", numero, complemento);
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