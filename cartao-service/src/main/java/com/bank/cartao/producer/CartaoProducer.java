package com.bank.cartao.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartaoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CartaoProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(String mensagem) {
        kafkaTemplate.send("cartao-topic", mensagem);
    }
}