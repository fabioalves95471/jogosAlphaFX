package com.fabiomalves.jogosAlphaFX.login;

import java.util.List;

public class Usuario {
    private long usuarioId;
    private boolean loginAtivo;
    private long ultimoTempoAtivo;
    private short tipoDeLogin;
    private String nome;
    private String email;
    private String senha;
    private List<Object> jogosRealizados;

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isLoginAtivo() {
        return loginAtivo;
    }

    void setLoginAtivo(boolean loginAtivo) {
        this.loginAtivo = loginAtivo;
    }

    public long getUltimoTempoAtivo() {
        return ultimoTempoAtivo;
    }

    public short getTipoDeLogin() {
        return tipoDeLogin;
    }

    public String getNome() {
        return nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public List<Object> getJogosRealizados() {
        return jogosRealizados;
    }
}
