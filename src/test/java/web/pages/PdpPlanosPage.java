package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.models.Product;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static web.pages.ComumPage.*;

@Component
@ScenarioScope
public class PdpPlanosPage {

    private final DriverWeb driverWeb;
    private final CartOrder cartOrder;

    @Autowired
    public PdpPlanosPage(DriverWeb driverWeb, CartOrder cartOrder) {
        this.driverWeb = driverWeb;
        this.cartOrder = cartOrder;
    }

    private WebElement debitPayment;
    private WebElement ticketPayment;
    private WebElement loyalty;
    private WebElement noLoyalty;
    private WebElement planCharacteristics;

    public void validarPdpPlanos(Product plan) {
        driverWeb.waitPageLoad(plan.getUrl(), 10);

        debitPayment = driverWeb.findElement("rdn-debitcard", "id");
        ticketPayment = driverWeb.findElement("rdn-ticket", "id");
        loyalty = driverWeb.findElement("rdn-loyalty-true", "id");
        noLoyalty = driverWeb.findElement("rdn-loyalty-false", "id");
        planCharacteristics = driverWeb.findElement("plan-characteristics", "id");

        //Valida opções default
        validarDebito();
        validarFidelidade();
        validarValorPlano(plan, true, true);

        //Valida resumo, caso configurado
        if (!plan.getSummary().isEmpty()) {
            WebElement summary = driverWeb.findElement("//*[@id='plan-name']/following-sibling::p[1]", "xpath");

            Assert.assertTrue(summary.isDisplayed());
            Assert.assertTrue(plan.getSummary().contains(summary.getText()));
        }

        //Valida descrição, caso configurado
        if (!plan.getDescription().isEmpty()) {
            List<WebElement> descriptionElements = driverWeb.findElements("//*[@id='product-page-description']/div[2]/div/p", "xpath");

            descriptionElements.forEach(webElement -> { //Valida a exibição de cada parágrafo da descrição, pode haver um ou vários.
                if (!webElement.getText().isEmpty()) {
                    Assert.assertTrue("Texto de descrição é exibido", webElement.isDisplayed());
                }
            });

            String descriptionText = descriptionElements
                    .stream()
                    .map(webElement -> webElement.getAttribute("outerHTML"))
                    .collect(Collectors.joining());

            Assert.assertTrue(descriptionText.contains(plan.getDescription()));
        }

        //Valida nome do Plano, caso configurado
        if (!plan.getName().isEmpty()) {
            //Nome principal
            WebElement planName = driverWeb.findElement("//*[@id='plan-name']/span", "xpath");
            validateElementText(plan.getName(), planName);

            //Nome nav (barra horizontal superior)
            showNav();
            WebElement planNameNav = driverWeb.findElement("//*[@id='plan-name-nav']/strong[1]", "xpath");
            Assert.assertEquals(plan.getName(), planNameNav.getText());
            Assert.assertTrue(planNameNav.isDisplayed());
        }

        validarAppsIlimitadosPdp(plan,true);

        //Valida título extraPlay, caso configurado
        if (plan.hasExtraPlayTitle()) {
            WebElement claroExtraPlayTitle = planCharacteristics
                    .findElement(By.xpath("div[contains(@class, 'title-extra-play')]/p"));

            validateElementText(plan.getExtraPlayTitle(), claroExtraPlayTitle);
        }

        //Valida apps extraPlay, caso configurado
        if (plan.hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = planCharacteristics
                    .findElements(By.xpath("div[contains(@class, ' extra-play ')]//img"));

            validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
        }

        //Valida serviços Claro, caso configurado
        if (plan.hasClaroServices()) {
            WebElement claroServicesTitle = planCharacteristics
                    .findElement(By.xpath("div[contains(@class, ' claro-services')]/p"));

            List<WebElement> claroServicesApps = planCharacteristics
                    .findElements(By.xpath("div[contains(@class, ' claro-services')]//img"));

            validarServicosClaro(driverWeb, plan, claroServicesTitle, claroServicesApps);
        }
    }

    private void showNav() {
        //Scroll forçado para exibir o nav (barra suspensa superior). Só aparece ao descer na página.
        driverWeb.javaScriptScrollTo(driverWeb.findElement("footer-claro", "id"));
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
        driverWeb.javaScriptClick(debitPayment);
        validarDebito();
    }

    public void selecionarBoleto() {
        driverWeb.javaScriptClick(ticketPayment);
        Assert.assertTrue(ticketPayment.isSelected());
        Assert.assertFalse(debitPayment.isSelected());
    }

    public void selecionarFidelidade() {
        driverWeb.javaScriptClick(loyalty);
        validarFidelidade();
    }

    public void selecionarSemFidelidade() {
        driverWeb.javaScriptClick(noLoyalty);
        Assert.assertTrue(noLoyalty.isSelected());
        Assert.assertFalse(loyalty.isSelected());
    }

