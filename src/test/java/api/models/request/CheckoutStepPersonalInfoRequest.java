package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "fullName",
        "parentFullName",
        "birthdate",
        "cartGUID"
})
public class CheckoutStepPersonalInfoRequest {

    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("parentFullName")
    private String parentFullName;
    @JsonProperty("birthdate")
    private String birthdate;
    @JsonProperty("cartGUID")
    private String cartGUID;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setParentFullName(String parentFullName) {
        this.parentFullName = parentFullName;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setCartGUID(String cartGUID) {
        this.cartGUID = cartGUID;
    }
}