package api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "msisdn",
        "ddd",
        "cpf",
        "email",
        "cartGUID",
        "service",
        "origin",
        "ipClient"
})

public class CheckoutStepIdentificarClienteRequest {

     @JsonProperty("msisdn")
     private String msisdn;
     @JsonProperty("ddd")
     private String ddd;
     @JsonProperty("cpf")
     private String cpf;
     @JsonProperty("email")
     private String email;
     @JsonProperty("cartGUID")
     private String cartGUID;
     @JsonProperty("service")
     private String service;
     @JsonProperty("origin")
     private String origin;
     @JsonProperty("ipClient")
     private String ipClient;


     public void setMsisdn(String msisdn) {
          this.msisdn = msisdn;
     }

     public void setDdd(String ddd) {
          this.ddd = ddd;
     }

     public void setCpf(String cpf) {
          this.cpf = cpf;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public void setCartGUID(String cartGUID) {
          this.cartGUID = cartGUID;
     }

     public void setService(String service) {
          this.service = service;
     }

     public void setOrigin(String origin) {
          this.origin = origin;
     }

     public void setIpClient(String ipClient) {
          this.ipClient = ipClient;
     }

}