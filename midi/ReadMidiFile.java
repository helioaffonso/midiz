package midi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import model.vo.ArquivoMidi;
import model.vo.Musica;
import model.vo.Nota;

public final class ReadMidiFile implements JMC{
	
	/*#util.ReadFile Dependency20*/
/*#model.vo.ArquivoMidi Dependency201*/

private int[] ivCanaisIgnorados = {9,10};

	
	public ArquivoMidi readMidi( String asFileName ){

		ArquivoMidi arquivoMidi = new ArquivoMidi();
		Musica musicaVO = new Musica();
		Nota notaVO = new Nota();
		List listaNotas = new ArrayList();
		
		int dDuracao = 0;
		double dPosicao = 0.0;
		int decimalPlace = 2;
		BigDecimal bigDecimal;
		
		if( asFileName.indexOf( "\\" ) > 0 )
		{
			musicaVO.setCaminho(asFileName.substring( 0,asFileName.lastIndexOf("\\") ));
			musicaVO.setNome(asFileName.substring( asFileName.lastIndexOf("\\")+1, asFileName.length() ));
		}
		else
		{
			musicaVO.setCaminho(asFileName);
			musicaVO.setNome(asFileName);
		}
		
		Score score = new Score("");
        
		try
		{
			Read.midi(score, asFileName);
			
			double dTime = 0;
			double dAuxCompasso = 0;
			double dInicioNota = 0;
			
			int iCount = 0;
			int iCompasso = 1;
			
			for( int i = 0; i < score.getPartList().size(); i++ )
			{
				Part part = score.getPart(i);
				Vector vPartes = part.getPhraseList();
				
				if( !ignoraCanal(part.getChannel()) )
				{
					for( int j = 0; j < vPartes.size(); j++ )
					{
						Phrase frase = (Phrase)vPartes.get(j);
						Vector vNotas = frase.getNoteList();
						
						dTime = 0.0;
						dPosicao = 0.0;
						for( int k = 0; k < vNotas.size(); k++ )
						{
							Note nota = (Note)vNotas.get(k);
							dTime += nota.getRhythmValue();
							dAuxCompasso += nota.getRhythmValue();
							if( nota.getPitch() > 0 && nota.getRhythmValue() > 0 )
							{
								notaVO = new Nota();
								
								if( dAuxCompasso > 4 )
								{
									dAuxCompasso = 0;
									iCompasso++;
								}
								
								/*bigDecimal = new BigDecimal((nota.getRhythmValue() * score.getTempo())/60);
								bigDecimal = bigDecimal.setScale( decimalPlace, BigDecimal.ROUND_HALF_UP );
									
								dDuracao = bigDecimal.doubleValue();*/

								dDuracao = (int) (nota.getRhythmValue() * 64);
								dPosicao = Math.floor(dInicioNota);
								
								notaVO.setCanal( part.getChannel() );
								notaVO.setDuracao( dDuracao < 512 ? dDuracao : 512 );
								notaVO.setNota( nota.getPitch() );
								notaVO.setPosicao( dPosicao );
								notaVO.setInstrumento( part.getInstrument() );

								listaNotas.add( notaVO );
								iCount++;
								dInicioNota = dTime;
							}
						}
					}
				}
			}
			
			arquivoMidi.setListaNotas(listaNotas);
			arquivoMidi.setMusica(musicaVO);
		}
		catch( Exception e )
		{
			System.out.println( "Erro ao realizar leitura de arquivo midi. \nVerifique se o arquivo " + asFileName + 
								" está corrompido" );
		}
		
		return arquivoMidi;
	}
	
	private boolean ignoraCanal( int aiCanal )
	{
		boolean bIgnoraCanal = false;
		
		for( int i = 0; i < ivCanaisIgnorados.length; i++ )
		{
			if( aiCanal == ivCanaisIgnorados[i] )
			{
				bIgnoraCanal = true;
				break;
			}
		}
		
		return bIgnoraCanal;
	}
}