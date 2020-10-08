package com.fabiomalves.jogosAlphaFX.descubraONumero.model;

import java.util.ArrayList;

import com.fabiomalves.jogosAlphaFX.descubraONumero.service.enums.Operador;
import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListsJogos implements IListsJogos {

	private ObservableList<JogoDN>[] lists = new ObservableList[8];
	
	public ListsJogos () {
		for (short i=0; i<lists.length; i++)
			lists[i] = FXCollections.observableArrayList();
	}
	
	public ObservableList<JogoDN> getListUsuario (String operadorNome) {
		if		(Operador.ADICAO.getNome().equals(operadorNome))
			return lists[0];
		else if	(Operador.SUBTRACAO.getNome().equals(operadorNome))
			return lists[1];
		else if	(Operador.MULTIPLICACAO.getNome().equals(operadorNome))
			return lists[2];
		else if	(Operador.DIVISAO.getNome().equals(operadorNome))
			return lists[3];
		return null;
	}
}
