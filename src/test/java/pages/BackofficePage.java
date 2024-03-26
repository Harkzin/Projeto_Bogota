package pages;

import org.openqa.selenium.Keys;
import support.BaseSteps;
import support.DriverQA;

public class BackofficePage extends BaseSteps {
    private final DriverQA driver;

    private static final String BASE_URL = "https://backoffice.cokecxf-commercec1-";
    private static final String LOGIN_URL_SUFFIX = "-public.model-t.cc.commerce.ondemand.com/backoffice/login.zul";

    public static final String nameTxtUsuario = "j_username";
    public static final String nameTxtSenha = "j_password";
    public static final String xpathBtnLogin = "//button[@class='Conectar']";
    public static final String xpathBtnIdioma = "//button[@class='languageSelectorBtn z-button']";
    public static final String xpathIdioma = "(//div[@class='z-listcell-content'])[2]";
    public static final String classMenu = "yw-navigationNode-level1";
    public static final String filtroInputXpath = "//input[@placeholder='Filtrar árvore (Alt+Baixo para opções)']";

    public BackofficePage(DriverQA driver) {
        this.driver = driver;
    }

    public void acessarTelaBKO() {
        String environment = System.getProperty("env", "S6");
        String url = BASE_URL + environment + LOGIN_URL_SUFFIX;
        driver.getDriver().get(url);
        //driver.waitElementVisibility(nameTxtUsuario, "name");
        driver.waitSeconds(10);
        String acessoBackoffice = "ecomplanos-backoffice";
        driver.sendKeys(nameTxtUsuario, "name", acessoBackoffice);
        //driver.waitElementVisibility(nameTxtSenha, "name");
        driver.sendKeys(nameTxtSenha, "name", acessoBackoffice);
        //driver.waitElementVisibility(xpathBtnIdioma, "xpath");
        driver.click(xpathBtnIdioma, "xpath");
        //driver.waitElementVisibility(xpathIdioma, "xpath");
        driver.click(xpathIdioma, "xpath");
        //driver.waitElementVisibility(xpathBtnIdioma, "xpath");
        driver.sendKeyBoard(Keys.ENTER);
    }

    public void preencherFiltro(String filtro) {
        //driver.waitElementVisibility(classMenu, "class");
        driver.waitSeconds(1);
        driver.sendKeysCampoMascara(filtro, filtroInputXpath, "xpath");
    }

    public void selecionarOpcaoDoFiltro(String filtro) {
        driver.waitSeconds(5);
        String filtroXpath = "//tr[@aria-label='" + filtro + "']";
        driver.moveToElementJs(filtroXpath, "xpath");
        driver.click(filtroXpath, "xpath");
    }

    public void selecionarCliente(String nome) {
        driver.waitSeconds(5);
        String xpathNomeCliente = "(//span[contains(translate(normalize-space(.), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), '" + nome.toUpperCase() + "')])[position()=2]";
        int i = 0;

        while (!driver.isEnabled(xpathNomeCliente, "xpath") && i < 2) {
            driver.click("//span[contains(text(), 'itens')]/..//a[contains(@class, 'z-paging-next')]", "xpath");
            i++;
        }

        driver.click(xpathNomeCliente, "xpath");
        driver.waitSeconds(3);
        driver.click("//div[@class='yw-collapsibleContainer-caption z-north']//button[2]", "xpath");
    }

    public void selecionarMenu(String menu) {
        driver.waitSeconds(2);
        String xpathMenuCliente = "//li[@title='" + menu + "']";
        while (!driver.isDisplayed(xpathMenuCliente, "xpath")) {
            driver.click(xpathMenuCliente, "xpath");
            driver.waitSeconds(1);
        }
        //driver.waitElementVisibility(xpathMenuCliente, "xpath");
        driver.click(xpathMenuCliente, "xpath");
        driver.waitSeconds(2);
    }

    public void telefonePedidoPropriedade(String telefone) {
        driver.getValueParam("((//input[@value='" + telefone + "'])[2]", "xpath", "value");
    }

    public String valorCampoBKO(String campo) {
        driver.moveToElementJs("//div[text()='Comum']", "xpath");
        driver.waitSeconds(1);
        return driver.getValueParam("//span[text()='" + campo + "']/../../div//span/input", "xpath", "value");
    }
}
