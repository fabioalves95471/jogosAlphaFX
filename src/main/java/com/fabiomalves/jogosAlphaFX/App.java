package com.fabiomalves.jogosAlphaFX;

import com.fabiomalves.jogosAlphaFX.inicio.controller.ControllerInicio;
import com.fabiomalves.jogosAlphaFX.login.Login;
import com.fabiomalves.jogosAlphaFX.login.Usuario;
import com.fabiomalves.jogosAlphaFX.tratamentoErros.Erro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

//@SpringBootApplication
public class App extends Application {

    private static Stage primaryStage = null;
	private static Scene scene = null;
    private static Parent   parentInicio = null,
                            parentDescubraONumero = null;
    private static ControllerInicio controllerInicio;
    private Apresentacao apresentacao = null;
    private static Login login;
    private static Usuario usuario;
    private static String pathJogosAlphaFX = "/com/fabiomalves/jogosAlphaFX/";

	@Override
	public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
//        rodaApresentacao();
//        apresentacao.getAnimacao().setOnFinished( e -> {
//            rodaInicio((int)apresentacao.getX(),(int)apresentacao.getY()-30);
//            apresentacao = null; // Retira a referencia para reduzir a memoria.
//        });
        carregaParentsPrincipais(); // Carrega os arquivos enquanto a apresentacao roda.
        rodaInicio(); // Roda o programa sem a apresentação inicial (comentar o codigo acima referente a apresentacao).

    }

    /**
     * Apresentacao inicial do aplicativo.
     */
    private void rodaApresentacao() throws IOException {
        apresentacao = new Apresentacao(primaryStage, pathJogosAlphaFX);
        apresentacao.run();
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
        usuario = new Usuario();
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

    public static FXMLLoader FXML_loader (MainViews view) {
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

	public static Parent FXML_load (MainViews view) {
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
     * @param mainViews
     * @throws IOException
     */
    public static void setRoot(MainViews mainViews) {
        setRoot(mainViews, null);
    }

    /**
     * Coloca root na scene principal.
     * Carrega, caso não exista, o root selecionado pelo enum Jogos. Se o parametro controller for diferente de "null", carrega novamente o root com o controller informado.
     * @param mainViews
     * @param controller
     * @throws IOException
     */
	public static void setRoot (MainViews mainViews, Object controller) {
        switch (mainViews) {
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
            FXMLLoader loader = FXML_loader(MainViews.INICIO);
            if (parentInicio == null)
                parentInicio = loader.load();
            controllerInicio = loader.getController();
            parentDescubraONumero = FXML_load(MainViews.DESCUBRAONUMERO); // Carrega o Parent DescubraONumero
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

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        App.usuario = usuario;
    }

    public static void main(String[] args) {
//		SpringApplication.run(App.class, args);
		launch(args);
	}
}

