package support.utils;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

    private Constants() {}

    public static final String ambiente = System.getProperty("env", "s6").toLowerCase();
    public static final String urlAmbiente = "https://accstorefront.cokecxf-commercec1-" + ambiente + "-public.model-t.cc.commerce.ondemand.com";

    public enum ProcessType {
        ACQUISITION,
        APARELHO_TROCA_APARELHO,
        EXCHANGE,
        EXCHANGE_PROMO,
        MIGRATE,
        PORTABILITY
    }

    public enum Email {
        CONFIRMA_TOKEN("Claro: Confirmação de dados Token"),
        PEDIDO_REALIZADO_BASE("Pedido Realizado com Sucesso"),
        PEDIDO_REALIZADO_AQUISICAO("Claro: Pedido realizado com Sucesso");

        //TODO Listar demais e-mails

        private final String subject;

        Email(String subject) {
            this.subject = subject;
        }

        public String getSubject() {
            return subject;
        }
    }

    public enum DeliveryMode {
        CONVENTIONAL,
        EXPRESS
    }

    public static final Map<String, String> planSingleToCombo = Map.of(
            "17216", "17353",
            "17218", "17354",
            "17226", "17230",

            "17270", "17260",
            "17268", "17258",
            "17266", "17256"
    );
}
