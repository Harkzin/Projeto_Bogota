package support;

import java.util.ArrayList;
import java.util.List;

import static support.Common.*;

public final class CartOrder {

    public List<Product> products = new ArrayList<>();
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
    public String allPromotionResults;
    public String appliedCupomCodes;
    public String children;
    public String chosenPlan;
    public String claroDdd;
    public String claroLegacyOrderId;
    public String creationtime;
    public String dayInvoiceExpiration;
    public String eaTicket;
    public String eventId;
    public String gradePlan;
    public String guid;
    public String journeyInformation;
    public String offeredPlan;
    public String orderTrackingUrl;
    public String portabilityClaroTicket;
    public String rentabilizationCoupon;
    public String selectedInvoiceType;

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
    }

    public static class ClaroChip {

        public String activationCode;
        public String claroChipType;
        public String iccIdSim;
        public String qrCdode;
        public String techlonogy;
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
    }

    public static class ClaroSapResponse {

        public String center;
        public String invoiceNumber;
        public String nfeNumber;
        public String salesOrg;
        public String sapOrderId;
        public List<SapStatusHistory> sapStatusHistory;
    }

    public static class Delivery {

        public DeliveryAddress deliveryAddress;
        public DeliveryMode deliveryMode;
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
    }

    public static class Dependent {

        public boolean hasDependent;
        public List<DependentsInformation> dependentsInformation;
    }

    public static class DependentsInformation {

        public String id;
        public String msisdn;
        public String processTypeOfDependent;
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
    }

    public static class Essential {

        public String code;
        public User user;
        public String telephone;
        public ProcessType processType;
    }

    public static class OrderProcess {

        public String processDefinitionName;
        public String state;
        public List<TaskLog> taskLogs;
    }

    public static class Payment {

        public PaymentAddress paymentAddress;
        public PaymentInfo paymentInfo;
        public List<PaymentMethod> paymentMethods;
        public boolean iframePaymentAcquirer;
        public String iframePaymentResult;
        public ClaroAuthenticationPaymentResponse claroAuthenticationPaymentResponse;
        public PixPaymentInfo pixPaymentInfo;
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
    }

    public static class PaymentInfo {

        public String account;
        public String agency;
        public String bank;
        public String expireDate;
        public String invoiceType;
    }

    public static class PaymentMethod {

        public String paymentMode;
        public String paymentMethodType;
    }

    public static class PixPaymentInfo {

        public String txId;
        public double value;
    }

    public static class PositionsAndPrices {

        public List<Entry> entries;
        public List<String> entryGroups;
        public double totalPrice;
    }

    public static class SapStatusHistory {

        public String date;
        public String id;
        public String description;
        public String orderType;
    }

    public static class Status {

        public String status;
        public List<OrderProcess> orderProcess;
    }

    public static class SubOrder {

        public String status;
        public String statusDescription;
        public String type;
    }

    public static class TaskLog {

        public String actionId;
        public String startDate;
        public String endDate;
        public String returnCode;
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
    }
}