package com.fabiomalves.jogosAlphaFX.login;

import com.fabiomalves.jogosAlphaFX.App;
import com.fabiomalves.jogosAlphaFX.MainViews;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerLogin {

    private Stage stagePopUp, stageLogin;
    private Scene scenePopUp;
    private Parent parentMensagemVisitante, parentCadastroVisitante;
    private boolean endApp;

    @FXML
    Button bVisitante;
    @FXML
    Button bOk_MENSAGEMVISITANTE;
    @FXML
    Button bCancelar_MENSAGEMVISITANTE;

    public ControllerLogin () {
        stagePopUp = new Stage();
        stagePopUp.initModality(Modality.WINDOW_MODAL);
        parentMensagemVisitante = App.FXML_load(MainViews.LOGIN_MENSAGEMVISITANTE);
        parentCadastroVisitante = App.FXML_load(MainViews.LOGIN_CADASTROVISITANTE);
        scenePopUp = new Scene(parentMensagemVisitante);
        stagePopUp.setScene(scenePopUp);
    }

    @FXML
    public void bVisitanteEventAction () {
        scenePopUp.setRoot(parentMensagemVisitante);
        stagePopUp.showAndWait();
        stageLogin.onCloseRequestProperty();
        stagePopUp.setOnCloseRequest(e -> {
        boolean showing;
            if(showing = stagePopUp.isShowing())
                System.out.println("isShowing: "+ showing);
            else {
                System.out.println("isShowing: "+ showing);
                stagePopUp.showAndWait();
            }
        });
//        stagePopUp.getOwner().setOnCloseRequest( e -> {
//            System.out.println("teste");
//        });
    }

    @FXML
    public void bVisitanteEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bVisitanteEventAction();
        }
    }

    void setStageLogin(Stage stageLogin) {
        this.stageLogin = stageLogin;
        stagePopUp.initOwner(stageLogin);
    }

    public boolean isEndApp() {
        return endApp;
    }
}
