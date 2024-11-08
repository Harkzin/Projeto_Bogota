package web.support.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import web.support.utils.Constants.Email;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.util.*;

import static java.time.Duration.ofSeconds;
import static web.support.utils.Constants.ambiente;

public final class RestAPI {

    private RestAPI() {}

    public static final HttpClient clientHttp = HttpClient.newHttpClient();
    private static final String MAILSAC_KEY = "k_YKJeUgIItKTd03DqOGRFAPty89C2gXR6zLLw39";
    public static final ObjectMapper objMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);

    private static void validateStatusCodeOk(String message, int status, String url) throws HttpStatusException {
        if (status != 200) {
            throw new HttpStatusException("Erro na API de Login. Response Body=" + message, status, url);
        }
    }

    public static String getCpf() {
        final HttpRequest getCpfRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://www.4devs.com.br/ferramentas_online.php"))
                .timeout(ofSeconds(15))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("acao=gerar_cpf&pontuacao=N&cpf_estado=SP"))
                .build();

        long startTime = System.currentTimeMillis() + 200L; //200ms delay

        try {
            while (true) { //Delay para chamadas consecutivas
                if (System.currentTimeMillis() >= startTime) {
                    return clientHttp.send(getCpfRequest, HttpResponse.BodyHandlers.ofString()).body(); //Retorna um CPF como String.
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkCpfDiretrix(String cpf) {
        final HttpRequest diretrixTokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-lab.claro.com.br/oauth2/v1/token"))
                .timeout(ofSeconds(15))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("X-Client-Auth", "Basic MWxKR1dqdkhsVzEzWkdmT0pxUVlHQ3JFTlRNY0x3Vno6QVowTTF3aVA5cFJSWGplTg==")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        try {
            String token = objMapper.readTree(clientHttp.send(diretrixTokenRequest, HttpResponse.BodyHandlers.ofString()).body())
                    .get("access_token")
                    .asText();

            final HttpRequest diretrixRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api-lab.claro.com.br/customers/v1/profiles/historical"))
                    .timeout(ofSeconds(15))
                    .header("X-Client-Auth", "Bearer " + token)
                    .header("X-QueryString", "documentnumber=" + cpf)
                    .GET()
                    .build();

            HttpResponse<String> response = clientHttp.send(diretrixRequest, HttpResponse.BodyHandlers.ofString());
            JsonNode node = objMapper.readTree(response.body());

            return response.statusCode() != 422 || !node.at("/error").get("detailedMessage").asText().equalsIgnoreCase("CPF não encontrado"); //CPF na Diretrix? = true, fora da Diretrix? = false, para testes deve ser false.
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document getEmailMessage(String emailAddress, Email emailSubject) {
        final HttpRequest getMessages = HttpRequest.newBuilder()
                .uri(URI.create("https://mailsac.com/api/addresses/" + emailAddress + "/messages"))
                .timeout(ofSeconds(15))
                .header("Mailsac-Key", MAILSAC_KEY)
                .GET()
                .build();

        List<JsonNode> messageList;

        try {
            messageList = objMapper.readValue(clientHttp.send(getMessages, HttpResponse.BodyHandlers.ofString()).body(), new TypeReference<>() {});
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Optional<JsonNode> message = messageList.stream().filter(node -> node.get("subject").asText().contains(emailSubject.getSubject())).findFirst();

        if (message.isPresent()) {
            final HttpRequest getMessage = HttpRequest.newBuilder()
                    .uri(URI.create("https://mailsac.com/api/dirty/" + emailAddress + "/" + message.get().get("_id").asText()))
                    .timeout(ofSeconds(15))
                    .header("Mailsac-Key", MAILSAC_KEY)
                    .GET()
                    .build();

            Document email;

            try {
                email = Jsoup.parseBodyFragment(clientHttp.send(getMessage, HttpResponse.BodyHandlers.ofString()).body());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            return email;
        } else {
            return null;
        }
    }

    public static void clearInbox(String emailAddress) {
        final HttpRequest deleteMessages = HttpRequest.newBuilder()
                .uri(URI.create("https://mailsac.com/api/addresses/" + emailAddress + "/messages"))
                .timeout(ofSeconds(15))
                .header("Mailsac-Key", MAILSAC_KEY)
                .DELETE()
                .build();

        try {
            clientHttp.send(deleteMessages, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getBankAccount(String bankId) {
        final HttpRequest getAccount = HttpRequest.newBuilder()
                .uri(URI.create("https://www.invertexto.com/ajax/gerar-conta-bancaria.php"))
                .timeout(ofSeconds(15))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("banco=" + bankId + "&estado=SP"))
                .build();

        JsonNode response;
        long startTime = System.currentTimeMillis() + 250L; //250ms delay

        try {
            while (true) { //Delay para chamadas consecutivas
                if (System.currentTimeMillis() >= startTime) {
                    response = objMapper.readTree(clientHttp.send(getAccount, HttpResponse.BodyHandlers.ofString()).body());
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Arrays.asList(response.get("agencia").asText(), response.get("conta").asText());
    }

    public static String getProductDetails(String product) {
        final HttpRequest productDetails = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cokecxf-commercec1-" + ambiente + "-public.model-t.cc.commerce.ondemand.com/clarowebservices/v2/claro/products/" + product + "?fields=FULL"))
                .timeout(ofSeconds(10))
                .GET()
                .build();

        try {
            return clientHttp.send(productDetails, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getEcommToken() {
        final HttpRequest ecommTokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.cokecxf-commercec1-s6-public.model-t.cc.commerce.ondemand.com/authorizationserver/oauth/token?client_id=claro_client&client_secret=cl4r0&grant_type=client_credentials"))
                .timeout(ofSeconds(10))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            return objMapper.readTree(clientHttp.send(ecommTokenRequest, HttpResponse.BodyHandlers.ofString()).body()).get("access_token").asText();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDevicePriceInfo(String campaign, String planCode, String deviceCode, String salesOrg) {
        final HttpRequest devicePriceInfoRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api.cokecxf-commercec1-%s-public.model-t.cc.commerce.ondemand.com/clarowebservices/v2/claro/products/campaign/automation?campaign=%s&planCode=%s&deviceCode=%s&salesOrg=%s", ambiente, campaign, planCode, deviceCode, salesOrg)))
                .timeout(ofSeconds(10))
                .header("Authorization", "Bearer " + getEcommToken())
                .GET()
                .build();

        try {
            return clientHttp.send(devicePriceInfoRequest, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPlanCartPromotion(String guid) {
        final HttpRequest planCartPromotionRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://api.cokecxf-commercec1-%s-public.model-t.cc.commerce.ondemand.com/clarowebservices/v2/claro/cart/promotions/automation/%s", ambiente, guid)))
                .timeout(ofSeconds(10))
                .header("Authorization", "Bearer " + getEcommToken())
                .GET()
                .build();

        try {
            String response = clientHttp.send(planCartPromotionRequest, HttpResponse.BodyHandlers.ofString()).body();
            return objMapper.readTree(response).get("promotion").get(0).toString();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getApigeeToken(String basic) {
        final HttpRequest apigeeTokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://test.apigw.claro.com.br/oauth2/v1/token"))
                .timeout(ofSeconds(10))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + basic)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        try {
            String response = clientHttp.send(apigeeTokenRequest, HttpResponse.BodyHandlers.ofString()).body();
            return objMapper.readTree(response).get("access_token").asText();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Verificar conexao com a VPN (local) ou liberacao de acesso (Jenkins/Bstack).\n" + e);
        }
    }

    public static JsonNode customerProductDetailsRequest(String msisdn) throws HttpStatusException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://test.apigw.claro.com.br/mobile/v1/customers/productdetails")) //Necessário VPN (local) ou liberação de acesso (Jenkins/Bstack)
                .timeout(ofSeconds(15))
                .header("Authorization", "Bearer " + getApigeeToken("V09Dc0xwNmZwMmNWdVpQWkZvVklqQTJNZUFWdnpuR2k6M1pUbzdmT0hvZGZ6R3BDRg=="))
                .header("Accept", "application/json")
                .header("X-QueryString", "msisdn=" + msisdn)
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = clientHttp.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        validateStatusCodeOk(response.body(), response.statusCode(), response.uri().toASCIIString());

        JsonNode jnode;
        try {
            jnode = objMapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return jnode.path("data");
    }
}