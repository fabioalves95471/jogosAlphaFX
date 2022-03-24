package com.fabiomalves.jogosAlphaFX.util;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CamposDeEntrada {

	public static void numero4Dig (TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			String valueInput;
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("\\d{0,3}"))
					return;
				valueInput = newValue.replaceAll("[^\\d]","");
	        	int i = valueInput.length();
				if (i == 4) {
					valueInput = valueInput.substring(0,1)+"."+valueInput.substring(1,4);
				} else if (i>=5) {
					valueInput = valueInput.substring(0,1)+"."+valueInput.substring(1,4);
				}
				Platform.runLater (() -> {
					textField.setText(valueInput);
					int j = valueInput.length();
					if (j != 0) textField.positionCaret(j);
				});
		    }
		});
	
	}
	public static int getOnlyNumbers (TextField textField) {
		String value = textField.getText().replaceAll("[^\\d]","");
		if (value.length() == 0)
			return 0;
		return Integer.parseInt(value);
	}
	public static void textComMaximo15Digitos (TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			String valueInput;
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
				if (newValue.matches(".{16,}"))
					textField.setText(oldValue.substring(0,15));

			}
		});

	}
}
