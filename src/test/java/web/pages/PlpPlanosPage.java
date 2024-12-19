package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.product.PlanProduct;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static web.pages.ComumPage.*;

@Component
@ScenarioScope
public class PlpPlanosPage {

    private final DriverWeb driverWeb;

    @Autowired
    public PlpPlanosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private boolean isDebitPaymentFlow;

    public void validarPlpControle() {
        driverWeb.waitPageLoad("controle", 10);
    }

    public void validarCardPlano(PlanProduct plan) {
        //TODO Atualizar seletores quando forem criados
        WebElement cardParent = driverWeb.findElement("//*[@id='addToCartForm" + plan.getCode() + "']/../preceding-sibling::div[contains(@class, 'top-card')]/div", "xpath");

        //Valida nome
        assertNotNull(plan.getName());
        WebElement planName = cardParent.findElement(By.xpath("h2"));
        driverWeb.javaScriptScrollTo(planName);
        validateElementText(plan.getName(), planName);

        //Valida preço
        WebElement price = cardParent.findElement(By.xpath(".//*[contains(@class, 'component-preco')]"));
        String priceRef = String.format("R$ %s /mês", plan.getFormattedPrice(isDebitPaymentFlow, true));
        validateElementText(priceRef, price);

        //Valida apps ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = cardParent.findElements(By.xpath("div[@class='characteristics']/div[@class='component-apps-ilimitados apps-ilimitados']//img"));
            validatePlanMedias(plan.getPlanApps(), planApps, driverWeb);
        }

        //Valida título extraPlay
        if (plan.hasExtraPlayTitle()) {
            WebElement extraPlayTitle = cardParent.findElement(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')][1]/p"));
            validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
        }

        //Valida apps extraPlay
        if (plan.hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = cardParent.findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'component-apps-ilimitados extra-play')]//img"));
            validatePlanMedias(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
        }

        //Valida planPortability (GB e bônus - antigo)
        if (plan.hasPlanPortability()) {
            List<WebElement> planPortability = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')]"))
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            //Remove o elemento do [título extraPlay] que vem junto na lista, planportability e clarotitleextraplay usam as mesmas classes css.
            //A posição entre eles pode mudar, não servindo como referência.
            if (plan.hasExtraPlayTitle()) {
                planPortability.remove(planPortability.stream()
                        .filter(webElement -> webElement.getText().equals(plan.getExtraPlayTitle()))
                        .findFirst().orElseThrow());
            }

            IntStream.range(0, planPortability.size()).forEachOrdered(i -> {
                assertEquals(plan.getPlanPortability().get(i), planPortability.get(i).getText());
                assertTrue("Texto planPortability visível", planPortability.get(i).isDisplayed());
            });
        }
    }

    public void selecionarPlano(String id) {
        driverWeb.javaScriptClick("btn-eu-quero-" + id, "id");
    }

    public void selecionarDebito() {
        isDebitPaymentFlow = true;

        //TODO
    }

    public void selecionarBoleto() {
        isDebitPaymentFlow = false;

        //TODO
    }
}
