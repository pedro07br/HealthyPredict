package com.pedro.HealthyPredict;

import com.pedro.HealthyPredict.service.IaService;
import java.io.File;

public class TesteIa {

    public static void main(String[] args) {
        IaService iaService = new IaService();
        File imagem = new File("C:\\Users\\User\\IdeaProjects\\HealthyPredict\\IA_py\\sem_classificacao\\uva/uva_sem_ju (4).jpg");

        String resultado = iaService.enviarImagem(imagem);
        System.out.println("Resultado da IA: " + resultado);
    }
}
