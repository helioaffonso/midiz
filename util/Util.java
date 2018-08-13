package util;

import java.io.File;

import model.vo.Ponto;
import model.vo.PontoNotaDuracao;

public class Util {

	public static String[] buscaAquivosPasta( String sPasta )
	{
		File f = new File(sPasta);
		File files[] = f.listFiles();

		String[] listaArquivos = new String[files.length];
		
		for( int i=0; i < files.length; i++ )
		{
			if( files[i].isFile() )
			{
				listaArquivos[i] = files[i].getAbsolutePath();
			}
		}
		
		//TODO: Fazer teste com tamanho de listaArquivos 
		
		return listaArquivos;
	}
	
	public static double distanciaEuclidiana(Ponto p1, PontoNotaDuracao p2, boolean isNota)
	{
		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		double d4 = 0;
		double d5 = 0;
		double d6 = 0;
		double d7 = 0;
		double dist = 0;
		
		if( isNota )
		{
			Ponto pontoNota = p2.getPontoNota();
			
			d1 = p1.getCoord1() - pontoNota.getCoord1();
			d2 = p1.getCoord2() - pontoNota.getCoord2();
			d3 = p1.getCoord3() - pontoNota.getCoord3();
			d4 = p1.getCoord4() - pontoNota.getCoord4();
			d5 = p1.getCoord5() - pontoNota.getCoord5();
			d6 = p1.getCoord6() - pontoNota.getCoord6();
			d7 = p1.getCoord7() - pontoNota.getCoord7();
		}
		else
		{
			Ponto pontoDuracao = p2.getPontoDuracao();
			
			d1 = p1.getCoordDuracao1() - pontoDuracao.getCoordDuracao1();
			d2 = p1.getCoordDuracao2() - pontoDuracao.getCoordDuracao2();
			d3 = p1.getCoordDuracao3() - pontoDuracao.getCoordDuracao3();
			d4 = p1.getCoordDuracao4() - pontoDuracao.getCoordDuracao4();
			d5 = p1.getCoordDuracao5() - pontoDuracao.getCoordDuracao5();
			d6 = p1.getCoordDuracao6() - pontoDuracao.getCoordDuracao6();
			d7 = p1.getCoordDuracao7() - pontoDuracao.getCoordDuracao7();			
		}
		
		dist = Math.sqrt( d1*d1 + d2*d2 + d3*d3 + d4*d4 + d5*d5 + d6*d6 + d7*d7 );
		
		return dist;
	}
}
