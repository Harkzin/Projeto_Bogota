package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

public class CheckoutStepPaymentsResponse {

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("errorList")
    private List<String> errorList = new ArrayList<>();
    @JsonProperty("message")
    private String message;
    @JsonProperty("contingency")
    private boolean contingency;
    @JsonProperty("controleFacilCart")
    private boolean controleFacilCart;
    @JsonProperty("diretrixCart")
    private boolean diretrixCart;
    @JsonProperty("controleAntecipado")
    private boolean controleAntecipado;
    @JsonProperty("isTroca")
    private boolean isTroca;
    @JsonProperty("cartGUID")
    private String cartGUID;
    @JsonProperty("processType")
    private String processType;
    @JsonProperty("validateTokenTest")
    private String validateTokenTest;
    @JsonProperty("isTokenOnlineForABrFlow")
    private boolean isTokenOnlineForABrFlow;
    @JsonProperty("isDDDabr")
    private boolean isDDDabr;
    @JsonProperty("controleFacilUrl")
    private String controleFacilUrl;
    @JsonProperty("availablebanks")
    private List<Availablebank> availablebanks = new ArrayList<Availablebank>();
    @JsonProperty("caixaaccountvalues")
    private List<Caixaaccountvalue> caixaaccountvalues = new ArrayList<Caixaaccountvalue>();
    @JsonProperty("expiredates")
    private List<Expiredate> expiredates = new ArrayList<Expiredate>();
    @JsonProperty("billingcountries")
    private List<Billingcountry> billingcountries = new ArrayList<Billingcountry>();
    @JsonProperty("cardtypes")
    private List<Cardtype> cardtypes = new ArrayList<Cardtype>();
    @JsonProperty("bestdateposition")
    private String bestdateposition;
    @JsonProperty("paymentModeList")
    private List<PaymentMode> paymentModeList = new ArrayList<PaymentMode>();
    @JsonProperty("paymentmode")
    private String paymentmode;
    @JsonProperty("hasPaymentInfo")
    private boolean hasPaymentInfo;
    @JsonProperty("paymentModeDebit")
    private boolean paymentModeDebit;
    @JsonProperty("alternativaCredito")
    private AlternativaCredito alternativaCredito;

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public String getMessage() {
        return message;
    }

    public boolean isContingency() {
        return contingency;
    }

    public boolean isControleFacilCart() {
        return controleFacilCart;
    }


    public boolean isDiretrixCart() {
        return diretrixCart;
    }


    public boolean isControleAntecipado() {
        return controleAntecipado;
    }


    public boolean isIsTroca() {
        return isTroca;
    }


    public String getCartGUID() {
        return cartGUID;
    }

    public String getProcessType() {
        return processType;
    }

    public String getValidateTokenTest() {
        return validateTokenTest;
    }

    public boolean isIsTokenOnlineForABrFlow() {
        return isTokenOnlineForABrFlow;
    }

    public boolean isIsDDDabr() {
        return isDDDabr;
    }

    public String getControleFacilUrl() {
        return controleFacilUrl;
    }

    public List<Availablebank> getAvailablebanks() {
        return availablebanks;
    }

    public List<Caixaaccountvalue> getCaixaaccountvalues() {
        return caixaaccountvalues;
    }

    public List<Expiredate> getExpiredates() {
        return expiredates;
    }

    public List<Billingcountry> getBillingcountries() {
        return billingcountries;
    }

    public List<Cardtype> getCardtypes() {
        return cardtypes;
    }

    public String getBestdateposition() {
        return bestdateposition;
    }

