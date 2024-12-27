package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CheckoutStepOfferResponse {

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("contingency")
    private boolean contingency;
    @JsonProperty("cartGUID")
    private String cartGUID;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public boolean getContingency() {
        return contingency;
    }

    public String getCartGUID() {
        return cartGUID;
    }
}