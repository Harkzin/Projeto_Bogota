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
import static web.support.utils.Constants.ClaroJourneyInformation.CONTROLE_FACIL;

import web.models.CartOrder;

@Component
@ScenarioScope
public class ReadequacaoPage {

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
            validatePlanMedias(plan.getPlanApps(), planApps, driverWeb);
        }

        //Valida planPortability (GB e bônus - antigo)
        if (plan.hasPlanPortability()) {
            List<WebElement> planPortability = driverWeb
                    .findElements("//*[@id='controle-antecipado']//div[contains(@class, 'title-extra-play')]", "xpath")
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            validatePlanPortability(plan, planPortability);
        }
    }

    public void validarPaginaReadCtrlFacil(CartOrder cart) {
        driverWeb.waitPageLoad("/claro/pt/offer-plan/controle-facil", 10);

        driverWeb.findElements("//div[contains(@class, 'characteristics')]", "xpath");
        int quantidadeCards = driverWeb.findElements("//div[contains(@class, 'characteristics')]", "xpath").size();
        Assert.assertTrue("A quantidade de cards exibidos para controle facil deve ser no minimo 1 e no maximo 3: " + quantidadeCards, quantidadeCards >= 1 && quantidadeCards <= 3);

        cart.setJourneyInformation(CONTROLE_FACIL);

        WebElement cardControleFacil = driverWeb.findByXpath("(//*[@class='preco-home bestPrice']/parent::div)[1]");
        cart.setPlan(cardControleFacil.getAttribute("data-productcode"));
        cart.updatePlanCartPromotion();

        PlanProduct plan = cart.getPlan();

        // Verifica se o elemento principal está presente
        Assert.assertNotNull(driverWeb.findElement("offers-template", "class"));

        // Valida o nome do plano
        if (plan.getName() != null) {
            WebElement name = driverWeb.findElement("//h3[contains(@class, 'titulo-produto')]", "xpath");
            validateElementText(plan.getName(), name);
        }

        // Valida o preço do plano
        WebElement price = driverWeb.findElement("//div[contains(@class, 'price')]", "xpath");
        validateElementText(String.format("%s /mês", plan.getFormattedPrice()), price);

        // Valida aplicativos ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = driverWeb.findElements("(//div[contains(@class, 'characteristics')])[1]//div[contains(@class, 'apps-ilimitados')][1]//img", "xpath");
            validatePlanMedias(plan.getPlanApps(), planApps, driverWeb);
        }

        // Valida portabilidade do plano (GB e bônus)
        if (plan.hasPlanPortability()) {
            List<WebElement> planPortability = driverWeb
                    .findElements("(//div[contains(@class, 'characteristics')])[1]//div[contains(@class, 'title-extra-play')]", "xpath")
                    .stream()
                    .map(webElement -> webElement.findElement(By.tagName("p")))
                    .collect(Collectors.toList());

            validatePlanPortability(plan, planPortability);
        }
    }

    public void clicarEuQuero() {
        driverWeb.javaScriptClick("buttonCheckoutThab", "id");
    }

    public void clicarEuQueroControleFacil() {
        driverWeb.javaScriptClick("//button[@type='submit' and @class='button button-primary' and contains(@onclick, 'ACC.claroGlobal.loading.show')]", "xpath");
    }
}
