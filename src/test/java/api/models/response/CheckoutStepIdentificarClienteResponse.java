package api.models.response;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class CheckoutStepIdentificarClienteResponse {

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
     @JsonProperty("fullName")
     private String fullName;
     @JsonProperty("parentFullName")
     private String parentFullName;
     @JsonProperty("birthdate")
     private String birthdate;
     @JsonProperty("hasPersonalInfo")
     private boolean hasPersonalInfo;
     @JsonProperty("hasAddressInfo")
     private boolean hasAddressInfo;
     @JsonProperty("dependent")
     private boolean dependent;

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

     public String getFullName() {
          return fullName;
     }

     public String getParentFullName() {
          return parentFullName;
     }

     public String getBirthdate() {
          return birthdate;
     }

     public boolean isHasPersonalInfo() {
          return hasPersonalInfo;
     }

     public boolean isHasAddressInfo() {
          return hasAddressInfo;
     }

     public boolean isDependent() {
          return dependent;
     }
}