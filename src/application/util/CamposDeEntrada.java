package application.util;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CamposDeEntrada {

	public static void numero6Dig (TextField textField) {
		textField.textProperty().addListener(new ChangeListener<String>() {
			String valueInput;
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (newValue.matches("\\d{0,3}") || newValue.matches("\\d{3}[.]\\d{1,3}"))
					return;
				valueInput = newValue.replaceAll("[^\\d]","");
	        	int i = valueInput.length();
				if (4 <= i && i <= 6) {
					valueInput = valueInput.substring(0,3)+"."+valueInput.substring(3,i);
				} else if (i>=7) {
					valueInput = valueInput.substring(0,3)+"."+valueInput.substring(3,6);
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
}
