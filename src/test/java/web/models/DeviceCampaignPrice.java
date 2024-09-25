package web.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DeviceCampaignPrice {

    @JsonProperty("campaignPrice")
    public CampaignPrice campaignPrice;

    @JsonProperty("campaignPriceWithoutSIM")
    public CampaignPriceWithoutSIM campaignPriceWithoutSIM;

    @JsonProperty("installment")
    public Installment installment;

    @JsonProperty("potentialPromotions")
    public List<PotentialPromotion> potentialPromotions = new ArrayList<>();

    @JsonProperty("price")
    public Price price;

    public static class CampaignPrice {

        @JsonProperty("formattedValue")
        public String formattedValue;

        @JsonProperty("value")
        public double value;
    }

    public static class CampaignPriceWithoutSIM {

        @JsonProperty("formattedValue")
        public String formattedValue;

        @JsonProperty("value")
        public double value;
    }

    public static class Installment {

        @JsonProperty("price")
        public Price price;

        @JsonProperty("quantity")
        public int quantity;
    }

    public static class PotentialPromotion {

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

            @JsonProperty("entry")
            public List<Map<String, String>> entry = new ArrayList<>();
        }
    }

    public static class Price {

        @JsonProperty("formattedValue")
        public String formattedValue;

        @JsonProperty("value")
        public double value;
    }
}