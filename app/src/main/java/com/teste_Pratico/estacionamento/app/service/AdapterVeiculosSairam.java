package com.teste_Pratico.estacionamento.app.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teste_Pratico.estacionamento.R;
import com.teste_Pratico.estacionamento.app.model.Movimentacao;

import java.util.ArrayList;
import java.util.List;

public class AdapterVeiculosSairam extends RecyclerView.Adapter<AdapterVeiculosSairam.MyViewHolder>{

    private List<Movimentacao> listaveiculos = new ArrayList<>();

    public AdapterVeiculosSairam(List<Movimentacao> lista){
        listaveiculos = lista;
    }


    @NonNull
    @Override
    public AdapterVeiculosSairam.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_veiculos_sairam, parent, false);

        return new AdapterVeiculosSairam.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVeiculosSairam.MyViewHolder holder, int position) {
        Movimentacao veiculos = listaveiculos.get(position);

        holder.modelo.setText(veiculos.getModelo());
        holder.placa.setText("Placa: " + veiculos.getPlaca());
        holder.dataEntrada.setText("Entrou: " + veiculos.getDataEntrada());
        holder.dataSaida.setText("Saiu: " + veiculos.getDataSaida());
        holder.totalHoras.setText("Quantidade de horas: " + veiculos.getTotalHoras());
        holder.valor.setText("Valor: R$ " + veiculos.getValor());
    }

    @Override
    public int getItemCount() {
        return listaveiculos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView modelo;
        TextView placa;
        TextView dataEntrada;
        TextView dataSaida;
        TextView totalHoras;
        TextView valor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            modelo  = itemView.findViewById(R.id.textViewModelo);
            placa = itemView.findViewById(R.id.textViewPlaca);
            dataEntrada = itemView.findViewById(R.id.textViewDataEntrada);
            dataSaida = itemView.findViewById(R.id.textViewDataSaida);
            totalHoras = itemView.findViewById(R.id.textViewTotalHoras);
            valor = itemView.findViewById(R.id.textViewValor);
        }
    }
}
