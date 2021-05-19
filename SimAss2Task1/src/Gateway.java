import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gateway extends Proc {
	private int xCoord;
	private int yCoord;

	// Slumptalsgeneratorn startas:
	// The random number generator is started:
	Random slump = new Random();

	// Generatorn har tv� parametrar:
	// There are two parameters:
	public Proc sendTo; // Anger till vilken process de genererade kunderna ska skickas //Where to send
						// customers
	public double lambda; // Hur m�nga per sekund som ska generas //How many to generate per second

	// H�r nedan anger man vad som ska g�ras n�r en signal kommer //What to do when
	// a signal arrives
	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		// case RECEIVE:
		// GlobalTimeList.Add([signal.arrivaltime signal.arrivaltime + 2])
		// Create signal Complete

		// CASE Complete
		// Check back 2 s for Complete/receive.
		// IF open nbrsuccess ++
		// IF busy drop

//				SignalList.SendSignal(ARRIVAL, sendTo, time);
//				SignalList.SendSignal(READY, this, time + (2.0/lambda)*slump.nextDouble());
//		
		case COMPLETE:
			signals.add(x);

			if (checkTimeStamp()) {
				nbrSuccess++;
			} else {
				nbrBlocked++;
			}
			break;
		}
	}

	public boolean checkTimeStamp() {
		int n = 2;
		double testT = time;
		double succ = nbrSuccess;
		double block =nbrBlocked;
		ArrayList<Signal> Alist = signals;

		if (signals.size() <= 2) // checks if list is empty
			return true;
		
		Node node = signals.get(signals.size()-n).sender;
		int i = signals.get(signals.size() - n).signalType;
		double t = signals.get(signals.size() - n).arrivalTime;
		
		while (time - t < TP) { // checks if there is a signal within one second
			if (i != -1) { // checks if the signal is interferring
				return false;
			}
			
			// This code is for implementing strategy 2
			n++;

			if (signals.size() - n < 0) // checks if list is empty
				return true;

			i = signals.get(signals.size() - n).signalType;
			t = signals.get(signals.size() - n).arrivalTime;

		}
		return true;
	}

	public int getX() {
		return xCoord;
	}

	public int getY() {
		return yCoord;
	}
}