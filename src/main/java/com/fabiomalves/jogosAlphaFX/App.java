package com.fabiomalves.jogosAlphaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//@SpringBootApplication
public class App extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/fabiomalves/jogosAlphaFX/descubraONumero/view/descubraONumero.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setMaxHeight(500);
			primaryStage.setMaxWidth(700);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
		launch(args);
	}
}
