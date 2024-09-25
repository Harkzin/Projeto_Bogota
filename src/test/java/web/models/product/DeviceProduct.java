package web.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class DeviceProduct extends Product {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("variantMatrix")
    private List<VariantMatrix> variantMatrix;

    @JsonProperty("stock")
    private Stock stock;

    private Classification.Feature getDeviceAttributes(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("mobilephoneclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .filter(feature -> feature.code.contains(code))
                .findFirst().orElseThrow();
    }

    public String getFormattedBaseDevicePrice() {
        return "R$ " + String.format(Locale.GERMAN, "%,.2f", (price.value - 10D)); //API retorna o valor com 10 reais a mais. Motivo desconhecido
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

    public static class Stock {

        private Stock() {}

        @JsonProperty("stockLevelStatus")
        String stockLevelStatus;
    }

    public static class VariantMatrix {

        private VariantMatrix() {}

        @JsonProperty("variantOption")
        VariantOption variantOption;

        @JsonProperty("variantValueCategory")
        VariantValueCategory variantValueCategory;

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