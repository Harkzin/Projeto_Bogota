package web.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.cucumber.java.pt.E;

import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import web.models.CartOrder;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.Duration.*;
import static web.models.CartOrder.Status.OrderProcess.*;
import static web.support.api.RestAPI.*;
import static web.support.utils.Constants.ChipType.*;
import static web.support.utils.Constants.GradePlan.*;
import static web.support.utils.Constants.StandardPaymentMode.*;

public class ValidateOrderSteps {

    private final CartOrder cart;

    @Autowired
    public ValidateOrderSteps(CartOrder cart) {
        this.cart = cart;
    }

    private CartOrder order;

    final int VALIDATE_ORDER_TIMEOUT = 600;
    final int GET_ORDER_UPDATE_INTERVAL = 30;

    private final Logger logger = LoggerFactory.getLogger(ValidateOrderSteps.class);

    private List<ProcessTaskLog> orderProcessRef;
    private int currentActionRefIndex = 0;

    @E("os dados do pedido estão corretos")
    public void validateOrder() {
        FluentWait<CartOrder> wait = new FluentWait<>(order)
                .withTimeout(ofSeconds(VALIDATE_ORDER_TIMEOUT))
                .pollingEvery(ofSeconds(GET_ORDER_UPDATE_INTERVAL));

        orderProcessRef = getExpectedOrderProcess();
        logger.debug("Expected order-process:{}", orderProcessRef.stream().map(p -> String.format("\nactionId: %s | returnCode: %s", p.getActionId(), p.getReturnCode())).collect(Collectors.joining()));

        logger.info("------------------------------------------------------------------------");
        logger.info("Validate order: START");
        logger.info("------------------------------------------------------------------------");

        wait.until(o -> {
            order = refreshOrder();
            logger.info("Current order status: {} | Next update in {}s", order.getStatus(), GET_ORDER_UPDATE_INTERVAL);

            //order-process
            validateOrderProcess();

            //TODO chamar novas validações aqui

            return order.getStatus().equals("AWAITING_INVOICE");
        });

        logger.info("------------------------------------------------------------------------");
        logger.info("Validate order: END");
        logger.info("------------------------------------------------------------------------");
        logger.info("Final order status: {}", order.getStatus());
    }

