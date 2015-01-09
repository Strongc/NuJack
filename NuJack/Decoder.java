package NuJack;

public class Decoder
{
	///////////////////////////////////////
	/////////////// Members ///////////////
	///////////////////////////////////////
	private AudioReceiver _audioReceiver;
	private enum ReceiveState { IDLE, DATA, DATANEXT };

	private ReceiveState _rxState = ReceiveState.IDLE;

	private int _lastEdgeLength = 0;
	private int _currentEdgeLength = 0;
	private boolean _currentEdgeHigh = false;

	private int _threshold = 14;
	
	private int _deltaT = 0;
	private enum Freq { ZERO, ONE, TWO};

	public Decoder() {
		_audioReceiver = new AudioReceiver(new FakeAudioRecord());
		_audioReceiver.registerIncomingSink(_incomingSink);
	}

	private IncomingSink _incomingSink = new IncomingSink() {
		public void handleNextBit(int transistionPeriod, boolean isHighToLow)
		{
            		//_lengths.add(transistionPeriod);
            		//_trans.add(isHighToLow);
			Freq freq = checkFreq(transistionPeriod);
        
			System.out.println("Tran: " + transistionPeriod + " HL: " + isHighToLow);
			System.out.println("Freq:  " + freq + "\n");
            
			_currentEdgeLength = transistionPeriod;
			_currentEdgeHigh = isHighToLow;
			
			switch (_rxState) {
			case DATA:
                		System.out.println("--DATA--");
				receiveData(freq);
				break;
			case DATANEXT:
                		System.out.println("--DATANEXT--");
				receiveDataNext(freq);
				break;
			case IDLE:
                		System.out.println("--IDLE--");
				receiveIdle(freq);
				break;
			default:
				break;
			}
			_lastEdgeLength = _currentEdgeLength;
		}
	};


	private void receiveIdle(Freq freq) {
		if (_currentEdgeHigh == true &&
				freq == Freq.TWO) {
				//isWithinThreshold(_currentEdgeLength,
						//_lastEdgeLength * 4)) {
				//isWithinThreshold(
						//_lastEdgeLength * 4)) {
			
			_deltaT = _lastEdgeLength;
			
			_rxState = ReceiveState.DATA;

			//System.out.println("IDLE TRUE");
			
			/*
			_rxByte = 0;
			_rxBits = 1;
			*/
		}
			//System.out.println("IDLE FALSE");
	}
	
	private void receiveData(Freq freq) {
		//if (isWithinThreshold(_currentEdgeLength, _deltaT)) {
		if (isWithinThreshold(_deltaT)) {
			_rxState = ReceiveState.DATANEXT;
			System.out.println("---0----EL: " + _currentEdgeLength + " DT: " + _deltaT);
		}
		//else if (isWithinThreshold(_currentEdgeLength, _deltaT * 2)) {
		else if (isWithinThreshold(_deltaT * 2)) {
			//if (((_rxByte >> (_rxBits - 1)) & 1) == 0) {
				//_rxByte |= (1 << _rxBits);
			//}
			//_rxBits++;
			//advanceReceiveDataState();
			System.out.println("---1----EL: " + _currentEdgeLength + " DT: " + _deltaT);
		}
		else {
			System.out.println("EL: " + _currentEdgeLength + " DT: " + _deltaT);
			//System.out.println("Error.");
			_rxState = ReceiveState.IDLE;
		}
	}
	
	private void receiveDataNext(Freq freq) {
		//if (isWithinThreshold(_currentEdgeLength, _deltaT)) {
		if (isWithinThreshold(_deltaT)) {
			//if (((_rxByte >> (_rxBits - 1)) & 1) == 1) {
				//_rxByte |= (1 << _rxBits);
			//}
			
			//_rxBits++;
			//advanceReceiveDataState();
			System.out.println("---0----EL: " + _currentEdgeLength + " DT: " + _deltaT);
		}
		else {
			System.out.println("BEL: " + _currentEdgeLength + " DT: " + _deltaT);
			_rxState = ReceiveState.IDLE;
			//System.out.println("Error.");
		}
	}


	public void start() {
		_audioReceiver.startAudioIO();		
	}
	
	public void stop() {
		_audioReceiver.stopAudioIO();
	}

	public void setFreq(int freq) {
		_audioReceiver.setPowerFrequency(freq);
	}

	private boolean isWithinThreshold2(int value, int desired) {
		return value < desired + _threshold && value > desired - _threshold;
	}

	private boolean isWithinThreshold(int value)
	{
		return value < zeroFreq + _threshold && value > zeroFreq - _threshold;
	}

	private Freq checkFreq(int value)
	{
		if (isWithinThreshold2(value, zeroFreq))
		{
			return Freq.ZERO;
		}
		else if(isWithinThreshold2(value, zeroFreq*2))
		{
			return Freq.ONE;
		}
		else if(isWithinThreshold2(value, zeroFreq*4))
		{
			return Freq.TWO;
		}
		else
		{
			return Freq.TWO;
		}
	}


	private final int zeroFreq = 25;

	/*
	public void registerBytesAvailableListener(OnBytesAvailableListener _listener) {
		_bytesAvailableListener = _listener;
	}

	private void notifyBytesAvailable(int numberBytes) {
		if (_bytesAvailableListener != null) {
			_bytesAvailableListener.onBytesAvailable(numberBytes);
		}
	}
	*/
}
