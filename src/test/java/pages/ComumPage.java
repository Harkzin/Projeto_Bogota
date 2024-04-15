package pages;

import support.DriverQA;

public class ComumPage {
    private final DriverQA driver;

    public ComumPage(DriverQA stepDriver) {
        driver = stepDriver;
    }

    public static String urlAmbiente = "https://accstorefront.cokecxf-commercec1-" + System.getProperty("env", "s6").toLowerCase() + "-public.model-t.cc.commerce.ondemand.com";
    public static String planId;
    public static Boolean isDebitPaymentFlow;
    public static Boolean hasLoyalty;

    public void validarResumoCompra() {
        //TODO ECCMAUT-351
    }
}
