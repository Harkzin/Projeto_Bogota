package web.steps;

import mock.ativacao.UpdateOrderRequest;
import mock.sap.UpdateOrderSapRequest;
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
import web.models.CartOrder.ClaroSapResponse.SapStatusHistory;
import web.support.utils.Constants;

import java.net.http.HttpResponse;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static java.time.Duration.*;
import static org.junit.Assert.*;
import static web.models.CartOrder.Essential.*;
import static web.models.CartOrder.PositionsAndPrices.*;
import static web.models.CartOrder.Status.*;
import static web.models.CartOrder.Status.OrderProcess.*;
import static web.support.api.RestAPI.*;
import static web.support.utils.Constants.ChipType.*;
import static web.support.utils.Constants.GradePlan.*;
import static web.support.utils.Constants.ProcessType.*;
import static web.support.utils.Constants.StandardPaymentMode.*;
import static web.support.utils.Constants.ZoneDeliveryMode.*;

public class ValidateOrderSteps {

    private final CartOrder cart;

    @Autowired
    public ValidateOrderSteps(CartOrder cart) {
        this.cart = cart;
    }

    private CartOrder order;
    private CartOrder childOrder;

    final int VALIDATE_ORDER_TIMEOUT = 1200;
    final int GET_ORDER_UPDATE_INTERVAL = 30;

    private final Logger logger = LoggerFactory.getLogger(ValidateOrderSteps.class);

    private List<ProcessTaskLog> orderProcessRef;
    private int currentActionRefIndex = 0;

    private boolean sapFlow = false;

    @E("os dados do pedido estão corretos")
    public void validateOrder() {
        Clock clock = Clock.systemDefaultZone();

        FluentWait<CartOrder> wait = new FluentWait<>(order, clock, Sleeper.SYSTEM_SLEEPER)
                .withTimeout(ofSeconds(VALIDATE_ORDER_TIMEOUT))
                .pollingEvery(ofSeconds(GET_ORDER_UPDATE_INTERVAL));

        String finalStatus = getFinalStatus();
        logger.info("------------------------------------------------------------------------");
        logger.info("Expected order final status: {}", finalStatus);

        logger.info("------------------------------------------------------------------------");
        orderProcessRef = getExpectedOrderProcess();
        logger.debug("Expected order-process:{}", orderProcessRef.stream().map(p -> String.format("\nactionId: %s | returnCode: %s", p.getActionId(), p.getReturnCode())).collect(Collectors.joining()));

        logger.info("------------------------------------------------------------------------");
        logger.info("Validate order: WAITING");

        Instant start = clock.instant().plusSeconds(44);

        FluentWait<Clock> waitBeforeStart = new FluentWait<>(clock, clock, Sleeper.SYSTEM_SLEEPER)
                .pollingEvery(ofSeconds(45));
        waitBeforeStart.until(c -> clock.instant().isAfter(start));

        logger.info("------------------------------------------------------------------------");
        logger.info("Validate order: START");
        logger.info("------------------------------------------------------------------------");

        sapFlow = isSapFlow();
        Instant end = clock.instant().plusSeconds(VALIDATE_ORDER_TIMEOUT);

        //#############################################################

        wait.until(o -> {
            Duration remainingTime = ofSeconds(clock.instant().until(end, ChronoUnit.SECONDS));
            String remainingTimeStr = String.format("%dm%ds", remainingTime.toMinutesPart(), remainingTime.toSecondsPart());

            order = refreshOrder(cart.getCode());
            if (cart.getProcessType() != ACCESSORY) {
                childOrder = refreshOrder(order.getChildren());
            }

            logger.info("Current order status: {} | Next update in: {}s | Remaining time until timeout: {}", order.getStatus(), GET_ORDER_UPDATE_INTERVAL, remainingTimeStr);

            //------------------------------

            //Essential
            //user
            validateUser(order.getUser());
            validateUser(childOrder.getUser());

            //telephone
            if (cart.getProcessType() == ACQUISITION && checkOrderProcessAction("gerarPedidoVenda", "ACQUISITION")) {
                assertNotNull(order.getTelephone());
                assertNotNull(childOrder.getTelephone());
            } else {
                assertEquals(cart.getTelephone(), order.getTelephone());
                assertEquals(cart.getTelephone(), childOrder.getTelephone());
            }

            //processType
            assertEquals(cart.getProcessType(), order.getProcessType());

            //------------------------------

            //Positions and Prices
            validateEntries();
            validateTotalPrice();
            validateOrderProcess();

            //------------------------------

            //Administration
            validateAdministration();

            //------------------------------

            //TODO chamar novas validações aqui

            //------------------------------
            //Mocks
            if (cart.getProcessType() == PORTABILITY && order.getEventId() != null && (order.getEventId().matches("1000|1010|1050") || (order.getEventId().equals("1015") && cart.getClaroSapResponse().getStatus().equals("900")))) {
                mockPortability();
            }

            if (sapFlow && order.getOrderProcess().stream().filter(op -> op.getProcessDefinitionName().equals("order-process")).findFirst().orElseThrow().getTaskLogs().stream().anyMatch(a -> a.getActionId().equals("checkMessageType"))) {
                mockSapEcc();
            }

            if (cart.getProcessType() == PORTABILITY && order.getEventId() != null && order.getEventId().matches("1050|1060")) { //TODO MIGRA, ACQUISITION, ETC
                mockSimplifiedActivation();
            }


            //------------------------------

            return order.getStatus().equals(finalStatus);
        });

        //#############################################################

        logger.info("------------------------------------------------------------------------");
        logger.info("Validate order: END");
        logger.info("------------------------------------------------------------------------");
        logger.info("Final order status: {}", order.getStatus());
    }

