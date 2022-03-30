package com.fabiomalves.jogosAlphaFX;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Apresentacao {

    private AnimationTimer caminha, apresenta;
    private Stage stage;
    private double x, y;
    private GridPane rootApresentacao;
    private String pathProgram;
    private SequentialTransition animacao;

    Apresentacao (Stage stage, String pathProgram) {
        this.stage = stage;
        this.pathProgram = pathProgram;
        // ---Constroi a apresentacao---
        stage.setHeight(400);
        stage.setWidth(600);
        stage.initStyle(StageStyle.UNDECORATED);
        rootApresentacao = (GridPane)App.FXML_load(Views.APRESENTACAO);
        stage.setScene(new Scene(rootApresentacao));
        stage.show();
        // Coloca a posicao da tela em variaveis internas. A tela será fechada após a finalização da apresentacao.
        x = stage.getX()-50;
        y = stage.getY();
        // Coloca borda na tela de apresentação.
        rootApresentacao.setStyle("-fx-border-style: solid; -fx-border-color: grey;");
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
        rootApresentacao.add(ap, 0, 1);

        // Constroi a animacao.
        PauseTransition pause01 = new PauseTransition(Duration.seconds(1.5));

        TranslateTransition mov01 = new TranslateTransition(Duration.seconds(1.5), personagem);
        mov01.setToX(250);
        mov01.setInterpolator(Interpolator.LINEAR);
        mov01.setOnFinished(e -> {
            caminha.stop();
            apresenta.start();
        });

        PauseTransition pause02 = new PauseTransition(Duration.seconds(7));
        pause02.setOnFinished(e -> {
            apresenta.stop();
            caminha.start();
            personagem.setScaleX(-1);
        });

        TranslateTransition mov02 = new TranslateTransition(Duration.seconds(2.5),personagem);
        mov02.setToX(-120);
        mov02.setInterpolator(Interpolator.LINEAR);
        mov02.setOnFinished(e -> {
            caminha.stop();
        });

        PauseTransition pause03 = new PauseTransition(Duration.seconds(0.1));
        pause03.setOnFinished(e -> {
            stage.close();
        });

        animacao = new SequentialTransition(pause01, mov01, pause02, mov02, pause03);

        caminha = new AnimationTimer() {
            private long timeCaminha = 0;
            private boolean semaforoInicial = true;
            private long timeSwitchImages = 100_000_000l; // tempo de troca das imagens (em nanosegundos).
            private int i = 0;
            @Override
            public void handle(long now) {
                if (semaforoInicial) {
                    timeCaminha = now;
                    semaforoInicial = false;
                }
                if (now>=timeCaminha+timeSwitchImages) {
                    personagem.setImage(imgsCaminha[i%8]);
                    i++;
                    timeCaminha = now;
                }
            }
        };
        apresenta = new AnimationTimer() {
            private long timeApresenta = 0l;
            private boolean semaforoInicial = true;
            private short step = 0;
            @Override
            public void handle(long now) {
                if (semaforoInicial) {
                    timeApresenta = now;
                    semaforoInicial = false;
                }
                if ((now >= timeApresenta) && step == 0) {
                    personagem.setImage(imgsApresenta[0]);
                    step++;
                }
                if ((now >= timeApresenta+400_000_000l) && step == 1) {
                    personagem.setImage(imgsApresenta[1]);
                    step++;
                }
                if ((now >= timeApresenta+480_000_000l) && step == 2) {
                    personagem.setImage(imgsApresenta[2]);
                    step++;
                }
                if ((now >= timeApresenta+1_000_000_000l) && step == 3) {
                    falaDoPersonagem.setVisible(true);
                    step++;
                }
                if ((now >= timeApresenta+2_800_000_000l) && step == 4) {
                    falaDoPersonagem.setVisible(false);
                    step++;
                }
                if ((now >= timeApresenta+3_700_000_000l) && step == 5) {
                    falaDoPersonagem.setImage(imgsFalaDoPersonagem[1]);
                    falaDoPersonagem.setTranslateX(-10);
                    falaDoPersonagem.setTranslateY(-30);
                    falaDoPersonagem.setVisible(true);
                    step++;
                }
                if ((now >= timeApresenta+6_200_000_000l) && step == 6) {
                    falaDoPersonagem.setVisible(false);
                    step++;
                }
                if ((now >= timeApresenta+6_400_000_000l) && step == 7) {
                    personagem.setImage(imgsApresenta[1]);
                    step++;
                }
                if ((now >= timeApresenta+6_480_000_000l) && step == 8) {
                    personagem.setImage(imgsApresenta[0]);
                    step++;
                }
            }
        };
    }
	void run () {
        animacao.play();
        caminha.start();
    }
    SequentialTransition getAnimacao () {
        return animacao;
    }
    double getX () {
        return x;
    }
    double getY () {
        return y;
    }
}

