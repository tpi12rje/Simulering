import java.util.ArrayList;


public class Global{
	public static final int AWAKE = 1, COMPLETE = 2, LISTEN = 3, DISTURBANCE = 4;
	public static double time = 0;
	public static final double TS = 4000.0;
	public static final double TP = 1.0;
	public static final int RADIUS = 7000;	// Change to the right radius for 1d [6, 7, 8, 9, 10, 11] km
	public static final int NBR = 10000;	// Change to right amount of sensors [1k, 2k, 3k, 4k, 5k, 6k, 7k, 8k, 9k, 10k]
	public static final int LB = 1000;	// Choose the lower bound, vary & compare performance.
	public static final int UB = 1500;	// Choose the upper bound , vary & compare performance.
	public static int nbrSuccess = 0, nbrSends = 0, nbrBlocked = 0;
	public static int totalSuccess = 0, totalSends = 0, totalBlocked = 0;
	public static ArrayList<Node> nodes = new ArrayList<>();
	public static ArrayList<Node> nodesTransmit = new ArrayList<>();
}
