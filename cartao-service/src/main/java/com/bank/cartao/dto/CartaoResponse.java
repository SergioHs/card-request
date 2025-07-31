package com.bank.cartao.dto;

public class CartaoResponse {
    private String cpf;
    private String nome;
    private String status;
    private String bandeira; // null se negado
    private Integer score;

    public CartaoResponse(String cpf, String nome, String status, String bandeira, Integer score) {
        this.cpf = cpf;
        this.nome = nome;
        this.status = status;
        this.bandeira = bandeira;
        this.score = score;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public Integer getScore() {
        return score;
    }
}