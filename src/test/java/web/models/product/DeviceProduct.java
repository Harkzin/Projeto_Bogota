package web.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import web.models.DevicePriceInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static web.support.api.RestAPI.*;

public final class DeviceProduct extends Product {

    private DevicePriceInfo devicePriceInfo;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @JsonProperty("variantMatrix")
    private List<VariantMatrix> variantMatrix;

    @JsonProperty("stock")
    private Stock stock;

    private Classification.Feature getDeviceAttribute(String code) {
        return classifications.stream()
                .filter(classification -> classification.code.contains("mobilephoneclassification"))
                .findFirst().orElseThrow()
                .features.stream()
                .filter(feature -> feature.code.contains(code))
                .findFirst().orElseThrow();
    }

    public String getBrand() {
        return getDeviceAttribute(".fabricante").featureValues.get(0).value;
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

    public void setDevicePriceInfo(String campaign, String planCode, String salesOrg) {
        try {
            devicePriceInfo = objMapper.readValue(getDevicePriceInfo(campaign, planCode, code, salesOrg), DevicePriceInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public double getCampaignPrice(boolean withSIM) {
        return devicePriceInfo.getCampaignPrice(withSIM);
    }

    public String getFormattedCampaignPrice(boolean withoutSIM) {
        return devicePriceInfo.getFormattedCampaignPrice(withoutSIM);
    }

    public int getInstallmentQuantity() {
        return devicePriceInfo.getInstallmentQuantity();
    }

    public double getInstallmentPrice() {
        return devicePriceInfo.getInstallmentPrice();
    }

    public String getFormattedInstallmentPrice() {
        return  devicePriceInfo.getFormattedInstallmentPrice();
    }

    @Override
    public double getPrice() {
        return devicePriceInfo.getPrice();
    }

    @Override
    public String getFormattedPrice() {
        return devicePriceInfo.getFormattedPrice();
    }

    public boolean isEsimOnly() {
        return !getDeviceAttribute("features").featureValues.get(0).value.toLowerCase().contains("nanosim");
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