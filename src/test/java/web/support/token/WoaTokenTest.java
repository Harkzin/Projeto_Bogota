package web.support.token;

public class WoaTokenTest {

    public static void main(String[] args) {
        // Substitua pelo MSISDN que deseja testar
        String msisdn = "11947840279";

        try {
            // Chamada do método getToken
            String token = WoaToken.getToken(msisdn);
            // Exibir o token no console
            System.out.println("Token encontrado: " + token);
        } catch (Exception e) {
            // Exibir erros no console
            System.err.println("Erro ao buscar o token: " + e.getMessage());
            e.printStackTrace();
        }
    }
}