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
import java.util.stream.Collectors;

import static web.pages.ComumPage.*;

@Component
@ScenarioScope
public class ReadequacaoPage  {

    private final DriverWeb driverWeb;

    @Autowired
    public ReadequacaoPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPaginaReadequacaoTHAB(PlanProduct plan) {
        driverWeb.waitPageLoad("claro/pt/cart?THAB=true", 10);

        Assert.assertNotNull(driverWeb.findElement("controle-antecipado", "id"));

        //Valida card
        //Valida nome
        if (!(plan.getName() == null)) {
            WebElement name = driverWeb.findElement("//*[@id='controle-antecipado']//h3[contains(@class, 'titulo-produto')]", "xpath");
            validateElementText(plan.getName(), name);
        }

        //Valida preço
        WebElement price = driverWeb.findElement("//*[@id='controle-antecipado']//p[contains(@class, 'valor')]", "xpath");
        validateElementText(plan.getFormattedPrice(false, true), price);

        //Valida apps ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = driverWeb.findElements("//*[@id='controle-antecipado']//div[contains(@class, ' apps-ilimitados')]//img", "xpath");
            validarMidiasPlano(plan.getPlanApps(), planApps, driverWeb);
        }

        //Valida planPortability (GB e bônus - antigo)
        if (plan.hasPlanPortability()) {
            List<WebElement> planPortability = driverWeb
                    .findElements("//*[@id='controle-antecipado']//div[contains(@class, 'title-extra-play')]", "xpath")
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            validarPlanPortability(planPortability, plan);
        }
    }

    public void clicarEuQuero() {
        driverWeb.javaScriptClick("buttonCheckoutThab", "id");
    }
}
