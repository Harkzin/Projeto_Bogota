package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddOfferPlanResponse {

    @JsonProperty("cartGUID")
    private String cartGUID;
    @JsonProperty("message")
    private String message;
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("cartGUID")
    public String getCartGUID() {
        return cartGUID;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}