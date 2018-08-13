package testes;

import kdTree.ArvoreKD;
import midi.ReadMidiFile;
import model.Wavelet;

public class TesteMain {

	public static void main( String[] args )
	{
		ArvoreKD tree = new ArvoreKD(2);
		
		double[] p1 = {6.0,4.0};
		double[] p2 = {7.0,3.0};
		double[] p3 = {2.0,1.0};
		double[] p4 = {4.0,6.0};
		
		try
		{
			tree.insert(p1, "p1");
			tree.insert(p2, "p2");
			tree.insert(p3, "p3");
			tree.insert(p4, "p4");
		}
		catch( Exception e )
		{
			
		}
		
		System.out.println( "debug!" );
		
		/*System.out.println((int) (0.25 * 64));
		System.out.println((int) (0.333 * 64));
		System.out.println((int) (0.3333 * 64));
		System.out.println((int) (0.6666 * 64));
		System.out.println((int) (1.01 * 64));*/
		
		//testaWavelet();
		//testaLeitura();
	}
	
	public static void testaLeitura()
	{
		ReadMidiFile read = new ReadMidiFile();
		read.readMidi("C:\\MIDIZSW\\arqsmidi\\blackknight_deeppurple60.mid");
	}
	
	public static void testaWavelet()
	{
		/*int[] iNotas = {42,42,43,42,45,44,42,42};
		//int[] iNotas = {1,1,2,1,4,3,1,1};
		int[] itemp = new int[8];
		
		itemp = Wavelet.transformada( iNotas );
		
		for(int i=0; i < itemp.length; i++)
		{
			System.out.println(itemp[i]);
		}
		*/

	}
}
