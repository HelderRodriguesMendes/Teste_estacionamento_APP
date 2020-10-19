package com.teste_Pratico.estacionamento.app.model.DTO;

public class VeiculoEstacionado_DTO {
    private Long id;
    private String modelo;
    private String placa;

    private String tempo;

    public VeiculoEstacionado_DTO() {}

    public Long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getTempo() {
        return tempo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
