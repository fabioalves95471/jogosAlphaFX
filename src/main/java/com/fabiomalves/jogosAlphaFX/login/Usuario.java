package com.fabiomalves.jogosAlphaFX.login;

import java.util.List;

public class Usuario {
    private boolean loginAtivo;
    private long ultimoTempoAtivo;
    private short tipoDeLogin;
    private String nome;
    private String email;
    private String senha;
    private List<Object> jogosRealizados;

    public boolean isLoginAtivo() {
        return loginAtivo;
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
