package support;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.json.internal.LazyMap;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RESTSupport {

    protected static DriverQA driver = new DriverQA();
    private static Response response;
    private static String verificationProcessId;
    private static String solicitationId;
    private static String xAuthToken;
    private static String userId;
    private static ArrayList<String> op;

    public static String getUserId(){
        return userId;
    }

    public static String getxAuthToken() {
		return xAuthToken;
	}

	public static Response getResponse() {
        return response;
    }
	
	public static String getSolicitation() {
		return solicitationId;
	}

	public static String getVerificationProcessId() {
		return verificationProcessId;
	}

    private static void setResponse(Response response) {
        RESTSupport.response = response;
    }

    private static RequestSpecification buildBaseRequestSpecification() {

        RequestSpecification rs = given()
        .when()
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON);
        return rs;
    }

    private static void addHeader(Header h, RequestSpecification rs) {
        if (h != null) {
            rs.header(h);
        }
    }

    private static void addCookies(Map<String, String> c, RequestSpecification rs) {
        if (c != null) {
            rs.cookies(c);
        }
    }

    public static void executeGet(String endpoint, Integer statusCode) {
        response = buildBaseRequestSpecification()
                .accept("*/*")
                .get(endpoint)
                .then()
                .statusCode(statusCode)
                .extract().response();
        printLog(response.getBody().asString(),endpoint,"");
        setResponse(response);
    }

    public static Response executeGet(String endpoint, LazyMap json) {
        response = buildBaseRequestSpecification()
        		.headers(json)
                .accept("*/*")
                .get(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint,"");
        return response;
    }

    public static Response executePost(String endpoint, Integer statusCode, LazyMap json) {
        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
        		.body(json)
                .post(endpoint)
                .then()
                .statusCode(statusCode)
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }

    public static Response executePost(String endpoint, LazyMap json) {
        System.out.println("JSON -> " + json.toString());
        response = buildBaseRequestSpecification()
        		.body(json)
                .post(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }
    public static Response executePost(String endpoint, LazyMap json, LazyMap json1) {
        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
        	    .headers(json1)
                .body(json)
                .post(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }
    
    public static Response executePost(String endpoint, String json, LazyMap json1) {
//        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
        	    .headers(json1)
                .body(json)
                .post(endpoint)
                .then()
                .extract().response();
        xAuthToken = response.getHeader("X-Auth-Token");
        
        System.out.println("Token:\n" + xAuthToken);
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
        
    }
    
    public static Response executePut(String endpoint, LazyMap json) {
        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
                .body(json)
                .put(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }

    public static Response executeDelete(String endpoint) {

        response = buildBaseRequestSpecification()
                .delete(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, "");
        return response;
    }

    public static Response executeOptions(String endpoint) {
        response = buildBaseRequestSpecification()
                .options(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint,"");
        return response;
    }

    public static Response executePatch(String endpoint, LazyMap json, LazyMap json2) {
        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
                .body(json)
                .patch(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }
    
    public static Response executePatch(String endpoint, LazyMap json) {
        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
                .body(json)
                .patch(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }

    public static Response executePatch(String endpoint, String json, LazyMap json1) {
//      System.out.println("JSON -> " + json.toString());

      response = buildBaseRequestSpecification()
      	      .headers(json1)
              .body(json)
              .patch(endpoint)
              .then()
              .extract().response();
      xAuthToken = response.getHeader("X-Auth-Token");
      
      System.out.println("Token:\n" + xAuthToken);
      printLog(response.getBody().asString(),endpoint, json.toString());
      return response;
    
    }

    private static void printLog(String response, String url, String json){
        System.out.println("");
        System.out.println("====================================");
        System.out.println("");
        System.out.println("Endpoint => "+ url);
        System.out.println("");
        System.out.println("Body - Request => " + json);
        System.out.println("");
        System.out.println("Response => "+ response);
    }

    public static Integer getResponseCode() {
    	return response.getStatusCode();
    }

    public static Object key(String field) {
        return response.getBody().jsonPath().get(field);
    }


    
    public static Object opFromFlow(String field) {
		String op = "";
		ArrayList<HashMap<String,String>> test = response.jsonPath().get("flow");
		for (HashMap<String,String> tes : test) {
			if(tes.get("target").equals(field)){ 
				op = tes.get("op");
			}
		}
        return op;
    }
    
    public static Response executePost(String endpoint, String json) {
        System.out.println("JSON -> " + json.toString());
        response = buildBaseRequestSpecification()
        		.body(json)
                .post(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }


	public static void executePostToken(String url, String op, String jsonFormat, LazyMap fieldsHeader) {
		System.out.println("JSON -> " + jsonFormat);
		
        response = buildBaseRequestSpecification()
        	    .headers("X-Auth-Token", xAuthToken)
        	    .headers("op", op)
                .body(jsonFormat)
                .post(url)
                .then()
                .extract().response();
//        System.out.println("X-Auth-Token:\n" + xAuthToken);
//        System.out.println("op:\n" + op);
        printLog(response.getBody().asString(),url, jsonFormat.toString());
		
		
	}

	public static Response executePostGetProspectId(String endpoint, String json, LazyMap json1) {
        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
        	    .headers(json1)
                .body(json)
                .post(endpoint)
                .then()
                .extract().response();
        solicitationId = response.path("data.prospect_id");
        System.out.println("pegou o ID do solicitation que eh: " +  solicitationId);
//        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }
	
	public static Response executePostGetVerificationProcessId(String endpoint, String json, LazyMap json1) {
//        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
        	    .headers(json1)
                .body(json)
                .post(endpoint)
                .then()
                .extract().response();
        verificationProcessId = response.path("data.verification_process_id");
        System.out.println("pegou o ID do verification que eh: " +  verificationProcessId);
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }

	public static Response executePostSolicitation(String endpoint, String json, LazyMap json1) {
//        System.out.println("JSON -> " + json.toString());
        System.out.println("Antes de executar o Solicitation " +  solicitationId);
//        resultadoSolicitation = "{\"data\":{\"solicitation_id\":\""+solicitationId+"\",\"solicitation_type\":\"openAccount\",\"channel_id\":\"A\",\"prospect_id\":[\""+solicitationId+"\"],\"accepted_terms_ids\":[\"HABEAS_1.0\"]}}";
       
        response = buildBaseRequestSpecification()
        	    .headers(json1)
                .body(json)
                .post(endpoint)
                .then()
                .extract().response();
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
    }

    public static Response executePostGetUserId(String endpoint, String json, LazyMap json1) {
        System.out.println("JSON -> " + json.toString());

        response = buildBaseRequestSpecification()
                .headers(json1)
                .body(json)
                .post(endpoint)
                .then()
                .extract().response();
        userId = response.path("data.id");
        System.out.println("pegou o UserID " +  userId);
        printLog(response.getBody().asString(),endpoint, json.toString());
        return response;
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
