package com.bank.score.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ScoreConsumer {

    @KafkaListener(topics = "cartao-topic", groupId = "score-consumer-group")
    public void listen(String mensagem) {
        System.out.println("📥 Mensagem recebida no score-service: " + mensagem);

        int score = (int) (Math.random() * 1000);
        System.out.println("🔎 Score atribuído: " + score);
    }
}