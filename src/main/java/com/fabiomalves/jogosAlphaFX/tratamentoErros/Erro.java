package com.fabiomalves.jogosAlphaFX.tratamentoErros;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Erro {

	public Erro (String msg, Stage stageOwner) {
		try {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/tratamentoErros/view/erro.fxml"));
		Parent rootErro = fxml.load();
		ControllerErro ctrErro = fxml.getController();
		ctrErro.chamaTelaErro(msg, rootErro, stageOwner);
		} catch (Exception ee) {
			System.out.println("Erro: "+ee.getMessage());
			ee.printStackTrace();
		}
	}
}
