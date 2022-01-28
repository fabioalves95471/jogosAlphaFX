package com.fabiomalves.jogosAlphaFX.descubraONumero.service;

import com.fabiomalves.jogosAlphaFX.descubraONumero.model.JogoDN;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAO_DN {

	public DAO_DN () {
	}

	private ObservableList<JogoDN> newList() {
		return FXCollections.observableArrayList();
	}

}
