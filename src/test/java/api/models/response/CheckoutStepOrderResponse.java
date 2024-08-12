package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class CheckoutStepOrderResponse {

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
     @JsonProperty("ordercode")
     private String ordercode;
     @JsonProperty("expiredate")
     private String expiredate;
     @JsonProperty("plan")
     private String plan;
     @JsonProperty("priceplan")
     private String priceplan;
     @JsonProperty("customername")
     private String customername;
     @JsonProperty("customerid")
     private String customerid;

     @JsonProperty("success")
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

     public String getOrdercode() {
          return ordercode;
     }

     public String getExpiredate() {
          return expiredate;
     }

     public String getPlan() {
          return plan;
     }

     public String getPriceplan() {
          return priceplan;
     }

     public String getCustomername() {
          return customername;
     }

     public String getCustomerid() {
          return customerid;
     }
}