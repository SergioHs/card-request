package com.bank.cartao.controller;

import com.bank.cartao.dto.CartaoRequest;
import com.bank.cartao.dto.CartaoResponse;
import com.bank.cartao.service.SolicitacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final SolicitacaoService solicitacaoService;

    public CartaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping
    public CartaoResponse solicitar(@RequestBody CartaoRequest req) throws Exception {
        return solicitacaoService.processarSolicitacao(req);
    }
}
