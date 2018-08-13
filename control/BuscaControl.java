package control;

import java.util.List;

import kdTree.ArvoreKD;

import exception.EntradaInvalidaException;


import model.Consulta;
import model.vo.Entrada;

public class BuscaControl {

	/*#model.Consulta Dependency20*/
ArvoreKD arvoreNotas;
	ArvoreKD arvoreDuracoes;
	
	public BuscaControl(ArvoreKD arvoreN, ArvoreKD arvoreD )
	{
		this.arvoreNotas = arvoreN;
		this.arvoreDuracoes = arvoreD;
	}
	
	// TODO: realizar validações da Entrada
	public List realizaConsulta(Entrada entrada) throws EntradaInvalidaException
	{
		System.out.println("realizaConsulta...");
		List listaResultados;
		
		if( entrada.validate() )
		{
			Consulta consulta = new Consulta();
			
			listaResultados = consulta.realizaConsulta(arvoreNotas, arvoreDuracoes, entrada);
		}
		else
			throw new EntradaInvalidaException();
		
		return listaResultados;
	}
}
