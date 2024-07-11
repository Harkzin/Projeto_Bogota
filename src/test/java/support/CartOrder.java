package support;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

import static support.utils.Constants.*;
import static support.api.RestAPI.getProductDetails;
import static support.api.RestAPI.objMapper;

public class CartOrder {

    private boolean eSim;
    private boolean thab;

    private String planId;
    private String deviceId;

    public boolean isDebitPaymentFlow = true;
    public boolean hasLoyalty = true;

    private Essential essential;
    private PositionsAndPrices positionsAndPrices;
    private Dependent dependent;
    private Status status;
    private Payment payment;
    private Delivery delivery;
    private ClaroChip claroChip;
    private ClaroClube claroClube;
    private ClaroSapResponse claroSapResponse;
    private SubOrder subOrder;

    private boolean abandonedCartOrder;
    private boolean acceptFine;
    private boolean contingencyStepOne;
    private boolean isPreSale;
    private boolean minhaClaroOrder;
    private boolean offerRealized;
    private boolean passedByClearSale;

    private String allPromotionResults;
    private String appliedCupomCodes;
    private String children;
    private String chosenPlan;
    private String claroDdd;
    private String claroLegacyOrderId;
    private String creationtime;
    private String dayInvoiceExpiration;
    private String eaTicket;
    private String eventId;
    private String gradePlan;
    private String guid;
    private String journeyInformation;
    private String offeredPlan;
    private String orderTrackingUrl;
    private String portabilityClaroTicket;
    private String rentabilizationCoupon;
    private String selectedInvoiceType;

    private Product getProduct(String id) {
        return positionsAndPrices.entries.stream()
                .map(e -> e.product)
                .filter(product -> product.getCode().equals(id))
                .findFirst().orElseThrow();
    }

    public Product getDevice() {
        return getProduct(deviceId);
    }

    public Product getPlan() {
        return getProduct(planId);
    }

    private void removeProduct(String id) {
        positionsAndPrices.entries.remove(positionsAndPrices.entries.stream()
                .filter(e -> e.product.getCode().equals(id))
                .findFirst()
                .orElseThrow());
    };

