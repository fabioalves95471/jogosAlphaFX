package com.fabiomalves.jogosAlphaFX.login;

import com.fabiomalves.jogosAlphaFX.App;
import com.fabiomalves.jogosAlphaFX.MainViews;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerLogin {

    private Stage stagePopUp, stageLogin;
    private Scene sceneMensagemVisitante, sceneCadastroVisitante;

    @FXML
    Button bVisitante;
    @FXML
    Button bOk_MENSAGEMVISITANTE;
    @FXML
    Button bCancelar_MENSAGEMVISITANTE;
    @FXML
    Button bOk_CADASTROVISITANTE;
    @FXML
    Button bCancelar_CADASTROVISITANTE;
    @FXML
    TextField tfNomeUsuario_CADASTROVISITANTE;    
    
    public ControllerLogin () {
        stagePopUp = new Stage();
        stagePopUp.initModality(Modality.WINDOW_MODAL);
        stagePopUp.initOwner(stageLogin);
        FXMLLoader loaderCV = App.FXML_loader(MainViews.LOGIN_CADASTROVISITANTE);
        FXMLLoader loaderMV = App.FXML_loader(MainViews.LOGIN_MENSAGEMVISITANTE);
        loaderCV.setController(this);
        loaderMV.setController(this);
        sceneMensagemVisitante = new Scene(App.FXML_load(loaderMV));
        sceneCadastroVisitante = new Scene(App.FXML_load(loaderCV));
    }

    @FXML
    public void bVisitanteEventAction () {
        stagePopUp.setScene(sceneMensagemVisitante);
        stagePopUp.show();
    }
    @FXML
    public void bVisitanteEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bVisitanteEventAction();
        }
    }
    public void bOk_MENSAGEMVISITANTEEventAction () {
        stagePopUp.close();
        stagePopUp.setScene(sceneCadastroVisitante);
        stagePopUp.show();
    }
    public void bOk_MENSAGEMVISITANTEEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bOk_MENSAGEMVISITANTEEventAction();
        }
    }
    public void bCancelar_MENSAGEMVISITANTEEventAction () {
        stagePopUp.close();
    }
    public void bCancelar_MENSAGEMVISITANTEEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bCancelar_MENSAGEMVISITANTEEventAction();
        }
    }

    public void bOk_CADASTROVISITANTEEventAction () {
        App.getUsuario().setNome(tfNomeUsuario_CADASTROVISITANTE.getText());
        App.getUsuario().setLoginAtivo(true);
        stagePopUp.close();
        stageLogin.close();
    }
    public void bOk_CADASTROVISITANTEEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bOk_CADASTROVISITANTEEventAction();
        }
    }
    public void bCancelar_CADASTROVISITANTEEventAction () {
        stagePopUp.close();
    }
    public void bCancelar_CADASTROVISITANTEEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bCancelar_CADASTROVISITANTEEventAction();
        }
    }
    public void tfNomeUsuario_CADASTROVISITANTEEventAction () {
        bOk_CADASTROVISITANTEEventAction();
    }
    public void tfNomeUsuario_CADASTROVISITANTEEventKey (KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            tfNomeUsuario_CADASTROVISITANTEEventAction();
        }
    }

    void setStageLogin(Stage stageLogin) {
        this.stageLogin = stageLogin;
        stagePopUp.initOwner(stageLogin);
    }
}
