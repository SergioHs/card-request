package com.bank.score.services;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ScoreCalculatorService {

    /**
     * Simula cálculo de score entre 0 e 1000.
     */
    public int calcularScore(String cpf, String nome) {
        // lógica simples/randomizada; você pode refinar usando CPF/nome como seed
        return ThreadLocalRandom.current().nextInt(0, 1001);
    }
}
