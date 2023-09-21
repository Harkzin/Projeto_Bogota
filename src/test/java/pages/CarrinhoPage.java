package pages;

import org.openqa.selenium.Keys;
import support.DriverQA;

import static pages.HomePage.*;

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
    private String xpathMetodoPagamentoResumo = "(//*[@class='mdn-Price-suffix'])[2]";
    private String xpathFidelizadoResumo = "(//*[@class='mdn-Price-suffix hidden-xs hidden-sm'])[2]";

    //Form Aq/Port/MigTroc
    private String xpathMigracaoForm = "(//*[@data-automation='migracao'])";
    private String xpathPortabilidadeForm = "(//*[@data-automation='portabilidade'])";
    private String xpathAquisicaoForm = "(//*[@data-automation='aquisicao'])";
    private String idTelefoneMigracaoForm = "telephone";
    private String idEmailForm = "email";
    private String idCpfMigracaoForm = "cpf";
    private String idTelefonePortabilidadeForm = "telephonePortability";
    private String idCpfPortabilidadeForm = "cpfPortability";
    private String idDDDAquisicaoForm = "dddAquisicao";
    private String idTelefoneAquisicaoForm = "telephoneAcquisition";
    private String idCpfAquisicaoForm = "cpfAcquisition";
    private String idEuQueroForm = "buttonCheckout";

    // Mensagem erro
    private String xpathMsgErroBloqueioDependente = "(//*[@id='cboxLoadedContent'])";

    // Dados Pessoais
    private String idFormDadosPessoais = "addressForm.personalInformation";
    private String xpathTxtNome = "//input[@data-automation='nome-completo']";
    private String xpathTxtDtNascimento = "//input[@data-automation='nascimento']";
    private String xpathTxtNomeMae = "//input[@data-automation='nome-completo-mae']";

    // Dados Endereco
    private String idTxtCep = "postcode_deliveryAddress";
    private String xpathTxtNumero = "//input[@data-automation='numero']";
    private String xpathTxtComplemento = "//input[@data-automation='complemento']";
    private String xpathBtnContinuar = "//button[@data-automation='continuar']";
    private String xpathBtnContinuarPagamento = "//button[@title='Continuar']";
    private String xpathEuQueroTHAB = "//a[@data-automation='eu-quero']";


    // Dados Pagamentos
    private String xpathContainerTermoDeAdesao = "//div[contains(@class, 'active')]//div[@class='mdn-Checkbox']";
    private String xpathAbaBoleto = "//li[@data-automation='aba-boleto']";
    private String idBanco = "faturaBancoSelect";
    private String idAgencia = "faturaAgenciaText";
    private String idConta = "faturaContaCorrenteText";
    private String xpathCCredito = "//*[@id='faturaPagamentoRecorrenteRadio']//label/p[text()='Não']";
    private String xpathChkTermosDeAdesao = "(//*[@class='mdn-Checkbox-label'])[2]";
    private String xpathChkTermosTHAB = "(//*[@class='mdn-Checkbox-label'])";

    // Token
    private String xpathInputToken = "//input[@data-automation='token']";

    private String xpathPrecoCarrinhoComparativo = "(//*[@id='hasPromotionalPricesMonetization'])[2]";

    // Clicar não concordo
    private String xpathNaoConcordo = "//*[@data-multa-action='goStep2']";

    //Clicar no Checkbox ok, entendi
    private String xpathClicarOKEntendi = "//*[@data-multa-action='backHome']";

    //Validar que foi direcionado para a Home
    private String xpathValidarQueFoiDirecionadoParaAHome = "/html/body";

    //Thab
    //private String xpathBotaoFinalizarCarrinho = TO DO

    public void validarCarrinho() {
        driver.waitElementXP(xpathTituloPlanoResumo);
        String titulo = driver.getText(xpathTituloPlanoResumo, "xpath");
        String gbPlano = driver.getText(xpathGbNoPlanoResumo, "xpath");
        String gbBonus = driver.getText(xpathGbDeBonusResumo, "xpath");
        String valor = driver.getText(xpathValorTotalResumo, "xpath");
        Assert.assertTrue((getTituloCardHome().equals(titulo)));
        Assert.assertTrue((getValorPlanoHome().contains(valor)));
        Assert.assertTrue((getGbPlanoCardHome().equals(gbPlano)));
        Assert.assertTrue((getGbBonusCardHome().equals(gbBonus)));

    }

    public void selecionarOpcaoForm(String opcao) {
        driver.waitElementToBeClickableAll(xpathAquisicaoForm, 5, "xpath");
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
        driver.waitElementToBeClickableAll(idCpfMigracaoForm, 5, "id");
        driver.sendKeys(email, idEmailForm);
        switch (fluxo) {
            case "migracao":
                driver.waitSeconds(1);
                driver.actionSendKey(telefone, idTelefoneMigracaoForm, "id");
                driver.actionSendKey(cpf, idCpfMigracaoForm, "id");
                break;
            case "portabilidade":
                driver.waitElementToBeClickableAll(idTelefonePortabilidadeForm, 1, "id");
                driver.sendKeys(telefone, idTelefonePortabilidadeForm, "id");
                driver.sendKeys(cpf, idCpfPortabilidadeForm, "id");
                break;
            case "aquisicao":
                driver.waitElementToBeClickableAll(idDDDAquisicaoForm, 1, "id");
                driver.clear(idDDDAquisicaoForm, "id");
                driver.sendKeys(ddd, idDDDAquisicaoForm, "id");
                driver.sendKeys(telefone, idTelefoneAquisicaoForm, "id");
                driver.sendKeys(cpf, idCpfAquisicaoForm, "id");
        }
    }

    public void euQueroCarrinho(String botao) {
        switch (botao) {
            case "Eu quero!":
                driver.actionClick(idEuQueroForm, "id");
                break;
            case "Continuar":
                driver.actionClick(xpathBtnContinuar, "xpath");
                break;
            case "Continuar pagamento":
                driver.actionClick(xpathBtnContinuarPagamento, "xpath");
                break;
            case "Eu quero! Controle Antecipado":
                driver.actionClick(xpathEuQueroTHAB, "xpath");
                break;
            case "Não concordo":
                driver.actionClick(xpathNaoConcordo, "xpath");
                break;
            case "Ok, entendi":
                driver.actionClick(xpathClicarOKEntendi, "xpath");
                break;
            case "Finalizar":
//                driver.actionClick(xpathBotaoFinalizarCarrinho);

        }
    }

    public void validarMensagemBloqueioClienteDependente(String mensagem) {
        driver.waitElementXP(xpathMsgErroBloqueioDependente);
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(0, 106));
        Assert.assertEquals("Favor informar a linha titular.", driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(108, 139));

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
      //To do validar qualquer elemento da home
            //Exemplo: Assert.assertEquals(texto qualquer da pagina inicial, driver.getText("seletor'", "tipo do seletor"));

        }
    }
