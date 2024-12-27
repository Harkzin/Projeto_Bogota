
package api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class CartNewResponse {

    @JsonProperty("type")
    private String type;
    @JsonProperty("calculated")
    private boolean calculated;
    @JsonProperty("code")
    private String code;
    @JsonProperty("entries")
    private List<Object> entries = new ArrayList<Object>();
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("orderDiscounts")
    private OrderDiscounts orderDiscounts;
    @JsonProperty("productDiscounts")
    private ProductDiscounts productDiscounts;
    @JsonProperty("site")
    private String site;
    @JsonProperty("store")
    private String store;
    @JsonProperty("subTotal")
    private SubTotal subTotal;
    @JsonProperty("totalDiscounts")
    private TotalDiscounts totalDiscounts;
    @JsonProperty("totalItems")
    private int totalItems;
    @JsonProperty("totalPrice")
    private TotalPrice totalPrice;
    @JsonProperty("totalPriceWithTax")
    private TotalPriceWithTax totalPriceWithTax;
    @JsonProperty("totalTax")
    private TotalTax totalTax;
    @JsonProperty("name")
    private String name;
    @JsonProperty("totalUnitCount")
    private int totalUnitCount;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public String getCode() {
        return code;
    }

    public List<Object> getEntries() {
        return entries;
    }

    public String getGuid() {
        return guid;
    }

    public OrderDiscounts getOrderDiscounts() {
        return orderDiscounts;
    }

    public ProductDiscounts getProductDiscounts() {
        return productDiscounts;
    }

    public String getSite() {
        return site;
    }

    public String getStore() {
        return store;
    }

    public SubTotal getSubTotal() {
        return subTotal;
    }

    public TotalDiscounts getTotalDiscounts() {
        return totalDiscounts;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public TotalPrice getTotalPrice() {
        return totalPrice;
    }

    public TotalPriceWithTax getTotalPriceWithTax() {
        return totalPriceWithTax;
    }

    public TotalTax getTotalTax() {
        return totalTax;
    }

    public String getName() {
        return name;
    }

    public int getTotalUnitCount() {
        return totalUnitCount;
    }


    public class OrderDiscounts {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("priceType")
        private String priceType;
        @JsonProperty("value")
        private double value;

        @JsonProperty("currencyIso")
        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPriceType() {
            return priceType;
        }

        public double getValue() {
            return value;
        }

    }

    public class ProductDiscounts {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("priceType")
        private String priceType;
        @JsonProperty("value")
        private double value;

        @JsonProperty("currencyIso")
        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPriceType() {
            return priceType;
        }

        public double getValue() {
            return value;
        }
    }

    public class SubTotal {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("priceType")
        private String priceType;
        @JsonProperty("value")
        private double value;

        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPriceType() {
            return priceType;
        }

        public double getValue() {
            return value;
        }
    }

    public class TotalDiscounts {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("priceType")
        private String priceType;
        @JsonProperty("value")
        private double value;

        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPriceType() {
            return priceType;
        }

        public double getValue() {
            return value;
        }
    }


    public class TotalPrice {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("priceType")
        private String priceType;
        @JsonProperty("value")
        private double value;

        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPriceType() {
            return priceType;
        }

        public double getValue() {
            return value;
        }
    }

    public class TotalPriceWithTax {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("priceType")
        private String priceType;
        @JsonProperty("value")
        private double value;

        @JsonProperty("currencyIso")
        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPriceType() {
            return priceType;
        }

        public double getValue() {
            return value;
        }
    }

    public class TotalTax {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("priceType")
        private String priceType;
        @JsonProperty("value")
        private double value;

        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public String getPriceType() {
            return priceType;
        }

        public double getValue() {
            return value;
        }
    }
}