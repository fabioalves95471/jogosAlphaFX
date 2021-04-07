package com.fabiomalves.jogosAlphaFX.inicio.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.fabiomalves.jogosAlphaFX.App;
import com.fabiomalves.jogosAlphaFX.Jogos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ControllerInicio implements Initializable {

    @FXML
    Button bDescubraONumero;
    @FXML
    GridPane gpCorpo;

    @FXML
    private void chamaTelaDescubraONumero() {
        App.setRoot(Jogos.DESCUBRAONUMERO);
    }

    public void mostraCorpoDaTela() {
        gpCorpo.setVisible(true);
    }

    public void initialize(URL location, ResourceBundle resources) {
	}
}
