package com.teste_Pratico.estacionamento.app.model.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class VeiculoEstacionado_DTO implements Parcelable {
    private Long id;
    private String modelo;
    private String placa;

    private String tempo;

    public VeiculoEstacionado_DTO() {}

    protected VeiculoEstacionado_DTO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        modelo = in.readString();
        placa = in.readString();
        tempo = in.readString();
    }

    public static final Creator<VeiculoEstacionado_DTO> CREATOR = new Creator<VeiculoEstacionado_DTO>() {
        @Override
        public VeiculoEstacionado_DTO createFromParcel(Parcel in) {
            return new VeiculoEstacionado_DTO(in);
        }

        @Override
        public VeiculoEstacionado_DTO[] newArray(int size) {
            return new VeiculoEstacionado_DTO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(modelo);
        parcel.writeString(placa);
        parcel.writeString(tempo);
    }
}
