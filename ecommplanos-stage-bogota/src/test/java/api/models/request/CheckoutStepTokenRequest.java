package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "token",
        "resend",
        "cartGUID"
})
public class CheckoutStepTokenRequest {

     @JsonProperty("token")
     private String token;
     @JsonProperty("resend")
     private boolean resend;
     @JsonProperty("cartGUID")
     private String cartGUID;

     public void setToken(String token) {
          this.token = token;
     }

     public void setResend(boolean resend) {
          this.resend = resend;
     }

     public void setCartGUID(String cartGUID) {
          this.cartGUID = cartGUID;
     }
}