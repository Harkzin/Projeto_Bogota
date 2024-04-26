package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import support.DriverQA;

import java.util.function.Function;

import static pages.ComumPage.*;

public class PdpPlanosPage {
    private final DriverQA driverQA;

    public PdpPlanosPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private WebElement debitPayment;
    private WebElement ticketPayment;
    private WebElement loyalty;
    private WebElement noLoyalty;
    private WebElement planCharacteristics;

    private void validarDebito() {
        Assert.assertTrue(debitPayment.isSelected());
        Assert.assertFalse(ticketPayment.isSelected());
    }

    private void validarFidelidade() {
        Assert.assertTrue(loyalty.isSelected());
        Assert.assertFalse(noLoyalty.isSelected());
    }

    public void validarPDP() {
        driverQA.waitPageLoad(Cart_planId, 10);

        debitPayment = driverQA.findElement("rdn-debitcard", "id");
        ticketPayment = driverQA.findElement("rdn-ticket", "id");
        loyalty = driverQA.findElement("rdn-loyalty-true", "id");
        noLoyalty = driverQA.findElement("rdn-loyalty-false", "id");
        planCharacteristics = driverQA.findElement("plan-characteristics", "id");

        validarDebito();
        validarFidelidade();
        validarAppIlimitados(true);
        validarValorPlano(true, true);

        String planName = "TODO ECCMAUT-351";
        //Assert.assertEquals(driverQA.findElement("//*[@id='plan-name']/span", "xpath").getText(), planName);
        //Assert.assertEquals(driverQA.findElement("//*[@id='plan-name-nav']/strong[1]", "xpath").getText(), planName);
    }

    public void selecionarDebito() {
        driverQA.JavaScriptClick(debitPayment);
        validarDebito();

        Cart_isDebitPaymentFlow = true;
    }

    public void selecionarBoleto() {
        driverQA.JavaScriptClick(ticketPayment);
        Assert.assertTrue(ticketPayment.isSelected());
        Assert.assertFalse(debitPayment.isSelected());

        Cart_isDebitPaymentFlow = false;
    }

    public void selecionarFidelidade() {
        driverQA.JavaScriptClick(loyalty);
        validarFidelidade();

        Cart_hasLoyalty = true;
    }

    public void selecionarSemFidelidade() {
        driverQA.JavaScriptClick(noLoyalty);
        Assert.assertTrue(noLoyalty.isSelected());
        Assert.assertFalse(loyalty.isSelected());

        Cart_hasLoyalty = false;
    }

    public void validarValorPlano(Boolean isDebit, Boolean hasLoyalty) {
        WebElement debitLoyalty = driverQA.findElement("price-debit-loyalty", "id");
        WebElement ticketLoyalty = driverQA.findElement("price-ticket-loyalty", "id");
        WebElement debitNotLoyalty = driverQA.findElement("price-debit-not-loyalty", "id");
        WebElement ticketNotLoyalty = driverQA.findElement("price-ticket-not-loyalty", "id");

        WebElement debitLoyaltyNav = driverQA.findElement("price-debit-loyalty-nav", "id");
        WebElement ticketLoyaltyNav = driverQA.findElement("price-ticket-loyalty-nav", "id");
        WebElement debitNotLoyaltyNav = driverQA.findElement("price-debit-not-loyalty-nav", "id");
        WebElement ticketNotLoyaltyNav = driverQA.findElement("price-ticket-not-loyalty-nav", "id");

        String debitLoyaltyPrice = "TODO ECCMAUT-351";
        String ticketLoyaltyPrice = "TODO ECCMAUT-351";
        String debitNotLoyaltyPrice = "TODO ECCMAUT-351";
        String ticketNotLoyaltyPrice = "TODO ECCMAUT-351";

        Function<WebElement, String> getPrice = element -> element.findElement(By.xpath("span[2]")).getText().replace("&nbsp;", "");

        if (isDebit) {
            if (hasLoyalty) {
                //Assert.assertEquals(getPrice.apply(debitLoyalty), debitLoyaltyPrice);
                //Assert.assertEquals(getPrice.apply(debitLoyaltyNav), debitNotLoyaltyPrice);

                Assert.assertTrue(debitLoyalty.isDisplayed());
                Assert.assertFalse(ticketLoyalty.isDisplayed());
                Assert.assertFalse(debitNotLoyalty.isDisplayed());
                Assert.assertFalse(ticketNotLoyalty.isDisplayed());
            } else {
                //Assert.assertEquals(getPrice.apply(debitNotLoyalty), debitNotLoyaltyPrice);
                //Assert.assertEquals(getPrice.apply(debitNotLoyaltyNav), debitNotLoyaltyPrice);

                Assert.assertFalse(debitLoyalty.isDisplayed());
                Assert.assertFalse(ticketLoyalty.isDisplayed());
                Assert.assertTrue(debitNotLoyalty.isDisplayed());
                Assert.assertFalse(ticketNotLoyalty.isDisplayed());
            }
        } else {
            if (hasLoyalty) {
                //Assert.assertEquals(getPrice.apply(ticketLoyalty), ticketLoyaltyPrice);
                //Assert.assertEquals(getPrice.apply(ticketLoyaltyNav), ticketLoyaltyPrice);

                Assert.assertFalse(debitLoyalty.isDisplayed());
                Assert.assertTrue(ticketLoyalty.isDisplayed());
                Assert.assertFalse(debitNotLoyalty.isDisplayed());
                Assert.assertFalse(ticketNotLoyalty.isDisplayed());

            } else {
                //Assert.assertEquals(getPrice.apply(ticketNotLoyalty), ticketNotLoyaltyPrice);
                //Assert.assertEquals(getPrice.apply(ticketNotLoyaltyNav), ticketNotLoyaltyPrice);

                Assert.assertFalse(debitLoyalty.isDisplayed());
                Assert.assertFalse(ticketLoyalty.isDisplayed());
                Assert.assertFalse(debitNotLoyalty.isDisplayed());
                Assert.assertTrue(ticketNotLoyalty.isDisplayed());
            }
        }
    }

    public void validarAppIlimitados(Boolean exibe) {
        WebElement appsIlimitadosParent = planCharacteristics.findElement(By.xpath("div[contains(concat(' ',normalize-space(@class),' '), ' apps-ilimitados ')]"));

        if (exibe) {
            //Assert.assertTrue(appsIlimitadosParent.isDisplayed());
            driverQA.waitElementVisibility(appsIlimitadosParent, 2);
            //TODO validar apps exibidos ECCMAUT-351
        } else {
            driverQA.waitElementInvisibility(appsIlimitadosParent, 2);
            //Assert.assertFalse(appsIlimitadosParent.isDisplayed());
        }
    }

    public void clicarEuQuero() {
        driverQA.JavaScriptClick("btn-eu-quero-" + Cart_planId, "id");
    }
}