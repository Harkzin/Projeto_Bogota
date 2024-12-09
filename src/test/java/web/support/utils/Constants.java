package web.support.utils;

import java.util.List;
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
        PORTABILITY,
        ACCESSORY
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
        CREDITCARD,
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

    public enum StatusSuccessPage { //TODO adicionar demais fluxos
        ACQUISITION_PLAN(List.of("Pedido realizado", "Pedido em análise", "Pedido aprovado e em separação", "Pedido a caminho", "Pedido entregue", "Pedido concluído")),
        ACQUISITION_PLAN_EXPRESS(List.of("Pedido realizado", "Pedido em análise", "Pedido aprovado e em separação", "Pedido concluído")),
        ACQUISITION_DEVICE(List.of("Pedido recebido", "Pedido em análise", "Pedido aprovado e em separação", "Pedido faturado", "Pedido em transporte", "Pedido entregue e ativado")),
        MIGRATE_EXCHANGE_PLAN(List.of()),
        MIGRATE_EXCHANGE_DEVICE(List.of()),
        PORTABILITY_PLAN(List.of()),
        PORTABILITY_DEVICE(List.of()),
        ACCESSORY(List.of());

        private final List<String> statusList;

        StatusSuccessPage(List<String> statusList) {
            this.statusList = statusList;
        }

        public List<String> getStatusList() {
            return this.statusList;
        }
    }
}