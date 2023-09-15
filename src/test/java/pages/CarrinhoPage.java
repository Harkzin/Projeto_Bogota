package pages;

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
    private String idEuQueroForm = "buttonCheckout";

    public void validarCarrinho() {
        driver.waitSeconds(1);
        String titulo = driver.getText(xpathTituloPlanoResumo, "xpath");
        String gbPlano = driver.getText(xpathGbNoPlanoResumo, "xpath");
        String gbBonus = driver.getText(xpathGbDeBonusResumo, "xpath");
        String valor = driver.getText(xpathValorTotalResumo, "xpath");
        Assert.assertTrue((getTituloCardHome().equals(titulo)));
        Assert.assertTrue((getValorPlanoHome().contains(valor)));
        Assert.assertTrue((getGbPlanoCardHome().equals(gbPlano)));
        Assert.assertTrue((getGbBonusCardHome().equals(gbBonus)));

    }

}
