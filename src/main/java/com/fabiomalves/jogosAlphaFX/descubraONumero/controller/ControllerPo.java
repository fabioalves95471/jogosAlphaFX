package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.descubraONumero.model.JogoDN;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.IServiceDN;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerPo implements Initializable  {

	@FXML
	ComboBox<String> cbOperador;
	@FXML
	Button btSair;
	@FXML
	TableView<JogoDN> tvPontuacao;
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
	@FXML
	public void runPontuacao() {
		listAtual = service.getListUsuario((String)cbOperador.getValue());
		tvPontuacao.getItems().clear();
		tvPontuacao.getItems().addAll(listAtual);
		btSair.requestFocus();
	}
	public void runPontuacao(String operador) {
		cbOperador.getSelectionModel().select(operador);
		runPontuacao();
	}
	@FXML
	public void fechaTelaTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			fechaTela();
    }
	@FXML
	public void fechaTelaClickEsquerdo(MouseEvent me) {
		if (me.getButton().equals(MouseButton.PRIMARY))
			fechaTela();
	}
	@FXML
	public void fechaTela () {
		stage.close();
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		cbOperador.getItems().addAll(IServiceDN.getOperadorNomes());
		cbOperador.getSelectionModel().select(0);
		coNome.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		coAcertosPorcentual.setCellValueFactory(new PropertyValueFactory<>("acertosPorcentualFormatado"));
		coTotalQuestoes.setCellValueFactory(new PropertyValueFactory<>("totalQuestoes"));
		coTempoFinalDeJogo.setCellValueFactory(new PropertyValueFactory<>("tempoFinalDeJogoFormatado"));
		coPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
		tvPontuacao.setPlaceholder(new Label("Nenhum jogo aqui."));
	}
}


