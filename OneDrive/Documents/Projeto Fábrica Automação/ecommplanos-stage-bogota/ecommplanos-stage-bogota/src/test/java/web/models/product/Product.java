package web.models.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public abstract class Product {

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

    @JsonProperty("categories")
    protected List<Category> categories;

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

    public String getFormattedPrice() {
        return price.formattedValue;
    }

    public List<Category> getCategories() {
        return categories;
    }

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

    public static final class Category {

        private Category() {}

        @JsonProperty("code")
        private String code;

        public String getCode() {
            return code;
        }
    }
}