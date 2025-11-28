package com.pedro.HealthyPredict;

import com.pedro.HealthyPredict.controller.ChatbotController;
import com.pedro.HealthyPredict.model.RotinaAlimentar;
import com.pedro.HealthyPredict.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChatbotIntegrationTest {

    @Autowired
    private ChatbotController chatbotController;

    @Test
    public void testGerarPlanoIntegracao() {
        Usuario usuario = new Usuario();
        usuario.setAltura(1.75);
        usuario.setPeso(70);
        usuario.setIdade(25);
        usuario.setObjetivo("manter");


        RotinaAlimentar rotina = chatbotController.gerarPlano(usuario);

        assertNotNull(rotina, "Rotina não pode ser nula");
        assertNotNull(rotina.getPlano(), "Plano da rotina não pode ser nulo");

        assertTrue(
                rotina.getPlano().length() > 10,
                "Plano da rotina deve conter texto significativo"
        );

        String plano = rotina.getPlano().toLowerCase();
        assertTrue(
                plano.contains("café") || plano.contains("almoço") || plano.contains("jantar"),
                "O plano deve mencionar pelo menos uma refeição típica"
        );
        System.out.println(rotina.getPlano());
    }
}
