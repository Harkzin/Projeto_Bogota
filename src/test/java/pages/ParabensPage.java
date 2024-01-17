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
    private String xpathNumeroDoPedido = "(//*[@data-automation='informacao-pedido'])[2]";
    private String xpathNomePedido = "//*[@data-automation='informacao-nome']";
    private String xpathCpfPedido = "//*[@data-automation='informacao-cpf']";
    private String xpathTelefonePedido = "//*[@data-automation='informacao-telefone']";
    private String xpathPlanoNomePedido = "(//*[@data-automation='informacao-plano'])";
    private String xpathValorPedido = "(//*[@data-automation='informacao-valor'])";
    private String xpathFormaPagamentoPedido = "(//*[@data-automation='informacao-forma-pagamento'])";
    private String xpathDiaVencimentoFatura = "(//*[@data-automation='informacao-vencimento'])";
    private String xpathEnderecoDeEntregaPedido = "//*[@data-automation='endereco-conclusao']";
    private String xpathTipoDoFretePedido = "//*[@data-automation='tipo-frete-conclusao']";
    private String xpathValorDoFretePedido = "//*[@data-automation='valor-frete-conclusao']";

    public void validarCamposPedido() {
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            driver.JavaScriptClick("(//*[@title='Acordion clicável para expandir o conteúdo'])[1]", "xpath");
        }
        Assert.assertTrue(driver.getText(xpathParabensNomeCliente, "xpath").contains("Parabéns, " + driver.capitalizeFirstLetter(DadosPessoaisPage.nomeCliente.split(" ")[0]) + "!"));
        Assert.assertTrue(driver.getText(xpathParabensNomePlano, "xpath").contains(HomePage.tituloCardHome) && driver.getText(xpathPlanoNomePedido, "xpath").contains(HomePage.tituloCardHome));
        Assert.assertTrue(driver.getText(xpathParabensPedidoCliente, "xpath").contains(driver.getText(xpathNumeroDoPedido, "xpath").replaceAll("\\s", "").substring(14).replaceAll("^0+","")));
        Assert.assertTrue(driver.getText(xpathNomePedido, "xpath").contains(DadosPessoaisPage.nomeCliente.toUpperCase()));
        Assert.assertTrue(driver.getText(xpathCpfPedido, "xpath").contains(driver.mascararCpf(CarrinhoPage.cpfCliente)));
        if (Hooks.tagScenarios.contains("@entregaExpressa")) {
            Assert.assertTrue(driver.getText(xpathTipoDoFretePedido, "xpath").contains(driver.capitalizeFirstLetter(DadosPessoaisPage.tipoDeFreteCarrinho)));
            Assert.assertTrue(driver.getText(xpathValorDoFretePedido, "xpath").contains(driver.capitalizeFirstLetter(DadosPessoaisPage.valorDoFreteCarrinho)));
        } else if (Hooks.tagScenarios.contains("@entregaConvencional")) {
            Assert.assertTrue(driver.getText(xpathTipoDoFretePedido, "xpath").contains(DadosPessoaisPage.tipoDeFreteCarrinho));
            Assert.assertTrue(driver.getText(xpathValorDoFretePedido, "xpath").contains(Character.toUpperCase(DadosPessoaisPage.valorDoFreteCarrinho.charAt(6)) + DadosPessoaisPage.valorDoFreteCarrinho.substring(7)));
        } else {
            Assert.assertTrue(driver.getText(xpathTelefonePedido, "xpath").contains(driver.mascararTelefone(CarrinhoPage.telefoneCliente)));
        }
        Assert.assertTrue(driver.getText(xpathValorPedido, "xpath").contains(CustomizarFaturaPage.valorPedidoCarrinho));
        Assert.assertTrue(driver.getText(xpathFormaPagamentoPedido, "xpath").contains(CustomizarFaturaPage.formaPagamentoPedidoCarrinho));
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            Assert.assertTrue(driver.getText(xpathDiaVencimentoFatura, "xpath").contains(CustomizarFaturaPage.dataVencimentoFatura));
            driver.JavaScriptClick("(//*[@title='Acordion clicável para expandir o conteúdo'])[2]", "xpath");
            Assert.assertTrue(driver.getText(xpathEnderecoDeEntregaPedido, "xpath").contains(driver.montaEnderecoValidacaoParabens(DadosPessoaisPage.enderecoCliente, DadosPessoaisPage.numeroEndCliente, DadosPessoaisPage.complementoCliente, DadosPessoaisPage.bairroCliente, DadosPessoaisPage.cidadeCliente, DadosPessoaisPage.ufCliente, DadosPessoaisPage.cepCliente)));
        }
    }
}
