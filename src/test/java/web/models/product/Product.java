package web.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

@SuppressWarnings("UnusedDeclaration")
public abstract class Product {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("classifications")
    protected List<Classification> classifications;

    @JsonProperty("price")
    protected Price price;

    @JsonProperty("description")
    protected String description;

    @JsonProperty("summary")
    protected String summary;

    @JsonProperty("code")
    protected String code;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("url")
    protected String url;

    /*private Classification.Feature getPlanAttributes(String code) {
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
    }*/

    /*private List<String> getMediaFeatureValues(String code) {
        return getPlanAttributes(code)
                .featureValues
                .stream()
                .map(featureValue -> featureValue.value.replace("/thumbs/", "")) //Sanity
                .collect(Collectors.toList());
    }*/

    protected boolean hasAttribute(String classification, String code) {
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

    /*public String getFormattedPlanPrice(boolean isDebit, boolean hasLoyalty) {
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
    }*/

    /*public double getPlanPrice(boolean isDebit, boolean isPrintedInvoice) {
        String paymentMode = isDebit && !isPrintedInvoice ? "debitcard" : "ticket";

        return loyaltyClaroPrices.stream().filter(price -> price.promotionSource.equals(paymentMode))
                .findFirst().orElseThrow()
                .value;
    }*/

    /*public String getFormattedBaseDevicePrice() {
        return "R$ " + String.format(Locale.GERMAN, "%,.2f", (price.value - 10D)); //API retorna o valor com 10 reais a mais. Motivo desconhecido
    }*/

    /*public boolean hasPlanApps() {
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
    }*/

    /*public boolean hasManufacturer() {
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
    }*/

    public static class Classification {

        private Classification() {}

        @JsonProperty("code")
        String code;

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        @JsonProperty("features")
        List<Feature> features;

        @JsonProperty("name")
        String name;

        public static class Feature {

            private Feature() {}

            @JsonProperty("code")
            String code;

            @JsonProperty("featureUnit")
            FeatureUnit featureUnit;

            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            @JsonProperty("featureValues")
            List<FeatureValue> featureValues;

            @JsonProperty("name")
            String name;

            public static class FeatureUnit {

                private FeatureUnit() {}

                @JsonProperty("unitType")
                String unitType;
            }

            public static class FeatureValue {

                private FeatureValue() {}

                @JsonProperty("value")
                String value;
            }
        }
    }

    public static class Price {

        private Price() {}

        @JsonProperty("formattedValue")
        String formattedValue;

        @JsonProperty("value")
        double value;
    }
}