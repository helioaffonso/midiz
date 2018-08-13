package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import midi.TabelaMidi;
import model.vo.ArquivoMidi;
import model.vo.Musica;
import model.vo.Nota;
import model.vo.Ponto;
import model.vo.PontoNotaDuracao;
import model.vo.Resultado;

public class AcessoTabelas extends AcessoAoBanco{

	/*#midi.TabelaMidi Dependency20*/



/*#model.vo.PontoNotaDuracao Dependency201*/


/*#model.vo.Nota Dependency202*/

/*#model.vo.Musica Dependency203*/
/*#model.vo.Ponto Dependency204*/




/**
	 * 
	 * @param arquivoMidi
	 * @throws SQLException
	 */
	public List insereArquivoMidi( ArquivoMidi arquivoMidi, List listaPontos ) throws SQLException
	{
		Connection conexao = getConexao();
		int idMusica = 0;
		
		List listaIdPontosNovos = new ArrayList();
		List listaNotas = new ArrayList();
		
		try
		{
			conexao.setAutoCommit(false);
			Statement stmt = conexao.createStatement();

			insereMusica( arquivoMidi.getMusica(), stmt );

			ResultSet rs = stmt.executeQuery("SELECT @@IDENTITY");

			if (rs.next())
			{
				idMusica = rs.getInt(1);
			}

			listaNotas = insereNotas( arquivoMidi.getListaNotas(), stmt, idMusica );
			listaIdPontosNovos = inserePontos( listaPontos, listaNotas, idMusica, stmt ); 
			
			conexao.commit();
		}
		catch( SQLException e )
		{
			System.out.println( e );
		}
		finally
		{
			if( !conexao.isClosed() )
				conexao.close();
		}
		
		return listaIdPontosNovos;
	}
	
	/**
	 * 
	 * @param musica
	 * @param stmt
	 * @throws SQLException
	 */
	public void insereMusica( Musica musica, Statement stmt ) throws SQLException
	{
		StringBuffer sSQL = new StringBuffer();
		sSQL.append( "insert into musica (nome,caminho) values ('" );
		sSQL.append( musica.getNome() );
		sSQL.append( "','" );
		sSQL.append( musica.getCaminho() );
		sSQL.append( "')" );		
		
		System.out.println( sSQL.toString() );
		try
		{
			stmt.executeUpdate( sSQL.toString() );
		}
		catch( SQLException e )
		{
			throw e;
		}
	}

	/**
	 * 
	 * @param listaNotas
	 * @param stmt
	 * @throws SQLException
	 */
	public List insereNotas( List listaNotas, Statement stmt, int idMusica ) throws SQLException
	{
		List listaNotasInseridas = new ArrayList();
		Nota notaInserida;
		
		Nota nota;
		for( int i = 0; i < listaNotas.size(); i++ )
		{
			nota = new Nota();
			nota = (Nota) listaNotas.get(i);
			nota.setMusicaId( idMusica );
			
			StringBuffer sSQL = new StringBuffer();
			sSQL.append( "insert into nota (nota,duracao,canal,instrumento) values ('" );
			sSQL.append( nota.getNota() );
			sSQL.append( "','" );
			sSQL.append( nota.getDuracao() );
			sSQL.append( "','" );
			sSQL.append( nota.getCanal() );
			sSQL.append( "','" );
			sSQL.append( nota.getInstrumento() );
			sSQL.append( "')" );
			
			System.out.println( sSQL.toString() );
			try
			{
				stmt.executeUpdate( sSQL.toString() );
				
				ResultSet rs = stmt.executeQuery("SELECT @@IDENTITY");

				if (rs.next())
				{
					notaInserida = new Nota();
					notaInserida.setId(rs.getInt(1));
					notaInserida.setPosicao(nota.getPosicao());					
					listaNotasInseridas.add(notaInserida);
				}
			}
			catch( com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException e )
			{
				StringBuffer SQL = new StringBuffer();
				SQL.append( "SELECT * from nota where nota = '" );
				SQL.append( nota.getNota() );
				SQL.append( "' and duracao = '" );
				SQL.append( nota.getDuracao() );
				SQL.append( "' and canal = '" );
				SQL.append( nota.getCanal() );
				SQL.append( "' and instrumento =  '" );
				SQL.append( nota.getInstrumento() );
				SQL.append( "'" );
				
				System.out.println( SQL.toString() );
				
				ResultSet rs = stmt.executeQuery(SQL.toString());

				notaInserida = new Nota();
				if (rs.next())
				{
					notaInserida.setId(rs.getInt("ID"));
					notaInserida.setPosicao(nota.getPosicao());					
					listaNotasInseridas.add(notaInserida);
				}				
				
				System.out.println( "Nota já existente: id = " +  notaInserida.getId() );
			}
			catch( SQLException e )
			{
				throw e;
			}
		}
		
		return listaNotasInseridas;
	}

