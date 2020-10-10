package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.descubraONumero.service.IServiceDN;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.ServiceDN;
import com.fabiomalves.jogosAlphaFX.tratamentoErros.Erro;
import com.fabiomalves.jogosAlphaFX.util.CamposDeEntrada;

import javafx.util.Duration;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.KeyFrame;

public class ControllerDN implements Initializable {

	@FXML
	private BorderPane bpPrimario;
	@FXML
	private VBox vbQuadroCentral;
	@FXML
	private TextField tfResposta;
	@FXML
	private Label lbTempo;
	@FXML
	private Label lbErros;
	@FXML
	private Label lbAcertos;
	@FXML
	private Label lbOperadorNome;
	@FXML
	private Label lbPergunta;
	@FXML
	private Button btIniciar;
	@FXML
	private Button btPontuacao;
	@FXML
	private ComboBox<String> cbOperadores;

	private IServiceDN service;
	private Stage stagePo;
	private Stage stageFJ;
	private ControllerPo controllerPo;
	private ControllerFJ controllerFJ;
	private short numeroQuestoes;
	private short tempoCorrente;
	private Timeline tl;
	private KeyFrame kf;

	public ControllerDN() {
		service = new ServiceDN();
	}
	@FXML
	public void focusIniciar() {
		btIniciar.requestFocus();
	}	
	@FXML
	public void iniciarJogo() {
		numeroQuestoes = 12;
		// Tempo de jogo
		if (tl != null) tl.stop();
		tempoCorrente = 0;
		lbTempo.setText("00:00");
		kf = new KeyFrame(Duration.millis(1000), e -> {
			lbTempo.setText(atualizaDisplayTempo((short)1));
		});
		tl = new Timeline(kf);
		tl.setCycleCount(3599);
		// Inicia o Jogo.
		vbQuadroCentral.setVisible(true);
		service.iniciarJogoDN(cbOperadores.getValue(), numeroQuestoes);
		tl.play();
		lbOperadorNome.setText(service.getOperadorNome());
		atualizaTela();
		tfResposta.setDisable(false);
		tfResposta.setText("");
		tfResposta.requestFocus();
	}
	public void carregaTelaFimDeJogo() {
		try {
			FXMLLoader loaderFimDeJogo = new FXMLLoader(getClass().getResource("/com/fabiomalves/jogosAlphaFX/descubraONumero/view/fimDeJogo.fxml"));
			Parent root1 = loaderFimDeJogo.load();
			Scene scene = new Scene(root1);
			stageFJ = new Stage();
			stageFJ.setScene(scene);
			controllerFJ = loaderFimDeJogo.getController();
			controllerFJ.setStage(stageFJ);
			stageFJ.initModality(Modality.WINDOW_MODAL);
			stageFJ.initOwner(this.getStage());
		}
		catch (Exception e) {
			e.printStackTrace();
			new Erro ("Não pode carregar a Tela de \"Fim De Jogo\": "+e.getMessage(), this.getStage());
		}
	}
	public void chamaTelaFimDeJogo() {
		String operadorNome	= service.getOperadorNome();
		String score		= (int)(Math.rint(service.getAcertosPorcentual()*100)) + "%";
		String tempo		= service.getTempoFinalDeJogoStr();
		controllerFJ.setDados(operadorNome, score, tempo);
		stageFJ.showAndWait();
	}
	@FXML
	public void iniciarJogoTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			iniciarJogo();
    }
	@FXML
	public void chamaTelaPontuacaoTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			chamaTelaPontuacao();
    }
	private void carregaTelaPontuacao() {
		try {
		FXMLLoader loaderPo = new FXMLLoader(getClass().getResource("/com/fabiomalves/jogosAlphaFX/descubraONumero/view/pontuacao.fxml"));
		Parent rootPo = loaderPo.load();
		Scene scenePo = new Scene(rootPo);
		stagePo = new Stage();
		stagePo.setScene(scenePo);
		controllerPo = loaderPo.getController();
		controllerPo.setConfig(stagePo, service);
		stagePo.initModality(Modality.WINDOW_MODAL);
		stagePo.initOwner(this.getStage());
		} catch (Exception e) {
			e.printStackTrace();
			new Erro ("Não pode carregar a Tela de Pontuacao: "+e.getMessage(), this.getStage());
		}
	}
	@FXML
	public void chamaTelaPontuacaoComOperador() {
		controllerPo.runPontuacao(service.getOperadorNome());
		stagePo.showAndWait();
	}
	public void chamaTelaPontuacao() {
		controllerPo.runPontuacao();
		stagePo.showAndWait();
	}
	@FXML
	public void responde() {
		if (tfResposta.getText().length() == 0)
			return;
		service.incrementaPontuacao(service.verificaResposta(CamposDeEntrada.getOnlyNumbers(tfResposta)));
		if (service.temProximaQuestao()) {
			service.rodaProximaQuestao();
			atualizaTela();
			tfResposta.setText("");
			tfResposta.requestFocus();
		} else {
			service.finalizaJogoDN();
			atualizaTela();
			tl.stop();
			tl = null;
			tfResposta.setDisable(true);
			chamaTelaFimDeJogo();
			chamaTelaPontuacaoComOperador();
		}
	}
	public void gravaResultado() {
		System.out.println("metodo gravaResultado");
	}
	// Atualiza a hora que sera exibida na tela.
	public String atualizaDisplayTempo (short segundos) {
		if (segundos >= 3600 || tempoCorrente >= 3600)
			return "00:00";
		tempoCorrente += segundos;
		byte min = (byte)(tempoCorrente/60);
		byte sec = (byte)(tempoCorrente%60);
		String minStr, secStr;
		if (min < 10) 	minStr = "0"+min;
		else 			minStr = "" +min;
		if (sec < 10) 	secStr = "0"+sec;
		else 			secStr = "" +sec;
		return minStr+":"+secStr;
	}
	public void atualizaTela () {
		lbErros.setText(""+service.getErros());
		lbAcertos.setText(""+service.getAcertos());
		lbPergunta.setText(service.getQuestaoString());
	}
	public Stage getStage () { 
		return (Stage)bpPrimario.getScene().getWindow();
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		cbOperadores.getItems().addAll(IServiceDN.getOperadorNomes()); // Insere lista na caixa de selecao.
		cbOperadores.getSelectionModel().select(0);
		CamposDeEntrada.numero4Dig(tfResposta); // Aplica Ouvidor ao campo de resposta.
		Platform.runLater (() -> {
			btIniciar.requestFocus();
			carregaTelaFimDeJogo();
			carregaTelaPontuacao();
		});
	}
}


// ----------------



