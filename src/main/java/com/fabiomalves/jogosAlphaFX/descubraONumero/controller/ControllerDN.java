package com.fabiomalves.jogosAlphaFX.descubraONumero.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.App;
import com.fabiomalves.jogosAlphaFX.Jogos;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.IServiceDN;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.ServiceDN;
import com.fabiomalves.jogosAlphaFX.tratamentoErros.Erro;
import com.fabiomalves.jogosAlphaFX.util.CamposDeEntrada;

import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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

import javax.swing.event.HyperlinkEvent;

public class ControllerDN implements Initializable {

	@FXML
	private StackPane spPrimario;
	@FXML
	private Rectangle rErros;
	@FXML
	private VBox vbQuadroCentral;
	@FXML
	private ComboBox<String> cbOperadores;
	@FXML
	private ComboBox<Integer> cbQuestoes;
	@FXML
	private TextField tfResposta;
	@FXML
	private Label lTempo;
	@FXML
	private Label lErros;
	@FXML
	private Label lAcertos;
	@FXML
	private Label lOperadorNome;
	@FXML
	private Label lPergunta;
	@FXML
	private TitledPane tpErros;
	@FXML
	private ListView lvErros;
	@FXML
	private Button bIniciar;
	@FXML
	private Button bRanking;
	@FXML
	private Button bHome;
	@FXML
	private Button bSom;

	private IServiceDN service = new ServiceDN();
	private MyGroup<ControllerRa> groupPo = new MyGroup<>();
	private MyGroup<ControllerFJ> groupFJ = new MyGroup<>();
	private boolean botaoErrosAceso = false;
	private boolean mudo = false;
	private short tempoCorrente;
	private int erros;
	private Timeline tlTempoTela, tlError;
	private KeyFrame kfTempoTela, kfError;
	private final PseudoClass PSEUDOCLASS_ACESO = PseudoClass.getPseudoClass("aceso");
	private final PseudoClass PSEUDOCLASS_SEMAUDIO = PseudoClass.getPseudoClass("semAudio");
	private final URL urlAudioAcerto = getClass().getResource("../view/acerto02.wav");
	private final URL urlAudioErro = getClass().getResource("../view/erro.wav");
	private final Media mediaAudioAcerto = new Media(urlAudioAcerto.toString());
	private final Media mediaAudioErro = new Media(urlAudioErro.toString());

