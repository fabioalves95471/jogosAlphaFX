package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerFJ implements Initializable  {

	ControllerDN controllerDN;
	@FXML
	AnchorPane noPrincipal;
	@FXML
	Label lbOperador;
	@FXML
	Label lbScore;
	@FXML
	Label lbTempo;
	
	Stage stage;

	public ControllerFJ () {}

	@FXML
	public void fechaTelaTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			fechaTela();
    }
	@FXML
	public void fechaTelaClickDireito(MouseEvent me) {
		if (me.getButton().equals(MouseButton.PRIMARY))
			fechaTela();
    }
	public void fechaTela () {
		stage.close();
	}
	public void setControllerDN (ControllerDN controllerDN) {
		this.controllerDN = controllerDN;
	}
	public void setStage (Stage stage) {
		this.stage = stage;
	}
	public void setDados (String operador, String score, String tempo) {
		lbOperador.setText(operador);
		lbScore.setText(score);
		lbTempo.setText(tempo);
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
}

