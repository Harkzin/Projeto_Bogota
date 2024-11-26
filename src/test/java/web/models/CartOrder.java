package web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.HttpStatusException;
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
        claroClube = new ClaroClube();
        appliedCouponCodes = new ArrayList<>();
        dependentsInformation = new ArrayList<>();
    }

    private boolean thab;

    private String planId;
    private String deviceId;

    public boolean isDebitPaymentFlow;
    public boolean hasErrorPasso1 = false;

    @JsonProperty("essential")
    private final Essential essential;

    @JsonProperty("positionsAndPrices")
    private final PositionsAndPrices positionsAndPrices;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("payment")
    private final Payment payment;

    @JsonProperty("delivery")
    private final Delivery delivery;

    @JsonProperty("claroChip")
    private ClaroChip claroChip;

    @JsonProperty("claroClube")
    private ClaroClube claroClube;

    @JsonProperty("claroSapResponse")
    private ClaroSapResponse claroSapResponse;

    @JsonProperty("subOrder")
    private SubOrder subOrder;

    @JsonProperty("abandonedCartOrder")
    private boolean abandonedCartOrder;

    @JsonProperty("acceptFine")
    private boolean acceptFine;

    @JsonProperty("contingencyStepOne")
    private boolean contingencyStepOne;

    @JsonProperty("isPreSale")
    private boolean isPreSale;

    @JsonProperty("minhaClaroOrder")
    private boolean minhaClaroOrder;

    @JsonProperty("offerRealized")
    private boolean offerRealized;

    @JsonProperty("passedByClearSale")
    private boolean passedByClearSale;

    @JsonProperty("claroDdd")
    private int claroDdd;

    @JsonProperty("allPromotionResults")
    private Promotion allPromotionResults;

    @JsonProperty("appliedCouponCodes")
    private final List<String> appliedCouponCodes;

    @JsonProperty("children")
    private String children;

    @JsonProperty("chosenPlan")
    private String chosenPlan;

    @JsonProperty("claroLegacyOrderId")
    private String claroLegacyOrderId;

    @JsonProperty("creationtime")
    private String creationtime;

    @JsonProperty("dayInvoiceExpiration")
    private String dayInvoiceExpiration;

    @JsonProperty("dependentsInformation")
    private final List<DependentsInformation> dependentsInformation;

    @JsonProperty("eaTicket")
    private String eaTicket;

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("gradePlan")
    private String gradePlan;

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("journeyInformation")
    private String journeyInformation;

    @JsonProperty("offeredPlan")
    private String offeredPlan;

    @JsonProperty("orderTrackingUrl")
    private String orderTrackingUrl;

    @JsonProperty("portabilityClaroTicket")
    private String portabilityClaroTicket;

    @JsonProperty("rentabilizationCoupon")
    private String rentabilizationCoupon;

    @JsonProperty("selectedInvoiceType")
    private InvoiceType selectedInvoiceType;

    //######################################################################

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

        PositionsAndPrices.Entry planEntry = new PositionsAndPrices.Entry(plan, 1, planFullPrice, promoDiscount);
        planEntry.paymentMode = TICKET; //Pagamento default atual
        positionsAndPrices.entries.add(planEntry);
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
        if (essential.processType != APARELHO_TROCA_APARELHO || !getEntry(deviceId).bpo.equals("PPV")) { //Fluxo Aparelhos [Manter o Plano sem Fid] não existe promo
            try {
                allPromotionResults = objMapper.readValue(getPlanCartPromotion(guid), Promotion.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            PositionsAndPrices.Entry planEntry = getEntry(planId);
            planEntry.totalPrice = getPlan().getPrice() - allPromotionResults.discountValue;
            planEntry.paymentMode = allPromotionResults.paymentMethod;
        }
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

    public void populateCustomerProductDetails() throws HttpStatusException {
        JsonNode response = customerProductDetailsRequest(essential.user.claroTelephone);
        JsonNode productNodeResponse = response.path("product");

        //User
        getUser().name = productNodeResponse.get("customerName").asText();
        getUser().displayName = essential.user.name;
        getUser().cpf = productNodeResponse.get("cpf").asText();

        if (response.get("discountValue") != null) {
            getUser().claroClubBalance = response.get("discountValue").asDouble();
        }

        //User.ClaroSubscription
        JsonNode planPriceNodeResponse = productNodeResponse.path("planPrice");
        getUser().claroSubscription = new Essential.User.ClaroSubscription();
        getUser().claroSubscription.planTypePrice = planPriceNodeResponse.get("planTypePrice").asText();

        if (!getUser().claroSubscription.planTypePrice.equals("PRE_PAGO")) {
            getUser().claroSubscription.claroPlan = planPriceNodeResponse.get("id").asText();
            getUser().claroSubscription.claroPlanName = planPriceNodeResponse.get("name").asText();
            getUser().claroSubscription.claroPlanPrice = planPriceNodeResponse.get("planValue").asDouble();
            getUser().claroSubscription.claroMonthlyPrice = productNodeResponse.get("netSubscriberValue").asDouble();
            getUser().claroSubscription.customerMobileSubType = productNodeResponse.get("customerMobileSubType").asText();
            getUser().claroSubscription.loyalty = response.get("loyalty") != null;

            updatePlanAndDevicePrice(getUser().getClaroSubscription().getClaroPlan());
        } else {
            updatePlanAndDevicePrice(focusPlan); //Para cliente Pré será exibido apenas planos Controle, com o focusPlan setado por padrão (considerando que o plano foco é o primeiro Controle)
        }

        //Address
        //TODO
    }

    public ClaroClube getClaroClube() {
        return claroClube;
    }

    //###########################################################################

    public static class ClaroChip {

        @JsonProperty("activationCode")
        private String activationCode;

        @JsonProperty("claroChipType")
        private ChipType claroChipType;

        @JsonProperty("iccIdSim")
        private String iccIdSim;

        @JsonProperty("qrCdode")
        private String qrCdode;

        @JsonProperty("technology")
        private String technology;

        private ClaroChip() {}

        //########################################

        public ChipType getChipType() {
            return claroChipType;
        }

        public void setChipType(ChipType chip) {
            claroChipType = chip;
        }
    }

    public static class ClaroClube {

        @JsonProperty("awardPoints")
        private int awardPoints;

        @JsonProperty("discountValue")
        private double discountValue;

        @JsonProperty("isClaroClubeApplied")
        private boolean isClaroClubeApplied;

        @JsonProperty("redeemId")
        private String redeemId;

        @JsonProperty("redeemed")
        private boolean redeemed;

        @JsonProperty("refund")
        private boolean refund;

        @JsonProperty("reserved")
        private boolean reserved;

        @JsonProperty("used")
        private boolean used;

        private ClaroClube() {}

        //########################################

        public int getAwardPoints() {
            return awardPoints;
        }

        public double getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(double discountValue) {
            this.discountValue = discountValue;
        }

        public boolean isClaroClubeApplied() {
            return isClaroClubeApplied;
        }

        public void setClaroClubeApplied(boolean claroClubeApplied) {
            isClaroClubeApplied = claroClubeApplied;
        }

        public String getRedeemId() {
            return redeemId;
        }

        public boolean isRedeemed() {
            return redeemed;
        }

        public boolean isRefund() {
            return refund;
        }

        public boolean isReserved() {
            return reserved;
        }

        public boolean isUsed() {
            return used;
        }
    }

    public static class ClaroSapResponse {

        @JsonProperty("center")
        private String center;

        @JsonProperty("invoiceNumber")
        private String invoiceNumber;

        @JsonProperty("nfeNumber")
        private String nfeNumber;

        @JsonProperty("salesOrg")
        private String salesOrg;

        @JsonProperty("sapOrderId")
        private String sapOrderId;

        @JsonProperty("sapStatusHistory")
        private List<SapStatusHistory> sapStatusHistory;

        private ClaroSapResponse() {}

        //########################################

        public static class SapStatusHistory {

            @JsonProperty("date")
            private String date;

            @JsonProperty("id")
            private String id;

            @JsonProperty("description")
            private String description;

            @JsonProperty("orderType")
            private String orderType;

            private SapStatusHistory() {}

            //########################################
        }
    }

    public static class Delivery {

        @JsonProperty("deliveryAddress")
        private DeliveryAddress deliveryAddress;

        @JsonProperty("deliveryMode")
        private DeliveryMode deliveryMode;

        private Delivery() {
            deliveryAddress = new Delivery.DeliveryAddress();
        }

        //########################################

        public static class DeliveryAddress {

            @JsonProperty("streetname")
            private String streetname;

            @JsonProperty("streetnumber")
            private String streetnumber;

            @JsonProperty("building")
            private String building;

            @JsonProperty("postalcode")
            private String postalcode;

            @JsonProperty("town")
            private String town;

            @JsonProperty("stateCode")
            private String stateCode;

            @JsonProperty("neighbourhood")
            private String neighbourhood;

            @JsonProperty("shippingAddress")
            private boolean shippingAddress;

            @JsonProperty("billingAddress")
            private boolean billingAddress;

            private DeliveryAddress() {}

            //########################################
        }
    }

    public static class DependentsInformation {

        @JsonProperty("id")
        private String id;

        @JsonProperty("msisdn")
        private String msisdn;

        @JsonProperty("processTypeOfDependent")
        private ProcessType processTypeOfDependent;

        private DependentsInformation() {}

        //########################################
    }

    public static class Essential {

        @JsonProperty("code")
        private String code;

        @JsonProperty("user")
        private final User user;

        @JsonProperty("telephone")
        private String telephone;

        @JsonProperty("processType")
        private ProcessType processType;

        private Essential() {
            user = new Essential.User();
        }

        //########################################

        public static class User {

            @JsonProperty("name")
            private String name;

            @JsonProperty("displayName")
            private String displayName;

            @JsonProperty("parentfullname")
            private String parentfullname;

            @JsonProperty("claroTelephone")
            private String claroTelephone;

            @JsonProperty("telephone")
            private String telephone;

            @JsonProperty("claroProvisionalTelephone")
            private String claroProvisionalTelephone;

            @JsonProperty("birthdate")
            private String birthdate;

            @JsonProperty("cpf")
            private String cpf;

            @JsonProperty("email")
            private String email;

            @JsonProperty("optinWhatsapp")
            private boolean optinWhatsapp;

            @JsonProperty("type")
            private String type;

            @JsonProperty("claroClubBalance")
            private double claroClubBalance;

            @JsonProperty("claroSubscription")
            private ClaroSubscription claroSubscription;

            private User() {}

            //########################################

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

                @JsonProperty("claroPlan")
                private String claroPlan;

                @JsonProperty("claroPlanName")
                private String claroPlanName;

                @JsonProperty("claroPlanPrice")
                private double claroPlanPrice;

                @JsonProperty("claroMonthlyPrice")
                private double claroMonthlyPrice;

                @JsonProperty("customerMobileSubType")
                private String customerMobileSubType;

                @JsonProperty("loyalty")
                private boolean loyalty;

                @JsonProperty("planTypePrice")
                private String planTypePrice;

                private ClaroSubscription() {}

                //########################################

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

        @JsonProperty("paymentAddress")
        private PaymentAddress paymentAddress;

        @JsonProperty("paymentInfo")
        private PaymentInfo paymentInfo;

        @JsonProperty("paymentMethods")
        private List<PaymentMethod> paymentMethods;

        @JsonProperty("iframePaymentAcquirer")
        private boolean iframePaymentAcquirer;

        @JsonProperty("iframePaymentResult")
        private String iframePaymentResult;

        @JsonProperty("claroAuthenticationPaymentResponse")
        private ClaroAuthenticationPaymentResponse claroAuthenticationPaymentResponse;

        @JsonProperty("pixPaymentInfo")
        private PixPaymentInfo pixPaymentInfo;

        private Payment() {}

        //########################################

        public static class PaymentAddress {

            @JsonProperty("streetname")
            private String streetname;

            @JsonProperty("streetnumber")
            private String streetnumber;

            @JsonProperty("building")
            private String building;

            @JsonProperty("postalcode")
            private String postalcode;

            @JsonProperty("town")
            private String town;

            @JsonProperty("stateCode")
            private String stateCode;

            @JsonProperty("neighbourhood")
            private String neighbourhood;

            @JsonProperty("shippingAddress")
            private boolean shippingAddress;

            @JsonProperty("billingAddress")
            private boolean billingAddress;

            private PaymentAddress() {}

            //########################################
        }

        public static class PaymentInfo {

            @JsonProperty("account")
            private String account;

            @JsonProperty("agency")
            private String agency;

            @JsonProperty("bank")
            private String bank;

            @JsonProperty("expireDate")
            private int expireDate;

            @JsonProperty("invoiceType")
            private String invoiceType;

            private PaymentInfo() {}

            //########################################
        }

        public static class PaymentMethod {

            @JsonProperty("paymentMode")
            private String paymentMode;

            @JsonProperty("paymentMethodType")
            private String paymentMethodType;

            private PaymentMethod() {}

            //########################################
        }

        public static class ClaroAuthenticationPaymentResponse {

            @JsonProperty("amount")
            private String amount;

            @JsonProperty("card")
            private String card;

            @JsonProperty("flag")
            private String flag;

            @JsonProperty("numberInstallments")
            private String numberInstallments;

            @JsonProperty("responseDescription")
            private String responseDescription;

            private ClaroAuthenticationPaymentResponse() {}

            //########################################
        }

        public static class PixPaymentInfo {

            @JsonProperty("txId")
            private String txId;

            @JsonProperty("value")
            private String value;

            private PixPaymentInfo() {}

            //########################################
        }
    }

    public static class PositionsAndPrices {

        @JsonProperty("entries")
        private List<Entry> entries;

        @JsonProperty("entryGroups")
        private List<String> entryGroups;

        @JsonProperty("totalPrice")
        private double totalPrice;

        private PositionsAndPrices() {
            entries = new ArrayList<>();
            entryGroups = new ArrayList<>();
        }

        //########################################

        public static class Entry {

            @JsonProperty("product")
            private Product product;

            @JsonProperty("quantity")
            private int quantity;

            @JsonProperty("basePrice")
            private double basePrice;

            @JsonProperty("totalPrice")
            private double totalPrice;

            @JsonProperty("discountValues")
            private final List<Double> discountValues = new ArrayList<>();

            @JsonProperty("entryNumber")
            private int entryNumber;

            @JsonProperty("paymentMode")
            private PaymentMode paymentMode;

            @JsonProperty("bpo")
            private String bpo;

            @JsonProperty("status")
            private String status;

            private Entry() {}

            private Entry(Product product, int quantity, double basePrice, double discount) {
                this.product = product;
                this.quantity = quantity;
                this.basePrice = basePrice;

                discountValues.add(discount);
                totalPrice = basePrice - discount;
            }

            //########################################

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

        @JsonProperty("status")
        private String status;

        @JsonProperty("orderProcess")
        private List<OrderProcess> orderProcess;

        private Status() {}

        //########################################

        public static class OrderProcess {

            @JsonProperty("processDefinitionName")
            private String processDefinitionName;

            @JsonProperty("state")
            private String state;

            @JsonProperty("taskLogs")
            private List<TaskLog> taskLogs;

            private OrderProcess() {}

            //########################################

            public static class TaskLog {

                @JsonProperty("actionId")
                private String actionId;

                @JsonProperty("startDate")
                private String startDate;

                @JsonProperty("endDate")
                private String endDate;

                @JsonProperty("returnCode")
                private String returnCode;

                private TaskLog() {}

                //########################################
            }
        }
    }

    public static class SubOrder {

        @JsonProperty("status")
        private String status;

        @JsonProperty("statusDescription")
        private String statusDescription;

        @JsonProperty("type")
        private String type;

        private SubOrder() {}

        //########################################
    }

    public static final class Promotion {

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

        private Promotion() {}

        //########################################

        public String getName() {
            return name;
        }

        public boolean isRentabilization() {
            return rentabilizationCampaign;
        }

        public int getDiscountValue() {
            return discountValue;
        }
    }
}