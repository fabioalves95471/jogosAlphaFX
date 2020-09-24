package com.fabiomalves.meusJogosFX.descubraONumero.service.enums;

public enum Operador {
	ADICAO("Adição", "+"), SUBTRACAO("Subtração", "-"), MULTIPLICACAO("Multiplicação", "x"), DIVISAO("Divisão", "/");
	private String nome;
	private String simbolo;
	Operador(String nm, String smb) {
		nome = nm;
		simbolo = smb;
	}
	public String getNome() {
		return nome;
	}
	public String getSimbolo() {
		return simbolo;
	}
}
