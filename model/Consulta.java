package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kdTree.ArvoreKD;

import model.vo.Entrada;
import model.vo.Ponto;
import model.vo.PontoNotaDuracao;
import util.Util;

public class Consulta {
	
	/*#model.AcessoTabelas Dependency20*/

/*#model.Wavelet Dependency201*/
/*#model.vo.PontoNotaDuracao Dependency202*/


// TODO: ponto do tipo duracao
	public List realizaConsulta(ArvoreKD arvoreNotas, ArvoreKD arvoreDuracoes, Entrada entrada )
	{
		List listaResultados = new ArrayList();
		List listaPontosDuracao = new ArrayList();
		List listaMerge = new ArrayList();
		
		Ponto pontoTipoNota = getPontoTipoNota(entrada);
		Ponto pontoTipoDuracao = new Ponto();
		
		// TODO: num de vizinhos. 
		List listaPontosNota = arvoreNotas.nearest(pontoTipoNota, 1 ,true);
		
		if( entrada.possuiDuracao )
		{
			pontoTipoDuracao = getPontoTipoDuracao(entrada);
			listaPontosDuracao = arvoreDuracoes.nearest(pontoTipoDuracao, entrada.getNumVizinhos(),false);
		}
		
		AcessoTabelas acessoTabelas = new AcessoTabelas();
		
		try
		{
			if( entrada.possuiDuracao )
			{
				listaMerge = mergeResultados(listaPontosNota, listaPontosDuracao);
				
				// TODO #1 Gerar a transformada da entrada!
				listaMerge = ordenaPorSimilaridade( listaMerge, 
													pontoTipoNota, 
													pontoTipoDuracao,
													entrada.getPesoNota(),
													entrada.getPesoDuracao(),
													entrada.possuiDuracao ); 
				
				for( int i = 0; i < listaMerge.size(); i++ )
				{
					Ponto ponto = new Ponto();
					
					ponto = (Ponto) listaMerge.get(i);
					listaResultados.addAll(acessoTabelas.buscaPontoMusica(ponto));
				}
			}
			// TODO verificar se precisa ordenar quando a consulta não é por duração. 
			// SIM! Precisa fazer ainda! 
			else
			{
				listaResultados = acessoTabelas.buscaPontoMusica(listaPontosNota);
			}
			
		}
		catch( SQLException e )
		{
			System.out.println(e.getMessage());
		}

		return listaResultados;
	}
	
	/**
	 * Calcula o coeficiente de similaridade e ordena os resultados obtidos 
	 * @param listaResultados
	 * @return
	 */
	
	private List ordenaPorSimilaridade( List listaResultados, 
										Ponto pontoBuscaNota, 
										Ponto pontoBuscaDuracao,
										double pesoNota,
										double pesoDuracao,
										boolean possuiDuracao )
	{
		PontoNotaDuracao pNotaDuracao;
		List listaOrdenada = new ArrayList();
		Ponto pontoResultado;
		
		double pitchDist = 0;
		double durationDist = 0;
		double coeficiente = 0;
		
		if( listaResultados != null && listaResultados.size() > 0 )
		{
			for( int i = 0; i < listaResultados.size(); i++ )
			{
				pNotaDuracao = new PontoNotaDuracao();
				pNotaDuracao = (PontoNotaDuracao) listaResultados.get(i);
				
				pitchDist = Util.distanciaEuclidiana(pontoBuscaNota, pNotaDuracao, true);
				durationDist = Util.distanciaEuclidiana(pontoBuscaDuracao, pNotaDuracao, false);
				
				coeficiente = calculaCoeficiente( pitchDist,durationDist,pesoNota,pesoDuracao );

				pontoResultado = new Ponto();
				pontoResultado.setIdPonto(pNotaDuracao.getIdNota());
				pontoResultado.setSimilaridade(coeficiente);
				
				listaOrdenada.add( pontoResultado );
			}
				
		}
		
		Collections.sort(listaOrdenada);
		
		return listaOrdenada;
	}
	
