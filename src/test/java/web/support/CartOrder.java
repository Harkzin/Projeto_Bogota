package web.support;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

import static web.support.utils.Constants.*;
import static web.support.api.RestAPI.getProductDetails;
import static web.support.api.RestAPI.objMapper;

public class CartOrder {

    private final List<Product> products = new ArrayList<>();
    public boolean isDebitPaymentFlow = true;
    public boolean hasDevice;
    public boolean hasLoyalty = true;
    public Essential essential;
    public PositionsAndPrices positionsAndPrices;
    public Dependent dependent;
    public Status status;
    public Payment payment;
    public Delivery delivery;
    public ClaroChip claroChip;
    public ClaroClube claroClube;
    public ClaroSapResponse claroSapResponse;
    public SubOrder subOrder;
    public boolean abandonedCartOrder;
    public boolean acceptFine;
    public boolean contingencyStepOne;
    public boolean isPreSale;
    public boolean minhaClaroOrder;
    public boolean offerRealized;
    public boolean passedByClearSale;
    public boolean thab;
    public String appliedCupomCodes;
    public String chosenPlan;
    public String claroDdd;
    public String claroLegacyOrderId;
    public String creationtime;
    public String dayInvoiceExpiration;
    public String eaTicket;
    public String eventId;
    public String gradePlan;
    public String journeyInformation;
    public String offeredPlan;
    public String orderTrackingUrl;
    public String portabilityClaroTicket;
    public String rentabilizationCoupon;
    public String selectedInvoiceType;
    private String planId;
    private String deviceId;
    private String allPromotionResults;
    private String children;
    private String guid;

    private Product getProduct(String id) {
        return products.stream().filter(product -> product.getCode().equals(id)).findFirst().orElseThrow();
    }

    public Product getPlan() {
        return getProduct(planId);
    }

    public void setPlan(String planId) {
        this.planId = planId;

        try {
            products.add(objMapper.readValue(getProductDetails(planId), Product.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getDevice() {
        return getProduct(deviceId);
    }

    public void setDevice(String deviceId) {
        this.deviceId = deviceId;

        try {
            products.add(objMapper.readValue(getProductDetails(deviceId), Product.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CartOrder initializeDefaultCartOrder() {
        essential = new Essential();
        essential.user = new User();

        positionsAndPrices = new PositionsAndPrices();

        payment = new Payment();
        payment.paymentAddress = new PaymentAddress();
        payment.paymentInfo = new PaymentInfo();

        delivery = new Delivery();
        delivery.deliveryAddress = new DeliveryAddress();

        return this;
    }

    public static class ClaroAuthenticationPaymentResponse {

        public String amount;
        public String card;
        public String flag;
        public int numberInstallments;
        public String responseDescription;

        private ClaroAuthenticationPaymentResponse() {
        }
    }

    public static class ClaroChip {

        public String activationCode;
        public String claroChipType;
        public String iccIdSim;
        public String qrCdode;
        public String techlonogy;

        private ClaroChip() {
        }
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

        private ClaroClube() {
        }
    }

    public static class ClaroSapResponse {

        public String center;
        public String invoiceNumber;
        public String nfeNumber;
        public String salesOrg;
        public String sapOrderId;
        public List<SapStatusHistory> sapStatusHistory;

        private ClaroSapResponse() {
        }
    }

    public static class Delivery {

        public DeliveryAddress deliveryAddress;
        public DeliveryMode deliveryMode;

        private Delivery() {
        }
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

        private DeliveryAddress() {
        }
    }

    public static class Dependent {

        public boolean hasDependent;
        public List<DependentsInformation> dependentsInformation;

        private Dependent() {
        }
    }

    public static class DependentsInformation {

        public String id;
        public String msisdn;
        public String processTypeOfDependent;

        private DependentsInformation() {
        }
    }

    public static class Entry {

        public int entryNumber;
        public String product;
        public int quantity;
        public double basePrice;
        public double totalPrice;
        public List<String> discountValues;
        public String paymentMode;
        public String status;

        private Entry() {
        }
    }

    public static class Essential {

        public String code;
        public User user;
        public String telephone;
        public ProcessType processType;

        private Essential() {
        }
    }

    public static class OrderProcess {

        public String processDefinitionName;
        public String state;
        public List<TaskLog> taskLogs;

        private OrderProcess() {
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

        private Payment() {
        }
    }

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

        private PaymentAddress() {
        }
    }

    public static class PaymentInfo {

        public String account;
        public String agency;
        public String bank;
        public String expireDate;
        public String invoiceType;

        private PaymentInfo() {
        }
    }

    public static class PaymentMethod {

        public String paymentMode;
        public String paymentMethodType;

        private PaymentMethod() {
        }
    }

    public static class PixPaymentInfo {

        public String txId;
        public double value;

        private PixPaymentInfo() {
        }
    }

    public static class PositionsAndPrices {

        public List<Entry> entries;
        public List<String> entryGroups;
        public double totalPrice;

        private PositionsAndPrices() {
        }
    }

    public static class SapStatusHistory {

        public String date;
        public String id;
        public String description;
        public String orderType;

        private SapStatusHistory() {
        }
    }

    public static class Status {

        public String status;
        public List<OrderProcess> orderProcess;

        private Status() {
        }
    }

    public static class SubOrder {

        public String status;
        public String statusDescription;
        public String type;

        private SubOrder() {
        }
    }

    public static class TaskLog {

        public String actionId;
        public String startDate;
        public String endDate;
        public String returnCode;

        private TaskLog() {
        }
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

        private User() {
        }
    }
}