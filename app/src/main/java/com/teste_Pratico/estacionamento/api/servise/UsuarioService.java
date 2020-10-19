package com.teste_Pratico.estacionamento.api.servise;

import com.teste_Pratico.estacionamento.app.model.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsuarioService {
    @GET("/usuario/logar?usuario=usuario&senha=senha")
    Call<Usuario> logar(@Query(value = "usuario") String usuario, @Query(value = "senha") String senha);
}
