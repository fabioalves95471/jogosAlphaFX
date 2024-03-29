package com.fabiomalves.jogosAlphaFX.descubraONumero.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

import com.fabiomalves.jogosAlphaFX.descubraONumero.model.JogoDN;
import com.fabiomalves.jogosAlphaFX.descubraONumero.service.enums.Operador;

import com.fabiomalves.jogosAlphaFX.login.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceDN implements IServiceDN {

	private ListsJogos listsJogos;
	private ObservableList<JogoDN> listAtual;
	private Comparator<JogoDN> comparator;
	private JogoDN jogo;
	private Questao questao;
	private Tempo tempo;
	private int questaoCorrente;

	public ServiceDN () {
		listsJogos = new ListsJogos();
		comparator = new ComparatorJogoDN();
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
	public void iniciarJogoDN (String operadorNome, int totalQuestoes) {
		jogo = new JogoDN();
		jogo.setUsuarioNome(Login.getUsuario().getNome());
		jogo.setUsuarioId(Login.getUsuario().getUsuarioId());
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
	public void finalizaJogoDN() {
		jogo.setAcertosPorcentual((float)jogo.getAcertos()/jogo.getTotalQuestoes());
		tempo.finalizaTempo();
		jogo.setTempoFinalDeJogo(tempo.getTempoFinalDeJogo());
		listAtual = listsJogos.getListUsuario(jogo.getOperador());
		listAtual.add(jogo);
		ordenarLista(listAtual);
	}
	private void ordenarLista (ObservableList<JogoDN> ol) {
		ol.sort(comparator);
		for (int i=0; i<ol.size(); i++)
			ol.get(i).setPosicao(i+1);
	}
	public String getQuestaoString() { return questao.getQuestaoString();}
	public String getTempoFinalDeJogoStr() { return tempo.getTempoFinalDeJogoStr();}
	public ObservableList<JogoDN> getListUsuario (String operadorNome) { return listsJogos.getListUsuario(operadorNome);}
// Getters e Setters JogoDN ---
	public int getErros() { return jogo.getErros();}
	public int getAcertos() { return jogo.getAcertos();}
	public float getAcertosPorcentual() { return jogo.getAcertosPorcentual();}
	public int getQuestaoCorrente() { return questaoCorrente;};
	public int getTotalQuestoes() { return jogo.getTotalQuestoes();};
	public String getOperadorNome() {
		if (jogo == null)
			return Operador.ADICAO.getNome();
		return jogo.getOperador();
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
		if  ((fim-inicio) >= 3_600_000)
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
//---------------------------------------------------------
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
		x = (short)Math.floor((Math.random()*(10))+1);
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
//---------------------------------------------------------
class ListsJogos {

	private ObservableList<JogoDN>[] lists = new ObservableList[8];

	public ListsJogos () {
		for (short i=0; i<lists.length; i++) {
			lists[i] = FXCollections.observableArrayList();
		}
	}
	public ObservableList<JogoDN> getListUsuario (String operadorNome) {
		if		(Operador.ADICAO.getNome().equals(operadorNome))
			return lists[0];
		else if	(Operador.SUBTRACAO.getNome().equals(operadorNome))
			return lists[1];
		else if	(Operador.MULTIPLICACAO.getNome().equals(operadorNome))
			return lists[2];
		else if	(Operador.DIVISAO.getNome().equals(operadorNome))
			return lists[3];
		return null;
	}
}
//---------------------------------------------------------
class ComparatorJogoDN implements Comparator<JogoDN> {
	
	@Override
	public synchronized int compare (JogoDN jg1, JogoDN jg2) {
		if (jg1.getAcertosPorcentual() < jg2.getAcertosPorcentual()) return 1;
		else if (jg1.getAcertosPorcentual() > jg2.getAcertosPorcentual()) return -1;
		else if (jg1.getTotalQuestoes() < jg2.getTotalQuestoes()) return 1;
		else if (jg1.getTotalQuestoes() > jg2.getTotalQuestoes()) return -1;
		else if (jg1.getTempoFinalDeJogo().isBefore(jg2.getTempoFinalDeJogo())) return -1;
		else if (jg1.getTempoFinalDeJogo().isAfter(jg2.getTempoFinalDeJogo())) return 1;
		else return 0;
	}
}

