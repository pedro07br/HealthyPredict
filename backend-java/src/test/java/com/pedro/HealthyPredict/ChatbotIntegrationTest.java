package com.pedro.HealthyPredict;

import com.pedro.HealthyPredict.model.RotinaAlimentar;
import com.pedro.HealthyPredict.model.Usuario;
import com.pedro.HealthyPredict.service.GeminiService;
import com.pedro.HealthyPredict.service.NutricionalService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatbotIntegrationTest {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private NutricionalService nutricionalService;

    @Test
    public void testeGerarRotina() {
        Usuario usuario = new Usuario();
        usuario.setIdade(25);
        usuario.setAltura(1.75);
        usuario.setPeso(70);
        usuario.setObjetivo("Manter peso");

        double imc = nutricionalService.calcularIMC(usuario);
        double tmb = nutricionalService.calcularTMB(usuario);

        RotinaAlimentar rotina = geminiService.gerarRotina(usuario, imc, tmb);

        assertNotNull(rotina);
        assertNotNull(rotina.getCafeDaManha());
        assertNotNull(rotina.getAlmoco());
        assertNotNull(rotina.getJantar());
    }
}
