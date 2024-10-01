package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.utils.DriverQA;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@ScenarioScope
public class PdpPlanosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public PdpPlanosPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private WebElement debitPayment;
    private WebElement ticketPayment;
    private WebElement loyalty;
    private WebElement planCharacteristics;
    private WebElement planNameNav;

    public void validarPdpPlanos() {
        driverQA.waitPageLoad(cartOrder.getPlan().getUrl(), 10);

        debitPayment = driverQA.findElement("rdn-debitcard", "id");
        ticketPayment = driverQA.findElement("rdn-ticket", "id");
        loyalty = driverQA.findElement("rdn-loyalty-true", "id");
        planCharacteristics = driverQA.findElement("plan-characteristics", "id");
        planNameNav = driverQA.findElement("//*[@id='plan-name-nav']/strong[1]", "xpath");

        //Valida opções default
        validarDebito();
        validarFidelidade();
        validarValorPlano(true);

        //Valida resumo, caso configurado
        if (!cartOrder.getPlan().getSummary().isEmpty()) {
            WebElement summary = driverQA.findElement("//*[@id='plan-name']/following-sibling::p[1]", "xpath");

            Assert.assertTrue(summary.isDisplayed());
            Assert.assertTrue(cartOrder.getPlan().getSummary().contains(summary.getText()));
        }

        //Valida descrição, caso configurado
        if (!cartOrder.getPlan().getDescription().isEmpty()) {
            List<WebElement> descriptionElements = driverQA.findElements("//*[@id='product-page-description']/div[2]/div/p", "xpath");

            descriptionElements.forEach(webElement -> { //Valida a exibição de cada parágrafo da descrição, pode haver um ou vários.
                if (!webElement.getText().isEmpty()) {
                    Assert.assertTrue("Texto de descrição é exibido", webElement.isDisplayed());
                }
            });

            String descriptionText = descriptionElements
                    .stream()
                    .map(webElement -> webElement.getAttribute("outerHTML") + "\n")
                    .collect(Collectors.joining());

            Assert.assertTrue(descriptionText.contains(cartOrder.getPlan().getDescription()));
        }

        //Valida nome do Plano, caso configurado
        //Nome principal
        WebElement planName = driverQA.findElement("//*[@id='plan-name']/span", "xpath");
        ComumPage.validateElementText(cartOrder.getPlan().getName(), planName);

        //Nome nav (barra horizontal superior)
        showNav();
        Assert.assertEquals(cartOrder.getPlan().getName(), planNameNav.getText());
        Assert.assertTrue(planNameNav.isDisplayed());

        validarAppsIlimitados(true);

        //Valida título extraPlay, caso configurado
        if (cartOrder.getPlan().hasExtraPlayTitle()) {
            WebElement claroExtraPlayTitle = planCharacteristics
                    .findElement(By.xpath("div[contains(@class, 'title-extra-play')]/p"));

            ComumPage.validateElementText(cartOrder.getPlan().getExtraPlayTitle(), claroExtraPlayTitle);
        }

        //Valida apps extraPlay, caso configurado
        if (cartOrder.getPlan().hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = planCharacteristics
                    .findElements(By.xpath("div[contains(@class, ' extra-play ')]//img"));

            ComumPage.validarMidiasPlano(cartOrder.getPlan().getExtraPlayApps(), extraPlayApps, driverQA);
        }

        //Valida serviços Claro, caso configurado
        if (cartOrder.getPlan().hasClaroServices()) {
            WebElement claroServicesTitle = planCharacteristics
                    .findElement(By.xpath("div[contains(@class, ' claro-services')]/p"));

            List<WebElement> claroServicesApps = planCharacteristics
                    .findElements(By.xpath("div[contains(@class, ' claro-services')]//img"));

            ComumPage.validarServicosClaro(driverQA, cartOrder, claroServicesTitle, claroServicesApps);
        }
    }

    private void showNav() {
        //Scroll forçado para exibir o nav (barra suspensa superior). Só aparece ao descer na página.
        driverQA.javaScriptScrollTo(driverQA.findElement("footer-claro", "id"));
        driverQA.waitElementToBeClickable(planNameNav, 5);
    }

    private void validarDebito() {
        Assert.assertTrue(debitPayment.isSelected());
        Assert.assertFalse(ticketPayment.isSelected());
    }

    private void validarFidelidade() {
        Assert.assertTrue(loyalty.isSelected());
    }

    public void selecionarDebito() {
        driverQA.javaScriptClick(debitPayment);
        validarDebito();
    }

    public void selecionarBoleto() {
        driverQA.javaScriptClick(ticketPayment);
        Assert.assertTrue(ticketPayment.isSelected());
        Assert.assertFalse(debitPayment.isSelected());
    }

    public void validarValorPlano(boolean isDebit) {
        //Valores do Front
        WebElement debitLoyalty = driverQA.findElement("price-debit-loyalty", "id");
        WebElement ticketLoyalty = driverQA.findElement("price-ticket-loyalty", "id");

        WebElement debitLoyaltyNav = driverQA.findElement("price-debit-loyalty-nav", "id");
        WebElement ticketLoyaltyNav = driverQA.findElement("price-ticket-loyalty-nav", "id");

        //Valores de referência API
        String debitLoyaltyPrice = cartOrder.getPlan().getFormattedPlanPrice(true, true);
        String ticketLoyaltyPrice = cartOrder.getPlan().getFormattedPlanPrice(false, true);

        //Recebe o WebElement e retorna o preço em String
        Function<WebElement, String> getPrice = element -> element
                .findElement(By.xpath("span[2]"))
                .getText()
                .trim();

        if (isDebit) {
            Consumer<WebElement> validateDebitLoyalty = webElement -> {
                Assert.assertEquals(debitLoyaltyPrice, getPrice.apply(webElement));

                //Exibe apenas um dos preços
                Assert.assertTrue(webElement.isDisplayed());
                Assert.assertFalse(ticketLoyalty.isDisplayed());
            };

            validateDebitLoyalty.accept(debitLoyalty);
            showNav();
            validateDebitLoyalty.accept(debitLoyaltyNav);
        } else {
            Consumer<WebElement> validateTicketLoyalty = webElement -> {
                Assert.assertEquals(ticketLoyaltyPrice, getPrice.apply(webElement));

                //Exibe apenas um dos preços
                Assert.assertFalse(debitLoyalty.isDisplayed());
                Assert.assertTrue(webElement.isDisplayed());
            };

            validateTicketLoyalty.accept(ticketLoyalty);
            showNav();
            validateTicketLoyalty.accept(ticketLoyaltyNav);
        }
    }

    public void validarAppsIlimitados(Boolean exibe) {
        if (cartOrder.getPlan().hasPlanApps()) {
            WebElement planAppsParent = planCharacteristics
                    .findElement(By.xpath("div[contains(concat(' ',normalize-space(@class),' '), ' apps-ilimitados ')]"));

            if (exibe) {
                driverQA.waitElementVisibility(planAppsParent, 2);

                //Título
                WebElement planAppsTitle = planAppsParent.findElement(By.xpath("div[1]/div"));
                //Apps
                List<WebElement> planApps = planAppsParent.findElements(By.xpath(".//img"));
                ComumPage.validarAppsIlimitados(driverQA, cartOrder, planAppsTitle, planApps);
            } else {
                driverQA.waitElementInvisibility(planAppsParent, 2);
            }
        }
    }

    public void clicarEuQuero() {
        driverQA.javaScriptClick("btn-eu-quero-" + cartOrder.getPlan().getCode(), "id");
    }
}