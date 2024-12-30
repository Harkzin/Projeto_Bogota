
package mock.sap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "ecommerceOrderId",
        "ecommerceEnv",
        "statusDate",
        "statusTime",
        "sapOrderId",
        "salesOrg",
        "distributionChannel",
        "type",
        "typeDescription",
        "sapRequesterClientCode",
        "sapReceiverClientCode",
        "center",
        "status",
        "statusDesc",
        "occurrenceDate",
        "occurrenceTime",
        "invoiceDocument",
        "invoiceNumber",
        "invoiceSeries",
        "item",
        "nfeNumber"
})
public class UpdateOrderSapRequest {

    @JsonProperty("ecommerceOrderId")
    private String ecommerceOrderId;

    @JsonProperty("ecommerceEnv")
    private String ecommerceEnv;

    @JsonProperty("statusDate")
    private String statusDate;

    @JsonProperty("statusTime")
    private String statusTime;

    @JsonProperty("sapOrderId")
    private String sapOrderId;

    @JsonProperty("salesOrg")
    private String salesOrg;

    @JsonProperty("distributionChannel")
    private String distributionChannel;

    @JsonProperty("type")
    private String type;

    @JsonProperty("typeDescription")
    private String typeDescription;

    @JsonProperty("sapRequesterClientCode")
    private String sapRequesterClientCode;

    @JsonProperty("sapReceiverClientCode")
    private String sapReceiverClientCode;

    @JsonProperty("center")
    private String center;

    @JsonProperty("status")
    private String status;

    @JsonProperty("statusDesc")
    private String statusDesc;

    @JsonProperty("occurrenceDate")
    private String occurrenceDate;

    @JsonProperty("occurrenceTime")
    private String occurrenceTime;

    @JsonProperty("invoiceDocument")
    private String invoiceDocument;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("invoiceSeries")
    private String invoiceSeries;

    @JsonProperty("item")
    private Item item = new Item();

    @JsonProperty("nfeNumber")
    private String nfeNumber;

    public void setEcommerceOrderId(String ecommerceOrderId) {
        this.ecommerceOrderId = ecommerceOrderId;
    }

    public void setEcommerceEnv(String ecommerceEnv) {
        this.ecommerceEnv = ecommerceEnv;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public void setSapOrderId(String sapOrderId) {
        this.sapOrderId = sapOrderId;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg;
    }

    public void setDistributionChannel(String distributionChannel) {
        this.distributionChannel = distributionChannel;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public void setSapRequesterClientCode(String sapRequesterClientCode) {
        this.sapRequesterClientCode = sapRequesterClientCode;
    }

    public void setSapReceiverClientCode(String sapReceiverClientCode) {
        this.sapReceiverClientCode = sapReceiverClientCode;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public void setOccurrenceDate(String occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public void setOccurrenceTime(String occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    public void setInvoiceDocument(String invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceSeries(String invoiceSeries) {
        this.invoiceSeries = invoiceSeries;
    }

    public Item getItem() {
        return item;
    }

    public void setNfeNumber(String nfeNumber) {
        this.nfeNumber = nfeNumber;
    }

    @JsonPropertyOrder({
            "seq",
            "iccid",
            "imei"
    })

    public static class Item {

        @JsonProperty("seq")
        private String seq;

        @JsonProperty("iccid")
        private String iccid;

        @JsonProperty("imei")
        private String imei;

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public void setIccid(String iccid) {
            this.iccid = iccid;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }
    }

}