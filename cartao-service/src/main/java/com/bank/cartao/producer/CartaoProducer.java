package com.bank.cartao.producer;

import com.bank.cartao.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartaoProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public CartaoProducer(@Qualifier("customKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviar(String key, Object payload) throws Exception {
        String json = mapper.writeValueAsString(payload);
        kafkaTemplate.send(KafkaConfig.REQUEST_TOPIC, key, json);
    }
}
