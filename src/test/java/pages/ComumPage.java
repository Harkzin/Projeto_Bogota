package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import support.CartOrder;
import support.utils.DriverQA;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class ComumPage {

    private final DriverQA driverQA;
    private final CartOrder cartOrder;

    public ComumPage(DriverQA driverQA, CartOrder cartOrder) { //Spring Autowired
        this.driverQA = driverQA;
        this.cartOrder = cartOrder;
    }

    //Valida os ícones dos Apps e Países da composição do Plano
    public static void validarMidiasPlano(List<String> appRef, List<WebElement> appFront, DriverQA driverQA) {
        Assert.assertEquals("Mesma quantidade de [apps/países configurados] e [presentes no Front]", appRef.size(), appFront.size());

        driverQA.waitElementVisibility(appFront.get(0), 2); //Lazy loading Front
        IntStream.range(0, appRef.size()).forEachOrdered(i -> {
            Assert.assertTrue("App/País do Front deve ser o mesmo da API - Front: <" + appFront.get(i)
                            .getAttribute("src")
                            .replace("https://mondrian.claro.com.br/brands/app/72px-default/", "") + ">, API: <" + appRef.get(i) + ">",
                    appFront.get(i).getAttribute("src").contains(appRef.get(i)));

            if (i < 5) { //Até 5 ícones são exibidos diretamente, o restante fica oculto no tooltip (+X).
                Assert.assertTrue("App/País deve estar visível", appFront.get(i).isDisplayed());
            } else {
                Assert.assertFalse("App/País deve estar oculto", appFront.get(i).isDisplayed());
            }
        });
    }

    public static void validarNomePlano(CartOrder cartOrder, WebElement planName) {
        String planNameRef = cartOrder.getPlan().getName();

        Assert.assertEquals(planNameRef, planName.getText());
        Assert.assertTrue(planName.isDisplayed());
    }

    public static void validarTituloExtraPlay(CartOrder cartOrder, WebElement claroTitleExtraPlay) {
        Assert.assertTrue(claroTitleExtraPlay.isDisplayed());
        Assert.assertEquals(cartOrder.getPlan().getExtraPlayTitle(), claroTitleExtraPlay.getText());
    }

    public static void validarServicosClaro(DriverQA driverQA, CartOrder cartOrder, String claroServicesTitle, List<WebElement> claroServicesApps) {
        //Valida título
        Assert.assertEquals(cartOrder.getPlan().getClaroServicesTitle(), claroServicesTitle);

        //Valida Apps
        validarMidiasPlano(cartOrder.getPlan().getClaroServices(), claroServicesApps, driverQA);
    }

    public void validarResumoCompraPlano() {
        //TODO ECCMAUT-351
    }

    public void validarResumoCompraAparelho() {
        //TODO ECCMAUT-351
    }
}