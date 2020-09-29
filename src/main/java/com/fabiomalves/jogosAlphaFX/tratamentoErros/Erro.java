package com.fabiomalves.jogosAlphaFX.tratamentoErros;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Erro {

	public Erro (String msg, Stage stage) {
		try {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/com/fabiomalves/jogosAlphaFX/tratamentoErros/view/erro.fxml"));
		Parent rootErro = fxml.load();
		ControllerErro ctrErro = fxml.getController();
		ctrErro.chamaTelaErro(msg, rootErro, stage);
		} catch (Exception ee) {
			System.out.println("Erro: "+ee.getMessage());
			ee.printStackTrace();
		}
	}
}
