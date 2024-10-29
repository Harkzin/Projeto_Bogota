package web.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public final class PlanProduct extends Product {

    @JsonProperty("claroPaymentModePrices")
    private List<ClaroPaymentModePrice> claroPaymentModePrices;

    @JsonProperty("loyaltyClaroPrices")
    private List<LoyaltyClaroPrice> loyaltyClaroPrices;

    @JsonProperty("dataBonusForPlan")
    private DataBonusForPlan dataBonusForPlan;

    @JsonProperty("dependentQuantity")
    private int dependentQuantity;

    @JsonProperty("passports")
    private List<Passport> passports;

    private boolean hasAttribute(String code) {
        return classifications.stream()
                .filter(c -> c.code.contains("serviceplanclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .anyMatch(feature -> feature.code.contains(code));
    }

    private Classification.Feature getAttributes(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("serviceplanclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .filter(feature -> feature.code.contains(code))
                .findFirst().orElseThrow();
    }

    private List<String> getMediaFeatureValues(String code) {
        return getAttributes(code)
                .featureValues
                .stream()
                .map(featureValue -> featureValue.value.replace("/thumbs/", "")) //Sanity
                .collect(Collectors.toList());
    }

    public String getFormattedPrice(boolean isDebit, boolean hasLoyalty) {
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

    public double getPrice(boolean isDebit, boolean isPrintedInvoice) {
        String paymentMode = isDebit && !isPrintedInvoice ? "debitcard" : "ticket";

        return loyaltyClaroPrices.stream().filter(price -> price.promotionSource.equals(paymentMode))
                .findFirst().orElseThrow()
                .value;
    }

    public boolean hasPlanApps() {
        return hasAttribute("planapps");
    }

    public String getPlanAppsTitle() {
        return getAttributes("planapps").name;
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
        return getAttributes("clarotitleextraplay").featureValues.get(0).value;
    }

    public List<String> getExtraPlayApps() {
        return getMediaFeatureValues("planextraplayapps");
    }

    public boolean hasClaroServices() {
        return hasAttribute("claroservicespdp");
    }

    public String getClaroServicesTitle() {
        return getAttributes("claroservicespdp").name;
    }

    public List<String> getClaroServices() {
        return getMediaFeatureValues("claroservicespdp");
    }

    public boolean hasPlanPortability() {
        return hasAttribute("planportability");
    }

    public List<String> getPlanPortability() {
        return getAttributes("planportability")
                .featureValues
                .stream()
                .map(featureValue -> featureValue.value)
                .collect(Collectors.toList());
    }

    public int getDependentQuantity() {
        return dependentQuantity;
    }

    public boolean hasBonus() {
        return dataBonusForPlan != null;
    }

    public List<String> getDataBonus() {
        return dataBonusForPlan.values.stream().map(v -> String.format("%sGB %s", v.value, v.key)).collect(Collectors.toList());
    }

    public boolean hasPassport() {
        return passports != null;
    }

    public List<Passport> getPassports() {
        return passports;
    }

    public static final class ClaroPaymentModePrice {

        private ClaroPaymentModePrice() {}

        @JsonProperty("formattedValue")
        String formattedValue;

        @JsonProperty("promotionSource")
        String promotionSource;

        @JsonProperty("value")
        double value;
    }

    public static final class LoyaltyClaroPrice {

        private LoyaltyClaroPrice() {}

        @JsonProperty("formattedValue")
        String formattedValue;

        @JsonProperty("promotionSource")
        String promotionSource;

        @JsonProperty("value")
        double value;
    }

    public static final class DataBonusForPlan {

        private DataBonusForPlan() {}

        @JsonProperty("total")
        public int total;

        @JsonProperty("values")
        public List<Value> values;

        public static final class Value {

            @JsonProperty("key")
            public String key;

            @JsonProperty("value")
            public int value;
        }
    }

    public static final class Passport {

        private Passport() {}

        @JsonProperty("description")
        private String description;

        @JsonProperty("passportTraffics")
        private List<PassportTraffic> passportTraffics;

        public String getDescription() {
            return description;
        }

        public List<PassportTraffic> getPassportTraffics() {
            return passportTraffics;
        }

        public static final class PassportTraffic {

            private PassportTraffic() {}

            @JsonProperty("country")
            private Country country;

            public Country getCountry() {
                return country;
            }

            public static final class Country {

                private Country() {}

                @JsonProperty("altText")
                private String altText;

                @JsonProperty("url")
                private String url;

                public String getAltText() {
                    return altText;
                }

                public String getUrl() {
                    return url;
                }

            }

        }

    }


}
