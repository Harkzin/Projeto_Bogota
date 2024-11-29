package web.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.HttpStatusException;
import web.models.product.DeviceProduct;
import web.models.product.GenericProduct;
import web.models.product.PlanProduct;
import web.models.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static web.support.api.RestAPI.*;
import static web.support.utils.Constants.*;
import static web.support.utils.Constants.InvoiceType.*;
import static web.support.utils.Constants.StandardPaymentMode.*;
import static web.support.utils.Constants.ProcessType.*;

public class CartOrder {

    private String planId;
    private String deviceId;

    public boolean isDebitPaymentFlow; //TODO
    public boolean hasErrorPasso1 = false; //TODO

    // Essential -------------------------------------------------
    @JsonProperty("essential")
    private final Essential essential;


    // Positions and Prices --------------------------------------
    @JsonProperty("positionsAndPrices")
    private final PositionsAndPrices positionsAndPrices;


    // Dependent -------------------------------------------------
    @JsonProperty("dependentsInformation")
    private final List<DependentsInformation> dependentsInformation;


    // Payment and Delivery --------------------------------------
    @JsonProperty("status")
    private Status status;

    @JsonProperty("payment")
    private final Payment payment;

    @JsonProperty("delivery")
    private final Delivery delivery;


    // Order History ---------------------------------------------
    @JsonProperty("acceptFine")
    private boolean acceptFine;


    // Administration --------------------------------------------
    @JsonProperty("creationtime")
    private String creationtime;

    @JsonProperty("abandonedCartOrder")
    private boolean abandonedCartOrder;

    @JsonIgnore
    private PromotionSourceRule allPromotionResults; //Modificado de RuleBasedPromotion para PromotionSourceRule

    @JsonProperty("appliedCouponCodes")
    private final List<String> appliedCouponCodes;

    @JsonProperty("children")
    private String children;

    @JsonProperty("chosenPlan")
    private String chosenPlan;

    @JsonProperty("claroChip")
    private ClaroChip claroChip;

    @JsonProperty("claroClube")
    private ClaroClubeInfo claroClube;

    @JsonProperty("claroDdd")
    private int claroDdd;

    @JsonProperty("claroLegacyOrderId")
    private String claroLegacyOrderId;

    @JsonProperty("claroSapResponse")
    private ClaroSapResponse claroSapResponse;

    @JsonProperty("comboMultiContract")
    private CustomerContractResidential comboMultiContract;

    @JsonProperty("contingencyStepOne")
    private boolean contingencyStepOne;

    @JsonProperty("dayInvoiceExpiration")
    private String dayInvoiceExpiration;

    //TODO eSimActivationUrl ?

    @JsonProperty("eaTicket")
    private String eaTicket;

    //TODO eSimTermsAccept ?

    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("gradePlan")
    private String gradePlan;

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("isPreSale")
    private boolean isPreSale;

    @JsonProperty("isTheComboMultiFlowForProspectMovelCustomer")
    private boolean isTheComboMultiFlowForProspectMovelCustomer;

    @JsonProperty("journeyInformation")
    private String journeyInformation;

    @JsonProperty("minhaClaroOrder")
    private boolean minhaClaroOrder;

    @JsonProperty("offerRealized")
    private boolean offerRealized;

    @JsonProperty("offeredPlan")
    private String offeredPlan;

    @JsonProperty("orderTrackingUrl")
    private String orderTrackingUrl;

    @JsonProperty("passedByClearSale")
    private boolean passedByClearSale;

    @JsonProperty("portabilityClaroTicket")
    private String portabilityClaroTicket;

    @JsonProperty("rentabilizationCoupon")
    private String rentabilizationCoupon;

    @JsonProperty("selectedInvoiceType")
    private InvoiceType selectedInvoiceTypes;

    @JsonProperty("subOrder")
    private SubOrder subOrder;

    @JsonProperty("thab")
    private boolean thab;

    public CartOrder() {
        essential = new Essential();
        positionsAndPrices = new PositionsAndPrices();
        payment = new Payment();
        delivery = new Delivery();
        claroChip = new ClaroChip();
        claroClube = new ClaroClubeInfo();

        appliedCouponCodes = new ArrayList<>();
        dependentsInformation = new ArrayList<>();
    }


    //##################################################################################
    // Getter - Setter #################################################################

