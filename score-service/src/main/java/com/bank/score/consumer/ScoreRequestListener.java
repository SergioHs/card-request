package com.bank.score.consumer;

import com.bank.score.dto.ScoreRequest;
import com.bank.score.dto.ScoreResponse;
import com.bank.score.services.ScoreCalculatorService;
import com.bank.score.producer.ScoreResponseProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ScoreRequestListener {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ScoreCalculatorService calculator;
    private final ScoreResponseProducer producer;

    public ScoreRequestListener(ScoreCalculatorService calculator, ScoreResponseProducer producer) {
        this.calculator = calculator;
        this.producer = producer;
    }

    @KafkaListener(topics = "cartao-topic", groupId = "score-service-group")
    public void onMessage(String message) {
        try {
            ScoreRequest req = mapper.readValue(message, ScoreRequest.class);
            int score = calculator.calcularScore(req.getCpf(), req.getNome());

            ScoreResponse resp = new ScoreResponse();
            resp.setCpf(req.getCpf());
            resp.setCorrelationId(req.getCorrelationId());
            resp.setScore(score);

            producer.enviarResposta(req.getCpf(), resp);
        } catch (Exception e) {
            // log e continue (pode adicionar logger)
            e.printStackTrace();
        }
    }
}
