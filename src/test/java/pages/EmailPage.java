package pages;

import org.json.JSONException;
import org.junit.Assert;
import support.DriverQA;
import support.RESTSupport;

public class EmailPage {

    private DriverQA driver;

    public EmailPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public void validarEmail() throws JSONException, InterruptedException {
        String messageId = RESTSupport.getMessageFirstId();
        String endereco = driver.montaEnderecoValidacaoEmail(DadosPessoaisPage.enderecoCliente,
                DadosPessoaisPage.numeroEndCliente,
                DadosPessoaisPage.complementoCliente,
                DadosPessoaisPage.bairroCliente,
                DadosPessoaisPage.cidadeCliente,
                DadosPessoaisPage.ufCliente,
                DadosPessoaisPage.cepCliente);

        String response = RESTSupport.getPedidoEnderecoNome(messageId);
        Assert.assertTrue(response.contains(DadosPessoaisPage.nomeCliente));
        Assert.assertTrue(response.contains(ParabensPage.pedidoParabens));
        Assert.assertTrue(response.contains(endereco));
        RESTSupport.purgeInbox(messageId);

    }

}
