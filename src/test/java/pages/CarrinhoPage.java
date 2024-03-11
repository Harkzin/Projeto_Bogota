package pages;

import support.DriverQA;
import org.junit.Assert;

import java.io.IOException;

import static support.RestAPI.checkCpfDiretrix;
import static support.RestAPI.getCpf;

public class CarrinhoPage {
    private final DriverQA driver;

    public CarrinhoPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    // Refactor
    //private final String GbNoPlanoResumo = (HomePage.gbBonusCardHome.isEmpty() && !HomePage.gbPlanoCardHome.isEmpty()) ? "(//*[@class='modality']//p)[2]" : "(//*[@class='modality']//p)[3]";
    //public static String MetodoPagamentoResumo = "(//*[@class='mdn-Price-suffix'])[2]";
    //public static String MetodoPagamentoResumo2 = "(//*[@class='mdn-Price-suffix'])[2]";
    //private static final String TermosComboMulti = "//p[@class='terms-and-conditions-page-description']";
    private final String MigrateFlow = "rdn-migracao";
    private final String PortabilityFlow = "rdn-portabilidade";
    private final String AcquisitionFlow = "rdn-aquisicao";
    private final String emailCart = "txt-email";

    // Variaveis para validacao na tela de parabens
    public static String telefoneCliente;
    public static String cpfCliente;

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

    public void validarCarrinho() {
        driver.waitElementToBeClickableAll(AcquisitionFlow, 5, "id");
        Assert.assertTrue(driver.isDisplayed(MigrateFlow, "id") && driver.isDisplayed(PortabilityFlow, "id") && driver.isDisplayed(AcquisitionFlow, "id"));

        /* //Refactor
        String TituloPlanoResumo = "(//*[@class='product-fullname isOrderConfPage mdn-Heading mdn-Heading--sm'])[2]";
        String ValorTotalResumo = "(//*[@class='js-entry-price-plan js-revenue'])";
        String FidelizadoResumo = "(//*[@class='mdn-Price-suffix hidden-xs hidden-sm'])[2]";

        driver.waitElementXP(TituloPlanoResumo);
        Assert.assertEquals(HomePage.tituloCardHome, driver.getText(TituloPlanoResumo, "xpath"));
        if (!HomePage.gbBonusCardHome.isEmpty() && !HomePage.gbPlanoCardHome.isEmpty()) {
            Assert.assertEquals(HomePage.gbPlanoCardHome, driver.getText(GbNoPlanoResumo, "xpath"));
            String xpathGbDeBonusResumo = "(//*[@class='modality']//p)[4]";
            Assert.assertEquals(HomePage.gbBonusCardHome, driver.getText(xpathGbDeBonusResumo, "xpath"));
            //} else if (HomePage.gbBonusCardHome.isEmpty() && !HomePage.gbPlanoCardHome.isEmpty()) {
            //Assert.assertTrue(HomePage.gbPlanoCardHome.equals(driver.getText(xpathGbNoPlanoResumo, "xpath")));
        }

        Assert.assertTrue(HomePage.valorCardHome.contains(driver.getText(ValorTotalResumo, "xpath")));
        Assert.assertEquals("Fidelizado por 12 meses", driver.getText(FidelizadoResumo, "xpath"));
        Assert.assertEquals("Débito automático", driver.getText(MetodoPagamentoResumo, "xpath"));
        */
    }

    public void inserirDadosBase(String telephone, String email, String cpf) {
        String telephoneMigrate = "txt-telefone-migracao";
        String cpfMigrate = "txt-cpf-migracao";

        driver.click(MigrateFlow, "id");
        driver.waitElementToBeClickableAll(cpfMigrate, 2, "id");

        driver.actionSendKey(telephone, telephoneMigrate, "id");
        driver.actionSendKey(emailCart, email, "id");
        driver.actionSendKey(cpf, cpfMigrate, "id");
    }

    public void inserirDadosPortabilidade(String telephoneContact, String email, boolean isCpfApproved, boolean isCpfDiretrix) throws IOException, InterruptedException {
        String telephonePortability = "txt-telefone-portabilidade";
        String cpfPortability = "txt-cpf-portabilidade";

        driver.click(PortabilityFlow, "id");
        driver.waitElementToBeClickableAll(telephonePortability, 2, "id");

        driver.sendKeys(telephoneContact, telephonePortability, "id");
        driver.sendKeys(emailCart, email, "id");
        driver.sendKeys(getCpfForPlanFlow(isCpfApproved, isCpfDiretrix), cpfPortability, "id");
    }

    public void inserirDadosAquisicao(String telephoneContact, String email, boolean isCpfApproved, boolean isCpfDiretrix) throws IOException, InterruptedException {
        String telephoneContactAcquisition = "txt-telefone-aquisicao";
        String cpfAcquisition = "txt-cpf-aquisicao";

        driver.click(AcquisitionFlow, "id");
        driver.waitElementToBeClickableAll(telephoneContactAcquisition, 2, "id");

        driver.actionSendKey(telephoneContact, telephoneContactAcquisition, "id");
        driver.sendKeys(emailCart, email, "id");
        driver.actionSendKey(getCpfForPlanFlow(isCpfApproved, isCpfDiretrix), cpfAcquisition, "id");
    }

    public void euQueroCarrinho(String botao) {
        String idEuQueroForm = "buttonCheckout";

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
        // Mensagem erro Bloqueio Dependente
        String xpathMsgErroBloqueioDependente = "(//*[@id='cboxLoadedContent'])";

        driver.waitElementXP(xpathMsgErroBloqueioDependente);
        Assert.assertEquals(mensagem, driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(0, 106));
        Assert.assertEquals("Favor informar a linha titular.", driver.getText(xpathMsgErroBloqueioDependente, "xpath").substring(108, 139));
    }

    public void validarQueFoiDirecionadoParaAHome() {
        Assert.assertEquals("O básico para o dia a dia", driver.getText(HomePage.xpathTituloControleHome, "xpath"));
    }

    /*public void validarClienteCombo() {
        Assert.assertEquals("Como você já é combo, seu bônus está garantido!\n" + "Para sua comodidade, sua data de vencimento e forma de pagamento continuam a mesma.", driver.getText(CarrinhoPage.TermosComboMulti, "xpath"));
    }*/
}