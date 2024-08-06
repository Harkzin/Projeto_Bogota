package pages;

import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public ParabensPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private final String parabensNomeCliente = "msg-parabens-sucesso";
    private final String parabensNomePlano = "msg-sucesso-plano";
    private final String parabensPedidoCliente = "msg-informacao-pedido";
    private final String xpathNumeroDoPedido = "(//*[@data-automation='informacao-pedido'])[2]";
    private final String nomePedido = "msg-informacao-nome";
    private final String cpfPedido = "msg-informacao-cpf";
    private final String xpathTelefonePedido = "//*[@data-automation='informacao-telefone']";
    private final String planoNomePedido = "msg-informacao-plano";
    private final String valorPedido = "msg-informacao-valor";
    private final String xpathFormaPagamentoPedido = "msg-informacao-pagamento";
    private final String diaVencimentoFatura = "msg-informacao-vencimento";
    private final String enderecoDeEntregaPedido = "msg-endereco";
    private final String xpathTipoDoFretePedido = "//*[@data-automation='tipo-frete-conclusao']";
    private final String xpathValorDoFretePedido = "//*[@data-automation='valor-frete-conclusao']";
    public static String pedidoParabens;

    public void validarPaginaParabens() {
        driverQA.waitPageLoad("/checkout/orderConfirmation", 30);
    }

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

    public void clicarOkEntendi() {
        driverQA.javaScriptClick("btn-entendi-modal-abr", "id");
    }

    public void validarCamposPedido() {
        //TODO ECCMAUT-351
    }
}