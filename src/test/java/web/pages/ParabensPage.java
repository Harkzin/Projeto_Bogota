package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.support.utils.Constants;
import web.support.utils.DriverWeb;

import static org.junit.Assert.assertTrue;
import static web.pages.ComumPage.formatPrice;
import static web.pages.ComumPage.validateElementText;
import static web.support.utils.Constants.ProcessType.*;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverWeb driverWeb;

    @Autowired
    public ParabensPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    public void validarPaginaParabens(CartOrder cart) {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
        driverWeb.actionPause(2000);

        Constants.ProcessType processType = cart.getProcessType();

        //Nome (Parabéns, {nome-cliente})
        if (!cart.isDeviceCart()) {
            String name = String.format("Parabéns, %s!", StringUtils.capitalize(cart.getUser().getName().split(" ")[0].toLowerCase()));
            validateElementText(name, driverWeb.findById("txt-parabens"));
        }

        //Plano escolhido
        if (!cart.isDeviceCart() && processType != PORTABILITY && processType != ACCESSORY) {
            validateElementText(String.format("Sua solicitação para adquirir o %s foi recebida com sucesso!", cart.getPlan().getName()), driverWeb.findById("txt-sucesso-plano"));
        }

        //Número pedido
        WebElement orderNumber = driverWeb.findById("txt-pedido");
        assertTrue(orderNumber.isDisplayed());

        //TODO inconsistência entre S6 e prod
        //Status pedido

        //Abre Accordion - Informações do pedido
        driverWeb.javaScriptClick(driverWeb.findByXpath("//*[@id='acr-expandir-informacao']/.."));
        driverWeb.actionPause(1000);

        //Número do pedido
        WebElement orderNumberWithZeros = driverWeb.findById("txt-numero-pedido");
        validateElementText("Número do pedido " + StringUtils.leftPad(orderNumber.getText(), 12, "0"), orderNumberWithZeros);
        cart.setCode(orderNumberWithZeros.getText());

        //TODO não aparece em S6
        //Número de contato
        //validateElementText(cart.getUser().getTelephone() , driverWeb.findById(""));

        //Nome
        validateElementText("Nome " + cart.getUser().getName() , driverWeb.findById("msg-informacao-nome"));

        //CPF
        String formattedCpf = cart.getUser().getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        validateElementText("CPF " + formattedCpf, driverWeb.findById("msg-informacao-cpf"));

        //Forma de pagamento
        String paymentMode = switch (cart.getEntry(cart.getPlan().getCode()).getPaymentMode()) {
            case TICKET -> "Boleto";
            case DEBITCARD -> "Débito automático";
            case CREDITCARD -> "Crédito";
            default -> "error";
        };
        validateElementText("Forma de pagamento " + paymentMode, driverWeb.findById("msg-informacao-pagamento"));

        if (cart.getProcessType() != ACCESSORY) {
            //Número de Protocolo
            validateElementText("Número de Protocolo Aguarde enquanto o seu número de Protocolo está sendo gerado", driverWeb.findById("txt-numero-protocolo"));

            //Plano
            validateElementText("Plano " + cart.getPlan().getName(), driverWeb.findById("msg-informacao-plano"));

            //Vencimento da fatura //TODO sem step de escolha de data
            //validateElementText(String.format("Vencimento da fatura Dia %s de cada mês", cart.getPaymentInfo().getExpireDateSelected()), driverWeb.findById("msg-informacao-vencimento"));

            //Valor do plano
            WebElement planPrice = driverWeb.findById("msg-informacao-valor");
            driverWeb.javaScriptScrollTo(planPrice);
            validateElementText(String.format("Valor do plano R$ %s/mês", formatPrice(cart.getEntry(cart.getPlan().getCode()).getTotalPrice())), planPrice);

            //Dependentes
            if (cart.dependentQuantity() > 0) {
                //Valor dos dependentes
                CartOrder.PositionsAndPrices.OrderEntry depEntry = cart.getEntry("dependente");
                String depTotalPrice = String.format("Valor dos dependentes R$ %s ( + %d dependentes )", formatPrice(depEntry.getTotalPrice()), cart.dependentQuantity());
                validateElementText(depTotalPrice, driverWeb.findById("txt-valor-dep"));

                //Valor total
                String totalPlanPrice = formatPrice(depEntry.getTotalPrice() + cart.getEntry(cart.getPlan().getCode()).getTotalPrice());
                validateElementText(String.format("Valor total R$ %s", totalPlanPrice), driverWeb.findById("txt-valor-total"));
            }
        }

        //Abre Accordion - Endereço de entrega
        driverWeb.javaScriptClick(driverWeb.findByXpath("//*[@id='acr-expandir-endereco']/.."));
        driverWeb.actionPause(1000);

        if (processType == ACQUISITION || processType == PORTABILITY) { //TODO Para fluxos de base atualmente não há de onde obter os dados de endereço
            //Endereço de Entrega
            CartOrder.Address addr = cart.getDeliveryAddress();
            String address = String.format("Endereço de entrega %s, %s - %s - %s - %s %s CEP %s", addr.getStreetname(), addr.getStreetnumber(), addr.getBuilding(), addr.getNeighbourhood(), addr.getTown(), addr.getStateCode(), addr.getPostalcode().replaceAll("(\\d{5})(\\d{3})", "$1-$2"));

            WebElement deliveryText = driverWeb.findByXpath("//*[@id='txt-end-entrega']/..");
            driverWeb.javaScriptScrollTo(deliveryText);
            validateElementText(address, deliveryText);
        }
    }

    public void validarPaginaParabensPix() {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
        //TODO Validar valor do pix é o mesmo valor do aparelho
        WebElement qrCodePix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[2]/img");
        WebElement temporizadorPix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[1]/ul/li[1]/div[3]/div[1]/p");
        WebElement copiarCodigoPix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[1]/ul/li[1]/button");

        driverWeb.waitElementVisible(temporizadorPix, 10);
        assertTrue(qrCodePix.isDisplayed());
        assertTrue(copiarCodigoPix.isDisplayed());
    }

    public void clicarOkEntendiModal() {
        driverWeb.javaScriptClick("btn-entendi-modal-abr", "id");
    }
}