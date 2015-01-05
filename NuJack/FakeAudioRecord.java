//
//  Testing low level HiJack functionality.
//

package NuJack;

import java.util.*;
import java.io.*;

public class FakeAudioRecord implements IAudioRecord {

	private List<String> _lines = new ArrayList<String>();

	public FakeAudioRecord()
	{
		loadFile();
		read();
	}

	public void loadFile()  // Reading a file in Java is ridiculously verbose
	{
		try
		{
			File file = new File("data.txt");
			FileReader fr = new FileReader(file);
			BufferedReader in2 = new BufferedReader(fr);

			while (in2.ready()) {
  				String s = in2.readLine();
				_lines.add(s);
          			//System.out.println("str:  " + s);
			}
			in2.close();
		}
		catch(IOException e)
		{
          		System.out.println("IO error: " + e.getMessage());
		}
	}
	
	public void Print()
	{
		for (String s : _lines)
		{
			System.out.println("Print: " + s);
		}

		for (Short s : _data)
		{
			System.out.println("Out: " + s.toString());
		}
	}


   	public int read (short[] buffer, int start, int end)
	{
		return _lines.size();
	}

	public short[] _data;

	public short[] read()
	{
		_data = new short[_lines.size()];
		for (int i = 0; i < _lines.size(); i++)
		{
			_data[i] = Short.parseShort(_lines.get(i));
		}
		return _data;
	}

	public int getSize()
	{
		return _data.length;
	}

	public void startRecording()
	{
	}

   	public void release()
	{
	}
}
