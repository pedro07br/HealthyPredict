package com.pedro.HealthyPredict.controller;

import com.pedro.HealthyPredict.service.IaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/alimento")
@CrossOrigin(origins = "*") // permite que seu HTML local acesse
public class AlimentoController {

    @Autowired
    private IaService iaService;

    @PostMapping("/classificar")
    public String classificarImagem(@RequestParam("file") MultipartFile file) {
        try {

            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            String nomeAlimento = iaService.enviarImagem(tempFile);

            tempFile.delete();

            return nomeAlimento;

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao processar a imagem";
        }
    }
}
