package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import support.DriverQA;

public class CustomizarFaturaPage {
    private final DriverQA driverQA;

    public CustomizarFaturaPage(DriverQA stepDriver) {
        driverQA = stepDriver;
    }

    private final String pagamentoDebito = "tab-debito-automatico";

    private void validarCamposDebito() {
        Select selectBanco = new Select(driverQA.findElement("slc-banco", "id"));
        Assert.assertEquals(selectBanco.getFirstSelectedOption().getText(), "Banco");

        WebElement agencia = driverQA.findElement("slc-agencia", "id");
        Assert.assertEquals(agencia.getAttribute("value"), "");
        Assert.assertFalse(agencia.isEnabled());

        WebElement conta = driverQA.findElement("slc-conta", "id");
        Assert.assertEquals(conta.getAttribute("value"), "");
        Assert.assertFalse(conta.isEnabled());
    }

    public void validarPaginaCustomizarFatura() {
        driverQA.waitPageLoad("/checkout/multi/payment-method", 60);

        if (driverQA.findElement(pagamentoDebito, "id").findElement(By.tagName("input")).isSelected()) {
            validarCamposDebito();
        }
    }

    public void selecionarDebito() {
        WebElement pagamentoDebitoElement = driverQA.findElement(pagamentoDebito, "id");
        driverQA.JavaScriptClick(pagamentoDebitoElement.findElement(By.tagName("div")));
        Assert.assertTrue(pagamentoDebitoElement.findElement(By.tagName("input")).isSelected());

        validarCamposDebito();
    }

    public void selecionarBoleto() {
        WebElement pagamentoBoletoElement = driverQA.findElement("tab-boleto", "id");
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
        String termos = (driverQA.findElement(pagamentoDebito, "id").findElement(By.tagName("input")).isSelected()) ? "chk-termos-clarodebitpayment" : "chk-termos-claroticketpayment";

        WebElement termosElement = driverQA.findElement(termos, "id");
        driverQA.JavaScriptClick(termosElement);

        Assert.assertTrue(termosElement.isSelected());
    }

    public void clicarContinuar() {
        driverQA.JavaScriptClick("btn-continuar", "id");
    }

    public void clickOkEntendi() {
        driverQA.JavaScriptClick("btn-multa-entendi", "id");
    }

    public void clickNaoConcordo() {
        driverQA.JavaScriptClick("btn-multa-nao-concordo", "id");
    }

    public void direcionadoParaMulta() {
        driverQA.waitPageLoad("claro/pt/checkout/multi/payment-method/add", 10);

        Assert.assertNotNull(driverQA.findElement("txt-mensagem-multa", "id"));
    }

    public void direcionadoParaTHAB() {
        driverQA.waitPageLoad("checkout/multi/payment-method/add", 10);

//        Assert.assertNotNull(driverQA.findElement("txt-controle-antecipado", "id"));
    }
}