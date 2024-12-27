package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CheckoutStepAddressResponse {

     @JsonProperty("success")
     private boolean success;
     @JsonProperty("errorList")
     private List<String> errorList = new ArrayList<>();
     @JsonProperty("message")
     private String message;
     @JsonProperty("contingency")
     private boolean contingency;
     @JsonProperty("controleFacilCart")
     private boolean controleFacilCart;
     @JsonProperty("diretrixCart")
     private boolean diretrixCart;
     @JsonProperty("controleAntecipado")
     private boolean controleAntecipado;
     @JsonProperty("isTroca")
     private boolean isTroca;
     @JsonProperty("cartGUID")
     private String cartGUID;
     @JsonProperty("processType")
     private String processType;
     @JsonProperty("validateTokenTest")
     private String validateTokenTest;
     @JsonProperty("isTokenOnlineForABrFlow")
     private boolean isTokenOnlineForABrFlow;
     @JsonProperty("isDDDabr")
     private boolean isDDDabr;
     @JsonProperty("controleFacilUrl")
     private String controleFacilUrl;
     @JsonProperty("street")
     private String street;
     @JsonProperty("neighbourhood")
     private String neighbourhood;
     @JsonProperty("town")
     private String town;
     @JsonProperty("state")
     private String state;
     @JsonProperty("zipcode")
     private String zipcode;
     @JsonProperty("deliveryInfo")
     private DeliveryInfo deliveryInfo;

     public boolean isSuccess() {
          return success;
     }

     public List<String> getErrorList() {
          return errorList;
     }

     public String getMessage() {
          return message;
     }

     public boolean isContingency() {
          return contingency;
     }

     public boolean isControleFacilCart() {
          return controleFacilCart;
     }

     public boolean isDiretrixCart() {
          return diretrixCart;
     }

     public boolean isControleAntecipado() {
          return controleAntecipado;
     }

     public boolean isTroca() {
          return isTroca;
     }

     public String getCartGUID() {
          return cartGUID;
     }

     public String getProcessType() {
          return processType;
     }

     public String getValidateTokenTest() {
          return validateTokenTest;
     }

     public boolean isTokenOnlineForABrFlow() {
          return isTokenOnlineForABrFlow;
     }

     public boolean isDDDabr() {
          return isDDDabr;
     }

     public String getControleFacilUrl() {
          return controleFacilUrl;
     }

     public String getStreet() {
          return street;
     }

     public String getNeighbourhood() {
          return neighbourhood;
     }

     public String getTown() {
          return town;
     }

     public String getState() {
          return state;
     }

     public String getZipcode() {
          return zipcode;
     }

     public DeliveryInfo getDeliveryInfo() {
          return deliveryInfo;
     }

     public static class DeliveryInfo {

          @JsonProperty("deliveryType")
          private String deliveryType;
          @JsonProperty("deliveryDays")
          private String deliveryDays;

          public String getDeliveryType() {
               return deliveryType;
          }

          public String getDeliveryDays() {
               return deliveryDays;
          }
     }

}