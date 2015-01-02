package NuJack;

public interface IncomingSink {
	// Called with the period between the last frequency
	// shift and the current one, and what type of transistion
	// it was (HIGH TO LOW or LOW TO HIGH).
	void handleNextBit(int transistionPeriod, boolean isHighToLow);
}