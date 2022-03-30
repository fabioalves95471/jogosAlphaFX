package com.fabiomalves.jogosAlphaFX;

import com.fabiomalves.jogosAlphaFX.inicio.controller.ControllerInicio;
import com.fabiomalves.jogosAlphaFX.login.Login;
import com.fabiomalves.jogosAlphaFX.tratamentoErros.Erro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

//@SpringBootApplication
public class App extends Application {

    private static Stage primaryStage = null;
	private static Scene scene = null;
    private static Parent   parentInicio = null,
                            parentDescubraONumero = null;
    private static ControllerInicio controllerInicio;
    private Apresentacao apresentacao = null;
    private static Login login;
    private static String pathJogosAlphaFX = "/com/fabiomalves/jogosAlphaFX/";

	@Override
	public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Font.loadFont(this.getClass().getResource("/com/fabiomalves/jogosAlphaFX/fonts/PoetsenOne-Regular.ttf").toExternalForm(), 20);
        apresentacao = new Apresentacao(primaryStage, pathJogosAlphaFX);
        apresentacao.run();
        apresentacao.getAnimacao().setOnFinished( e -> {
            this.primaryStage = new Stage();
            rodaInicio((int)apresentacao.getX(),(int)apresentacao.getY()-30);
            apresentacao = null; // Retira a referencia para reduzir a memoria.
        });
        carregaParentsPrincipais();
//        rodaInicio(); // Roda o programa sem a apresentação inicial (comentar o codigo acima referente a apresentacao).

    }

    /**
     * Roda a tela inicial do aplicatico (após a apresentacao).
     * Pode-se também iniciar o aplicativo a partir daqui.
     */
    static void rodaInicio() {
        rodaInicio(-1, -1);
    }
    /**
     * Roda a tela inicial do aplicatico (após a apresentacao).
     * Pode-se também iniciar o aplicativo a partir daqui.
     */
    static void rodaInicio(int x, int y)  {
        login = new Login(primaryStage);
        if (x >= 0 && y >= 0) {
            primaryStage.setX(x);
            primaryStage.setY(y);
        }
        // Carrega a cena inicial.
        if (scene == null)
            scene = new Scene(parentInicio);
        else
            scene.setRoot(parentInicio);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getWidth());
        controllerInicio.mostraCorpoDaTela();
        login.showLogin(primaryStage);
    }

    public static FXMLLoader FXML_loader (Views view) {
        return new FXMLLoader(App.class.getResource(pathJogosAlphaFX+view.getPath()));
    }

    public static Parent FXML_load (FXMLLoader loader) {
        try {
            Parent parent = loader.load();
            return parent;
        } catch (IOException e) {
            e.printStackTrace();
            new Erro ("Não pode carregar a Tela: \t"+loader.getLocation().toString()+"\t\n"+e.getMessage(), primaryStage);
//			System.exit(0);
            return null;
        }
        
    }

	public static Parent FXML_load (Views view) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(pathJogosAlphaFX+view.getPath()));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
			new Erro ("Não pode carregar a Tela: \t"+view.name()+"\t\n"+e.getMessage(), primaryStage);
//			System.exit(0);
            return null;
        }
	}

    /**
     * Coloca o root na scene principal.
     * O root selecionado pelo enum Jogos.
     * @param views
     * @throws IOException
     */
    public static void setRoot(Views views) {
        setRoot(views, null);
    }

    /**
     * Coloca root na scene principal.
     * Carrega, caso não exista, o root selecionado pelo enum Jogos. Se o parametro controller for diferente de "null", carrega novamente o root com o controller informado.
     * @param views
     * @param controller
     * @throws IOException
     */
	public static void setRoot (Views views, Object controller) {
        switch (views) {
            case INICIO :
                scene.setRoot(parentInicio);
                break;
            case DESCUBRAONUMERO :
                scene.setRoot(parentDescubraONumero);
                break;
        }
	}

    private void carregaParentsPrincipais() {
        try {
            Thread.sleep(10); // Aguarda o carregamento da apresentação.
            FXMLLoader loader = FXML_loader(Views.INICIO);
            if (parentInicio == null)
                parentInicio = loader.load();
            controllerInicio = loader.getController();
            parentDescubraONumero = FXML_load(Views.DESCUBRAONUMERO); // Carrega o Parent DescubraONumero
//            parentDescubraONumero = FXML_load(MainViews.LOGIN); // Carrega o Parent DescubraONumero
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException e) {
            new Erro("Não pode carregar a Tela: "+"\t\n"+e.getMessage(), primaryStage);
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
		launch(args);
	}
}

