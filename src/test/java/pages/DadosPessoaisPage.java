package pages;

import cucumber.api.Scenario;
import support.DriverQA;

public class DadosPessoaisPage {
    private Scenario cenario;
    private DriverQA driver;

    public DadosPessoaisPage(DriverQA driver) {
        this.driver = driver;
    }

    // Dados Pessoais
    private String idFormDadosPessoais = "addressForm.personalInformation";

    private String xpathTxtNome = "//input[@data-automation='nome-completo']";
    private String xpathTxtDtNascimento = "//input[@data-automation='nascimento']";
    private String xpathTxtNomeMae = "//input[@data-automation='nome-completo-mae']";
    private String xpathSubmitContinuarDados = "//input[@data-automation='continuar']";

    //Mensagens de Erro para Dados pessoais


//    public void secaoDadosPessoaisEExibida() {
//        Generico.printTelas(Hooks.report, "" + ThreadLocalStepDefinitionMatch.get().getStepName() + "", driver);
//
//        if (!driver.waitElementAllTimeOut(idFormDadosPessoais, "id", 25)) {
//            driver.report(cenario, false, "Página de pedido não apresentada", true);
//        }
//    }

    public void preencherNomeCompleto(String nomeCompleto) {
        if (driver.isDisplayed(xpathTxtNome, "xpath")) {
            driver.actionSendKey(nomeCompleto, xpathTxtNome, "xpath");
        }
    }

    public void preencherDataNascimento(String dataNascimento) {
        if (driver.isDisplayed(xpathTxtDtNascimento, "xpath")) {
            driver.actionSendKey(dataNascimento, xpathTxtDtNascimento, "xpath");
        }
    }

    public void preencherNomeDaMae(String nomeDaMae) {
        if (driver.isDisplayed(xpathTxtNomeMae, "xpath")) {
            driver.actionSendKey(nomeDaMae, xpathTxtNomeMae, "xpath");
        }
    }

//    public void clicarEmContinuarDadosPessoais() {
//        Generico.printTelas(Hooks.report, "" + ThreadLocalStepDefinitionMatch.get().getStepName() + "", driver);
//        driver.click(xpathSubmitContinuarDados, "xpath");
//    }
}
