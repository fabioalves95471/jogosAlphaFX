package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.descubraONumero.service.IServiceDN;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerPo implements Initializable  {

	@FXML
	ComboBox cbOperador;
	@FXML
	TableColumn coPosicao;
	@FXML
	TableColumn coNome;
	@FXML
	TableColumn coAcertosPorcentual; 
	@FXML
	TableColumn coTotalQuestoes;
	@FXML
	TableColumn coTempoFinalDeJogo;

	IServiceDN service;
	Stage stage;

	public void setService (IServiceDN service) {
		this.service = service;
	}
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
	public void setStage (Stage stage) {
		this.stage = stage;
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		cbOperador.getItems().addAll(IServiceDN.getOperadorNomes());
		cbOperador.getSelectionModel().select(0);
	}
}


