package com.fabiomalves.jogosAlphaFX;

import java.io.IOException;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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

        PauseTransition pause01 = new PauseTransition(Duration.seconds(1.5));

        TranslateTransition mov01 = new TranslateTransition(Duration.seconds(1.5), personagem);
        mov01.setToX(250);
        mov01.setInterpolator(Interpolator.LINEAR);
        mov01.setOnFinished(e -> {
            caminha.stop();
            count = i = 0;
            apresenta.start();
        });

        PauseTransition pause02 = new PauseTransition(Duration.seconds(7));
        pause02.setOnFinished(e -> {
            apresenta.stop();
            count = i = 0;
            caminha.start();
            personagem.setScaleX(-1);
        });

        TranslateTransition mov02 = new TranslateTransition(Duration.seconds(2.5),personagem);
        mov02.setToX(-120);
        mov02.setInterpolator(Interpolator.LINEAR);
        mov02.setOnFinished(e -> {
            caminha.stop();
        });

        PauseTransition pause03 = new PauseTransition(Duration.seconds(1));

        Animation aumenta01 = new Transition() {
            int aumenta = 100;
            boolean primeiraVez = true;
            int stageHeight, stageWidth, stageX;
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
                final int n = Math.round(aumenta * (float) frac);
                stage.setHeight(stageHeight+n);
                stage.setWidth(stageWidth+n);
                stage.setX(stageX-(n/2));
            }
        };


        PauseTransition pause04 = new PauseTransition(Duration.seconds(1));
        pause04.setOnFinished(e -> {
            stage.close();
            App.rodaInicio((int)stage.getX(), (int)stage.getY()-30);
        });

        SequentialTransition sequentialAnimation = new SequentialTransition(pause01, mov01, pause02, mov02, pause03, aumenta01, pause04);

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
