package com.pedro.HealthyPredict.service;

import com.pedro.HealthyPredict.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class NutricionalService {

    public double calcularIMC(Usuario usuario) {
        return usuario.getPeso() / (usuario.getAltura() * usuario.getAltura());
    }

    public double calcularTMB(Usuario usuario) {
        double alturaCm = usuario.getAltura() * 100;

        return (10 * usuario.getPeso()) + (6.25 * alturaCm) - (5 * usuario.getIdade()) + 5;
    }
}