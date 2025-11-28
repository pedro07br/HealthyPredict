package com.pedro.HealthyPredict;

import com.pedro.HealthyPredict.model.Alimento;
import com.pedro.HealthyPredict.service.ArvoreBinariaService;

public class MainTesteArvore {

    public static void main(String[] args) {

        ArvoreBinariaService arvore = new ArvoreBinariaService();

        arvore.carregarExcel();

        String busca = "BANANA";
        Alimento resultado = arvore.buscar(busca);

        if (resultado != null) {
            System.out.println("Alimento encontrado: " + resultado.getNome());
            System.out.println("Grupo: " + resultado.getGrupo());
            System.out.println("Preparo/Estado: " + resultado.getPreparoEstado());
            System.out.println("Calorias: " + resultado.getCalorias());
            System.out.println("Carboidratos: " + resultado.getCarboidratos());
            System.out.println("Proteínas: " + resultado.getProteinas());
            System.out.println("Gorduras: " + resultado.getGordurasTotais());
            System.out.println("Classificação: " + resultado.getClassificacao());
        } else {
            System.out.println("Alimento " + busca + " não encontrado!");
        }
    }
}
