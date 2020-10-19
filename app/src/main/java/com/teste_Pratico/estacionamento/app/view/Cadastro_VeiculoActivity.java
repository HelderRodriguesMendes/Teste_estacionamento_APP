package com.teste_Pratico.estacionamento.app.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
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
import com.teste_Pratico.estacionamento.app.model.Movimentacao;
import com.teste_Pratico.estacionamento.app.service.Utilitarios;

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

    String DATA_ATUAL;
    String HORA_ATUAL;

    private String MSG = "", TITULO = "", STATUS = "";
    private static String STATUS_FORM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro__veiculo);

        titulo = findViewById(R.id.txtTitulo);
        txtPlaca = findViewById(R.id.txtPlaca);
        txtModelo = findViewById(R.id.txtModelo);
        btnSalvar = findViewById(R.id.btnSalvar);

        DATA_ATUAL = utilitarios.getDataAtual();
        HORA_ATUAL = utilitarios.getHoraAtual();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampus()){
                    salvar(preencherObjeto());
                }
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
                }else{

                }

                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    public static void statusForm(String status){
        STATUS_FORM = status;
    }
}