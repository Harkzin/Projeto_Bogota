package pages;

import cucumber.api.java.es.Dados;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.junit.Assert;
import support.BaseSteps;
import support.DriverQA;

public class ParabensPage extends BaseSteps {
    private DriverQA driver;
    public ParabensPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String xpathParabensNomeCliente = "//*[@data-automation='parabens-sucesso']";
    private String xpathParabensNomePlano = "//*[@data-automation='msg-sucesso-plano']";
    private String xpathParabensPedidoCliente = "//*[@data-automation='msg-sucesso-pedido']";
    public static String xpathNumeroDoPedido = "//*[@data-automation='informacao-pedido']";
    private String xpathNomePedido = "//*[@data-automation='informacao-nome']";
    private String xpathCpfPedido = "//*[@data-automation='informacao-cpf']";
    private String xpathTelefonePedido = "//*[@data-automation='informacao-numero-linha']";
    private String xpathPlanoNomePedido = "//*[@data-automation='informacao-plano']";
    private String xpathValorPedido = "//*[@data-automation='informacao-valor']";
    private String xpathFormaPagamentoPedido = "//*[@data-automation='informacao-forma-pagamento']";
    private String xpathDiaVencimentoFatura = "//*[@data-automation='informacao-vencimento']";
    private String xpathEnderecoDeEntregaPedido = "//*[@data-automation='endereco-conclusao']";
    private String xpathTipoDoFretePedido = "//*[@data-automation='tipo-frete-conclusao']";
    private String xpathTipoEntregaPedido = "//*[@data-automation='valor-frete-conclusao']";

    public void validarCamposPedido() {
        Assert.assertTrue(driver.getText(xpathParabensNomeCliente, "xpath").contains("Parabéns, " + DadosPessoaisPage.nomeCliente.split(" ")[0]));
        Assert.assertTrue(driver.getText(xpathParabensNomePlano, "xpath").contains(HomePage.tituloCardHome) && driver.getText(xpathPlanoNomePedido, "xpath").contains(HomePage.tituloCardHome));
        Assert.assertTrue(driver.getText(xpathParabensPedidoCliente, "xpath").contains(driver.getText(xpathNumeroDoPedido, "xpath")));
        Assert.assertTrue(driver.getText(xpathNomePedido, "xpath").contains(DadosPessoaisPage.nomeCliente.toUpperCase()));
        Assert.assertTrue(driver.getText(xpathCpfPedido, "xpath").contains(driver.mascararCpf(CarrinhoPage.cpfCliente)));
        Assert.assertTrue(driver.getText(xpathTelefonePedido, "xpath").contains(driver.mascararTelefone(CarrinhoPage.telefoneCliente)));
        System.out.println(driver.getText(xpathValorPedido, "xpath"));
        System.out.println(CustomizarFaturaPage.valorPedidoCarrinho);
        Assert.assertTrue(driver.getText(xpathValorPedido, "xpath").contains(CustomizarFaturaPage.valorPedidoCarrinho));
//        System.out.println(driver.getText(xpathFormaPagamentoPedido, "xpath"));
//        System.out.println(CarrinhoPage.xpathMetodoPagamentoResumo);
        Assert.assertTrue(driver.getText(xpathFormaPagamentoPedido, "xpath").contains(CustomizarFaturaPage.formaPagamentoPedidoCarrinho));
//        Assert.assertTrue(driver.getText(xpathTipoDoFretePedido, "xpath").contains(DadosPessoaisPage.tipoFrete));
//        Assert.assertTrue(driver.getText(xpathTipoEntregaPedido, "xpath").contains(DadosPessoaisPage.tipoEntrega));
//        driver.getText(xpathDiaVencimentoFatura)
//        driver.getText(xpathEnderecoDeEntregaPedido)
    }
}
