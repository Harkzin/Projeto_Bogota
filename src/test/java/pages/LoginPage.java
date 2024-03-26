package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import support.DriverQA;

import static javax.swing.text.html.CSS.getAttribute;

public class LoginPage {
    private final DriverQA driverQA;

    public LoginPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    public void validarPaginaLogin() {
        driverQA.waitPageLoad("/login", 10);

//        Assert.assertNotNull(driverQA.findElement("txt-telefone", "id"));
//        driverQA.findElement("btn-continuar", "id").isEnabled();

            // Verifica se o botão continuar está presente e habilitado
            WebElement btnContinuar = driverQA.findElement("btn-continuar", "id");
            Assert.assertTrue("O botão 'Continuar' não está habilitado.", btnContinuar.isEnabled());
        }

    }
