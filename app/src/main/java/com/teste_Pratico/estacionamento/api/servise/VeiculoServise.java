package com.teste_Pratico.estacionamento.api.servise;

import com.teste_Pratico.estacionamento.app.model.DTO.VeiculoEstacionado_DTO;
import com.teste_Pratico.estacionamento.app.model.Movimentacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VeiculoServise {
    @POST("veiculo/cadastrar")
    Call<Movimentacao> salvarVeiculo(@Body Movimentacao movimentacao);

    @GET("veiculo/VeivulosEstacionados")
    Call<List<VeiculoEstacionado_DTO>> getVeiculosEstacionados();

    @GET("veiculo/estacionamentosFinalizados")
    Call<List<Movimentacao>> getVeiculosNaoEstacionados();
}
