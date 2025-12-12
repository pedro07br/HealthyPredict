package com.pedro.HealthyPredict;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HealthyPredictApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthyPredictApplication.class, args);
        System.out.println("🚀 HealthyPredict está rodando em http://localhost:8080");
    }

    // Bean para executar código ao iniciar a aplicação
    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("✅ Backend iniciado com sucesso!");
            System.out.println("Use a API POST http://localhost:8080/api/rotina/gerar");
        };
    }
}
