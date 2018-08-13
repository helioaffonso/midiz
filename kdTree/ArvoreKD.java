package kdTree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Constantes;
import model.vo.Ponto;

public class ArvoreKD extends KDTree implements Serializable{

	/*#model.vo.Ponto Dependency20*/
private static final long serialVersionUID = -1L;
	public  static final int k = 0;	
	
	public ArvoreKD()
	{
		super( Constantes.JANELA -1 );
	}
	
	public ArvoreKD(int k)
	{
		super(k);
	}

	public void insert( Ponto ponto, boolean isTipoNota )
	{
		// TODO: generalizar usando k
		double[] dvPontos = new double[7];
		
		if( isTipoNota )
		{
			dvPontos[0] = ponto.getCoord1();
			dvPontos[1] = ponto.getCoord2();
			dvPontos[2] = ponto.getCoord3();
			dvPontos[3] = ponto.getCoord4();
			dvPontos[4] = ponto.getCoord5();
			dvPontos[5] = ponto.getCoord6();
			dvPontos[6] = ponto.getCoord7();
		}
		else
		{
			dvPontos[0] = ponto.getCoordDuracao1();
			dvPontos[1] = ponto.getCoordDuracao2();
			dvPontos[2] = ponto.getCoordDuracao3();
			dvPontos[3] = ponto.getCoordDuracao4();
			dvPontos[4] = ponto.getCoordDuracao5();
			dvPontos[5] = ponto.getCoordDuracao6();
			dvPontos[6] = ponto.getCoordDuracao7();			
		}
		
		try
		{
			int iId = isTipoNota ? (ponto.getIdPontoRef() == 0 ? ponto.getIdPonto() : ponto.getIdPontoRef()) : ponto.getIdPonto();
			super.insert(dvPontos, new Integer(iId));
		}
		catch( KeySizeException e )
		{
			System.out.println( "Erro ao indexar ponto: Tamanho da chave inválido. Chave: " + ponto.getIdPonto() );
		}
		catch( KeyDuplicateException e )
		{
			//System.out.println( "Erro ao indexar ponto: Item duplicado" );
		}
	}
	
	public List nearest(Ponto ponto, int numResultados, boolean isTipoNota)
	{
		List listaPontos = new ArrayList();
		
		// TODO: generalizar usando k
		double[] dvPontos = new double[7];
		
		if( isTipoNota )
		{
			dvPontos[0] = ponto.getCoord1();
			dvPontos[1] = ponto.getCoord2();
			dvPontos[2] = ponto.getCoord3();
			dvPontos[3] = ponto.getCoord4();
			dvPontos[4] = ponto.getCoord5();
			dvPontos[5] = ponto.getCoord6();
			dvPontos[6] = ponto.getCoord7();
		}
		else
		{
			dvPontos[0] = ponto.getCoordDuracao1();
			dvPontos[1] = ponto.getCoordDuracao2();
			dvPontos[2] = ponto.getCoordDuracao3();
			dvPontos[3] = ponto.getCoordDuracao4();
			dvPontos[4] = ponto.getCoordDuracao5();
			dvPontos[5] = ponto.getCoordDuracao6();
			dvPontos[6] = ponto.getCoordDuracao7();			
		}
				
		try
		{
			Object[] objs = super.nearest(dvPontos, numResultados);

			if( objs != null && objs.length > 0 )
			{
				for( int i = 0; i < objs.length; i++ )
				{
					listaPontos.add( (Integer) objs[i] );
				}
			}
		}
		catch( KeySizeException e )
		{
			System.out.println( "Erro ao buscar ponto: Tamanho da chave inválido. Chave: " + ponto.getIdPonto() );
		}
		
		return listaPontos;
	}
	
	
}