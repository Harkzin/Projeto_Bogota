package web.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import web.models.product.DeviceProduct;
import web.models.product.PlanProduct;
import web.models.product.Product;
import static web.support.api.RestAPI.getProductDetails;
import static web.support.api.RestAPI.objMapper;
import web.support.utils.Constants.DeliveryMode;
import web.support.utils.Constants.InvoiceType;
import static web.support.utils.Constants.InvoiceType.PRINTED;
import web.support.utils.Constants.PaymentMode;
import static web.support.utils.Constants.PaymentMode.TICKET;
import web.support.utils.Constants.ProcessType;
import static web.support.utils.Constants.ProcessType.ACQUISITION;
import static web.support.utils.Constants.ProcessType.PORTABILITY;
import static web.support.utils.Constants.focusPlan;

public class CartOrder {

    public CartOrder() {
        essential = new Essential();
        positionsAndPrices = new PositionsAndPrices();
        payment = new Payment();
        delivery = new Delivery();
    }

    private boolean eSIM;
    private boolean thab;

    private String planId;
    private String deviceId;

    public boolean isDebitPaymentFlow;
    public boolean hasLoyalty = true;

    private final Essential essential;
    private final PositionsAndPrices positionsAndPrices;
    private Status status;
    private final Payment payment;
    private final Delivery delivery;
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

    private int claroDdd;

    private String allPromotionResults;
    private final List<String> appliedCouponCodes = new ArrayList<>();
    private String children;
    private String chosenPlan;
    private String claroLegacyOrderId;
    private String creationtime;
    private String dayInvoiceExpiration;
    private final List<DependentsInformation> dependentsInformation = new ArrayList<>();
    private String eaTicket;
    private String eventId;
    private String gradePlan;
    private String guid;
    private String journeyInformation;
    private String offeredPlan;
    private String orderTrackingUrl;
    private String portabilityClaroTicket;
    private String rentabilizationCoupon;

    private InvoiceType selectedInvoiceType;

    private Product getProduct(String id) {
        return positionsAndPrices.entries.stream()
                .map(e ->  e.product)
                .filter(product -> product.getCode().equals(id))
                .findFirst().orElseThrow();
    }

    public DeviceProduct getDevice() {
        return (DeviceProduct) getProduct(deviceId);
    }

    public PlanProduct getPlan() {
        return (PlanProduct) getProduct(planId);
    }

    private void removeProduct(String id) {
        positionsAndPrices.entries.remove(positionsAndPrices.entries.stream()
                .filter(e -> e.product.getCode().equals(id))
                .findFirst()
                .orElseThrow());
    }

