package massasController;

import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static web.support.api.RestAPI.objMapper;

public class ConsultaCPFMSISDN {

    private static final String BASE_FILE_PATH = "src/main/resources/massas/Base.json";
    private static String msisdn;
    private static String cpf;
    private static final List<Massas.Massa> massasConsultadas = new ArrayList<>();

    public static AbstractMap.SimpleEntry<String, String> consultarDadosBase(String segmento, String formaPagamento,
                                                                             String formaEnvio, String combo,
                                                                             String multaServico, String multaAparelho,
                                                                             String claroClube, String crivo) {
        try {
            Massas massas = loadMassas();
            Massas.Massa massa = findMassaLiberada(massas, segmento, formaPagamento, formaEnvio, combo,
                    multaServico, multaAparelho, claroClube, crivo);
            updateMassaStatus(massas, massa);
            msisdn = massa.msisdn;
            cpf = massa.cpf;
            return new AbstractMap.SimpleEntry<>(msisdn, cpf);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao consultar dados liberados: " + e.getMessage(), e);
        }
    }

    public static String consultarDadosPortabilidade() {
        try {
            Massas massas = loadMassas();
            Massas.Massa massa = massas.massasList.stream()
                    .filter(m -> m.status.equals("ativo") && m.segmento.equalsIgnoreCase("portabilidade"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Nenhuma massa ativa encontrada para o segmento 'portabilidade'"));

            updateMassaStatus(massas, massa);

            massasConsultadas.add(massa);
            return massa.msisdn;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao consultar dados de portabilidade: " + e.getMessage(), e);
        } finally {
            restaurarStatusPosCenario(null, "", false);
        }
    }

    private static List<Predicate<Massas.Massa>> createFilters(String segmento, String formaPagamento, String formaEnvio,
                                                               String combo, String multaServico, String multaAparelho,
                                                               String claroClube, String crivo) {
        List<Predicate<Massas.Massa>> filters = new ArrayList<>();
        filters.add(m -> m.status.equals("ativo"));
        filters.add(m -> m.segmento.equals(segmento));
        filters.add(m -> m.formaPagamento.equals(formaPagamento));
        filters.add(m -> m.formaEnvio.equals(formaEnvio));
        filters.add(m -> m.comboMulti.equals(combo));
        filters.add(m -> m.multaServico == Boolean.parseBoolean(multaServico));
        filters.add(m -> m.multaAparelho == Boolean.parseBoolean(multaAparelho));
        filters.add(m -> m.claroClube == Boolean.parseBoolean(claroClube));
        filters.add(m -> m.crivo.equals(crivo));
        return filters;
    }

    private static Massas loadMassas() throws IOException {
        return objMapper.readValue(Files.readAllBytes(Paths.get(BASE_FILE_PATH)), Massas.class);
    }

    private static Massas.Massa findMassaLiberada(Massas massas, String segmento, String formaPagamento,
                                                  String formaEnvio, String combo, String multaServico,
                                                  String multaAparelho, String claroClube, String crivo) {
        List<Predicate<Massas.Massa>> filters = createFilters(segmento, formaPagamento, formaEnvio, combo,
                multaServico, multaAparelho, claroClube, crivo);
        return massas.massasList.stream()
                .filter(filters.stream().reduce(x -> true, Predicate::and))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhuma massa liberada encontrada"));
    }

    public static void restaurarStatusPosCenario(Scenario scenario, String url, boolean hasErrorPasso1) {
        if (url.contains("/checkout/orderConfirmation")) {
            restoreStatus("queimada");
        } else if (hasErrorPasso1) {
            restoreStatus("erroPasso1");
        } else {
            restoreStatus("ativo");
        }
    }

    private static void restoreStatus(String status) {
        try {
            Massas massas = loadMassas();
            massas.massasList.stream()
                    .filter(m -> m.msisdn.equals(msisdn) || massasConsultadas.stream().anyMatch(mc -> mc.msisdn.equals(m.msisdn)))
                    .forEach(m -> m.status = status);
            saveMassas(massas);
            massasConsultadas.clear();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao restaurar status da massa: " + e.getMessage(), e);
        }
    }

    private static void updateMassaStatus(Massas massas, Massas.Massa massa) {
        massa.setStatus("bloqueada");
        msisdn = massa.msisdn;
        saveMassas(massas);
    }

    private static void saveMassas(Massas massas) {
        try {
            objMapper.writeValue(new File(BASE_FILE_PATH), massas);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados no arquivo local: " + e.getMessage(), e);
        }
    }
}