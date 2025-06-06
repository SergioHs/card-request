package com.bank.cartao.dto;

import lombok.Data;

@Data
public class CartaoRequest {
    private String cpf;
    private String nome;

    // Getters e setters (ou use Lombok com @Data)
}