    public void validarValorPlano(Product plan, boolean isDebit, boolean hasLoyalty) {
        //Valores do Front
        WebElement debitLoyalty = driverWeb.findElement("price-debit-loyalty", "id");
        WebElement ticketLoyalty = driverWeb.findElement("price-ticket-loyalty", "id");
        WebElement debitNotLoyalty = driverWeb.findElement("price-debit-not-loyalty", "id");
        WebElement ticketNotLoyalty = driverWeb.findElement("price-ticket-not-loyalty", "id");

        WebElement debitLoyaltyNav = driverWeb.findElement("price-debit-loyalty-nav", "id");
        WebElement ticketLoyaltyNav = driverWeb.findElement("price-ticket-loyalty-nav", "id");
        WebElement debitNotLoyaltyNav = driverWeb.findElement("price-debit-not-loyalty-nav", "id");
        WebElement ticketNotLoyaltyNav = driverWeb.findElement("price-ticket-not-loyalty-nav", "id");

        //Valores de referência API
        String debitLoyaltyPrice = plan.getFormattedPlanPrice(true, true);
        String ticketLoyaltyPrice = plan.getFormattedPlanPrice(false, true);
        String debitNotLoyaltyPrice = plan.getFormattedPlanPrice(true, false);
        String ticketNotLoyaltyPrice = plan.getFormattedPlanPrice(false, false);

        //Recebe o WebElement e retorna o preço em String
        Function<WebElement, String> getPrice = element -> element
                .findElement(By.xpath("span[2]"))
                .getText()
                .trim();

        if (isDebit) {
            if (hasLoyalty) {
                Consumer<WebElement> validateDebitLoyalty = webElement -> {
                    Assert.assertEquals(debitLoyaltyPrice, getPrice.apply(webElement));

                    //Exibe apenas um dos preços
                    Assert.assertTrue(webElement.isDisplayed());
                    Assert.assertFalse(ticketLoyalty.isDisplayed());
                    Assert.assertFalse(debitNotLoyalty.isDisplayed());
                    Assert.assertFalse(ticketNotLoyalty.isDisplayed());
                };

                validateDebitLoyalty.accept(debitLoyalty);
                showNav();
                validateDebitLoyalty.accept(debitLoyaltyNav);
            } else {
                Consumer<WebElement> validateDebitNotLoyalty = webElement -> {
                    Assert.assertEquals(debitNotLoyaltyPrice, getPrice.apply(webElement));

                    //Exibe apenas um dos preços
                    Assert.assertFalse(debitLoyalty.isDisplayed());
                    Assert.assertFalse(ticketLoyalty.isDisplayed());
                    Assert.assertTrue(webElement.isDisplayed());
                    Assert.assertFalse(ticketNotLoyalty.isDisplayed());
                };

                validateDebitNotLoyalty.accept(debitNotLoyalty);
                showNav();
                validateDebitNotLoyalty.accept(debitNotLoyaltyNav);
            }
        } else {
            if (hasLoyalty) {
                Consumer<WebElement> validateTicketLoyalty = webElement -> {
                    Assert.assertEquals(ticketLoyaltyPrice, getPrice.apply(webElement));

                    //Exibe apenas um dos preços
                    Assert.assertFalse(debitLoyalty.isDisplayed());
                    Assert.assertTrue(webElement.isDisplayed());
                    Assert.assertFalse(debitNotLoyalty.isDisplayed());
                    Assert.assertFalse(ticketNotLoyalty.isDisplayed());
                };

                validateTicketLoyalty.accept(ticketLoyalty);
                showNav();
                validateTicketLoyalty.accept(ticketLoyaltyNav);
            } else {
                Consumer<WebElement> validateTicketNotLoyalty = webElement -> {
                    Assert.assertEquals(ticketNotLoyaltyPrice, getPrice.apply(webElement));

                    //Exibe apenas um dos preços
                    Assert.assertFalse(debitLoyalty.isDisplayed());
                    Assert.assertFalse(ticketLoyalty.isDisplayed());
                    Assert.assertFalse(debitNotLoyalty.isDisplayed());
                    Assert.assertTrue(webElement.isDisplayed());
                };

                validateTicketNotLoyalty.accept(ticketNotLoyalty);
                showNav();
                validateTicketNotLoyalty.accept(ticketNotLoyaltyNav);
            }
        }
    }

    public void validarAppsIlimitadosPdp(Product plan, boolean exibe) {
        if (plan.hasPlanApps()) {
            WebElement planAppsParent = planCharacteristics
                    .findElement(By.xpath("div[contains(concat(' ',normalize-space(@class),' '), ' apps-ilimitados ')]"));

            if (exibe) {
                driverWeb.waitElementVisible(planAppsParent, 2);

                //Título
                WebElement planAppsTitle = planAppsParent.findElement(By.xpath("div[1]/div"));
                //Apps
                List<WebElement> planApps = planAppsParent.findElements(By.xpath(".//img"));
                validarAppsIlimitados(driverWeb, plan, planAppsTitle, planApps);
            } else {
                driverWeb.waitElementInvisible(planAppsParent, 2);
            }
        }
    }

    public void clicarEuQuero(String id) {
        driverWeb.javaScriptClick("btn-eu-quero-" + id, "id");
    }
}