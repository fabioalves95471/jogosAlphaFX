package com.fabiomalves.jogosAlphaFX;

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

//@SpringBootApplication
public class App extends Application {

	private int count = 0, i = 0; // apresentacaoInicial
    private AnimationTimer caminha, apresenta; // apresentacaoInicial;
    static Stage stage;
	static Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
		apresentacaoInicial(primaryStage);

//		try {
//			//scene = new Scene(FXML_load("descubraONumero/view/descubraONumero.fxml"));
//			scene = new Scene(FXML_load("apresentacao/view/primary.fxml"));
//			primaryStage.setScene(scene);
//			primaryStage.setMaxHeight(500);
//			primaryStage.setMaxWidth(700);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	private static Parent FXML_load (String resource) throws IOException {
		return FXMLLoader.load(App.class.getResource("/com/fabiomalves/jogosAlphaFX/"+resource));
	}
	public static void setRoot (String resource) throws IOException {
		Parent root = FXML_load(resource);
		scene.setRoot(root);
	}
    private void telaDeInicio () {
		try {
			scene.setRoot(FXML_load("descubraONumero/view/descubraONumero.fxml"));
            stage = new Stage();
			stage.setScene(scene);
			stage.setMaxHeight(500);
			stage.setMaxWidth(700);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	private void apresentacaoInicial(Stage primaryStage) {
        String path = "/com/fabiomalves/jogosAlphaFX/apresentacaoInicial/view/";
        AnchorPane root = new AnchorPane();
        // insere imagem em plano de fundo.
        Image imageBG = new Image(getClass().getResourceAsStream(path+"telaInicial.png"));
        root.setBackground(new Background(new BackgroundImage(imageBG, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        // Prepara a cena e a janela.
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setHeight(imageBG.getHeight());
        primaryStage.setWidth(imageBG.getWidth());
        primaryStage.show();
        // Carrega as imagens do personagem.
        Image[] imgsCaminha = new Image[8];
        for (short i=0; i<imgsCaminha.length; i++) {
            imgsCaminha[i] = new Image(getClass().getResourceAsStream(path+"personagem/personagemLado0"+i+".png"));
        }
        Image[] imgsApresenta = new Image[3];
        for (short i=0; i<imgsApresenta.length; i++) {
            imgsApresenta[i] = new Image(getClass().getResourceAsStream(path+"personagem/personagemApresentacao0"+i+".png"));
        }
        Image[] imgsFalaDoPersonagem = new Image[2];
        for (short i=0; i<imgsFalaDoPersonagem.length; i++) {
            imgsFalaDoPersonagem[i] = new Image(getClass().getResourceAsStream(path+"personagem/Fala0"+i+".png"));
        }
        // Carrega as imagens inicial do personagem e fala.
        ImageView personagem = new ImageView(imgsCaminha[0]);
        ImageView falaDoPersonagem = new ImageView(imgsFalaDoPersonagem[0]);
        // Posição inicial do personagem e fala na tela.
        personagem.setLayoutX(-80);
        personagem.setLayoutY(230);
        falaDoPersonagem.setLayoutX(200);
        falaDoPersonagem.setLayoutY(170);
        falaDoPersonagem.setVisible(false);
        // Insere o personagem.
        root.getChildren().addAll(personagem, falaDoPersonagem);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
        new KeyFrame(new Duration(1000), new KeyValue(personagem.translateXProperty(), -80)),
        new KeyFrame(new Duration(3000), e-> {
            caminha.stop();
            count = i = 0;
            apresenta.start();
        }, new KeyValue(personagem.translateXProperty(), 230)),
        new KeyFrame(new Duration(10000), e-> {
            apresenta.stop();
            count = i = 0;
            caminha.start();
            personagem.setScaleX(-1);
        },new KeyValue(personagem.translateXProperty(), 230)),
        new KeyFrame(new Duration(12000), e -> {
            caminha.stop();
            stage.close();
            telaDeInicio();
        }, new KeyValue(personagem.translateXProperty(), -80))
        );
        caminha = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (0==count%8) {
                    personagem.setImage(imgsCaminha[i%8]);
                    i++;
                }
                count++;
            }
        };
        apresenta = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if ((i!=imgsApresenta.length) && (0==count%6) && (count ==0 || count > 23)) {
                    personagem.setImage(imgsApresenta[i]);
                    i++;
                }
                if (count == 55) {
                    falaDoPersonagem.setVisible(true);
                }
                if (count == 170) {
                    falaDoPersonagem.setVisible(false);
                }
                if (count == 220) {
                    falaDoPersonagem.setImage(imgsFalaDoPersonagem[1]);
                    falaDoPersonagem.setTranslateX(-10);
                    falaDoPersonagem.setTranslateY(-30);
                    falaDoPersonagem.setVisible(true);
                }
                if (count == 420) {
                    falaDoPersonagem.setVisible(false);
                }
                count++;
            }
        };
        timeline.play();
        caminha.start();
	}

	public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
		launch(args);
	}
}
