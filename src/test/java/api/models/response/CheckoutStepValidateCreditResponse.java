package api.models.response;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class CheckoutStepValidateCreditResponse {

    @JsonProperty("type")
    private String type;
    @JsonProperty("cartGUID")
    private String cartGUID;
    @JsonProperty("controleAntecipado")
    private boolean controleAntecipado;
    @JsonProperty("controleFacilCart")
    private boolean controleFacilCart;
    @JsonProperty("errorList")
    private List<Object> errorList = new ArrayList<Object>();
    @JsonProperty("isDDDabr")
    private boolean isDDDabr;
    @JsonProperty("isTokenOnlineForABrFlow")
    private boolean isTokenOnlineForABrFlow;
    @JsonProperty("eligibleQuantityDependent")
    private int eligibleQuantityDependent;
    @JsonProperty("message")
    private String message;
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("cart")
    private Cart cart;
    @JsonProperty("creditApproved")
    private boolean creditApproved;

    public String getType() {
        return type;
    }

    public String getCartGUID() {
        return cartGUID;
    }

    public boolean isControleAntecipado() {
        return controleAntecipado;
    }

    public boolean isControleFacilCart() {
        return controleFacilCart;
    }

    public List<Object> getErrorList() {
        return errorList;
    }

    public boolean isDDDabr() {
        return isDDDabr;
    }

    public boolean isTokenOnlineForABrFlow() {
        return isTokenOnlineForABrFlow;
    }

    public int getEligibleQuantityDependent() {
        return eligibleQuantityDependent;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean isCreditApproved() {
        return creditApproved;
    }

    public static class Cart {

        @JsonProperty("code")
        private String code;
        @JsonProperty("entries")
        private List<Entry> entries = new ArrayList<Entry>();
        @JsonProperty("guid")
        private String guid;
        @JsonProperty("totalItems")
        private int totalItems;
        @JsonProperty("totalPrice")
        private TotalPrice__1 totalPrice;
        @JsonProperty("totalPriceWithTax")
        private TotalPriceWithTax totalPriceWithTax;
        @JsonProperty("checkoutStep")
        private String checkoutStep;
        @JsonProperty("isControleAntecipado")
        private boolean isControleAntecipado;
        @JsonProperty("isControleFacil")
        private boolean isControleFacil;

        public String getCode() {
            return code;
        }

        public List<Entry> getEntries() {
            return entries;
        }

        public String getGuid() {
            return guid;
        }

        public int getTotalItems() {
            return totalItems;
        }

        public TotalPrice__1 getTotalPrice() {
            return totalPrice;
        }

        public TotalPriceWithTax getTotalPriceWithTax() {
            return totalPriceWithTax;
        }

        public String getCheckoutStep() {
            return checkoutStep;
        }

        public boolean isControleAntecipado() {
            return isControleAntecipado;
        }

        public boolean isControleFacil() {
            return isControleFacil;
        }
    }

    public static class Entry {

        @JsonProperty("entryNumber")
        private int entryNumber;
        @JsonProperty("product")
        private Product product;
        @JsonProperty("quantity")
        private int quantity;
        @JsonProperty("totalPrice")
        private TotalPrice totalPrice;

        @JsonProperty("entryNumber")
        public int getEntryNumber() {
            return entryNumber;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public TotalPrice getTotalPrice() {
            return totalPrice;
        }
    }

    public static class Product {

        @JsonProperty("code")
        private String code;
        @JsonProperty("isComponentEditable")
        private boolean isComponentEditable;
        @JsonProperty("isMaxLimitReachedForBundle")
        private boolean isMaxLimitReachedForBundle;
        @JsonProperty("isRemovableEntry")
        private boolean isRemovableEntry;
        @JsonProperty("name")
        private String name;
        @JsonProperty("preselected")
        private boolean preselected;
        @JsonProperty("productSpecDescription")
        private List<String> productSpecDescription = new ArrayList<String>();
        @JsonProperty("url")
        private String url;

        public String getCode() {
            return code;
        }

        public boolean isComponentEditable() {
            return isComponentEditable;
        }

        public boolean isMaxLimitReachedForBundle() {
            return isMaxLimitReachedForBundle;
        }

        public boolean isRemovableEntry() {
            return isRemovableEntry;
        }

        public String getName() {
            return name;
        }

        public boolean isPreselected() {
            return preselected;
        }

        public List<String> getProductSpecDescription() {
            return productSpecDescription;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class TotalPrice {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("value")
        private double value;

        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public double getValue() {
            return value;
        }
    }

    public static class TotalPriceWithTax {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("value")
        private double value;

        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public double getValue() {
            return value;
        }
    }

    public static class TotalPrice__1 {

        @JsonProperty("currencyIso")
        private String currencyIso;
        @JsonProperty("formattedValue")
        private String formattedValue;
        @JsonProperty("value")
        private double value;

        public String getCurrencyIso() {
            return currencyIso;
        }

        public String getFormattedValue() {
            return formattedValue;
        }

        public double getValue() {
            return value;
        }
    }
}
