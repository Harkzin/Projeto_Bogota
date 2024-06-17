package support.utils;

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

    public enum InvoiceType {
        WHATSAPP,
        EMAIL,
        PRINTED
    }

    public enum PlanPaymentMode {
        DEBIT,
        TICKET,
        CREDIT_CARD
    }
}
