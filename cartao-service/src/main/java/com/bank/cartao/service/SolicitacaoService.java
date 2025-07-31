package com.bank.cartao.service;

import com.bank.cartao.consumer.ScoreResponseListener;
import com.bank.cartao.dto.*;
import com.bank.cartao.producer.CartaoProducer;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;

@Service
public class SolicitacaoService {

    private final CartaoProducer producer;
    private final ScoreResponseListener responseListener;
    private final BandeiraService bandeiraService;

    public SolicitacaoService(CartaoProducer producer,
                              ScoreResponseListener responseListener,
                              BandeiraService bandeiraService) {
        this.producer = producer;
        this.responseListener = responseListener;
        this.bandeiraService = bandeiraService;
    }

    public CartaoResponse processarSolicitacao(CartaoRequest req) throws Exception {
        String correlationId = UUID.randomUUID().toString();
        ScoreRequest scoreRequest = new ScoreRequest();
        scoreRequest.setCpf(req.getCpf());
        scoreRequest.setNome(req.getNome());
        scoreRequest.setCorrelationId(correlationId);

        CompletableFuture<ScoreResponse> future = new CompletableFuture<>();
        responseListener.register(correlationId, future);

        producer.enviar(req.getCpf(), scoreRequest);

        // espera resposta com timeout
        ScoreResponse scoreResp;
        try {
            scoreResp = future.get(5, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return new CartaoResponse(req.getCpf(), req.getNome(), "negado", null, 0);
        }

        if (scoreResp.getScore() < 400) {
            return new CartaoResponse(req.getCpf(), req.getNome(), "negado", null, scoreResp.getScore());
        } else {
            String bandeira = bandeiraService.determinarBandeira(scoreResp.getScore());
            return new CartaoResponse(req.getCpf(), req.getNome(), "aprovado", bandeira, scoreResp.getScore());
        }

    }
}