	/**
	 * 
	 * @param listaNotas
	 * @param stmt
	 * @throws SQLException
	 */
	public List inserePontos( List listaPontos, List listaNotas, int idMusica, Statement stmt ) throws SQLException
	{
		Ponto ponto;
		int idPonto = 0;
		int idPontoRef = 0;
		List listaIdPonto = new ArrayList();
		
		for( int i = 0; i < listaPontos.size(); i++ )
		{
			idPonto = 0;
			ponto = new Ponto();
			ponto = (Ponto) listaPontos.get(i);

			StringBuffer sSQL = new StringBuffer();
			
			idPonto = buscaChavePonto(stmt, ponto, true);
			try
			{
				if( idPonto == 0 )
				{
					sSQL.append( "insert into ponto (coord1,coord2,coord3,coord4,coord5,coord6,coord7,tipo,idRef) values (" );
					sSQL.append( ponto.getCoord1() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoord2() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoord3() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoord4() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoord5() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoord6() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoord7() );
					sSQL.append( ",'" );
					sSQL.append( Constantes.PONTO_TIPO_NOTA );
					sSQL.append( "',null)" );
					
					System.out.println( sSQL.toString() );
					
					stmt.executeUpdate( sSQL.toString() );
					
					ResultSet rs = stmt.executeQuery("SELECT @@IDENTITY");

					if (rs.next())
					{
						idPonto = rs.getInt(1);
						ponto.setIdPonto(idPonto);
					}
				}
				
				// TODO Check indice de listaNotas
				if( idPonto != 0 )
					inserePosPontos( (Nota)listaNotas.get(i), idMusica, idPonto, stmt );

			}
			catch( com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException e )
			{
				System.out.println( "Ponto já existente " );
			}
			catch( SQLException e )
			{
				throw e;
			}
			
			// insere pontos de duração

			sSQL = new StringBuffer();
			idPonto = 0;
			try
			{
				ponto.setIdPontoRef(ponto.getIdPonto());
				idPonto = buscaChavePonto(stmt, ponto, false);
				
				if( idPonto == 0 && ponto.getIdPontoRef() != 0)
				{
					sSQL.append( "insert into ponto (coord1,coord2,coord3,coord4,coord5,coord6,coord7,tipo,idRef) values (" );
					sSQL.append( ponto.getCoordDuracao1() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoordDuracao2() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoordDuracao3() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoordDuracao4() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoordDuracao5() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoordDuracao6() );
					sSQL.append( "," );
					sSQL.append( ponto.getCoordDuracao7() );
					sSQL.append( ",'" );
					sSQL.append( Constantes.PONTO_TIPO_DURACAO );
					sSQL.append( "','" );
					sSQL.append( ponto.getIdPontoRef() );					
					sSQL.append( "')" );
					
					System.out.println( sSQL.toString() );
					
					stmt.executeUpdate( sSQL.toString() );
					
					ResultSet rs = stmt.executeQuery("SELECT @@IDENTITY");

					if (rs.next())
					{
						idPonto = rs.getInt(1);
						ponto.setIdPonto(idPonto);

					}
				}
				
				// TODO Check indice de listaNotas
				if( ponto.getIdPonto() != 0 && ponto.getIdPontoRef() != 0 )
					inserePosPontos( (Nota)listaNotas.get(i), idMusica, idPonto, stmt );

				if( ponto.getIdPonto() != 0 )
					listaIdPonto.add(ponto);
			}
			catch( com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException e )
			{
				System.out.println( "Ponto já existente " );
			}
			catch( SQLException e )
			{
				throw e;
			}
		}
		
		return listaIdPonto;
	}	


