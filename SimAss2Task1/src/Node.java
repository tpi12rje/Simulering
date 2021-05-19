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

			//Coment away for Strategy 1. 
//			if (listen()) { // checks if another node is transmitting
//				x.setSignalType(-1);
//				signals.add(x);
//				SignalList.SendSignal(RESEND, this, time +  (double) LB + (double) slump.nextDouble()*(UB-LB), this);
//				break;
//			}

			nbrSends++;
			if (checkPos()) {
				signals.add(x);
				send();
			}

		//	SignalList.SendSignal(AWAKE, this, time + Math.log(1.0 - slump.nextDouble()) / (1.0 / -TS), this);
			//SignalList.SendSignal(AWAKE, this, time + 2/(1/TS)*slump.nextDouble(), this);
			SignalList.SendSignal(AWAKE, this, time + 2/(lambda)*slump.nextDouble(), this);

			break;

		case RESEND:
			nbrSends++;
			if (checkPos()) {
				send();
				signals.add(x);
			}

		//	SignalList.SendSignal(AWAKE, this, time + Math.log(1.0 - slump.nextDouble()) / (1.0 / -TS), this);
		//	SignalList.SendSignal(AWAKE, this, time + 2/(1/TS)*slump.nextDouble(), this);
			SignalList.SendSignal(AWAKE, this, time + 2/(lambda)*slump.nextDouble(), this);

			//time + 2/(1/4000)*rand.nextDouble();

			break;
		}
	}

	public void send() {
		SignalList.SendSignal(COMPLETE, sendTo, time + TP, this);
	}

	public boolean listen() {
		int n = 1;
		if (signals.size() <= n)
			return false;

		n++;
		Node node = signals.get(signals.size() - n).sender;
		int i = signals.get(signals.size() - n).signalType;
		double t = signals.get(signals.size() - n).arrivalTime;

		while (time - t < TP) {
			if (checkRadius(node)) {
				return true;
			}

			n++;
			if (signals.size() - n < 0) // checks if list is empty
				return false;
			
			node = signals.get(signals.size() - n).sender;
			i = signals.get(signals.size() - n).signalType;
			t = signals.get(signals.size() - n).arrivalTime;

		}
		return false; 
	}

	public boolean checkRadius(Node node) {
		double x =Math.abs((double) xCoord - (double) node.getX());
		double y = Math.abs((double) yCoord - (double) node.getY());
		
		double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		if (distance <= (double) RADIUS && distance != 0)
			return true;
		
		return false;
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
