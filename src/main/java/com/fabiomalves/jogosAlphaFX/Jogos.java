package com.fabiomalves.jogosAlphaFX;

public enum Jogos {
        DESCUBRAONUMERO("descubraONumero/view/descubraONumero.fxml"),
        INICIO("inicio/view/inicio.fxml");
        private String path;
        Jogos (String path) {
            this.path = path;
        }
        String getPath() {
            return path;
        }
}
