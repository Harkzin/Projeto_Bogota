package pages;

import support.DriverQA;
import support.Hooks;

public class PLPPage {
    private final DriverQA driver;

    public PLPPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    // Card
    public static String xpathTituloControlePLP = "//*[@class='mdn-Heading mdn-Heading--lg'][text()='O plano básico para o dia a dia!']";
    public static String xpathTituloPosHome = "//*[@class='mensagem-plano'][text()='Para quem é ultra conectado']";
    private String xpathTituloCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//*[@class='titulo-produto'])" : "(//*/*[@class='titulo-produto'])";
    private String xpathPrecoCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//*[@class='price'])" : "(//*/*[@class='price'])";

    private String xpathGbPlano = (Hooks.tagScenarios.contains("@controle")) ? "(//*[contains(text(), 'no Plano')])" : "(//*/*[contains(text(), 'no Plano')])";
    private String xpathGbBonus = (Hooks.tagScenarios.contains("@controle")) ? "(//*[contains(text(), 'de Bônus')])" : "(//*/*[contains(text(), 'de Bônus')])";
    private String xpathEuQueroCard = (Hooks.tagScenarios.contains("@controle")) ? "(//*//button[@data-automation='eu-quero'])" : "(//*/button[@data-automation='eu-quero'])";

    private String xpathProximoCarrossel = (Hooks.tagScenarios.contains("@controle")) ? "//*[@data-automation='seta-carrossel-direita']" : "//*/*[@data-automation='seta-carrossel-direita']";

    // Variaveis para validacao posterior no carrinho Controle
    public static String titulocardPLP = "";
    public static String gbPlanocardPLP = "";
    public static String gbBonuscardPLP = "";
    public static String valorcardPLP = "";

    public void selecionarCardControle(String cardPLP) {

        driver.waitElement(xpathTituloControlePLP, "xpath");
        if (Integer.parseInt(cardPLP) > 3) {
            driver.JavaScriptClick(xpathProximoCarrossel, "xpath");
            driver.JavaScriptClick(xpathProximoCarrossel, "xpath");
        }
        driver.waitElement(xpathEuQueroCard + "[" + cardPLP + "]", "xpath");
        titulocardPLP = driver.getText(xpathTituloCard + "[" + cardPLP + "]", "xpath");
        valorcardPLP = driver.getText(xpathPrecoCard + "[" + cardPLP + "]", "xpath");
        gbPlanocardPLP = (driver.findListElements(xpathGbPlano + "[" + cardPLP + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbPlano + "[" + cardPLP + "]", "xpath");
        gbBonuscardPLP = (driver.findListElements(xpathGbBonus + "[" + cardPLP + "]", "xpath").isEmpty()) ? "" : driver.getText(xpathGbBonus + "[" + cardPLP + "]", "xpath");
        driver.JavaScriptClick(xpathEuQueroCard + "[" + cardPLP + "]", "xpath");
    }
}
