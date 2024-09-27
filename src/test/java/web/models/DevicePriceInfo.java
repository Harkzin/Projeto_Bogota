package web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DevicePriceInfo {

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

    public double getCampaignPrice(boolean withoutSIM) {
        if (withoutSIM) {
            return campaignPriceWithoutSIM.value;
        } else {
            return campaignPrice.value;
        }
    }

    public String getFormattedCampaignPrice(boolean withoutSIM) {
        if (withoutSIM) {
            return campaignPriceWithoutSIM.formattedValue;
        } else {
            return campaignPrice.formattedValue;
        }
    }

    public int getInstallmentQuantity() {
        if (installment != null) {
            return installment.quantity;
        } else {
            return 0;
        }
    }

    public double getInstallmentPrice() {
        return installment.price.value;
    }

    public String getFormattedInstallmentPrice() {
        return  installment.price.formattedValue;
    }

    public double getPrice() {
        return price.value;
    }

    public String getFormattedPrice() {
        return price.formattedValue;
    }

    public static class CampaignPrice {

        private CampaignPrice() {}

        @JsonProperty("formattedValue")
        public String formattedValue;

        @JsonProperty("value")
        public double value;
    }

    public static class CampaignPriceWithoutSIM {

        private CampaignPriceWithoutSIM() {}

        @JsonProperty("formattedValue")
        public String formattedValue;

        @JsonProperty("value")
        public double value;
    }

    public static class Installment {

        private Installment() {}

        @JsonProperty("price")
        public Price price;

        @JsonProperty("quantity")
        public int quantity;
    }

    public static class PotentialPromotion {

        private PotentialPromotion() {}

        @JsonProperty("code")
        public String code;

        @JsonProperty("loyalty")
        public boolean loyalty;

        @JsonProperty("dddList")
        public List<Integer> dddList = new ArrayList<>();

        @JsonProperty("discountValue")
        public int discountValue;

        @JsonProperty("fixedDiscount")
        public boolean fixedDiscount;

        @JsonProperty("invoiceList")
        public List<String> invoiceList = new ArrayList<>();

        @JsonProperty("loyaltyMessage")
        public LoyaltyMessage loyaltyMessage;

        @JsonProperty("paymentMethod")
        public String paymentMethod;

        @JsonProperty("priority")
        public int priority;

        @JsonProperty("processTypeList")
        public List<String> processTypeList = new ArrayList<>();

        public static class LoyaltyMessage {

            private LoyaltyMessage() {}

            @JsonProperty("entry")
            public List<Map<String, String>> entry = new ArrayList<>();
        }
    }

    public static class Price {

        private Price() {}

        @JsonProperty("formattedValue")
        public String formattedValue;

        @JsonProperty("value")
        public double value;
    }
}