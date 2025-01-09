
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
        "devices",
        "suborders",
        "crmprotocol"
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

    @JsonProperty("subOrders")
    private SubOrders subOrders = new SubOrders();
    @JsonProperty("crmprotocol")
    private String crmprotocol;
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

    public SubOrders getSubOrders() {
        return subOrders;
    }

    public void setSubOrdersNull() {
        subOrders = null;
    }

    public void setCrmProtocol(String crmprotocol) {
        this.crmprotocol = crmprotocol;
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
        private Portability portability = new Portability();

        @JsonProperty("mobileSubscriberId")
        private String mobileSubscriberId;

        @JsonProperty("sapOrders")
        private SapOrders sapOrders = new SapOrders();

        @JsonProperty("simcard")
        private SimCard simCard = new SimCard();

        public void setTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
        }

        public void setRowType(String rowType) {
            this.rowType = rowType;
        }

        public Portability getPortability() {
            return portability;
        }

        public void setPortabilityNull() {
            portability = null;
        }

        public void setMobileSubscriberId(String mobileSubscriberId) {
            this.mobileSubscriberId = mobileSubscriberId;
        }

        public SapOrders getSapOrders() {
            return sapOrders;
        }

        public SimCard getSimCard() {
            return simCard;
        }

        @JsonPropertyOrder({
                "eaTicket",
                "claroTicket",
                "eaWindowDate",
                "provisionalTelephoneNumber",
                "portabilityStatus",
                "reason",
                "spn"
        })

        public static class Portability {

            @JsonProperty("eaTicket")
            private String eaTicket;
            @JsonProperty("claroTicket")
            private String claroTicket;
            @JsonProperty("eaWindowDate")
            private String eaWindowDate;
            @JsonProperty("provisionalTelephoneNumber")
            private String provisionalTelephoneNumber;
            @JsonProperty("portabilityStatus")
            private String portabilityStatus;
            @JsonProperty("reason")
            private String reason;
            @JsonProperty("spn")
            private Spn spn = new Spn();

            public void setEaTicket(String eaTicket) {
                this.eaTicket = eaTicket;
            }

            public void setClaroTicket(String claroTicket) {
                this.claroTicket = claroTicket;
            }

            public void setEaWindowDate(String eaWindowDate) {
                this.eaWindowDate = eaWindowDate;
            }

            public void setProvisionalTelephoneNumber(String provisionalTelephoneNumber) {
                this.provisionalTelephoneNumber = provisionalTelephoneNumber;
            }

            public void setPortabilityStatus(String portabilityStatus) {
                this.portabilityStatus = portabilityStatus;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public Spn getSpn() {
                return spn;
            }

            @JsonPropertyOrder({
                    "eventId",
                    "description"
            })

            public static class Spn {
                @JsonProperty("eventId")
                private String eventId;

                @JsonProperty("description")
                private String description;

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setEventId(String eventId) {
                    this.eventId = eventId;
                }
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

        @JsonPropertyOrder({
                "type",
                "iccid",
                "technologyGeneration",
                "activationCode",
                "qrCodeImage"
        })

        public static class SimCard {

            @JsonProperty("type")
            private String type;
            @JsonProperty("iccid")
            private String iccid;
            @JsonProperty("technologyGeneration")
            private String technologyGeneration;
            @JsonProperty("activationCode")
            private String activationCode;
            @JsonProperty("qrCodeImage")
            private String qrCodeImage;

            public void setType(String type) {
                this.type = type;
            }

            public void setIccid(String iccid) {
                this.iccid = iccid;
            }

            public void setTechnologyGeneration(String technologyGeneration) {
                this.technologyGeneration = technologyGeneration;
            }

            public void setActivationCode(String activationCode) {
                this.activationCode = activationCode;
            }

            public void setQrCodeImage(String qrCodeImage) {
                this.qrCodeImage = qrCodeImage;
            }

        }
    }
    @JsonPropertyOrder({
            "transaction",
            "type",
            "status",
            "protocol",
            "statusDescription"
    })

    public static class SubOrders {

        @JsonProperty("transaction")
        private String transaction;
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

        @JsonProperty("dateTime")
        private String dateTime;
        @JsonProperty("protocol")
        private String protocol;
        @JsonProperty("statusDescription")
        private String statusDescription;

        public void setTransaction(String transaction) {
            this.transaction = transaction;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public void setStatusDescription(String statusDescription) {
            this.statusDescription = statusDescription;
        }
    }
}