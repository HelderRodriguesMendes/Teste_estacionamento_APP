package com.teste_Pratico.estacionamento.app.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.teste_Pratico.estacionamento.R;
import com.teste_Pratico.estacionamento.api.retrofit.Retrofit_URL;
import com.teste_Pratico.estacionamento.api.servise.VeiculoServise;
import com.teste_Pratico.estacionamento.app.model.DTO.VeiculoEstacionado_DTO;
import com.teste_Pratico.estacionamento.app.model.Movimentacao;
import com.teste_Pratico.estacionamento.app.service.Utilitarios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Finalizar_EstacionamentoActivity extends AppCompatActivity {

    TextView txtmodelo, txtplaca, txtentrada, txtsaida, txttempo, txtvalor;
    Button btnOK;

    VeiculoEstacionado_DTO veiculoFinalizar = new VeiculoEstacionado_DTO();
    Movimentacao veiculoFinalizado = new Movimentacao();
    Utilitarios utilitarios = new Utilitarios();
    Retrofit_URL retrofit = new Retrofit_URL();

    String DATA_ATUAL;
    String HORA_ATUAL;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar__estacionamento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtmodelo = findViewById(R.id.txt_modelo);
        txtplaca = findViewById(R.id.txt_placa);
        txtentrada = findViewById(R.id.txt_entrada);
        txtsaida = findViewById(R.id.txt_saida);
        txttempo = findViewById(R.id.txt_totalH);
        txtvalor = findViewById(R.id.txt_valor);
        btnOK = findViewById(R.id.btnOk);

        veiculoFinalizar = getIntent().getExtras().getParcelable("veiculo");
        DATA_ATUAL = utilitarios.getDataAtual();
        HORA_ATUAL = utilitarios.getHoraAtual();
        finalizar(veiculoFinalizar);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Finalizar_EstacionamentoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void finalizar(VeiculoEstacionado_DTO veiculo){
        veiculoFinalizado.setId(veiculo.getId());
        veiculoFinalizado.setDataSaida(DATA_ATUAL);
        veiculoFinalizado.setTempo(HORA_ATUAL);

        VeiculoServise compraService = retrofit.URLBase().create(VeiculoServise.class);
        Call<Movimentacao> call = compraService.finalizar(veiculoFinalizado.getId(), veiculoFinalizado);

        call.enqueue(new Callback<Movimentacao>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Movimentacao> call, Response<Movimentacao> response) {
                if(response.isSuccessful()){
                    veiculoFinalizado = new Movimentacao();
                    veiculoFinalizado = response.body();
                    exibirDados();
                    String titulo = "Finalizar Estacionamento", msg = veiculoFinalizado.getModelo() + "Finalizado com Sucesso";
                    msgSucesso(titulo, msg);

                }
            }

            @Override
            public void onFailure(Call<Movimentacao> call, Throwable t) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void exibirDados(){
        confgDatas();
        txtmodelo.setText(veiculoFinalizado.getModelo());
        txtplaca.setText(veiculoFinalizado.getPlaca());
        txtentrada.setText(veiculoFinalizado.getDataEntrada());
        txtsaida.setText(veiculoFinalizado.getDataSaida());
        txttempo.setText(veiculoFinalizado.getTotalHoras() + " Horas");
        txtvalor.setText("R$ " + String.valueOf(veiculoFinalizado.getValor()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void confgDatas(){
        LocalDate dE = LocalDate.parse(veiculoFinalizado.getDataEntrada());
        LocalDate dS = LocalDate.parse(veiculoFinalizado.getDataSaida());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatadaE = dE.format(formatter);
        String dataFormatadaS = dS.format(formatter);
        veiculoFinalizado.setDataEntrada(dataFormatadaE);
        veiculoFinalizado.setDataSaida(dataFormatadaS);
    }

    public void msgSucesso(String titulo, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(Finalizar_EstacionamentoActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Finalizar_EstacionamentoActivity.this).inflate(
                R.layout.alert_sucesso,(ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.txtTitle)).setText(titulo);
        ((TextView) view.findViewById(R.id.txtMessage)).setText(msg);
        ((Button) view.findViewById(R.id.btnAction)).setText(getResources().getString(R.string.btnOK));
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_success);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btnAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

}