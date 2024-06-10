package support.utils;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.IntStream;

public final class Common {

    private Common() {}

    public static final String ambiente = System.getProperty("env", "s6").toLowerCase();
    public static final String urlAmbiente = "https://accstorefront.cokecxf-commercec1-" + ambiente + "-public.model-t.cc.commerce.ondemand.com";

    public enum ProcessType {
        ACQUISITION,
        APARELHO_TROCA_APARELHO,
        EXCHANGE,
        EXCHANGE_PROMO,
        MIGRATE,
        PORTABILITY
    }

    public enum Email {
        CONFIRMA_TOKEN("Claro: Confirmação de dados Token"),
        PEDIDO_REALIZADO_BASE("Pedido Realizado com Sucesso"),
        PEDIDO_REALIZADO_AQUISICAO("Claro: Pedido realizado com Sucesso");

        //TODO Listar demais e-mails

        private final String subject;

        Email(String subject) {
            this.subject = subject;
        }

        public String getSubject() {
            return subject;
        }
    }

    public enum DeliveryMode {
        CONVENTIONAL,
        EXPRESS
    }

    public static void validarThumbs(List<String> appRef, List<WebElement> appFront, DriverQA driverQA) {
        Assert.assertEquals("Mesma quantidade de [apps/países configurados] e [presentes no Front]", appRef.size(), appFront.size());

        driverQA.waitElementVisibility(appFront.get(0), 2); //Lazy loading Front
        IntStream.range(0, appRef.size()).forEachOrdered(i -> {
            Assert.assertTrue("App/País do Front deve ser o mesmo da API - Front: <"
                    + appFront.get(i)
                            .getAttribute("src")
                            .replace("https://mondrian.claro.com.br/brands/app/72px-default/", "")
                    + ">, API: <"
                    + appRef.get(i)
                    + ">",
                    appFront.get(i).getAttribute("src").contains(appRef.get(i)));

            if (i < 5) { //Até 5 ícones são exibidos diretamente, o restante fica oculto no tooltip (+X).
                Assert.assertTrue("App/País deve estar visível", appFront.get(i).isDisplayed());
            } else {
                Assert.assertFalse("App/País deve estar oculto", appFront.get(i).isDisplayed());
            }
        });
    }
}