    private <T> T createProduct(String id, Class<T> cls) {
        try {
            return objMapper.readValue(getProductDetails(id), cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Verificar se a service [API] do ambiente esta funcionando\n" + e);
        }
    }

    public void setDeviceWithFocusPlan(String deviceId) {
        if (!(this.deviceId == null)) {
            removeProduct(this.deviceId);
        }
        this.deviceId = deviceId;

        essential.processType = ACQUISITION; //Default atual PLP/PDP
        String defaultCampaign = "APV"; //Default atual PLP/PDP

        DeviceProduct device = createProduct(deviceId, DeviceProduct.class);
        device.setDevicePriceInfo(defaultCampaign, focusPlan, "1100"); //SalesOrg fixo para SP 11, sem regionalização implementada.
        double basePrice = device.getCampaignPrice(true);

        PositionsAndPrices.Entry deviceEntry = new PositionsAndPrices.Entry(device, 1, basePrice, 0D);
        deviceEntry.bpo = defaultCampaign;
        positionsAndPrices.entries.add(deviceEntry);

        setPlan(focusPlan);
    }

    public void setPlan(String planId) {
        if (!(this.planId == null)) {
            removeProduct(this.planId);
        }
        this.planId = planId;

        PlanProduct plan = createProduct(planId, PlanProduct.class);

        double promoDiscount;
        if (isDeviceCart()) {
            promoDiscount = getDevice().getPlanPromoDiscount(essential.processType, TICKET, null, true, claroDdd); //Default Boleto na PDP (ticket)
        } else {
            promoDiscount = plan.getPrice(isDebitPaymentFlow, selectedInvoiceType == PRINTED) - plan.getPrice();
            //TODO Atualizar para pegar o preço da API (preço da promo, ECCMAUT-888)
        }

        positionsAndPrices.entries.add(new PositionsAndPrices.Entry(plan, 1, plan.getPrice(), promoDiscount));
    }

    public void updatePlanForDevice(String planId) {
        getDevice().setDevicePriceInfo(getEntry(deviceId).bpo, planId, "1100"); //SalesOrg fixo para SP 11, sem regionalização implementada.

        double deviceCampaignPrice = getDevice().getCampaignPrice(true);
        getEntry(deviceId).basePrice = deviceCampaignPrice;
        getEntry(deviceId).totalPrice = deviceCampaignPrice;

        setPlan(planId);
    }

    public PositionsAndPrices.Entry getEntry(String id) {
        return positionsAndPrices.entries.stream()
                .filter(e -> e.product.getCode().equals(id))
                .findFirst().orElseThrow();
    }

    public void addVoucherForDevice(String voucher) {
        double amount = 100D; //TODO Mock para CUPOM100. Valor deve vir da API ECCMAUT-888
        getEntry(deviceId).setDiscount(amount);
        appliedCouponCodes.add(voucher);
    }

    public String getAppliedCoupon() {
        if (!appliedCouponCodes.isEmpty()) {
            return appliedCouponCodes.get(0);
        }
        return null;
    }

    public void setEsimChip(boolean isEsim) {
        eSIM = isEsim;
    }

    public boolean isEsim() {
        return eSIM;
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

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        delivery.deliveryMode = deliveryMode;
    }

    public DeliveryMode getDeliveryMode() {
        return delivery.deliveryMode;
    }

    public void setProcessType(ProcessType processType) {
        essential.processType = processType;

        if (isDeviceCart()) {
            updatePlanForDevice(focusPlan);
        }
    }

    public ProcessType getProcessType() {
        return essential.processType;
    }

    public void setUserEmail(String email) {
        essential.user.email = email;
    }

    public String getUserEmail() {
        return essential.user.email;
    }

    public int hasDependent() {
        return dependentsInformation.size();
    }

    private void addDependent(String id, String msisdn, ProcessType processType) {
        if (hasDependent() == 0) { //Cria entry caso seja o primeiro dependente
            Product dependente = createProduct("dependente", PlanProduct.class);
            positionsAndPrices.entries.add(new PositionsAndPrices.Entry(dependente, 1, dependente.getPrice(), 0D));
        } else { //Atualiza a quantidade e preço na entry caso já tenha dependentes adicionados
            PositionsAndPrices.Entry depEntry = getEntry("dependente");
            depEntry.quantity++;
            depEntry.totalPrice += depEntry.basePrice;
        }

        DependentsInformation dep = new DependentsInformation();
        dep.id = id;
        dep.msisdn = msisdn;
        dep.processTypeOfDependent = processType;
        dependentsInformation.add(dep);
    }

    public void addNewLineDependent(String id) {
        addDependent(id, null, ACQUISITION);
    }

    public void addPortabilityDependent(String id, String msisdn) {
        addDependent(id, msisdn, PORTABILITY);
    }

    public void setSelectedInvoiceType(InvoiceType invoiceType) {
        selectedInvoiceType = invoiceType;

        getEntry(planId).totalPrice = getPlan().getPrice(isDebitPaymentFlow, invoiceType == PRINTED);
        //TODO Pegar valor da API ECCMAUT-888
    }

    public InvoiceType getSelectedInvoiceType() {
        return selectedInvoiceType;
    }

    public void updatePlanEntryPaymentMode(PaymentMode paymentMode) {
        getEntry(planId).paymentMode = paymentMode;
        getEntry(planId).totalPrice = getPlan().getPrice(isDebitPaymentFlow, selectedInvoiceType == PRINTED);
        //TODO Pegar valor da API ECCMAUT-888
    }

    public void setDDD(int ddd) {
        claroDdd = ddd;
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

        public int awardPoints;
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

        private Delivery() {
            deliveryAddress = new Delivery.DeliveryAddress();
        }

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

    public static class DependentsInformation {

        public String id;
        public String msisdn;
        public ProcessType processTypeOfDependent;

        private DependentsInformation() {}
    }

    public static class Essential {

        public String code;
        public User user;
        public String telephone;
        public ProcessType processType;

        private Essential() {
            user = new Essential.User();
        }

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
            public int expireDate;
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
            public String numberInstallments;
            public String responseDescription;

            private ClaroAuthenticationPaymentResponse() {}
        }

        public static class PixPaymentInfo {

            public String txId;
            public String value;

            private PixPaymentInfo() {}
        }
    }

    public static class PositionsAndPrices {

        public List<Entry> entries;
        public List<String> entryGroups;
        public double totalPrice;

        private PositionsAndPrices() {
            entries = new ArrayList<>();
            entryGroups = new ArrayList<>();
        }

        public static class Entry {

            private Product product;
            private int quantity;
            private double basePrice;
            private double totalPrice;
            private final List<Double> discountValues = new ArrayList<>();

            private int entryNumber;

            private PaymentMode paymentMode;
            private String bpo;
            private String status;

            private Entry() {}

            private Entry(Product product, int quantity, double basePrice, double discount) {
                this.product = product;
                this.quantity = quantity;
                this.basePrice = basePrice;

                discountValues.add(discount);
                totalPrice = basePrice - discount;
            }

            public double getBasePrice() {
                return basePrice;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public double getDiscount() {
                return discountValues.get(0);
            }

            public Product getProduct() {
                return product;
            }

            private void setDiscount(double discount) {
                totalPrice -= discount;
                discountValues.set(0, discount);
            }

            public void setBpo(String bpo) {
                this.bpo = bpo;
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