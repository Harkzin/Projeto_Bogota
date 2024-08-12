package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "cartGUID"
})
public class CheckoutStepValidateCreditRequest {

    @JsonProperty("cartGUID")
    private String cartGUID;

    public void setCartGUID(String cartGUID) {
        this.cartGUID = cartGUID;
    }
}