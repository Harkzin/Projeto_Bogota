package src.test.java.web.support.token;

import src.test.java.web.support.token.database.ConfigLoader;
import src.test.java.web.support.token.database.DatabaseConfig;
import src.test.java.web.support.utils.CryptographySHA256;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WoaToken {

    /**
     * Busca um token baseado no MSISDN informado.
     *
     * @param msisdn O MSISDN a ser pesquisado.
     * @return O token descriptografado.
     */

    public static String getToken(String msisdn) {
        // Carregar configurações do arquivo YAML
        DatabaseConfig config = ConfigLoader.loadDatabaseConfig();
        String token = "";

        try (Connection connection = DriverManager.getConnection(
                config.getJdbcUrl(), config.getUsername(), config.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from SUE_VALIDACAO_TOKEN where cd_composicao_token=? order by dt_criacao desc")) {

            preparedStatement.setString(1, msisdn);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String data = resultSet.getString("vl_codigo_autorizado");
                    token = CryptographySHA256.decrypt(data);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar o banco de dados", e);
        }

        return token;
    }
}
