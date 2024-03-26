package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import support.DriverQA;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomizarFaturaPage {
    private final DriverQA driverQA;

    public CustomizarFaturaPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private final String pagamentoDebito = "btn-aba-debito-automatico";

    // Multa
    private final String NaoConcordo = "//*[@data-multa-action='goStep2']";
    private final String ClicarOKEntendi = "//*[@data-multa-action='backHome']";

    private void validaCamposDebito() {
        Select selectBanco = new Select(driverQA.findElement("slc-banco", "id"));
        Assert.assertEquals(selectBanco.getFirstSelectedOption().getText(), "Banco");

        WebElement agencia = driverQA.findElement("slc-agencia", "id");
        Assert.assertEquals(agencia.getAttribute("value"), "");
        //Assert.assertEquals(agencia.getAttribute("disabled"), "disabled");

        WebElement conta = driverQA.findElement("slc-conta", "id");
        Assert.assertEquals(conta.getAttribute("value"), "");
        //Assert.assertEquals(conta.getAttribute("disabled"), "disabled");
    }

    public void validarPaginaCustomizarFatura() {
        driverQA.waitPageLoad("/checkout/multi/payment-method", 60);

        if (driverQA.findElement(pagamentoDebito, "id").findElement(By.tagName("input")).isSelected()) {
            validaCamposDebito();
        }
    }

    public void selecionarDebito() {
        WebElement pagamentoDebitoElement = driverQA.findElement(pagamentoDebito, "id");
        driverQA.JavaScriptClick(pagamentoDebitoElement.findElement(By.tagName("div")));
        Assert.assertTrue(pagamentoDebitoElement.findElement(By.tagName("input")).isSelected());

        validaCamposDebito();
    }

    public void selecionarBoleto() {
        WebElement pagamentoBoletoElement = driverQA.findElement("btn-aba-boleto", "id");
        driverQA.JavaScriptClick(pagamentoBoletoElement.findElement(By.tagName("div")));

        Assert.assertTrue(pagamentoBoletoElement.findElement(By.tagName("input")).isSelected());
    }

    public void selecionarTipoFatura(String fatura) {
        String pagamento = (driverQA.findElement(pagamentoDebito, "id").findElement(By.tagName("input")).isSelected()) ? "debit" : "ticket";

        driverQA.JavaScriptClick("rdn-" + fatura.toLowerCase() + "-payment-claro" + pagamento + "paymentinfo", "id");
    }

    public void selecionarDataVencimento(String data) {
        driverQA.JavaScriptClick("//label[@data-automation='vencimento-" + data + "']", "xpath");
    }

    public void aceitarTermos() {
        String termos = (driverQA.findElement(pagamentoDebito, "id").findElement(By.tagName("input")).isSelected()) ? "chk-termos-debit" : "chk-termos-ticket";

        WebElement termosElement = driverQA.findElement(termos, "id");
        driverQA.JavaScriptClick(termosElement);

        Assert.assertTrue(termosElement.isSelected());
    }

    public void clicarContinuar() {
        driverQA.JavaScriptClick("btn-continuar", "id");
    }

    public void clickOkEntendi() {driverQA.JavaScriptClick("btn-ok-entendi", "id");
    }

    public void clickNaoConcordo() {driverQA.JavaScriptClick("btn-nao-concordo", "id");
    }

    public void direcionadoParaCombo() {
        driverQA.waitPageLoad("/claro/pt/checkout/multi/terms-and-conditions", 10);

        WebElement body = driverQA.findElement("body", "tag");
        String textoComboMulti = body.getText();

        Pattern padrao = Pattern.compile("Parabéns, [A-Z]+!\\s+Como você já é combo, seu bônus está garantido!\\s+Para sua comodidade, sua data de vencimento e forma de pagamento continuam a mesma.");

        Matcher matcher = padrao.matcher(textoComboMulti);

        if (matcher.find()) {
            System.out.println("O texto foi encontrado: " + matcher.group());
        } else {
            System.out.println("O texto não foi encontrado.");
        }
    }
}