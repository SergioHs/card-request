package com.bank.score.producer;

import com.bank.score.config.KafkaConfig;
import com.bank.score.dto.ScoreResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScoreResponseProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public ScoreResponseProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarResposta(String key, ScoreResponse response) {
        try {
            String json = mapper.writeValueAsString(response);
            kafkaTemplate.send(KafkaConfig.RESPONSE_TOPIC, key, json);
        } catch (Exception e) {
            throw new RuntimeException("Erro serializando ScoreResponse", e);
        }
    }
}
