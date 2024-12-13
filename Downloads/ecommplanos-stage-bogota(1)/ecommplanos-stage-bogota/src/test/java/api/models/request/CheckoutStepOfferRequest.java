
package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "msisdn",
        "cartGUID",
        "removeAllDependents",
        "subscriberType",
        "processType"
})
public class CheckoutStepOfferRequest {

    @JsonProperty("msisdn")
    private String msisdn;
    @JsonProperty("cartGUID")
    private String cartGUID;
    @JsonProperty("removeAllDependents")
    private boolean removeAllDependents;
    @JsonProperty("subscriberType")
    private String subscriberType;
    @JsonProperty("processType")
    private String processType;

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public void setCartGUID(String cartGUID) {
        this.cartGUID = cartGUID;
    }

    public void setRemoveAllDependents(boolean removeAllDependents) {
        this.removeAllDependents = removeAllDependents;
    }

    public void setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

}