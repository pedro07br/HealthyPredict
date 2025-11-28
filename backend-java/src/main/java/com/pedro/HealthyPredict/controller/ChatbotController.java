package com.pedro.HealthyPredict.controller;

import com.pedro.HealthyPredict.model.Usuario;
import com.pedro.HealthyPredict.model.RotinaAlimentar;
import com.pedro.HealthyPredict.service.GeminiService;
import com.pedro.HealthyPredict.service.NutricionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private NutricionalService nutricionalService;

    @PostMapping("/gerar")
    public RotinaAlimentar gerarPlano(@RequestBody Usuario usuario) {
        double imc = nutricionalService.calcularIMC(usuario);
        double tmb = nutricionalService.calcularTMB(usuario);
        return geminiService.gerarRotina(usuario, imc, tmb);
    }
}
