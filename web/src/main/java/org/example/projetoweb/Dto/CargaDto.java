package org.example.projetoweb.Dto;

public class CargaDto {
    private int id;
    private String observacoes;
    private String estado;
    private double peso;
    private double volume;

    // Construtores, getters e setters

    public CargaDto(int id, String observacoes, double peso, double volume, String estado) {
        this.id = id;
        this.observacoes =observacoes;
        this.peso = peso;
        this.volume = volume;
        this.estado = estado;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String descricao) {
        this.observacoes = descricao;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
