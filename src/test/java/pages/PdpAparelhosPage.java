package pages;

import io.cucumber.spring.ScenarioScope;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.Product;
import support.utils.DriverQA;

import java.util.List;
import java.util.stream.IntStream;

import static pages.ComumPage.*;

@Component
@ScenarioScope
public class PdpAparelhosPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    @Autowired
    public PdpAparelhosPage(DriverQA driverQA, CartOrder cartOrder) {
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    private boolean prePaidPlanSelected;

    private void validarInfosPlano() {

    }

    public void validarPdpAparelho(Product device) {
        driverQA.waitPageLoad(device.getCode(), 10);
        driverQA.actionPause(1000);

        //Validar fabricante
        if (device.hasBrand()) {
            validateElementText(device.getBrand(), driverQA.findElement("subtitle-marca-pdp", "id"));
        }

        //Validar nome
        if (!(device.getName() == null)) {
            validateElementText(device.getName(), driverQA.findElement("head-nome-aparelho-pdp", "id"));
        }

        //Validar cores
        List<WebElement> variantColors = driverQA.findElements("//*[@id='txt-cor-do-produto']/following-sibling::div/div/div", "xpath");

        IntStream.range(0, variantColors.size()).forEachOrdered(i -> {
            WebElement variantUrl = variantColors.get(i).findElement(By.tagName("a"));
            WebElement variantName = variantColors.get(i).findElement(By.tagName("p"));

            Assert.assertTrue("Cor variante com url do modelo correto", variantUrl.getAttribute("href").contains(device.getVariants().get(i).get(0)));
            Assert.assertEquals("Nome da cor variante igual ao configurado", device.getVariants().get(i).get(1).toLowerCase(), variantName.getText().toLowerCase());

            Assert.assertTrue("Imagem com url da cor variante exibida", variantUrl.isDisplayed());
            Assert.assertTrue("Nome da cor variante exibido", variantName.isDisplayed());
        });

        //Validar plano
        validarInfosPlano();

        //Validar preço base "De"
        if (device.inStock() && !prePaidPlanSelected) {
            WebElement fullPrice = driverQA.findElement("value-total-aparelho-pdp", "id");
            driverQA.waitElementVisibility(fullPrice, 5);

            Assert.assertEquals("Valor sem desconto (De) igual ao configurado", fullPrice.getText(), device.getFormattedFullDevicePrice());
            Assert.assertTrue("Valor sem desconto (De) é exibido", fullPrice.isDisplayed());
        }

        //Validar infos técnicas
        if (device.hasDeviceFeatures()) {
            driverQA.javaScriptClick("//*[@id='tab-info-tecnicas']/h2", "xpath");
            driverQA.waitElementVisibility(driverQA.findElement("especificationDevice", "id"), 2);

            List<WebElement> features = driverQA.findElements("//*[@id='especificationDevice']/div/div", "xpath");

            IntStream.range(0, features.size()).forEachOrdered(i -> {
                WebElement featureType = features.get(i).findElement(By.xpath("div[1]/span"));
                WebElement featureValue = features.get(i).findElement(By.xpath("div[2]/span"));

                validateElementText(device.getDeviceFeatures().get(i).get(0) + ":", featureType);
                validateElementText(device.getDeviceFeatures().get(i).get(1), featureValue);
            });
        }
    }

    public void selecionarCorAparelho(String id) {
        driverQA.javaScriptClick("//a[contains(@href, '" + id + "')]", "xpath");
    }

    public void validarProdutoSemEstoque() {
        Assert.assertEquals("Produto Esgotado", driverQA.findElement("produto-esgotado", "id").getText().trim());
    }
}