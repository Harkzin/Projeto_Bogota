package support;

import java.util.List;

public final class Product {

    public List<Classification> classifications;
    public List<ClaroPaymentModePrice> claroPaymentModePrices;
    public List<LoyaltyClaroPrice> loyaltyClaroPrices;
    public Price price;
    public String description;
    public String summary;
    public String code;
    public String name;
    public String url;

    public static class Classification {

        public String code;
        public List<Feature> features;
        public String name;
    }

    public static class ClaroPaymentModePrice {
        public String currencyIso;
        public String formattedValue;
        public boolean loyalty;
        public String promotionSource;
        public double value;
    }

    public static class Feature {

        public String code;
        public List<FeatureValue> featureValues;
        public String name;
    }

    public static class FeatureValue {

        public String value;
    }

    public static class LoyaltyClaroPrice {

        public String formattedValue;
        public boolean loyalty;
        public String promotionSource;
        public double value;
    }

    public static class Price {

        public String formattedValue;
        public double value;
    }
}