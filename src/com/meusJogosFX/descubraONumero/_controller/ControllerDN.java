package com.meusJogosFX.descubraONumero._controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.meusJogosFX.descubraONumero._model.IJogoDN;
import com.meusJogosFX.descubraONumero._model.JogoDN;
import com.meusJogosFX.descubraONumero.fimDeJogo._controller.ControllerFJ;
import com.meusJogosFX.util.CamposDeEntrada;

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
	private BorderPane noPrincipal;
	@FXML
	private VBox quadroCentral;
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
	private ComboBox<String> cbOperadores;

	private IJogoDN jogo;
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
		jogo = new JogoDN (cbOperadores.getValue(), numeroQuestoes);
		lbOperadorNome.setText(jogo.getOperadorNome());
		atualizaTela();
		quadroCentral.setVisible(true);
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
	public void responde() {
		if (tfResposta.getText().length() == 0)
			return;
		jogo.incrementaPontuacao(jogo.verificaResposta(CamposDeEntrada.getOnlyNumbers(tfResposta)));
		if (jogo.temProximaQuestao()) {
			jogo.rodaProximaQuestao();
			atualizaTela();
			tfResposta.setText("");
			tfResposta.requestFocus();
		} else {
			jogo.finalizaJogo();
			atualizaTela();
			tl.stop();
			tl = null;
			tfResposta.setDisable(true);
			// Chamar tela FimDeJogo e envia este Controller.
			try {
				FXMLLoader loaderFimDeJogo = new FXMLLoader(getClass().getResource("/com/meusJogosFX/descubraONumero/fimDeJogo/_view/ViewFJ.fxml"));
				Parent root1 = loaderFimDeJogo.load();
				Scene scene = new Scene(root1);
				ControllerFJ ctrFimDeJogo = loaderFimDeJogo.getController();
				ctrFimDeJogo.setControllerDN(this);
				String operadorNome = jogo.getOperadorNome();
				String score = (int)(Math.rint(jogo.getAcertosPorcentual()*100)) + "%";
				String tempo = jogo.getTempoFinalDeJogo();
				ctrFimDeJogo.setDados(operadorNome, score, tempo);
				Stage stageFimDeJogo = new Stage();
				ctrFimDeJogo.setStage(stageFimDeJogo);
				stageFimDeJogo.initModality(Modality.WINDOW_MODAL);
				stageFimDeJogo.setScene(scene);
				stageFimDeJogo.initOwner((Stage)noPrincipal.getScene().getWindow());
				stageFimDeJogo.showAndWait();
			}
			catch (IOException e) {
				Label labelErro = new Label("Não pode abrir a tela. "+e.getMessage());
				Scene sceneErro = new Scene(labelErro);
				Stage stageErro = new Stage();
				stageErro.setScene(sceneErro);
				stageErro.initModality(Modality.WINDOW_MODAL);
				stageErro.showAndWait();
				e.printStackTrace();
			}
		}
	}
	public void gravaResultado() {
		System.out.println("método gravaResultado");
	}
	// Atualiza a hora que será exibida na tela.
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
		lbErros.setText(""+jogo.getErros());
		lbAcertos.setText(""+jogo.getAcertos());
		lbPergunta.setText(jogo.getQuestaoString());
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
			// Insere lista na caixa de seleção.
//			ObservableList<String> operadores = FXCollections.observableArrayList("Adição", "Subtração", "Multiplicação", "Divisão");
//			cbOperadores.setItems(operadores);
			cbOperadores.getItems().addAll(JogoDN.getOperadorNomes());
			cbOperadores.getSelectionModel().select(0);
			// Aplica Ouvidor ao campo.
			CamposDeEntrada.numero4Dig(tfResposta);
			Platform.runLater (() -> {
				btIniciar.requestFocus();
			});
	}
}


// ----------------



