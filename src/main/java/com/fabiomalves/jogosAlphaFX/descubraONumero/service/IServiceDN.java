package com.fabiomalves.jogosAlphaFX.descubraONumero.service;

import com.fabiomalves.jogosAlphaFX.descubraONumero.model.IListsJogos;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.enums.Operador;

public interface IServiceDN {

	public int getAcertos();
	public float getAcertosPorcentual();
	public int getErros();
	public String getOperadorNome();
	public String getQuestaoString();
	public String getTempoFinalDeJogoStr();
	public IListsJogos getListsJogos();
	public void iniciarJogoDN(String operadorNome, int totalQuestoes);
	public void finalizaJogoDN();
	public void incrementaPontuacao (boolean resp);
	public void rodaProximaQuestao();
	public boolean temProximaQuestao();
	public boolean verificaResposta (int y);
	public static String[] getOperadorNomes() {
		Operador[] ops = Operador.values();
		String[] opsStr = new String[ops.length];
		for (int i=0; i<ops.length; i++) 
			opsStr[i] = ops[i].getNome();
		return opsStr;
	}
}
