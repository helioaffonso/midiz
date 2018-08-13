package util;

import java.io.*;

public class ReadFile {

	
	public void readFile(String sFileName)
	{
		try 
		{
			FileReader input = new FileReader(sFileName);
			BufferedReader bufRead = new BufferedReader(input);

			bufRead.close();
      
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Usage: java ReadFile filename\n");          
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//9936285927725691
	
}
