package com.pedro.HealthyPredict.controller;

import com.pedro.HealthyPredict.model.RotinaAlimentar;
import com.pedro.HealthyPredict.model.Usuario;
import com.pedro.HealthyPredict.service.GeminiService;
import com.pedro.HealthyPredict.service.NutricionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/api/rotina")
public class RotinaController {
    @Autowired
    private GeminiService geminiService;

    @Autowired
    private NutricionalService nutricionalService;

    @PostMapping("/gerar")
    public ResponseEntity<RotinaAlimentar> gerarRotina(@RequestBody Usuario usuario) {
        double imc = nutricionalService.calcularIMC(usuario);
        double tmb = nutricionalService.calcularTMB(usuario);

        RotinaAlimentar rotina = geminiService.gerarRotina(usuario, imc, tmb);

        return ResponseEntity.ok(rotina);
    }
}

