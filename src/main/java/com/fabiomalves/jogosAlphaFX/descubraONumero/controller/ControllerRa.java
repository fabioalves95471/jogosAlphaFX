package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.descubraONumero.model.JogoDN;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.IServiceDN;

import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ControllerRa implements Initializable  {

	@FXML
	ComboBox<String> cbOperador;
	@FXML
	Label lOperador;
	@FXML
	Button bSair;
	@FXML
	TableView<JogoDN> tvRanking;
	@FXML
	TableColumn<Integer, JogoDN> coPosicao;
	@FXML
	TableColumn<String, JogoDN>coNome;
	@FXML
	TableColumn<Short, JogoDN> coAcertosPorcentual; 
	@FXML
	TableColumn<Short, JogoDN> coTotalQuestoes;
	@FXML
	TableColumn<String, JogoDN> coTempoFinalDeJogo;
	@FXML
	
	private IServiceDN service;
	private ObservableList<JogoDN> listAtual;
	private Stage stage;

	public void runRanking(String operador) {
		cbOperador.getSelectionModel().select(operador);
		runRanking();
	}
	public void runRanking() {
		String operador = (String)cbOperador.getValue();
		listAtual = service.getListUsuario(operador);
		lOperador.setText(operador);
		tvRanking.getItems().clear();
		tvRanking.getItems().addAll(listAtual);
		bSair.requestFocus();
	}

//----------Medotos com  interação direta com a tela----------
	@FXML
	private void cbOperadorEventOnHiding() {
		runRanking();
	}
	@FXML
	private void bSairEventAction () {
		stage.close();
	}
	@FXML
	private void bSairEventKey (KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if(ke.getEventType().equals(KeyEvent.KEY_PRESSED))
				bSair.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), true);
			if(ke.getEventType().equals(KeyEvent.KEY_RELEASED)){
				bSair.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), false);
				bSairEventAction();
			}
		}
	}
// -------------------Getters and Setters-----------------------
	public void setConfig(Stage stage, IServiceDN service) {
		setService(service);
		setStage(stage);
	}
	public void setService (IServiceDN service) {
		this.service = service;
	}
	public void setStage (Stage stage) {
		this.stage = stage;
	}

	@Override
    public void initialize(URL location, ResourceBundle resources) {
		cbOperador.getItems().addAll(IServiceDN.getOperadorNomes());
		cbOperador.getSelectionModel().select(0);
		coNome.setCellValueFactory(new PropertyValueFactory<>("usuarioNome"));
		coAcertosPorcentual.setCellValueFactory(new PropertyValueFactory<>("acertosPorcentualFormatado"));
		coTotalQuestoes.setCellValueFactory(new PropertyValueFactory<>("totalQuestoes"));
		coTempoFinalDeJogo.setCellValueFactory(new PropertyValueFactory<>("tempoFinalDeJogoFormatado"));
		coPosicao.setCellValueFactory(new PropertyValueFactory<>("posicaoFormatada"));
		tvRanking.setPlaceholder(new Label("Nenhum jogo aqui."));
	}
}


