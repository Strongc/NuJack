//
// Capstone testing HiJack in Console-App
//

import NuJack.*;

class main
{  
        public static void main(String args[])
        {
		//FakeAudioRecord fru = new FakeAudioRecord();
		//fru.loadFile();
		//fru.Print();

		//FakeSink fs = new FakeSink();

		//AudioReceiver aru = new AudioReceiver(fru);
		//aru.registerIncomingSink(fs);
		//aru.startAudioIO();

		/*
		SerialDecoder decoder = new SerialDecoder();
		decoder.start();
		decoder.print();
		*/

		
		Decoder dec = new Decoder();
		dec.start();

		//fs.Print();
          	System.out.println("------DONE------");
        }
}
