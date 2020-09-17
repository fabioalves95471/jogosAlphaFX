package descubraONumero;

import java.util.Calendar;

enum Operador {
	Adição, Subtração, Multiplicação, Divisão
}

public class ModelJogoDN {


//	private Collection<Questao> questao;

	private int id;
	private String nome;
	private int erros;
	private int acertos;
	private float acertosPorcentual;
	private int totalQuestoes;
	private int questaoCorrente;
	private Questao questao;
	private Tempo tempo;

	public ModelJogoDN (Operador operador, int totalQuestoes) {
		nome = "";
		erros = 0;
		acertos = 0;
		acertosPorcentual = 0.0f;
		this.totalQuestoes = totalQuestoes;
		questaoCorrente = 0;
		switch (operador) {
		case Adição:
			questao = new Adicao();
			break;
		case Subtração:
			questao = new Subtracao();
			break;
		case Multiplicação:
			questao = new Multiplicacao();
			break;
		case Divisão:
			questao = new Divisao();
			break;
		default:
			questao = new Adicao();
			break;
		}			
		proximaQuestao();
		tempo = new Tempo();
	}
	public boolean temProximaQuestao() {
		if (questaoCorrente >= totalQuestoes)
			return false;
		return true;
	}
	public void proximaQuestao() {
		if (questaoCorrente >= totalQuestoes)
			return;
		questaoCorrente++;
		questao.calculaQuestao();
	}
	public boolean verificaResposta(int y) {
		if (questao.y == y) {
			acertos++;
			return true;
		} else {
			erros++;
			return false;
		}
	}
	public void finalizaJogo() {
		acertosPorcentual = (float)acertos/totalQuestoes;
		tempo.calcFinalDeJogo();
	}
/*
	public Collection<Questao> getQuestao() {
		return questao;
	}
	public void setQuestao(Collection<Questao> questao) {
		this.questao = questao;
	}
*/
// - Getters e Setters --------------------------------------------
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQuestaoCorrente() {
		return questaoCorrente;
	}
	public void setQuestaoCorrente(int questaoCorrente) {
		this.questaoCorrente = questaoCorrente;
	}
	public Questao getQuestao() {
		return questao;
	}
	public void setQuestao(Questao questao) {
		this.questao = questao;
	}
	public Tempo getTempo() {
		return tempo;
	}
	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}
}
// -Classe não testada ------------------------------------------
class Tempo {
	Calendar inicio;
	Calendar fim;
	int tempoCorrente;
	String tempoFinalDeJogo;
	
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
	void calcFinalDeJogo() {
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
	String operadorNome;
	String operadorSinal;
	int x;
	int y;
	String resp;

	Questao () {}
	
	abstract void calculaQuestao();
	public String getPergunta() {
		return x+" "+operadorSinal+" ? = "+resp;
	}

	public String getOperadorNome() {
		return operadorNome;
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
		calculaQuestao();
		operadorNome = "Adição";
		operadorSinal = "+";
	}

	public void calculaQuestao() {
		x = (short)Math.floor(Math.random()*(10+1));
		y = (short)Math.floor(Math.random()*(10+1));
		resp = x + y + "";
	}
	public String toString() {
		return x+" + "+y+" = "+resp;
	}
}
// ---------------------------------------------------------
class Subtracao extends Questao {
	
	Subtracao () {
		calculaQuestao();
		operadorNome = "Subtração";
		operadorSinal = "-";
	}

	public void calculaQuestao() {
		x = (short)Math.floor(Math.random()*(10+1));
		y = (short)Math.floor(Math.random()*( x+1));
		resp = x - y + "";
	}
	public String toString() {
		return x+" - "+y+" = "+resp;
	}
}
// ---------------------------------------------------------
class Multiplicacao extends Questao {
	
	Multiplicacao () {
		calculaQuestao();
		operadorNome = "Multiplicação";
		operadorSinal = "x";
	}

	public void calculaQuestao() {
		x = (short)Math.floor(Math.random()*(4+1)+1);
		y = (short)Math.floor(Math.random()*(5+1));
		resp = x * y + "";
	}
	public String toString() {
		return x+" * "+y+" = "+resp;
	}
}
// ---------------------------------------------------------
class Divisao extends Questao {
	
	Divisao () {
		calculaQuestao();
		operadorNome = "Divisão";
		operadorSinal = "/";
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
	public String toString() {
		return x+" / "+y+" = "+resp;
	}
}
// ---------------------------------------------------------


