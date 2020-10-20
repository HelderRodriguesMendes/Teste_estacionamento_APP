package com.teste_Pratico.estacionamento.app.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teste_Pratico.estacionamento.R;
import com.teste_Pratico.estacionamento.api.retrofit.Retrofit_URL;
import com.teste_Pratico.estacionamento.api.servise.VeiculoServise;
import com.teste_Pratico.estacionamento.app.model.DTO.VeiculoEstacionado_DTO;
import com.teste_Pratico.estacionamento.app.model.Movimentacao;
import com.teste_Pratico.estacionamento.app.service.AdapterVeiculosEstacionados;
import com.teste_Pratico.estacionamento.app.service.AdapterVeiculosSairam;
import com.teste_Pratico.estacionamento.app.service.RecyclerItemClickListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List_VeiculosActivity extends AppCompatActivity {

    EditText editPlaca, editModelo;
    TextView tituloHora, txtTituloList;
    RecyclerView recyclerView;

    static String STATUS_LIST;

    String TITULO = "", MSG = "", STATUS = "";

    Retrofit_URL retrofit = new Retrofit_URL();

    VeiculoServise veiculoServise = retrofit.URLBase().create(VeiculoServise.class);

    List<VeiculoEstacionado_DTO> listaVeiculos = new ArrayList<>();
    List<Movimentacao> listaVeiculosSairam = new ArrayList<>();

    VeiculoEstacionado_DTO veiculoEstacionado_dto = new VeiculoEstacionado_DTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_veiculos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editPlaca = findViewById(R.id.editTextPlaca);
        editModelo = findViewById(R.id.editTextModelo);
        tituloHora = findViewById(R.id.txtTituloHora);
        txtTituloList = findViewById(R.id.txtTituloList);

        recyclerView = findViewById(R.id.recyclerView);


        if (STATUS_LIST.equals("estao") || STATUS_LIST.equals("estao - edicao") || STATUS_LIST.equals("estao - finalizacao")) {
            getVeiculosEstacionados();
            if (STATUS_LIST.equals("estao - edicao")|| STATUS_LIST.equals("estao - finalizacao")) {
                Toast.makeText(List_VeiculosActivity.this, "SELECIONE O VEICULO DESEJADO", Toast.LENGTH_SHORT).show();
            }
        } else if (STATUS_LIST.equals("sairam")) {
            txtTituloList.setText("Estacionamentos finalizados");
            getVeiculosNaoEstacionados();
        }


        editPlaca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override //Evento ao digitar no EditText
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editModelo.getText().toString().equals("")) {

                    //toUpperCase() converte letras menusculas para maiuscolas
                    String placa = editPlaca.getText().toString().toUpperCase();

                    if (!placa.equals("")) {
                        if (STATUS_LIST.equals("estao") || STATUS_LIST.equals("estao - edicao") || STATUS_LIST.equals("estao - finalizacao")) {
                            getVeiculosEstacionados_placa(placa);
                        } else if (STATUS_LIST.equals("sairam")) {
                            getVeiculos_N_Estacionados_placa(placa);
                        }
                    } else {
                        if (STATUS_LIST.equals("estao") || STATUS_LIST.equals("estao - edicao") || STATUS_LIST.equals("estao - finalizacao")) {
                            getVeiculosEstacionados();
                        } else if (STATUS_LIST.equals("sairam")) {
                            getVeiculosNaoEstacionados();
                        }
                    }
                } else {
                    Toast.makeText(List_VeiculosActivity.this, "Utilize apenas um tipo de consulta", Toast.LENGTH_SHORT).show();
                    editModelo.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editModelo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override //Evento ao digitar no EditText
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editPlaca.getText().toString().equals("")) {

                    String modelo = editModelo.getText().toString();

                    if (!modelo.equals("")) {
                        if (STATUS_LIST.equals("estao") || STATUS_LIST.equals("estao - edicao") || STATUS_LIST.equals("estao - finalizacao")) {
                            getVeiculosEstacionados_modelo(modelo);
                        } else if (STATUS_LIST.equals("sairam")) {
                            getVeiculos_N_Estacionados_modelo(modelo);
                        }
                    } else {
                        if (STATUS_LIST.equals("estao") || STATUS_LIST.equals("estao - edicao") || STATUS_LIST.equals("estao - finalizacao")) {
                            getVeiculosEstacionados();
                        } else if (STATUS_LIST.equals("sairam")) {
                            getVeiculosNaoEstacionados();
                        }
                    }
                } else {
                    Toast.makeText(List_VeiculosActivity.this, "Utilize apenas um tipo de consulta", Toast.LENGTH_SHORT).show();
                    editModelo.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void getVeiculosEstacionados() {
        Call<List<VeiculoEstacionado_DTO>> call = veiculoServise.getVeiculosEstacionados();
        call.enqueue(new Callback<List<VeiculoEstacionado_DTO>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<VeiculoEstacionado_DTO>> call, Response<List<VeiculoEstacionado_DTO>> response) {
                if (response.isSuccessful()) {
                    listaVeiculos = response.body();
                    listarVeiculos(listaVeiculos);
                }
            }

            @Override
            public void onFailure(Call<List<VeiculoEstacionado_DTO>> call, Throwable t) {
                System.out.println("não busco: " + t.getMessage());
            }
        });
    }

    public void getVeiculosEstacionados_placa(String placa) {
        System.out.println("veio para a placa");
        Call<List<VeiculoEstacionado_DTO>> call = veiculoServise.getVeiculosEstacionados_placa(placa);
        call.enqueue(new Callback<List<VeiculoEstacionado_DTO>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<VeiculoEstacionado_DTO>> call, Response<List<VeiculoEstacionado_DTO>> response) {
                if (response.isSuccessful()) {
                    listaVeiculos = response.body();
                    listarVeiculos(listaVeiculos);
                }
            }

            @Override
            public void onFailure(Call<List<VeiculoEstacionado_DTO>> call, Throwable t) {
                System.out.println("não busco: " + t.getMessage());
            }
        });
    }

    public void getVeiculos_N_Estacionados_placa(String placa) {
        System.out.println("veio para a placa");
        Call<List<VeiculoEstacionado_DTO>> call = veiculoServise.getVeiculos_N_Estacionados_placa(placa);
        call.enqueue(new Callback<List<VeiculoEstacionado_DTO>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<VeiculoEstacionado_DTO>> call, Response<List<VeiculoEstacionado_DTO>> response) {
                if (response.isSuccessful()) {
                    listaVeiculos = response.body();
                    listarVeiculos(listaVeiculos);
                }
            }

            @Override
            public void onFailure(Call<List<VeiculoEstacionado_DTO>> call, Throwable t) {
                System.out.println("não busco: " + t.getMessage());
            }
        });
    }

    public void getVeiculosEstacionados_modelo(String modelo) {
        System.out.println("veio para a placa");
        Call<List<VeiculoEstacionado_DTO>> call = veiculoServise.getVeiculosEstacionados_Modelo(modelo);
        call.enqueue(new Callback<List<VeiculoEstacionado_DTO>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<VeiculoEstacionado_DTO>> call, Response<List<VeiculoEstacionado_DTO>> response) {
                if (response.isSuccessful()) {
                    listaVeiculos = response.body();
                    listarVeiculos(listaVeiculos);
                }
            }

            @Override
            public void onFailure(Call<List<VeiculoEstacionado_DTO>> call, Throwable t) {
                System.out.println("não busco: " + t.getMessage());
            }
        });
    }

    public void getVeiculos_N_Estacionados_modelo(String modelo) {
        System.out.println("veio para a placa");
        Call<List<VeiculoEstacionado_DTO>> call = veiculoServise.getVeiculos_N_Estacionados_Modelo(modelo);
        call.enqueue(new Callback<List<VeiculoEstacionado_DTO>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<VeiculoEstacionado_DTO>> call, Response<List<VeiculoEstacionado_DTO>> response) {
                if (response.isSuccessful()) {
                    listaVeiculos = response.body();
                    listarVeiculos(listaVeiculos);
                }
            }

            @Override
            public void onFailure(Call<List<VeiculoEstacionado_DTO>> call, Throwable t) {
                System.out.println("não busco: " + t.getMessage());
            }
        });
    }

    public void getVeiculosNaoEstacionados() {
        Call<List<Movimentacao>> call = veiculoServise.getVeiculosNaoEstacionados();
        call.enqueue(new Callback<List<Movimentacao>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Movimentacao>> call, Response<List<Movimentacao>> response) {
                if (response.isSuccessful()) {
                    listaVeiculosSairam = response.body();
                    listarVeiculosSairam(listaVeiculosSairam);
                }
            }

            @Override
            public void onFailure(Call<List<Movimentacao>> call, Throwable t) {
                System.out.println("não busco: " + t.getMessage());
            }
        });
    }

    public void listarVeiculos(final List<VeiculoEstacionado_DTO> listVeiculos) {
        AdapterVeiculosEstacionados adapterVeiculosEstacionados = new AdapterVeiculosEstacionados(listVeiculos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()); //Gerenciador de Layout
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterVeiculosEstacionados);

        //EVENTO DE CLICK
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                veiculoEstacionado_dto = listVeiculos.get(position);
                                //click curto
                                if (STATUS_LIST.equals("estao - edicao")) {
                                    TITULO = "ALTERAR VEICULO";
                                    MSG = "Deseja alterar os dados do veiculo: " + veiculoEstacionado_dto.getModelo();
                                    STATUS = "editar";
                                    msgAlert(TITULO, MSG, STATUS);
                                } else if(STATUS_LIST.equals("estao - finalizacao")){
                                    TITULO = "Finalizar Estacionamento";
                                    MSG = "Deseja finalizar o estacionamento do veiculo: " + veiculoEstacionado_dto.getModelo();
                                    STATUS = "finalizar";
                                    msgAlert(TITULO, MSG, STATUS);
                                }
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                VeiculoEstacionado_DTO veiculoEstacionado_dto = listVeiculos.get(position);
                                //click longo
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void listarVeiculosSairam(final List<Movimentacao> listVeiculosSairam) {
        for (Movimentacao v : listVeiculosSairam) {
            LocalDate dE = LocalDate.parse(v.getDataEntrada());
            LocalDate dS = LocalDate.parse(v.getDataSaida());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatadaE = dE.format(formatter);
            String dataFormatadaS = dS.format(formatter);
            v.setDataEntrada(dataFormatadaE);
            v.setDataSaida(dataFormatadaS);
        }

        tituloHora.setText("");
        AdapterVeiculosSairam adapterVeiculosSairam = new AdapterVeiculosSairam(listVeiculosSairam);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()); //Gerenciador de Layout
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterVeiculosSairam);

        //EVENTO DE CLICK
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Movimentacao veiculos = listVeiculosSairam.get(position);
                                //click curto
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Movimentacao veiculos = listVeiculosSairam.get(position);
                                //click longo
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }

    public void msgAlert(final String titulo, String msg, final String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(List_VeiculosActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(List_VeiculosActivity.this).inflate(
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
                if (status.equals("editar")) {
                    Intent intent = new Intent(List_VeiculosActivity.this, Cadastro_VeiculoActivity.class);
                    intent.putExtra("veiculo", veiculoEstacionado_dto);
                    Cadastro_VeiculoActivity.statusForm("alterar");
                    startActivity(intent);
                }else if (status.equals("finalizar")){
                    Intent intent = new Intent(List_VeiculosActivity.this, Finalizar_EstacionamentoActivity.class);
                    intent.putExtra("veiculo", veiculoEstacionado_dto);
                    startActivity(intent);
                }

                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                //se clica sim

                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    public static void statusList(String status) {
        STATUS_LIST = status;
    }
}