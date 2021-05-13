import java.util.*;

import java.io.*;

public class Node extends Proc {
	private int xCoord;
	private int yCoord;
	private Proc sendTo;

	// Just a random number generator
	Random slump = new Random();

	public Node(int xCoord, int yCoord, Proc sendTo) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.sendTo = sendTo;
	}

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case AWAKE:
			nbrSends++;
			if (checkPos())
				send(); 

			SignalList.SendSignal(AWAKE, this, time + Math.log(1.0 - slump.nextDouble()) / (1.0 / -TS));
			break;

		case RESEND:
			break;
		}
	}

	public void send() {
		SignalList.SendSignal(COMPLETE, sendTo, time + TP);
		// Save timestamp for when we send in a list 

	}

	public boolean checkPos() {
		double x = Math.abs((double) xCoord - (double) 50000);
		double y = Math.abs((double) yCoord - (double) 50000);

		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

		if (distance <= (double) RADIUS)
			return true;

		return false;
	}

	public int getX() {
		return xCoord;
	}

	public int getY() {
		return yCoord;
	}
}
