package support;

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

    private Feature getPlanAttributes(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("serviceplanclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .filter(feature -> feature.code.contains(code))
                .findFirst().orElseThrow();
    }

    private List<String> getThumbsFeatureValues(String code) {
        return getPlanAttributes(code)
                .featureValues
                .stream()
                .map(featureValue -> featureValue.getValue().substring(8)) //Remove o /thumbs/ do nome
                .collect(Collectors.toList());
    }

    public String getTitlePlanApps() {
        return getPlanAttributes("planapps").name;
    }

    public List<String> getPlanApps() {
        return getThumbsFeatureValues("planapps");
    }

    public String getTitleExtraPlay() {
        return getPlanAttributes("clarotitleextraplay").featureValues.get(0).getValue();
    }

    public List<String> getExtraPlayApps() {
        return getThumbsFeatureValues("planextraplayapps");
    }

    public String getTitleClaroServices() {
        return getPlanAttributes("claroservicespdp").name;
    }

    public List<String> getClaroServices() {
        return getThumbsFeatureValues("claroservicespdp");
    }

    public static class Classification {

        private String code;
        private List<Feature> features;
        private String name;

        private Classification() {
        }

        public String getCode() {
            return code;
        }

        public List<Feature> getFeatures() {
            return features;
        }

        public String getName() {
            return name;
        }
    }

    public static class ClaroPaymentModePrice {

        private String formattedValue;
        private String promotionSource;
        private double value;

        private ClaroPaymentModePrice() {
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPromotionSource() {
            return promotionSource;
        }

        public double getValue() {
            return value;
        }
    }

    public static class Feature {

        private String code;
        private List<FeatureValue> featureValues;
        private String name;

        private Feature() {
        }

        public String getCode() {
            return code;
        }

        public List<FeatureValue> getFeatureValues() {
            return featureValues;
        }

        public String getName() {
            return name;
        }
    }

    public static class FeatureValue {

        private String value;

        private FeatureValue() {
        }

        public String getValue() {
            return value;
        }
    }

    public static class LoyaltyClaroPrice {

        private String formattedValue;
        private String promotionSource;
        private double value;

        private LoyaltyClaroPrice() {
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPromotionSource() {
            return promotionSource;
        }

        public double getValue() {
            return value;
        }
    }

    public static class Price {

        private String formattedValue;
        private double value;

        private Price() {
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public double getValue() {
            return value;
        }
    }
}