    //#############################################################################################

    private CartOrder refreshOrder(String code) {
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
            orderStatusResponse = orderStatusRequest(code);

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
                        new ProcessTaskLog("checkHasCredit", "CREDIT"),
                        new ProcessTaskLog("verifyAuthenticationPayment", "SUCCEEDED"),
                        new ProcessTaskLog("authorizationPayment", "OK"),
                        new ProcessTaskLog("paymentRequestFraudStatus", "OK")
                ));
                case PIX -> orderProcess.addAll(List.of(
                        new ProcessTaskLog("checkHasCredit", "PIX"),
                        new ProcessTaskLog("verifyPixPayment", "WAIT"),
                        new ProcessTaskLog("waitingPixPayment", "OK")
                        //TODO
                ));
                case VOUCHER, CLAROCLUBE -> orderProcess.add(new ProcessTaskLog("checkHasCredit", "OTHER_PAYMENT"));
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
            String redeemClaroClubePointsReturn = "";
            if (claroClubeValidationReturn.equals("NOK") && cart.getEntry(cart.getDevice().getCode()).getPaymentMode() == PIX) {
                redeemClaroClubePointsReturn = "SUCCEEDED_PAID_FULLY";
                orderProcess.add(new ProcessTaskLog("redeemClaroClubePoints", redeemClaroClubePointsReturn));
            } else if (claroClubeValidationReturn.equals("NOK") && cart.getEntry(cart.getDevice().getCode()).getPaymentMode() != PIX) {
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

            //identifyFlowAfterPedidoVenda
            if (cart.getProcessType() == PORTABILITY) {
                orderProcess.addAll(List.of(
                        new ProcessTaskLog("identifyFlowAfterPedidoVenda", "PORTABILITY"),
                        new ProcessTaskLog("awaitSimplifiedActivation", "OK"), //TODO
                        new ProcessTaskLog("simplifiedActivation", "OK_ABR")
                ));
            } else {
                orderProcess.add(new ProcessTaskLog("identifyFlowAfterPedidoVenda", "SAP"));
            }
        }

        //generateSalesOrdersAuthentication
        orderProcess.addAll(List.of(
                new ProcessTaskLog("generateSalesOrdersAuthentication", "SUCCEEDED"),
                new ProcessTaskLog("generateSalesOrders", "SUCCEEDED"),
                new ProcessTaskLog("awaitGenerateSalesOrdersReturn", "OK"),
                new ProcessTaskLog("checkMessageType", "WAIT")
        ));

        return orderProcess;
    }

    private String getFinalStatus() {
        return switch (cart.getProcessType()) {
            case ACQUISITION, PORTABILITY, APARELHO_TROCA_APARELHO, ACCESSORY, ACCESSORY_PIX -> "ORDER_COMPLETED";
            case MIGRATE, EXCHANGE -> cart.isDeviceCart() ? "ORDER_COMPLETED" : "Activation_migration_completed";
            case EXCHANGE_PROMO -> "Activation_migration_completed";
        };
    }

    //#############################################################################################

    private OrderProcess getOrderProcess(String processDefinitionName) {
        return order.getOrderProcess().stream().filter(op -> op.getProcessDefinitionName().equals(processDefinitionName)).findFirst().orElseThrow();
    }

    private boolean checkOrderProcessAction(String actionId, String returnCodeRegex) {
        return getOrderProcess("order-process").getTaskLogs().stream().anyMatch(a -> a.getActionId().equals(actionId) && a.getReturnCode().matches(returnCodeRegex));
    }

    private void validateOrderProcess() {
        OrderProcess orderProcess = getOrderProcess("order-process");
        List<ProcessTaskLog> orderProcessTaskLogs = orderProcess.getTaskLogs();

        assertNotEquals("ERROR", orderProcess.getState());
        assertNotEquals("FAILED", orderProcess.getState());

        while (currentActionRefIndex < orderProcessTaskLogs.size() && currentActionRefIndex < orderProcessRef.size()) {
            ProcessTaskLog currentRefAction = orderProcessRef.get(currentActionRefIndex);
            List<ProcessTaskLog> currentOrderActionList = orderProcessTaskLogs.stream().skip(currentActionRefIndex).filter(action -> action.getActionId().equals(currentRefAction.getActionId())).toList();
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

    //#############################################################################################

    private void validateUser(Customer orderUser) {
        Customer cartUser = cart.getUser();

        assertEquals(cartUser.getName(), orderUser.getName());
        assertEquals(cartUser.getName(), orderUser.getDisplayName());
        assertEquals(cartUser.getParentfullname(), orderUser.getParentfullname());

        //claroTelephone
        if (cart.getProcessType() != ACQUISITION) {
            assertEquals(cartUser.getClaroTelephone(), orderUser.getClaroTelephone());
        } else if (checkOrderProcessAction("gerarPedidoVenda", "ACQUISITION")) {
            assertNotNull(orderUser.getClaroTelephone());
        }

        assertEquals(cartUser.getTelephone(), orderUser.getTelephone());

        //claroProvisionalTelephone
        if (cart.getProcessType() == PORTABILITY && checkOrderProcessAction("gerarPedidoVenda", "PORTABILITY")) {
            assertNotNull(orderUser.getClaroProvisionalTelephone());
        }

        //birthdate
        ZonedDateTime cartUserBirthdate = ZonedDateTime.of(LocalDate.parse(cartUser.getBirthdate(), DateTimeFormatter.ofPattern("ddMMyyyy")), LocalTime.of(0, 0, 0), ZoneId.of("America/Sao_Paulo"));
        ZonedDateTime orderUserBirthdate = ZonedDateTime.parse(orderUser.getBirthdate(), DateTimeFormatter.ofPattern("eee MMM dd HH:mm:ss z yyyy").withLocale(Locale.ENGLISH));
        assertEquals(cartUserBirthdate, orderUserBirthdate);

        assertEquals(cartUser.getCpf(), orderUser.getCpf());
        assertEquals(cartUser.getEmail(), orderUser.getEmail());
        assertEquals(cartUser.isOptinWhatsapp(), orderUser.isOptinWhatsapp());
        assertEquals("REGISTERED", orderUser.getType());
    }

    //#############################################################################################

    private void validateEntries() {
        BiConsumer<OrderEntry, OrderEntry> validateEntry = (expected, actual) -> {
            assertEquals(expected.getQuantity(), actual.getQuantity());
            assertEquals(expected.getBasePrice(), actual.getBasePrice(), 0D);
            assertEquals(expected.getTotalPrice(), actual.getTotalPrice(), 0D);

            assertEquals(expected.getDiscountValues().size(), actual.getDiscountValues().size());
            expected.getDiscountValues().forEach(expectedDiscount ->
                assertTrue(actual.getDiscountValues().stream().anyMatch(actualDiscount -> Double.compare(actualDiscount, expectedDiscount) == 0))
            );
        };

        assertEquals(cart.getEntries().size(), order.getEntries().size());

        if (cart.getProcessType() != ACCESSORY) {
            //Parent PlanEntry
            String cartPlan = cart.getPlan().getCode();
            OrderEntry cartPlanEntry = cart.getEntry(cartPlan);
            OrderEntry orderPlanEntry = order.getEntry(cartPlan);

            assertEquals(1, orderPlanEntry.getQuantity());
            assertEquals(0D, orderPlanEntry.getBasePrice(), 0D);
            assertEquals(0D, orderPlanEntry.getTotalPrice(), 0D);
            assertTrue(orderPlanEntry.getDiscountValues().isEmpty());
            assertEquals(cartPlanEntry.getPaymentMode(), orderPlanEntry.getPaymentMode());

            //PlanEntry.status
            //TODO

            //------------------------------

            //Child PlanEntry
            OrderEntry childPlanEntry = childOrder.getEntry(cartPlan);
            validateEntry.accept(cartPlanEntry, childPlanEntry);
            assertEquals(order.getEntry(cartPlan).getPaymentMode(), childPlanEntry.getPaymentMode());

            //#############################################################

            if (cart.dependentQuantity() > 0) {
                //Parent DependentEntry
                OrderEntry cartDependentEntry = cart.getEntry("dependente");
                OrderEntry orderDependentEntry = order.getEntry("dependente");

                assertEquals(cartDependentEntry.getQuantity(), orderDependentEntry.getQuantity());
                assertEquals(0D, orderDependentEntry.getBasePrice(), 0D);
                assertEquals(0D, orderDependentEntry.getTotalPrice(), 0D);
                assertTrue(orderDependentEntry.getDiscountValues().isEmpty());
                assertEquals(cartDependentEntry.getPaymentMode(), orderDependentEntry.getPaymentMode());

                //------------------------------

                //Child DependentEntry
                OrderEntry childDependentEntry = childOrder.getEntry("dependente");

                validateEntry.accept(cart.getEntry("dependente"), childDependentEntry);
                assertEquals(order.getEntry("dependente").getPaymentMode(), childDependentEntry.getPaymentMode());
            }

            if (cart.isDeviceCart()) {
                //Parent DeviceEntry
                OrderEntry cartDeviceEntry = cart.getEntry(cart.getDevice().getCode());
                OrderEntry orderDeviceEntry = childOrder.getEntry(cart.getDevice().getCode());

                validateEntry.accept(cartDeviceEntry, orderDeviceEntry);
                //assertEquals(cartDeviceEntry.getPaymentMode(), orderDeviceEntry.getPaymentMode()); TODO inconsistente

                //DeviceEntry.status
                //TODO

                //------------------------------

                //Child DeviceEntry
                OrderEntry childDeviceEntry = childOrder.getEntry(cart.getDevice().getCode());

                assertEquals(1, childDeviceEntry.getQuantity());
                assertEquals(0D, childDeviceEntry.getBasePrice(), 0D);
                assertEquals(0D, childDeviceEntry.getTotalPrice(), 0D);
                assertTrue(childDeviceEntry.getDiscountValues().isEmpty());
                assertEquals(order.getEntry(cart.getDevice().getCode()).getPaymentMode(), childDeviceEntry.getPaymentMode());

                //------------------------------

                //Device Chip
                if (cart.getClaroChip().getChipType() == SIM) {
                    OrderEntry cartDeviceChipEntry = cart.getEntry("23100"); //Sem regionalização
                    OrderEntry orderDeviceChipEntry = order.getEntry("23100"); //Sem regionalização

                    validateEntry.accept(cartDeviceChipEntry, orderDeviceChipEntry);
                    assertEquals(cartPlanEntry.getPaymentMode(), orderDeviceChipEntry.getPaymentMode());

                    //DeviceChipEntry.status
                    //TODO
                }
            }

            //Plan Chip
            if (cart.getClaroChip().getChipType() == SIM) {
                OrderEntry cartPlanChipEntry = cart.getEntry("Claro_Chip");
                OrderEntry orderPlanChipEntry = order.getEntry("Claro_Chip");

                validateEntry.accept(cartPlanChipEntry, orderPlanChipEntry);
                assertEquals(cartPlanEntry.getPaymentMode(), orderPlanChipEntry.getPaymentMode());

                //PlanChipEntry.status
                //TODO
            }
        } else { //Accessory cart
            cart.getEntries().forEach(cartEntry ->
                validateEntry.accept(cartEntry, order.getEntries().stream().filter(en -> en.getProduct().getCode().equals(cartEntry.getProduct().getCode())).findFirst().orElseThrow())
            );
        }
    }

    private void validateTotalPrice() {
        //ParentOrder.totalPrice (carrinho pai) = Sempre zerado para fluxo de Planos. Caso Aparelhos = valor do Aparelho + 10 de chip (caso fluxo gross com chip comum). Caso Acessórios = valor total final do(s) produto(s)
        //ChildOrder.totalPrice (carrinho filho) = Plano + Dependentes (caso adicionado). Chip gratuito

        double cartTotalPriceRef = 0D;

        if (cart.getProcessType() != ACCESSORY) {
            if (cart.isDeviceCart()) {
                double totalPrice = cart.getEntry(cart.getDevice().getCode()).getTotalPrice();
                cartTotalPriceRef = cart.getProcessType().toString().matches("ACQUISITION|PORTABILITY") && cart.getClaroChip().getChipType() == SIM ? totalPrice + 10D : totalPrice;
            }

            //Child order
            OrderEntry cartPlanEntry = cart.getEntry(cart.getPlan().getCode());
            double childTotalPriceRef = (cart.dependentQuantity() > 0) ? cartPlanEntry.getTotalPrice() + cart.getEntry("dependente").getTotalPrice() : cartPlanEntry.getTotalPrice();
            assertEquals(childTotalPriceRef, childOrder.getTotalPrice(), 0D);
        } else {
            cartTotalPriceRef = cart.getEntries().stream().mapToDouble(OrderEntry::getTotalPrice).sum();
        }

        assertEquals(cartTotalPriceRef, order.getTotalPrice(), 0D);
    }

    //#############################################################################################

    private void validateAdministration() {
        //Flags
        assertFalse(order.isAbandonedCartOrder());
        assertEquals(cart.isAcceptFine(), order.isAcceptFine());
        assertFalse(order.isContingencyStepOne());
        assertEquals(cart.isPreSale(), order.isPreSale());
        assertEquals(cart.isTheComboMultiFlowForProspectMovelCustomer(), order.isTheComboMultiFlowForProspectMovelCustomer());
        assertEquals(cart.isMinhaClaroOrder(), order.isMinhaClaroOrder());
        assertEquals(cart.isOfferRealized(), order.isOfferRealized());

        //passedByClearSale
        if (checkOrderProcessAction("clearSaleRequestFraudStatus", "APA_APP")) {
            assertTrue(order.isPassedByClearSale());
        }

        assertEquals(cart.isThab(), order.isThab());

        //------------------------------

        assertEquals(cart.getPromotion().getCode(), childOrder.getPromotion().getCode()); //allPromotionResults
        assertEquals(cart.getAppliedCouponCodes(), order.getAppliedCouponCodes());
        assertEquals(cart.getChosenPlan(), order.getChosenPlan());
        //TODO claroChip inconsistente
        assertEquals(cart.getClaroDdd(), order.getClaroDdd());

        //claroLegacyOrderId
        if (checkOrderProcessAction("gerarPedidoVenda", "ACQUISITION|MIGRATE|PORTABILITY|DEVICE")) {
            assertNotNull(order.getClaroLegacyOrderId());
        }

        //TODO dayInvoiceExpiration inconsistente

        //gradePlan
        if (!cart.getProcessType().toString().matches("EXCHANGE|EXCHANGE_PROMO|MIGRATE")) { //TODO Para fluxos base é definido pelo valor do Plano (atual vs novo)
            assertEquals(cart.getGradePlan(), order.getGradePlan());
        }

        assertEquals(cart.getGuid(), order.getGuid());
        assertEquals(cart.getJourneyInformation(), order.getJourneyInformation());
        assertEquals(cart.getOfferedPlan(), order.getOfferedPlan());
        assertNotNull(order.getOrderTrackingUrl());
        assertEquals(cart.getRentabilizationCoupon(), order.getRentabilizationCoupon());
        assertEquals(cart.getSelectedInvoiceType(), order.getSelectedInvoiceType());
    }

    //#############################################################################################

    private boolean isSapFlow() {
        if (cart.isDeviceCart() || (!cart.isDeviceCart() && !cart.getProcessType().toString().matches("MIGRATE|EXCHANGE|EXCHANGE_PROMO"))) {
            cart.getClaroSapResponse().setStatus("090");
            return true;
        }
        return false;
    }

    private void mockSapEcc() {
        List<SapStatusHistory> statusList = order.getClaroSapResponse().getSapStatusHistory();
        String cartSapStatus = cart.getClaroSapResponse().getStatus();
        boolean isLast = statusList.stream().anyMatch(s -> s.getId().equals(cartSapStatus));

        if (((order.getStatus().equals("AWAITING_INVOICE") && !cartSapStatus.equals("880")) || order.getStatus().equals("ORDER_BILLED")) && isLast && !cartSapStatus.equals("900")) {
            UpdateOrderSapRequest updateOrderSapRequest = new UpdateOrderSapRequest();
            LocalDate dataAtual = LocalDate.now();
            LocalTime horaAtual = LocalTime.now();
            DateTimeFormatter formatterDateMonthYear = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter formatterYearMonthDayHour = DateTimeFormatter.ofPattern(("yyyy-MM-dd'T'HH:mm:ssXXX"));
            DateTimeFormatter formatterHourMinuteSec = DateTimeFormatter.ofPattern("HHmmss");

            ZonedDateTime timeZone = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

            updateOrderSapRequest.setEcommerceOrderId(cart.getCode());
            updateOrderSapRequest.setEcommerceEnv(Constants.ambiente);
            updateOrderSapRequest.setStatusDate(timeZone.format(formatterYearMonthDayHour));
            updateOrderSapRequest.setStatusTime(horaAtual.format(formatterHourMinuteSec));
            updateOrderSapRequest.setSapOrderId(order.getClaroSapResponse().getSapOrderId());
            updateOrderSapRequest.setSalesOrg("1100");
            updateOrderSapRequest.setDistributionChannel("50");
            if (cart.isDeviceCart()) {
                updateOrderSapRequest.setType("ZECO");
                updateOrderSapRequest.setCenter("1195");
            } else {
                updateOrderSapRequest.setType("ZBRI");
                updateOrderSapRequest.setCenter("11TL");
            }
            updateOrderSapRequest.setTypeDescription("Dev Doacao NF Pro");
            updateOrderSapRequest.setSapRequesterClientCode("0024259158");
            updateOrderSapRequest.setSapReceiverClientCode("0024259158");
            switch (cart.getClaroSapResponse().getStatus()) {
                case "090" -> {
                    if (cart.getDeliveryMode() == EXPRESS) {
                        updateOrderSapRequest.setStatus("900");
                        updateOrderSapRequest.setStatusDesc("Finalizada");
                        cart.getClaroSapResponse().setStatus("900");
                    } else {
                        updateOrderSapRequest.setStatus("820");
                        updateOrderSapRequest.setStatusDesc("Aguardando Impressão NF");
                        cart.getClaroSapResponse().setStatus("820");
                    }
                }
                case "820" -> {
                    updateOrderSapRequest.setStatus("880");
                    updateOrderSapRequest.setStatusDesc("Aguardando Conf. de Entrega 1");
                    cart.getClaroSapResponse().setStatus("880");
                }
                case "880" -> {
                    updateOrderSapRequest.setStatus("900");
                    updateOrderSapRequest.setStatusDesc("Finalizada");
                    cart.getClaroSapResponse().setStatus("900");
                }
            }
            updateOrderSapRequest.setOccurrenceDate(dataAtual.format(formatterDateMonthYear));
            updateOrderSapRequest.setOccurrenceTime(horaAtual.format(formatterHourMinuteSec));
            updateOrderSapRequest.setInvoiceDocument("1000001569");
            updateOrderSapRequest.setInvoiceNumber("001627340");
            updateOrderSapRequest.setInvoiceSeries("525");

            HttpResponse<String> updateOrderSapResponse = updateOrderSap(updateOrderSapRequest);
            assertEquals(updateOrderSapResponse.statusCode(), 200);
        }
    }

    //#############################################################################################

    private static String generateEaWindowDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
        return now.plusDays(5).format(formatter);
    }

    private void mockPortability() {
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();

        updateOrderRequest.setId(order.getClaroLegacyOrderId());
        updateOrderRequest.getDevices().setTelephoneNumber(order.getUser().getClaroTelephone());
        updateOrderRequest.getDevices().getPortability().setEaTicket(order.getEaTicket());
        updateOrderRequest.getDevices().getPortability().setClaroTicket(order.getPortabilityClaroTicket());
        updateOrderRequest.getDevices().getPortability().setEaWindowDate(generateEaWindowDate());
        updateOrderRequest.getDevices().getPortability().setProvisionalTelephoneNumber("11989858521");
        switch (order.getEventId()) {
            case "1000" -> {
                updateOrderRequest.getDevices().getPortability().setPortabilityStatus("LIBERADA");
                updateOrderRequest.getDevices().getPortability().setReason("Aguardando data/hora janela");
                updateOrderRequest.getDevices().getPortability().getSpn().setEventId("1010");
                updateOrderRequest.getDevices().getPortability().getSpn().setDescription("Autenticação bem sucedida notificada à Receptora");
                updateOrderRequest.getDevices().getPortability().setEaWindowDate(generateEaWindowDate());

                updateOrderRequest.setSubOrdersNull();
            }
            case "1010" -> {
                updateOrderRequest.setStatus("EM_PROCESSAMENTO");
                updateOrderRequest.setStatusDescription("710 - Aguardando retorno do parceiro logístico");
                updateOrderRequest.getOperation().setType("Ativacao");
                updateOrderRequest.getOperation().setDescription("Portabilidade Claro Conta");
                updateOrderRequest.getOperation().setLineSubtype("NORMAL");
                updateOrderRequest.getThab().setLicenseFee("NAO");
                updateOrderRequest.getDevices().getPortability().setPortabilityStatus("PENDENTE");
                updateOrderRequest.getDevices().getPortability().setReason("Aguardando data/hora janela - Aprovação do cliente via SMS");
                updateOrderRequest.getDevices().getPortability().getSpn().setEventId("1015");
                updateOrderRequest.getDevices().getPortability().getSpn().setDescription("Autenticação SMS notificada à Receptora");
                updateOrderRequest.getDevices().getSapOrders().setOrderNumber(order.getClaroSapResponse().getSapOrderId());
                updateOrderRequest.getDevices().getSapOrders().setType("DOACAO");
                updateOrderRequest.getDevices().getSapOrders().setStatus("ENVIADO");
                updateOrderRequest.getDevices().getSapOrders().setIccid("89550534120038193503");
                updateOrderRequest.getDevices().getPortability().setEaWindowDate(generateEaWindowDate());

                updateOrderRequest.setSubOrdersNull();
            }
            case "1015" -> {
                if (order.getStatus().equals("ORDER_DELIVERED")) {
                }
                updateOrderRequest.getDevices().getPortability().setPortabilityStatus("PENDENTE");
                updateOrderRequest.getDevices().getPortability().setReason("Aguardando data/hora janela");
                updateOrderRequest.getDevices().getPortability().getSpn().setEventId("1050");
                updateOrderRequest.getDevices().getPortability().getSpn().setDescription("Bilhete atualizado pela Receptora");
                updateOrderRequest.getSubOrders().setTransaction(null);
                updateOrderRequest.getSubOrders().setType("REAGENDAMENTO_PORTABILIDADE");
                updateOrderRequest.getSubOrders().setStatus(null);
                updateOrderRequest.getSubOrders().setDateTime(null);
                updateOrderRequest.getSubOrders().setProtocol(null);
                updateOrderRequest.getSubOrders().setStatusDescription(null);
                updateOrderRequest.getDevices().getPortability().setEaWindowDate(generateEaWindowDate());
            }
            case "1050" -> {
                updateOrderRequest.getDevices().getPortability().setPortabilityStatus("ATIVA");
                updateOrderRequest.getDevices().getPortability().setReason("Aguardando data/hora janela");
                updateOrderRequest.getDevices().getPortability().getSpn().setEventId("1060");
                updateOrderRequest.getDevices().getPortability().getSpn().setDescription("Bilhete efetivado pela Receptora (ativação do assinante concluída e confirmada à EA)");
                updateOrderRequest.getDevices().getPortability().setEaWindowDate("30122024 16:00:00");

                updateOrderRequest.setSubOrdersNull();
            }
        }

        HttpResponse<String> updateOrderResponse = updateOrder(updateOrderRequest);
        assertEquals(updateOrderResponse.statusCode(), 200);
    }

    private void mockSimplifiedActivation() {
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest();

        updateOrderRequest.setId(order.getClaroLegacyOrderId());
        updateOrderRequest.getDevices().setTelephoneNumber(order.getUser().getClaroTelephone());
        updateOrderRequest.getDevices().setRowType("TITULAR");
        updateOrderRequest.getDevices().getSapOrders().setOrderNumber(order.getClaroSapResponse().getSapOrderId());
        updateOrderRequest.getDevices().getSapOrders().setType("DOACAO");
        updateOrderRequest.getDevices().getSapOrders().setStatus("ENTREGUE");
        updateOrderRequest.getDevices().getSapOrders().setIccid("89550534120038193503");
        updateOrderRequest.setStatus("SUCESSO");
        updateOrderRequest.getThab().setLicenseFee("NAO");
        updateOrderRequest.getDevices().setMobileSubscriberId("925546878");

        if (cart.getProcessType() == PORTABILITY) {
            updateOrderRequest.getDevices().getPortability().setEaTicket(order.getEaTicket());
            updateOrderRequest.getDevices().getPortability().setClaroTicket(order.getPortabilityClaroTicket());
            updateOrderRequest.getDevices().getPortability().setEaWindowDate(generateEaWindowDate());
            updateOrderRequest.getDevices().getPortability().setProvisionalTelephoneNumber("11989858521");
            updateOrderRequest.getDevices().getPortability().setPortabilityStatus("PENDENTE");
        } else {
            updateOrderRequest.getDevices().setPortabilityNull();
        }

        updateOrderRequest.getOperation().setType("Ativacao");
        updateOrderRequest.getOperation().setDescription("Portabilidade Claro Conta");

        String operationDescription = "";
        String operationType = "Ativacao";
        String planCategory;

        if (cart.getPlan().getCategories().stream().anyMatch(c -> c.getCode().equals("controle"))) {
            planCategory = "Controle";
        } else if (cart.getPlan().getCategories().stream().anyMatch(c -> c.getCode().equals("pospago"))) {
            planCategory = "Conta";
        } else {
            planCategory = "Cartão";
        }

        switch (cart.getProcessType()) {
            case ACQUISITION -> {
                updateOrderRequest.setStatusDescription("2 - Ativação processada com sucesso");
                operationDescription = "Ativação Claro " + planCategory;
            }
            case PORTABILITY -> {
                updateOrderRequest.setStatusDescription("2 - Ativação processada com sucesso");
                operationDescription = "Portabilidade Claro " + planCategory;
            }
            case MIGRATE, EXCHANGE, APARELHO_TROCA_APARELHO -> {
                updateOrderRequest.setStatusDescription("102 - Migração processada com sucesso");
                updateOrderRequest.getOperation().setDescription("Alteração de Plano");
                operationType = "Migracao de Plano";
            }
        }

        updateOrderRequest.getOperation().setDescription(operationDescription);
        updateOrderRequest.getOperation().setType(operationType);

        if (cart.isComboFlow()) {
            updateOrderRequest.getOperation().setLineSubtype("COMBO"); //TODO verificar string correta pra COMBO E MPLAY
        } else {
            updateOrderRequest.getOperation().setLineSubtype("NORMAL");
        }

        HttpResponse<String> updateOrderResponse = updateOrder(updateOrderRequest);
        assertEquals(updateOrderResponse.statusCode(), 200);
    }
}
