package web.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static web.support.api.RestAPI.*;
import static web.support.utils.Constants.*;

public final class DeviceProduct extends Product {

    private DevicePriceInfo devicePriceInfo;

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

    public double getCampaignPrice(boolean withoutSIM) {
        if (withoutSIM) {
            return devicePriceInfo.campaignPriceWithoutSIM.value;
        } else {
            return devicePriceInfo.campaignPrice.value;
        }
    }

    public String getFormattedCampaignPrice(boolean withoutSIM) {
        if (withoutSIM) {
            return devicePriceInfo.campaignPriceWithoutSIM.formattedValue;
        } else {
            return devicePriceInfo.campaignPrice.formattedValue;
        }
    }

    public int getInstallmentQuantity() {
        if (devicePriceInfo.installment != null) {
            return devicePriceInfo.installment.quantity;
        } else {
            return 0;
        }
    }

    public double getInstallmentPrice() {
        return devicePriceInfo.installment.price.value;
    }

    public String getFormattedInstallmentPrice() {
        return  devicePriceInfo.installment.price.formattedValue;
    }

    @Override
    public double getPrice() {
        return devicePriceInfo.price.value;
    }

    @Override
    public String getFormattedPrice() {
        return devicePriceInfo.price.formattedValue;
    }

    public String getSimType() {
        return getDeviceAttribute("features").featureValues.get(0).value;
    }

    public double getPlanPromoDiscount(ProcessType processType, PaymentMode paymentMode, InvoiceType invoiceType, boolean hasLoyalty, int ddd) {
        return devicePriceInfo.potentialPromotions.stream()
                .filter(p -> p.processTypeList.contains(processType) && p.paymentMethod == paymentMode)
                .filter(p -> paymentMode == PaymentMode.TICKET || !p.invoiceList.contains(invoiceType))
                .filter(p -> p.loyalty == hasLoyalty && p.dddList.contains(ddd))
                .findFirst().orElseThrow()
                .discountValue;
    }

    public static final class Stock {

        private Stock() {}

        @JsonProperty("stockLevelStatus")
        String stockLevelStatus;
    }

    public static final class VariantMatrix {

        private VariantMatrix() {}

        @JsonProperty("variantOption")
        VariantOption variantOption;

        @JsonProperty("variantValueCategory")
        VariantValueCategory variantValueCategory;

        public static final class VariantOption {

            private VariantOption() {}

            @JsonProperty("code")
            String code;
        }

        public static final class VariantValueCategory {

            private VariantValueCategory() {}

            @JsonProperty("name")
            String name;
        }
    }

    public static final class DevicePriceInfo {

        @JsonProperty("campaignPrice")
        private CampaignPrice campaignPrice;

        @JsonProperty("campaignPriceWithoutSIM")
        private CampaignPriceWithoutSIM campaignPriceWithoutSIM;

        @JsonProperty("installment")
        private Installment installment;

        @JsonProperty("potentialPromotions")
        private final List<PotentialPromotion> potentialPromotions = new ArrayList<>();

        @JsonProperty("price")
        private Price price;

        public static final class CampaignPrice {

            private CampaignPrice() {}

            @JsonProperty("formattedValue")
            public String formattedValue;

            @JsonProperty("value")
            public double value;
        }

        public static final class CampaignPriceWithoutSIM {

            private CampaignPriceWithoutSIM() {}

            @JsonProperty("formattedValue")
            public String formattedValue;

            @JsonProperty("value")
            public double value;
        }

        public static final class Installment {

            private Installment() {}

            @JsonProperty("price")
            public Price price;

            @JsonProperty("quantity")
            public int quantity;
        }

        public static final class PotentialPromotion {

            private PotentialPromotion() {}

            @JsonProperty("code")
            public String code;

            @JsonProperty("loyalty")
            public boolean loyalty;

            @JsonProperty("dddList")
            public List<Integer> dddList = new ArrayList<>();

            @JsonProperty("discountValue")
            public double discountValue;

            @JsonProperty("fixedDiscount")
            public boolean fixedDiscount;

            @JsonProperty("invoiceList")
            public List<InvoiceType> invoiceList = new ArrayList<>();

            @JsonProperty("loyaltyMessage")
            public PotentialPromotion.LoyaltyMessage loyaltyMessage;

            @JsonProperty("paymentMethod")
            public PaymentMode paymentMethod;

            @JsonProperty("priority")
            public int priority;

            @JsonProperty("processTypeList")
            public List<ProcessType> processTypeList = new ArrayList<>();

            public static final class LoyaltyMessage {

                private LoyaltyMessage() {}

                @JsonProperty("entry")
                public List<Map<String, String>> entry = new ArrayList<>();
            }
        }

        public static final class Price {

            private Price() {}

            @JsonProperty("formattedValue")
            public String formattedValue;

            @JsonProperty("value")
            public double value;
        }
    }
}