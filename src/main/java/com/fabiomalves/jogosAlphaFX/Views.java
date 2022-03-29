package com.fabiomalves.jogosAlphaFX;

public enum Views {
    APRESENTACAO("inicio/view/inicio.fxml"),
    INICIO("inicio/view/inicio.fxml"),
    DESCUBRAONUMERO("descubraONumero/view/descubraONumero.fxml"),
    LOGIN("login/view/loginInicio.fxml"),
    LOGIN_MENSAGEMVISITANTE("login/view/mensagemVisitante.fxml"),
    LOGIN_CADASTROVISITANTE("login/view/cadastroVisitante.fxml"),
    LOGIN_ENCERRAMENTO("login/view/encerramento.fxml");
    private String path;
    Views(String path) {
        this.path = path;
    }
    String getPath() {
        return path;
    }
}
