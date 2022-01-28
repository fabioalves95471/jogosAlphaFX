package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControllerFJ implements Initializable  {

	@FXML
	AnchorPane apPrimario;
	@FXML
	Button bFechar;
	@FXML
	Label lOperador;
	@FXML
	Label lScore;
	@FXML
	Label lTempo;
	
	Stage stage;

	public ControllerFJ () {}
//----------Medotos com  interação direta com a tela----------
	@FXML
	private void bFecharEventAction () {
		stage.close();
	}
	@FXML
	private void bFecharEventKey (KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if(ke.getEventType().equals(KeyEvent.KEY_PRESSED))
				bFechar.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), true);
			if(ke.getEventType().equals(KeyEvent.KEY_RELEASED)){
				bFechar.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), false);
				bFecharEventAction();
			}
		}
	}
// ------------------Getters and Setters-----------------------
	public void setStage (Stage stage) {
		this.stage = stage;
	}
	public void setDados (String operador, String score, String tempo) {
		lOperador.setText(operador);
		lScore.setText(score);
		lTempo.setText(tempo);
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
}


