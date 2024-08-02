package api.models.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


public class CheckoutStepIdentifyCustomerOfferResponse {

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

    public boolean isIsDDDabr() {
        return isDDDabr;
    }

    public boolean isIsTokenOnlineForABrFlow() {
        return isTokenOnlineForABrFlow;
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


    @JsonPropertyOrder({
            "code",
            "entries",
            "guid",
            "totalItems",
            "totalPrice",
            "totalPriceWithTax",
            "checkoutStep",
            "isControleAntecipado",
            "isControleFacil"
    })
    public class Cart {

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

        public boolean isIsControleAntecipado() {
            return isControleAntecipado;
        }

        public boolean isIsControleFacil() {
            return isControleFacil;
        }
    }

    @JsonPropertyOrder({
            "entryNumber",
            "product",
            "quantity",
            "totalPrice"
    })
    public class Entry {

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

    @JsonPropertyOrder({
            "code",
            "isComponentEditable",
            "isMaxLimitReachedForBundle",
            "isRemovableEntry",
            "name",
            "preselected",
            "productSpecDescription",
            "url"
    })
    public class Product {

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

        public boolean isIsComponentEditable() {
            return isComponentEditable;
        }

        public boolean isIsMaxLimitReachedForBundle() {
            return isMaxLimitReachedForBundle;
        }

        public boolean isIsRemovableEntry() {
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

    @JsonPropertyOrder({
            "currencyIso",
            "formattedValue",
            "value"
    })
    public class TotalPrice {

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

    @JsonPropertyOrder({
            "currencyIso",
            "formattedValue",
            "value"
    })
    public class TotalPriceWithTax {

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

    @JsonPropertyOrder({
            "currencyIso",
            "formattedValue",
            "value"
    })
    public class TotalPrice__1 {

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
