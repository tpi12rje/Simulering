// This class defines a signal. What can be seen here is a minimum. If one wants to add more
// information just do it here. 

class Signal {
	public Proc destination;
	public double arrivalTime;
	public int signalType;
	public Signal next;
	// In order to reach the previous signal, which in turn will make the list
	// double linked
	public Signal prev;
	// Send the Sensor object within the Signal in order to reach other Sensors
	public Sensor node;
}
