package pages;

import support.DriverQA;

public class ComumPage {
    private final DriverQA driver;

    //private final String GbNoPlanoResumo = (HomePage.gbBonusCardHome.isEmpty() && !HomePage.gbPlanoCardHome.isEmpty()) ? "(//*[@class='modality']//p)[2]" : "(//*[@class='modality']//p)[3]";
    //public static String MetodoPagamentoResumo = "(//*[@class='mdn-Price-suffix'])[2]";
    //public static String MetodoPagamentoResumo2 = "(//*[@class='mdn-Price-suffix'])[2]";
    //private static final String TermosComboMulti = "//p[@class='terms-and-conditions-page-description']";

    public static String urlAmbiente = "https://accstorefront.cokecxf-commercec1-" + System.getProperty("env", "s6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com";
    public ComumPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

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

            /*
        switch (botao) {
            case "Continuar":
                driver.JavaScriptClick(DadosPessoaisPage.BtnContinuar, "id");
                break;
            case "Continuar pagamento":
                driver.JavaScriptClick(DadosPessoaisPage.BtnContinuarPagamento, "id");
                break;
            case "Não concordo":
                driver.JavaScriptClick(CustomizarFaturaPage.NaoConcordo, "id");
                break;
            case "Ok, entendi":
                driver.JavaScriptClick(CustomizarFaturaPage.ClicarOKEntendi, "id");
                break;
            case "Finalizar":
                driver.JavaScriptClick(TokenPage.BotaoFinalizarCarrinho, "id");
            case "Entrar":
                driver.JavaScriptClick(HomePage.EntrarBtn, "id");
                break;
            case "Acessar":
                driver.JavaScriptClick(HomePage.AcessarBtn, "id");
                break;
            case "11947190768":
                driver.JavaScriptClick(HomePage.OlaEcommerceBtn, "id");
                break;
            case "Meus pedidos":
                driver.JavaScriptClick(HomePage.MeusPedidosBtn, "id");
                break;
        }
         */

}