	/**
	 * 
	 * @param nota
	 * @param idMusica
	 * @param idPonto
	 * @param stmt
	 * @throws SQLException
	 */
	public void inserePosPontos( Nota nota, int idMusica, int idPonto, Statement stmt ) throws SQLException
	{
		StringBuffer sSQL = new StringBuffer();
		sSQL.append( "insert into posPontoMusica (musica_id,ponto_id,nota_id,posicao) values (" );
		sSQL.append( idMusica );
		sSQL.append( "," );
		sSQL.append( idPonto );
		sSQL.append( "," );
		sSQL.append( nota.getId() );
		sSQL.append( "," );
		sSQL.append( nota.getPosicao() );
		sSQL.append( ")" );
		
		System.out.println( sSQL.toString() );
		try
		{
			stmt.executeUpdate( sSQL.toString() );
		}
		catch( SQLException e )
		{
			throw e;
		}
	}	
	
	public int buscaChavePonto( Statement stmt, Ponto ponto, boolean isTipoNota ) throws SQLException
	{
		int idPonto = 0;
		
		StringBuffer sSQL = new StringBuffer();
		sSQL.append( "select * from Ponto where coord1 = " );
		sSQL.append( isTipoNota ? ponto.getCoord1() : ponto.getCoordDuracao1() );
		sSQL.append( " and coord2 = " );
		sSQL.append( isTipoNota ? ponto.getCoord2() : ponto.getCoordDuracao2() );
		sSQL.append( " and coord3 = " );
		sSQL.append( isTipoNota ? ponto.getCoord3() : ponto.getCoordDuracao3() );
		sSQL.append( " and coord4 = " );
		sSQL.append( isTipoNota ? ponto.getCoord4() : ponto.getCoordDuracao4() );
		sSQL.append( " and coord5 = " );
		sSQL.append( isTipoNota ? ponto.getCoord5() : ponto.getCoordDuracao5() );
		sSQL.append( " and coord6 = " );
		sSQL.append( isTipoNota ? ponto.getCoord6() : ponto.getCoordDuracao6() );
		sSQL.append( " and coord7 = " );
		sSQL.append( isTipoNota ? ponto.getCoord7() : ponto.getCoordDuracao7() );
		sSQL.append( " and tipo = '" );
		sSQL.append( isTipoNota ? Constantes.PONTO_TIPO_NOTA : Constantes.PONTO_TIPO_DURACAO );
		sSQL.append( "'" );
		sSQL.append( isTipoNota ? "" : " and idRef = " + ponto.getIdPontoRef() );
		
		System.out.println( sSQL.toString() );
		try
		{
			ResultSet rs = stmt.executeQuery( sSQL.toString() );

			if (rs.next())
			{
				idPonto = rs.getInt(1);
			}
		}
		catch( SQLException e )
		{
			throw e;
		}		
		
		return idPonto;
	}
	
	public boolean existeNomeArquivo( String asFileName ) throws SQLException
	{
		StringBuffer sSQL = new StringBuffer();
		sSQL.append( "select 1 from musica where nome = '" );
		sSQL.append( asFileName );		
		sSQL.append( "'" );
		
		System.out.println( sSQL.toString() );
		
		Connection conexao = getConexao();
		try
		{
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery( sSQL.toString() );

			return rs.next();
		}
		catch( SQLException e )
		{
			throw e;
		}		

	}
	
