package pages;

import io.cucumber.java.pt.Então;
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
    }
    public void direcionadoParaTHAB() {
        driverQA.waitPageLoad("claro/pt/cart?THAB=true", 10);

        Assert.assertNotNull(driverQA.findElement("controle-antecipado", "id"));
    }

    public void clicarEuQueroTHAB() {
        driverQA.JavaScriptClick("buttonCheckoutThab", "id");
    }
}
