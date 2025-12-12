package com.pedro.HealthyPredict.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RotinaAlimentar {

    @JsonProperty("cafe_da_manha")
    private List<String> cafeDaManha;

    @JsonProperty("almoco")
    private List<String> almoco;

    @JsonProperty("jantar")
    private List<String> jantar;

    // Construtor padrão (necessário para o Jackson)
    public RotinaAlimentar() {
    }

    // Construtor completo
    public RotinaAlimentar(List<String> cafeDaManha, List<String> almoco, List<String> jantar) {
        this.cafeDaManha = cafeDaManha;
        this.almoco = almoco;
        this.jantar = jantar;
    }

    // Getters e Setters
    public List<String> getCafeDaManha() { return cafeDaManha; }
    public List<String> getAlmoco() { return almoco; }
    public List<String> getJantar() { return jantar; }

    public void setCafeDaManha(List<String> cafeDaManha) { this.cafeDaManha = cafeDaManha; }
    public void setAlmoco(List<String> almoco) { this.almoco = almoco; }
    public void setJantar(List<String> jantar) { this.jantar = jantar; }
}