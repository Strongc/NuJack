package NuJack;

import java.util.ArrayList;
import java.util.List;

public class Decoder
{
	//////////////////////////////
	// Members 
	/////////////////////////////

	private AudioReceiver _audioReceiver;

	private enum ReceiveState {IDLE, DATA, DATANEXT}; // TODO consider adding an IDLENEXT
	private enum Freq {ZERO, ONE, TWO};

	private ReceiveState _rxState = ReceiveState.IDLE;

	private nByte _nByte = new nByte();

	private Freq _lastFreq = Freq.ZERO;
	private Freq _freq = Freq.ZERO;

	private final int _threshold = 14;
	private final int zeroFreq = 25;

	OnBytesAvailableListener _bytesAvailableListener = null;

	private List<Integer> _bits = new ArrayList<Integer>();  // DEBUG

	//////////////////////////////
	// Constructors
	/////////////////////////////

	public Decoder() {
		_audioReceiver = new AudioReceiver(new FakeAudioRecord());  //DEBUG
		_audioReceiver.registerIncomingSink(_incomingSink);
	}

	/*  
	public Decoder(IAudioRecord aru) {
		_audioReceiver = aru;
		_audioReceiver.registerIncomingSink(_incomingSink);
	}
	*/

	//////////////////////////////
	// Receive State Machine
	/////////////////////////////
	private IncomingSink _incomingSink = new IncomingSink() {
		public void handleNextBit(int transistionPeriod, boolean isHighToLow)
		{
			_freq = checkFreq(transistionPeriod);
        
			// DEBUG //
			System.out.println("Tran: " + transistionPeriod + " HL: " + isHighToLow);
			System.out.println("Freq:  " + _freq );
			//
            
			switch (_rxState) {
			case DATA:
                		System.out.println("--DATA--");
				receiveData();
				break;
			case DATANEXT:
                		System.out.println("--DATANEXT--");
				receiveDataNext();
				break;
			case IDLE:
                		System.out.println("--IDLE--");
				receiveIdle(isHighToLow);
				break;
			default:
				break;
			}
		}
	};

	private void receiveIdle(boolean currentEdgeHigh) {
		if (currentEdgeHigh == true && _freq == Freq.TWO) 
		{
			_rxState = ReceiveState.DATA;
			_lastFreq = Freq.TWO;

		}
		System.out.println("\n");
	}

	private void receiveData() {
		if (_freq == Freq.TWO) // 2nd part of 3rd Frequency. this happen if we come from IDLE
		{
			System.out.println("DATA --2--");
		}
		else if (_freq == Freq.ZERO)
		{
			_rxState = ReceiveState.DATANEXT;
			_lastFreq = Freq.ZERO;
			System.out.println("DATA --0--");
		}
		else if (_freq == Freq.ONE)
		{
			_rxState = ReceiveState.DATANEXT;
			_lastFreq = Freq.ONE;
			System.out.println("DATA --1--");
		}
		else
		{
			// throw error unknow freq
		}
		System.out.println("\n");
	}

	private void receiveDataNext() {
		//TODO need to handle 2 case. should never happen.
		//if (freq == Freq.TWO || freq != _lastFreq)
		if (_freq != _lastFreq) // Malformed frequency. Throw an error here and clear bit buffer
		{
			
			_nByte.Reset();
			_rxState = ReceiveState.IDLE;
			return;
		}

		if (_freq == Freq.ZERO)
		{
			System.out.println("--0--END"); //DEBUG
			_bits.add(0); //DEBUG

			_nByte.push(0);
		}
		//else if (freq == Freq.ONE)
		else // freq == Freq.ONE
		{
			System.out.println("--1--END"); //DEBUG
			_bits.add(1); //DEBUG

			_nByte.push(1);
		}

		if (_nByte.IsByteComplete())
		{
			float value = _nByte.GetValue();
			//_bytesAvailableListener.onBytesAvailable(numberBytes);
			_nByte.Reset();
			_rxState = ReceiveState.IDLE;
		}

		_rxState = ReceiveState.DATA;
	}



	//////////////////////////////
	// Helpers
	/////////////////////////////
	private boolean isWithinThreshold2(int value, int desired) {
		return value < desired + _threshold && value > desired - _threshold;
	}

	private Freq checkFreq(int value)
	// TODO refactor this to return an Error for an invalid frequency.
	{
		if (isWithinThreshold2(value, zeroFreq*2))
		{
			return Freq.ZERO;
		}
		else if (isWithinThreshold2(value, zeroFreq))
		{
			return Freq.ONE;
		}
		else if (isWithinThreshold2(value, zeroFreq*4))
		{
			return Freq.TWO;
		}
		else
		{
			return Freq.TWO;
		}
	}

	private void notifyBytesAvailable(int numberBytes) {
		if (_bytesAvailableListener != null) {
			_bytesAvailableListener.onBytesAvailable(numberBytes);
		}
	}
	

	//////////////////////////////
	// Public Interface
	/////////////////////////////
	public void print() // DEBUG 
	{
		for (Integer i : _bits)
		{
			System.out.println("BIT:  " + i);
		}
		System.out.println("--- nBYTE---");
		System.out.println(_nByte.GetValue());
		System.out.println(_nByte.intVal());
	}
	
	public void start() {
		_audioReceiver.startAudioIO();		
	}
	
	public void stop() {
		_audioReceiver.stopAudioIO();
	}

	public void setFreq(int freq) { // TODO prob can get rid of this
		_audioReceiver.setPowerFrequency(freq);
	}

	public void registerBytesAvailableListener(OnBytesAvailableListener _listener) {
		_bytesAvailableListener = _listener;
	}
}
