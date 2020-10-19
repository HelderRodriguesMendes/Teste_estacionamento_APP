package com.teste_Pratico.estacionamento.app.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teste_Pratico.estacionamento.R;
import com.teste_Pratico.estacionamento.app.model.DTO.VeiculoEstacionado_DTO;

import java.util.ArrayList;
import java.util.List;

public class AdapterVeiculosEstacionados extends RecyclerView.Adapter<AdapterVeiculosEstacionados.MyViewHolder>{
    private List<VeiculoEstacionado_DTO> listaveiculos = new ArrayList<>();

    public AdapterVeiculosEstacionados(List<VeiculoEstacionado_DTO> lista){
        listaveiculos = lista;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_veiculos_estacionados, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VeiculoEstacionado_DTO veiculosDTO = listaveiculos.get(position);

        holder.modelo.setText(veiculosDTO.getModelo());
        holder.placa.setText("Placa: " + veiculosDTO.getPlaca());
        holder.horaEntrada.setText(veiculosDTO.getTempo());
        holder.id.setText(String.valueOf(veiculosDTO.getId()));
    }

    @Override
    public int getItemCount() {
        return listaveiculos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView modelo;
        TextView placa;
        TextView horaEntrada;
        TextView id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            modelo  = itemView.findViewById(R.id.textModelo);
            placa = itemView.findViewById(R.id.textPlaca);
            horaEntrada = itemView.findViewById(R.id.textHorarioEntrada);
            id = itemView.findViewById(R.id.textID);
        }
    }
}
