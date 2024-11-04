package massasController;

import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import web.support.utils.DriverWeb;

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
    private static DriverWeb driverWeb = null;

    @Autowired
    public ConsultaCPFMSISDN(DriverWeb driverWeb) {
        ConsultaCPFMSISDN.driverWeb = driverWeb;
    }

    public static AbstractMap.SimpleEntry<String, String> consultarDadosBase(String segmento, String formaPagamento, String formaEnvio,
                                                                             String combo, String multaServico, String multaAparelho,
                                                                             String dependente, String claroClube, String crivo) {
        try {
            Massas massas = loadMassas(BASE_FILE_PATH);
            Massas.Massa massa = findMassaLiberada(massas, segmento, formaPagamento, formaEnvio, combo, multaServico, multaAparelho, dependente, claroClube, crivo);
            updateMassaStatus(massas, massa, "bloqueada", BASE_FILE_PATH);
            msisdn = massa.msisdn;
            cpf = massa.cpf;
            return new AbstractMap.SimpleEntry<>(msisdn, cpf);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao consultar dados liberados: " + e.getMessage(), e);
        }
    }

    public static String consultarDadosPortabilidade() {
        try {
            Massas massas = loadMassas(BASE_FILE_PATH);
            Massas.Massa massa = massas.massasList.stream()
                    .filter(m -> m.status.equals("ativo") && m.segmento.equalsIgnoreCase("portabilidade"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Nenhuma massa ativa encontrada para o segmento 'portabilidade'"));

            updateMassaStatus(massas, massa, "bloqueada", BASE_FILE_PATH);
            return massa.msisdn;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao consultar dados de portabilidade: " + e.getMessage(), e);
        }
    }

    public static void restaurarStatusParaAtivo(Scenario scenario) {
        String url = driverWeb.getDriver().getCurrentUrl();
        if (scenario.isFailed() && !url.contains("/checkout/orderConfirmation")) {
            restoreStatus("ativo");
        }
    }

    public static void restaurarStatusParaAtivoCenariosBloqueio() {
        restoreStatus("ativo");
    }

    public static void marcarComoQueimada() {
        restoreStatus("queimada");
    }

    private static Massas loadMassas(String baseFilePath) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(BASE_FILE_PATH));
        return objMapper.readValue(jsonData, Massas.class);
    }

    private static Massas.Massa findMassaLiberada(Massas massas, String segmento, String formaPagamento, String formaEnvio,
                                                  String combo, String multaServico, String multaAparelho,
                                                  String dependente, String claroClube, String crivo) {
        List<Predicate<Massas.Massa>> filters = createFilters(segmento, formaPagamento, formaEnvio, combo, multaServico, multaAparelho, dependente, claroClube, crivo);
        return massas.massasList.stream()
                .filter(filters.stream().reduce(x -> true, Predicate::and))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhuma massa liberada encontrada"));
    }

    private static List<Predicate<Massas.Massa>> createFilters(String segmento, String formaPagamento, String formaEnvio,
                                                               String combo, String multaServico, String multaAparelho,
                                                               String dependente, String claroClube, String crivo) {
        List<Predicate<Massas.Massa>> filters = new ArrayList<>();
        filters.add(m -> m.status.equals("ativo"));
        filters.add(m -> m.segmento.equals(segmento));
        filters.add(m -> m.formaPagamento.equals(formaPagamento));
        filters.add(m -> m.formaEnvio.equals(formaEnvio));
        filters.add(m -> m.comboMulti.equals(combo));
        addOptionalFilters(filters, multaServico, multaAparelho, dependente, claroClube, crivo);
        return filters;
    }

    private static void addOptionalFilters(List<Predicate<Massas.Massa>> filters, String multaServico, String multaAparelho,
                                           String dependente, String claroClube, String crivo) {
        if (multaServico != null) {
            filters.add(m -> m.multaServico == Boolean.parseBoolean(multaServico));
        }
        if (multaAparelho != null) {
            filters.add(m -> m.multaAparelho == Boolean.parseBoolean(multaAparelho));
        }
        if (dependente != null) {
            filters.add(m -> m.dependente == Boolean.parseBoolean(dependente));
        }
        if (claroClube != null) {
            filters.add(m -> m.claroClube == Boolean.parseBoolean(claroClube));
        }
        if (crivo != null) {
            filters.add(m -> m.crivo.equals(crivo));
        }
    }

    private static void restoreStatus(String status) {
        try {
            Massas massas = loadMassas(BASE_FILE_PATH);
            massas.massasList.stream()
                    .filter(m -> m.msisdn.equals(msisdn))
                    .forEach(m -> m.status = status);
            saveMassas(massas);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao restaurar status da massa: " + e.getMessage(), e);
        }
    }

    private static void updateMassaStatus(Massas massas, Massas.Massa massa, String newStatus, String baseFilePath) {
        massa.setStatus(newStatus);
        msisdn = massa.msisdn;
        saveMassas(massas);
    }

    private static void saveMassas(Massas massas) {
        try {
            File file = new File(BASE_FILE_PATH);
            objMapper.writeValue(file, massas);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados no arquivo local: " + e.getMessage(), e);
        }
    }
}
