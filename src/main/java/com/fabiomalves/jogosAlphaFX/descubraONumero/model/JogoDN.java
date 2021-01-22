package com.fabiomalves.jogosAlphaFX.descubraONumero.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class JogoDN {

	private int id;
	private String usuario;
	private String operador;
	private int erros;
	private int acertos;
	private float acertosPorcentual;
	private int totalQuestoes;
	private LocalTime tempoFinalDeJogo;
	private LocalDate dataHora;
	private int posicao;

	public JogoDN () {}

	public JogoDN(int id, String usuario, String operador, int erros, int acertos, float acertosPorcentual,
			int totalQuestoes, LocalTime tempoFinalDeJogo, LocalDate dataHora, int posicao) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.operador = operador;
		this.erros = erros;
		this.acertos = acertos;
		this.acertosPorcentual = acertosPorcentual;
		this.totalQuestoes = totalQuestoes;
		this.tempoFinalDeJogo = tempoFinalDeJogo;
		this.dataHora = dataHora;
		this.posicao = posicao;
	}
	@Override
	public String toString() {
		return "JogoDN [id=" + id + ", usuario=" + usuario + ", operador=" + operador + ",\terros=" + erros
				+ ", acertos=" + acertos + ", acertosPorcentual=" + acertosPorcentual + ",totalQuestoes="
				+ totalQuestoes + ", tempoFinalDeJogo=" + tempoFinalDeJogo + ", dataHora=" + dataHora + ", posicao="
				+ posicao + "]";
	}

// Getters: Especiais

	public short getAcertosPorcentualFormatado () {
		return (short)(Math.rint(acertosPorcentual*100));
	}
	public String getTempoFinalDeJogoFormatado () {
		return tempoFinalDeJogo.toString().substring(3);
	}

// Getters e Setters: Padrões---

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public int getErros() {
		return erros;
	}

	public void setErros(int erros) {
		this.erros = erros;
	}

	public int getAcertos() {
		return acertos;
	}

	public void setAcertos(int acertos) {
		this.acertos = acertos;
	}

	public float getAcertosPorcentual() {
		return acertosPorcentual;
	}

	public void setAcertosPorcentual(float acertosPorcentual) {
		this.acertosPorcentual = acertosPorcentual;
	}

	public int getTotalQuestoes() {
		return totalQuestoes;
	}

	public void setTotalQuestoes(int totalQuestoes) {
		this.totalQuestoes = totalQuestoes;
	}

	public LocalTime getTempoFinalDeJogo() {
		return tempoFinalDeJogo;
	}

	public void setTempoFinalDeJogo(LocalTime tempoFinalDeJogo) {
		this.tempoFinalDeJogo = tempoFinalDeJogo;
	}

	public LocalDate getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDate dataHora) {
		this.dataHora = dataHora;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

}
