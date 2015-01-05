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
		Integer i1 = new Integer(transistionPeriod);
		Boolean i2 = new Boolean(isHighToLow);
		lengths.add(i1);
		trans.add(i2);
	}

	public void Print()
	{
		//Integer i1 = new Integer(1);
		//lengths.add(i1);

		for (Integer i : lengths)
		{
			System.out.println(i);
		}
	}
}
