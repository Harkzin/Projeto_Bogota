package support;

import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pages.ComumPage.Email;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.get;
import static java.time.Duration.ofSeconds;
import static pages.ComumPage.Email.CONFIRMA_TOKEN;

public final class RestAPI {
    private RestAPI() {
    }

    private static final HttpClient clientHttp = HttpClient.newHttpClient();
    private static final ObjectMapper objMapper = new ObjectMapper();

    public static String getCpf() throws IOException, InterruptedException {
        final HttpRequest getCpfRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://www.4devs.com.br/ferramentas_online.php"))
                .timeout(ofSeconds(15))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("acao=gerar_cpf&pontuacao=N&cpf_estado=SP"))
                .build();

        return clientHttp.send(getCpfRequest, HttpResponse.BodyHandlers.ofString()).body(); //Retorna um CPF como String.
    }

    public static boolean checkCpfDiretrix(String cpf) throws IOException, InterruptedException {
        final HttpRequest diretrixTokenRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api-lab.claro.com.br/oauth2/v1/token"))
                .timeout(ofSeconds(15))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("X-Client-Auth", "Basic MWxKR1dqdkhsVzEzWkdmT0pxUVlHQ3JFTlRNY0x3Vno6QVowTTF3aVA5cFJSWGplTg==")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

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
    }

    public static Document getEmailMessage(String emailAddress, Email emailSubject) {
        final String MAILSAC_KEY = "k_YKJeUgIItKTd03DqOGRFAPty89C2gXR6zLLw39";

        final HttpRequest getMessages = HttpRequest.newBuilder()
                .uri(URI.create("https://mailsac.com/api/addresses/" + emailAddress + "/messages"))
                .timeout(ofSeconds(15))
                .header("Mailsac-Key", MAILSAC_KEY)
                .GET()
                .build();

        final HttpRequest clearInbox = HttpRequest.newBuilder()
                .uri(URI.create("https://mailsac.com/api/addresses/" + emailAddress + "/messages"))
                .timeout(ofSeconds(15))
                .header("Mailsac-Key", MAILSAC_KEY)
                .DELETE()
                .build();

        List<JsonNode> messageList;

        try {
            if (emailSubject.equals(CONFIRMA_TOKEN)) {
                clientHttp.send(clearInbox, HttpResponse.BodyHandlers.discarding());
            }

            messageList = objMapper.readValue(clientHttp.send(getMessages, HttpResponse.BodyHandlers.ofString()).body(), new TypeReference<>() {
            });
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