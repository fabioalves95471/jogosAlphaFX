package com.fabiomalves.jogosAlphaFX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.App2;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Apresentacao {

	private int count = 0, i = 0;
    private AnimationTimer caminha, apresenta;
    private Stage stage;
    private GridPane root;
    private String pathProgram;

    Apresentacao (Stage stage, GridPane root, String pathProgram) {
        this.stage = stage;
        this.root = root;
        this.pathProgram = pathProgram;
    }

	public synchronized void run () throws IOException {
        // Carrega as imagens do personagem e fala.
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
        ImageView personagem = new ImageView(imgsCaminha[0]);
        ImageView falaDoPersonagem = new ImageView(imgsFalaDoPersonagem[0]);
        // Posição inicial do personagem e fala na tela.
        personagem.setX(-80);
        personagem.setY(100);
        falaDoPersonagem.setX(215);
        falaDoPersonagem.setY(40);
        falaDoPersonagem.setVisible(false);
        // Insere o personagem e a fala.
        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(personagem, falaDoPersonagem);
        root.add(ap, 0, 1);

        TranslateTransition mov01 = new TranslateTransition(Duration.seconds(2), personagem);
        mov01.setToX(250);
        mov01.setOnFinished(e -> {
            caminha.stop();
            count = i = 0;
            apresenta.start();
        });

        PauseTransition pause01 = new PauseTransition(Duration.seconds(7));
        pause01.setOnFinished(e -> {
            apresenta.stop();
            count = i = 0;
            caminha.start();
            personagem.setScaleX(-1);
        });

        TranslateTransition mov02 = new TranslateTransition(Duration.seconds(3),personagem);
        mov02.setToX(-120);
        mov02.setOnFinished(e -> {
            caminha.stop();
        });

        PauseTransition pause02 = new PauseTransition(Duration.seconds(1));

        Animation aumenta01 = new Transition() {
            int length = 100;
            boolean primeiraVez = true;
            int rootHeight = (int)root.getPrefHeight(),
                rootWidth = (int)root.getPrefWidth(),
                stageHeight,
                stageWidth,
                stageX;
            {
                setCycleDuration(Duration.seconds(1));
                setOnFinished(e -> {
                });
            }
            protected void interpolate (double frac) {
                if (primeiraVez) {
                    stageHeight = (int)stage.getHeight();
                    stageWidth = (int)stage.getWidth();
                    stageX = (int)stage.getX();
                    primeiraVez = false;
                }

                final int n = Math.round(length * (float) frac);
                stage.setHeight(stageHeight+n);
                stage.setWidth(stageWidth+n);
                root.setPrefHeight(rootHeight+n);
                root.setPrefWidth(rootWidth+n);
                if (n%2 == 0) {
                    stage.setX(stageX-(n/2));
//                  stage.setY(stage.getY()-1);
                }
            }
        };

        PauseTransition pause03 = new PauseTransition(Duration.seconds(1));
        pause03.setOnFinished(e -> {
            stage.close();
            App2.rodaInicio((int)stage.getX(), (int)stage.getY()-30);
        });

        SequentialTransition sequentialAnimation = new SequentialTransition(mov01, pause01, mov02, pause02, aumenta01, pause03);
//        SequentialTransition sequentialAnimation = new SequentialTransition(aumenta01);

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
        sequentialAnimation.play();
        caminha.start();
	}
}
