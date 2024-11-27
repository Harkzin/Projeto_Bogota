package web.support.utils;

import java.util.Map;

public final class Constants {

    private Constants() {}

    public static final String ambiente = System.getProperty("env", "s6").toLowerCase();
    public static final String urlAmbiente = String.format("https://accstorefront.cokecxf-commercec1-%s-public.model-t.cc.commerce.ondemand.com", ambiente);
    public static final String focusPlan = System.getProperty("focusPlan", "17528"); //Plano foco configurado via property no ambiente.

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

    public enum ZoneDeliveryMode {
        CONVENTIONAL,
        EXPRESS
    }

    public enum InvoiceType {
        WHATSAPP,
        DIGITAL, //E-mail
        PRINTED, //Correios
        APP //App Minha Claro
    }

    public enum StandardPaymentMode {
        DEBITCARD,
        TICKET,
        PIX,
        VOUCHER,
        CLAROCLUBE
    }

    public static final Map<String, String> planSingleToCombo = Map.of(
            "17528", "17562",
            "17536", "17564",
            "17558", "17566",

            "17515", "17530",
            "17522", "17538",
            "17524", "17540"
    );

    public static final double DEPENDENT_PRICE = 50D;

    public enum ChipType {
        SIM,
        ESIM
    }
}