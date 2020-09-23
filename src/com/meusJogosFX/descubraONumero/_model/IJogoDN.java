package com.meusJogosFX.descubraONumero._model;

public interface IJogoDN {

	public boolean temProximaQuestao();
	public void rodaProximaQuestao();
	public boolean verificaResposta (int y);
	public void incrementaPontuacao (boolean resp);
	public void finalizaJogo();
	public String getOperadorNome();
	public String getQuestaoString();
	public String getTempoFinalDeJogo();
// Getters e Setters gerais ------
	public int getId();
	public String getNome();
	public int getErros();
	public int getAcertos();
	public float getAcertosPorcentual();
	public int getTotalQuestoes();
	public int getQuestaoCorrente();

}