    public List<PaymentMode> getPaymentModeList() {
        return paymentModeList;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public boolean isHasPaymentInfo() {
        return hasPaymentInfo;
    }

    public boolean isPaymentModeDebit() {
        return paymentModeDebit;
    }

    public AlternativaCredito getAlternativaCredito() {
        return alternativaCredito;
    }


    @JsonPropertyOrder({
            "code",
            "label",
            "validationSize",
            "minimumSize",
            "analyticsCode",
            "selectedItem"
    })
    public class Availablebank {

        @JsonProperty("code")
        private String code;
        @JsonProperty("label")
        private String label;
        @JsonProperty("validationSize")
        private int validationSize;
        @JsonProperty("minimumSize")
        private int minimumSize;
        @JsonProperty("analyticsCode")
        private String analyticsCode;
        @JsonProperty("selectedItem")
        private boolean selectedItem;

        public String getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        public int getValidationSize() {
            return validationSize;
        }

        public int getMinimumSize() {
            return minimumSize;
        }

        public String getAnalyticsCode() {
            return analyticsCode;
        }

        public boolean isSelectedItem() {
            return selectedItem;
        }

    }

    @JsonPropertyOrder({
            "isocode",
            "name"
    })
    public class Billingcountry {

        @JsonProperty("isocode")
        private String isocode;
        @JsonProperty("name")
        private String name;

        public String getIsocode() {
            return isocode;
        }

        public String getName() {
            return name;
        }
    }

    @JsonPropertyOrder({
            "code",
            "label",
            "validationSize",
            "minimumSize",
            "analyticsCode",
            "selectedItem"
    })
    public class Caixaaccountvalue {

        @JsonProperty("code")
        private String code;
        @JsonProperty("label")
        private String label;
        @JsonProperty("validationSize")
        private int validationSize;
        @JsonProperty("minimumSize")
        private int minimumSize;
        @JsonProperty("analyticsCode")
        private String analyticsCode;
        @JsonProperty("selectedItem")
        private boolean selectedItem;

        public String getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        public int getValidationSize() {
            return validationSize;
        }

        public int getMinimumSize() {
            return minimumSize;
        }

        public String getAnalyticsCode() {
            return analyticsCode;
        }

        public boolean isSelectedItem() {
            return selectedItem;
        }
    }

    @JsonPropertyOrder({
            "code",
            "name"
    })
    public class Cardtype {

        @JsonProperty("code")
        private String code;
        @JsonProperty("name")
        private String name;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    @JsonPropertyOrder({
            "code",
            "label",
            "validationSize",
            "minimumSize",
            "analyticsCode",
            "selectedItem"
    })
    public class Expiredate {

        @JsonProperty("code")
        private String code;
        @JsonProperty("label")
        private String label;
        @JsonProperty("validationSize")
        private int validationSize;
        @JsonProperty("minimumSize")
        private int minimumSize;
        @JsonProperty("analyticsCode")
        private String analyticsCode;
        @JsonProperty("selectedItem")
        private boolean selectedItem;

        @JsonProperty("code")
        public String getCode() {
            return code;
        }

        @JsonProperty("label")
        public String getLabel() {
            return label;
        }

        public int getValidationSize() {
            return validationSize;
        }

        public int getMinimumSize() {
            return minimumSize;
        }

        public String getAnalyticsCode() {
            return analyticsCode;
        }

        public boolean isSelectedItem() {
            return selectedItem;
        }
    }

    @JsonPropertyOrder({
            "code",
            "name"
    })
    public class PaymentMode {

        @JsonProperty("code")
        private String code;
        @JsonProperty("name")
        private String name;

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


    @JsonPropertyOrder({
            "diavencimentovalue",
            "thab",
            "diavencimento"
    })

    public class AlternativaCredito {

        @JsonProperty("diavencimentovalue")
        private String diavencimentovalue;
        @JsonProperty("thab")
        private boolean thab;
        @JsonProperty("diavencimento")
        private String diavencimento;

        public String getDiavencimentovalue() {
            return diavencimentovalue;
        }

        @JsonProperty("thab")
        public boolean isThab() {
            return thab;
        }

        @JsonProperty("diavencimento")
        public String getDiavencimento() {
            return diavencimento;
        }
    }
}
