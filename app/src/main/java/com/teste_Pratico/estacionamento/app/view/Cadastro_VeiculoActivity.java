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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.teste_Pratico.estacionamento.R;
import com.teste_Pratico.estacionamento.api.retrofit.Retrofit_URL;
import com.teste_Pratico.estacionamento.api.servise.VeiculoServise;
import com.teste_Pratico.estacionamento.app.model.DTO.VeiculoEstacionado_DTO;
import com.teste_Pratico.estacionamento.app.model.Movimentacao;
import com.teste_Pratico.estacionamento.app.service.Utilitarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Cadastro_VeiculoActivity extends AppCompatActivity {

    TextView titulo;
    EditText txtPlaca, txtModelo;
    ImageView btnSalvar;

    Utilitarios utilitarios = new Utilitarios();
    Retrofit_URL retrofit = new Retrofit_URL();
    VeiculoEstacionado_DTO veiculoEdicao = new VeiculoEstacionado_DTO();

    String DATA_ATUAL;
    String HORA_ATUAL;

    private String MSG = "", TITULO = "", STATUS = "";
    private static String STATUS_FORM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__veiculo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titulo = findViewById(R.id.txtTitulo);
        txtPlaca = findViewById(R.id.txtPlaca);
        txtModelo = findViewById(R.id.txtModelo);
        btnSalvar = findViewById(R.id.btnSalvar);

        DATA_ATUAL = utilitarios.getDataAtual();
        HORA_ATUAL = utilitarios.getHoraAtual();

        if(STATUS_FORM.equals("alterar")){
            veiculoEdicao = getIntent().getExtras().getParcelable("veiculo");
            preencherCampus();
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampus()){
                    if(STATUS_FORM.equals("alterar")){
                        TITULO = "Alterar Veiculo";
                        MSG = "Deseja Alterar os Dados?";
                        STATUS = "alterar";
                        msgAlert(TITULO, MSG, STATUS);
                    }else{
                        salvar(preencherObjeto());
                    }
                }
            }
        });
    }

    public void alterar(VeiculoEstacionado_DTO veiculo){
        VeiculoServise compraService = retrofit.URLBase().create(VeiculoServise.class);
        Call<List<VeiculoEstacionado_DTO>> call = compraService.alterar(veiculo.getId(), veiculo);

        call.enqueue(new Callback<List<VeiculoEstacionado_DTO>>() {
            @Override
            public void onResponse(Call<List<VeiculoEstacionado_DTO>> call, Response<List<VeiculoEstacionado_DTO>> response) {
                if(response.isSuccessful()){
                    System.out.println("alterou");
                    TITULO = "Alterar veiculo";
                    MSG = "Alteração realizada com sucesso";
                    STATUS = "alterar";
                    msgSucesso(TITULO, MSG, STATUS);
                }else{
                    System.out.println("nao altero");
                }
            }

            @Override
            public void onFailure(Call<List<VeiculoEstacionado_DTO>> call, Throwable t) {
                System.out.println("erro ao alterar: " + t.getMessage());
            }
        });
    }

    public void salvar(Movimentacao movimentacao){
        VeiculoServise compraService = retrofit.URLBase().create(VeiculoServise.class);
        Call<Movimentacao> call = compraService.salvarVeiculo(movimentacao);

        call.enqueue(new Callback<Movimentacao>() {
            @Override
            public void onResponse(Call<Movimentacao> call, Response<Movimentacao> response) {
                if(response.isSuccessful()){
                    TITULO = "Cadastro de veiculo";
                    MSG = "Cadastro realizado com sucesso";
                    STATUS = "cadastrar";
                    msgSucesso(TITULO, MSG, STATUS);
                }
            }

            @Override
            public void onFailure(Call<Movimentacao> call, Throwable t) {

            }
        });
    }

    public boolean validarCampus(){
        boolean ok = false;

        if(txtPlaca.getText().toString().equals("")){
            txtPlaca.setError("Preenchimento Obrigatório");
            txtPlaca.requestFocus();
        }else if(txtModelo.getText().toString().equals("")){
            txtModelo.setError("Preenchimento Obrigatório");
            txtModelo.requestFocus();
        }else{
            ok = true;
        }
        return ok;
    }

    public void limparCampus(){
        txtPlaca.setText("");
        txtModelo.setText("");
    }

    public Movimentacao preencherObjeto(){
        Movimentacao m = new Movimentacao();
        m.setPlaca(txtPlaca.getText().toString());
        m.setModelo(txtModelo.getText().toString());
        m.setDataEntrada(DATA_ATUAL);
        m.setTempo(HORA_ATUAL);

        return m;
    }

    public void preencherCampus(){
        txtPlaca.setText(veiculoEdicao.getPlaca());
        txtModelo.setText(veiculoEdicao.getModelo());
        titulo.setText("Alterar Veiculo");
    }

    public VeiculoEstacionado_DTO alterarObjeto(){
        veiculoEdicao.setPlaca(txtPlaca.getText().toString());
        veiculoEdicao.setModelo(txtModelo.getText().toString());
        return veiculoEdicao;
    }

    public void msgSucesso(String titulo, String msg, final String status){
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro_VeiculoActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Cadastro_VeiculoActivity.this).inflate(
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
                if(status.equals("cadastrar")) {
                    limparCampus();
                }else if(status.equals("alterar")){
                    limparCampus();
                    Intent intent = new Intent(Cadastro_VeiculoActivity.this, List_VeiculosActivity.class);
                    List_VeiculosActivity.statusList("estao");
                    startActivity(intent);
                }

                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    public void msgAlert(final String titulo, String msg, final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro_VeiculoActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Cadastro_VeiculoActivity.this).inflate(
                R.layout.alert_inform, (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.txtTitle)).setText(titulo);
        ((TextView) view.findViewById(R.id.txtMessage)).setText(msg);

        ((Button) view.findViewById(R.id.btnYes)).setText("SIM");
        ((Button) view.findViewById(R.id.btnNo)).setText("NÃO");
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warning);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (status.equals("alterar")) {
                    alterar(alterarObjeto());
                }

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    public static void statusForm(String status){
        STATUS_FORM = status;
    }
}