	public List buscaPontoMusica( List listaPonto ) throws SQLException
	{
		List listaResultado = new ArrayList();
		Resultado resultado;
		
		StringBuffer sSQL = new StringBuffer();
		
		sSQL.append( "select p.posicao, m.nome, n.instrumento from posPontoMusica p, musica m, nota n where " );
		sSQL.append( " p.musica_id = m.id and " );
		sSQL.append( " p.nota_id = n.id " );
		
		if( listaPonto != null && listaPonto.size() > 0 )
		{
			sSQL.append( " and p.ponto_id in( " );
		
			for( int i = 0; i < listaPonto.size(); i++ )
			{
				sSQL.append( ((Integer) listaPonto.get(i)).intValue() );
				sSQL.append( (i == listaPonto.size()-1) ?  "" : "," );
			}
			sSQL.append(")");
		}
		
		System.out.println(sSQL.toString());
		
		TabelaMidi tabelaMIDI = new TabelaMidi();
		Connection conexao = getConexao();
		try
		{
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery( sSQL.toString() );

			while (rs.next())
			{
				resultado = new Resultado();
				
				resultado.setNomeMusica( rs.getString("nome") );
				resultado.setPosicao( Double.toString(rs.getDouble("posicao")) );
				resultado.setInstrumento( tabelaMIDI.getIntrumento(rs.getInt("instrumento")) );
				resultado.setArtista( "-" );
				
				listaResultado.add( resultado );
			}
		}
		catch( SQLException e )
		{
			throw e;
		}		
		
		return listaResultado;
	}

	public List buscaPontoMusica( Ponto ponto ) throws SQLException
	{
		List listaResultado = new ArrayList();
		Resultado resultado;
		
		StringBuffer sSQL = new StringBuffer();
		
		sSQL.append( "select p.posicao, m.nome, n.instrumento from posPontoMusica p, musica m, nota n where " );
		sSQL.append( " p.musica_id = m.id and " );
		sSQL.append( " p.nota_id = n.id " );
		sSQL.append( " and p.ponto_id ='" );
		sSQL.append( ponto.getIdPonto() );
		sSQL.append("'");
		
		System.out.println(sSQL.toString());
		
		TabelaMidi tabelaMIDI = new TabelaMidi();
		Connection conexao = getConexao();
		try
		{
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery( sSQL.toString() );

			while (rs.next())
			{
				resultado = new Resultado();
				
				resultado.setNomeMusica( rs.getString("nome") );
				resultado.setPosicao( Double.toString(rs.getDouble("posicao")) );
				resultado.setInstrumento( tabelaMIDI.getIntrumento(rs.getInt("instrumento")) );
				// TODO
				resultado.setArtista( Double.toString(ponto.getSimilaridade()) );
				
				listaResultado.add( resultado );
			}
		}
		catch( SQLException e )
		{
			throw e;
		}		
		
		return listaResultado;
	}

	
	public List buscaPontosDuracao( List listaIdPonto ) throws SQLException
	{
		List listaResultado = new ArrayList();
		PontoNotaDuracao ponto;
		Ponto pontoNota,pontoDuracao;
		
		int iIdPonto = 0;		
		
		for( int i = 0; i < listaIdPonto.size(); i++ )
		{
			ponto = new PontoNotaDuracao();
			
			iIdPonto = ((Integer)listaIdPonto.get(i)).intValue();
			
			StringBuffer sSQL = new StringBuffer();
			
			sSQL.append( "select * from ponto p where " );
			sSQL.append( " p.idRef = " );
			sSQL.append( iIdPonto );	
			sSQL.append( " or p.id = " );
			sSQL.append( iIdPonto );
			sSQL.append( " order by tipo desc " );
			
			System.out.println(sSQL.toString());
			
			Connection conexao = getConexao();
			try
			{
				Statement stmt = conexao.createStatement();
				ResultSet rs = stmt.executeQuery( sSQL.toString() );

				if (rs.next())
				{
					pontoNota = new Ponto();

					pontoNota.setCoord1(rs.getInt("Coord1") );
					pontoNota.setCoord2(rs.getInt("Coord2") );
					pontoNota.setCoord3(rs.getInt("Coord3") );
					pontoNota.setCoord4(rs.getInt("Coord4") );
					pontoNota.setCoord5(rs.getInt("Coord5") );
					pontoNota.setCoord6(rs.getInt("Coord6") );
					pontoNota.setCoord7(rs.getInt("Coord7") );					

					ponto.setPontoNota(pontoNota);
					ponto.setIdNota( rs.getInt("id") );
				}
				if (rs.next())
				{
					pontoDuracao = new Ponto();
					
					pontoDuracao.setCoord1(rs.getInt("Coord1") );
					pontoDuracao.setCoord2(rs.getInt("Coord2") );
					pontoDuracao.setCoord3(rs.getInt("Coord3") );
					pontoDuracao.setCoord4(rs.getInt("Coord4") );
					pontoDuracao.setCoord5(rs.getInt("Coord5") );
					pontoDuracao.setCoord6(rs.getInt("Coord6") );
					pontoDuracao.setCoord7(rs.getInt("Coord7") );
					
					ponto.setPontoDuracao(pontoDuracao);
					ponto.setIdDuracao( rs.getInt("id") );
				}
				listaResultado.add( ponto );
			}
			catch( SQLException e )
			{
				throw e;
			}		
		}
		
		
		return listaResultado;
	}
	
