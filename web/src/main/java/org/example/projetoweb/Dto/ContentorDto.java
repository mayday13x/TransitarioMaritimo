package org.example.projetoweb.Dto;

public class ContentorDto {
    private int cin;
    private double capacidade;
    private double pesoMax;
    private String localAtual;

    public ContentorDto(int cin, double capacidade, double pesoMaximo, String localAtual) {
        this.cin = cin;
        this.capacidade = capacidade;
        this.pesoMax = pesoMaximo;
        this.localAtual = localAtual;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    public double getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(double pesoMaximo) {
        this.pesoMax = pesoMaximo;
    }

    public String getLocalAtual() {
        return localAtual;
    }

    public void setLocalAtual(String localAtual) {
        this.localAtual = localAtual;
    }


}
