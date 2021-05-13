import java.util.Random;

public class Sensor extends Proc {
	private int xCoord;
	private int yCoord;
	private Proc sendTo;
	private Sensor node;

	// Just a random number generator
	Random slump = new Random();

	public Sensor(int xCoord, int yCoord, Proc sendTo) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.sendTo = sendTo;
		this.node = this;
	}

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

		case AWAKE:
//			listen(time, x); // This is for strategy 2, task 1c
			send(time); // Comment away when using strategy 2
			break;

		case RESEND:
			send(time);
			break;
		}
	}

	/*
	 * Sends a signal to the gateway, schedules a new awakening and then falls back
	 * to sleep.
	 * 
	 * @param At what time the message shall be sent.
	 */
	private void send(double time) {
		SignalList.SendSignal(RECEIVE, sendTo, time + TP, this);

		SignalList.SendSignal(AWAKE, this, time + Math.log(1.0 - slump.nextDouble()) / (1.0 / -TS), this);

		nbrSends++;
	}

	/*
	 * Listens if there are any other transmissions as of now (within the sensors
	 * radius), if so, the sensor falls asleep for a random time and then tries to
	 * send again.
	 * 
	 * @param Time is the time for the event.
	 * 
	 * @param Signal is the instance of this signal-object.
	 * 
	 */
	private void listen(double time, Signal x) {
		boolean send = true;
		Signal temp = x;

		while (temp.prev != null && Math.abs(time - temp.prev.arrivalTime) < 1.0) {
			if (checkPos(temp.prev) && temp.prev.signalType != 2) {
				// If time is less than 1.0 and the Sensor is within radius of this.Sensor, then
				// send becomes false
				send = false;
				break;
			}
			temp = temp.prev;
		}

		if (send) // If no other Sensor within the radius is sending right now
			send(time);
		else // If another Sensor is sending right now
				// Sleep interval: lower bound and upper bound
			SignalList.SendSignal(RESEND, this, time + (double) LB + (double) slump.nextInt((UB - LB) + 1), this);
	}

	/*
	 * This method checks if a certain Sensor-signal is within reach of this.Sensor.
	 * 
	 * @param Signal is the temporary signal.
	 * 
	 * @return If the Sensor-signal is within the radius of this.Sensor, return
	 * true, else return false.
	 * 
	 */
	private boolean checkPos(Signal temp) {
		if (temp.node != null) {
			double x = Math.abs((double) xCoord - (double) temp.node.xCoord);
			double y = Math.abs((double) yCoord - (double) temp.node.yCoord);

			double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

			if (distance <= (double) RADIUS)
				return true;
		}
		return false;
	}
	
	public int getX() {
		return xCoord;
	}
	
	public int getY() {
		return yCoord;
	}
}
