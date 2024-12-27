package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CheckoutStepPersonalInfoResponse {

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("step")
    private String step;
    @JsonProperty("cartGUID")
    private String cartGUID;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getStep() {
        return step;
    }

    public String getCartGUID() {
        return cartGUID;
    }
}