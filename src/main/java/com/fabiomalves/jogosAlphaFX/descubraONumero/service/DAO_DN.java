package com.fabiomalves.jogosAlphaFX.descubraONumero.service;

import com.fabiomalves.jogosAlphaFX.descubraONumero.model.JogoDN;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAO_DN {

	public ObservableList<JogoDN> adicaoList;
	public ObservableList<JogoDN> subtracaoList; 
	public ObservableList<JogoDN> multiplicacaoList;
	public ObservableList<JogoDN> divisaoList;

	public DAO_DN () {
		adicaoList = FXCollections.observableArrayList();
		subtracaoList = FXCollections.observableArrayList();
		multiplicacaoList = FXCollections.observableArrayList();
		divisaoList = FXCollections.observableArrayList();
	}

	public ObservableList<JogoDN> getAdicaoList() {
		return adicaoList;
	}

	public ObservableList<JogoDN> getSubtracaoList() {
		return subtracaoList;
	}

	public ObservableList<JogoDN> getMultiplicacaoList() {
		return multiplicacaoList;
	}

	public ObservableList<JogoDN> getDivisaoList() {
		return divisaoList;
	}

}
