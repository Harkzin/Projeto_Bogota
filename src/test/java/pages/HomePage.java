package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import support.DriverQA;
import support.Hooks;

import static pages.BackofficePage.*;

public class HomePage {
    private DriverQA driver;

    public HomePage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    //Overlay de abandono
    public static String fecharModalBtn = "(//i[@class='mdn-Icon-fechar mdn-Icon--md'])[1]";

    //Botão Entrar Home
    public static String xpathEntrarBtn = "//button[@data-uid='my-orders']";
    public static String xpathAcessarBtn = "(//button[@type='submit'])[1]";
    public static String xpathOlaEcommerceBtn = "(//button[contains(text(),'11947190768')])[1]";
    public static String xpathMeusPedidosBtn = "(//button[contains(text(),'Meus pedidos')])[1]";


    // Card
    public static String xpathTituloControleHome = "//*[@class='mensagem-plano'][text()='O básico para o dia a dia']";
    public static String xpathTituloPosHome = "//*[@class='mensagem-plano'][text()='Para quem é ultra conectado']";
    private String xpathTituloCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//*[@class='titulo-produto'])" : "(//*/*[@class='titulo-produto'])";
    private String xpathPrecoCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//*[@class='price'])" : "(//*/*[@class='price'])";

    private String xpathGbPlano = (Hooks.tagScenarios.contains("@controle")) ? "(//*[contains(text(), 'no Plano')])" : "(//*/*[contains(text(), 'no Plano')])";
    private String xpathGbBonus = (Hooks.tagScenarios.contains("@controle")) ? "(//*[contains(text(), 'de Bônus')])" : "(//*/*[contains(text(), 'de Bônus')])";
    private String xpathEuQueroCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//button[@data-automation='eu-quero'])" : "(//*/button[@data-automation='eu-quero'])";
    private String xpathMaisDetalhesCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*[@data-automation='mais-detalhes'])" : "(//*/[@data-automation='mais-detalhes'])";

    private String xpathProximoCarrossel = (Hooks.tagScenarios.contains("@controle")) ? "//*[@data-automation='seta-carrossel-direita']" : "//*/*[@data-automation='seta-carrossel-direita']";

    // Variaveis para validacao posterior no carrinho Controle
    public static String tituloCardHome = "";
    public static String gbPlanoCardHome = "";
    public static String gbBonusCardHome = "";
    public static String valorCardHome = "";

    // Menu Header
    public static String campoTelefone = "(//input[@name='telephone'])[1]";

    public static String PLPControleHomeMenu = "//a[@title='Controle']";
    public static String PLPPosHomeMenu = "//a[@title='Pós']";


    public void acessarLojaHome() {

        String url = System.getProperty("env", "S6");
        switch (url) {
            case "S1":
                url = "https://accstorefront.cokecxf-commercec1-s1-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "S2":
                url = "https://accstorefront.cokecxf-commercec1-s2-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "S3":
                url = "https://accstorefront.cokecxf-commercec1-s3-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "S4":
                url = "https://accstorefront.cokecxf-commercec1-s4-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "S5":
                url = "https://accstorefront.cokecxf-commercec1-s5-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "S6":
                url = "https://accstorefront.cokecxf-commercec1-s6-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "P1":
                url = "https://accstorefront.cokecxf-commercec1-p1-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "STG":
                url = "https://planoscelular-stg.claro.com.br/claro/pt/Cat%C3%A1logo-de-Planos/Confira-nossos-Planos/";
                break;
            case "D1":
                url = "https://accstorefront.cokecxf-commercec1-d1-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "D2":
                url = "https://accstorefront.cokecxf-commercec1-d2-public.model-t.cc.commerce.ondemand.com/";
                break;
            default:
                throw new IllegalArgumentException("Ambiente inválido: " + url);
        }
        driver.openURL(url);
    }

    public void selecionarCardControle(String cardHome) {
        driver.waitElementAll(xpathTituloControleHome, "xpath");
        if (Integer.parseInt(cardHome) > 3) {
            driver.JavaScriptClick(xpathProximoCarrossel, "xpath");
            driver.JavaScriptClick(xpathProximoCarrossel, "xpath");
        }
        driver.waitElementAll(xpathEuQueroCard + "[" + cardHome + "]", "xpath");
        tituloCardHome = driver.getText(xpathTituloCard + "[" + cardHome + "]", "xpath");
        valorCardHome = driver.getText(xpathPrecoCard + "[" + cardHome + "]", "xpath");
        gbPlanoCardHome = (driver.findListElements(xpathGbPlano + "[" + cardHome + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbPlano + "[" + cardHome + "]", "xpath");
        gbBonusCardHome = (driver.findListElements(xpathGbBonus + "[" + cardHome + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbBonus + "[" + cardHome + "]", "xpath");
        driver.JavaScriptClick(xpathEuQueroCard + "[" + cardHome + "]", "xpath");
    }

    public void selecionarCardPos(String cardHome) {
        driver.waitElementAll(xpathTituloPosHome, "xpath");
        if (Integer.parseInt(cardHome) > 3) {
            driver.JavaScriptClick(xpathProximoCarrossel, "xpath");
        }
        driver.waitElementAll(xpathEuQueroCard + "[" + cardHome + "]", "xpath");
        tituloCardHome = driver.getText(xpathTituloCard + "[" + cardHome + "]", "xpath");
        valorCardHome = driver.getText(xpathPrecoCard + "[" + cardHome + "]", "xpath");
        gbPlanoCardHome = (driver.findListElements(xpathGbPlano + "[" + cardHome + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbPlano + "[" + cardHome + "]", "xpath");
        gbBonusCardHome = (driver.findListElements(xpathGbBonus + "[" + cardHome + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbBonus + "[" + cardHome + "]", "xpath");
        driver.JavaScriptClick(xpathEuQueroCard + "[" + cardHome + "]", "xpath");
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        driver.waitSeconds(8);
        driver.actionSendKey(msisdn, campoTelefone, "xpath");
    }

    public void validarClienteMeusPedidos(String cliente) {
        String botaoOlaEcomm = "(//button[contains(text(), '" + cliente + "')])[1]";
        driver.findListElements(botaoOlaEcomm, "xpath");
    }

    public void acessarURLRentabilizacao() {
        driver.openURL("https://accstorefront.cokecxf-commercec1-" + System.getProperty("env", "S6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com/claro/pt/offer-plan/externalUri?offerPlanId=17218&coupon=09fd42fef86f8e0ea86d085f64a3696be6b4e91307c59913b172ddb5f60d0aaa&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE");
    }

    public void tokenTemp() {
        driver.createNewTab();
        driver.changeTab("1");
        driver.openURL("https://backoffice.cokecxf-commercec1-s6-public.model-t.cc.commerce.ondemand.com/backoffice/login.zul");
        driver.waitSeconds(100);
        driver.waitElementAll(nameTxtUsuario, "name");
        String acessoBackoffice = "ecomplanos-backoffice";
        driver.sendKeys(acessoBackoffice, nameTxtUsuario, "name");
        driver.waitElementAll(nameTxtSenha, "name");
        driver.sendKeys(acessoBackoffice, nameTxtSenha, "name");
        driver.waitElementAll(xpathBtnIdioma, "xpath");
        driver.click(xpathBtnIdioma, "xpath");
        driver.waitElementAll(xpathIdioma, "xpath");
        driver.click(xpathIdioma, "xpath");
        driver.waitElementAll(xpathBtnIdioma, "xpath");
        driver.sendKeyBoard(Keys.ENTER);

        driver.waitElementAll(classMenu, "class");
        driver.waitSeconds(1);
        driver.sendKeysCampoMascara("clientes", filtroInputXpath, "xpath");

        driver.waitSeconds(5);
        String filtroXpath = "//tr[@aria-label='Clientes']";
        driver.moveToElementJs(filtroXpath, "xpath");
        driver.click(filtroXpath, "xpath");

        driver.waitSeconds(5);
        String xpathPesquisar = "//input[@placeholder='Digitar para pesquisar']";
        driver.sendKeysCampoMascara("86447822824", xpathPesquisar, "xpath");
        driver.sendKeyBoard(Keys.ENTER);

        String xpathNomeCliente = "//span[contains(text(), 'ECOMMERCE CONTROLE CLUBE')]";
        driver.click(xpathNomeCliente, "xpath");
        driver.waitSeconds(3);

        driver.waitSeconds(3);
        String xpathMenuCliente = "//li[@title='Administração']";
        while (!driver.isDisplayed(xpathMenuCliente, "xpath")) {
            driver.click(xpathMenuCliente, "xpath");
            driver.waitSeconds(1);
        }
        driver.waitElementAll(xpathMenuCliente, "xpath");
        driver.click(xpathMenuCliente, "xpath");
        driver.waitSeconds(2);

        driver.actionClick("(//button[@title='Retrair'])[1]", "xpath");
        driver.browserScroll("down",800 );
        driver.moveToElementAction("(//input[@class='ye-input-text ye-com_hybris_cockpitng_editor_defaulttext z-textbox'])[22]", "xpath");
        WebElement campoToken = driver.getDriver().findElement(By.xpath("(//input[@class='ye-input-text ye-com_hybris_cockpitng_editor_defaulttext z-textbox'])[22]"));
        String token = campoToken.getAttribute("value");

        driver.changeTab("0");
        driver.browserScroll("up", 800);
        WebElement campoTokenDestino = driver.getDriver().findElement(By.xpath("(//input[@name='token'])[1]"));
        campoTokenDestino.sendKeys(token);
        driver.JavaScriptClick("(//button[@type='submit'])[4]", "xpath");
    }

    public void clicarPLP() {
        if (Hooks.tagScenarios.contains("@controle")) {
            driver.JavaScriptClick(PLPControleHomeMenu, "xpath");
        } else if (Hooks.tagScenarios.contains("@pos")) {
            driver.JavaScriptClick(PLPPosHomeMenu, "xpath");
        }
    }
// CONTINUAR PDP MAIS DETALHES
    public void selecionarPDPCard(String cardPDP) {
        driver.waitElementAll(xpathTituloControleHome, "xpath");
        if (Integer.parseInt(cardPDP) > 3) {
            driver.JavaScriptClick(xpathProximoCarrossel, "xpath");
            driver.JavaScriptClick(xpathProximoCarrossel, "xpath");
        }
        driver.waitElementAll(xpathEuQueroCard + "[" + cardPDP + "]", "xpath");
        tituloCardHome = driver.getText(xpathTituloCard + "[" + cardPDP + "]", "xpath");
        valorCardHome = driver.getText(xpathPrecoCard + "[" + cardPDP + "]", "xpath");
        gbPlanoCardHome = (driver.findListElements(xpathGbPlano + "[" + cardPDP + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbPlano + "[" + cardPDP + "]", "xpath");
        gbBonusCardHome = (driver.findListElements(xpathGbBonus + "[" + cardPDP + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbBonus + "[" + cardPDP + "]", "xpath");
        driver.JavaScriptClick(xpathMaisDetalhesCard + "[" + cardPDP + "]", "xpath");
    }
}
