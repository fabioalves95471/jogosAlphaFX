package com.fabiomalves.jogosAlphaFX.inicio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.App;
import com.fabiomalves.jogosAlphaFX.Jogos;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class ControllerInicio implements Initializable {

    @FXML
    Button bDescubraONumero;
    @FXML
    GridPane gpCorpo;

    public void mostraCorpoDaTela () {
        gpCorpo.setVisible(true);
    }
//----------Medotos com  interação direta com a tela----------
	@FXML
	private void bDescubraONumeroEventAction () {
        App.setRoot(Jogos.DESCUBRAONUMERO);
	}
	@FXML
	private void bDescubraONumeroEventKey (KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if(ke.getEventType().equals(KeyEvent.KEY_PRESSED))
				bDescubraONumero.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), true);
			if(ke.getEventType().equals(KeyEvent.KEY_RELEASED)){
				bDescubraONumero.pseudoClassStateChanged(PseudoClass.getPseudoClass("armed"), false);
				bDescubraONumeroEventAction();
			}
		}
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	}

}
