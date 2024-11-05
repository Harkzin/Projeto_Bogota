package web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import web.models.product.DeviceProduct;
import web.models.product.PlanProduct;
import web.models.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static web.support.api.RestAPI.*;
import static web.support.utils.Constants.*;
import static web.support.utils.Constants.InvoiceType.*;
import static web.support.utils.Constants.PaymentMode.*;
import static web.support.utils.Constants.ProcessType.*;

public class CartOrder {

    public CartOrder() {
        essential = new Essential();
        positionsAndPrices = new PositionsAndPrices();
        payment = new Payment();
        delivery = new Delivery();
        claroChip = new ClaroChip();
    }

    private boolean thab;

    private String planId;
    private String deviceId;

    public boolean isDebitPaymentFlow;

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

    private Promotion allPromotionResults;
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
            throw new RuntimeException("Verificar se a service [API] do ambiente esta funcionando (acessar Swagger).\n" + e);
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
        double planFullPrice = plan.getPrice();
        double promoDiscount = 0D;

        if (plan.getCategories().stream().noneMatch(c -> c.getCode().equals("prepago"))) {
            if (isDeviceCart()) {
                if (essential.processType != APARELHO_TROCA_APARELHO) {
                    promoDiscount = getDevice().getPlanPromoDiscount(essential.processType, TICKET, null, true, claroDdd); //Default Boleto na PDP (ticket)
                } else {
                    planFullPrice = essential.user.getClaroSubscription().claroPlanPrice; //Preço vem da API de login, sem desconto de promoção
                }
            } else {
                promoDiscount = plan.getPrice(isDebitPaymentFlow, selectedInvoiceType == PRINTED) - plan.getPrice();
            }
        }

