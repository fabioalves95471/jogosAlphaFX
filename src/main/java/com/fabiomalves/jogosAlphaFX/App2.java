package com.fabiomalves.jogosAlphaFX;

import com.fabiomalves.jogosAlphaFX.tratamentoErros.Erro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

//@SpringBootApplication
public class App2 extends Application {

    static Stage stage;
	static Scene scene = null;
    private static Parent   inicio = null,
                            descubraonumero = null,
                            labirinto = null;
    private Apresentacao apresentacao = null;
    static String pathJogosAlphaFX = "/com/fabiomalves/jogosAlphaFX/";

	@Override
	public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        rodaInicioComApresentacao();
//        rodaInicio();
	}
    private void rodaInicioComApresentacao() throws IOException {
        stage.setHeight(400);
        stage.setMinHeight(400);
//        stage.setMaxHeight(400);
        stage.setWidth(600);
        stage.setMinWidth(600);
//        stage.setMaxWidth(600);
        stage.initStyle(StageStyle.UNDECORATED);
        inicio = FXML_load(Jogos.INICIO, null);
        scene = new Scene(inicio);
        stage.setScene(scene);
        apresentacao = new Apresentacao(stage, (GridPane)inicio, pathJogosAlphaFX);
        apresentacao.run();
    }

    static void rodaInicio() {
        rodaInicio(-1, -1);
    }
    static void rodaInicio(int x, int y)  {
        try {
            stage = new Stage();
            if (x >= 0 && y >= 0) {
                stage.setX(x);
                stage.setY(y);
            }
            stage.setHeight(500);
            stage.setMinHeight(500);
            stage.setMaxHeight(500);
            stage.setWidth(700);
            stage.setMinWidth(700);
            stage.setMaxWidth(700);
            inicio = FXML_load(Jogos.INICIO, null);
            if (scene == null)
                scene = new Scene(inicio);
            else
                scene.setRoot(inicio);
            stage.setScene(scene);
            System.out.println(stage.getY());
            System.out.println(stage.getX());
            stage.show();
            System.out.println(stage.getY());
            System.out.println(stage.getX());
//            stage.setY(stage.getY());
//            stage.setX(stage.getX());
        } catch (IOException e) {
            new Erro("Não pode carregar a Tela: "+"\t\n"+e.getMessage(), stage);
        }
    }
//    public static void setStage (Stage newStage) {
//        stage = newStage;
//    }
	private static Parent FXML_load (Jogos jogos, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(App2.class.getResource(pathJogosAlphaFX+jogos.getPath()));
        if (controller != null)
            loader.setController(controller);
        return loader.load();
	}

    /**
     * Coloca root na scene principal.
     * Carrega, caso não exista, o root selecionado pelo enum Jogos. Se controller for diferente de "null", carrega novamente o root com o controller informado.
     * @param jogos
     * @param controller
     * @throws IOException
     */
	public static void setRoot (Jogos jogos, Object controller) throws IOException {
        switch (jogos) {
            case DESCUBRAONUMERO :
                System.out.println("descubraonumero");
                if (descubraonumero == null || controller != null)
                    descubraonumero = FXML_load(jogos, controller);
                scene.setRoot(descubraonumero);
            case INICIO :
                System.out.println("inicio");
                if (inicio == null || controller != null)
                    inicio = FXML_load(jogos, controller);
                scene.setRoot(inicio);
        }
	}

    /**
     * Coloca o root na scene principal.
     * Carrega, caso não exista, o root selecionado pelo enum Jogos.
     * @param jogos
     * @throws IOException
     */
    public static void setRoot(Jogos jogos) throws IOException {
        setRoot(jogos, null);
    }

	public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
		launch(args);
	}
}

