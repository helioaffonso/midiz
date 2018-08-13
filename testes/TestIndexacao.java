package testes;

import java.math.BigDecimal;
import java.sql.SQLException;

import kdTree.ArvoreKD;

import model.Indexacao;

public class TestIndexacao {

	
	public static void main( String[] args ) throws SQLException
	{
		/*Indexacao indexacao = new Indexacao();
		indexacao.indexaArquivos();
		*/
		testaIndex();
	}
		
	private static void testaIndex()
	{
		double d = 19.164;
		int decimalPlace = 2;
		BigDecimal bd = new BigDecimal( Double.toString(d) );
		bd = bd.setScale( decimalPlace, BigDecimal.ROUND_HALF_UP );
		
		double d1 = bd.doubleValue();
		System.out.println( d1 );
		
		/*double[] dPonto1 = {8,9};
		double[] dPonto2 = {6,3};
		double[] dPonto3 = {12,7};
		double[] dPonto4 = {7,9};
		double[] dPonto5 = {9,2};
		
		ArvoreKD arvore = new ArvoreKD(2);
		*/
		//arvore.insertIndice(dPonto1,"A");
		/*arvore.insertIndice(dPonto2,"Q");
		arvore.insertIndice(dPonto3,"C");
		arvore.insertIndice(dPonto4,"B");
		arvore.insertIndice(dPonto5,"F");*/
	}
	
	

}
