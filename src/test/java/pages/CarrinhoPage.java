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
    private String xpathTituloPlanoResumo = "(//*[@class='product-fullname isNotOrderConfPage'])[2]";
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

    // Token
    private String xpathInputToken = "//input[@data-automation='token']";

    private String xpathPrecoCarrinhoComparativo = "(//*[@id='hasPromotionalPricesMonetization'])[2]";

    // Clicar não concordo
    private String xpathNaoConcordo = "//*[@data-multa-action='goStep2']";

    //Clicar no Checkbox ok, entendi
    private String xpathClicarOKEntendi = "//*[@data-multa-action='backHome']";

    //Validar que foi direcionado para a Home
    private String idValidarQueFoiDirecionadoParaAHome = "//h3[@class='mensagem-plano'][text()='O básico para o dia a dia']  ";
    //Thab
    //private String xpathBotaoFinalizarCarrinho = TO DO

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
        }
    }

    public void validarMensagemBloqueioClienteDependente(String mensagem) {
        driver.waitElementXP(xpathMsgErroBloqueioDependente);
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(0, 106));
        Assert.assertEquals("Favor informar a linha titular.", driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(108, 139));
    }

    public void validarMensagemBloqueiocep(String mensagem) {
        driver.waitElementXP(xpathMsgErroCEP);
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroCEP, "xpath"));
//        Assert.assertEquals("O CEP deve ser do mesmo estado (UF) do DDD escolhido", driver.getText(xpathMsgErroCEP, "xpath").substring(108, 139));

    }

    public void paginaDadosPessoaisEExibida() {
        driver.waitElementAll(idFormDadosPessoais, "id");
    }

    public void paginaDadosEnderecoEExibida() {
    }

    public void paginaDadosPagamentoEExibida() {
        driver.waitElementAll(xpathContainerTermoDeAdesao, "xpath");
    }

    public void camposDadosPessoais(String nomeCompleto, String dataNascimento, String nomeDaMae) {
        driver.sendKeys(nomeCompleto, xpathTxtNome, "xpath");
        driver.sendKeysCampoMascara(dataNascimento, xpathTxtDtNascimento, "xpath");
        driver.sendKeys(nomeDaMae, xpathTxtNomeMae, "xpath");
    }

    public void camposEndereco(String cep, String numero, String complemento) {
        driver.waitSeconds(1);
        driver.sendKeysCampoMascara(cep, idTxtCep, "id");
        driver.sendTab(2, "");
        driver.waitSeconds(5);
        driver.sendKeys(numero, xpathTxtNumero, "xpath");
        driver.waitSeconds(1);
        driver.sendKeys(complemento, xpathTxtComplemento, "xpath");
    }

    public void clicarFormaDePagamento(String formaPagamento) {
        //formaPagamento => Boleto || Debito
        Assert.assertTrue(Float.parseFloat(driver.getValueParam(xpathPrecoCarrinhoComparativo, "data-parsed-formatted-price-old", "xpath")) > Float.parseFloat(driver.getValueParam(xpathPrecoCarrinhoComparativo, "data-full-price", "xpath")));
        System.out.println("Forma Pagamento: " + formaPagamento);
        if (formaPagamento.equals("Boleto")) {
            driver.waitSeconds(5);
            driver.click(xpathAbaBoleto, "xpath");
        } else {
            driver.sendKeys("237 - BRADESCO", idBanco, "id");
            driver.waitSeconds(1);
            driver.sendKeyBoard(Keys.ENTER);
            driver.waitSeconds(1);
            driver.sendKeys("6620", idAgencia, "id");
            driver.sendKeys("11868576", idConta, "id");
        }
    }

    public void selecionarDataVencimento(String data) {
        driver.waitSeconds(5);
        driver.click("//label[@data-automation='vencimento-" + data + "']", "xpath");
    }

    public void marcarCheckboxTermo() {
        driver.waitSeconds(10);
        driver.moveToElement(xpathChkTermosDeAdesao, "xpath");
        driver.waitSeconds(10);
        driver.actionClick(xpathChkTermosDeAdesao, "xpath");

    }

    public void marcarCheckboxTermoTHAB() {
        driver.waitSeconds(10);
        driver.moveToElement(xpathChkTermosTHAB, "xpath");
        driver.waitSeconds(10);
        driver.actionClick(xpathChkTermosTHAB, "xpath");
    }

    public void selecionarTipoFatura(String fatura) {
        driver.waitSeconds(1);
        driver.click("div[class$=active] .tipoFatura label[for^='" + fatura + "']", "css");
    }

    public void paginaControleAntecipadoEExibida() {
        driver.waitElementAll("controle-antecipado", "id");
    }

    public boolean PlanoControleAntecipadoExiste() {
        driver.waitElementAll(".card-planos", "css");
        return true;
    }

    public void paginaCustomizarFaturaTHABEExibida() {
        driver.waitElementAll(".showCards", "css");
    }

    public void secaoTokenEExibida() {
        driver.waitElementAll(xpathInputToken, "xpath");

    }
        public void validarQueFoiDirecionadoParaAHome() {
            driver.waitSeconds(10);
            Assert.assertEquals("O básico para o dia a dia", driver.getText(idValidarQueFoiDirecionadoParaAHome, "xpath"));

        }
    }
}
