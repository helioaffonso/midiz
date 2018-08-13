package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import kdTree.ArvoreKD;

import midi.ReadMidiFile;
import model.vo.ArquivoMidi;
import model.vo.Ponto;
import util.Util;

public class Indexacao {

	
/*#model.AcessoTabelas Dependency20*/







/*#model.Wavelet Dependency202*/



/*#model.vo.ArquivoMidi Dependency203*/

/*#midi.ReadMidiFile Dependency201*/
/*#model.vo.Ponto Dependency204*/






private ArvoreKD arvoreNotas;
	private ArvoreKD arvoreDuracoes;
	private static ResourceBundle rs;
	
	public Indexacao()
	{
		arvoreNotas = new ArvoreKD();
		arvoreDuracoes = new ArvoreKD();
		
		try
		{
			FileInputStream arq = new FileInputStream("arvoreNotas.mdz");
			ObjectInputStream in = new ObjectInputStream(arq);
			arvoreNotas = (ArvoreKD)in.readObject();
		}
		catch( Exception e )
		{
			System.out.println( "Erro ao buscar indice.\n " + e.getMessage() );
		}

		try
		{
			FileInputStream arq = new FileInputStream("arvoreDuracoes.mdz");
			ObjectInputStream in = new ObjectInputStream(arq);
			arvoreDuracoes = (ArvoreKD)in.readObject();
		}
		catch( Exception e )
		{
			System.out.println( "Erro ao buscar indice.\n " + e.getMessage() );
		}
		
		rs = ResourceBundle.getBundle("model/config");
	}
	
	public void indexaArquivos()
	{
		String folderPath = rs.getString("diretorioMusicas"); 
		String[] listaArquivos = Util.buscaAquivosPasta( folderPath );
		
		for( int i = 0; i < listaArquivos.length; i++ )
		{
			indexaMusica( listaArquivos[i] );
		}
	}
	
	private void indexaMusica( String sFileName )
	{
		String nomeArquivoMidi = sFileName.substring( sFileName.lastIndexOf("\\")+1, sFileName.length() ); 
		
		ReadMidiFile read = new ReadMidiFile();
		
		// insere na base
		AcessoTabelas acessoTabelas = new AcessoTabelas();

		boolean arquivoExiste = false;
		List listaPontos = null;
		List listaIdPontos = null;
		
		try
		{
			arquivoExiste = acessoTabelas.existeNomeArquivo( nomeArquivoMidi );

			if( !arquivoExiste )
			{	
				// extrai informação dos arquivos
				ArquivoMidi arquivoMidi = read.readMidi( sFileName );
				listaPontos = Wavelet.montaListaPontos( arquivoMidi.getListaNotas() ); 
				listaIdPontos = acessoTabelas.insereArquivoMidi( arquivoMidi, listaPontos );
			}
		}
		catch( SQLException e )
		{
			System.out.println(e.getMessage());
		}

		if( listaPontos != null && listaIdPontos != null  )
		{
			// insere na árvore kd
			for( int i = 0; i < listaIdPontos.size(); i++ )
			{
				arvoreNotas.insert( (Ponto) listaIdPontos.get(i), true );
				arvoreDuracoes.insert( (Ponto) listaIdPontos.get(i), false );
			}
		}
		
		System.out.println( "\n\nIndexou!!!" );
	}
	
	// TODO + uma maozada
	public void salvaIndice()
	{
		try
		{
			FileOutputStream saida = new FileOutputStream("arvoreNotas.mdz");
			ObjectOutputStream out = new ObjectOutputStream(saida);
			out.writeObject(this.arvoreNotas);
		}
		catch( Exception e )
		{
			System.out.println( "Erro ao salvar índice\n" + e.getMessage() );
		}
		
		try
		{
			FileOutputStream saida = new FileOutputStream("arvoreDuracoes.mdz");
			ObjectOutputStream out = new ObjectOutputStream(saida);
			out.writeObject(this.arvoreDuracoes);
		}
		catch( Exception e )
		{
			System.out.println( "Erro ao salvar índice\n" + e.getMessage() );
		}
	}

	public ArvoreKD getArvoreDuracoes() {
		return arvoreDuracoes;
	}

	public void setArvoreDuracoes(ArvoreKD arvoreDuracoes) {
		this.arvoreDuracoes = arvoreDuracoes;
	}

	public ArvoreKD getArvoreNotas() {
		return arvoreNotas;
	}

	public void setArvoreNotas(ArvoreKD arvoreNotas) {
		this.arvoreNotas = arvoreNotas;
	}
	
}
