package web.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public final class PlanProduct extends Product {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("claroPaymentModePrices")
    private List<ClaroPaymentModePrice> claroPaymentModePrices;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("loyaltyClaroPrices")
    private List<LoyaltyClaroPrice> loyaltyClaroPrices;

    private Classification.Feature getPlanAttributes(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("serviceplanclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .filter(feature -> feature.code.contains(code))
                .findFirst().orElseThrow();
    }

    private List<String> getMediaFeatureValues(String code) {
        return getPlanAttributes(code)
                .featureValues
                .stream()
                .map(featureValue -> featureValue.value.replace("/thumbs/", "")) //Sanity
                .collect(Collectors.toList());
    }

    public String getFormattedPlanPrice(boolean isDebit, boolean hasLoyalty) {
        String paymentMode = isDebit ? "debitcard" : "ticket";

        if (hasLoyalty) {
            return loyaltyClaroPrices.stream().filter(price -> price.promotionSource.equals(paymentMode))
                    .findFirst().orElseThrow()
                    .formattedValue.substring(3);
        } else {
            return claroPaymentModePrices.stream().filter(price -> price.promotionSource.equals(paymentMode))
                    .findFirst().orElseThrow()
                    .formattedValue.substring(3);
        }
    }

    public double getPlanPrice(boolean isDebit, boolean isPrintedInvoice) {
        String paymentMode = isDebit && !isPrintedInvoice ? "debitcard" : "ticket";

        return loyaltyClaroPrices.stream().filter(price -> price.promotionSource.equals(paymentMode))
                .findFirst().orElseThrow()
                .value;
    }

    public boolean hasPlanApps() {
        return hasAttribute("serviceplanclassification", "planapps");
    }

    public String getPlanAppsTitle() {
        return getPlanAttributes("planapps").name;
    }

    public List<String> getPlanApps() {
        return getMediaFeatureValues("planapps");
    }

    public boolean hasExtraPlayApps() {
        return hasAttribute("serviceplanclassification", "planextraplayapps");
    }

    public boolean hasExtraPlayTitle() {
        return hasAttribute("serviceplanclassification", "clarotitleextraplay");
    }

    public String getExtraPlayTitle() {
        return getPlanAttributes("clarotitleextraplay").featureValues.get(0).value;
    }

    public List<String> getExtraPlayApps() {
        return getMediaFeatureValues("planextraplayapps");
    }

    public boolean hasClaroServices() {
        return hasAttribute("serviceplanclassification", "claroservicespdp");
    }

    public String getClaroServicesTitle() {
        return getPlanAttributes("claroservicespdp").name;
    }

    public List<String> getClaroServices() {
        return getMediaFeatureValues("claroservicespdp");
    }

    public boolean hasPlanPortability() {
        return hasAttribute("serviceplanclassification", "planportability");
    }

    public List<String> getPlanPortability() {
        return getPlanAttributes("planportability")
                .featureValues
                .stream()
                .map(featureValue -> featureValue.value)
                .collect(Collectors.toList());
    }

    public static class ClaroPaymentModePrice {

        private ClaroPaymentModePrice() {}

        @JsonProperty("formattedValue")
        String formattedValue;

        @JsonProperty("promotionSource")
        String promotionSource;

        @JsonProperty("value")
        double value;
    }

    public static class LoyaltyClaroPrice {

        private LoyaltyClaroPrice() {}

        @JsonProperty("formattedValue")
        String formattedValue;

        @JsonProperty("promotionSource")
        String promotionSource;

        @JsonProperty("value")
        double value;
    }
}
