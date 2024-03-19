package pages;

import support.DriverQA;
import org.junit.Assert;

import java.io.IOException;

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
    public static String telefoneCliente; //Refactor
    public static String cpfCliente; //Refactor

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

    public void selecionaFluxo(String fluxo) {
        switch (fluxo) {
            case "Migração":
                driverQA.JavaScriptClick(fluxoMigracao, "id");
                break;
            case "Portabilidade":
                driverQA.JavaScriptClick(fluxoPortabilidade, "id");
                break;
            case "Aquisição":
                driverQA.JavaScriptClick(fluxoAquisicao, "id");
        }
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
        String xpathMsgErroBloqueioDependente = "(//*[@id='cboxLoadedContent'])";

        //driverQA.waitElementVisibility(xpathMsgErroBloqueioDependente, "id");
        Assert.assertEquals(mensagem, driverQA.getText(xpathMsgErroBloqueioDependente, "xpath").substring(0, 106));
        Assert.assertEquals("Favor informar a linha titular.", driverQA.getText(xpathMsgErroBloqueioDependente, "xpath").substring(108, 139));
    }
}