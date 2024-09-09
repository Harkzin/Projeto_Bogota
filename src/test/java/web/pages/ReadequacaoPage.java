package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.support.CartOrder;
import web.support.Product;
import web.support.utils.DriverQA;

import java.util.List;
import java.util.stream.Collectors;

import static web.pages.ComumPage.*;

@Component
@ScenarioScope
public class ReadequacaoPage  {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public ReadequacaoPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    public void validarPaginaReadequacaoTHAB(Product plan) {
        driverQA.waitPageLoad("claro/pt/cart?THAB=true", 10);

        Assert.assertNotNull(driverQA.findElement("controle-antecipado", "id"));

        //Valida card
        //Valida nome
        if (!(plan.getName() == null)) {
            WebElement name = driverQA.findElement("//*[@id='controle-antecipado']//h3[contains(@class, 'titulo-produto')]", "xpath");
            validateElementText(plan.getName(), name);
        }

        //Valida preço
        WebElement price = driverQA.findElement("//*[@id='controle-antecipado']//p[contains(@class, 'valor')]", "xpath");
        validateElementText(plan.getFormattedPlanPrice(false, true), price);

        //Valida apps ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = driverQA.findElements("//*[@id='controle-antecipado']//div[contains(@class, ' apps-ilimitados')]//img", "xpath");
            validarMidiasPlano(plan.getPlanApps(), planApps, driverQA);
        }

        //Valida planPortability (GB e bônus - antigo)
        if (plan.hasPlanPortability()) {
            List<WebElement> planPortability = driverQA
                    .findElements("//*[@id='controle-antecipado']//div[contains(@class, 'title-extra-play')]", "xpath")
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            validarPlanPortability(planPortability, plan);
        }
    }

    public void clicarEuQuero() {
        driverQA.javaScriptClick("buttonCheckoutThab", "id");
    }
}
