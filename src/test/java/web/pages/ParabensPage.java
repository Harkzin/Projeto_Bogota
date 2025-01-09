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
import static web.support.utils.Constants.ClaroJourneyInformation.CONTROLE_FACIL;
import static web.support.utils.Constants.ProcessType.*;
import static web.support.utils.Constants.StandardPaymentMode.*;
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

        List<String> statusListRef;

        switch (cart.getProcessType()) {
            case ACQUISITION -> {
                if (cart.isDeviceCart()) {
                    statusListRef = (cart.getEntry(cart.getDevice().getCode()).getPaymentMode()) == PIX ? ACQUISITION_DEVICE_PIX.getStatusList() : ACQUISITION_DEVICE.getStatusList();
                } else {
                    statusListRef = (deliveryMode == CONVENTIONAL) ? ACQUISITION_PLAN.getStatusList() : ACQUISITION_PLAN_EXPRESS.getStatusList();
                }
            }
            case MIGRATE, EXCHANGE, EXCHANGE_PROMO, APARELHO_TROCA_APARELHO -> statusListRef = isDeviceCart ? MIGRATE_EXCHANGE_DEVICE.getStatusList() : MIGRATE_EXCHANGE_PLAN.getStatusList();
            case PORTABILITY -> statusListRef = isDeviceCart ? PORTABILITY_DEVICE.getStatusList() : PORTABILITY_PLAN.getStatusList();
            case ACCESSORY -> statusListRef = StatusSuccessPage.ACCESSORY.getStatusList();
            case ACCESSORY_PIX -> statusListRef = StatusSuccessPage.ACCESSORY_PIX.getStatusList();
            default -> throw new RuntimeException("Unexpected processType value");
        }

        IntStream.range(0, statusListRef.size()).forEachOrdered(i ->
                validateElementText(statusListRef.get(i), statusList.get(i))
        );
    }

    public void validarDados(CartOrder cart) {
        ProcessType processType = cart.getProcessType();

        //Valida mensagem esim
        //TODO mudar para id apos mapeamento
        if (cart.getClaroChip().getChipType() == ESIM) {
            if (cart.isDeviceCart()) {
                validateElementText("Assim que o seu pedido for entregue, você receberá o código do eSIM por e-mail. Além disso, você terá acesso às instruções passo a passo para habilitar o eSIM em Entrar > Acompanhar Pedidos eSIM > Gerenciar eSIM",
                        driverWeb.findByXpath("/html/body/main/div[3]/div/div[2]/div/div/div/div/div[1]/div[5]/div[2]/p"));
            } else {
                validateElementText("Assim que o seu pedido for aprovado, você receberá o código do eSIM por e-mail. Além disso, na página Acompanhe seu Pedido, você terá acesso às instruções passo a passo para habilitar o eSIM.",
                        driverWeb.findByXpath("/html/body/main/div[3]/div/div[2]/div/div/div/div/div[1]/div[4]/div[2]/p"));
            }
        }

        //Nome (Parabéns, {nome-cliente}) //TODO Para fluxos de base atualmente não há de onde obter o nome do cliente
        if (processType == ACQUISITION || processType == PORTABILITY || processType == ProcessType.ACCESSORY) {
            String customerName = StringUtils.capitalize(cart.getUser().getName().split(" ")[0].toLowerCase());
            String successText = String.format("Parabéns, %s!", customerName);
            validateElementText(successText, driverWeb.findById("txt-parabens"));
        }

        //Previsão de entrega (Aparelhos e Acessórios)
        if (cart.isDeviceCart() || processType == ProcessType.ACCESSORY) {
            WebElement deliveryDate = driverWeb.findByXpath("//*[@id='txt-parabens']/following-sibling::div[2]/p");
            assertTrue(deliveryDate.isDisplayed());
            assertTrue(StringUtils.normalizeSpace(deliveryDate.getText()).matches("Previsão de entrega: \\d{2} de [a-zç]+ de \\d{4}"));
        }

        //Status pedido
        if (cart.isDeviceCart() || processType == ProcessType.ACCESSORY) { //Aparelhos e Acessórios (modal)
            WebElement statusModal = driverWeb.findById("status-modal");

            //Abre modal
            driverWeb.javaScriptClick(driverWeb.findById("btn-open-status-modal"));
            driverWeb.waitElementVisible(statusModal, 2);
            driverWeb.actionPause(1000);

            //Título modal
            validateElementText("Status do pedido", statusModal.findElement(By.xpath(".//h1")));

            //Status
            List<WebElement> statusList = driverWeb.findElements("//*[@class='mdn-Row']/div[1]/div[2]/div/div/div[3]/div//p", "xpath");
            validateStatus(statusList, cart);

            //Fecha modal
            driverWeb.javaScriptClick(driverWeb.findById("btn-close-status-modal"));
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
        } else if (cart.isDeviceCart() || processType == ProcessType.ACCESSORY) {
            validateElementText("Sua solicitação foi recebida com sucesso!", driverWeb.findById("txt-sucesso-plano"));
        }

        //Número pedido
        WebElement orderNumber = driverWeb.findById("txt-pedido");
        assertTrue(orderNumber.isDisplayed());

        // Informações do pedido ########################################################
        //Abre Accordion - Informações do pedido
        driverWeb.javaScriptClick(driverWeb.findById("acr-expandir-informacao"));
        driverWeb.actionPause(1000);

        //Número do pedido
        String orderNumberWithZeros = StringUtils.leftPad(orderNumber.getText(), 12, "0");
        validateElementText("Número do pedido " + orderNumberWithZeros, driverWeb.findById("txt-numero-pedido"));
        cart.setCode(orderNumberWithZeros);

        //TODO não aparece em S6
        //Número de contato
        if (cart.getProcessType() == ProcessType.ACCESSORY) {
            String formattedTelephone = cart.getUser().getTelephone().replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
            validateElementText("Número de contato " + formattedTelephone, driverWeb.findById("msg-informacao-contato"));
        }

        //Nome //TODO Para fluxos de base atualmente não há de onde obter o nome do cliente
        if (cart.getProcessType() == ACQUISITION || cart.getProcessType() == PORTABILITY || cart.getProcessType() == ProcessType.ACCESSORY) {
            validateElementText("Nome " + cart.getUser().getName(), driverWeb.findById("msg-informacao-nome"));
        }

        //CPF
        WebElement cpf = driverWeb.findById("msg-informacao-cpf");
        String formattedCpf = cart.getUser().getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        driverWeb.javaScriptScrollTo(cpf);
        validateElementText("CPF " + formattedCpf, cpf);

        //Forma de pagamento
        if (processType != APARELHO_TROCA_APARELHO && processType != ProcessType.ACCESSORY) { //TODO Método pagamento acessório
            String paymentMode = switch (cart.getEntry(cart.getPlan().getCode()).getPaymentMode()) {
                case TICKET -> "Boleto";
                case DEBITCARD -> "Débito";
                case CREDITCARD -> "Crédito";
                default -> throw new RuntimeException("Unexpected paymentMode value");
            };
            validateElementText("Forma de pagamento " + paymentMode, driverWeb.findById("msg-informacao-pagamento"));
        }

        if (cart.getProcessType() != ProcessType.ACCESSORY) {
            //Número de Protocolo
            validateElementText("Número de Protocolo Aguarde enquanto o seu número de Protocolo está sendo gerado", driverWeb.findById("txt-numero-protocolo"));

            //Plano
            String nameRef;
            if (cart.getProcessType() == APARELHO_TROCA_APARELHO) {
                nameRef = cart.getUser().getClaroSubscription().getClaroPlanName(); //Fluxo Aparelhos - Manter o Plano
            } else if (cart.getPromotion().isRentabilization()) {
                nameRef = cart.getPromotion().getName(); //Fluxo rentab
            } else {
                nameRef = cart.getPlan().getName(); //Fluxo normal
            }
            validateElementText("Plano " + nameRef, driverWeb.findById("msg-informacao-plano"));

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
        driverWeb.javaScriptClick(driverWeb.findById("acr-expandir-endereco"));
        driverWeb.actionPause(1000);

        if (processType == ACQUISITION || processType == PORTABILITY) { //TODO Para fluxos de base atualmente não há de onde obter os dados de endereço
            //Endereço de Entrega
            CartOrder.Address addr = cart.getDeliveryAddress();
            String building = (addr.getBuilding() == null) || (addr.getBuilding().isEmpty()) ? "" : " - " + addr.getBuilding();
            String address = String.format("Endereço de entrega %s, %s%s - %s - %s %s CEP %s", addr.getStreetname(), addr.getStreetnumber(), building, addr.getNeighbourhood(), addr.getTown(), addr.getStateCode(), addr.getPostalcode().replaceAll("(\\d{5})(\\d{3})", "$1-$2"));

            WebElement deliveryText = driverWeb.findById("txt-end-entrega");
            driverWeb.javaScriptScrollTo(deliveryText);
            validateElementText(address, deliveryText);

            if (!cart.getJourneyInformation().equals(CONTROLE_FACIL)) {
                WebElement deliveryTypeText = driverWeb.findById("txt-tipo-entrega");
                validateElementText(cart.getDeliveryMode().name(), deliveryTypeText);
            }
        }
    }

    public void validarPaginaParabens(CartOrder cart) {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
        driverWeb.actionPause(2000);

        if (cart.getProcessType() != PORTABILITY) {
            validarDados(cart);
        }
    }

    public void validarPaginaParabensPix(CartOrder cart) {
        driverWeb.waitPageLoad("/checkout/orderConfirmation", 60);
        driverWeb.actionPause(2000);

        WebElement qrCodePix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[2]/img");
        WebElement temporizadorPix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[1]/ul/li[1]/div[3]/div[1]/p");
        WebElement copiarCodigoPix = driverWeb.findByXpath("//*[@id='pix-payment-instructions']/div[2]/div[1]/ul/li[1]/button");
        WebElement msgSucesso = driverWeb.findById("txt-solicitacao-sucesso");
        WebElement numeroPedido = driverWeb.findByXpath("//*[@id='numero-pedido']//..");
        WebElement valorTotal = driverWeb.findByXpath("//*[@*='valor-total']/..");
        WebElement statusButton = driverWeb.findByXpath("//*[@class='mdn-Container']/div[1]/button");
        List<WebElement> statusList = driverWeb.findElements("//*[contains(@class, 'c_linha-do-tempo-text')]", "xpath");

        validateElementText("Solicitação recebida com sucesso!", msgSucesso);

        //Status pedido
        driverWeb.javaScriptClick(statusButton);
        validateStatus(statusList, cart);

        //Pix
        validateElementText(String.format("Pague R$ %s por Pix para garantir sua compra", formatPrice(cart.getEntry(cart.getDevice().getCode()).getTotalPrice())), valorTotal);
        driverWeb.waitElementVisible(temporizadorPix, 10);
        assertTrue(qrCodePix.isDisplayed());
        assertTrue(copiarCodigoPix.isDisplayed());
    }

    public void clicarOkEntendiModal() {
        driverWeb.javaScriptClick("btn-entendi-modal-abr", "id");
    }
}