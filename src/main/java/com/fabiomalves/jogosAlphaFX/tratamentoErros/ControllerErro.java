package com.fabiomalves.jogosAlphaFX.tratamentoErros;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerErro implements Initializable  {

	@FXML
	Label lbMensagem;
	

	public void chamaTelaErro(String msg, Parent root, Stage stageOwner) {
		lbMensagem.setText(msg);
		Scene sceneErro = new Scene(root);
		Stage stageErro = new Stage();
		stageErro.setScene(sceneErro);
		stageErro.initModality(Modality.WINDOW_MODAL);
		stageErro.initOwner(stageOwner);
		stageErro.showAndWait();
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
}


