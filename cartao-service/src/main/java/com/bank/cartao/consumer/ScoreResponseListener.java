package com.bank.cartao.consumer;

import com.bank.cartao.dto.ScoreResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class ScoreResponseListener {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ConcurrentMap<String, CompletableFuture<ScoreResponse>> pending = new ConcurrentHashMap<>();

    public void register(String correlationId, CompletableFuture<ScoreResponse> future) {
        pending.put(correlationId, future);
    }

    @KafkaListener(topics = "score-response", groupId = "cartao-service-group")
    public void onMessage(String message) {
        try {
            ScoreResponse resp = mapper.readValue(message, ScoreResponse.class);
            String correlationId = resp.getCorrelationId();
            if (correlationId != null) {
                CompletableFuture<ScoreResponse> future = pending.remove(correlationId);
                if (future != null) {
                    future.complete(resp);
                }
            } else {
                // log: correlationId nulo
            }
        } catch (Exception e) {
            // log erro
            e.printStackTrace();
        }
    }
}