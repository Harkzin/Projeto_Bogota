package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.CustomizarFaturaPage;
import support.BaseSteps;

public class CustomizarFaturaSteps extends BaseSteps {
    CustomizarFaturaPage customizarFaturaPage = new CustomizarFaturaPage(driverQA);

    @Entao("é direcionado para a tela de Customizar Fatura")
    public void validarPagiaCustomizarFatura() {
        customizarFaturaPage.validarPaginaCustomizarFatura();
    }

    @E("seleciona a forma de pagamento: {string}")
    public void selecionarPagamentoBoleto(String pagamento) {
        if (pagamento.equals("Débito")) {
            customizarFaturaPage.selecionarDebito();
        } else {
            customizarFaturaPage.selecionarBoleto();
        }
    }

    @E("seleciona o método de recebimento da fatura: {string}")
    public void selecionarRecebimentoFatura(String fatura) {
        customizarFaturaPage.selecionarTipoFatura(fatura);
    }

    @E("selecionar a data de vencimento {string}")
    public void selecionarDataDeVencimento(String data) {
        customizarFaturaPage.selecionarDataVencimento(data);
    }

    @E("marca o checkbox de termos de aceite")
    public void marcarTermosDeAceite() {
        customizarFaturaPage.aceitarTermos();
    }

    @Quando("o usuário clicar no botão [Continuar] da tela de Customizar Fatura")
    public void clicarContinuar() {
        customizarFaturaPage.clicarContinuar();
    }
}