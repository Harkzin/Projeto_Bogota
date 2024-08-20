package web.support;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public final class Product {

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    @JsonProperty("classifications")
    private List<Classification> classifications;
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    @JsonProperty("claroPaymentModePrices")
    private List<ClaroPaymentModePrice> claroPaymentModePrices;
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
    @JsonProperty("loyaltyClaroPrices")
    private List<LoyaltyClaroPrice> loyaltyClaroPrices;
    private Price price;
    private String description;
    private String summary;
    private String code;
    private String name;
    private String url;

    private Feature getPlanAttributes(String code) {
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

    private boolean hasAttribute(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("serviceplanclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .anyMatch(feature -> feature.code.contains(code));
    }

    public Price getBaseDevicePrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getSummary() {
        return summary;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
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

    public boolean hasPlanApps() {
        return hasAttribute("planapps");
    }

    public String getPlanAppsTitle() {
        return getPlanAttributes("planapps").name;
    }

    public List<String> getPlanApps() {
        return getMediaFeatureValues("planapps");
    }

    public boolean hasExtraPlayApps() {
        return hasAttribute("planextraplayapps");
    }

    public boolean hasExtraPlayTitle() {
        return hasAttribute("clarotitleextraplay");
    }

    public String getExtraPlayTitle() {
        return getPlanAttributes("clarotitleextraplay").featureValues.get(0).value;
    }

    public List<String> getExtraPlayApps() {
        return getMediaFeatureValues("planextraplayapps");
    }

    public boolean hasClaroServices() {
        return hasAttribute("claroservicespdp");
    }

    public String getClaroServicesTitle() {
        return getPlanAttributes("claroservicespdp").name;
    }

    public List<String> getClaroServices() {
        return getMediaFeatureValues("claroservicespdp");
    }

    public boolean hasPlanPortability() {
        return hasAttribute("planportability");
    }

    public List<String> getPlanPortability() {
        return getPlanAttributes("planportability")
                .featureValues
                .stream()
                .map(featureValue -> featureValue.value)
                .collect(Collectors.toList());
    }

    public static class Classification {

        @JsonProperty("code")
        private String code;
        @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
        @JsonProperty("features")
        private List<Feature> features;
        @JsonProperty("name")
        private String name;

        private Classification() {
        }
    }

    public static class ClaroPaymentModePrice {

        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("promotionSource")
        private String promotionSource;
        @JsonProperty("value")
        private double value;

        private ClaroPaymentModePrice() {
        }
    }

    public static class Feature {

        @JsonProperty("code")
        private String code;
        @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection"})
        @JsonProperty("featureValues")
        private List<FeatureValue> featureValues;
        @JsonProperty("name")
        private String name;

        private Feature() {
        }
    }

    public static class FeatureValue {

        @JsonProperty("value")
        private String value;

        private FeatureValue() {
        }
    }

    public static class LoyaltyClaroPrice {

        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("promotionSource")
        private String promotionSource;
        @JsonProperty("value")
        private double value;

        private LoyaltyClaroPrice() {
        }
    }

    public static class Price {

        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("value")
        private double value;

        private Price() {
        }
    }
}