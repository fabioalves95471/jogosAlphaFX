package com.fabiomalves.jogosAlphaFX.descubraONumero.service;

public interface IServiceDN {

	public void finalizaJogoDN();
	public int getAcertos();
	public float getAcertosPorcentual();
	public int getErros();
	public String getOperadorNome();
	public String getQuestaoString();
	public String getTempoFinalStr();
	public void incrementaPontuacao (boolean resp);
	public void rodaProximaQuestao();
	public boolean temProximaQuestao();
	public boolean verificaResposta (int y);

}
