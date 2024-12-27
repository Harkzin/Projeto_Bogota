package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "origin",
        "originRef",
        "cartGUID",
        "msisdn",
        "offerProduct",
        "promotionCode"
})
public class CheckoutStepOrderRequest {

     @JsonProperty("origin")
     private String origin;
     @JsonProperty("originRef")
     private String originRef;
     @JsonProperty("cartGUID")
     private String cartGUID;
     @JsonProperty("msisdn")
     private String msisdn;
     @JsonProperty("offerProduct")
     private String offerProduct;
     @JsonProperty("promotionCode")
     private String promotionCode;

     public void setOrigin(String origin) {
          this.origin = origin;
     }

     public void setOriginRef(String originRef) {
          this.originRef = originRef;
     }

     public void setCartGUID(String cartGUID) {
          this.cartGUID = cartGUID;
     }

     public void setMsisdn(String msisdn) {
          this.msisdn = msisdn;
     }

     public void setOfferProduct(String offerProduct) {
          this.offerProduct = offerProduct;
     }

     public void setPromotionCode(String promotionCode) {
          this.promotionCode = promotionCode;
     }
}