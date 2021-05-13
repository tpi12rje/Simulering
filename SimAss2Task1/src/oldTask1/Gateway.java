
public class Gateway extends Proc {

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
		case RECEIVE:
			receive(time, x);
			break;
		}
	}

	/*
	 * If the gateway receives more than one transmission which overlaps with each
	 * other, all transmissions within the overlap are dismissed. The overlap must
	 * be checked to see if there are any transmissions that started within 1.99
	 * seconds from the transmission was received. If so, they overlapped.
	 * 
	 * @param Time is the time when the transmission was received by the gateway.
	 */
	private void receive(double time, Signal x) {
		Signal temp = x;

		if (checkPos(x)) {
			while (temp.prev != null && time - temp.prev.arrivalTime <= 2.0) {
				if (temp.prev.signalType == 1 || temp.prev.signalType == 3) {
					return;
				}
				temp = temp.prev;
			}
			nbrSuccess++;
		}
	}

	/*
	 * This method checks if a certain Sensor is within reach of the Gateway.
	 * 
	 * @param Signal is the temporary signal.
	 * 
	 * @return If the Sensor-signal is within the radius of the Gateway, return
	 * true, else return false.
	 * 
	 */
	private boolean checkPos(Signal temp) {
		if (temp.node != null) {
			double x = Math.abs((double) 50000 - (double) temp.node.getX());
			double y = Math.abs((double) 50000 - (double) temp.node.getY());

			double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

			if (distance <= (double) RADIUS)
				return true;
		}
		return false;
	}
}
