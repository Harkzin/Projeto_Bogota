package pages;

import support.DriverQA;
import org.junit.Assert;
import support.Hooks;

public class CarrinhoPage {
    private DriverQA driver;

    public CarrinhoPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    //Resumo da compra
    private String xpathTituloPlanoResumo = "(//*[@class='product-fullname isOrderConfPage mdn-Heading mdn-Heading--sm'])[2]";
    private String xpathGbNoPlanoResumo = (HomePage.gbBonusCardHome.isEmpty() && !HomePage.gbPlanoCardHome.isEmpty()) ? "(//*[@class='modality']//p)[2]" : "(//*[@class='modality']//p)[3]";
    private String xpathGbDeBonusResumo = "(//*[@class='modality']//p)[4]";
    private String xpathValorTotalResumo = "(//*[@class='js-entry-price-plan js-revenue'])";
    public static String xpathMetodoPagamentoResumo = "(//*[@class='mdn-Price-suffix'])[2]";
    public static String xpathMetodoPagamentoResumo2 = "(//*[@class='mdn-Price-suffix'])[2]";
    private String xpathFidelizadoResumo = "(//*[@class='mdn-Price-suffix hidden-xs hidden-sm'])[2]";

    //Form Aq/Port/MigTroc
    private String xpathInputRadioMigracao = "(//*[@value='MIGRATE'])";
    private String xpathInputRadioPortabilidade = "(//*[@value='PORTABILITY'])";
    private String xpathInputRadioAquisicao = "(//*[@value='NEWLINE'])";
    private String xpathMigracaoForm = "(//*[@data-automation='migracao'])[2]";
    private String xpathPortabilidadeForm = "(//*[@data-automation='portabilidade'])[2]";
    private String xpathAquisicaoForm = "(//*[@data-automation='aquisicao'])[2]";
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

    //Carrinho
    public static String xpathTermosComboMulti = "//p[@class='terms-and-conditions-page-description']";

    public void validarCarrinho() {
        driver.waitElementXP(xpathTituloPlanoResumo);
        Assert.assertTrue(HomePage.tituloCardHome.equals(driver.getText(xpathTituloPlanoResumo, "xpath")));
        if (!HomePage.gbBonusCardHome.isEmpty() && !HomePage.gbPlanoCardHome.isEmpty()) {
            Assert.assertTrue(HomePage.gbPlanoCardHome.equals(driver.getText(xpathGbNoPlanoResumo, "xpath")));
            Assert.assertTrue(HomePage.gbBonusCardHome.equals(driver.getText(xpathGbDeBonusResumo, "xpath")));
//        } else if (HomePage.gbBonusCardHome.isEmpty() && !HomePage.gbPlanoCardHome.isEmpty()) {
//            Assert.assertTrue(HomePage.gbPlanoCardHome.equals(driver.getText(xpathGbNoPlanoResumo, "xpath")));
        }
        Assert.assertTrue(HomePage.valorCardHome.contains(driver.getText(xpathValorTotalResumo, "xpath")));
        Assert.assertTrue(("Fidelizado por 12 meses".equals(driver.getText(xpathFidelizadoResumo, "xpath"))));
        Assert.assertTrue(("Débito automático".equals(driver.getText(xpathMetodoPagamentoResumo, "xpath"))));
    }

