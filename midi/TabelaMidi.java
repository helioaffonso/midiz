package midi;

import java.util.HashMap;

public class TabelaMidi {

	public HashMap midiTable = new HashMap();
	
	public TabelaMidi()
	{
		midiTable.put("0", "-" );
		midiTable.put("1", "Piano" );
		midiTable.put("7", "Cravo" );
		midiTable.put("1", "Piano" );
		midiTable.put("1", "Piano" );
		midiTable.put("17", "Orgão" );
		midiTable.put("23", "Gaita" );
		midiTable.put("25", "Violão" );
		midiTable.put("26", "Violão" );
		midiTable.put("27", "Guitarra" );
		midiTable.put("33", "Contrabaixo" );
		midiTable.put("41", "Violino" );
		midiTable.put("42", "Viola" );
		midiTable.put("43", "Cello" );
		midiTable.put("44", "Cordas" );
		midiTable.put("57", "Trompete" );
		midiTable.put("58", "Trombone" );
		midiTable.put("59", "Tuba" );
		midiTable.put("60", "Trompete" );
		midiTable.put("61", "Metais" );
		midiTable.put("65", "Saxofone" );
		midiTable.put("74", "Flauta" );
	}
	
	public String getIntrumento( int i )
	{
		if( i > 0 && i < 7 )
			i = 1;
		else if( i > 16 && i < 21 )
			i = 17;
		else if( i > 26 && i < 33 )
			i = 27;
		else if( i > 32 && i < 41 )
			i = 33;
		else if( i > 43 && i < 56 )
			i = 44;
		else if( i > 60 && i < 65 )
			i = 61;		
		else if( i > 64 && i < 69 )
			i = 65;
		else if( i > 68 && i < 81 )
			i = 74;
		else if( i > 80 ) 
			i = 0;
		
		String sRetorno = (String)midiTable.get( Integer.toString(i));
		
		return sRetorno;
	}
}
