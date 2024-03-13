package pages;

import org.json.JSONException;
import org.junit.Assert;
import support.DriverQA;

import static support.RestAPI.getMessageFirstId;
import static support.RestAPI.getPedidoEnderecoNome;
import static support.RestAPI.purgeInbox;

public class EmailPage {
    private final DriverQA driver;

    public EmailPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public String montaEnderecoValidacaoEmail(String enderecoCliente, String numeroEndCliente, String
            complementoCliente, String bairroCliente, String cidadeCliente, String ufCliente, String cepCliente) {
        return enderecoCliente + " " + numeroEndCliente + " " + complementoCliente + ", " + bairroCliente + "," + cepCliente + ", " + cidadeCliente + ", " + ufCliente.replace("BR-", "").toLowerCase();
    }

    public void validarEmail() throws JSONException, InterruptedException {
        String messageId = getMessageFirstId();
        String endereco = montaEnderecoValidacaoEmail(DadosPessoaisPage.enderecoCliente,
                DadosPessoaisPage.numeroEndCliente,
                DadosPessoaisPage.complementoCliente,
                DadosPessoaisPage.bairroCliente,
                DadosPessoaisPage.cidadeCliente,
                DadosPessoaisPage.ufCliente,
                DadosPessoaisPage.cepCliente);

        String response = getPedidoEnderecoNome(messageId);
        Assert.assertTrue(response.contains(DadosPessoaisPage.nomeCliente));
        Assert.assertTrue(response.contains(ParabensPage.pedidoParabens));
        Assert.assertTrue(response.contains(endereco));
        purgeInbox(messageId);
    }
}