
package mock.ativacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "status",
        "statusDescription",
        "operation",
        "customer",
        "thab",
        "devices"
})
public class UpdateOrderRequest {

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("statusDescription")
    private String statusDescription;

    @JsonProperty("operation")
    private Operation operation = new Operation();

    @JsonProperty("customer")
    private Customer customer = new Customer();

    @JsonProperty("thab")
    private Thab thab = new Thab();

    @JsonProperty("devices")
    private Devices devices = new Devices();

    @JsonProperty("simcard")
    private String simcard;

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Operation getOperation() {
        return operation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Thab getThab() {
        return thab;
    }

    public Devices getDevices() {
        return devices;
    }

    public void setSimcard(String simcard) {
        this.simcard = simcard;
    }

    @JsonPropertyOrder({
            "type",
            "description",
            "lineSubtype",
            "residential"
    })

    public static class Operation {

        @JsonProperty("type")
        private String type;

        @JsonProperty("description")
        private String description;

        @JsonProperty("lineSubtype")
        private String lineSubtype;

        @JsonProperty("residential")
        private String residential;

        public void setType(String type) {
            this.type = type;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLineSubtype(String lineSubtype) {
            this.lineSubtype = lineSubtype;
        }

        public void setResidential(String residential) {
            this.residential = residential;
        }
    }

    @JsonPropertyOrder({
            "id",
            "mobileBan"
    })

    public static class Customer {

        @JsonProperty("id")
        private String id;

        @JsonProperty("mobileBan")
        private String mobileBan;

        public void setId(String id) {
            this.id = id;
        }

        public void setMobileBan(String mobileBan) {
            this.mobileBan = mobileBan;
        }
    }

    @JsonPropertyOrder({
            "licenseFee",
            "dueDate"
    })

    public static class Thab {

        @JsonProperty("licenseFee")
        private String licenseFee;

        @JsonProperty("dueDate")
        private String dueDate;

        public void setLicenseFee(String licenseFee) {
            this.licenseFee = licenseFee;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

    }

    @JsonPropertyOrder({
            "telephoneNumber",
            "rowType",
            "portability",
            "mobileSubscriberId",
            "sapOrders",
            "simcard"
    })

    public static class Devices {

        @JsonProperty("telephoneNumber")
        private String telephoneNumber;

        @JsonProperty("rowType")
        private String rowType;

        @JsonProperty("portability")
        private String portability;

        @JsonProperty("mobileSubscriberId")
        private String mobileSubscriberId;

        @JsonProperty("sapOrders")
        private SapOrders sapOrders = new SapOrders();

        @JsonProperty("simcard")
        private String simcard;

        public void setTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
        }

        public void setRowType(String rowType) {
            this.rowType = rowType;
        }

        public void setPortability(String portability) {
            this.portability = portability;
        }

        public void setMobileSubscriberId(String mobileSubscriberId) {
            this.mobileSubscriberId = mobileSubscriberId;
        }

        public SapOrders getSapOrders() {
            return sapOrders;
        }

        public void setSimcard(String simcard) {
            this.simcard = simcard;
        }
    }

    @JsonPropertyOrder({
            "orderNumber",
            "type",
            "status",
            "iccid",
            "imei"
    })

    public static class SapOrders {

        @JsonProperty("orderNumber")
        private String orderNumber;

        @JsonProperty("type")
        private String type;

        @JsonProperty("status")
        private String status;

        @JsonProperty("iccid")
        private String iccid;

        @JsonProperty("imei")
        private String imei;

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setIccid(String iccid) {
            this.iccid = iccid;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }
    }

}