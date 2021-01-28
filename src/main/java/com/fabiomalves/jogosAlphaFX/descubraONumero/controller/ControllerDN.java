package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.descubraONumero.service.IServiceDN;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.ServiceDN;
import com.fabiomalves.jogosAlphaFX.tratamentoErros.Erro;
import com.fabiomalves.jogosAlphaFX.util.CamposDeEntrada;

import javafx.scene.shape.Rectangle;
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
	private StackPane spPrimario;
	@FXML
	private Rectangle reErros;
	@FXML
	private VBox vbQuadroCentral;
	@FXML
	private ComboBox<String> cbOperadores;
	@FXML
	private ComboBox<Integer> cbQuestoes;
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

	private IServiceDN service = new ServiceDN();
	private MyGroup<ControllerPo> groupPo = new MyGroup<>();
	private MyGroup<ControllerFJ> groupFJ = new MyGroup<>();
	private short tempoCorrente;
	private int erros;
	private Timeline tlTempoTela, tlError;
	private KeyFrame kfTempoTela, kfError;

	@FXML
	public void focusIniciar() {
		btIniciar.requestFocus();
	}	
	@FXML
	public void iniciarJogo() {
		// Tempo de jogo
		if (tlTempoTela != null) tlTempoTela.stop();
		tempoCorrente = 0;
		lbTempo.setText("00:00");
		kfTempoTela = new KeyFrame(Duration.millis(1000), e -> {
			lbTempo.setText(atualizaDisplayTempo((short)1));
		});
		tlTempoTela = new Timeline(kfTempoTela);
		tlTempoTela.setCycleCount(3599);
		// Inicia o Jogo.
		vbQuadroCentral.setVisible(true);
		int numeroQuestoes;
		if (cbQuestoes.getValue() > 50)
			numeroQuestoes = 50;
		else numeroQuestoes = cbQuestoes.getValue();
		service.iniciarJogoDN(cbOperadores.getValue(), numeroQuestoes);
		tlTempoTela.play();
		atualizaTela();
		tfResposta.setDisable(false);
		tfResposta.setText("");
		tfResposta.requestFocus();
	}

	private void carregaGroup(MyGroup<? extends Object> group, String resource, String resourceCss) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			if (resourceCss == null) resourceCss = "";
			if (!resourceCss.isEmpty())
				scene.getStylesheets().add(resourceCss);
			group.getStage().setScene(scene);
			group.setController(loader.getController());
			group.getStage().initModality(Modality.WINDOW_MODAL);
			group.getStage().initOwner(this.getStage());
		} catch (Exception e) {
			new Erro ("Não pode carregar a Tela: "+resource+"\t\n"+e.getMessage(), this.getStage());
			System.exit(0);
		}
	}
	public void chamaStageFimDeJogo() {
		String operadorNome	= service.getOperadorNome();
		String score		= (int)(Math.rint(service.getAcertosPorcentual()*100)) + "%";
		String tempo		= service.getTempoFinalDeJogoStr();
		groupFJ.getController().setDados(operadorNome, score, tempo);
		groupFJ.getStage().showAndWait();
	}

	@FXML
	public void iniciarJogoTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			iniciarJogo();
    }
	@FXML
	public void chamaStagePontuacaoTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER))
			chamaStagePontuacao();
    }
	@FXML
	public void chamaStagePontuacaoComOperador() {
		groupPo.getController().runPontuacao(service.getOperadorNome());
		groupPo.getStage().showAndWait();
	}
	public void chamaStagePontuacao() {
		groupPo.getController().runPontuacao();
		groupPo.getStage().showAndWait();
	}
	private void eventoError() {
		reErros.setHeight(getStage().getHeight());
		reErros.setWidth(getStage().getWidth());
		reErros.setStyle("visibility: visible");
		tlError.play();
	}
	@FXML
	public void responde() {
		if (tfResposta.getText().length() == 0)
			return;
		erros = service.getErros();
		service.incrementaPontuacao(service.verificaResposta(CamposDeEntrada.getOnlyNumbers(tfResposta)));
		if (erros != service.getErros())
			eventoError();
		if (service.temProximaQuestao()) {
			service.rodaProximaQuestao();
			atualizaTela();
			tfResposta.setText("");
			tfResposta.requestFocus();
		} else {
			service.finalizaJogoDN();
			atualizaTela();
			tlTempoTela.stop();
			tlTempoTela = null;
			tfResposta.setDisable(true);
			chamaStageFimDeJogo();
			chamaStagePontuacaoComOperador();
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
		lbOperadorNome.setText(service.getOperadorNome()+" - Questão:  "+service.getQuestaoCorrente()+" / "+service.getTotalQuestoes());
		lbErros.setText(""+service.getErros());
		lbAcertos.setText(""+service.getAcertos());
		lbPergunta.setText(service.getQuestaoString());
	}
	public Stage getStage () {
		return (Stage)spPrimario.getScene().getWindow();
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		cbOperadores.getItems().addAll(IServiceDN.getOperadorNomes()); // Insere lista na caixa de selecao.
		cbOperadores.getSelectionModel().select(0); // Seleciona um item da caixa de seleção.
		cbQuestoes.getItems().addAll(5,10,15,20); // Insere lista na caixa de seleção.
		cbQuestoes.getSelectionModel().select(1); // Seleciona o segundo item da caixa de seleção.
		CamposDeEntrada.numero4Dig(tfResposta); // Aplica Ouvidor ao campo de resposta para retirar caracteres invalidos.
		// Efeito ao errar.
		kfError = new KeyFrame(Duration.millis(80), e -> {
			reErros.setStyle("visibility: hidden");
		});
		tlError = new Timeline(kfError);
		tlError.setCycleCount(1);

		Platform.runLater (() -> {
			btIniciar.requestFocus(); // Focus no botão "Iniciar".
			carregaGroup( groupPo, "/com/fabiomalves/jogosAlphaFX/descubraONumero/view/pontuacao.fxml", "/com/fabiomalves/jogosAlphaFX/descubraONumero/view/pontuacaoStyle.css");
			carregaGroup( groupFJ, "/com/fabiomalves/jogosAlphaFX/descubraONumero/view/fimDeJogo.fxml", null);
			groupPo.getController().setConfig(groupPo.getStage(), service);
			groupFJ.getController().setStage(groupFJ.getStage());

		});
	}
}

class MyGroup<Contr> {
	private Stage stage = new Stage();
	private Contr controller;

	Stage getStage() {
		return stage;
	}
	Contr getController() {
		return controller;
	}
	void setStage(Stage stage) {
		this.stage = stage;
	}
	void setController(Contr controller) {
		this.controller = controller;
	}
}

// ----------------



