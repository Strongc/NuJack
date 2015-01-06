//
//  Testing low level HiJack functionality.
//

package NuJack;

import java.util.*;

public class FakeSink implements IncomingSink {

	List<Integer> lengths = new ArrayList<Integer>();
	List<Boolean> trans = new ArrayList<Boolean>();
	
	public void handleNextBit(int transistionPeriod, boolean isHighToLow)
	{
		//System.out.println("Handle next hit");
		Integer i1 = new Integer(transistionPeriod);
		Boolean i2 = new Boolean(isHighToLow);
		lengths.add(i1);
		trans.add(i2);
		//System.out.println("Length:  " + lengths.size());
	}

	public void Print()
	{
		//Integer i1 = new Integer(1);
		//lengths.add(i1);
		//System.out.println("Length:  " + lengths.size());

		System.out.println("-------FakeSink Results-------");
		for (int i = 0; i < lengths.size(); i++)
		{
			System.out.println(lengths.get(i) + ", " + trans.get(i).toString());
		}
		System.out.println("------------------------------");
	}
}
