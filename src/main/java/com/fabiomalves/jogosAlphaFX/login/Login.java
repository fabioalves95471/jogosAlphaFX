package com.fabiomalves.jogosAlphaFX.login;

import com.fabiomalves.jogosAlphaFX.App;
import com.fabiomalves.jogosAlphaFX.MainViews;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login {
    private Stage stageLogin;
    private ControllerLogin controllerLogin;
    private Scene sceneLogin;

    public Login (Stage stageOwn) {
        FXMLLoader loader = App.FXML_loader(MainViews.LOGIN);
        sceneLogin = new Scene(App.FXML_load(loader));
        controllerLogin = loader.getController();
        newStageLogin(stageOwn);
        controllerLogin.setStageLogin(stageLogin); // passa o stageLogin para o controler: para colocar o stageOwn nas janelas popup da view login.
    }

    private void newStageLogin (Stage stageOwn) {
        stageLogin = new Stage();
        stageLogin.setScene(sceneLogin);
        stageLogin.initModality(Modality.WINDOW_MODAL);
        stageLogin.initOwner(stageOwn);
    }

    public void showLogin(Stage stageOwn) {
        if (!stageLogin.getOwner().equals(stageOwn)) {
            newStageLogin(stageOwn);
        }
        stageLogin.showAndWait();
        if (!controllerLogin.isEndApp())
            showLogin(stageOwn);
    }
    public ControllerLogin getControllerLogin() {
        return controllerLogin;
    }

}
