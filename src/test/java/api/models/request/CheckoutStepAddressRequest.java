package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "cep",
        "cartGUID",
        "deliveryAddress",
        "billingAddress",
        "sameAddress"
})
public class CheckoutStepAddressRequest {

     @JsonProperty("cep")
     private String cep;
     @JsonProperty("cartGUID")
     private String cartGUID;
     @JsonProperty("deliveryAddress")
     private DeliveryAddress deliveryAddress = new DeliveryAddress();
     //MODELO
     //FAZER ISSO QUANDO FOR ACESSAR O CAMPO DO OBJETO FILHO
     @JsonProperty("billingAddress")
     private BillingAddress billingAddress = new BillingAddress();
     @JsonProperty("sameAddress")
     private boolean sameAddress;

     public void setCep(String cep) {
          this.cep = cep;
     }

     public void setCartGUID(String cartGUID) {
          this.cartGUID = cartGUID;
     }
     public DeliveryAddress getDeliveryAddress() {
          return deliveryAddress;
     }

     public BillingAddress getBillingAddress() {
          return billingAddress;
     }

     public void setSameAddress(boolean sameAddress) {
          this.sameAddress = sameAddress;
     }

     @JsonPropertyOrder({
             "streetname",
             "housenumber",
             "neighbourhood",
             "buildingnumber",
             "statecode",
             "towncity",
             "zipcode",
             "countryiso"
     })
     public static class BillingAddress {

          @JsonProperty("streetname")
          private String streetname;
          @JsonProperty("housenumber")
          private String housenumber;
          @JsonProperty("neighbourhood")
          private String neighbourhood;
          @JsonProperty("buildingnumber")
          private String buildingnumber;
          @JsonProperty("statecode")
          private String statecode;
          @JsonProperty("towncity")
          private String towncity;
          @JsonProperty("zipcode")
          private String zipcode;
          @JsonProperty("countryiso")
          private String countryiso;

          public void setStreetname(String streetname) {
               this.streetname = streetname;
          }

          public void setHousenumber(String housenumber) {
               this.housenumber = housenumber;
          }

          public void setNeighbourhood(String neighbourhood) {
               this.neighbourhood = neighbourhood;
          }

          public void setBuildingnumber(String buildingnumber) {
               this.buildingnumber = buildingnumber;
          }

          public void setStatecode(String statecode) {
               this.statecode = statecode;
          }

          public void setTowncity(String towncity) {
               this.towncity = towncity;
          }

          public void setZipcode(String zipcode) {
               this.zipcode = zipcode;
          }

          public void setCountryiso(String countryiso) {
               this.countryiso = countryiso;
          }
     }

     @JsonPropertyOrder({
             "streetname",
             "housenumber",
             "neighbourhood",
             "buildingnumber",
             "statecode",
             "towncity",
             "zipcode",
             "countryiso"
     })
     public static class DeliveryAddress {

          @JsonProperty("streetname")
          private String streetname;
          @JsonProperty("housenumber")
          private String housenumber;
          @JsonProperty("neighbourhood")
          private String neighbourhood;
          @JsonProperty("buildingnumber")
          private String buildingnumber;
          @JsonProperty("statecode")
          private String statecode;
          @JsonProperty("towncity")
          private String towncity;
          @JsonProperty("zipcode")
          private String zipcode;
          @JsonProperty("countryiso")
          private String countryiso;

          public void setStreetname(String streetname) {
               this.streetname = streetname;
          }

          public void setHousenumber(String housenumber) {
               this.housenumber = housenumber;
          }

          public void setNeighbourhood(String neighbourhood) {
               this.neighbourhood = neighbourhood;
          }

          public void setBuildingnumber(String buildingnumber) {
               this.buildingnumber = buildingnumber;
          }

          public void setStatecode(String statecode) {
               this.statecode = statecode;
          }

          public void setTowncity(String towncity) {
               this.towncity = towncity;
          }

          public void setZipcode(String zipcode) {
               this.zipcode = zipcode;
          }

          public void setCountryiso(String countryiso) {
               this.countryiso = countryiso;
          }

     }
}