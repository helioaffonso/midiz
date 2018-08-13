package model;

import java.util.ArrayList;
import java.util.List;

import model.vo.Nota;
import model.vo.Ponto;

public class Wavelet {

	/*#model.vo.Nota Dependency20*/
	/*#model.vo.Ponto Dependency201*/

	// TODO: Transformada de durações
	private static int[] transformada(int[] ivNotas) {
		int[] ivPrimeiro = new int[Constantes.JANELA];
		int[] ivSegundo = new int[Constantes.JANELA / 2];
		int[] ivTerceiro = new int[Constantes.JANELA / 4];
		int[] ivTodos = new int[((2 ^ Constantes.JANELA) - 1) * 2];
		int[] ivRetorno = new int[Constantes.JANELA - 1];

		ivPrimeiro = calcularIntervalos(ivNotas);
		ivSegundo = segundoPasso(ivPrimeiro, ivSegundo.length);
		ivTerceiro = segundoPasso(ivSegundo, ivTerceiro.length);

		int iIndexTodos = 0;
		for (int i = 0; i < ivPrimeiro.length; i++) {
			ivTodos[iIndexTodos] = ivPrimeiro[i];
			iIndexTodos++;
		}
		for (int i = 0; i < ivSegundo.length; i++) {
			ivTodos[iIndexTodos] = ivSegundo[i];
			iIndexTodos++;
		}
		for (int i = 0; i < ivTerceiro.length; i++) {
			ivTodos[iIndexTodos] = ivTerceiro[i];
			iIndexTodos++;
		}

		iIndexTodos--;
		for (int i = 0; i < ivRetorno.length; i++) {
			ivRetorno[i] = ivTodos[iIndexTodos] - ivTodos[iIndexTodos - 1];
			iIndexTodos -= 2;
		}

		return finalizaTransformada(ivRetorno);
	}

	public Ponto transformadaPonto(int[] ivNotas) {
		Ponto ponto = new Ponto();

		int[] ivResultado = transformada(ivNotas);

		ponto.setCoord1(ivResultado[0]);
		ponto.setCoord2(ivResultado[1]);
		ponto.setCoord3(ivResultado[2]);
		ponto.setCoord4(ivResultado[3]);
		ponto.setCoord5(ivResultado[4]);
		ponto.setCoord6(ivResultado[5]);
		ponto.setCoord7(ivResultado[6]);

		return ponto;
	}

	/**
	 * Primeiro passo do algoritmo de Beeferman
	 *
	 * @param ivNotas
	 * @return
	 */
	private static int[] calcularIntervalos(int[] ivNotas) {
		int[] ivTemp = new int[Constantes.JANELA];
		int iPrimeiro;

		if (ivNotas.length == Constantes.JANELA) {
			ivTemp = new int[Constantes.JANELA];
			iPrimeiro = ivNotas[0];

			for (int i = 0; i < ivTemp.length; i++) {
				ivTemp[i] = ivNotas[i] - iPrimeiro + 1;
			}
		}
		return ivTemp;
	}

	/**
	 *
	 * @param ivNotas
	 * @return
	 */
	private static int[] segundoPasso(int[] ivNotas, int iTamVetor) {
		int[] ivRetorno = new int[iTamVetor];
		int indexRetorno = 0;

		for (int i = 0; i < ivNotas.length - 1; i += 2) {
			ivRetorno[indexRetorno] = ivNotas[i] + ivNotas[i + 1];
			indexRetorno++;
		}

		return ivRetorno;
	}

	/**
	 *
	 * @param ivNotas
	 * @return
	 */
	private static int[] finalizaTransformada(int[] ivNotas) {
		for (int i = 0; i < ivNotas.length; i++)
			ivNotas[i] = -ivNotas[i];

		return ivNotas;
	}

	public static List montaListaPontos(List listaNotas) {
		List listaPontos = new ArrayList();
		Ponto pontoNota;
		Nota nota1, nota2, nota3, nota4, nota5, nota6, nota7, nota8;

		// TODO replace 8 por JANELA
		int[] dvDuracoes = new int[8];
		int[] dvDuracoesTransformada = new int[7];
		int[] ivNotas = new int[8];
		int[] ivNotasTransformada = new int[7];

		for (int i = 0; i < listaNotas.size() - 8; i++) {
			pontoNota = new Ponto();

			// TODO Refactoring -> extract method
			nota1 = (Nota) listaNotas.get(i);
			nota2 = (Nota) listaNotas.get(i + 1);
			nota3 = (Nota) listaNotas.get(i + 2);
			nota4 = (Nota) listaNotas.get(i + 3);
			nota5 = (Nota) listaNotas.get(i + 4);
			nota6 = (Nota) listaNotas.get(i + 5);
			nota7 = (Nota) listaNotas.get(i + 6);
			nota8 = (Nota) listaNotas.get(i + 7);

			ivNotas[0] = nota1.getNota();
			ivNotas[1] = nota2.getNota();
			ivNotas[2] = nota3.getNota();
			ivNotas[3] = nota4.getNota();
			ivNotas[4] = nota5.getNota();
			ivNotas[5] = nota6.getNota();
			ivNotas[6] = nota7.getNota();
			ivNotas[7] = nota8.getNota();

			System.arraycopy(transformada(ivNotas), 0, ivNotasTransformada, 0,
					7);

			pontoNota.setCoord1(ivNotasTransformada[0]);
			pontoNota.setCoord2(ivNotasTransformada[1]);
			pontoNota.setCoord3(ivNotasTransformada[2]);
			pontoNota.setCoord4(ivNotasTransformada[3]);
			pontoNota.setCoord5(ivNotasTransformada[4]);
			pontoNota.setCoord6(ivNotasTransformada[5]);
			pontoNota.setCoord7(ivNotasTransformada[6]);
			pontoNota.setTipoPonto(Constantes.PONTO_TIPO_NOTA);

			// Pontos de Duração
			dvDuracoes[0] = nota1.getDuracao();
			dvDuracoes[1] = nota2.getDuracao();
			dvDuracoes[2] = nota3.getDuracao();
			dvDuracoes[3] = nota4.getDuracao();
			dvDuracoes[4] = nota5.getDuracao();
			dvDuracoes[5] = nota6.getDuracao();
			dvDuracoes[6] = nota7.getDuracao();
			dvDuracoes[7] = nota8.getDuracao();

			System.arraycopy(transformada(dvDuracoes), 0,
					dvDuracoesTransformada, 0, 7);

			pontoNota.setCoordDuracao1(dvDuracoesTransformada[0]);
			pontoNota.setCoordDuracao2(dvDuracoesTransformada[1]);
			pontoNota.setCoordDuracao3(dvDuracoesTransformada[2]);
			pontoNota.setCoordDuracao4(dvDuracoesTransformada[3]);
			pontoNota.setCoordDuracao5(dvDuracoesTransformada[4]);
			pontoNota.setCoordDuracao6(dvDuracoesTransformada[5]);
			pontoNota.setCoordDuracao7(dvDuracoesTransformada[6]);

			listaPontos.add(pontoNota);
		}

		return listaPontos;
	}

}