	public List buscaReferenciaPontosDuracao( List listIdPonto ) throws SQLException
	{
		PontoNotaDuracao ponto;
		Ponto pontoNota,pontoDuracao;
		
		List listaResult = new ArrayList();
			
		int iIdPonto = 0;
		
		for( int i = 0; i < listIdPonto.size(); i++ )
		{
			ponto = new PontoNotaDuracao();
			
			iIdPonto = ((Integer)listIdPonto.get(i)).intValue();
			
			StringBuffer sSQL = new StringBuffer();
			
			sSQL.append(" select * from ponto p1 where p1.id = "); 
			sSQL.append( iIdPonto ); 
			sSQL.append(" or p1.id = (select idRef from ponto p2 where ");
			sSQL.append("p2.tipo = 'D' and p2.id =  ");
			sSQL.append( iIdPonto ); 
			sSQL.append(") order by tipo desc ");
			
			System.out.println(sSQL.toString());
			
			Connection conexao = getConexao();
			try
			{
				Statement stmt = conexao.createStatement();
				ResultSet rs = stmt.executeQuery( sSQL.toString() );

				if (rs.next())
				{
					pontoNota = new Ponto();

					pontoNota.setCoord1(rs.getInt("Coord1") );
					pontoNota.setCoord2(rs.getInt("Coord2") );
					pontoNota.setCoord3(rs.getInt("Coord3") );
					pontoNota.setCoord4(rs.getInt("Coord4") );
					pontoNota.setCoord5(rs.getInt("Coord5") );
					pontoNota.setCoord6(rs.getInt("Coord6") );
					pontoNota.setCoord7(rs.getInt("Coord7") );					

					ponto.setPontoNota(pontoNota);
					ponto.setIdNota( rs.getInt("id") );				

				}
				if (rs.next())
				{
					pontoDuracao = new Ponto();
					
					pontoDuracao.setCoord1(rs.getInt("Coord1") );
					pontoDuracao.setCoord2(rs.getInt("Coord2") );
					pontoDuracao.setCoord3(rs.getInt("Coord3") );
					pontoDuracao.setCoord4(rs.getInt("Coord4") );
					pontoDuracao.setCoord5(rs.getInt("Coord5") );
					pontoDuracao.setCoord6(rs.getInt("Coord6") );
					pontoDuracao.setCoord7(rs.getInt("Coord7") );
					
					ponto.setPontoDuracao(pontoDuracao);
					ponto.setIdDuracao( rs.getInt("id") );				
				}
				listaResult.add( ponto );
			}
			catch( SQLException e )
			{
				throw e;
			}		
			
		}	
		
		return listaResult;
	}
	
}