	@FXML
	private void focusIniciar() {
		bIniciar.requestFocus();
	}	
	@FXML
	private void iniciarJogo() {
		limpaTela();
		// Tempo de jogo
		tempoCorrente = 0;
		kfTempoTela = new KeyFrame(Duration.millis(1000), e -> {
			lTempo.setText(atualizaDisplayTempo((short)1));
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
		lvErros.getItems().clear();
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
			group.getStage().initOwner(App.getStage());
		} catch (Exception e) {
			e.printStackTrace();
			new Erro ("Não pode carregar a Tela: \t"+resource+"\t\n"+e.getMessage(), App.getStage());
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
		if (ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.SPACE))
			iniciarJogo();
    }
	@FXML
	public void chamaStageRankingTeclaEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.SPACE))
			chamaStageRanking();
    }
	@FXML
	public void bSomEvent() {
		if (mudo) {
			mudo = false;
			bSom.pseudoClassStateChanged(PSEUDOCLASS_SEMAUDIO, mudo);
		} else {
			mudo = true;
			bSom.pseudoClassStateChanged(PSEUDOCLASS_SEMAUDIO, mudo);
		}
		tfResposta.requestFocus();
	}
	@FXML
	public void bSomEventKey(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if(ke.getEventType().equals(KeyEvent.KEY_PRESSED))
				bSom.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), true);
			if(ke.getEventType().equals(KeyEvent.KEY_RELEASED)){
				bSom.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), false);
				bSomEvent();
			}
		}
	}
	@FXML
	public void chamaStageRankingComOperador() {
		groupPo.getController().runRanking(service.getOperadorNome());
		groupPo.getStage().showAndWait();
	}
	public void chamaStageRanking() {
		groupPo.getController().runRanking();
		groupPo.getStage().showAndWait();
	}
	private void chamaEventoRespostaErrada() {
		rErros.setHeight(App.getStage().getHeight());
		rErros.setWidth(App.getStage().getWidth());
		rErros.setStyle("visibility: visible");
		tlError.play();
		// Inclue a resposta errada no listView para visualizacao do usuario.
		String questaoString = service.getQuestaoString();
		short ocorrencia1 = (short)questaoString.indexOf(" ");
		short ocorrencia2 = (short)questaoString.indexOf(" ",ocorrencia1+1);
		TextFlow tfRespostaErrada = new TextFlow();
		Text t1 = new Text(questaoString.substring(0,ocorrencia2+1));
		Text t2 = new Text(String.valueOf(CamposDeEntrada.getOnlyNumbers(tfResposta)));
		Text t3 = new Text(questaoString.substring(ocorrencia2+2));
		t1.setStyle("-fx-fill: #363636;");
		t2.setStyle("-fx-fill: #ff6666");
		t3.setStyle("-fx-fill: #363636;");
		tfRespostaErrada.getChildren().addAll(t1,t2,t3);
		tfRespostaErrada.setStyle("	 -fx-background-color: transparent;" +
									"-fx-background-radius: 5 5 0 0;" +
									"-fx-padding: 2 5 2 5;");
		lvErros.getItems().add(tfRespostaErrada);
		if (!botaoErrosAceso) {
			tpErros.lookup(".arrow").pseudoClassStateChanged(PSEUDOCLASS_ACESO, true);
			tpErros.lookup(".text").pseudoClassStateChanged(PSEUDOCLASS_ACESO, true);
			botaoErrosAceso = true;
		}
	}
	@FXML
	private void chamaHome() {
		limpaTela();
		vbQuadroCentral.setVisible(false);
		App.setRoot(Jogos.INICIO);
	}
	private void limpaTela() {
		if(tlTempoTela != null)
			tlTempoTela.stop();
		lErros.setText("0");
		lAcertos.setText("0");
		lTempo.setText("00:00");
		lvErros.getItems().clear();
		if (botaoErrosAceso) {
			tpErros.lookup(".arrow").pseudoClassStateChanged(PSEUDOCLASS_ACESO, false);
			tpErros.lookup(".text").pseudoClassStateChanged(PSEUDOCLASS_ACESO, false);
			botaoErrosAceso = false;
		}
	}
	@FXML
	private void chamaHomeEnter (KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.SPACE))
			chamaHome();
	}
	@FXML
	public void responde() {
		if (tfResposta.getText().length() == 0)
			return;
		erros = service.getErros();
		service.incrementaPontuacao(service.verificaResposta(CamposDeEntrada.getOnlyNumbers(tfResposta)));
		// Verifica resposta (certa ou errada) e chama os eventos correspondentes.
		if (erros != service.getErros()) {
			if (!mudo)
				new MediaPlayer(mediaAudioErro).play();
			chamaEventoRespostaErrada();
		} else {
			if (!mudo)
				new MediaPlayer(mediaAudioAcerto).play();
		}
		// Roda próxima questão.
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
			chamaStageRankingComOperador();
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
		lOperadorNome.setText(service.getOperadorNome()+" - Questão:  "+service.getQuestaoCorrente()+" / "+service.getTotalQuestoes());
		lErros.setText(""+service.getErros());
		lAcertos.setText(""+service.getAcertos());
		lPergunta.setText(service.getQuestaoString());
	}
//	public Stage getStage () {
//		return (Stage)spPrimario.getScene().getWindow();
//	}
	private void preparaEfeitoErrar() {
		kfError = new KeyFrame(Duration.millis(80), e -> {
			rErros.setStyle("visibility: hidden");
		});
		tlError = new Timeline(kfError);
		tlError.setCycleCount(1);
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		cbOperadores.getItems().addAll(IServiceDN.getOperadorNomes()); // Insere lista na caixa de selecao.
		cbOperadores.getSelectionModel().select(0); // Seleciona um item da caixa de seleção.
		cbQuestoes.getItems().addAll(5,15,30); // Insere lista na caixa de seleção.
		cbQuestoes.getSelectionModel().select(0); // Seleciona o segundo item da caixa de seleção.
		CamposDeEntrada.numero4Dig(tfResposta); // Aplica Ouvidor ao campo de resposta para retirar caracteres invalidos.
		preparaEfeitoErrar();
		Platform.runLater (() -> {
			bIniciar.requestFocus(); // Focus no botão "Iniciar".
			carregaGroup( groupPo, "/com/fabiomalves/jogosAlphaFX/descubraONumero/view/ranking.fxml", "/com/fabiomalves/jogosAlphaFX/descubraONumero/view/rankingStyle.css");
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



