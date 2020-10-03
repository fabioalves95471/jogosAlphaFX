package com.fabiomalves.jogosAlphaFX.descubraONumero.model;

import java.util.ArrayList;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListJogos {

	ObservableList<JogoDN>[] lists = new ObservableList[8];
	
	public ListJogos () {

	}
	private ObservableList newList() {
		return FXCollections.observableArrayList();
	}
}
