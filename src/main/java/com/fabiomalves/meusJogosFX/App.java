package com.fabiomalves.meusJogosFX;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// Classe principal.
@SpringBootApplication
public class App extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {



/* Teste de Persistencia JPA
			System.out.println("Antes da persistencia");

			Usuario1 usu1 = new Usuario1();
			usu1.setNome("Fulano1");
			usu1.setTipo("Cliente");
			Usuario1 usu2 = new Usuario1();
			usu2.setNome("Fulano2");
			usu2.setTipo("Funcionario");
			
			PersisteUsuario pu = new PersisteUsuario();
			pu.salvar(usu1);

			System.out.println("Depois da persistencia");
*/
			Parent root = FXMLLoader.load(getClass().getResource("/com/fabiomalves/meusJogosFX/descubraONumero/view/descubraONumero.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		launch(args);
	}
}
