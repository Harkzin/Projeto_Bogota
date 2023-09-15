package pages;

import support.DriverQA;

public class HomePage {
    private DriverQA driver;

    public HomePage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    // Card Controle
    private String xpathTituloCardControle = "(//*[@class='titulo-produto'])[1]";
    private String xpathPrecoCardControle = "(//*[@class='price'])[1]";
    private String xpathGbPlano = "(//*[@class='portabilidade'])[1]";
    private String xpathGbBonus = "(//*[@class='portabilidade'])[2]";
    private String xpathEuQueroCardControle = "(//*[@data-automation='eu-quero'])[1]";

    // Variaveis para validacao
    private static String tituloCardHome = "";
    private static String gbPlanoCardHome = "";
    private static String gbBonusCardHome = "";
    private static String valorCardHome = "";

    public void acessarLojaHome() {

        String url = System.getProperty("env", "S5");
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

    public void selecionarCardControle() {
        driver.waitSeconds(2);
        driver.moveToElement(xpathEuQueroCardControle, "xpath");
        driver.waitSeconds(1);
        tituloCardHome = driver.getText(xpathTituloCardControle, "xpath");
        gbPlanoCardHome = driver.getText(xpathGbPlano, "xpath");
        gbBonusCardHome = driver.getText(xpathGbBonus, "xpath");
        valorCardHome = driver.getText(xpathPrecoCardControle, "xpath");
        driver.click(xpathEuQueroCardControle, "xpath");
    }

    public static String getValorPlanoHome() {
        return valorCardHome;
    }

    public static String getTituloCardHome() {
        return tituloCardHome;
    }

    public static String getGbPlanoCardHome() {
        return gbPlanoCardHome;
    }

    public static String getGbBonusCardHome() {
        return gbBonusCardHome;
    }

}

