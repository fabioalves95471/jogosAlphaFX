package com.fabiomalves.jogosAlphaFX.descubraONumero.service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.fabiomalves.jogosAlphaFX.descubraONumero.model.JogoDN;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.enums.Operador;

public class ServiceDN implements IServiceDN {

	private int questaoCorrente;
	private JogoDN jogo;
	private Questao questao;
	private Tempo tempo;

	public ServiceDN (String operadorNome, int totalQuestoes) {
		jogo = new JogoDN();
		jogo.setOperador(operadorNome);
		jogo.setErros(0);
		jogo.setAcertos(0);
		jogo.setAcertosPorcentual(0.0f);
		jogo.setTotalQuestoes(totalQuestoes);
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
		if (questaoCorrente >= jogo.getTotalQuestoes())
			return false;
		return true;
	}
	public void rodaProximaQuestao() {
		if (questaoCorrente >= jogo.getTotalQuestoes())
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
		int erros = jogo.getErros();
		int acertos = jogo.getAcertos();
		if (questaoCorrente > ( erros + acertos)) {
			if (resp)
				jogo.setAcertos(++acertos);
			else
				jogo.setErros(++erros);
		}
	}
	public void finalizaJogoDN() {
		jogo.setAcertosPorcentual((float)jogo.getAcertos()/jogo.getTotalQuestoes());
		tempo.finalizaTempo();
		jogo.setUsuario("Visitante");
		jogo.setTempoFinalDeJogo(tempo.getTempoFinalDeJogo());
		jogo.printValores();
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
	public String getTempoFinalDeJogoStr() {
		return tempo.getTempoFinalDeJogoStr();
	}

// Getters e Setters JogoDN ---
	public int getErros() {
		return jogo.getErros();
	}
	public int getAcertos() {
		return jogo.getAcertos();
	}
	public float getAcertosPorcentual() {
		return jogo.getAcertosPorcentual();
	}
}
//---------------------------------------------------------
class Tempo {
	private Long inicio;
	private Long fim;
	private int tempoFinalDeJogoInt;
	private LocalTime tempoFinalDeJogo;
	
	Tempo () {
		inicio = fim = System.currentTimeMillis();
		tempoFinalDeJogo = LocalTime.of(0,0);
	}
	public void finalizaTempo() {
		fim = System.currentTimeMillis();
		if ((fim-inicio) >= 3_600_000)
			tempoFinalDeJogoInt = 3_599_999;
		else
			tempoFinalDeJogoInt = (int)(fim-inicio);
		tempoFinalDeJogo = tempoFinalDeJogo.plus(tempoFinalDeJogoInt, ChronoUnit.MILLIS);
	}
	public String getTempoFinalDeJogoStr () {
		return tempoFinalDeJogo.toString().substring(3);
	}
	public LocalTime getTempoFinalDeJogo () {
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
