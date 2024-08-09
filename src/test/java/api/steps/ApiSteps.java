package api.steps;

import api.models.request.*;
import api.models.response.*;
import io.cucumber.java.pt.Dado;
import org.junit.Assert;
import web.support.utils.Constants;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.time.Duration.ofSeconds;
import static web.support.api.RestAPI.*;

public class ApiSteps {

    //MODELO

    //CheckoutStepAddressRequest checkoutStepAddressRequest = new CheckoutStepAddressRequest();
    //checkoutStepAddressRequest.getBillingAddress().setBuildingnumber("sdfasdf");
    private String validateToken;
    private String plan;
    private String guid;
    private String countryIso;
    private String neighbourhood;
    private String stateCode;
    private String streetName;
    private String townCity;
    private String zipCode;

    private CartNewResponse cartNewObjectResponse;
    private AddOfferPlanResponse addPlanOfferObjectResponse;
    private CheckoutStepIdentificarClienteResponse idenfificarClienteObjectResponse;
    private CheckoutStepPersonalInfoResponse personalInfoObjectResponse;
    private CheckoutStepAddressResponse addressObjectResponse;
    private HttpResponse<String> OfferResponse;
    private CheckoutStepOrderResponse OrderObjectResponse;
    private CheckoutStepPaymentResponse paymentObjectResponse;
    private CheckoutStepPaymentsResponse paymentsObjectResponse;
    private HttpResponse<String> PersonalInfoResponse;
    private CheckoutStepTokenResponse tokenObjectResponse;
    private CheckoutStepValidateCreditResponse validateCreditObjectResponse;
    private String baseURI = "https://api.cokecxf-commercec1-" + Constants.ambiente + "-public.model-t.cc.commerce.ondemand.com/clarowebservices/v2/claro";
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
        Assert.assertEquals(0.0, cartNewObjectResponse.getOrderDiscounts().getValue(), 1e-3);
        Assert.assertEquals(0.0, cartNewObjectResponse.getProductDiscounts().getValue(), 1e-3);
        Assert.assertEquals(0.0, cartNewObjectResponse.getSubTotal().getValue(), 1e-3);
        Assert.assertEquals(0.0, cartNewObjectResponse.getTotalDiscounts().getValue(), 1e-3);
        Assert.assertEquals(0.0, cartNewObjectResponse.getTotalPrice().getValue(), 1e-3);
        Assert.assertEquals(0.0, cartNewObjectResponse.getTotalPriceWithTax().getValue(), 1e-3);
        Assert.assertEquals(0.0, cartNewObjectResponse.getTotalTax().getValue(), 1e-3);
        Assert.assertEquals(0, cartNewObjectResponse.getTotalUnitCount());
        Assert.assertTrue(cartNewObjectResponse.getEntries().isEmpty());
    }

    @Dado("add-offer-plan [plan {string}], [fields {string}]")
    public void AddOfferPlan(String plan, String fields) {

        final HttpRequest addOfferPlan = HttpRequest.newBuilder()
                .uri(URI.create(baseURI + "/cart/" + guid + "/" + plan + fields))
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
        this.plan = plan;
        Assert.assertEquals("product of code " + plan + " added to cart", addPlanOfferObjectResponse.getMessage());
        Assert.assertTrue(addPlanOfferObjectResponse.isSuccess());
    }


    @Dado("identificar-cliente [msisdn {string}], [CPF aprovado na clearSale? {string}, CPF na diretrix? {string}], [ddd {string}] e [service {string}]")
    public void identificarClient(String msisdn, String cpfAprovado, String cpfDiretrix, String ddd, String service) {
        CheckoutStepIdentificarClienteRequest identificarClienteRequest = new CheckoutStepIdentificarClienteRequest();
        identificarClienteRequest.setMsisdn(msisdn);
        identificarClienteRequest.setCpf(getCpfForPlanFlow(Boolean.parseBoolean(cpfAprovado), Boolean.parseBoolean(cpfDiretrix)));
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
            idenfificarClienteObjectResponse = objMapper.readValue(identificarClienteResponse.body(), CheckoutStepIdentificarClienteResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(idenfificarClienteObjectResponse.isSuccess());
        Assert.assertFalse(idenfificarClienteObjectResponse.isContingency());
        Assert.assertFalse(idenfificarClienteObjectResponse.isDiretrixCart());
        Assert.assertFalse(idenfificarClienteObjectResponse.isDependent());
    }

    @Dado("identificar-cliente [msisdn {string}], [CPF {string}], [ddd {string}] e [service {string}]")
    public void identificarClientCpfEspecifico(String msisdn, String cpf, String ddd, String service) {
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
            idenfificarClienteObjectResponse = objMapper.readValue(identificarClienteResponse.body(), CheckoutStepIdentificarClienteResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(idenfificarClienteObjectResponse.isSuccess());
        Assert.assertFalse(idenfificarClienteObjectResponse.isContingency());
        Assert.assertFalse(idenfificarClienteObjectResponse.isDiretrixCart());
        Assert.assertFalse(idenfificarClienteObjectResponse.isDependent());
    }

    @Dado("personal-info [fullName {string}]")
    public void personalInfo(String fullName) {

        CheckoutStepPersonalInfoRequest checkoutStepPersonalInfoRequest = new CheckoutStepPersonalInfoRequest();
        checkoutStepPersonalInfoRequest.setFullName(fullName);
        checkoutStepPersonalInfoRequest.setParentFullName("Parent Name");
        checkoutStepPersonalInfoRequest.setBirthdate("15/05/1995");
        checkoutStepPersonalInfoRequest.setCartGUID(guid);

        final HttpResponse<String> personalInfoResponse;

        try {
            final HttpRequest personalInfo = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/personalInfo"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepPersonalInfoRequest)))
                    .build();

            personalInfoResponse = clientHttp.send(personalInfo, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, personalInfoResponse.statusCode());
            personalInfoObjectResponse = objMapper.readValue(personalInfoResponse.body(), CheckoutStepPersonalInfoResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertEquals("Informações Adicionadas com Sucesso!", personalInfoObjectResponse.getMessage());
        Assert.assertTrue(personalInfoObjectResponse.isSuccess());
    }

    @Dado("get-address [cep {string}]")
    public void getAddress(String cep) {

        CheckoutStepAddressRequest checkoutStepAddressRequest = new CheckoutStepAddressRequest();
        checkoutStepAddressRequest.setCep(cep);
        checkoutStepAddressRequest.setCartGUID(guid);

        final HttpResponse<String> addressResponse;

        try {
            final HttpRequest address = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/address"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .method("GET", HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepAddressRequest)))
                    .build();

            addressResponse = clientHttp.send(address, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, addressResponse.statusCode());
            addressObjectResponse = objMapper.readValue(addressResponse.body(), CheckoutStepAddressResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertFalse(addressObjectResponse.isContingency());
        Assert.assertTrue(addressObjectResponse.getErrorList().isEmpty());
        Assert.assertFalse(addressObjectResponse.isDiretrixCart());
        Assert.assertTrue(addressObjectResponse.isSuccess());

        countryIso = addressObjectResponse.getState().split("-")[0];
        neighbourhood = addressObjectResponse.getNeighbourhood();
        stateCode = addressObjectResponse.getState();
        streetName = addressObjectResponse.getStreet();
        townCity = addressObjectResponse.getTown();
        zipCode = addressObjectResponse.getZipcode();
    }

    @Dado("save-address")
    public void saveAddress() {

        CheckoutStepAddressRequest checkoutStepAddressRequest = new CheckoutStepAddressRequest();
        checkoutStepAddressRequest.setCartGUID(guid);
        checkoutStepAddressRequest.getBillingAddress().setBuildingnumber("");
        checkoutStepAddressRequest.getBillingAddress().setCountryiso(countryIso);
        checkoutStepAddressRequest.getBillingAddress().setHousenumber("158");
        checkoutStepAddressRequest.getBillingAddress().setNeighbourhood(neighbourhood);
        checkoutStepAddressRequest.getBillingAddress().setStatecode(stateCode);
        checkoutStepAddressRequest.getBillingAddress().setStreetname(streetName);
        checkoutStepAddressRequest.getBillingAddress().setTowncity(townCity);
        checkoutStepAddressRequest.getBillingAddress().setZipcode(zipCode);

        checkoutStepAddressRequest.setCartGUID(guid);
        checkoutStepAddressRequest.getDeliveryAddress().setBuildingnumber("");
        checkoutStepAddressRequest.getDeliveryAddress().setCountryiso(countryIso);
        checkoutStepAddressRequest.getDeliveryAddress().setHousenumber("158");
        checkoutStepAddressRequest.getDeliveryAddress().setNeighbourhood(neighbourhood);
        checkoutStepAddressRequest.getDeliveryAddress().setStatecode(stateCode);
        checkoutStepAddressRequest.getDeliveryAddress().setStreetname(streetName);
        checkoutStepAddressRequest.getDeliveryAddress().setTowncity(townCity);
        checkoutStepAddressRequest.getDeliveryAddress().setZipcode(zipCode);
        checkoutStepAddressRequest.setSameAddress(true);

        final HttpResponse<String> addressResponse;

        try {
            final HttpRequest address = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/address"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepAddressRequest)))
                    .build();

            addressResponse = clientHttp.send(address, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, addressResponse.statusCode());
            addressObjectResponse = objMapper.readValue(addressResponse.body(), CheckoutStepAddressResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertFalse(addressObjectResponse.isContingency());
        Assert.assertFalse(addressObjectResponse.isDiretrixCart());
        Assert.assertTrue(addressObjectResponse.getErrorList().isEmpty());
        Assert.assertTrue(addressObjectResponse.isSuccess());
    }

    @Dado("get-payments")
    public void getPayments() {

        CheckoutStepPaymentsRequest checkoutStepPaymentsRequest = new CheckoutStepPaymentsRequest();
        checkoutStepPaymentsRequest.setCartGUID(guid);

        final HttpResponse<String> paymentsResponse;

        try {
            final HttpRequest payments = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/payments"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepPaymentsRequest)))
                    .build();

            paymentsResponse = clientHttp.send(payments, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, paymentsResponse.statusCode());
            paymentsObjectResponse = objMapper.readValue(paymentsResponse.body(), CheckoutStepPaymentsResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(paymentsObjectResponse.isSuccess());
        Assert.assertFalse(paymentsObjectResponse.isContingency());
        Assert.assertTrue(paymentsObjectResponse.getAvailablebanks().size() > 1);
        Assert.assertTrue(paymentsObjectResponse.getCaixaaccountvalues().size() > 1);
        Assert.assertTrue(paymentsObjectResponse.getCardtypes().size() > 1);
        Assert.assertTrue(paymentsObjectResponse.getExpiredates().size() > 1);
        Assert.assertTrue(paymentsObjectResponse.getCaixaaccountvalues().size() > 1);
        Assert.assertTrue(paymentsObjectResponse.getPaymentModeList().size() > 1);
    }

    @Dado("save-payments [invoiceType {string}] e [paymentMode {string}]")
    public void savePayments(String invoiceType, String paymentMode) {

        CheckoutStepPaymentRequest checkoutStepPaymentRequest = new CheckoutStepPaymentRequest();
        checkoutStepPaymentRequest.setCartGUID(guid);
        checkoutStepPaymentRequest.setExpireDate(72);
        checkoutStepPaymentRequest.setInvoiceType(invoiceType);
        checkoutStepPaymentRequest.setPaymentMode(paymentMode);
        checkoutStepPaymentRequest.setTermsCheck(true);

        if (paymentMode.equals("debitcard")) {
            checkoutStepPaymentRequest.setBankNumber("001");
            checkoutStepPaymentRequest.setAgencyNumber("0001");
            checkoutStepPaymentRequest.setAccountNumber("0000001");
            checkoutStepPaymentRequest.setCustomBankNumber("1");
        }

        final HttpResponse<String> paymentResponse;

        try {
            final HttpRequest payment = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/payment"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepPaymentRequest)))
                    .build();

            paymentResponse = clientHttp.send(payment, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, paymentResponse.statusCode());
            paymentObjectResponse = objMapper.readValue(paymentResponse.body(), CheckoutStepPaymentResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(paymentsObjectResponse.isSuccess());
        Assert.assertEquals("Pagamento Salvo com Sucesso!", paymentObjectResponse.getMessage());
    }

    @Dado("validate-credit")
    public void validateCredit() {

        CheckoutStepValidateCreditRequest checkoutStepValidateCreditRequest = new CheckoutStepValidateCreditRequest();
        checkoutStepValidateCreditRequest.setCartGUID(guid);

        final HttpResponse<String> validateCreditResponse;

        try {
            final HttpRequest validateCredit = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/validateCredit"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepValidateCreditRequest)))
                    .build();

            validateCreditResponse = clientHttp.send(validateCredit, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, validateCreditResponse.statusCode());
            validateCreditObjectResponse = objMapper.readValue(validateCreditResponse.body(), CheckoutStepValidateCreditResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(validateCreditObjectResponse.isSuccess());
        Assert.assertTrue(validateCreditObjectResponse.getErrorList().isEmpty());
        Assert.assertEquals("Crédito Aprovado!", validateCreditObjectResponse.getMessage());
        Assert.assertEquals(plan, validateCreditObjectResponse.getCart().getEntries().get(0).getProduct().getCode());
        Assert.assertEquals(1, validateCreditObjectResponse.getCart().getTotalItems());
        Assert.assertTrue(validateCreditObjectResponse.isCreditApproved());
    }

    @Dado("get-otp-token")
    public void getToken() {

        CheckoutStepTokenRequest checkoutStepTokenRequest = new CheckoutStepTokenRequest();
        checkoutStepTokenRequest.setCartGUID(guid);

        final HttpResponse<String> tokenResponse;

        try {
            final HttpRequest otpToken = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/token"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepTokenRequest)))
                    .build();

            tokenResponse = clientHttp.send(otpToken, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, tokenResponse.statusCode());
            tokenObjectResponse = objMapper.readValue(tokenResponse.body(), CheckoutStepTokenResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(tokenObjectResponse.isSuccess());
        Assert.assertEquals("Token enviado",tokenObjectResponse.getMessage());
        Assert.assertNotNull( tokenObjectResponse.getValidateTokenTest());
        validateToken = tokenObjectResponse.getValidateTokenTest();
    }

    @Dado("send-otp-token")
    public void sendToken() {

        CheckoutStepTokenRequest checkoutStepTokenRequest = new CheckoutStepTokenRequest();
        checkoutStepTokenRequest.setCartGUID(guid);
        checkoutStepTokenRequest.setToken(validateToken);

        final HttpResponse<String> tokenResponse;

        try {
            final HttpRequest otpToken = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/token"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .method("GET",HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepTokenRequest)))
                    .build();

            tokenResponse = clientHttp.send(otpToken, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, tokenResponse.statusCode());
            tokenObjectResponse = objMapper.readValue(tokenResponse.body(), CheckoutStepTokenResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(tokenObjectResponse.isSuccess());
        Assert.assertEquals("Token válido",tokenObjectResponse.getMessage());
        Assert.assertFalse(tokenObjectResponse.isContingency());
    }

    @Dado("create-order")
    public void createOrder() {
        CheckoutStepOrderRequest checkoutStepOrderRequest = new CheckoutStepOrderRequest();
        checkoutStepOrderRequest.setCartGUID(guid);

        final HttpResponse<String> OrderResponse;

        try {
            final HttpRequest validateCredit = HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + "/checkout/step/order"))
                    .timeout(ofSeconds(15))
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objMapper.writeValueAsString(checkoutStepOrderRequest)))
                    .build();

            OrderResponse = clientHttp.send(validateCredit, HttpResponse.BodyHandlers.ofString());
            Assert.assertEquals(200, OrderResponse.statusCode());
            OrderObjectResponse = objMapper.readValue(OrderResponse.body(), CheckoutStepOrderResponse.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assert.assertTrue(OrderObjectResponse.isSuccess());
        Assert.assertFalse(OrderObjectResponse.isContingency());
        Assert.assertEquals("Pedido Gerado com Sucesso", OrderObjectResponse.getMessage());
        Assert.assertNotNull(OrderObjectResponse.getOrdercode());
    }

    private String getCpfForPlanFlow(boolean isApproved, boolean isDiretrix) {
        String cpf;
        String clearSaleRule = isApproved ? ".*[1348]$" : ".*5$"; //Regra do final do CPF da clearSale.

        do {
            do {
                cpf = getCpf();
            } while (!cpf.matches(clearSaleRule));
        } while (checkCpfDiretrix(cpf) != isDiretrix);

        return cpf;
    }
}
