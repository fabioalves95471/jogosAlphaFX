package com.fabiomalves.jogosAlphaFX;

import com.fabiomalves.jogosAlphaFX.inicio.controller.Apresentacao;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

import static com.fabiomalves.jogosAlphaFX.Jogos.DESCUBRAONUMERO;

//@SpringBootApplication
public class App2 extends Application {

    static Stage stage;
	static Scene scene = null;
    private static Parent   inicio = null,
                            descubraonumero = null,
                            labirinto = null;
    private Apresentacao apresentacao;
    static String pathJogosAlphaFX = "/com/fabiomalves/jogosAlphaFX/";

	@Override
	public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        carregaApresentacao();
        apresentacao.start(stage, (GridPane)inicio, pathJogosAlphaFX);

	}
    private void carregaApresentacao() throws IOException {
        stage.setHeight(400);
        stage.setMinHeight(400);
        stage.setMaxHeight(400);
        stage.setWidth(600);
        stage.setMinWidth(600);
        stage.setMaxWidth(600);
        stage.initStyle(StageStyle.UNDECORATED);
        apresentacao = new Apresentacao();
        inicio = FXML_load(Jogos.INICIO, apresentacao);
        scene = new Scene(inicio);
        stage.setScene(scene);
    }
    public static void setStage (Stage newStage) {
        stage = newStage;
        stage.show();
    }
	private static Parent FXML_load (Jogos jogos, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(App2.class.getResource(pathJogosAlphaFX+jogos.getPath()));
        if (controller != null)
            loader.setController(controller);
        return loader.load();
	}

	public static void setRoot (Jogos jogos, Object controller) throws IOException {
        Parent root = null;
        switch (jogos) {
            case DESCUBRAONUMERO :
                System.out.println("descubraonumero");
                root = descubraonumero;
                if (root == null || controller != null)
                    root = descubraonumero = FXML_load(jogos, controller);
            case INICIO :
                System.out.println("inicio");
                root = inicio;
                if (root == null || controller != null)
                    root = inicio = FXML_load(jogos, controller);
        }
        if (scene == null)
            scene = new Scene(root);
        else
		    scene.setRoot(root);
	}
    public static void setRoot(Jogos jogos) throws IOException {
        setRoot(jogos, null);
    }

	public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
		launch(args);
	}
}

