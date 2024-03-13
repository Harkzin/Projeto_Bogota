package support;

import io.restassured.response.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpRequest;
import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.get;
import static java.time.Duration.ofSeconds;

public final class RestAPI {

    private RestAPI() {
    }

    private static final HttpClient clientHttp = HttpClient.newHttpClient();
    private static final ObjectMapper objMapper = new ObjectMapper();

    public static String getCpf() throws IOException, InterruptedException {
        final HttpRequest getCpfRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://www.4devs.com.br/ferramentas_online.php"))
                .timeout(ofSeconds(10))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("acao=gerar_cpf&pontuacao=N&cpf_estado=SP"))
                .build();

        return clientHttp.send(getCpfRequest, HttpResponse.BodyHandlers.ofString()).body(); //Retorna um CPF como String.
    }

    public static boolean checkCpfDiretrix(String cpf) throws IOException, InterruptedException {
        final HttpRequest diretrixTokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-lab.claro.com.br/oauth2/v1/token"))
                .timeout(ofSeconds(10))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("X-Client-Auth", "Basic MWxKR1dqdkhsVzEzWkdmT0pxUVlHQ3JFTlRNY0x3Vno6QVowTTF3aVA5cFJSWGplTg==")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        String token = objMapper.readTree(clientHttp.send(diretrixTokenRequest, HttpResponse.BodyHandlers.ofString()).body())
                .get("access_token")
                .asText();

        final HttpRequest diretrixRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-lab.claro.com.br/customers/v1/profiles/historical"))
                .timeout(ofSeconds(10))
                .header("X-Client-Auth", "Bearer " + token)
                .header("X-QueryString", "documentnumber=" + cpf)
                .GET()
                .build();

        HttpResponse<String> response = clientHttp.send(diretrixRequest, HttpResponse.BodyHandlers.ofString());
        JsonNode node = objMapper.readTree(response.body());

        return response.statusCode() != 422 || !node.at("/error").get("detailedMessage").asText().equalsIgnoreCase("CPF não encontrado"); //CPF na Diretrix? = true, fora da Diretrix? = false, para testes deve ser false.
    }

    private static final DriverQA driver = new DriverQA();

    public static String getAccessToken(String url) throws JSONException {
        String paramAuth = url + "/authorizationserver/oauth/token?client_id=claro_client&client_secret=cl4r0&grant_type=client_credentials";
        String paramToken = url + "/clarowebservices/v2/claro/checkout/step/token";

        JSONObject requestParams = new JSONObject();
        requestParams.put("grant_type", "client_credentials");
        requestParams.put("client_secret", "cl4r0");
        requestParams.put("client_id", "claro_client");

        Map<String, Object> jsonObjectMap = toMap(requestParams);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(jsonObjectMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response accessAuth =
                given().
                        body(jsonString).
                        when().
                        post(paramAuth).
                        then().
                        assertThat().statusCode(200).
                        extract().response();

        String auth = accessAuth.jsonPath().getString("access_token");

        String requestBody = "{\n" +
                "    \"cartGUID\":\"" + driver.getCookies().substring(11) + "\"\n" +
                "}";

        Response returnToken =
                given().
                        header("Content-Type", "application/json").
                        auth().oauth2(auth).
                        body(requestBody).
                        when().
                        post(paramToken).
                        then().
                        assertThat().statusCode(200).
                        extract().response();

        return returnToken.jsonPath().getString("validateTokenTest");
    }

    private static Map<String, Object> toMap(JSONObject json) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, json.get(key));
        }
        return map;
    }

    public static String getMessageFirstId() throws JSONException, InterruptedException {
        Thread.sleep(30000);
        Response response = get("https://mailsac.com/api/addresses/clordertest@mailsac.com/messages/?_mailsacKey=k_TYuwAJiFKZzxwZynlOIrMNH3kIjpbcg42");
        JSONArray jsonArray = new JSONArray(response.getBody().asString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String idMessage = jsonObject.getString("_id");
        return idMessage;
    }

    public static void purgeInbox(String messageId) throws JSONException {
        delete("https://mailsac.com/api/addresses/clordertest@mailsac.com/messages/" + messageId + "?_mailsacKey=k_TYuwAJiFKZzxwZynlOIrMNH3kIjpbcg42");
    }

    public static String getPedidoEnderecoNome(String messageId) throws JSONException {
        Response response = get("https://mailsac.com/api/text/clordertest@mailsac.com/" + messageId + "?_mailsacKey=k_TYuwAJiFKZzxwZynlOIrMNH3kIjpbcg42");
        return response.getBody().asString();
    }
}
