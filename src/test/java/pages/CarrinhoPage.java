package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import support.DriverQA;
import org.junit.Assert;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static support.RestAPI.checkCpfDiretrix;
import static support.RestAPI.getCpf;

public class CarrinhoPage {
    private final DriverQA driverQA;

    public CarrinhoPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private final String fluxoMigracao = "rdn-migracao";
    private final String fluxoPortabilidade = "rdn-portabilidade";
    private final String fluxoAquisicao = "rdn-aquisicao";

    private String getCpfForPlanFlow(boolean isApproved, boolean isDiretrix) throws IOException, InterruptedException {
        String cpf;
        String clearSaleRule = isApproved ? ".*[1348]$" : ".*5$"; //Regra do final do CPF da clearSale.

        do {
            do {
                cpf = getCpf();
            } while (!cpf.matches(clearSaleRule));
        } while (checkCpfDiretrix(cpf) != isDiretrix);

        return cpf;
    }

    public void validarPaginaCarrinho() {
        driverQA.waitPageLoad("/cart", 10);

        Assert.assertNotNull(driverQA.findElement(fluxoMigracao, "id"));
        Assert.assertNotNull(driverQA.findElement(fluxoPortabilidade, "id"));
        Assert.assertNotNull(driverQA.findElement(fluxoAquisicao, "id"));
    }

    public void selecionarFluxo(String fluxo) {
        switch (fluxo) {
            case "Migração/Troca":
                driverQA.JavaScriptClick(fluxoMigracao, "id");
                break;
            case "Portabilidade":
                driverQA.JavaScriptClick(fluxoPortabilidade, "id");
                break;
            case "Aquisição":
                driverQA.JavaScriptClick(fluxoAquisicao, "id");
        }
    }

    public void acessarUrlCarrinho(String url) {
        driverQA.getDriver().get(url);
    }

    public void inserirDadosBase(String telefone, String cpf) {
        String telefoneMigracao = "txt-telefone-migracao";
        String cpfMigracao = "txt-cpf-migracao";

        driverQA.actionSendKeys(telefoneMigracao, "id", telefone);
        driverQA.actionSendKeys(cpfMigracao, "id", cpf);
    }

    public void inserirDadosPortabilidade(String telefone, boolean cpfAprovado, boolean cpfDiretrix) throws IOException, InterruptedException {
        String telefonePortabilidade = "txt-telefone-portabilidade";
        String cpfPortabilidade = "txt-cpf-portabilidade";

        driverQA.actionSendKeys(telefonePortabilidade, "id", telefone);
        driverQA.actionSendKeys(cpfPortabilidade, "id", getCpfForPlanFlow(cpfAprovado, cpfDiretrix));
    }

    public void inserirDadosAquisicao(String telefoneContato, boolean cpfAprovado, boolean cpfDiretrix) throws IOException, InterruptedException {
        String telefoneContatoAquisicao = "txt-telefone-aquisicao";
        String cpfAquisicao = "txt-cpf-aquisicao";

        driverQA.actionSendKeys(telefoneContatoAquisicao, "id", telefoneContato);
        driverQA.actionSendKeys(cpfAquisicao, "id", getCpfForPlanFlow(cpfAprovado, cpfDiretrix));
    }

    public void inserirEmail(String email) {
        driverQA.actionSendKeys("txt-email", "id", email);
    }

    public void clicarEuQuero() {
        driverQA.JavaScriptClick("btn-eu-quero", "id");
    }

    public void validarMensagemBloqueioClienteDependente(String mensagem) {
        String msgErroBloqueioDependente = "cboxLoadedContent";
        Assert.assertEquals(mensagem, driverQA.getText(msgErroBloqueioDependente, "id").substring(0, 106));
        Assert.assertEquals("Favor informar a linha titular.", driverQA.getText(msgErroBloqueioDependente, "id").substring(108, 139));
    }

    public void direcionadoParaMulta() {
        WebElement tituloMulta = driverQA.findElement( "(//p[@class='mdn-Subtitle--md strong mdn-u-marginBottom--xs'])[1]", "xpath");
        String tituloTexto = tituloMulta.getText();

        String regex = "R\\$ \\d{1,3},\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tituloTexto);
        String valorMulta = "";
        if (matcher.find()) { valorMulta = matcher.group(0); }
        Assert.assertTrue(tituloTexto.contains("Identificamos que a alteração de plano gerará uma multa contratual no valor de " + valorMulta));

        WebElement subtituloElement = driverQA.findElement( "(//p[@class='mdn-Subtitle--xs'])[1]", "xpath");
        String subtituloTexto = subtituloElement.getText();
        Assert.assertEquals(subtituloTexto, "Caso queira seguir com a contratação deste plano, a multa será cobrada de uma única vez, em sua próxima fatura.");

    }
    }
