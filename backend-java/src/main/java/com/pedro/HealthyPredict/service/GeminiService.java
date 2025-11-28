package com.pedro.HealthyPredict.service;

import com.pedro.HealthyPredict.model.RotinaAlimentar;
import com.pedro.HealthyPredict.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class GeminiService {

    private final String GEMINI_API_URL = "https://api.gemini.ai/v1/chat/completions";

    public RotinaAlimentar gerarRotina(Usuario usuario, double imc, double tmb) {
        String apiKey = System.getenv("AIzaSyDOq2k1sMo0rMfSs1kiG5QED-B9nLDulXA");

        if (apiKey == null || apiKey.isEmpty()) {
            return new RotinaAlimentar("""
                Café da manhã: 1 banana e 1 ovo cozido
                Almoço: arroz integral e frango grelhado
                Jantar: salada de folhas e peixe
            """);
        }


        String prompt = "Você é um nutricionista profissional. Crie uma rotina alimentar personalizada para o usuário com os seguintes dados:"
                + "\nAltura: " + usuario.getAltura()
                + "\nPeso: " + usuario.getPeso()
                + "\nIdade: " + usuario.getIdade()
                + "\nIMC: " + String.format("%.2f", imc)
                + "\nTMB: " + String.format("%.2f", tmb)
                + "\nObjetivo: " + usuario.getObjetivo()
                + "\nResponda com um plano alimentar detalhado para 1 dia.";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String body = String.format("""
            {
              "model": "gemini-2.5-flash", 
              "contents": [
                {
                  "role": "user",
                  "parts": [
                    {
                      "text": "%s"
                    }
                  ]
                }
              ]
            }
            """, prompt.replace("\"", "\\\""));

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_API_URL, request, String.class);
            return new RotinaAlimentar(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return new RotinaAlimentar("Erro ao gerar rotina alimentar.");
        }
    }
}
