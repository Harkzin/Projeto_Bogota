package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@ScenarioScope
public class PlpPlanosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public PlpPlanosPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    //TODO Não testado, nenhum cenário passa pela PLP em 13/06/2024.
    private void validarCardPlano(String code) {
        //TODO Atualizar seletores quando forem criados
        WebElement cardParent = driverQA.findElement("//*[@id='addToCartForm" + code + "']/../preceding-sibling::div[contains(@class, 'top-card')]/div", "xpath");

        //Valida nome
        if (!(cartOrder.getPlan().getName() == null)) {
            WebElement planName = cardParent.findElement(By.xpath("h2"));
            ComumPage.validateElementText(cartOrder.getPlan().getName(), planName);
        }

        //Valida preço
        WebElement price = cardParent
                .findElement(By.xpath("div[@data-price-for]//p[contains(@class, 'p-valor')]"));
        Assert.assertEquals(cartOrder.getPlan().getFormattedPlanPrice(cartOrder.isDebitPaymentFlow, true), price.getText().trim());
        Assert.assertTrue(price.isDisplayed());

        //Valida apps ilimitados
        if (cartOrder.getPlan().hasPlanApps()) {
            List<WebElement> planApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[@class='component-apps-ilimitados apps-ilimitados']//img"));
            ComumPage.validarMidiasPlano(cartOrder.getPlan().getPlanApps(), planApps, driverQA);
        }

        //Valida título extraPlay
        if (cartOrder.getPlan().hasExtraPlayTitle()) {
            WebElement extraPlayTitle = cardParent
                    .findElement(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')][1]/p"));
            ComumPage.validateElementText(cartOrder.getPlan().getExtraPlayTitle(), extraPlayTitle);
        }

        //Valida apps extraPlay
        if (cartOrder.getPlan().hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'component-apps-ilimitados extra-play')]//img"));
            ComumPage.validarMidiasPlano(cartOrder.getPlan().getExtraPlayApps(), extraPlayApps, driverQA);
        }

        //Valida planPortability (GB e bônus - antigo)
        if (cartOrder.getPlan().hasPlanPortability()) {
            List<WebElement> planPortability = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')]"))
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            //Remove o elemento do [título extraPlay] que vem junto na lista, planportability e clarotitleextraplay usam as mesmas classes css.
            //A posição entre eles pode mudar, não servindo como referência.
            if (cartOrder.getPlan().hasExtraPlayTitle()) {
                planPortability.remove(planPortability
                        .stream()
                        .filter(webElement -> webElement.getText().equals(cartOrder.getPlan().getExtraPlayTitle()))
                        .findFirst().orElseThrow());
            }

            IntStream.range(0, planPortability.size()).forEachOrdered(i -> {
                Assert.assertEquals(cartOrder.getPlan().getPlanPortability().get(i), planPortability.get(i).getText());

                Assert.assertTrue("Texto planPortability visível", planPortability.get(i).isDisplayed());
            });
        }
    }

    public void selecionarPlano(String id) {
        validarCardPlano(id);
        driverQA.javaScriptClick("btn-eu-quero-" + id + "", "id");
    }
}
