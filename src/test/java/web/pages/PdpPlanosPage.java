package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.product.PlanProduct;
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

    @Autowired
    public PdpPlanosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private WebElement debitPayment;
    private WebElement ticketPayment;
    private WebElement loyalty;
    private WebElement planCharacteristics;
    private WebElement planNameNav;

    public void validarPdpPlanos(PlanProduct plan) {
        driverWeb.waitPageLoad(plan.getUrl(), 10);

        debitPayment = driverWeb.findElement("rdn-debitcard", "id");
        ticketPayment = driverWeb.findElement("rdn-ticket", "id");
        loyalty = driverWeb.findElement("rdn-loyalty-true", "id");
        planCharacteristics = driverWeb.findElement("plan-characteristics", "id");
        planNameNav = driverWeb.findElement("//*[@id='plan-name-nav']/strong[1]", "xpath");

        //Valida opções default
        validarDebito();
        validarFidelidade();
        validarValorPlano(plan, true);

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
                    .map(webElement -> webElement.getAttribute("outerHTML") + "\n")
                    .collect(Collectors.joining());

            Assert.assertTrue(descriptionText.contains(plan.getDescription()));
        }

        //Nome principal
        WebElement planName = driverWeb.findElement("//*[@id='plan-name']/span", "xpath");
        validateElementText(plan.getName(), planName);

        //Nome nav (barra horizontal superior)
        showNav();
        //WebElement planNameNav = driverWeb.findElement("//*[@id='plan-name-nav']/strong[1]", "xpath");
        Assert.assertEquals(plan.getName(), planNameNav.getText());
        Assert.assertTrue(planNameNav.isDisplayed());

        validarAppsIlimitadosPdp(plan, true);

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
        driverWeb.waitElementClickable(planNameNav, 5);
    }

    private void validarDebito() {
        Assert.assertTrue(debitPayment.isSelected());
        Assert.assertFalse(ticketPayment.isSelected());
    }

    private void validarFidelidade() {
        Assert.assertTrue(loyalty.isSelected());
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

    public void validarValorPlano(PlanProduct plan, boolean isDebit) {
        //Valores do Front
        WebElement debitLoyalty = driverWeb.findElement("price-debit-loyalty", "id");
        WebElement ticketLoyalty = driverWeb.findElement("price-ticket-loyalty", "id");

        WebElement debitLoyaltyNav = driverWeb.findElement("price-debit-loyalty-nav", "id");
        WebElement ticketLoyaltyNav = driverWeb.findElement("price-ticket-loyalty-nav", "id");

        //Valores de referência API
        String debitLoyaltyPrice = plan.getFormattedPrice(true, true);
        String ticketLoyaltyPrice = plan.getFormattedPrice(false, true);

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

    public void validarAppsIlimitadosPdp(PlanProduct plan, boolean exibe) {
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