    private Product createProduct(String id) {
        try {
            return objMapper.readValue(getProductDetails(id), Product.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Verificar se a service [API] do ambiente esta funcionando\n" + e);
        }
    }

    private void initializePositionAndPrices() {
        if (positionsAndPrices == null) {
            positionsAndPrices = new PositionsAndPrices();
            positionsAndPrices.entries = new ArrayList<>();
            positionsAndPrices.entryGroups = new ArrayList<>();
        }
    }

    public void setDevice(String deviceId, double price) { //TODO Remover price quando a API estiver pronta ECCMAUT-806
        if (!(this.deviceId == null)) {
            removeProduct(this.deviceId);
        }

        this.deviceId = deviceId;

        initializePositionAndPrices();
        positionsAndPrices.entries.add(new PositionsAndPrices.Entry(createProduct(deviceId), 1, price, price)); //TODO Deve pegar o preço da API ECCMAUT-806
    }

    public void setPlan(String planId) {
        if (!(this.planId == null)) {
            removeProduct(this.planId);
        }

        this.planId = planId;

        Product plan = createProduct(planId);
        initializePositionAndPrices();
        positionsAndPrices.entries.add(new PositionsAndPrices.Entry(plan, 1, plan.getPrice(), plan.getDebitPlanPrice())); //TODO Atualizar plan.getDebitPlanPrice() para pegar o preço da API (preço da promo, API sem definição ainda)
    }

    public double getCartProductPrice(Product product) {
        return positionsAndPrices.entries.stream()
                .filter(e -> e.product == product)
                .findFirst().orElseThrow()
                .totalPrice;
    }

    public void setEsimChip() {
        eSim = true;
    }

    public boolean isEsim() {
        return eSim;
    }

    public void setThab() {
        thab = true;
    }

    public boolean isThab() {
        return thab;
    }

    public boolean isDeviceCart() {
        return !(deviceId == null);
    }

    private void initializeDelivery() {
        if (delivery == null) {
            delivery = new Delivery();
            delivery.deliveryAddress = new Delivery.DeliveryAddress();
        }
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        initializeDelivery();
        delivery.deliveryMode = deliveryMode;
    }

    public DeliveryMode getDeliveryMode() {
        return delivery.deliveryMode;
    }

    private void initializeEssential() {
        if (essential == null) {
            essential = new Essential();
            essential.user = new Essential.User();
        }
    }

    public void setProcessType(ProcessType processType) {
        initializeEssential();
        essential.processType = processType;
    }

    public ProcessType getProcessType() {
        return essential.processType;
    }

    public void setUserEmail(String email) {
        initializeEssential();
        essential.user.email = email;
    }

    public String getUserEmail() {
        return essential.user.email;
    }

    public static class ClaroChip {

        public String activationCode;
        public String claroChipType;
        public String iccIdSim;
        public String qrCdode;
        public String techlonogy;

        private ClaroChip() {}
    }

    public static class ClaroClube {

        public String awardPoints;
        public double discountValue;
        public boolean isClaroClubeApplied;
        public String redeemId;
        public boolean redeemed;
        public boolean refund;
        public boolean reserved;
        public boolean used;

        private ClaroClube() {}
    }

    public static class ClaroSapResponse {

        public String center;
        public String invoiceNumber;
        public String nfeNumber;
        public String salesOrg;
        public String sapOrderId;
        public List<SapStatusHistory> sapStatusHistory;

        private ClaroSapResponse() {}

        public static class SapStatusHistory {

            public String date;
            public String id;
            public String description;
            public String orderType;

            private SapStatusHistory() {}
        }
    }

    public static class Delivery {

        public DeliveryAddress deliveryAddress;
        public DeliveryMode deliveryMode;

        private Delivery() {}

        public static class DeliveryAddress {

            public String streetname;
            public String streetnumber;
            public String building;
            public String postalcode;
            public String town;
            public String stateCode;
            public String neighbourhood;
            public boolean shippingAddress;
            public boolean billingAddress;

            private DeliveryAddress() {}
        }
    }

    public static class Dependent {

        public boolean hasDependent;
        public List<DependentsInformation> dependentsInformation;

        private Dependent() {}

        public static class DependentsInformation {

            public String id;
            public String msisdn;
            public String processTypeOfDependent;

            private DependentsInformation() {}
        }
    }

    public static class Essential {

        public String code;
        public User user;
        public String telephone;
        public ProcessType processType;

        private Essential() {}

        public static class User {

            public String name;
            public String displayName;
            public String parentfullname;
            public String claroTelephone;
            public String telephone;
            public String claroProvisionalTelephone;
            public String birthdate;
            public String cpf;
            public String email;
            public boolean optinWhatsapp;
            public String type;

            private User() {}
        }
    }

    public static class Payment {

        public PaymentAddress paymentAddress;
        public PaymentInfo paymentInfo;
        public List<PaymentMethod> paymentMethods;
        public boolean iframePaymentAcquirer;
        public String iframePaymentResult;
        public ClaroAuthenticationPaymentResponse claroAuthenticationPaymentResponse;
        public PixPaymentInfo pixPaymentInfo;

        private Payment() {}

        public static class PaymentAddress {

            public String streetname;
            public String streetnumber;
            public String building;
            public String postalcode;
            public String town;
            public String stateCode;
            public String neighbourhood;
            public boolean shippingAddress;
            public boolean billingAddress;

            private PaymentAddress() {}
        }

        public static class PaymentInfo {

            public String account;
            public String agency;
            public String bank;
            public String expireDate;
            public String invoiceType;

            private PaymentInfo() {}
        }

        public static class PaymentMethod {

            public String paymentMode;
            public String paymentMethodType;

            private PaymentMethod() {}
        }

        public static class ClaroAuthenticationPaymentResponse {

            public String amount;
            public String card;
            public String flag;
            public int numberInstallments;
            public String responseDescription;

            private ClaroAuthenticationPaymentResponse() {}
        }

        public static class PixPaymentInfo {

            public String txId;
            public double value;

            private PixPaymentInfo() {}
        }
    }

    public static class PositionsAndPrices {

        public List<Entry> entries;
        public List<String> entryGroups;
        public double totalPrice;

        private PositionsAndPrices() {}

        public static class Entry {

            public Product product;
            public int quantity;
            public double basePrice;
            public double totalPrice;
            public List<String> discountValues;

            public int entryNumber;

            public PaymentMode paymentMode;
            public String status;

            private Entry() {}

            private Entry(Product product, int quantity, double basePrice, double totalPrice) {
                this.product = product;
                this.quantity = quantity;
                this.basePrice = basePrice;
                this.totalPrice = totalPrice;
            }
        }
    }

    public static class Status {

        public String status;
        public List<OrderProcess> orderProcess;

        private Status() {}

        public static class OrderProcess {

            public String processDefinitionName;
            public String state;
            public List<TaskLog> taskLogs;

            private OrderProcess() {}

            public static class TaskLog {

                public String actionId;
                public String startDate;
                public String endDate;
                public String returnCode;

                private TaskLog() {}
            }
        }
    }

    public static class SubOrder {

        public String status;
        public String statusDescription;
        public String type;

        private SubOrder() {}
    }
}