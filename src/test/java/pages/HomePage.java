package pages;

import support.DriverQA;
import support.Hooks;

public class HomePage {
    private final DriverQA driver;

    public HomePage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    //Overlay de abandono
    public static String fecharModalBtn = "(//i[@class='mdn-Icon-fechar mdn-Icon--md'])[1]";

    //Botão Entrar Home
    public static String EntrarBtn = "//button[@data-uid='my-orders']";
    public static String AcessarBtn = "(//button[@type='submit'])[1]";
    public static String OlaEcommerceBtn = "(//button[contains(text(),'11947190768')])[1]";
    public static String MeusPedidosBtn = "(//button[contains(text(),'Meus pedidos')])[1]";

    //     Refactor
//    public static String tituloControleHome = "//*[@class='mensagem-plano'][text()='O básico para o dia a dia']";
//    public static String tituloPosHome = "//*[@class='mensagem-plano'][text()='Para quem é ultra conectado']";
//
//    private String tituloCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//*[@class='titulo-produto'])" : "(//*/*[@class='titulo-produto'])";
//    private String precoCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//*[@class='price'])" : "(//*/*[@class='price'])";
//    private String gbPlano = (Hooks.tagScenarios.contains("@controle")) ? "(//*[contains(text(), 'no Plano')])" : "(//*/*[contains(text(), 'no Plano')])";
//    private String gbBonus = (Hooks.tagScenarios.contains("@controle")) ? "(//*[contains(text(), 'de Bônus')])" : "(//*/*[contains(text(), 'de Bônus')])";
    private String tituloCard = "msg-titulo-plano";
    private String precoCard = "msg-valor-plano";
    private String gbPlano = "";
    private String gbBonus = "";
    private String euQueroCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//button[@data-automation='eu-quero'])" : "(//*/button[@data-automation='eu-quero'])";
    private String maisDetalhesCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*[@data-automation='mais-detalhes'])" : "(//*/[@data-automation='mais-detalhes'])";

    private String proximoCarrossel = "btn-carrolsel-esquerda";

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
            case "S4":
                url = "https://accstorefront.cokecxf-commercec1-s4-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "S5":
                url = "https://accstorefront.cokecxf-commercec1-s5-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "S6":
                url = "https://accstorefront.cokecxf-commercec1-s6-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "D1":
                url = "https://accstorefront.cokecxf-commercec1-d1-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "D2":
                url = "https://accstorefront.cokecxf-commercec1-d2-public.model-t.cc.commerce.ondemand.com/";
                break;
            case "D3":
                url = "https://accstorefront.cokecxf-commercec1-d3-public.model-t.cc.commerce.ondemand.com/";
                break;
            default:
                throw new IllegalArgumentException("Ambiente inválido: " + url);
        }
        driver.openURL(url);
    }

    public void selecionarCardControle(String cardHome) {
        String TituloControleHome = "";
        driver.waitElement(TituloControleHome, "id");
        if (Integer.parseInt(cardHome) > 3) {
            driver.JavaScriptClick(proximoCarrossel, "id");
            driver.JavaScriptClick(proximoCarrossel, "id");
        }
        driver.waitElement(euQueroCard + "[" + cardHome + "]", "xpath");
        tituloCardHome = driver.getText(tituloCard + "[" + cardHome + "]", "id");
        valorCardHome = driver.getText(precoCard + "[" + cardHome + "]", "id");
        gbPlanoCardHome = (driver.findListElements(gbPlano + "[" + cardHome + "]", "id").isEmpty()) ? "" : driver.getText(gbPlano + "[" + cardHome + "]", "id");
        gbBonusCardHome = (driver.findListElements(gbBonus + "[" + cardHome + "]", "id").isEmpty()) ? "" : driver.getText(gbBonus + "[" + cardHome + "]", "id");
        driver.JavaScriptClick(euQueroCard + "[" + cardHome + "]", "xpath");
    }

    public void selecionarCardPos(String cardHome) {
        if (Integer.parseInt(cardHome) > 3) {
            driver.JavaScriptClick(proximoCarrossel, "id");
        }
        driver.waitElement(euQueroCard + "[" + cardHome + "]", "id");
        tituloCardHome = driver.getText(tituloCard + "[" + cardHome + "]", "id");
        valorCardHome = driver.getText(precoCard + "[" + cardHome + "]", "id");
        gbPlanoCardHome = (driver.findListElements(gbPlano + "[" + cardHome + "]", "id").isEmpty()) ? "" : driver.getText(gbPlano + "[" + cardHome + "]", "id");
        gbBonusCardHome = (driver.findListElements(gbBonus + "[" + cardHome + "]", "id").isEmpty()) ? "" : driver.getText(gbBonus + "[" + cardHome + "]", "id");
        driver.JavaScriptClick(euQueroCard + "[" + cardHome + "]", "id");
    }

    public void preencherCampoSeuTelefoneHeader(String msisdn) {
        driver.waitSeconds(8);
        driver.sendKeys(campoTelefone, "id", msisdn);
    }

    public void validarClienteMeusPedidos(String cliente) {
        String botaoOlaEcomm = "(//button[contains(text(), '" + cliente + "')])[1]";
        driver.findListElements(botaoOlaEcomm, "id");
    }

    public void acessarURLRentabilizacao() {
        driver.openURL("https://accstorefront.cokecxf-commercec1-" + System.getProperty("env", "S6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com/claro/pt/offer-plan/externalUri?offerPlanId=17218&coupon=09fd42fef86f8e0ea86d085f64a3696be6b4e91307c59913b172ddb5f60d0aaa&msisdn=msisdn&targetCampaign=migra&paymentMethod=debitcard&loyalty=true&invoiceType=DIGITAL&processType=MIGRATE");
    }

    public void clicarPLP() {
        if (Hooks.tagScenarios.contains("@controle")) {
            driver.JavaScriptClick(PLPControleHomeMenu, "xpath");
        } else if (Hooks.tagScenarios.contains("@pos")) {
            driver.JavaScriptClick(PLPPosHomeMenu, "xpath");
        }
    }

    // CONTINUAR PDP MAIS DETALHES
    public void selecionarPDPCard(String idPlano) {
        driver.waitElement("txt-mais-detalhes-" + idPlano + "", "id");
        driver.JavaScriptClick("txt-mais-detalhes-" + idPlano + "", "id");
    }

    public void selecionarCardHome(String idPlano) {
        driver.JavaScriptClick("btn-eu-quero-" + idPlano + "", "id");
    }
}