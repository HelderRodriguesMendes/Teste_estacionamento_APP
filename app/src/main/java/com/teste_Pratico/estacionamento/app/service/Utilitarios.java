package com.teste_Pratico.estacionamento.app.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Utilitarios {

	 //PEGAR HORA ATUAL
	@RequiresApi(api = Build.VERSION_CODES.O)
    public String getHoraAtual() {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		LocalTime horaAtual = LocalTime.now();
		String horaAtualForrmat = formatter.format(horaAtual);

		return horaAtualForrmat;
	}

	// PEGAR DATA ATUAL
	@RequiresApi(api = Build.VERSION_CODES.O)
	public static String getDataAtual() {

		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		LocalDate dataAtual = LocalDate.now();
		String DataAtualForrmat = formatter.format(dataAtual);

		DateTimeFormatter formatte = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate localDate = LocalDate.parse(DataAtualForrmat, formatte);

		String pronta = localDate.format(fmt);

		return pronta;
	}
}
