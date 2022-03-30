package com.fabiomalves.jogosAlphaFX.descubraONumero.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class JogoDN {

	private int jogoId;
	private String usuarioNome;
	private long usuarioId;
	private String operador;
	private int erros;
	private int acertos;
	private float acertosPorcentual;
	private int totalQuestoes;
	private LocalTime tempoFinalDeJogo;
	private LocalDate dataHora;
	private int posicao;

	public JogoDN () {}

	public JogoDN(int jogoId, String usuarioNome, long usuarioId, String operador, int erros, int acertos, float acertosPorcentual,
				  int totalQuestoes, LocalTime tempoFinalDeJogo, LocalDate dataHora, int posicao) {
		super();
		this.jogoId = jogoId;
		this.usuarioNome = usuarioNome;
		this.usuarioId = usuarioId;
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
		return "JogoDN [id=" + jogoId + ", usuarioNome=" + usuarioNome + ", usuarioId=" + usuarioId + ", operador=" + operador + ",\terros=" + erros
				+ ", acertos=" + acertos + ", acertosPorcentual=" + acertosPorcentual + ",totalQuestoes="
				+ totalQuestoes + ", tempoFinalDeJogo=" + tempoFinalDeJogo + ", dataHora=" + dataHora + ", posicao="
				+ posicao + "]";
	}

// Getters: Especiais

	public String getPosicaoFormatada () {
		return posicao+"º";
	}

	public String getAcertosPorcentualFormatado () {
		short porcentual = (short)Math.rint(acertosPorcentual*100);
		if (porcentual < 10)
			return "  "+porcentual+"%";
		if (porcentual >=10 && porcentual <100)
			return " "+porcentual+"%";
		if (porcentual == 100)
			return ""+porcentual+"%";
		else
			return "valor desconhecido";
	}
	public String getTempoFinalDeJogoFormatado () {
		return tempoFinalDeJogo.toString().substring(3);
	}

// Getters e Setters: Padrões---

	public int getJogoId() {
		return jogoId;
	}

	public void setJogoId(int jogoId) {
		this.jogoId = jogoId;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public long getUsuarioId () {
		return usuarioId;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public void setUsuarioId (long usuarioId) {
		this.usuarioId = usuarioId;
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
