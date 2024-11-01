package massasController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

public class Massas {
    @JsonProperty("massas")
    List<Massa> massasList = new ArrayList<>();

    @JsonPropertyOrder({
            "id",
            "segmento",
            "cpf",
            "msisdn",
            "idPlano",
            "nomePlano",
            "idpromo",
            "promo",
            "multaServico",
            "multaAparelho",
            "valorPlano",
            "formaPagamento",
            "formaEnvio",
            "dependente",
            "crivo",
            "comboMulti",
            "claroClube",
            "status"
    })
    public static class Massa {

        @JsonProperty("id")
        public int id;
        @JsonProperty("segmento")
        public String segmento;
        @JsonProperty("cpf")
        public String cpf;
        @JsonProperty("msisdn")
        public String msisdn;
        @JsonProperty("idPlano")
        public String idPlano;
        @JsonProperty("nomePlano")
        public String nomePlano;
        @JsonProperty("idpromo")
        public String idpromo;
        @JsonProperty("promo")
        public String promo;
        @JsonProperty("multaServico")
        public boolean multaServico;
        @JsonProperty("multaAparelho")
        public boolean multaAparelho;
        @JsonProperty("valorPlano")
        public double valorPlano;
        @JsonProperty("formaPagamento")
        public String formaPagamento;
        @JsonProperty("formaEnvio")
        public String formaEnvio;
        @JsonProperty("dependente")
        public boolean dependente;
        @JsonProperty("crivo")
        public String crivo;
        @JsonProperty("comboMulti")
        public String comboMulti;
        @JsonProperty("claroClube")
        public boolean claroClube;
        @JsonProperty("status")
        public String status;

        @JsonProperty("status")
        public String getStatus() {
            return status;
        }

        @JsonProperty("status")
        public void setStatus(String status) {
            this.status = status;
        }

    }
}