	private double calculaCoeficiente(  double pitchDist,
										double durationDist,
										double pesoNota,
										double pesoDuracao )
	{
		double resultPitch = pesoNota * pitchDist * pitchDist;
		double resultDuration = pesoDuracao * durationDist * durationDist;
		
		return Math.sqrt(resultPitch + resultDuration);
	}
	
	private List mergeResultados( List aListaPontosNota, List aListaPontosDuracao )
	{
		PontoNotaDuracao pontoNota;
		PontoNotaDuracao pontoDuracao;
		Ponto pontoTemp;
		PontoNotaDuracao pNotaDuracao; 
		List listaDuracao = new ArrayList();
		List listaNota = new ArrayList();
		List listaResult = new ArrayList();

		AcessoTabelas acessoTabelas = new AcessoTabelas();
		
		try
		{
			// TODO verificar itens repetidos na lista retornada por mergeResultados()
			listaNota = acessoTabelas.buscaPontosDuracao(aListaPontosNota);
			listaDuracao = acessoTabelas.buscaReferenciaPontosDuracao(aListaPontosDuracao);
			
			/*
			for( int j = 0; j < aListaPontosDuracao.size(); j++ )
			{
				// busca os pontos das notas correspondentes as durações
				pontoDuracao = new Ponto();
				pontoTemp = new Ponto();
				
				pontoDuracao = (Ponto) aListaPontosDuracao.get(j);
				
				pontoTemp = acessoTabelas.buscaReferenciaPontosDuracao(pontoDuracao.getIdPontoRef()); 
				
				pontoDuracao.setIdPonto( pontoTemp.getIdPonto() );
				pontoDuracao.setIdPontoRef( pontoTemp.getIdPontoRef() );

				listaNotas.add(pontoDuracao);
			}*/
		}
		catch( SQLException e )
		{
			System.out.println(e.getMessage());
		}
		
		listaResult.addAll(listaNota);
		listaResult.addAll(listaDuracao);
		
		return listaResult;
	}
	
	private Ponto getPontoTipoNota(Entrada entrada)
	{
		Ponto pontoNota = new Ponto();
		
		Wavelet wavelet = new Wavelet();
		
		int[] ivNotasEntrada = new int[8];
		
		ivNotasEntrada[0] = Integer.parseInt(entrada.getN1());
		ivNotasEntrada[1] = Integer.parseInt(entrada.getN2());
		ivNotasEntrada[2] = Integer.parseInt(entrada.getN3());
		ivNotasEntrada[3] = Integer.parseInt(entrada.getN4());
		ivNotasEntrada[4] = Integer.parseInt(entrada.getN5());
		ivNotasEntrada[5] = Integer.parseInt(entrada.getN6());
		ivNotasEntrada[6] = Integer.parseInt(entrada.getN7());
		ivNotasEntrada[7] = Integer.parseInt(entrada.getN8());
		
		pontoNota = wavelet.transformadaPonto(ivNotasEntrada);
		
		return pontoNota;
	}
	
	private Ponto getPontoTipoDuracao(Entrada entrada)
	{
		Ponto pontoDuracao = new Ponto();
		
		Wavelet wavelet = new Wavelet();
		
		int[] ivDuracaoEntrada = new int[8];
		
		ivDuracaoEntrada[0] = (int) (Double.parseDouble(entrada.getD1()) * 64);
		ivDuracaoEntrada[1] = (int) (Double.parseDouble(entrada.getD2()) * 64);
		ivDuracaoEntrada[2] = (int) (Double.parseDouble(entrada.getD3()) * 64);
		ivDuracaoEntrada[3] = (int) (Double.parseDouble(entrada.getD4()) * 64);
		ivDuracaoEntrada[4] = (int) (Double.parseDouble(entrada.getD5()) * 64);
		ivDuracaoEntrada[5] = (int) (Double.parseDouble(entrada.getD6()) * 64);
		ivDuracaoEntrada[6] = (int) (Double.parseDouble(entrada.getD7()) * 64);
		ivDuracaoEntrada[7] = (int) (Double.parseDouble(entrada.getD8()) * 64);
		
		pontoDuracao = wavelet.transformadaPonto(ivDuracaoEntrada);
		
		return pontoDuracao;
	}
}
