package org.example.projetoweb.Dto;

public class ArmazemDto {
    private int id;
    private String descricao;
    private double capacidadeMax;
    private double capacidadeAtual;

    // Construtores, getters e setters

    public ArmazemDto(int id, String descricao, double capacidadeMax, double capacidadeAtual) {
        this.id = id;
        this.descricao = descricao;
        this.capacidadeMax = capacidadeMax;
        this.capacidadeAtual = capacidadeAtual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getCapacidadeMax() {
        return capacidadeMax;
    }

    public void setCapacidadeMax(double capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    public double getCapacidadeAtual() {
        return capacidadeAtual;
    }

    public void setCapacidadeAtual(double capacidadeAtual) {
        this.capacidadeAtual = capacidadeAtual;
    }
}
