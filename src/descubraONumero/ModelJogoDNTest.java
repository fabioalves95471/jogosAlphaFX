package descubraONumero;

import java.util.Calendar;


public class ModelJogoDNTest {

	public static void main(String[] args) {
	
	try {
		ModelJogoDN jogo = new ModelJogoDN (Operador.Divisão, 3);
		Calendar t1 = jogo.getTempo().inicio;
		System.out.println(
			" ---Instancia-o-Jogo---"+
			"\n idJogo: "+jogo.getId()+
			"\n nome: "+jogo.getNome()+
			"\n erros: "+jogo.getErros()+
			"\n acertos: "+jogo.getAcertos()+
			"\n acertosPorcentual: "+jogo.getAcertosPorcentual()+
			"\n totalQuestoes: "+jogo.getTotalQuestoes()+
			"\n questaoCorrente: "+jogo.getQuestaoCorrente()+
			"\n Questao: "+jogo.getQuestao().toString()+
			"\n Tempo.inicio: "+t1.get(Calendar.HOUR_OF_DAY)+":"+t1.get(Calendar.MINUTE)+":"+t1.get(Calendar.SECOND)+"."+t1.get(Calendar.MILLISECOND)+" (hora atual)"+
			"\n Tempo.tempoCorrente*: "+jogo.getTempo().tempoCorrente+
			"\n Tempo.tempoFinalDeJogo: "+jogo.getTempo().tempoFinalDeJogo+
			"\n Tempo.atualizaDisplay: "+jogo.getTempo().atualizaDisplay((short)31)+" (valor fictício)");
	
		while (jogo.temProximaQuestao()) {
			jogo.proximaQuestao();
			System.out.println(
				"\n ---Gera "+jogo.getQuestaoCorrente()+"ª questao e atualiza tela---" +
				"\n idJogo: "+jogo.getId()+
				"\n nome: "+jogo.getNome()+
				"\n erros: "+jogo.getErros()+
				"\n acertos: "+jogo.getAcertos()+
				"\n acertosPorcentual: "+jogo.getAcertosPorcentual()+
				"\n totalQuestoes: "+jogo.getTotalQuestoes()+
				"\n questaoCorrente: "+jogo.getQuestaoCorrente()+
				"\n Questao: "+jogo.getQuestao().toString()+
				"\n Tempo.tempoCorrente*: "+jogo.getTempo().tempoCorrente+
				"\n Tempo.tempoFinalDeJogo: "+jogo.getTempo().tempoFinalDeJogo+
				"\n Tempo.atualizaDisplay: "+jogo.getTempo().atualizaDisplay((short)31)+" (valor fictício)"+
				"\n RESPOSTA DO USUARIO: 2");
			jogo.verificaResposta(2);
		}		
		Thread.sleep(10);
		jogo.finalizaJogo();
		Calendar t2 = jogo.getTempo().fim;
		System.out.println("\nJogo finalizado.");
		System.out.println("\nNOME DO USUARIO: JOAO");
		jogo.setNome("JOAO");
		System.out.println(
			"\n ---Dados finais a serem gravados---"+
			"\n idJogo: "+jogo.getId()+
			"\n nome: "+jogo.getNome()+
			"\n erros: "+jogo.getErros()+
			"\n acertos: "+jogo.getAcertos()+
			"\n acertosPorcentual: "+jogo.getAcertosPorcentual()+
			"\n totalQuestoes: "+jogo.getTotalQuestoes()+
			"\n questaoCorrente: "+jogo.getQuestaoCorrente()+
			"\n Questao: "+jogo.getQuestao().toString()+
			"\n Tempo.inicio: \t"+t1.get(Calendar.HOUR_OF_DAY)+":"+t1.get(Calendar.MINUTE)+":"+t1.get(Calendar.SECOND)+"."+t1.get(Calendar.MILLISECOND)+" (hora inicial)"+
			"\n Tempo.fim: \t"+t2.get(Calendar.HOUR_OF_DAY)+":"+t2.get(Calendar.MINUTE)+":"+t2.get(Calendar.SECOND)+"."+t2.get(Calendar.MILLISECOND)+" (hora final)"+
			"\n Tempo.tempoCorrente*: "+jogo.getTempo().tempoCorrente+
			"\n Tempo.tempoFinalDeJogo: "+jogo.getTempo().tempoFinalDeJogo+
			"\n Tempo.atualizaDisplay: "+jogo.getTempo().atualizaDisplay((short)1)+" (valor fictício)");
		System.out.println("\n-----fim-do-teste----");
		System.out.println("Tempo.inicio: "+t1.getTimeInMillis());
		System.out.println("Tempo.fim: "+t2.getTimeInMillis());

	} catch (InterruptedException e) {
		System.out.println("Erro de execução Thread: "+e);
	}
	}
}