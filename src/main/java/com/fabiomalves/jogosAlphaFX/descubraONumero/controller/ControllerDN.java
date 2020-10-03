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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
	private short numeroQuestoes;
	private short tempoCorrente;
	private Timeline tl;
	private KeyFrame kf;

	public ControllerDN() {
		numeroQuestoes = 12;
		tl = null;
	}
	@FXML
	public void focusIniciar() {
		btIniciar.requestFocus();
	}	
	public void iniciarJogo() {
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
		tl.play();
		service = new ServiceDN (cbOperadores.getValue(), numeroQuestoes);
		lbOperadorNome.setText(service.getOperadorNome());
		atualizaTela();
		vbQuadroCentral.setVisible(true);
		tfResposta.setDisable(false);
		tfResposta.setText("");
		tfResposta.requestFocus();
	}
	@FXML
	public void iniciarJogoTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			iniciarJogo();
    }
	@FXML
	public void iniciarJogoClickDireito(MouseEvent me) {
		if (me.getButton().equals(MouseButton.PRIMARY))
			iniciarJogo();
    }
	@FXML
	public void chamaTelaPontuacaoTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			chamaTelaPontuacao();
    }
	@FXML
	public void chamaTelaPontuacaoClickDireito(MouseEvent me) {
		if (me.getButton().equals(MouseButton.PRIMARY))
			chamaTelaPontuacao();
    }
	public void chamaTelaPontuacao() {
		try {
		FXMLLoader loaderPo = new FXMLLoader(getClass().getResource("/com/fabiomalves/jogosAlphaFX/descubraONumero/view/pontuacao.fxml"));
		Parent rootPo = loaderPo.load();
		Scene scenePo = new Scene(rootPo);
		Stage stagePo = new Stage();
		stagePo.setScene(scenePo);
		ControllerPo controllerPo = loaderPo.getController();

		stagePo.initModality(Modality.WINDOW_MODAL);
		stagePo.initOwner(this.getStage());
		stagePo.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			new Erro ("Não pode abrir a Tela: "+e.getMessage(), this.getStage());
		}
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
			// Chamar tela FimDeJogo e envia o ControllerDN para essa.
			try {
				FXMLLoader loaderFimDeJogo = new FXMLLoader(getClass().getResource("/com/fabiomalves/jogosAlphaFX/descubraONumero/view/fimDeJogo.fxml"));
				Parent root1 = loaderFimDeJogo.load();
				Scene scene = new Scene(root1);
				ControllerFJ ctrFimDeJogo = loaderFimDeJogo.getController();
				ctrFimDeJogo.setControllerDN(this);
				String operadorNome = service.getOperadorNome();
				String score = (int)(Math.rint(service.getAcertosPorcentual()*100)) + "%";
				String tempo = service.getTempoFinalDeJogoStr();
				ctrFimDeJogo.setDados(operadorNome, score, tempo);
				Stage stageFimDeJogo = new Stage();
				ctrFimDeJogo.setStage(stageFimDeJogo);
				stageFimDeJogo.initModality(Modality.WINDOW_MODAL);
				stageFimDeJogo.setScene(scene);
				stageFimDeJogo.initOwner(this.getStage());
				stageFimDeJogo.showAndWait();
			}
			catch (Exception e) {
				new Erro ("Não pode abrir a tela: "+e.getMessage(), this.getStage());
			}
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
		// Insere lista na caixa de selecao.
//		ObservableList<String> operadores = FXCollections.observableArrayList("Adicao", "Subtracao", "Multiplicacao", "Divis�o");
//		cbOperadores.setItems(operadores);
		cbOperadores.getItems().addAll(IServiceDN.getOperadorNomes());
		cbOperadores.getSelectionModel().select(0);
		// Aplica Ouvidor ao campo.
		CamposDeEntrada.numero4Dig(tfResposta);
		Platform.runLater (() -> {
			btIniciar.requestFocus();
		});
	}
}


// ----------------



