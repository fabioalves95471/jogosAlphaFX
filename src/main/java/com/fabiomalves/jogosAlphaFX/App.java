package com.fabiomalves.jogosAlphaFX;

import com.fabiomalves.jogosAlphaFX.inicio.controller.ControllerInicio;
import com.fabiomalves.jogosAlphaFX.tratamentoErros.Erro;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

//@SpringBootApplication
public class App extends Application {

    static Stage stage;
	static Scene scene = null;
    private static Parent   rootInicio = null,
                            rootDescubraONumero = null;
    private Apresentacao apresentacao = null;
    static String pathJogosAlphaFX = "/com/fabiomalves/jogosAlphaFX/";

	@Override
	public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        carregaScenesJogos();
//        rodaInicioComApresentacao();
        rodaInicio(); // Roda o programa sem a apresentação inicial (comentar rodaInicioComApresentação()).
	}

    /**
     * O aplicativo inicia aqui.
     */
    private void rodaInicioComApresentacao() throws IOException {
        stage.setHeight(400);
        stage.setWidth(600);
        stage.initStyle(StageStyle.UNDECORATED);
        rootInicio = FXML_load(Jogos.INICIO);
        scene = new Scene(rootInicio);
        stage.setScene(scene);
        apresentacao = new Apresentacao(stage, (GridPane)rootInicio, pathJogosAlphaFX);
        apresentacao.run();
    }

    static void rodaInicio() {
        rodaInicio(-1, -1);
    }
    /**
     * É chamado após rodaInicioComApresentacao().
     * Pode-se também iniciar o aplicativo a partir daqui.
     */
    static void rodaInicio(int x, int y)  {
        try {
            stage = new Stage();
            if (x >= 0 && y >= 0) {
                stage.setX(x);
                stage.setY(y);
            }
            FXMLLoader loader = FXML_loader(Jogos.INICIO);
            rootInicio = loader.load();
            ControllerInicio controllerInicio = loader.getController();
            if (scene == null)
                scene = new Scene(rootInicio);
            else
                scene.setRoot(rootInicio);
            stage.setScene(scene);
            stage.show();
            stage.setMinHeight(stage.getHeight());
            stage.setMaxHeight(stage.getHeight());
            stage.setMinWidth(stage.getWidth());
            stage.setMaxWidth(stage.getWidth());
            controllerInicio.mostraCorpoDaTela();
        } catch (IOException e) {
            new Erro("Não pode carregar a Tela: "+"\t\n"+e.getMessage(), stage);
        }
    }

    public static FXMLLoader FXML_loader (Jogos jogos) {
        return new FXMLLoader(App.class.getResource(pathJogosAlphaFX+jogos.getPath()));
    }

	public static Parent FXML_load (Jogos jogos) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(pathJogosAlphaFX+jogos.getPath()));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
			new Erro ("Não pode carregar a Tela: \t"+jogos.name()+"\t\n"+e.getMessage(), stage);
			System.exit(0);
            return null;
        }
	}

    /**
     * Coloca o root na scene principal.
     * O root selecionado pelo enum Jogos.
     * @param jogos
     * @throws IOException
     */
    public static void setRoot(Jogos jogos) {
        setRoot(jogos, null);
    }

    /**
     * Coloca root na scene principal.
     * Carrega, caso não exista, o root selecionado pelo enum Jogos. Se controller for diferente de "null", carrega novamente o root com o controller informado.
     * @param jogos
     * @param controller
     * @throws IOException
     */
	public static void setRoot (Jogos jogos, Object controller) {
        switch (jogos) {
            case INICIO :
//                if (rootInicio == null || controller != null)
//                    rootInicio = FXML_load(jogos, controller);
                scene.setRoot(rootInicio);
                break;
            case DESCUBRAONUMERO :
//                if (rootDescubraONumero == null || controller != null)
//                    rootDescubraONumero = FXML_load(jogos, controller);
                scene.setRoot(rootDescubraONumero);
                break;
        }
	}

    private void carregaScenesJogos() {
        PauseTransition pause01 = new PauseTransition(Duration.seconds(1));
        pause01.setOnFinished (e -> {
            rootDescubraONumero = FXML_load(Jogos.DESCUBRAONUMERO);
        });
        pause01.play();
    }

    public static Stage getStage() {
        return stage;
    }

	public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
		launch(args);
	}
}

