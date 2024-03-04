package support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.net.URI;
import java.net.http.HttpRequest;
import java.io.IOException;
import static java.time.Duration.ofSeconds;

public class RestAPI {

    protected static DriverQA driver = new DriverQA();

    public static HttpRequest getCpfRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://www.4devs.com.br/ferramentas_online.php"))
            .timeout(ofSeconds(5))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString("acao=gerar_cpf&pontuacao=N&cpf_estado=SP"))
            .build();

    public static HttpRequest diretrixTokenRequest = HttpRequest.newBuilder()
            .uri(URI.create("https://api-lab.claro.com.br/oauth2/v1/token"))
            .timeout(ofSeconds(5))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .header("X-Client-Auth", "Basic MWxKR1dqdkhsVzEzWkdmT0pxUVlHQ3JFTlRNY0x3Vno6QVowTTF3aVA5cFJSWGplTg==")
            .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
            .build();

    public static HttpRequest diretrixRequest(String bearerToken, String cpf) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api-lab.claro.com.br/customers/v1/profiles/historical"))
                .timeout(ofSeconds(5))
                .header("X-Client-Auth", bearerToken)
                .header("X-QueryString", "documentnumber=" + cpf)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private static RequestSpecification buildBaseRequestSpecification() {
        RequestSpecification rs = given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        return rs;
    }

    public String getAccessToken(String url) throws JSONException {
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
}
