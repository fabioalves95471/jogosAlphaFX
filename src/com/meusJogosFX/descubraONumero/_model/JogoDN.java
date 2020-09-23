package com.meusJogosFX.descubraONumero._model;

import java.util.Calendar;

enum Operador {
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

public class JogoDN implements IJogoDN {

	private int id;
	private String nome;
	private int erros;
	private int acertos;
	private float acertosPorcentual;
	private int totalQuestoes;
	private int questaoCorrente;
	private Questao questao;
	private Tempo tempo;

	public JogoDN (String operadorNome, int totalQuestoes) {
		nome = "";
		erros = 0;
		acertos = 0;
		acertosPorcentual = 0.0f;
		this.totalQuestoes = totalQuestoes;
		questaoCorrente = 0;
		if 		(Operador.ADICAO.getNome().equals(operadorNome))
			questao = new Adicao();
		else if (Operador.SUBTRACAO.getNome().equals(operadorNome))
			questao = new Subtracao();
		else if (Operador.MULTIPLICACAO.getNome().equals(operadorNome))
			questao = new Multiplicacao();
		else if (Operador.DIVISAO.getNome().equals(operadorNome))
			questao = new Divisao();
		else 
			questao = new Adicao();
		rodaProximaQuestao();
		tempo = new Tempo();
	}
	public boolean temProximaQuestao() {
		if (questaoCorrente >= totalQuestoes)
			return false;
		return true;
	}
	public void rodaProximaQuestao() {
		if (questaoCorrente >= totalQuestoes)
			return;
		questaoCorrente++;
		questao.calculaQuestao();
	}
	public boolean verificaResposta (int y) {
		if (questao.y == y)
			return true;
		return false;
	}
	public void incrementaPontuacao (boolean resp) {
		if (questaoCorrente > (erros + acertos)) {
			if (resp)
				acertos++;
			else
				erros++;
		}
	}
	public void finalizaJogo() {
		acertosPorcentual = (float)acertos/totalQuestoes;
		tempo.calcFinalDeJogo();
	}
	public String getOperadorNome() {
		return questao.getOperador().getNome();
	}
	public static String[] getOperadorNomes() {
		Operador[] ops = Operador.values();
		String[] opsStr = new String[ops.length];
		for (int i=0; i<ops.length; i++) 
			opsStr[i] = ops[i].getNome();
		return opsStr;
	}
	public String getQuestaoString() {
		return questao.getQuestaoString();
	}
	public String getTempoFinalDeJogo() {
		return tempo.getTempoFinalDeJogo();
	}

// Getters e Setters atributos ---
	public int getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public int getErros() {
		return erros;
	}
	public int getAcertos() {
		return acertos;
	}
	public float getAcertosPorcentual() {
		return acertosPorcentual;
	}
	public int getTotalQuestoes() {
		return totalQuestoes;
	}
	public int getQuestaoCorrente() {
		return questaoCorrente;
	}
}
//---------------------------------------------------------
class Tempo {
	private Calendar inicio;
	private Calendar fim;
	private int tempoCorrente;
	private String tempoFinalDeJogo;
	
	Tempo () {
		inicio = Calendar.getInstance();
		tempoCorrente = 0;
		tempoFinalDeJogo = "00:00.000";
	}
	public String atualizaDisplay (short segundos) {
		tempoCorrente += segundos;
		if (segundos >= 3600)
			return "00:00";
		byte min = (byte)(tempoCorrente/60);
		byte sec = (byte)(tempoCorrente%60);
		String minStr, secStr;
		if (min < 10) 	minStr = "0"+min;
		else 			minStr = "" +min;
		if (sec < 10) 	secStr = "0"+sec;
		else 			secStr = "" +sec;
		return minStr+":"+secStr;
	}
	public void calcFinalDeJogo() {
		fim = Calendar.getInstance();
		long agora = fim.getTimeInMillis() -inicio.getTimeInMillis();
		if (agora >= 3_600_000)
			agora = 3_599_999;
		int mil = (int)agora%1000;
		int sec = ((int)agora/1000)%60;
		int min = (((int)agora/1000)%(60*60))/60;
		String minStr, secStr, milStr;
		if (min < 10) 	minStr = "0"+min;
		else 			minStr = "" +min;
		if (sec < 10) 	secStr = "0"+sec;
		else 			secStr = "" +sec;
		if (100>mil && mil>=10) milStr = "0" +mil;
		else if (mil<10)		milStr = "00"+mil;
		else 					milStr = ""  +mil;
		tempoFinalDeJogo = minStr+":"+secStr+"."+milStr;
	}
	public Calendar getInicio() {
		return inicio;
	}
	public Calendar getFim() {
		return fim;
	}
	public String getTempoFinalDeJogo() {
		return tempoFinalDeJogo;
	}
}
// ---------------------------------------------------------
abstract class Questao {

	Operador op;
	int x;
	int y;
	String resp;

	Questao () {}
	
	public abstract void calculaQuestao();

	public String getQuestaoString() {
		return x+" "+op.getSimbolo()+" ? = "+resp;
	}
	public Operador getOperador() {
		return op;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getResp() {
		return resp;
	}
}

// ---------------------------------------------------------
class Adicao extends Questao {

	Adicao () {
		op = Operador.ADICAO;
		calculaQuestao();
	}

	public void calculaQuestao() {
		x = (short)Math.floor(Math.random()*(10+1));
		y = (short)Math.floor(Math.random()*(10+1));
		resp = x + y + "";
	}
}
// ---------------------------------------------------------
class Subtracao extends Questao {
	
	Subtracao () {
		op = Operador.SUBTRACAO;
		calculaQuestao();
	}

	public void calculaQuestao() {
		x = (short)Math.floor(Math.random()*(10+1));
		y = (short)Math.floor(Math.random()*( x+1));
		resp = x - y + "";
	}
}
// ---------------------------------------------------------
class Multiplicacao extends Questao {
	
	Multiplicacao () {
		op = Operador.MULTIPLICACAO;
		calculaQuestao();
	}

	public void calculaQuestao() {
		x = (short)Math.floor(Math.random()*(4+1)+1);
		y = (short)Math.floor(Math.random()*(5+1));
		resp = x * y + "";
	}
}
// ---------------------------------------------------------
class Divisao extends Questao {
	
	Divisao () {
		op = Operador.DIVISAO;
		calculaQuestao();
	}
	public void calculaQuestao() {
		x = (short)Math.floor(Math.random()*(20+1));
		y = (short)Math.floor(Math.random()*(x+1));
		if (y == 0)
		resp = "Não existe";
		else {
			byte direcao = (byte)Math.floor(Math.random()*2);
			if (direcao == 0)
				while (x % y != 0)
					y--;
			else
				while (x % y != 0)
					y++;
			resp = String.valueOf(x/y);
		}
	}
}
// ---------------------------------------------------------


