package api.steps;

import api.models.request.CheckoutStepIdentificarClienteRequest;
import api.models.response.AddOfferPlanResponse;
import api.models.response.CartNewResponse;
import api.models.response.CheckoutStepIdentificarClienteResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.pt.Dado;
import org.junit.Assert;
import web.support.utils.Constants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.time.Duration.ofSeconds;
import static web.support.api.RestAPI.clientHttp;
import static web.support.api.RestAPI.objMapper;

public class ApiSteps {
    private String guid;
    private CartNewResponse cartNewObjectResponse;
    private AddOfferPlanResponse addPlanOfferObjectResponse;
    private CheckoutStepIdentificarClienteResponse IdenfificarClienteObjectResponse;
    private HttpResponse<String> IdentifyCustomerOfferResponse;
    private HttpResponse<String> AddressResponse;
    private HttpResponse<String> OfferResponse;
    private HttpResponse<String> OrderResponse;
    private HttpResponse<String> PaymentResponse;
    private HttpResponse<String> PaymentsResponse;
    private HttpResponse<String> PersonalInfoResponse;
    private HttpResponse<String> ValidateCreditResponse;
    private String baseURI = "https://api.cokecxf-commercec1-" + Constants.ambiente  + "-public.model-t.cc.commerce.ondemand.com/clarowebservices/v2/claro";
    private String token;


    @Dado("authorizationserver-oauth-token")
    public void gerarToken() {
        final HttpRequest getToken = HttpRequest.newBuilder()
                .uri(URI.create(baseURI.replace("/clarowebservices/v2/claro", "") + "/authorizationserver/oauth/token?client_id=claro_client&client_secret=cl4r0&grant_type=client_credentials"))
                .timeout(ofSeconds(15))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            final HttpResponse<String> tokenResponse = clientHttp.send(getToken, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, tokenResponse.statusCode());
            token = "Bearer " + objMapper.readTree(tokenResponse.body()).get("access_token").asText();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Dado("cart-new")
    public void criarCarrino() {
        final HttpRequest createCart = HttpRequest.newBuilder()
                .uri(URI.create(baseURI + "/cart/new?fields=FULL"))
                .timeout(ofSeconds(15))
                .header("Authorization", token)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        final HttpResponse<String> cartNewResponse;
        try {
            cartNewResponse = clientHttp.send(createCart, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, cartNewResponse.statusCode());
            cartNewObjectResponse = objMapper.readValue(cartNewResponse.body(), CartNewResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        guid = cartNewObjectResponse.getGuid();

    }

    @Dado("add-offer-plan [fields {string}]")
    public void AddOfferPlan(String fields) {
        final HttpRequest addOfferPlan = HttpRequest.newBuilder()
                .uri(URI.create(baseURI + "/cart/" + guid + fields))
                .timeout(ofSeconds(15))
                .header("Authorization", token)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        final HttpResponse<String> addPlanOfferResponse;
        try {
            addPlanOfferResponse = clientHttp.send(addOfferPlan, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, addPlanOfferResponse.statusCode());
            addPlanOfferObjectResponse = objMapper.readValue(addPlanOfferResponse.body(), AddOfferPlanResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Dado("identificar-cliente [msisdn {string}], [cpf {string}], [ddd {string}] [service {string}]")
    public void identificarClient(String msisdn, String cpf, String ddd, String service) {

        CheckoutStepIdentificarClienteRequest identificarClienteRequest = new CheckoutStepIdentificarClienteRequest();
        identificarClienteRequest.setMsisdn(msisdn);
        identificarClienteRequest.setCpf(cpf);
        identificarClienteRequest.setDdd(ddd);
        identificarClienteRequest.setService(service);
        identificarClienteRequest.setEmail("xxxxxxxx@xxx.com");
        identificarClienteRequest.setOrigin("SITEAPI");
        identificarClienteRequest.setCartGUID(guid);
        final HttpResponse<String> identificarClienteResponse;

        try {
            final HttpRequest identificarCliente = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/identificarCliente"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(identificarClienteRequest)))
                    .build();

            identificarClienteResponse = clientHttp.send(identificarCliente, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, identificarClienteResponse.statusCode());
            IdenfificarClienteObjectResponse = objMapper.readValue(identificarClienteResponse.body(), CheckoutStepIdentificarClienteResponse.class);
        } catch ( IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(identificarClienteResponse.body());
    }



}
