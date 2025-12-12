package com.pedro.HealthyPredict.service;

import com.pedro.HealthyPredict.model.RotinaAlimentar;
import com.pedro.HealthyPredict.model.Usuario;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private static final String GEMINI_API_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    private static final String API_KEY = "CHAVE DE API NOVA";

    private final ObjectMapper mapper = new ObjectMapper();

    public RotinaAlimentar gerarRotina(Usuario usuario, double imc, double tmb) {
        String fullUrl = GEMINI_API_BASE_URL + API_KEY;

        String prompt = "Você é um nutricionista profissional. Crie uma rotina alimentar detalhada e personalizada " +
                "para o usuário com os seguintes dados:\n" +
                "Altura: " + usuario.getAltura() + "m\n" +
                "Peso: " + usuario.getPeso() + "kg\n" +
                "Idade: " + usuario.getIdade() + " anos\n" +
                "IMC: " + String.format("%.2f", imc) + "\n" +
                "TMB: " + String.format("%.2f", tmb) + " calorias\n" +
                "Objetivo: " + usuario.getObjetivo() + "\n" +
                "Forneça a resposta **estritamente** no formato JSON especificado.";

        String jsonSchema = """
        {
          "type": "object",
          "properties": {
            "cafe_da_manha": {
              "type": "array",
              "items": { "type": "string" },
              "description": "Lista de sugestões detalhadas para o café da manhã, incluindo porções."
            },
            "almoco": {
              "type": "array",
              "items": { "type": "string" },
              "description": "Lista de sugestões detalhadas para o almoço, incluindo porções."
            },
            "jantar": {
              "type": "array",
              "items": { "type": "string" },
              "description": "Lista de sugestões detalhadas para o jantar, incluindo porções."
            }
          },
          "required": ["cafe_da_manha", "almoco", "jantar"]
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {

            JsonNode responseSchemaNode = mapper.readTree(jsonSchema);

            Map<String, Object> generationConfig = new HashMap<>();
            generationConfig.put("responseMimeType", "application/json");
            generationConfig.put("responseSchema", responseSchemaNode);

            Map<String, String> part = new HashMap<>();
            part.put("text", prompt);

            Map<String, Object> content = new HashMap<>();
            content.put("role", "user");
            content.put("parts", List.of(part));

            Map<String, Object> requestPayload = new HashMap<>();
            requestPayload.put("contents", List.of(content));
            requestPayload.put("generationConfig", generationConfig);

            String requestBody = mapper.writeValueAsString(requestPayload);

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(fullUrl, request, String.class);
            String rawResponse = response.getBody();

            JsonNode root = mapper.readTree(rawResponse);
            JsonNode jsonTextNode = root.path("candidates").path(0).path("content").path("parts").path(0).path("text");

            if (jsonTextNode.isMissingNode()) {
                throw new RuntimeException("Resposta da API inválida ou incompleta.");
            }

            String generatedJsonString = jsonTextNode.asText();

            return mapper.readValue(generatedJsonString, RotinaAlimentar.class);

        } catch (HttpClientErrorException e) {
            System.err.println("--- ERRO HTTP DA API GEMINI ---");
            System.err.println("Corpo do Erro: " + e.getResponseBodyAsString());
            return createErrorRotina("Erro HTTP " + e.getStatusCode().value() + ": " + e.getStatusText());

        } catch (ResourceAccessException e) {
            System.err.println("--- ERRO DE CONEXÃO ---");
            System.err.println("Mensagem: " + e.getMessage());
            return createErrorRotina("Erro de Conexão: Verifique a rede ou URL.");

        } catch (Exception e) {
            System.err.println("--- ERRO GERAL NO PROCESSAMENTO ---");
            System.err.println("Mensagem: " + e.getMessage());
            return createErrorRotina("Erro Geral: Falha ao processar a resposta da API.");
        }
    }

    private RotinaAlimentar createErrorRotina(String error) {
        return new RotinaAlimentar(
                Arrays.asList("Falha: " + error),
                Arrays.asList("Falha: " + error),
                Arrays.asList("Falha: " + error)
        );
    }
}