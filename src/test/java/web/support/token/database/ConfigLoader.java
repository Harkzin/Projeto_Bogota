package src.test.java.web.support.token.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static DatabaseConfig loadDatabaseConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            // Caminho para o arquivo token.yml
            return mapper.readValue(new File("src/main/resources/token.yml"), DatabaseConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo token.yml", e);
        }
    }
}

