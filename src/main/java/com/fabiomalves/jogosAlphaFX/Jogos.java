package com.fabiomalves.jogosAlphaFX;

public enum Jogos {
        INICIO("inicio/view/inicio.fxml"),
        INEXISTENTE_TESTE_DE_ERRO("caminho_errado"),
        DESCUBRAONUMERO("descubraONumero/view/descubraONumero.fxml");
        private String path;
        Jogos (String path) {
            this.path = path;
        }
        String getPath() {
            return path;
        }
}