        positionsAndPrices.entries.add(new PositionsAndPrices.Entry(plan, 1, planFullPrice, promoDiscount));
        getEntry(planId).paymentMode = TICKET; //Pagamento default atual
    }

    public void updatePlanAndDevicePrice(String planId) {
        PositionsAndPrices.Entry deviceEntry = getEntry(deviceId);

        getDevice().setDevicePriceInfo(deviceEntry.bpo, planId, "1100"); //SalesOrg fixo para SP 11, sem regionalização implementada.
        double price = getDevice().hasCampaignPrice() ? getDevice().getCampaignPrice(true) : getDevice().getPrice(); //Caso não exista linha de preço configurada = preço full
        deviceEntry.basePrice = price;
        deviceEntry.totalPrice = price;

        setPlan(planId);
    }

    public PositionsAndPrices.Entry getEntry(String id) {
        return positionsAndPrices.entries.stream()
                .filter(e -> e.product.getCode().equals(id))
                .findFirst().orElseThrow();
    }

    public void addVoucherForDevice(String voucher) {
        double amount = 100D; //TODO Mock para CUPOM100. Valor deve vir da API ECCMAUT-806
        getEntry(deviceId).setDiscount(amount);
        appliedCouponCodes.add(voucher);
    }

    public String getAppliedCoupon() {
        if (!appliedCouponCodes.isEmpty()) {
            return appliedCouponCodes.get(0);
        }
        return null;
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

        updatePlanCartPromotion();
    }

    public InvoiceType getSelectedInvoiceType() {
        return selectedInvoiceType;
    }

    public void setDDD(int ddd) {
        claroDdd = ddd;
    }

    public void updatePlanCartPromotion() {
        try {
            allPromotionResults = objMapper.readValue(getPlanCartPromotion(guid), Promotion.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        PositionsAndPrices.Entry planEntry = getEntry(planId);
        planEntry.totalPrice = getPlan().getPrice() - allPromotionResults.discountValue;
        planEntry.paymentMode = allPromotionResults.paymentMethod;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean hasLoyalty() {
        if (!isDeviceCart()) {
            return allPromotionResults.loyalty;
        } else {
            return !getEntry(deviceId).bpo.equals("PPV"); //Para Aparelhos, a única opção sem fid é no fluxo de base [Manter o Plano sem Fid], campanha PPV
        }
    }

    public void setRentabilizationCart(String url) {
        URIBuilder urlRentab = new URIBuilder(urlAmbiente + url);
        List<NameValuePair> params = urlRentab.getQueryParams();

        Function<String, String> getValue = param ->
            params.stream()
                    .filter(p -> p.getName().equals(param))
                    .findFirst().orElseThrow().getValue();

        rentabilizationCoupon = getValue.apply("coupon");
        setPlan(getValue.apply("offerPlanId"));
        essential.processType = ProcessType.valueOf(getValue.apply("processType").toUpperCase());
        getEntry(planId).paymentMode = PaymentMode.valueOf(getValue.apply("paymentMethod").toUpperCase());

        if (params.stream().anyMatch(p -> p.getName().equals("invoiceType"))) {
            selectedInvoiceType = InvoiceType.valueOf(getValue.apply("invoiceType").toUpperCase());
        }

        updatePlanCartPromotion();
    }

    public Promotion getPromotion() {
        return allPromotionResults;
    }

    public ClaroChip getClaroChip() {
        return claroChip;
    }

    public Essential.User getUser() {
        return essential.user;
    }

    public void setTelephone(String msisdn) {
        essential.telephone = msisdn;
    }

    public void populateCustomerProductDetails() {
        JsonNode response = getCustomerProductDetails(essential.user.claroTelephone);

        //User
        getUser().name = response.get("product").get("customerName").toString();
        getUser().displayName = essential.user.name;
        getUser().cpf = response.get("product").get("cpf").toString();

        if (response.get("discountValue") != null) {
            getUser().claroClubBalance = response.get("discountValue").asDouble();
        }

        //User.ClaroSubscription
        getUser().claroSubscription = new Essential.User.ClaroSubscription();
        JsonNode planPrice = response.get("product").get("planPrice");
        getUser().claroSubscription.planTypePrice = planPrice.get("planTypePrice").toString();

        if (!getUser().claroSubscription.planTypePrice.equals("PRE_PAGO")) {
            getUser().claroSubscription.claroPlan = planPrice.get("id").toString();
            getUser().claroSubscription.claroPlanName = planPrice.get("name").toString();
            getUser().claroSubscription.claroPlanPrice = planPrice.get("planValue").asDouble();
            getUser().claroSubscription.claroMonthlyPrice = response.get("product").get("netSubscriberValue").asDouble();
            getUser().claroSubscription.customerMobileSubType = response.get("product").get("customerMobileSubType").toString();
            getUser().claroSubscription.loyalty = response.get("loyalty") != null;
        }

        //Address
        //TODO
    }

    public static class ClaroChip {

        private String activationCode;
        private ChipType claroChipType;
        private String iccIdSim;
        private String qrCdode;
        private String techlonogy;

        private ClaroChip() {}

        public ChipType getChipType() {
            return claroChipType;
        }

        public void setChipType(ChipType chip) {
            claroChipType = chip;
        }
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

        private String code;
        private final User user;
        private String telephone;
        private ProcessType processType;

        private Essential() {
            user = new Essential.User();
        }

        public static class User {

            private User() {}

            private String name;
            private String displayName;
            private String parentfullname;
            private String claroTelephone;
            private String telephone;
            private String claroProvisionalTelephone;
            private String birthdate;
            private String cpf;
            private String email;
            private boolean optinWhatsapp;
            private String type;
            private double claroClubBalance;
            private ClaroSubscription claroSubscription;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public String getParentfullname() {
                return parentfullname;
            }

            public void setParentfullname(String parentfullname) {
                this.parentfullname = parentfullname;
            }

            public String getClaroTelephone() {
                return claroTelephone;
            }

            public void setClaroTelephone(String claroTelephone) {
                this.claroTelephone = claroTelephone;
                telephone = claroTelephone;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getClaroProvisionalTelephone() {
                return claroProvisionalTelephone;
            }

            public void setClaroProvisionalTelephone(String claroProvisionalTelephone) {
                this.claroProvisionalTelephone = claroProvisionalTelephone;
            }

            public String getBirthdate() {
                return birthdate;
            }

            public void setBirthdate(String birthdate) {
                this.birthdate = birthdate;
            }

            public String getCpf() {
                return cpf;
            }

            public void setCpf(String cpf) {
                this.cpf = cpf;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public boolean isOptinWhatsapp() {
                return optinWhatsapp;
            }

            public String getType() {
                return type;
            }

            public double getClaroClubBalance() {
                return claroClubBalance;
            }

            public ClaroSubscription getClaroSubscription() {
                return claroSubscription;
            }

            public static final class ClaroSubscription {

                private ClaroSubscription() {}

                private String claroPlan;
                private String claroPlanName;
                private double claroPlanPrice;
                private double claroMonthlyPrice;
                private String customerMobileSubType;
                private boolean loyalty;
                private String planTypePrice;

                public String getClaroPlan() {
                    return claroPlan;
                }

                public String getClaroPlanName() {
                    return claroPlanName;
                }

                public double getClaroPlanPrice() {
                    return claroPlanPrice;
                }

                public String getCustomerMobileSubType() {
                    return customerMobileSubType;
                }

                public boolean isLoyalty() {
                    return loyalty;
                }

                public String getPlanTypePrice() {
                    return planTypePrice;
                }

                public double getClaroMonthlyPrice() {
                    return claroMonthlyPrice;
                }
            }
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

            public PaymentMode getPaymentMode() {
                return paymentMode;
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

    public static final class Promotion {

        private Promotion() {}

        @JsonProperty("code")
        private String code;

        @JsonProperty("dddList")
        private List<Integer> dddList;

        @JsonProperty("discountValue")
        private int discountValue;

        @JsonProperty("fixedDiscount")
        private boolean fixedDiscount;

        @JsonProperty("loyalty")
        private boolean loyalty;

        @JsonProperty("name")
        private String name;

        @JsonProperty("paymentMethod")
        private PaymentMode paymentMethod;

        @JsonProperty("priority")
        private int priority;

        @JsonProperty("processTypeList")
        private List<String> processTypeList;

        @JsonProperty("rentabilizationCampaign")
        private boolean rentabilizationCampaign;

        public String getName() {
            return name;
        }

        public boolean isRentabilization() {
            return rentabilizationCampaign;
        }
    }
}