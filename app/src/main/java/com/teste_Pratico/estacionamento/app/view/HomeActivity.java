package com.teste_Pratico.estacionamento.app.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.teste_Pratico.estacionamento.app.model.DTO.VeiculoEstacionado_DTO;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cadastro, edicao, consulta, finalizacao;
    String tipoList = "", TITULO = "", MSG = "", STATUS = "", OP1 = "", OP2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cadastro = findViewById(R.id.cadastro);
        edicao = findViewById(R.id.edicao);
        consulta = findViewById(R.id.consulta);
        finalizacao = findViewById(R.id.finalizacao);

        cadastro.setOnClickListener(this);
        edicao.setOnClickListener(this);
        consulta.setOnClickListener(this);
        finalizacao.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.cadastro:
                i = new Intent(this, Cadastro_VeiculoActivity.class);
                VeiculoEstacionado_DTO v = new VeiculoEstacionado_DTO();
                i.putExtra("veiculo", v);
                Cadastro_VeiculoActivity.statusForm("cadastrar");
                startActivity(i);
                break;
            case R.id.consulta:
                TITULO = "TIPO DE CONSULTA";
                MSG = "Deseja Visualizar os veiculos que ainda estão no estacionamento ou que já sairam?";
                STATUS = "consulta";
                OP1 = "Estão";
                OP2 = "Sairam";
                msgAlert(TITULO, MSG, STATUS, OP1, OP2);
                break;
            case R.id.edicao:
                i = new Intent(HomeActivity.this, List_VeiculosActivity.class);
                List_VeiculosActivity.statusList("estao - edicao");
                startActivity(i);
                break;
            case R.id.finalizacao:
                i = new Intent(HomeActivity.this, List_VeiculosActivity.class);
                List_VeiculosActivity.statusList("estao - finalizacao");
                startActivity(i);
                break;
        }
    }

    public void msgAlert(final String titulo, String msg, final String status, String op1, String op2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(HomeActivity.this).inflate(
                R.layout.alert_inform, (ConstraintLayout) findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.txtTitle)).setText(titulo);
        ((TextView) view.findViewById(R.id.txtMessage)).setText(msg);

        ((Button) view.findViewById(R.id.btnYes)).setText(op1);
        ((Button) view.findViewById(R.id.btnNo)).setText(op2);
        ((ImageView) view.findViewById(R.id.imageIcon)).setImageResource(R.drawable.ic_warning);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(status.equals("consulta")){
                    Intent i = null;
                    i = new Intent(HomeActivity.this, List_VeiculosActivity.class);
                    List_VeiculosActivity.statusList("estao");
                    startActivity(i);
                }else{

                }
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(status.equals("consulta")){
                    Intent i = null;
                    i = new Intent(HomeActivity.this, List_VeiculosActivity.class);
                    List_VeiculosActivity.statusList("sairam");
                    startActivity(i);
                }else{

                }
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}