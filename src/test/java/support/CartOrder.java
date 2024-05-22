package support;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CartOrder {
    public class ClaroAuthenticationPaymentResponse {
        @JsonProperty("amount")
        public String amount;
        @JsonProperty("card")
        public String card;
        @JsonProperty("flag")
        public String flag;
        @JsonProperty("numberInstallments")
        public int numberInstallments;
        @JsonProperty("responseDescription")
        public String responseDescription;
    }

    public class ClaroChip {
        @JsonProperty("activationCode")
        public String activationCode;
        @JsonProperty("claroChipType")
        public String claroChipType;
        @JsonProperty("iccIdSim")
        public String iccIdSim;
        @JsonProperty("qrCdode")
        public String qrCdode;
        @JsonProperty("techlonogy")
        public String techlonogy;
    }

    public class ClaroClube {
        @JsonProperty("awardPoints")
        public String awardPoints;
        @JsonProperty("discountValue")
        public double discountValue;
        @JsonProperty("isClaroClubeApplied")
        public boolean isClaroClubeApplied;
        @JsonProperty("redeemId")
        public String redeemId;
        @JsonProperty("redeemed")
        public boolean redeemed;
        @JsonProperty("refund")
        public boolean refund;
        @JsonProperty("reserved")
        public boolean reserved;
        @JsonProperty("used")
        public boolean used;
    }

    public class ClaroSapResponse {
        @JsonProperty("center")
        public String center;
        @JsonProperty("invoiceNumber")
        public String invoiceNumber;
        @JsonProperty("nfeNumber")
        public String nfeNumber;
        @JsonProperty("salesOrg")
        public String salesOrg;
        @JsonProperty("sapOrderId")
        public String sapOrderId;
        @JsonProperty("sapStatusHistory")
        public List<SapStatusHistory> sapStatusHistory;
    }

    public class Delivery {
        @JsonProperty("deliveryAddress")
        public DeliveryAddress deliveryAddress = new DeliveryAddress();
        @JsonProperty("deliveryMode")
        public String deliveryMode;
    }

    public class DeliveryAddress {
        @JsonProperty("streetname")
        public String streetname;
        @JsonProperty("streetnumber")
        public String streetnumber;
        @JsonProperty("building")
        public String building;
        @JsonProperty("postalcode")
        public String postalcode;
        @JsonProperty("town")
        public String town;
        @JsonProperty("stateCode")
        public String stateCode;
        @JsonProperty("neighbourhood")
        public String neighbourhood;
        @JsonProperty("shippingAddress")
        public boolean shippingAddress;
        @JsonProperty("billingAddress")
        public boolean billingAddress;
    }

    public class Dependent {
        @JsonProperty("hasDependent")
        public boolean hasDependent;
        @JsonProperty("dependentsInformation")
        public List<DependentsInformation> dependentsInformation;
    }

    public class DependentsInformation {
        @JsonProperty("id")
        public String id;
        @JsonProperty("msisdn")
        public String msisdn;
        @JsonProperty("processTypeOfDependent")
        public String processTypeOfDependent;
    }

    public class Entry {
        @JsonProperty("entryNumber")
        public int entryNumber;
        @JsonProperty("product")
        public String product;
        @JsonProperty("quantity")
        public int quantity;
        @JsonProperty("basePrice")
        public double basePrice;
        @JsonProperty("totalPrice")
        public double totalPrice;
        @JsonProperty("discountValues")
        public List<String> discountValues;
        @JsonProperty("paymentMode")
        public String paymentMode;
        @JsonProperty("status")
        public String status;
    }

    public class Essential {
        @JsonProperty("code")
        public String code;
        @JsonProperty("user")
        public User user = new User();
        @JsonProperty("telephone")
        public String telephone;
        @JsonProperty("processType")
        public String processType;
    }

    public class OrderProcess {
        @JsonProperty("processDefinitionName")
        public String processDefinitionName;
        @JsonProperty("state")
        public String state;
        @JsonProperty("taskLogs")
        public List<TaskLog> taskLogs;
    }

    public class Payment {
        @JsonProperty("paymentAddress")
        public PaymentAddress paymentAddress = new PaymentAddress();
        @JsonProperty("paymentInfo")
        public PaymentInfo paymentInfo = new PaymentInfo();
        @JsonProperty("paymentMethods")
        public List<PaymentMethod> paymentMethods;
        @JsonProperty("iframePaymentAcquirer")
        public boolean iframePaymentAcquirer;
        @JsonProperty("iframePaymentResult")
        public String iframePaymentResult;
        @JsonProperty("claroAuthenticationPaymentResponse")
        public ClaroAuthenticationPaymentResponse claroAuthenticationPaymentResponse;
        @JsonProperty("pixPaymentInfo")
        public PixPaymentInfo pixPaymentInfo;
    }

    public class PaymentAddress {
        @JsonProperty("streetname")
        public String streetname;
        @JsonProperty("streetnumber")
        public String streetnumber;
        @JsonProperty("building")
        public String building;
        @JsonProperty("postalcode")
        public String postalcode;
        @JsonProperty("town")
        public String town;
        @JsonProperty("stateCode")
        public String stateCode;
        @JsonProperty("neighbourhood")
        public String neighbourhood;
        @JsonProperty("shippingAddress")
        public boolean shippingAddress;
        @JsonProperty("billingAddress")
        public boolean billingAddress;
    }

    public class PaymentInfo {
        @JsonProperty("account")
        public String account;
        @JsonProperty("agency")
        public String agency;
        @JsonProperty("bank")
        public String bank;
        @JsonProperty("expireDate")
        public String expireDate;
        @JsonProperty("invoiceType")
        public String invoiceType;
    }

    public class PaymentMethod {
        @JsonProperty("paymentMode")
        public String paymentMode;
        @JsonProperty("paymentMethodType")
        public String paymentMethodType;
    }

    public class PixPaymentInfo {
        @JsonProperty("txId")
        public String txId;
        @JsonProperty("value")
        public double value;
    }

    public class PositionsAndPrices {
        @JsonProperty("entries")
        public List<Entry> entries;
        @JsonProperty("entryGroups")
        public List<String> entryGroups;
        @JsonProperty("totalPrice")
        public double totalPrice;
    }

    public class SapStatusHistory {
        @JsonProperty("date")
        public String date;
        @JsonProperty("id")
        public String id;
        @JsonProperty("description")
        public String description;
        @JsonProperty("orderType")
        public String orderType;
    }

    public class Status {
        @JsonProperty("status")
        public String status;
        @JsonProperty("orderProcess")
        public List<OrderProcess> orderProcess;
    }

    public class SubOrder {
        @JsonProperty("status")
        public String status;
        @JsonProperty("statusDescription")
        public String statusDescription;
        @JsonProperty("type")
        public String type;
    }

    public class TaskLog {
        @JsonProperty("actionId")
        public String actionId;
        @JsonProperty("startDate")
        public String startDate;
        @JsonProperty("endDate")
        public String endDate;
        @JsonProperty("returnCode")
        public String returnCode;
    }

    public class User {
        @JsonProperty("name")
        public String name;
        @JsonProperty("displayName")
        public String displayName;
        @JsonProperty("parentfullname")
        public String parentfullname;
        @JsonProperty("claroTelephone")
        public String claroTelephone;
        @JsonProperty("telephone")
        public String telephone;
        @JsonProperty("claroProvisionalTelephone")
        public String claroProvisionalTelephone;
        @JsonProperty("birthdate")
        public String birthdate;
        @JsonProperty("cpf")
        public String cpf;
        @JsonProperty("email")
        public String email;
        @JsonProperty("optinWhatsapp")
        public boolean optinWhatsapp;
        @JsonProperty("type")
        public String type;
    }

    @JsonProperty("essential")
    public Essential essential = new Essential();
    @JsonProperty("positionsAndPrices")
    public PositionsAndPrices positionsAndPrices = new PositionsAndPrices();
    @JsonProperty("dependent")
    public Dependent dependent;
    @JsonProperty("status")
    public Status status;
    @JsonProperty("payment")
    public Payment payment = new Payment();
    @JsonProperty("delivery")
    public Delivery delivery = new Delivery();
    @JsonProperty("abandonedCartOrder")
    public boolean abandonedCartOrder;
    @JsonProperty("acceptFine")
    public boolean acceptFine;
    @JsonProperty("allPromotionResults")
    public String allPromotionResults;
    @JsonProperty("appliedCupomCodes")
    public String appliedCupomCodes;
    @JsonProperty("children")
    public String children;
    @JsonProperty("chosenPlan")
    public String chosenPlan;
    @JsonProperty("claroChip")
    public ClaroChip claroChip = new ClaroChip();
    @JsonProperty("claroClube")
    public ClaroClube claroClube;
    @JsonProperty("claroDdd")
    public String claroDdd;
    @JsonProperty("claroLegacyOrderId")
    public String claroLegacyOrderId;
    @JsonProperty("claroSapResponse")
    public ClaroSapResponse claroSapResponse;
    @JsonProperty("contingencyStepOne")
    public boolean contingencyStepOne;
    @JsonProperty("creationtime")
    public String creationtime;
    @JsonProperty("dayInvoiceExpiration")
    public String dayInvoiceExpiration;
    @JsonProperty("eaTicket")
    public String eaTicket;
    @JsonProperty("eventId")
    public String eventId;
    @JsonProperty("gradePlan")
    public String gradePlan;
    @JsonProperty("guid")
    public String guid;
    @JsonProperty("isPreSale")
    public boolean isPreSale;
    @JsonProperty("journeyInformation")
    public String journeyInformation;
    @JsonProperty("minhaClaroOrder")
    public boolean minhaClaroOrder;
    @JsonProperty("offerRealized")
    public boolean offerRealized;
    @JsonProperty("offeredPlan")
    public String offeredPlan;
    @JsonProperty("orderTrackingUrl")
    public String orderTrackingUrl;
    @JsonProperty("passedByClearSale")
    public boolean passedByClearSale;
    @JsonProperty("portabilityClaroTicket")
    public String portabilityClaroTicket;
    @JsonProperty("rentabilizationCoupon")
    public String rentabilizationCoupon;
    @JsonProperty("selectedInvoiceType")
    public String selectedInvoiceType;
    @JsonProperty("subOrder")
    public SubOrder subOrder;
    @JsonProperty("thab")
    public boolean thab;
}