    // Essential -------------------------------------------------
    public Essential.Customer getUser() {
        return essential.user;
    } //Getter only


    //code
    public String getCode() {
        return essential.code;
    }

    public void setCode(String code) {
        essential.code = code;
    }


    //telephone
    public String getTelephone() {
        return essential.telephone;
    }

    public void setTelephone(String telephone) {
        essential.telephone = telephone;
    }


    //processType
    public void setProcessType(ProcessType processType) {
        essential.processType = processType;
    }

    public ProcessType getProcessType() {
        return essential.processType;
    }


    // Positions and Prices --------------------------------------
    public List<PositionsAndPrices.OrderEntry> getEntries() {
        return positionsAndPrices.entries;
    } //Getter only

    public List<String> getEntryGroups() {
        return positionsAndPrices.entryGroups;
    } //Getter only

    public double getTotalPrice() {
        return positionsAndPrices.totalPrice;
    } //Getter only


    // Payment and Delivery --------------------------------------
        // Status
    public String getStatus() {
        return status.status;
    } //Getter only

    public List<Status.OrderProcess> getOrderProcess() {
        return status.orderProcess;
    } //Getter only

        // Payment
    public Address getPaymentAddress() {
        return payment.paymentAddress;
    } //Getter only

    public Payment.ClaroPaymentInfo getPaymentInfo() {
        return payment.paymentInfo;
    } //Getter only

    public List<Payment.ClaroPaymentMethod> getPaymentMethods() {
        return payment.paymentMethods;
    } //Getter only

    //TODO devicePaymentInfo ?

    public boolean isIframePaymentAcquirer() {
        return payment.iframePaymentAcquirer;
    } //Getter only

    public String getIframePaymentResult() {
        return payment.iframePaymentResult;
    } //Getter only

    public Payment.ClaroAuthenticationPaymentResponse getClaroAuthenticationPaymentResponse() {
        return payment.claroAuthenticationPaymentResponse;
    } //Getter only

    public Payment.PixPaymentInfo getPixPaymentInfo() {
        return payment.pixPaymentInfo;
    } //Getter only

        // Delivery
    public Address getDeliveryAddress() {
        return delivery.deliveryAddress;
    }  //Getter only

    public void setDeliveryMode(ZoneDeliveryMode deliveryMode) {
        delivery.deliveryMode = deliveryMode;
    }

    public ZoneDeliveryMode getDeliveryMode() {
        return delivery.deliveryMode;
    }


    // Order History ---------------------------------------------
    public boolean isAcceptFine() {
        return acceptFine;
    }

    public void setAcceptFine(boolean acceptFine) {
        this.acceptFine = acceptFine;
    }


    // Administration --------------------------------------------
    public boolean isAbandonedCartOrder() {
        return abandonedCartOrder;
    } //Getter only

    public PromotionSourceRule getPromotion() { // allPromotionResults -> PromotionResult.promotion - child Order only
        return allPromotionResults;
    } //Getter only

    public String getChildren() {
        return children;
    } //Getter only


    //chosenPlan
    public String getChosenPlan() {
        return chosenPlan;
    }

    public void setChosenPlan(String chosenPlan) {
        this.chosenPlan = chosenPlan;
    }


    public ClaroChip getClaroChip() {
        return claroChip;
    } //Getter only

    public ClaroClubeInfo getClaroClube() {
        return claroClube;
    } //Getter only


    //claroDdd
    public int getClaroDdd() {
        return claroDdd;
    }

    public void setClaroDdd(int ddd) {
        claroDdd = ddd;
    }


    public String getClaroLegacyOrderId() {
        return claroLegacyOrderId;
    } //Getter only

    //TODO claroPixClearSaleResponse ?

    //TODO claroPixTransactionStatusResponse ?

    public ClaroSapResponse getClaroSapResponse() {
        return claroSapResponse;
    } //Getter only

    public CustomerContractResidential getComboMultiContract() {
        return comboMultiContract;
    } //Getter only


    //contingencyStepOne
    public boolean isContingencyStepOne() {
        return contingencyStepOne;
    }

    public void setContingencyStepOne(boolean contingencyStepOne) {
        this.contingencyStepOne = contingencyStepOne;
    }


    //dayInvoiceExpiration
    public String getDayInvoiceExpiration() {
        return dayInvoiceExpiration;
    }

    public void setDayInvoiceExpiration(String dayInvoiceExpiration) {
        this.dayInvoiceExpiration = dayInvoiceExpiration;
    }


