package pages;

import org.junit.Assert;
import support.BaseSteps;
import support.DriverQA;
import support.Hooks;

public class ParabensPage extends BaseSteps {
    private final DriverQA driver;

    public ParabensPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    private String parabensNomeCliente = "msg-parabens-sucesso";
    private String parabensNomePlano = "msg-sucesso-plano";
    private String parabensPedidoCliente = "msg-informacao-pedido";
    private String xpathNumeroDoPedido = "(//*[@data-automation='informacao-pedido'])[2]";
    private String nomePedido = "msg-informacao-nome";
    private String cpfPedido = "msg-informacao-cpf";
    private String xpathTelefonePedido = "//*[@data-automation='informacao-telefone']";
    private String planoNomePedido = "msg-informacao-plano";
    private String valorPedido = "msg-informacao-valor";
    private String xpathFormaPagamentoPedido = "msg-informacao-pagamento";
    private String diaVencimentoFatura = "msg-informacao-vencimento";
    private String enderecoDeEntregaPedido = "msg-endereco";
    private String xpathTipoDoFretePedido = "//*[@data-automation='tipo-frete-conclusao']";
    private String xpathValorDoFretePedido = "//*[@data-automation='valor-frete-conclusao']";
    public static String pedidoParabens;

    public String mascararCpf(String cpf) {
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9);
    }

    public String mascararTelefone(String telefone) {
        return "(" + telefone.substring(0, 2) + ") " +
                telefone.substring(2, 7) + "-" +
                telefone.substring(7);
    }

    public String montaEnderecoValidacaoParabens(String enderecoCliente, String numeroEndCliente, String
            complementoCliente, String bairroCliente, String cidadeCliente, String ufCliente, String cepCliente) {
        return enderecoCliente + ", " + numeroEndCliente + " - " + complementoCliente.toUpperCase() + " - " + bairroCliente + " - " + cidadeCliente + " " + ufCliente.substring(3) + " CEP " + cepCliente.substring(0, 5) + "-" + cepCliente.substring(5);
    }

    public static String capitalizeFirstLetter(String text) {
        String[] palavras = text.split("\\s+");  // Divide a frase em palavras
        StringBuilder resultado = new StringBuilder();
        for (String palavra : palavras) {
            char primeiraLetra = Character.toUpperCase(palavra.charAt(0));
            String restantePalavra = palavra.substring(1).toLowerCase();
            resultado.append(primeiraLetra).append(restantePalavra).append(" ");
        }
        return resultado.toString().trim();
    }

    public void validarCamposPedido() {
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            driver.JavaScriptClick("(//*[@title='Acordion clicável para expandir o conteúdo'])[1]", "xpath");
        }
        Assert.assertTrue(driver.getText(parabensNomeCliente, "id").contains("Parabéns, " + capitalizeFirstLetter(DadosPessoaisPage.nomeCliente.split(" ")[0]) + "!"));
        Assert.assertTrue(driver.getText(parabensNomePlano, "id").contains(HomePage.tituloCardHome) && driver.getText(planoNomePedido, "xpath").contains(HomePage.tituloCardHome));
        Assert.assertTrue(driver.getText(parabensPedidoCliente, "id").contains(driver.getText(xpathNumeroDoPedido, "xpath").replaceAll("\\s", "").substring(14).replaceAll("^0+", "")));
        pedidoParabens = driver.getText(xpathNumeroDoPedido, "id").replaceAll("\\s", "").substring(14).replaceAll("^0+", "");
        Assert.assertTrue(driver.getText(nomePedido, "id").contains(DadosPessoaisPage.nomeCliente.toUpperCase()));
        Assert.assertTrue(driver.getText(cpfPedido, "id").contains(mascararCpf(CarrinhoPage.cpfCliente)));
        if (Hooks.tagScenarios.contains("@entregaExpressa")) {
            Assert.assertTrue(driver.getText(xpathTipoDoFretePedido, "xpath").contains(capitalizeFirstLetter(DadosPessoaisPage.tipoDeFreteCarrinho)));
            Assert.assertTrue(driver.getText(xpathValorDoFretePedido, "xpath").contains(capitalizeFirstLetter(DadosPessoaisPage.valorDoFreteCarrinho)));
        } else if (Hooks.tagScenarios.contains("@entregaConvencional")) {
            Assert.assertTrue(driver.getText(xpathTipoDoFretePedido, "xpath").contains(DadosPessoaisPage.tipoDeFreteCarrinho));
            Assert.assertTrue(driver.getText(xpathValorDoFretePedido, "xpath").contains(Character.toUpperCase(DadosPessoaisPage.valorDoFreteCarrinho.charAt(6)) + DadosPessoaisPage.valorDoFreteCarrinho.substring(7)));
        } else {
            Assert.assertTrue(driver.getText(xpathTelefonePedido, "xpath").contains(mascararTelefone(CarrinhoPage.telefoneCliente)));
        }
        Assert.assertTrue(driver.getText(valorPedido, "id").contains(CustomizarFaturaPage.valorPedidoCarrinho));
        Assert.assertTrue(driver.getText(xpathFormaPagamentoPedido, "xpath").contains(CustomizarFaturaPage.formaPagamentoPedidoCarrinho));
        if (Hooks.tagScenarios.contains("@aquisicao") || Hooks.tagScenarios.contains("@portabilidade") || Hooks.tagScenarios.contains("@migracaoPre")) {
            Assert.assertTrue(driver.getText(diaVencimentoFatura, "id").contains(CustomizarFaturaPage.dataVencimentoFatura));
            driver.JavaScriptClick("(//*[@tabindex='0'])[6]", "xpath");
            Assert.assertTrue(driver.getText(enderecoDeEntregaPedido, "id").contains(montaEnderecoValidacaoParabens(DadosPessoaisPage.enderecoCliente, DadosPessoaisPage.numeroEndCliente, DadosPessoaisPage.complementoCliente, DadosPessoaisPage.bairroCliente, DadosPessoaisPage.cidadeCliente, DadosPessoaisPage.ufCliente, DadosPessoaisPage.cepCliente)));
        }
    }
}