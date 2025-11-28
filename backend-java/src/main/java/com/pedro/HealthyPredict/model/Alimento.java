package com.pedro.HealthyPredict.model;

public class Alimento implements Comparable<Alimento> {
    private String nome;
    private String grupo;
    private String preparoEstado;
    private double calorias;
    private double carboidratos;
    private double proteinas;
    private double gordurasTotais;
    private String classificacao;

    public Alimento(String nome, String grupo, String preparoEstado,
                    double calorias, double carboidratos, double proteinas,
                    double gordurasTotais, String classificacao) {
        this.nome = nome;
        this.grupo = grupo;
        this.preparoEstado = preparoEstado;
        this.calorias = calorias;
        this.carboidratos = carboidratos;
        this.proteinas = proteinas;
        this.gordurasTotais = gordurasTotais;
        this.classificacao = classificacao;
    }

    public String getNome() { return nome; }
    public String getGrupo() { return grupo; }
    public String getPreparoEstado() { return preparoEstado; }
    public double getCalorias() { return calorias; }
    public double getCarboidratos() { return carboidratos; }
    public double getProteinas() { return proteinas; }
    public double getGordurasTotais() { return gordurasTotais; }
    public String getClassificacao() { return classificacao; }

    @Override
    public int compareTo(Alimento o) {
        return this.nome.compareToIgnoreCase(o.getNome());
    }
}
