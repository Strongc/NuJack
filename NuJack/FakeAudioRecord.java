//
//  Testing low level HiJack functionality.
//

package NuJack;

import java.util.*;
import java.io.*;

public class FakeAudioRecord implements IAudioRecord {

	public void loadFile()  // Reading a file in Java is ridiculously verbose
	{
      		FileInputStream in = null;
      		FileOutputStream out = null;

      		try 
		{
         		in = new FileInputStream("data.txt");
         		out = new FileOutputStream("output.txt");
         		
         		int c;
         		while ((c = in.read()) != -1) 
			{
            			out.write(c);
         		}
      		}
		catch(IOException e)
		{
          		System.out.println("IO 1: " + e.getMessage());
		}
		catch(Exception e)
		{
          		System.out.println("IO 2");
		}
		finally 
		{
			try
			{
         			if (in != null) 
            				in.close();
         			if (out != null) 
            				out.close();
			}
			catch(IOException e)
			{
          		System.out.println("IO 3");
			}
		}
	}


   	public int read (short[] buffer, int start, int end)
	{
		return 1;
	}

	public void startRecording()
	{
	}

   	public void release()
	{
	}
}