    public String getEaTicket() {
        return eaTicket;
    } //Getter only

    public String getEventId() {
        return eventId;
    } //Getter only

    public String getGradePlan() {
        return gradePlan;
    } //Getter only


    //guid
    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }


    //isPreSale
    public boolean isPreSale() {
        return isPreSale;
    }

    public void setPreSale(boolean preSale) {
        isPreSale = preSale;
    }


    //isTheComboMultiFlowForProspectMovelCustomer
    public boolean isTheComboMultiFlowForProspectMovelCustomer() {
        return isTheComboMultiFlowForProspectMovelCustomer;
    }

    public void setTheComboMultiFlowForProspectMovelCustomer(boolean isTheComboMultiFlowForProspectMovelCustomer) {
        this.isTheComboMultiFlowForProspectMovelCustomer = isTheComboMultiFlowForProspectMovelCustomer;
    }


    //journeyInformation
    public String getJourneyInformation() {
        return journeyInformation;
    }

    public void setJourneyInformation(String journeyInformation) {
        this.journeyInformation = journeyInformation;
    }


    //minhaClaroOrder
    public boolean isMinhaClaroOrder() {
        return minhaClaroOrder;
    }

    public void setMinhaClaroOrder(boolean minhaClaroOrder) {
        this.minhaClaroOrder = minhaClaroOrder;
    }


    public boolean isOfferRealized() {
        return offerRealized;
    } //Getter only

    public String getOfferedPlan() {
        return offeredPlan;
    } //Getter only

    public String getOrderTrackingUrl() {
        return orderTrackingUrl;
    } //Getter only

    public boolean isPassedByClearSale() {
        return passedByClearSale;
    } //Getter only

    public String getPortabilityClaroTicket() {
        return portabilityClaroTicket;
    } //Getter only

    public String getRentabilizationCoupon() {
        return rentabilizationCoupon;
    } //Getter only


    //selectedInvoiceType
    public InvoiceType getSelectedInvoiceType() {
        return selectedInvoiceTypes;
    }

    @JsonIgnore
    public void setSelectedInvoiceType(InvoiceType invoiceType) {
        selectedInvoiceTypes = invoiceType;

        updatePlanCartPromotion();
    }


    public SubOrder getSubOrder() {
        return subOrder;
    } //Getter only


    //thab
    public boolean isThab() {
        return thab;
    }

    public void setThab() {
        thab = true;
    }


    //##################################################################################
    // Business Logic ##################################################################

    //Product
    private <T> T createProduct(String id, Class<T> cls) {
        try {
            return objMapper.readValue(getProductDetails(id), cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Verificar se a service [API] do ambiente esta funcionando (acessar Swagger).\n" + e);
        }
    }

    private void removeProduct(String id) {
        positionsAndPrices.entries.remove(positionsAndPrices.entries.stream()
                .filter(e -> e.product.getCode().equals(id))
                .findFirst()
                .orElseThrow());
    }

    private Product getProduct(String id) {
        return positionsAndPrices.entries.stream()
                .map(e ->  e.product)
                .filter(product -> product.getCode().equals(id))
                .findFirst().orElseThrow();
    }


    //Plan
    public PlanProduct getPlan() {
        return (PlanProduct) getProduct(planId);
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
                promoDiscount = plan.getPrice(isDebitPaymentFlow, selectedInvoiceTypes == PRINTED) - plan.getPrice();
            }
        }

        PositionsAndPrices.OrderEntry planEntry = new PositionsAndPrices.OrderEntry(plan, 1, planFullPrice, promoDiscount);
        planEntry.paymentMode = TICKET; //Pagamento default atual
        positionsAndPrices.entries.add(planEntry);
    }

    public void updatePlanAndDevicePrice(String planId) {
        PositionsAndPrices.OrderEntry deviceEntry = getEntry(deviceId);

        getDevice().setDevicePriceInfo(deviceEntry.bpo, planId, "1100"); //SalesOrg fixo para SP 11, sem regionalização implementada.
        double price = getDevice().hasCampaignPrice() ? getDevice().getCampaignPrice(true) : getDevice().getPrice(); //Caso não exista linha de preço configurada = preço full
        deviceEntry.basePrice = price;
        deviceEntry.totalPrice = price;

        setPlan(planId);
    }


    //Device
    public DeviceProduct getDevice() {
        return (DeviceProduct) getProduct(deviceId);
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

        PositionsAndPrices.OrderEntry deviceEntry = new PositionsAndPrices.OrderEntry(device, 1, basePrice, 0D);
        deviceEntry.bpo = defaultCampaign;
        positionsAndPrices.entries.add(deviceEntry);

        setPlan(focusPlan);
    }

    public void addVoucherForDevice(String voucher) {
        double amount = 100D; //TODO Mock para CUPOM100. Valor deve vir da API ECCMAUT-806
        getEntry(deviceId).setDiscount(amount);
        appliedCouponCodes.add(voucher);
    }


    //Dependent
    public int dependentQuantity() {
        return dependentsInformation.size();
    }

    private void addDependent(String id, String msisdn, ProcessType processType) {
        if (dependentQuantity() == 0) { //Cria entry caso seja o primeiro dependente
            Product dependente = createProduct("dependente", PlanProduct.class);
            positionsAndPrices.entries.add(new PositionsAndPrices.OrderEntry(dependente, 1, dependente.getPrice(), 0D));
        } else { //Atualiza a quantidade e preço na entry caso já tenha dependentes adicionados
            PositionsAndPrices.OrderEntry depEntry = getEntry("dependente");
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


    //Misc
    public PositionsAndPrices.OrderEntry getEntry(String productId) {
        return positionsAndPrices.entries.stream()
                .filter(e -> e.product.getCode().equals(productId))
                .findFirst().orElseThrow();
    }

    public boolean isDeviceCart() {
        return !(deviceId == null);
    }

    public boolean hasLoyalty() {
        if (!isDeviceCart()) {
            return allPromotionResults.loyalty;
        } else {
            return !getEntry(deviceId).bpo.equals("PPV"); //Para Aparelhos, a única opção sem fid é no fluxo de base [Manter o Plano sem Fid], campanha PPV
        }
    }

    public String getAppliedCouponCodes() { //Only 1 coupon
        if (!appliedCouponCodes.isEmpty()) {
            return appliedCouponCodes.get(0);
        }
        return null;
    }

    public void updatePlanCartPromotion() {
        if (essential.processType != APARELHO_TROCA_APARELHO || !getEntry(deviceId).bpo.equals("PPV")) { //Fluxo Aparelhos [Manter o Plano sem Fid] não existe promo
            try {
                allPromotionResults = objMapper.readValue(getPlanCartPromotion(guid), PromotionSourceRule.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            PositionsAndPrices.OrderEntry planEntry = getEntry(planId);
            planEntry.totalPrice = getPlan().getPrice() - allPromotionResults.discountValue;
            planEntry.paymentMode = allPromotionResults.paymentMethod;
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
        getEntry(planId).paymentMode = StandardPaymentMode.valueOf(getValue.apply("paymentMethod").toUpperCase());

        if (params.stream().anyMatch(p -> p.getName().equals("invoiceType"))) {
            selectedInvoiceTypes = InvoiceType.valueOf(getValue.apply("invoiceType").toUpperCase());
        }

        updatePlanCartPromotion();
    }

    public void populateCustomerProductDetails() throws HttpStatusException {
        Essential.Customer user = essential.user;
        JsonNode response = customerProductDetailsRequest(user.claroTelephone);
        JsonNode productNodeResponse = response.path("product");

        //User
        user.name = productNodeResponse.get("customerName").asText();
        user.displayName = essential.user.name;
        user.cpf = productNodeResponse.get("cpf").asText();

        if (response.get("discountValue") != null) {
            user.claroClubBalance = response.get("discountValue").asDouble();
        }

        //User.ClaroSubscription
        JsonNode planPriceNodeResponse = productNodeResponse.path("planPrice");
        user.claroSubscription = new Essential.Customer.ClaroSubscription();
        Essential.Customer.ClaroSubscription claroSubscription = user.claroSubscription;
        claroSubscription.planTypePrice = planPriceNodeResponse.get("planTypePrice").asText();

        if (!claroSubscription.planTypePrice.equals("PRE_PAGO")) {
            claroSubscription.claroPlan = planPriceNodeResponse.get("id").asText();
            claroSubscription.claroPlanName = planPriceNodeResponse.get("name").asText();
            claroSubscription.claroPlanPrice = planPriceNodeResponse.get("planValue").asDouble();
            claroSubscription.claroMonthlyPrice = productNodeResponse.get("netSubscriberValue").asDouble();
            claroSubscription.customerMobileSubType = productNodeResponse.get("customerMobileSubType").asText();
            claroSubscription.loyalty = response.get("loyalty") != null;

            updatePlanAndDevicePrice(user.getClaroSubscription().getClaroPlan());
        } else {
            updatePlanAndDevicePrice(focusPlan); //Para cliente Pré será exibido apenas planos Controle, com o focusPlan setado por padrão (considerando que o plano foco é o primeiro Controle)
        }

        //Address
        //TODO
    }


    //##################################################################################
    // Inner Classes ###################################################################

    // Essential -------------------------------------------------
    public static final class Essential {

        @JsonProperty("code")
        private String code;

        @JsonProperty("user")
        private final Customer user;

        @JsonProperty("telephone")
        private String telephone;

        @JsonProperty("processType")
        private ProcessType processType;

        private Essential() {
            user = new Customer();
        }

        //########################################

        public static final class Customer {

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

            private Customer() {}

            //########################################

            //name
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }


            //displayName
            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }


            //parentfullname
            public String getParentfullname() {
                return parentfullname;
            }

            public void setParentfullname(String parentfullname) {
                this.parentfullname = parentfullname;
            }


            //claroTelephone
            public String getClaroTelephone() {
                return claroTelephone;
            }

            public void setClaroTelephone(String claroTelephone) {
                this.claroTelephone = claroTelephone;
                telephone = claroTelephone;
            }


            //telephone
            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }


            public String getClaroProvisionalTelephone() {
                return claroProvisionalTelephone;
            } //Getter only


            //birthdate
            public String getBirthdate() {
                return birthdate;
            }

            public void setBirthdate(String birthdate) {
                this.birthdate = birthdate;
            }


            //cpf
            public String getCpf() {
                return cpf;
            }

            public void setCpf(String cpf) {
                this.cpf = cpf;
            }


            //email
            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }


            //optinWhatsapp
            public boolean isOptinWhatsapp() {
                return optinWhatsapp;
            }

            public void setOptinWhatsapp(boolean optinWhatsapp) {
                this.optinWhatsapp = optinWhatsapp;
            }


            public String getType() {
                return type;
            } //Getter only

            public double getClaroClubBalance() {
                return claroClubBalance;
            } //Getter only

            public ClaroSubscription getClaroSubscription() {
                return claroSubscription;
            } //Getter only

            //#####################################################

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


    // Positions and Prices --------------------------------------
    public static final class PositionsAndPrices {

        @JsonProperty("entries")
        private List<OrderEntry> entries;

        @JsonProperty("entryGroups")
        private List<String> entryGroups;

        @JsonProperty("totalPrice")
        private double totalPrice;

        private PositionsAndPrices() {
            entries = new ArrayList<>();
            entryGroups = new ArrayList<>();
        }

        //########################################

        //Getters e Setters direto na CartOrder

        //########################################

        public static final class OrderEntry {

            @JsonProperty("product")
            @JsonDeserialize(as = GenericProduct.class)
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
            private StandardPaymentMode paymentMode;

            @JsonProperty("bpo")
            private String bpo;

            @JsonProperty("status")
            private String status;

            private OrderEntry() {}

            private OrderEntry(Product product, int quantity, double basePrice, double discount) {
                this.product = product;
                this.quantity = quantity;
                this.basePrice = basePrice;

                discountValues.add(discount);
                totalPrice = basePrice - discount;
            }

            //########################################

            public Product getProduct() {
                return product;
            } //Getter only

            public int getQuantity() {
                return quantity;
            } //Getter only

            public double getBasePrice() {
                return basePrice;
            } //Getter only

            public double getTotalPrice() {
                return totalPrice;
            } //Getter only


            //discountValues
            public double getDiscount() {
                return discountValues.get(0);
            }

            private void setDiscount(double discount) {
                totalPrice -= discount;
                discountValues.set(0, discount);
            }

            public List<Double> getDiscountValues() {
                return discountValues;
            } //Getter only


            public int getEntryNumber() {
                return entryNumber;
            } //Getter only


            //bpo
            public String getBpo() {
                return bpo;
            }

            public void setBpo(String bpo) {
                this.bpo = bpo;
            }


            //paymentMode
            public StandardPaymentMode getPaymentMode() {
                return paymentMode;
            }

            public void setPaymentMode(StandardPaymentMode paymentMode) {
                this.paymentMode = paymentMode;
            }


            public String getStatus() {
                return status;
            } //Getter only
        }
    }


    // Dependent -------------------------------------------------
    public static final class DependentsInformation {

        @JsonProperty("id")
        private String id;

        @JsonProperty("msisdn")
        private String msisdn;

        @JsonProperty("processTypeOfDependent")
        private ProcessType processTypeOfDependent;

        private DependentsInformation() {}

        //########################################

        //Getters only

        public String getId() {
            return id;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public ProcessType getProcessTypeOfDependent() {
            return processTypeOfDependent;
        }
    }


    // Payment and Delivery --------------------------------------
    public static final class Status {

        @JsonProperty("status")
        private String status;

        @JsonProperty("orderProcess")
        private List<OrderProcess> orderProcess;

        private Status() {
            orderProcess = new ArrayList<>();
        }

        //########################################

        //Getters e Setters direto na CartOrder

        //########################################

        public static class OrderProcess {

            @JsonProperty("processDefinitionName")
            private String processDefinitionName;

            @JsonProperty("state")
            private String state;

            @JsonProperty("taskLogs")
            private List<ProcessTaskLog> taskLogs;

            private OrderProcess() {
                taskLogs = new ArrayList<>();
            }

            //########################################

            //Getters only

            public String getProcessDefinitionName() {
                return processDefinitionName;
            }

            public String getState() {
                return state;
            }

            public List<ProcessTaskLog> getTaskLogs() {
                return taskLogs;
            }

            //########################################

            public static final class ProcessTaskLog {

                @JsonProperty("actionId")
                private String actionId;

                @JsonProperty("startDate")
                private String startDate;

                @JsonProperty("endDate")
                private String endDate;

                @JsonProperty("returnCode")
                private String returnCode;

                private ProcessTaskLog() {}

                //########################################

                //Getters only

                public String getActionId() {
                    return actionId;
                }

                public String getEndDate() {
                    return endDate;
                }

                public String getReturnCode() {
                    return returnCode;
                }

                public String getStartDate() {
                    return startDate;
                }
            }
        }
    }

    public static final class Payment {

        @JsonProperty("paymentAddress")
        private final Address paymentAddress;

        @JsonProperty("paymentInfo")
        private final ClaroPaymentInfo paymentInfo;

        @JsonProperty("paymentMethods")
        private final List<ClaroPaymentMethod> paymentMethods;

        @JsonProperty("iframePaymentAcquirer")
        private boolean iframePaymentAcquirer;

        @JsonProperty("iframePaymentResult")
        private String iframePaymentResult;

        @JsonProperty("claroAuthenticationPaymentResponse")
        private final ClaroAuthenticationPaymentResponse claroAuthenticationPaymentResponse;

        @JsonProperty("pixPaymentInfo")
        private final PixPaymentInfo pixPaymentInfo;

        private Payment() {
            paymentAddress = new Address();
            paymentInfo = new ClaroPaymentInfo();
            paymentMethods = new ArrayList<>();
            claroAuthenticationPaymentResponse = new ClaroAuthenticationPaymentResponse();
            pixPaymentInfo = new PixPaymentInfo();
        }

        //########################################

        //Getters only

        public ClaroAuthenticationPaymentResponse getClaroAuthenticationPaymentResponse() {
            return claroAuthenticationPaymentResponse;
        }

        public boolean isIframePaymentAcquirer() {
            return iframePaymentAcquirer;
        }

        public String getIframePaymentResult() {
            return iframePaymentResult;
        }

        public Address getPaymentAddress() {
            return paymentAddress;
        }

        public ClaroPaymentInfo getPaymentInfo() {
            return paymentInfo;
        }

        public List<ClaroPaymentMethod> getPaymentMethods() {
            return paymentMethods;
        }

        public PixPaymentInfo getPixPaymentInfo() {
            return pixPaymentInfo;
        }

        //########################################

        public static final class ClaroPaymentInfo {

            @JsonProperty("account")
            private String account;

            @JsonProperty("agency")
            private String agency;

            @JsonProperty("bank")
            private String bank;

            @JsonProperty("expireDate")
            private int expireDate;

            @JsonProperty("expireDateSelected")
            private int expireDateSelected;

            @JsonProperty("invoiceType")
            private String invoiceType;

            private ClaroPaymentInfo() {}

            //########################################

            //ClaroDebitPaymentInfo.account
            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }


            //ClaroDebitPaymentInfo.agency
            public String getAgency() {
                return agency;
            }

            public void setAgency(String agency) {
                this.agency = agency;
            }


            //ClaroDebitPaymentInfo.bank
            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }


            //expireDate
            public int getExpireDate() {
                return expireDate;
            }

            public void setExpireDate(int expireDate) {
                this.expireDate = expireDate;
            }


            //expireDateSelected
            public int getExpireDateSelected() {
                return expireDateSelected;
            }

            public void setExpireDateSelected(int expireDateSelected) {
                this.expireDateSelected = expireDateSelected;
            }


            //invoiceType
            public String getInvoiceType() {
                return invoiceType;
            }

            public void setInvoiceType(String invoiceType) {
                this.invoiceType = invoiceType;
            }
        }

        public static final class ClaroPaymentMethod {

            @JsonProperty("paymentMode")
            private StandardPaymentMode paymentMode;

            @JsonProperty("paymentMethodType")
            private String paymentMethodType;

            private ClaroPaymentMethod() {}

            //########################################

            //paymentMethodType
            public String getPaymentMethodType() {
                return paymentMethodType;
            }

            public void setPaymentMethodType(String paymentMethodType) {
                this.paymentMethodType = paymentMethodType;
            }


            //paymentMode
            public StandardPaymentMode getPaymentMode() {
                return paymentMode;
            }

            public void setPaymentMode(StandardPaymentMode paymentMode) {
                this.paymentMode = paymentMode;
            }
        }

        public static final class ClaroAuthenticationPaymentResponse {

            @JsonProperty("card")
            private String card;

            @JsonProperty("flag")
            private String flag;

            @JsonProperty("numberInstallments")
            private String numberInstallments;

            @JsonProperty("responseDescription")
            private String responseDescription;

            @JsonProperty("totalAmount")
            private String totalAmount;

            private ClaroAuthenticationPaymentResponse() {}

            //########################################

            //card
            public String getCard() {
                return card;
            }

            public void setCard(String card) {
                this.card = card;
            }


            //flag
            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }


            //numberInstallments
            public String getNumberInstallments() {
                return numberInstallments;
            }

            public void setNumberInstallments(String numberInstallments) {
                this.numberInstallments = numberInstallments;
            }


            public String getResponseDescription() {
                return responseDescription;
            } //Getter only

            public String getTotalAmount() {
                return totalAmount;
            } //Getter only
        }

        public static final class PixPaymentInfo {

            @JsonProperty("txId")
            private String txId;

            @JsonProperty("value")
            private String value;

            private PixPaymentInfo() {}

            //########################################

            //Getters only

            public String getTxId() {
                return txId;
            }

            public String getValue() {
                return value;
            }
        }
    }

    public static final class Delivery {

        @JsonProperty("deliveryAddress")
        private Address deliveryAddress;

        @JsonProperty("deliveryMode")
        private ZoneDeliveryMode deliveryMode;

        private Delivery() {
            deliveryAddress = new Address();
        }

        //########################################

        public Address getDeliveryAddress() {
            return deliveryAddress;
        } //Getter only


        //deliveryMode
        public ZoneDeliveryMode getDeliveryMode() {
            return deliveryMode;
        }

        public void setDeliveryMode(ZoneDeliveryMode deliveryMode) {
            this.deliveryMode = deliveryMode;
        }
    }

    public static final class Address {

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

        private Address() {}

        //########################################

        //billingAddress
        public boolean isBillingAddress() {
            return billingAddress;
        }

        public void setBillingAddress(boolean billingAddress) {
            this.billingAddress = billingAddress;
        }


        //building
        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }


        //neighbourhood
        public String getNeighbourhood() {
            return neighbourhood;
        }

        public void setNeighbourhood(String neighbourhood) {
            this.neighbourhood = neighbourhood;
        }


        //postalcode
        public String getPostalcode() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode = postalcode;
        }


        //shippingAddress
        public boolean isShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(boolean shippingAddress) {
            this.shippingAddress = shippingAddress;
        }


        //stateCode
        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }


        //streetname
        public String getStreetname() {
            return streetname;
        }

        public void setStreetname(String streetname) {
            this.streetname = streetname;
        }


        //streetnumber
        public String getStreetnumber() {
            return streetnumber;
        }

        public void setStreetnumber(String streetnumber) {
            this.streetnumber = streetnumber;
        }


        //town
        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }
    }


    // Administration --------------------------------------------
    public static final class ClaroChip {

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

        public String getActivationCode() {
            return activationCode;
        } //Getter only


        //claroChipType
        public ChipType getChipType() {
            return claroChipType;
        }

        public void setChipType(ChipType chip) {
            claroChipType = chip;
        }


        public String getIccIdSim() {
            return iccIdSim;
        } //Getter only

        public String getQrCdode() {
            return qrCdode;
        } //Getter only

        public String getTechnology() {
            return technology;
        } //Getter only
    }

    public static final class ClaroClubeInfo {

        @JsonProperty("awardPoints")
        private int awardPoints;

        @JsonProperty("cancelled")
        private boolean cancelled;

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

        private ClaroClubeInfo() {}

        //########################################

        public int getAwardPoints() {
            return awardPoints;
        } //Getter only

        public boolean isCancelled() {
            return cancelled;
        } //Getter only


        //discountValue
        public double getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(double discountValue) {
            this.discountValue = discountValue;
        }


        //isClaroClubeApplied
        public boolean isClaroClubeApplied() {
            return isClaroClubeApplied;
        }

        public void setClaroClubeApplied(boolean claroClubeApplied) {
            isClaroClubeApplied = claroClubeApplied;
        }


        public String getRedeemId() {
            return redeemId;
        }  //Getter only

        public boolean isRedeemed() {
            return redeemed;
        }  //Getter only

        public boolean isRefund() {
            return refund;
        } //Getter only

        public boolean isReserved() {
            return reserved;
        } //Getter only

        public boolean isUsed() {
            return used;
        } //Getter only
    }

    public static final class ClaroSapResponse {

        @JsonProperty("center")
        private String center;

        @JsonProperty("invoiceNumber")
        private List<String> invoiceNumber;

        @JsonProperty("nfeNumber")
        private String nfeNumber;

        @JsonProperty("salesOrg")
        private String salesOrg;

        @JsonProperty("sapOrderId")
        private String sapOrderId;

        @JsonProperty("sapStatusHistory")
        private final List<SapStatusHistory> sapStatusHistory;

        private ClaroSapResponse() {
            invoiceNumber = new ArrayList<>();
            sapStatusHistory = new ArrayList<>();
        }

        //########################################

        //Getters only

        public String getCenter() {
            return center;
        }

        public List<String> getInvoiceNumber() {
            return invoiceNumber;
        }

        public String getNfeNumber() {
            return nfeNumber;
        }

        public String getSalesOrg() {
            return salesOrg;
        }

        public String getSapOrderId() {
            return sapOrderId;
        }

        public List<SapStatusHistory> getSapStatusHistory() {
            return sapStatusHistory;
        }

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

            //Getters only

            public String getDate() {
                return date;
            }

            public String getDescription() {
                return description;
            }

            public String getId() {
                return id;
            }

            public String getOrderType() {
                return orderType;
            }
        }
    }

    public static final class CustomerContractResidential {

        @JsonProperty("contractId")
        private String contractId;

        @JsonProperty("document")
        private String document;

        @JsonProperty("postCode")
        private String postCode;

        private CustomerContractResidential() {}

        //########################################

        //contractId
        public String getContractId() {
            return contractId;
        }

        public void setContractId(String contractId) {
            this.contractId = contractId;
        }


        //document
        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            this.document = document;
        }


        //postCode
        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }
    }

    public static final class SubOrder {

        @JsonProperty("status")
        private String status;

        @JsonProperty("statusDescription")
        private String statusDescription;

        @JsonProperty("type")
        private String type;

        private SubOrder() {}

        //########################################

        //Getters only

        public String getStatus() {
            return status;
        }

        public String getStatusDescription() {
            return statusDescription;
        }

        public String getType() {
            return type;
        }
    }

    public static final class PromotionSourceRule {

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
        private StandardPaymentMode paymentMethod;

        @JsonProperty("priority")
        private int priority;

        @JsonProperty("processTypeList")
        private List<String> processTypeList;

        @JsonProperty("rentabilizationCampaign")
        private boolean rentabilizationCampaign;

        private PromotionSourceRule() {}

        //########################################

        //Getters only

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