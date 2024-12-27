package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "cartGUID",
        "termsCheck",
        "invoiceType",
        "expireDate",
        "paymentMode",
        "bankNumber",
        "agencyNumber",
        "accountNumber",
        "customBankNumber"
})
public class CheckoutStepPaymentRequest {

    @JsonProperty("cartGUID")
    private String cartGUID;
    @JsonProperty("termsCheck")
    private boolean termsCheck;
    @JsonProperty("invoiceType")
    private String invoiceType;
    @JsonProperty("expireDate")
    private int expireDate;
    @JsonProperty("paymentMode")
    private String paymentMode;
    @JsonProperty("bankNumber")
    private String bankNumber;
    @JsonProperty("agencyNumber")
    private String agencyNumber;
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("customBankNumber")
    private String customBankNumber;

    public void setCartGUID(String cartGUID) {
        this.cartGUID = cartGUID;
    }

    public void setTermsCheck(boolean termsCheck) {
        this.termsCheck = termsCheck;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void setExpireDate(int expireDate) {
        this.expireDate = expireDate;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setAgencyNumber(String agencyNumber) {
        this.agencyNumber = agencyNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setCustomBankNumber(String customBankNumber) {
        this.customBankNumber = customBankNumber;
    }
}