package pages;

import support.DriverQA;
import org.junit.Assert;

public class CarrinhoPage {
    private DriverQA driver;

    public CarrinhoPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    //Resumo da compra
    private String xpathTituloPlanoResumo = "(//*[@class='product-fullname isNotOrderConfPage'])[2]";
    private String xpathGbNoPlanoResumo = "(//*[@class='modality']//p)[3]";
    private String xpathGbDeBonusResumo = "(//*[@class='modality']//p)[4]";
    private String xpathValorTotalResumo = "(//*[@class='js-entry-price-plan js-revenue'])[2]";
    public static String xpathMetodoPagamentoResumo = "(//*[@class='mdn-Price-suffix'])[2]";
    private String xpathFidelizadoResumo = "(//*[@class='mdn-Price-suffix hidden-xs hidden-sm'])[2]";

    //Form Aq/Port/MigTroc
    private String xpathInputRadioMigracao = "(//*[@value='MIGRATE'])";
    private String xpathInputRadioPortabilidade = "(//*[@value='PORTABILITY'])";
    private String xpathInputRadioAquisicao = "(//*[@value='NEWLINE'])";
    private String xpathMigracaoForm = "(//*[@data-automation='migracao'])";
    private String xpathPortabilidadeForm = "(//*[@data-automation='portabilidade'])";
    private String xpathAquisicaoForm = "(//*[@data-automation='aquisicao'])";
    private String idTelefoneMigracaoForm = "telephone";
    private String idEmailForm = "email";
    private String idCpfMigracaoForm = "cpf";
    private String idTelefonePortabilidadeForm = "telephonePortability";
    private String idCpfPortabilidadeForm = "cpfPortability";
    private String idDDDAquisicaoForm = "ddd";
    private String idTelefoneAquisicaoForm = "telephoneAcquisition";
    private String idCpfAquisicaoForm = "cpfAcquisition";
    private String idEuQueroForm = "buttonCheckout";

    // Variaveis para validacao na tela de parabens
    public static String telefoneCliente;
    public static String cpfCliente;

    // Mensagem erro Bloqueio Dependente
    private String xpathMsgErroBloqueioDependente = "(//*[@id='cboxLoadedContent'])";

    public void validarCarrinho() {
        driver.waitElementXP(xpathTituloPlanoResumo);
        Assert.assertTrue(HomePage.tituloCardHome.equals(driver.getText(xpathTituloPlanoResumo, "xpath")));
        Assert.assertTrue(HomePage.gbPlanoCardHome.equals(driver.getText(xpathGbNoPlanoResumo, "xpath")));
        Assert.assertTrue(HomePage.gbBonusCardHome.equals(driver.getText(xpathGbDeBonusResumo, "xpath")));
        Assert.assertTrue(HomePage.valorCardHome.contains(driver.getText(xpathValorTotalResumo, "xpath")));
        Assert.assertTrue(("Fidelizado por 12 meses".equals(driver.getText(xpathFidelizadoResumo, "xpath"))));
        Assert.assertTrue(("Débito automático".equals(driver.getText(xpathMetodoPagamentoResumo, "xpath"))));
    }

    public void selecionarOpcaoForm(String opcao) {
        driver.waitElementToBeClickableAll(xpathAquisicaoForm, 5, "xpath");
        Assert.assertTrue(driver.isDisplayed(xpathMigracaoForm, "xpath") && driver.isDisplayed(xpathPortabilidadeForm, "xpath") && driver.isDisplayed(xpathAquisicaoForm, "xpath"));
        Assert.assertFalse(driver.isSelected(xpathInputRadioMigracao, "xpath") && driver.isSelected(xpathInputRadioPortabilidade, "xpath") && driver.isSelected(xpathInputRadioAquisicao, "xpath"));
        switch (opcao) {
            case "Quero uma linha nova da Claro":
                driver.click(xpathAquisicaoForm, "xpath");
                break;
            case "Trazer meu número para Claro":
                driver.click(xpathPortabilidadeForm, "xpath");
                break;
            case "Mudar meu plano da Claro":
                driver.click(xpathMigracaoForm, "xpath");
                break;
        }
    }

    public void preencherDadosLinhaForm(String ddd, String telefone, String email, String cpf, String fluxo) {
        telefoneCliente = telefone;
        cpfCliente = cpf;
        switch (fluxo) {
            case "migracao":
                driver.waitElementToBeClickableAll(idCpfMigracaoForm, 5, "id");
                driver.sendKeys(email, idEmailForm);
                driver.actionSendKey(telefone, idTelefoneMigracaoForm, "id");
                driver.actionSendKey(cpf, idCpfMigracaoForm, "id");
                break;
            case "portabilidade":
                driver.waitElementToBeClickableAll(idTelefonePortabilidadeForm, 1, "id");
                driver.sendKeys(telefone, idTelefonePortabilidadeForm, "id");
                driver.sendKeys(cpf, idCpfPortabilidadeForm, "id");
                break;
            case "aquisicao":
                driver.waitElementToBeClickableAll(idDDDAquisicaoForm, 5, "id");
                driver.actionSendKey(telefone, idTelefoneAquisicaoForm, "id");
                driver.sendKeys(email, idEmailForm, "id");
                driver.actionSendKey(cpf, idCpfAquisicaoForm, "id");
        }
    }

    public void euQueroCarrinho(String botao) {
        switch (botao) {
            case "Eu quero!":
                driver.actionClick(idEuQueroForm, "id");
                break;
            case "Continuar":
                driver.actionClick(DadosPessoaisPage.xpathBtnContinuar, "xpath");
                break;
            case "Continuar pagamento":
                driver.actionClick(DadosPessoaisPage.xpathBtnContinuarPagamento, "xpath");
                break;
            case "Eu quero! Controle Antecipado":
                driver.actionClick(ControleAntecipadoPage.xpathEuQueroTHAB, "xpath");
                break;
            case "Não concordo":
                driver.actionClick(CustomizarFaturaPage.xpathNaoConcordo, "xpath");
                break;
            case "Ok, entendi":
                driver.actionClick(CustomizarFaturaPage.xpathClicarOKEntendi, "xpath");
                break;
            case "Finalizar":
                driver.actionClick(TokenPage.xpathBotaoFinalizarCarrinho, "xpath");
        }
    }

    public void validarMensagemBloqueioClienteDependente(String mensagem) {
        driver.waitElementXP(xpathMsgErroBloqueioDependente);
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(0, 106));
        Assert.assertEquals("Favor informar a linha titular.", driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(108, 139));
    }

    public void validarQueFoiDirecionadoParaAHome() {
        Assert.assertEquals("O básico para o dia a dia", driver.getText(HomePage.xpathTituloControleHome, "xpath"));
    }
}
