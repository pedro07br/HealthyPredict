package com.pedro.HealthyPredict.controller;

import com.pedro.HealthyPredict.model.Alimento;
import com.pedro.HealthyPredict.service.ArvoreBinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ConsultaController {

    private final ArvoreBinariaService arvoreService;

    @Autowired
    public ConsultaController(ArvoreBinariaService arvoreService) {
        this.arvoreService = arvoreService;


        arvoreService.carregarExcel();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/consultar")
    public String consultar(@RequestParam String nome, Model model) {
        Alimento alimento = arvoreService.buscar(nome);
        if (alimento != null) {
            model.addAttribute("alimento", alimento);
        } else {
            model.addAttribute("mensagem", "Alimento não encontrado!");
        }
        return "index";
    }
}
