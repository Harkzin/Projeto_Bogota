package web.pages;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.cucumber.spring.ScenarioScope;
import web.models.product.PlanProduct;
import web.support.utils.DriverWeb;

@Component
@ScenarioScope
public class PlpPlanosPage {

    private final DriverWeb driverWeb;

    @Autowired
    public PlpPlanosPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    //TODO Não testado, nenhum cenário passa pela PLP em 13/06/2024.
    public void validarCardPlano(PlanProduct plan, boolean isDebitPaymentFlow) {
        //TODO Atualizar seletores quando forem criados
        WebElement cardParent = driverWeb.findElement("//*[@id='addToCartForm" + plan.getCode() + "']/../preceding-sibling::div[contains(@class, 'top-card')]/div", "xpath");

        //Valida nome
        if (!(plan.getName() == null)) {
            WebElement planName = cardParent.findElement(By.xpath("h2"));
            ComumPage.validateElementText(plan.getName(), planName);
        }

        //Valida preço
        WebElement price = cardParent
                .findElement(By.xpath("div[@data-price-for]//p[contains(@class, 'p-valor')]"));
        Assert.assertEquals(plan.getFormattedPrice(isDebitPaymentFlow, true), price.getText().trim());
        Assert.assertTrue(price.isDisplayed());

        //Valida apps ilimitados
        if (plan.hasPlanApps()) {
            List<WebElement> planApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[@class='component-apps-ilimitados apps-ilimitados']//img"));
            ComumPage.validarMidiasPlano(plan.getPlanApps(), planApps, driverWeb);
        }

        //Valida título extraPlay
        if (plan.hasExtraPlayTitle()) {
            WebElement extraPlayTitle = cardParent
                    .findElement(By.xpath("div[@class='characteristics']/div[contains(@class, 'title-extra-play')][1]/p"));
            ComumPage.validateElementText(plan.getExtraPlayTitle(), extraPlayTitle);
        }

        //Valida apps extraPlay
        if (plan.hasExtraPlayApps()) {
            List<WebElement> extraPlayApps = cardParent
                    .findElements(By.xpath("div[@class='characteristics']/div[contains(@class, 'component-apps-ilimitados extra-play')]//img"));
            ComumPage.validarMidiasPlano(plan.getExtraPlayApps(), extraPlayApps, driverWeb);
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
                planPortability.remove(planPortability
                        .stream()
                        .filter(webElement -> webElement.getText().equals(plan.getExtraPlayTitle()))
                        .findFirst().orElseThrow());
            }

            IntStream.range(0, planPortability.size()).forEachOrdered(i -> {
                Assert.assertEquals(plan.getPlanPortability().get(i), planPortability.get(i).getText());

                Assert.assertTrue("Texto planPortability visível", planPortability.get(i).isDisplayed());
            });
        }
    }

    public void selecionarPlano(String id) {
        driverWeb.javaScriptClick("btn-eu-quero-" + id, "id");
    }
    public void validarPopUpControleFacil() {
        // Localiza o pop-up de planos Controle Fácil
        WebElement popUp = driverWeb.findElement("(//div[@class='mdn-Modal-content'])[4]", "xpath");
        
        // Verifica se o pop-up está visível
    Assert.assertTrue("No momento, o plano que escolheu não está disponível para você. Mas que tal essa oferta pagando com cartão de crédito?", popUp.isDisplayed());

    // Valida o título do pop-up, se necessário
    WebElement tituloPopUp = popUp.findElement(By.xpath(".//h3"));
    String textoEsperado = "18GB";
    Assert.assertEquals("O título do pop-up não corresponde ao esperado", textoEsperado, tituloPopUp.getText().trim());

    // Localiza e valida o texto "15GB no Plano"
    WebElement planoGb = popUp.findElement(By.xpath(".//p[contains(@class, 'portabilidade') and text()='15GB no Plano']"));
    Assert.assertTrue("O texto '15GB no Plano' não está visível.", planoGb.isDisplayed());

    // Localiza e valida o texto "5GB Bônus na portabilidade"
    WebElement bonusGb = popUp.findElement(By.xpath(".//p[contains(@class, 'portabilidade') and text()='5GB Bônus na portabilidade']"));
    Assert.assertTrue("O texto '5GB Bônus na portabilidade' não está visível.", bonusGb.isDisplayed());

    // Localiza e valida o valor do plano (ex.: R$ 54,90)
    WebElement valorPlano = popUp.findElement(By.xpath("//dl[@class='mdn-Price']//dd[@class='mdn-Price-price']"));
    Assert.assertTrue("O valor do plano 'R$ 54,90' não está visível.", valorPlano.isDisplayed());

    // Validação final do valor mensal (ex.: "/mês")
    WebElement valorMes = popUp.findElement(By.xpath(".//span[@class='mdn-Price-price-period' and contains(text(), '/mês')]"));
    Assert.assertTrue("O período '/mês' do valor do plano não está visível.", valorMes.isDisplayed());
}
    
public void clicarNoPopUp() {
    driverWeb.javaScriptClick("buttonModalControleFacil", "id");
}
public void clicarNoBotãoBoleto() {
    // Localiza o elemento "Boleto" e o clica usando JavaScript
    driverWeb.actionPause(3000);
    driverWeb.javaScriptClick("//div[normalize-space()='Boleto']", "xpath");
}
public void clicarNoBotãoDebitoAlt() {
    // Localiza o elemento "Debito" e o clica usando JavaScript
    driverWeb.actionPause(3000);
    driverWeb.javaScriptClick("//div[normalize-space()='Débito automático']", "xpath");
    driverWeb.actionPause(9000);
}
}