    public void preencherDadosLinhaForm(String ddd, String telefone, String email, String cpf) {
        // Valida se todos os campos de aquisicao, migracao e portabilidade estao visiveis e se sao selecionaveis
        driver.waitElementToBeClickableAll(xpathAquisicaoForm, 5, "xpath");
        Assert.assertTrue(driver.isDisplayed(xpathMigracaoForm, "xpath") && driver.isDisplayed(xpathPortabilidadeForm, "xpath") && driver.isDisplayed(xpathAquisicaoForm, "xpath"));
        Assert.assertFalse(driver.isSelected(xpathInputRadioMigracao, "xpath") && driver.isSelected(xpathInputRadioPortabilidade, "xpath") && driver.isSelected(xpathInputRadioAquisicao, "xpath"));

        telefoneCliente = telefone;
        cpfCliente = cpf;
        if (Hooks.tagScenarios.contains("@migracao") || Hooks.tagScenarios.contains("@troca")) {
            driver.click(xpathMigracaoForm, "xpath");
            driver.waitElementToBeClickableAll(idCpfMigracaoForm, 5, "id");
            driver.sendKeys(email, idEmailForm);
            driver.actionSendKey(telefone, idTelefoneMigracaoForm, "id");
            driver.actionSendKey(cpf, idCpfMigracaoForm, "id");
        } else if (Hooks.tagScenarios.contains("@aquisicao")) {
            driver.click(xpathAquisicaoForm, "xpath");
            driver.waitElementToBeClickableAll(idDDDAquisicaoForm, 5, "id");
            driver.actionSendKey(telefone, idTelefoneAquisicaoForm, "id");
            driver.sendKeys(email, idEmailForm, "id");
            driver.actionSendKey(cpf, idCpfAquisicaoForm, "id");
        } else {
            driver.click(xpathPortabilidadeForm, "xpath");
            driver.waitElementToBeClickableAll(idTelefonePortabilidadeForm, 1, "id");
            driver.sendKeys(telefone, idTelefonePortabilidadeForm, "id");
            driver.sendKeys(cpf, idCpfPortabilidadeForm, "id");
        }
    }

    public void euQueroCarrinho(String botao) {
        switch (botao) {
            case "Eu quero!":
                driver.JavaScriptClick(idEuQueroForm, "id");
                break;
            case "Continuar":
                driver.JavaScriptClick(DadosPessoaisPage.xpathBtnContinuar, "xpath");
                break;
            case "Continuar pagamento":
                driver.JavaScriptClick(DadosPessoaisPage.xpathBtnContinuarPagamento, "xpath");
                break;
            case "Eu quero! Controle Antecipado":
                driver.JavaScriptClick(ControleAntecipadoPage.xpathEuQueroTHAB, "xpath");
                break;
            case "Não concordo":
                driver.JavaScriptClick(CustomizarFaturaPage.xpathNaoConcordo, "xpath");
                break;
            case "Ok, entendi":
                driver.JavaScriptClick(CustomizarFaturaPage.xpathClicarOKEntendi, "xpath");
                break;
            case "Finalizar":
                driver.JavaScriptClick(TokenPage.xpathBotaoFinalizarCarrinho, "xpath");
            case "Entrar":
                driver.JavaScriptClick(HomePage.xpathEntrarBtn, "xpath");
                break;
            case "Acessar":
                driver.JavaScriptClick(HomePage.xpathAcessarBtn, "xpath");
                break;
            case "11947190768":
                driver.JavaScriptClick(HomePage.xpathOlaEcommerceBtn, "xpath");
                break;
            case "Meus pedidos":
                driver.JavaScriptClick(HomePage.xpathMeusPedidosBtn, "xpath");
                break;

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

    public void preencherDadosLinhaRent(String telefone, String email, String cpf) {
        driver.waitElementToBeClickableAll(xpathMigracaoForm, 5, "xpath");
        driver.click(xpathMigracaoForm, "xpath");
        driver.waitElementToBeClickableAll(idCpfMigracaoForm, 5, "id");
        driver.sendKeys(email, idEmailForm);
        driver.actionSendKey(telefone, idTelefoneMigracaoForm, "id");
        driver.actionSendKey(cpf, idCpfMigracaoForm, "id");
    }

    public void validarClienteCombo() {
        Assert.assertEquals("Como você já é combo, seu bônus está garantido!\n"+"Para sua comodidade, sua data de vencimento e forma de pagamento continuam a mesma.", driver.getText(CarrinhoPage.xpathTermosComboMulti, "xpath"));
    }
}
