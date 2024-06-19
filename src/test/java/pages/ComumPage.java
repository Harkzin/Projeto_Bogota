package pages;

import support.DriverQA;

public class ComumPage {
    private final DriverQA driver;

    public ComumPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public static final String urlAmbiente = "https://accstorefront.cokecxf-commercec1-" + System.getProperty("env", "s6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com";
    public static String Cart_emailAddress;
    public static String Cart_planId;
    public static ProcessType Cart_processType;
    public static boolean Cart_isDebitPaymentFlow = true;
    public static boolean Cart_hasLoyalty = true;
    public static boolean Cart_hasDevice = false;
    public static boolean Cart_isExpressDelivery;
    public static boolean Cart_isThabFlow = false;
    public static String Cart_deviceId;

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

    public void validarResumoCompra() {
        //TODO ECCMAUT-351
    }

    public void validarAparelhoResumoCompra() {
        //TODO ECCMAUT-351
    }
}