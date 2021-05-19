import java.util.ArrayList;

//public class Global{
//	public static final int ARRIVAL = 1, READY = 2, MEASURE = 3;
//	public static double time = 0;
//}

public class Global {
	public static final int AWAKE = 1, COMPLETE = 2, RESEND = 3;
	public static double time = 0;
	public static final double TS = 4000.0;
	public static final double TP = 1.0;
	public static final int RADIUS = 70000;	// Change to the right radius for 1d [6, 7, 8, 9, 10, 11] km
	public static final int NBR = 1000;	// Change to right amount of sensors [1k, 2k, 3k, 4k, 5k, 6k, 7k, 8k, 9k, 10k]
	public static final int LB = 50;	// Choose the lower bound, vary & compare performance.
	public static final int UB = 500;	// Choose the upper bound , vary & compare performance.
	public static final double lambda =0.25; //1/TS * nbr //(4000, 1000)
	public static int nbrSends = 0, nbrSuccess = 0, nbrBlocked = 0;
	public static ArrayList<Signal> signals = new ArrayList<Signal>();
	public static ArrayList<Node> nodes = new ArrayList<Node>();

}