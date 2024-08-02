package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class CheckoutStepTokenResponse {

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

     public boolean isIsTroca() {
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

     public boolean isIsTokenOnlineForABrFlow() {
          return isTokenOnlineForABrFlow;
     }

     public boolean isIsDDDabr() {
          return isDDDabr;
     }

     public String getControleFacilUrl() {
          return controleFacilUrl;
     }
}