    private CartOrder refreshOrder() {
        final int ORDER_STATUS_REQUEST_ATTEMPT_INTERVAL = 30;
        final int ORDER_STATUS_REQUEST_MAX_RETRY = 3;

        final JsonMapper jsonMapper = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .build();

        HttpResponse<String> orderStatusResponse;

        int attemptNumber = 1;
        while (true) {
            logger.debug("REQUEST_ORDER_STATUS | Attempt: {}/{}", attemptNumber, ORDER_STATUS_REQUEST_MAX_RETRY);
            orderStatusResponse = orderStatusRequest(cart.getCode());

            if (orderStatusResponse.statusCode() != 200) {
                logger.debug("RESPONSE_ORDER_STATUS - Error | Uri: {} | HttpCode: {} | Body:\n{}", orderStatusResponse.uri(), orderStatusResponse.statusCode(), orderStatusResponse.body());

                if (attemptNumber < ORDER_STATUS_REQUEST_MAX_RETRY) {
                    logger.debug("Retrying in {} seconds", ORDER_STATUS_REQUEST_ATTEMPT_INTERVAL);
                } else {
                    logger.error("RESPONSE_ORDER_STATUS - Max retry attempt reached");
                    throw new RuntimeException("RESPONSE_ORDER_STATUS - Unexpected response");
                }

                attemptNumber++;

                Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
                try {
                    sleeper.sleep(ofSeconds(ORDER_STATUS_REQUEST_ATTEMPT_INTERVAL));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                logger.debug("RESPONSE_ORDER_STATUS | OK");
                break;
            }
        }

        try {
            return jsonMapper.readValue(orderStatusResponse.body(), CartOrder.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ProcessTaskLog> getExpectedOrderProcess() {
        List<ProcessTaskLog> orderProcess = new ArrayList<>();

        orderProcess.add(new ProcessTaskLog("createOrder", "OK"));
        //orderProcess.add(new ProcessTaskLog("sendSMSEmailOrderConfirmed", "OK")); //TODO

        //checkHasCredit
        if (!cart.isDeviceCart()) {
            orderProcess.add(new ProcessTaskLog("checkHasCredit", "OTHER_PAYMENT"));
        } else {
            switch (cart.getEntry(cart.getDevice().getCode()).getPaymentMode()) {
                case CREDITCARD -> orderProcess.addAll(List.of(
                        new ProcessTaskLog("checkHasCredit","CREDIT"),
                        new ProcessTaskLog("verifyAuthenticationPayment", "SUCCEEDED"),
                        new ProcessTaskLog("authorizationPayment", "OK"),
                        new ProcessTaskLog("paymentRequestFraudStatus", "OK")
                ));
                case PIX -> orderProcess.addAll(List.of(
                        new ProcessTaskLog("checkHasCredit","PIX"),
                        new ProcessTaskLog("verifyPixPayment","WAIT"),
                        new ProcessTaskLog("waitingPixPayment","OK")
                        //TODO
                ));
                case VOUCHER, CLAROCLUBE -> orderProcess.add(new ProcessTaskLog("checkHasCredit","OTHER_PAYMENT"));
            }
        }

        //purchaseFlowWithoutIntegration
        orderProcess.add(new ProcessTaskLog("purchaseFlowWithoutIntegration", "REGULAR"));

        //multiCombo
        String multiComboReturn;
        if (cart.isComboFlow() && !cart.isDeviceCart()) {
            multiComboReturn = "MULTICOMBO";
        } else if (cart.isComboFlow() && cart.isDeviceCart()) {
            multiComboReturn = "MULTICOMBOWITHDEVICE";
        } else if (cart.isEasyControlFlow()) {
            multiComboReturn = "EASYCONTROL";
        } else if (cart.isDeviceCart() && cart.getEntry(cart.getDevice().getCode()).getPaymentMode() == PIX) {
            multiComboReturn = "PIX";
        } else {
            multiComboReturn = "NOMULTICOMBO";
        }
        orderProcess.add(new ProcessTaskLog("multiCombo", multiComboReturn));

        if (multiComboReturn.matches("NOMULTICOMBO|MULTICOMBOWITHDEVICE|PIX")) {
            //wentThroughFraudAnalysis (clearSale)
            if (!multiComboReturn.equals("PIX")) {
                if (cart.getGradePlan() == DOWNGRADE) { //TODO
                    orderProcess.add(new ProcessTaskLog("wentThroughFraudAnalysis", "SUCCEEDED"));
                } else {
                    orderProcess.addAll(List.of(
                            new ProcessTaskLog("wentThroughFraudAnalysis", "FAILED"),
                            new ProcessTaskLog("clearSaleAuthentication", "OK"),
                            new ProcessTaskLog("clearSaleRequest", "OK"),
                            new ProcessTaskLog("waitClearSaleNotification", "OK"),
                            new ProcessTaskLog("clearSaleRequestFraudStatus", "APA_APP")
                    ));
                }
            }

            //identificarDadosFaturamento (STEP_5)
            orderProcess.addAll(List.of(
                    new ProcessTaskLog("identificarDadosFaturamento", "OK"),
                    new ProcessTaskLog("checkErrorStepFive", "SKIP_VALIDATION")
            ));
        }

        //generateSalesOrdersAuthentication
        List<ProcessTaskLog> generateSalesOrdersAuthentication = List.of(
                new ProcessTaskLog("generateSalesOrdersAuthentication", "SUCCEEDED"),
                new ProcessTaskLog("generateSalesOrders", "SUCCEEDED"),
                new ProcessTaskLog("awaitGenerateSalesOrdersReturn", "OK"),
                new ProcessTaskLog("checkMessageType", "WAIT")
        );

        //gerarPedidoVenda (STEP_6)
        String gerarPedidoVendaReturn = "";
        if (!cart.isDeviceCart()) {
            switch (cart.getProcessType()) {
                case ACQUISITION -> {
                    gerarPedidoVendaReturn = "ACQUISITION";
                    orderProcess.add(new ProcessTaskLog("gerarPedidoVenda", gerarPedidoVendaReturn));
                }
                case MIGRATE, EXCHANGE, EXCHANGE_PROMO -> {
                    gerarPedidoVendaReturn = "MIGRATION";
                    orderProcess.addAll(List.of(
                            new ProcessTaskLog("gerarPedidoVenda", gerarPedidoVendaReturn),
                            new ProcessTaskLog("updateOrderStatusActivationProceeding", "OK"),
                            new ProcessTaskLog("awaitMigrationReturn", "OK"),
                            new ProcessTaskLog("receiveOrderStatus", "SUCCEEDED"),
                            new ProcessTaskLog("updateOrderStatusActMigCompleted", "OK")
                            //new ProcessTaskLog("sendSMSCompletionEmail", "OK") //TODO
                            //END
                    ));

                    return orderProcess;
                }
                case PORTABILITY -> {
                    gerarPedidoVendaReturn = "PORTABILITY";
                    orderProcess.addAll(List.of(
                            new ProcessTaskLog("gerarPedidoVenda", gerarPedidoVendaReturn),
                            new ProcessTaskLog("awaitSimplifiedActivation", "OK"),
                            new ProcessTaskLog("simplifiedActivation", "OK")
                    ));
                }
            }
        } else {
            gerarPedidoVendaReturn = "DEVICE";
            orderProcess.add(new ProcessTaskLog("gerarPedidoVenda", "DEVICE"));
        }

        if (gerarPedidoVendaReturn.matches("ACQUISITION|PORTABILITY")) {
            //verifyEsimFlow
            if (cart.getClaroChip().getChipType() == ESIM) {
                orderProcess.addAll(List.of(
                        new ProcessTaskLog("verifyEsimFlow", "OK")
                        //new ProcessTaskLog("sendApprovedOrderEmail", "WAIT") //TODO
                ));
            } else {
                orderProcess.add(new ProcessTaskLog("verifyEsimFlow", "NOK"));
                orderProcess.addAll(generateSalesOrdersAuthentication);
            }
        } else { //DEVICE
            //checkPreSale
            if (cart.isPreSale()) {
                orderProcess.addAll(List.of(
                        new ProcessTaskLog("checkPreSale", "OK")
                        //new ProcessTaskLog("sendPreSaleEmail", "OK") //TODO
                ));
            } else {
                orderProcess.add(new ProcessTaskLog("checkPreSale", "NOK"));
            }

            //claroClubeValidation
            String claroClubeValidationReturn;
            if (!cart.getClaroClube().isUsed()) {
                if (cart.getEntry(cart.getDevice().getCode()).getPaymentMode() == PIX) {
                    claroClubeValidationReturn = "PIX";
                    orderProcess.add(new ProcessTaskLog("claroClubeValidation", claroClubeValidationReturn));
                } else {
                    claroClubeValidationReturn = "OK";
                    orderProcess.add(new ProcessTaskLog("claroClubeValidation", claroClubeValidationReturn));
                }
            } else {
                claroClubeValidationReturn = "NOK";
                orderProcess.add(new ProcessTaskLog("claroClubeValidation", claroClubeValidationReturn));
            }

            //redeemClaroClubePoints //TODO clube 100% = ?
            String redeemClaroClubePointsReturn;
            if (claroClubeValidationReturn.equals("NOK") && cart.getEntry(cart.getDevice().getCode()).getPaymentMode() == PIX) {
                redeemClaroClubePointsReturn = "SUCCEEDED_PAID_FULLY";
                orderProcess.add(new ProcessTaskLog("redeemClaroClubePoints", redeemClaroClubePointsReturn));
            } else {
                redeemClaroClubePointsReturn = "SUCCEEDED_PAID_PARTIALLY";
                orderProcess.add(new ProcessTaskLog("redeemClaroClubePoints", redeemClaroClubePointsReturn));
            }

            //paymentVerification
            if (claroClubeValidationReturn.equals("OK") || redeemClaroClubePointsReturn.equals("SUCCEEDED_PAID_PARTIALLY")) {
                if (cart.getEntry(cart.getDevice().getCode()).getPaymentMode() == CREDITCARD) {
                    orderProcess.addAll(List.of(
                            new ProcessTaskLog("paymentVerification", "CREDIT"),
                            new ProcessTaskLog("performPaymentAuthentication", "SUCCEEDED"),
                            new ProcessTaskLog("performPayment", "SUCCEEDED")
                    ));
                } else if (cart.getEntry(cart.getDevice().getCode()).getPaymentMode() == VOUCHER) {
                    orderProcess.add(new ProcessTaskLog("paymentVerification", "VOUCHER"));
                }
            }

            //identifyFlowAfterPedidoVenda //TODO
        }

        return orderProcess;
    }

    private void validateOrderProcess() {
        List<ProcessTaskLog> orderProcess = order.getOrderProcess().stream().filter(op -> op.getProcessDefinitionName().equals("order-process")).findFirst().orElseThrow().getTaskLogs();

        while (currentActionRefIndex < orderProcess.size() && currentActionRefIndex < orderProcessRef.size()) {
            ProcessTaskLog currentRefAction = orderProcessRef.get(currentActionRefIndex);
            List<ProcessTaskLog> currentOrderActionList = orderProcess.stream().skip(currentActionRefIndex).filter(action -> action.getActionId().equals(currentRefAction.getActionId())).toList();
            int size = currentOrderActionList.size();

            if (size > 0) {
                ProcessTaskLog currentOrderAction = currentOrderActionList.get(size - 1);
                logger.debug("Comparing returnCode from actionId: {} | Expected: {} - Actual: {}", currentRefAction.getActionId(), currentRefAction.getReturnCode(), currentOrderAction.getReturnCode());

                if (currentOrderAction.getReturnCode().equals(currentRefAction.getReturnCode())) {
                    currentActionRefIndex++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }
}