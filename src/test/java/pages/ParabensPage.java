package pages;

import org.junit.Assert;
import support.BaseSteps;
import support.DriverQA;
import support.Hooks;

public class ParabensPage extends BaseSteps {
    private DriverQA driver;

    public ParabensPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String xpathParabensNomeCliente = "//*[@data-automation='parabens-sucesso']";
    private String xpathParabensNomePlano = "//*[@data-automation='msg-sucesso-plano']";
    private String xpathParabensPedidoCliente = "//*[@data-automation='msg-sucesso-pedido']";
    private String xpathNumeroDoPedido = "//*[@class='information-label order-label-title d-flex']";
    private String xpathNomePedido = "(//*[@class='information-label d-flex'])[1]";
    private String xpathCpfPedido = "(//*[@class='information-label d-flex'])[2]";
    private String xpathTelefonePedido = "(//*[@class='information-label d-flex'])[3]";
    private String xpathPlanoNomePedido = "(//*[@class='information-label d-flex'])[4]";
    private String xpathValorPedido = "(//*[@class='information-label d-flex'])[5]";
    private String xpathFormaPagamentoPedido = "(//*[@class='information-label d-flex'])[6]";
    private String xpathDiaVencimentoFatura = "(//*[@class='information-label d-flex'])[7]";
    private String xpathEnderecoDeEntregaPedido = "(//*[@class='information-label d-flex'])[8]";
    private String xpathTipoDoFretePedido = "//*[@data-automation='tipo-frete-conclusao']";
    private String xpathValorDoFretePedido = "//*[@data-automation='valor-frete-conclusao']";

    public void validarCamposPedido() {
        Assert.assertTrue(driver.getText(xpathParabensNomeCliente, "xpath").contains("Parabéns, " + DadosPessoaisPage.nomeCliente.split(" ")[0]));
        Assert.assertTrue(driver.getText(xpathParabensNomePlano, "xpath").contains(HomePage.tituloCardHome) && driver.getText(xpathPlanoNomePedido, "xpath").contains(HomePage.tituloCardHome));
        Assert.assertTrue(driver.getText(xpathParabensPedidoCliente, "xpath").contains(driver.getText(xpathNumeroDoPedido, "xpath").replaceAll("\\s", "").substring(14)));
        Assert.assertTrue(driver.getText(xpathNomePedido, "xpath").contains(DadosPessoaisPage.nomeCliente.toUpperCase()));
        Assert.assertTrue(driver.getText(xpathCpfPedido, "xpath").contains(driver.mascararCpf(CarrinhoPage.cpfCliente)));
        if (Hooks.tagScenarios.contains("@entregaExpressa")) {
            Assert.assertTrue(driver.getText(xpathTipoDoFretePedido, "xpath").contains(driver.capitalizeFirstLetter(DadosPessoaisPage.tipoDeFreteCarrinho)));
            Assert.assertTrue(driver.getText(xpathValorDoFretePedido, "xpath").contains(driver.capitalizeFirstLetter(DadosPessoaisPage.valorDoFreteCarrinho)));
        } else if (Hooks.tagScenarios.contains("@entregaConvencional")) {
            Assert.assertTrue(driver.getText(xpathTipoDoFretePedido, "xpath").contains(DadosPessoaisPage.tipoDeFreteCarrinho));
            Assert.assertTrue(driver.getText(xpathValorDoFretePedido, "xpath").contains(Character.toUpperCase(DadosPessoaisPage.valorDoFreteCarrinho.charAt(6)) + DadosPessoaisPage.valorDoFreteCarrinho.substring(7)));
            Assert.assertTrue(driver.getText(xpathTelefonePedido, "xpath").contains(driver.mascararTelefone(CarrinhoPage.telefoneCliente)));
        }else{
            Assert.assertTrue(driver.getText(xpathTelefonePedido, "xpath").contains(driver.mascararTelefone(CarrinhoPage.telefoneCliente)));
        }
        Assert.assertTrue(driver.getText(xpathValorPedido, "xpath").contains(CustomizarFaturaPage.valorPedidoCarrinho));
        Assert.assertTrue(driver.getText(xpathFormaPagamentoPedido, "xpath").contains(CustomizarFaturaPage.formaPagamentoPedidoCarrinho));
        Assert.assertTrue(driver.getText(xpathDiaVencimentoFatura, "xpath").contains(CustomizarFaturaPage.dataVencimentoFatura));
        Assert.assertTrue(driver.getText(xpathEnderecoDeEntregaPedido, "xpath").contains(driver.montaEnderecoValidacaoParabens(DadosPessoaisPage.enderecoCliente, DadosPessoaisPage.numeroEndCliente, DadosPessoaisPage.complementoCliente, DadosPessoaisPage.bairroCliente, DadosPessoaisPage.cidadeCliente, DadosPessoaisPage.ufCliente, DadosPessoaisPage.cepCliente)));
    }
}
