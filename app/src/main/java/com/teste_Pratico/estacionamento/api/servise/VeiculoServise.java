package com.teste_Pratico.estacionamento.api.servise;

import com.teste_Pratico.estacionamento.app.model.DTO.VeiculoEstacionado_DTO;
import com.teste_Pratico.estacionamento.app.model.Movimentacao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VeiculoServise {
    @POST("veiculo/cadastrar")
    Call<Movimentacao> salvarVeiculo(@Body Movimentacao movimentacao);

    @GET("veiculo/VeivulosEstacionados")
    Call<List<VeiculoEstacionado_DTO>> getVeiculosEstacionados();

    @GET("veiculo/estacionamentosFinalizados")
    Call<List<Movimentacao>> getVeiculosNaoEstacionados();

    @GET("veiculo/VeivulosEstacionadosPlaca?")
    Call<List<VeiculoEstacionado_DTO>> getVeiculosEstacionados_placa(@Query("placa") String placa);

    @GET("veiculo/Veivulos_N_EstacionadosPlaca?")
    Call<List<VeiculoEstacionado_DTO>> getVeiculos_N_Estacionados_placa(@Query("placa") String placa);

    @GET("veiculo/VeivulosEstacionadosModelo?")
    Call<List<VeiculoEstacionado_DTO>> getVeiculosEstacionados_Modelo(@Query("modelo") String modelo);

    @GET("veiculo/Veivulos_N_EstacionadosModelo?")
    Call<List<VeiculoEstacionado_DTO>> getVeiculos_N_Estacionados_Modelo(@Query("modelo") String modelo);

    @PUT("veiculo/alterarVeiculoEstacionado/{id}")
    Call<List<VeiculoEstacionado_DTO>> alterar(@Path("id") Long id, @Body VeiculoEstacionado_DTO veiculo);

    @PUT("veiculo/finalizarEstacionamento/{id}")
    Call<Movimentacao> finalizar(@Path("id") Long id, @Body Movimentacao veiculo);
}
