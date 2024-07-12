package org.example.projetoweb.Dto;

public class CargaDto {
    private int id;
    private String descricao;
    private double peso;
    private double volume;

    // Construtores, getters e setters

    public CargaDto(int id, String descricao, double peso, double volume) {
        this.id = id;
        this.descricao = descricao;
        this.peso = peso;
        this.volume = volume;
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
