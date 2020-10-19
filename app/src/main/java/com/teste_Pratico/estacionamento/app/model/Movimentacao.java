package com.teste_Pratico.estacionamento.app.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Movimentacao {

    private Long id;
    private String placa;
    private String modelo;
    private String dataEntrada;
    private String dataSaida;
    private String tempo;
    private Double valor;
    public Integer totalHoras;

    public Movimentacao(){

    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public String getTempo() {
        return tempo;
    }

    public Double getValor() {
        return valor;
    }

    public Integer getTotalHoras() {
        return totalHoras;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setTotalHoras(Integer totalHoras) {
        this.totalHoras = totalHoras;
    }
}
