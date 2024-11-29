package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.utils.DriverWeb;

import java.util.function.BiConsumer;

import static web.support.utils.Constants.*;
import static web.support.utils.Constants.ZoneDeliveryMode.CONVENTIONAL;
import static web.support.utils.Constants.ZoneDeliveryMode.EXPRESS;

@Component
@ScenarioScope
public class DadosPessoaisPage {

    private final DriverWeb driverWeb;

    @Autowired
    public DadosPessoaisPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private WebElement cep;
    private WebElement chipComumConvencional;
    private WebElement chipComumExpressa;
    private WebElement entregaConvencional;
    private WebElement entregaExpressa;
    private WebElement chipEsimConvencional;
    private WebElement chipEsimExpress;
    private WebElement cepCobranca;

    private boolean bloqueioCepDiferente;

    private void validarCampoCep() {
        cep = driverWeb.findElement("txt-cep-endereco-entrega", "id");
        Assert.assertTrue(cep.isDisplayed());
        Assert.assertEquals(cep.getAttribute("value"), "");
    }

    private void inserirDadosEndereco(String numeroId, String complementoId, String numero, String complemento) {
        WebElement numeroElement = driverWeb.findElement(numeroId, "id");
        WebElement complementoElement = driverWeb.findElement(complementoId, "id");

        Assert.assertTrue(numeroElement.getAttribute("value").isEmpty());
        Assert.assertTrue(complementoElement.getAttribute("value").isEmpty());

        driverWeb.sendKeys(numeroElement, numero);
        driverWeb.sendKeys(complementoElement, complemento);
    }

    public void validarPaginaDadosPessoais() {
        driverWeb.waitPageLoad("/checkout/multi/delivery-address/addClaroAddress", 15);

        validarCampoCep();
    }

    public void validarPaginaDadosPessoaisBloqueioCep(String mensagem) {
        driverWeb.waitPageLoad("/checkout/multi/delivery-address/addClaroAddress", 10);

        validarCampoCep();

        WebElement fraseErro = driverWeb.findElement("txt-cep-endereco-entrega-error", "id");
        Assert.assertTrue(fraseErro.isDisplayed());
        Assert.assertEquals(mensagem, fraseErro.getText());

        Assert.assertNull(driverWeb.findElement("txt-nome-completo", "id"));
        Assert.assertNull(driverWeb.findElement("txt-nascimento", "id"));
        Assert.assertNull(driverWeb.findElement("txt-nome-mae", "id"));
        bloqueioCepDiferente = true;
    }

    public void inserirNome(String nomeCompleto) {
        driverWeb.sendKeys("txt-nome-completo", "id", nomeCompleto);
    }

    public void inserirDataNascimento(String dataNasc) {
        driverWeb.sendKeys("txt-nascimento", "id", dataNasc);
    }

    public void inserirNomeMae(String nomeMae) {
        driverWeb.sendKeys("txt-nome-mae", "id", nomeMae);
    }

    public void inserirCep(String cepNumber) {
        driverWeb.sendKeys(cep, cepNumber);

        WebElement endereco = driverWeb.findElement("txt-endereco-endereco-entrega", "id");
        driverWeb.waitElementVisible(endereco, 12);
        Assert.assertNotEquals("Preenchimento automático [endereço]", endereco.getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [bairro]", driverWeb.findElement("txt-bairro-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [estado]", driverWeb.findElement("txt-estado-endereco-entrega", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [cidade]", driverWeb.findElement("txt-cidade-endereco-entrega", "id").getAttribute("value"), "");
    }

