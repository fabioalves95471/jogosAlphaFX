package com.fabiomalves.jogosAlphaFX.login;

import com.fabiomalves.jogosAlphaFX.App;
import com.fabiomalves.jogosAlphaFX.Views;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Login {
    private Stage stageLogin;
    private ControllerLogin controllerLogin;
    private Scene sceneLogin, sceneEncerramento;
    private static Usuario usuario;

    @FXML
    Button bSim_ENCERRAMENTO;
    @FXML
    Button bCancelar_ENCERRAMENTO;

    public Login (Stage stageOwn) {
        usuario = new Usuario();
        // Cria e prepara o stage LOGIN_ENCERRAMENTO.
        FXMLLoader loaderE = App.FXML_loader(Views.LOGIN_ENCERRAMENTO);
        loaderE.setController(this);
        sceneEncerramento = new Scene(App.FXML_load(loaderE));
        // Cria e prepara o stage LOGIN.
        FXMLLoader loader = App.FXML_loader(Views.LOGIN);
        sceneLogin = new Scene(App.FXML_load(loader));
        controllerLogin = loader.getController();
        newStageLogin(stageOwn);
    }

    private void newStageLogin (Stage stageOwn) {
        stageLogin = new Stage();
        stageLogin.setScene(sceneLogin);
        stageLogin.initModality(Modality.WINDOW_MODAL);
        stageLogin.initOwner(stageOwn);
        controllerLogin.setStageLogin(stageLogin); // passa o stageLogin para o controler: para colocar o stageOwn nas janelas popup da view login.
        // Funcão ao encerrar o programa.
        stageLogin.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle (WindowEvent we) {
                if (!usuario.isLoginAtivo()) {
                    newStageLogin((Stage)stageLogin.getOwner());
                    showEncerramento();
                }
            }
        });
    }

    public void showLogin(Stage stageOwn) {
        if (!stageLogin.getOwner().equals(stageOwn)) {
            newStageLogin(stageOwn);
        }
        stageLogin.show();
    }

    private void showEncerramento() {
        stageLogin.setScene(sceneEncerramento);
        stageLogin.show();
    }

    public ControllerLogin getControllerLogin() {
        return controllerLogin;
    }

    public void bSim_ENCERRAMENTOEventAction () {
        Platform.exit();
    }
    public void bSim_ENCERRAMENTOEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bSim_ENCERRAMENTOEventAction();
        }
    }

    public void bCancelar_ENCERRAMENTOEventAction () {
        stageLogin.close();
        stageLogin.setScene(sceneLogin);
        showLogin((Stage)stageLogin.getOwner());
    }

    public void bCancelar_ENCERRAMENTOEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bCancelar_ENCERRAMENTOEventAction();
        }
    }

    public static Usuario getUsuario() {
        return usuario;
    }
}
