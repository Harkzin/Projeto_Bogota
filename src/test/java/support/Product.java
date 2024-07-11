package support;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("UnusedDeclaration")
public final class Product {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("classifications")
    private List<Classification> classifications;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("claroPaymentModePrices")
    private List<ClaroPaymentModePrice> claroPaymentModePrices;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("loyaltyClaroPrices")
    private List<LoyaltyClaroPrice> loyaltyClaroPrices;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("variantMatrix")
    private List<VariantMatrix> variantMatrix;

    @JsonProperty("price")
    private Price price;

    @JsonProperty("stock")
    private Stock stock;

    private String description;
    private String summary;
    private String code;
    private String name;
    private String url;

    private Classification.Feature getPlanAttributes(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("serviceplanclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .filter(feature -> feature.code.contains(code))
                .findFirst().orElseThrow();
    }

    private Classification.Feature getDeviceAttributes(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("mobilephoneclassification"))
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

    private boolean hasAttribute(String classification, String code) {
        return classifications.stream()
                .filter(c -> c.code.contains(classification))
                .findFirst().orElseThrow()
                .features.stream()
                .anyMatch(feature -> feature.code.contains(code));
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

    public double getPrice() {
        return price.value;
    }

    public double getDebitPlanPrice() {
        return loyaltyClaroPrices.stream().filter(price -> price.promotionSource.equals("debitcard"))
                .findFirst().orElseThrow().value;
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

    public String getFormattedBaseDevicePrice() {
        return "R$ " + String.format(Locale.GERMAN, "%,.2f", (price.value - 10D)); //API retorna o valor com 10 reais a mais. Motivo desconhecido
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

    public boolean hasManufacturer() {
        return hasAttribute("mobilephoneclassification", ".fabricante");
    }

    public String getBrand() {
        return getDeviceAttributes(".fabricante").featureValues.get(0).value;
    }

    public boolean inStock() {
        return stock.stockLevelStatus.equals("inStock");
    }

    public List<List<String>> getVariants() {
        List<List<String>> variants = new ArrayList<>();

        variantMatrix.forEach(variantColor -> {
            List<String> variant = Arrays.asList(variantColor.variantOption.code, variantColor.variantValueCategory.name);
            variants.add(variant);
        });

        return variants;
    }

    public boolean hasDeviceFeatures() {
        return !(classifications == null) && classifications.stream().anyMatch(c -> c.code.equals("mobilephoneclassification"));
    }

    public List<List<String>> getDeviceFeatures() {
        List<List<String>> features = new ArrayList<>();

        classifications.stream()
                .filter(c -> c.code.equals("mobilephoneclassification"))
                .findFirst()
                .orElseThrow()
                .features
                .forEach(feature -> features.add(Arrays.asList(feature.name, feature.featureUnit == null ? feature.featureValues.get(0).value : feature.featureValues.get(0).value + feature.featureUnit.unitType)));

        return features;
    }

    public static class Classification {

        private Classification() {}

        @JsonProperty("code")
        private String code;

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        @JsonProperty("features")
        private List<Feature> features;

        @JsonProperty("name")
        private String name;

        public static class Feature {

            private Feature() {}

            @JsonProperty("code")
            private String code;

            @JsonProperty("featureUnit")
            private FeatureUnit featureUnit;

            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            @JsonProperty("featureValues")
            private List<FeatureValue> featureValues;

            @JsonProperty("name")
            private String name;

            public static class FeatureUnit {

                private FeatureUnit() {}

                @JsonProperty("unitType")
                private String unitType;
            }

            public static class FeatureValue {

                private FeatureValue() {}

                @JsonProperty("value")
                private String value;
            }
        }
    }

    public static class ClaroPaymentModePrice {

        private ClaroPaymentModePrice() {}

        @JsonProperty("formattedValue")
        private String formattedValue;

        @JsonProperty("promotionSource")
        private String promotionSource;

        @JsonProperty("value")
        private double value;
    }

    public static class LoyaltyClaroPrice {

        private LoyaltyClaroPrice() {}

        @JsonProperty("formattedValue")
        private String formattedValue;

        @JsonProperty("promotionSource")
        private String promotionSource;

        @JsonProperty("value")
        private double value;
    }

    public static class Price {

        private Price() {}

        @JsonProperty("formattedValue")
        private String formattedValue;

        @JsonProperty("value")
        private double value;
    }

    public static class Stock {

        private Stock() {}

        @JsonProperty("stockLevelStatus")
        private String stockLevelStatus;
    }

    public static class VariantMatrix {

        private VariantMatrix() {}

        @JsonProperty("variantOption")
        private VariantOption variantOption;

        @JsonProperty("variantValueCategory")
        private VariantValueCategory variantValueCategory;

        public static class VariantOption {

            private VariantOption() {}

            @JsonProperty("code")
            String code;
        }

        public static class VariantValueCategory {

            private VariantValueCategory() {}

            @JsonProperty("name")
            String name;
        }
    }
}