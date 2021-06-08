
import java.util.*;

import java.io.*;

import java.awt.geom.Point2D;

class Node extends Proc {
	Random slump = new Random();

	Point2D.Double point;
	boolean checkGateway;
	private boolean failure = false;

	public Node(int x, int y) {
		point = new Point2D.Double(x, y);
		checkGateway = (point.distance(5000, 5000) <= RADIUS);
	}

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		
		// Used in both Strategy 1 & 2
		case AWAKE:
		failure = false;
		for (Node n : nodesTransmit) {
			SignalList.SendSignal(DISTURBANCE, n, time, this);
		}
		
		nodesTransmit.add(this);
		nbrSends++;
		SignalList.SendSignal(COMPLETE, this, time + TP, this);
		break;
		
		//Strategy 1
//		case COMPLETE:
//			if (failure) {
//				nbrBlocked++;
//				nodesTransmit.remove(this);
//				SignalList.SendSignal(AWAKE, this, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), this);
//				break;
//			}
//			// If not in range of gateway
//			else if (!checkGateway) {
//				nodesTransmit.remove(this);
//				SignalList.SendSignal(AWAKE, this, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), this);
//				break;
//			}
//
//			else {
//				nbrSuccess++;
//				nodesTransmit.remove(this);
//				SignalList.SendSignal(AWAKE, this, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), this);
//				break;
//			}
		//END Strategy 1
			
		//Strategy 2
		//Changed AWAKE signal to Listen signal for strategy 2
		case COMPLETE:
			if (failure) {
				nbrBlocked++;
				nodesTransmit.remove(this);
				SignalList.SendSignal(LISTEN, this, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), this);
				break;
			}
			// If not in range of gateway
			else if (!checkGateway) {
				nodesTransmit.remove(this);
				SignalList.SendSignal(LISTEN, this, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), this);
				break;
			}

			else {
				nbrSuccess++;
				nodesTransmit.remove(this);
				SignalList.SendSignal(LISTEN, this, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), this);
				break;
			}
			
		case LISTEN: {
			boolean checkDisturbance = false;
			for (Node n : nodesTransmit) {
				if (n.point.distance(this.point) <= RADIUS) {
					checkDisturbance = true;
					break;
				}
			}
			if (checkDisturbance)
				SignalList.SendSignal(AWAKE, this, time + (slump.nextDouble() * (UB - LB)) + LB, this);
			else
				SignalList.SendSignal(AWAKE, this, time, this);
			
			break;
		}
			//END Strategy 2

		// Used in both strategy 1 & 2
		case DISTURBANCE:
			Node sender = x.sender;
			if (sender.checkGateway && this.checkGateway) {
				this.failure = true;
				sender.failure = true;
			}
			break;
		}
	}
}