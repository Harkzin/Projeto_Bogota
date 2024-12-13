package web.pages;

import io.cucumber.spring.ScenarioScope;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.models.CartOrder;
import web.support.utils.DriverWeb;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;
import static web.pages.ComumPage.formatPrice;
import static web.pages.ComumPage.validateElementText;
import static web.support.utils.Constants.*;
import static web.support.utils.Constants.ChipType.ESIM;
import static web.support.utils.Constants.ProcessType.*;
import static web.support.utils.Constants.StatusSuccessPage.*;
import static web.support.utils.Constants.ZoneDeliveryMode.*;

@Component
@ScenarioScope
public class ParabensPage {

    private final DriverWeb driverWeb;

    @Autowired
    public ParabensPage(DriverWeb driverWeb) {
        this.driverWeb = driverWeb;
    }

    private void validateStatus(List<WebElement> statusList, CartOrder cart) {
        boolean isDeviceCart = cart.isDeviceCart();
        ZoneDeliveryMode deliveryMode = cart.getDeliveryMode();

        List<String> statusListRef = switch (cart.getProcessType()) {
            case ACQUISITION -> isDeviceCart ? ACQUISITION_DEVICE.getStatusList() : (deliveryMode == CONVENTIONAL ? ACQUISITION_PLAN.getStatusList() : ACQUISITION_PLAN_EXPRESS.getStatusList());
            case MIGRATE, EXCHANGE, EXCHANGE_PROMO, APARELHO_TROCA_APARELHO -> isDeviceCart ? MIGRATE_EXCHANGE_DEVICE.getStatusList() : MIGRATE_EXCHANGE_PLAN.getStatusList();
            case PORTABILITY -> isDeviceCart ? PORTABILITY_DEVICE.getStatusList() : PORTABILITY_PLAN.getStatusList();
            case ACCESSORY -> StatusSuccessPage.ACCESSORY.getStatusList();
        };

        IntStream.range(0, statusListRef.size()).forEachOrdered(i ->
                validateElementText(statusListRef.get(i), statusList.get(i))
        );
    }

    public void validarDados(CartOrder cart) {
        ProcessType processType = cart.getProcessType();

        //Valida mensagem esim
        //TODO mudar para id apos mapeamento
        if (cart.getClaroChip().getChipType() == ESIM) {
            driverWeb.findElement("/html/body/main/div[3]/div/div[2]/div/div/div/div/div[1]/div[4]/div[2]/p", "xpath").getText().equals("Assim que o seu pedido for aprovado, você receberá o código do eSIM por e-mail. Além disso, na página Acompanhe seu Pedido, você terá acesso às instruções passo a passo para habilitar o eSIM.");
        }

        //Nome (Parabéns, {nome-cliente})
        String customerName = StringUtils.capitalize(cart.getUser().getName().split(" ")[0].toLowerCase());
        String successText = String.format("Parabéns, %s!", customerName);
        validateElementText(successText, driverWeb.findById("txt-parabens"));

        //Previsão de entrega (Aparelhos)
        if (cart.isDeviceCart()) {
            WebElement deliveryDate = driverWeb.findByXpath("//*[@id='txt-parabens']/following-sibling::div[2]/p");
            assertTrue(deliveryDate.isDisplayed());
            assertTrue(StringUtils.normalizeSpace(deliveryDate.getText()).matches("Previsão de entrega: \\d{2} de [a-zç]+ de \\d{4}"));
        }

        //Status pedido
        if (cart.isDeviceCart()) { //Aparelhos (modal)
            WebElement statusModal = driverWeb.findByXpath("//*[@class='mdn-Row']/div[1]/div[2]/div/div");

            //Abre modal
            driverWeb.javaScriptClick(driverWeb.findByXpath("//*[@class='mdn-Row']/div[1]/div[2]/button"));
            driverWeb.waitElementVisible(statusModal, 2);
            driverWeb.actionPause(1000);

            //Título modal
            validateElementText("Status do pedido", statusModal.findElement(By.xpath(".//h1")));

            //Status
            List<WebElement> statusList = driverWeb.findElements("//*[@class='mdn-Row']/div[1]/div[2]/div/div/div[3]/div//p", "xpath");
            validateStatus(statusList, cart);

            //Fecha modal
            driverWeb.javaScriptClick(statusModal.findElement(By.tagName("button")));
            driverWeb.waitElementInvisible(statusModal, 2);
        } else { //Planos
            List<WebElement> statusListPlan = driverWeb.findElements("//*[@id='txt-sucesso-pedido']/../following-sibling::div[1]//*[contains(@class, 'mdn-Heading')]", "xpath");
            validateStatus(statusListPlan, cart);
        }

        //Plano escolhido
        if (!cart.isDeviceCart() && processType != PORTABILITY && processType != ProcessType.ACCESSORY) {
            validateElementText(String.format("Sua solicitação para adquirir o %s foi recebida com sucesso!", cart.getPlan().getName()), driverWeb.findById("txt-sucesso-plano"));
        } else if (processType == PORTABILITY) {
            validateElementText("Sua solicitação para trazer seu número para Claro foi recebida com sucesso!", driverWeb.findById("txt-sucesso-plano"));
        }


        //Número pedido
        WebElement orderNumber = driverWeb.findById("txt-pedido");
        assertTrue(orderNumber.isDisplayed());

        // Informações do pedido ########################################################
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
        validateElementText("Nome " + cart.getUser().getName(), driverWeb.findById("msg-informacao-nome"));

        //CPF
        WebElement cpf = driverWeb.findById("msg-informacao-cpf");
        String formattedCpf = cart.getUser().getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        driverWeb.javaScriptScrollTo(cpf);
        validateElementText("CPF " + formattedCpf, cpf);

        //Forma de pagamento
        String paymentMode = switch (cart.getEntry(cart.getPlan().getCode()).getPaymentMode()) {
            case TICKET -> "Boleto";
            case DEBITCARD -> "Débito automático";
            case CREDITCARD -> "Crédito";
            default -> "error";
        };
        validateElementText("Forma de pagamento " + paymentMode, driverWeb.findById("msg-informacao-pagamento"));

        if (cart.getProcessType() != ProcessType.ACCESSORY) {
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

        // Endereço de entrega ##########################################################
        //Abre Accordion
        driverWeb.javaScriptClick(driverWeb.findByXpath("//*[@id='acr-expandir-endereco']/.."));
        driverWeb.actionPause(1000);

        if (processType == ACQUISITION || processType == PORTABILITY) { //TODO Para fluxos de base atualmente não há de onde obter os dados de endereço
            //Endereço de Entrega
            CartOrder.Address addr = cart.getDeliveryAddress();
            String building = (addr.getBuilding() == null) || (addr.getBuilding().isEmpty()) ? "" : " - " + addr.getBuilding();
            String address = String.format("Endereço de entrega %s, %s%s - %s - %s %s CEP %s", addr.getStreetname(), addr.getStreetnumber(), building, addr.getNeighbourhood(), addr.getTown(), addr.getStateCode(), addr.getPostalcode().replaceAll("(\\d{5})(\\d{3})", "$1-$2"));

            WebElement deliveryText = driverWeb.findByXpath("//*[@id='txt-end-entrega']/..");
            driverWeb.javaScriptScrollTo(deliveryText);
            validateElementText(address, deliveryText);
        }
    }

    public void validarPaginaParabens(CartOrder cart) {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
        driverWeb.actionPause(2000);

        if (cart.getProcessType() != PORTABILITY) {
            validarDados(cart);
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