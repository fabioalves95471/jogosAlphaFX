package com.fabiomalves.jogosAlphaFX.inicio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.App2;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Apresentacao implements Initializable {

	private int count = 0, i = 0;
    private AnimationTimer caminha, apresenta;


	public void start (Stage stage, GridPane root, String pathProgram) {
        // Carrega as imagens do personagem.
        Image[] imgsCaminha = new Image[8];
        for (short i=0; i<imgsCaminha.length; i++) {
            imgsCaminha[i] = new Image(getClass().getResourceAsStream(pathProgram+"inicio/view/personagem/personagemLado0"+i+".png"));
        }
        Image[] imgsApresenta = new Image[3];
        for (short i=0; i<imgsApresenta.length; i++) {
            imgsApresenta[i] = new Image(getClass().getResourceAsStream(pathProgram+"inicio/view/personagem/personagemApresentacao0"+i+".png"));
        }
        Image[] imgsFalaDoPersonagem = new Image[2];
        for (short i=0; i<imgsFalaDoPersonagem.length; i++) {
            imgsFalaDoPersonagem[i] = new Image(getClass().getResourceAsStream(pathProgram+"inicio/view/personagem/Fala0"+i+".png"));
        }
        // Carrega as imagens inicial do personagem e fala.
        ImageView personagem = new ImageView(imgsCaminha[0]);
        ImageView falaDoPersonagem = new ImageView(imgsFalaDoPersonagem[0]);
        // Posição inicial do personagem e fala na tela.
        personagem.setLayoutX(-80);
        personagem.setLayoutY(130);
        falaDoPersonagem.setLayoutX(200);
        falaDoPersonagem.setLayoutY(70);
        falaDoPersonagem.setVisible(false);
        // Insere o personagem e a fala.
        root.add(personagem, 0, 1);
        root.add(falaDoPersonagem, 0, 1);

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
            carregaInicio(stage);
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
        stage.show();
        timeline.play();
        caminha.start();
	}
    private void carregaInicio(Stage stage) {
        Stage newStage = new Stage();
        newStage.setHeight(500);
        newStage.setMinHeight(500);
        newStage.setMaxHeight(500);
        newStage.setWidth(700);
        newStage.setMinWidth(700);
        newStage.setMaxWidth(700);
    }
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater( () -> {
//            start();
        });
	}
}
