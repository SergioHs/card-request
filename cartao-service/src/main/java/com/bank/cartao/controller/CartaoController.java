package com.bank.cartao.controller;

import com.bank.cartao.dto.CartaoRequest;
import com.bank.cartao.producer.CartaoProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @PostConstruct
    public void init() {
        System.out.println("### CartaoController carregado!");
    }

    private final CartaoProducer producer;
    private final ObjectMapper mapper = new ObjectMapper();

    public CartaoController(CartaoProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String solicitarCartao(@RequestBody CartaoRequest request) throws Exception {
        String json = mapper.writeValueAsString(request);
        producer.enviar(json);
        return "Solicitação enviada com sucesso";
    }
}