    public void validarTiposEntregaEChip(boolean showDeliveryModes, ZoneDeliveryMode deliveryMode, boolean isDeviceCart) {
        String conventionalParent = "//*[@id='conventional-parent']";
        String expressParent = "//*[@id='express-parent']";

        //Chip comum
        String commonChip = "//*[@id='rdn-chipTypeCommom']";
        chipComumConvencional = driverWeb.findElement(conventionalParent + commonChip, "xpath");
        chipComumExpressa = driverWeb.findElement(expressParent + commonChip, "xpath");

        //eSIM
        String eSimChip = "//*[@id='rdn-chipTypeEsim']";
        chipEsimConvencional = driverWeb.findElement(conventionalParent + eSimChip, "xpath");
        chipEsimExpress = driverWeb.findElement(expressParent + eSimChip, "xpath");

        //Entrega
        entregaConvencional = driverWeb.findElement("rdn-0", "id"); //TODO Bug merge RL22 "rdn-entrega-convencional", "id");
        entregaExpressa = driverWeb.findElement("rdn-entrega-expressa", "id");

        WebElement usarMesmoEnderecoCobranca = driverWeb.findElement("endereco-cobranca_checkbox", "id");

        if (showDeliveryModes) {
            //Elementos pai dos inputs para validar a exibição
            WebElement entregaConvencionalParent = driverWeb.findElement(conventionalParent, "xpath");
            WebElement entregaExpressaParent = driverWeb.findElement(expressParent, "xpath");

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

                if (bloqueioCepDiferente) {
                    Assert.assertNull(usarMesmoEnderecoCobranca);
                } else {
                    //Endereço de cobrança apenas em entrega expressa
                    Assert.assertFalse(usarMesmoEnderecoCobranca.findElement(By.xpath("../../..")).isDisplayed());
                }

            } else {
                validateChipTypes.accept(chipComumExpressa, chipEsimExpress);

                //Exibe entrega expressa
                Assert.assertTrue(entregaExpressa.isSelected());
                Assert.assertTrue(entregaExpressaParent.isDisplayed());

                //Exibe a seção para endereço de cobrança com o checkbox marcado
                Assert.assertTrue(usarMesmoEnderecoCobranca.findElement(By.xpath("../../..")).isDisplayed());
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

    public void selecionarEsim(ZoneDeliveryMode deliveryMode) {
        if(deliveryMode == EXPRESS) {
            driverWeb.javaScriptClick(chipEsimExpress);
            driverWeb.waitElementInvisible(entregaExpressa, 1);
        } else {
            driverWeb.javaScriptClick(chipEsimConvencional);
            driverWeb.waitElementInvisible(entregaConvencional, 1);
        }
    }

    public void clicarUsarMesmoEnderecoEntrega() {
        driverWeb.javaScriptClick("endereco-cobranca_checkbox", "id");
    }

    public void exibirCepCobranca() {
        cepCobranca = driverWeb.findElement("txt-cep-endereco-cobranca", "id");
        driverWeb.waitElementVisible(cepCobranca, 2);
        Assert.assertTrue(cepCobranca.getAttribute("value").isEmpty());
    }

    public void inserirCepCobranca(String cep) {
        driverWeb.sendKeys("txt-cep-endereco-cobranca","id",cep);

        WebElement endereco = driverWeb.findElement("txt-endereco-endereco-cobranca", "id");
        driverWeb.waitElementVisible(endereco, 12);
        Assert.assertNotEquals("Preenchimento automático [endereço]", endereco.getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [bairro]", driverWeb.findElement("txt-cidade-endereco-cobranca", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [estado]", driverWeb.findElement("txt-bairro-endereco-cobranca", "id").getAttribute("value"), "");
        Assert.assertNotEquals("Preenchimento automático [cidade]", driverWeb.findElement("txt-estado-endereco-cobranca", "id").getAttribute("value"), "");
    }

    public void inserirDadosEnderecoCobranca(String numero, String complemento) {
        inserirDadosEndereco("txt-numero-endereco-cobranca", "txt-complemento-endereco-cobranca", numero, complemento);
    }

    public void clicarContinuar() {
        driverWeb.javaScriptClick("btn-continuar", "id");
    }
}