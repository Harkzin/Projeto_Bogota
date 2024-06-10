package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

import java.util.List;
import java.util.function.Function;

import static support.utils.Common.validarThumbs;

@Component
public class PdpPlanosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public PdpPlanosPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private WebElement debitPayment;
    private WebElement ticketPayment;
    private WebElement loyalty;
    private WebElement noLoyalty;
    private WebElement planCharacteristics;
    private boolean hasLoyalty = true;

    public void validarPDP() {
        driverQA.waitPageLoad(cartOrder.getPlan().getUrl(), 10);

        debitPayment = driverQA.findElement("rdn-debitcard", "id");
        ticketPayment = driverQA.findElement("rdn-ticket", "id");
        loyalty = driverQA.findElement("rdn-loyalty-true", "id");
        noLoyalty = driverQA.findElement("rdn-loyalty-false", "id");
        planCharacteristics = driverQA.findElement("plan-characteristics", "id");

        //Valida opções default
        validarDebito();
        validarFidelidade();
        validarValorPlano(true);

        //Valida nome
        String planName = cartOrder.getPlan().getName();
        Assert.assertEquals(planName, driverQA.findElement("//*[@id='plan-name']/span", "xpath").getText());
        //TODO erro Assert.assertEquals(planName, driverQA.findElement("//*[@id='plan-name-nav']/strong[1]", "xpath").getText());

        //Valida resumo
        WebElement summary = driverQA.findElement("//*[@id='plan-name']/following-sibling::p[1]", "xpath");
        Assert.assertTrue(summary.isDisplayed());
        Assert.assertTrue(cartOrder.getPlan().getSummary().contains(summary.getText()));

        //Valida composição
        validarAppsIlimitados(true);
        validarTituloExtraPlay();
        validarAppsExtraPlay();
        validarAppsServicosClaro();
    }

    private void validarDebito() {
        Assert.assertTrue(debitPayment.isSelected());
        Assert.assertFalse(ticketPayment.isSelected());
    }

    private void validarFidelidade() {
        Assert.assertTrue(loyalty.isSelected());
        Assert.assertFalse(noLoyalty.isSelected());
    }

    public void selecionarDebito() {
        driverQA.javaScriptClick(debitPayment);
        validarDebito();

        cartOrder.isDebitPaymentFlow = true;
    }

    public void selecionarBoleto() {
        driverQA.javaScriptClick(ticketPayment);
        Assert.assertTrue(ticketPayment.isSelected());
        Assert.assertFalse(debitPayment.isSelected());

        cartOrder.isDebitPaymentFlow = false;
    }

    public void selecionarFidelidade() {
        driverQA.javaScriptClick(loyalty);
        validarFidelidade();

        hasLoyalty = true;
    }

    public void selecionarSemFidelidade() {
        driverQA.javaScriptClick(noLoyalty);
        Assert.assertTrue(noLoyalty.isSelected());
        Assert.assertFalse(loyalty.isSelected());

        hasLoyalty = false;
    }

    public void validarValorPlano(Boolean isDebit) {
        //Valores do Front
        WebElement debitLoyalty = driverQA.findElement("price-debit-loyalty", "id");
        WebElement ticketLoyalty = driverQA.findElement("price-ticket-loyalty", "id");
        WebElement debitNotLoyalty = driverQA.findElement("price-debit-not-loyalty", "id");
        WebElement ticketNotLoyalty = driverQA.findElement("price-ticket-not-loyalty", "id");

        WebElement debitLoyaltyNav = driverQA.findElement("price-debit-loyalty-nav", "id");
        WebElement ticketLoyaltyNav = driverQA.findElement("price-ticket-loyalty-nav", "id");
        WebElement debitNotLoyaltyNav = driverQA.findElement("price-debit-not-loyalty-nav", "id");
        WebElement ticketNotLoyaltyNav = driverQA.findElement("price-ticket-not-loyalty-nav", "id");

        //Valores de referência API
        String debitLoyaltyPrice = cartOrder.getPlan().getFormattedPlanPrice(true, true);
        String ticketLoyaltyPrice = cartOrder.getPlan().getFormattedPlanPrice(false, true);
        String debitNotLoyaltyPrice = cartOrder.getPlan().getFormattedPlanPrice(true, false);
        String ticketNotLoyaltyPrice = cartOrder.getPlan().getFormattedPlanPrice(false, false);

        Function<WebElement, String> getPrice = element -> element
                .findElement(By.xpath("span[2]"))
                .getText()
                .trim();

        if (isDebit) {
            if (hasLoyalty) {
                Assert.assertEquals(debitLoyaltyPrice, getPrice.apply(debitLoyalty));
                Assert.assertEquals(debitLoyaltyPrice, getPrice.apply(debitLoyaltyNav));

                Assert.assertTrue(debitLoyalty.isDisplayed());
                Assert.assertFalse(ticketLoyalty.isDisplayed());
                Assert.assertFalse(debitNotLoyalty.isDisplayed());
                Assert.assertFalse(ticketNotLoyalty.isDisplayed());
            } else {
                Assert.assertEquals(debitNotLoyaltyPrice, getPrice.apply(debitNotLoyalty));
                Assert.assertEquals(debitNotLoyaltyPrice, getPrice.apply(debitNotLoyaltyNav));

                Assert.assertFalse(debitLoyalty.isDisplayed());
                Assert.assertFalse(ticketLoyalty.isDisplayed());
                Assert.assertTrue(debitNotLoyalty.isDisplayed());
                Assert.assertFalse(ticketNotLoyalty.isDisplayed());
            }
        } else {
            if (hasLoyalty) {
                Assert.assertEquals(ticketLoyaltyPrice, getPrice.apply(ticketLoyalty));
                Assert.assertEquals(ticketLoyaltyPrice, getPrice.apply(ticketLoyaltyNav));

                Assert.assertFalse(debitLoyalty.isDisplayed());
                Assert.assertTrue(ticketLoyalty.isDisplayed());
                Assert.assertFalse(debitNotLoyalty.isDisplayed());
                Assert.assertFalse(ticketNotLoyalty.isDisplayed());

            } else {
                Assert.assertEquals(ticketNotLoyaltyPrice, getPrice.apply(ticketNotLoyalty));
                Assert.assertEquals(ticketNotLoyaltyPrice, getPrice.apply(ticketNotLoyaltyNav));

                Assert.assertFalse(debitLoyalty.isDisplayed());
                Assert.assertFalse(ticketLoyalty.isDisplayed());
                Assert.assertFalse(debitNotLoyalty.isDisplayed());
                Assert.assertTrue(ticketNotLoyalty.isDisplayed());
            }
        }
    }

    private void validarTituloAppsIlimitados() {
        String appsTitleResume = planCharacteristics
                .findElement(By.xpath("div[contains(concat(' ',normalize-space(@class),' '), ' apps-ilimitados ')]/div/div[1]"))
                .getText();

        Assert.assertEquals(cartOrder.getPlan().getTitlePlanApps(), appsTitleResume);
    }

    public void validarAppsIlimitados(Boolean exibe) {
        WebElement planAppsParent = planCharacteristics
                .findElement(By.xpath("div[contains(concat(' ',normalize-space(@class),' '), ' apps-ilimitados ')]"));
        //TODO
        List<WebElement> planApps = planAppsParent.findElements(By.xpath(".//img"));

        if (exibe) {
            driverQA.waitElementVisibility(planAppsParent, 2);
            validarTituloAppsIlimitados();
            validarThumbs(cartOrder.getPlan().getPlanApps(), planApps, driverQA);
        } else {
            driverQA.waitElementInvisibility(planAppsParent, 2);
        }
    }

    private void validarTituloExtraPlay() {
        WebElement claroTitleExtraPlay = planCharacteristics
                .findElement(By.xpath("div[contains(concat(' ',normalize-space(@class)), ' title-extra-play')]/p"));

        Assert.assertTrue(claroTitleExtraPlay.isDisplayed());
        Assert.assertEquals(cartOrder.getPlan().getTitleExtraPlay(), claroTitleExtraPlay.getText());
    }

    private void validarAppsExtraPlay() {
        List<WebElement> extraPlayApps = planCharacteristics
                .findElements(By.xpath("div[contains(concat(' ',normalize-space(@class),' '), ' extra-play ')]/.//img"));

        validarThumbs(cartOrder.getPlan().getExtraPlayApps(), extraPlayApps, driverQA);
    }

    private void validarTituloServicosClaro() {
        String claroServices = planCharacteristics
                .findElement(By.xpath("div[contains(concat(' ',normalize-space(@class)), ' claro-services')]/p"))
                .getText();

        Assert.assertEquals(cartOrder.getPlan().getTitleClaroServices(), claroServices);
    }

    private void validarAppsServicosClaro() {
        List<WebElement> claroServicesApps = planCharacteristics
                .findElements(By.xpath("div[contains(concat(' ',normalize-space(@class)), ' claro-services')]/div/.//img"));

        validarTituloServicosClaro();
        validarThumbs(cartOrder.getPlan().getClaroServices(), claroServicesApps, driverQA);
    }

    public void clicarEuQuero() {
        driverQA.javaScriptClick("btn-eu-quero-" + cartOrder.getPlan().getCode(), "id");
    }
}