
import java.util.*;
import java.io.*;

// It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {

		// Design a file format for a configuration file which is read into the
		// simulator at the beginning of a
		// run. The file should then be populated with the configuration of the scenario
		// including all
		// parameters for both the gateway and the sensor nodes (times, coordinates
		// etc.) before the
		// simulation starts using a separate program/script. The file format should be
		// properly documented
		// and explained.

		Signal actSignal;
		new SignalList();
		
		Random slump = new Random();


		// Change to file name for each simulation with new variables
		SimpleFileWriter w = new SimpleFileWriter("Test.txt", false);
		
		// The same file is used for all simulations, however, NBR in the for loop below is the amount of
		// Sensors being created, i.e. how many lines from the configurations file are
		// read for each simulation
		FileReader reader = new FileReader("10kSensors.txt");
		Scanner scan = new Scanner(reader);

		// Inserts nodes into list
		for (int i = 0; i < NBR; i++) {
			while (!scan.hasNextInt())
				scan.next();
			int x = scan.nextInt();

			while (!scan.hasNextInt())
				scan.next();
			int y = scan.nextInt();

			Node n = new Node(x,y);
			nodes.add(n);
			//Strategy 1
//			SignalList.SendSignal(AWAKE, n, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), n);
			//Strategy 2
			SignalList.SendSignal(LISTEN, n, time + Math.log(1 - slump.nextDouble()) / (-1.0 / TS), n);


		}

		scan.close();

		int runs = 0;

		// This is the main loop
		while (runs < 30) {
			// The signal list is started and actSignal is declared. actSignal is the latest
			// signal that has been fetched from the
			// signal list in the main loop below.
			
			while (time < 100000) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
			}

			// Write the result into a file with help of the file writer
			w.print("Successfull " + Integer.toString(nbrSuccess) + " ");
			w.print("Sent " + Integer.toString(nbrSends) + " ");
			w.print("Throughput " + Double.toString(((double) nbrSuccess / (double) time)) + "% ");
			w.print("Blocked " + Integer.toString(nbrBlocked) + " ");
			w.print("Radius " + Integer.toString(RADIUS) + " ");

			w.println("");
			
			// Before next run, declare the values again
			new SignalList();
			totalSuccess += nbrSuccess;
			nbrSuccess = 0;
			totalSends += nbrSends;
			nbrSends = 0;
			totalBlocked += nbrBlocked;
			nbrBlocked = 0;
			time = 0;
			nodesTransmit.clear();
			// This simulation is over
			//prepare nodes for next run

			
			//Strategy 1
//			for (Node n : nodes) {
//				SignalList.SendSignal(AWAKE, n, time + Math.log(1 - slump.nextDouble()) / (-(1.0 / TS)), n);
//			}
			
			//Strategy 2
			for (Node n : nodes) {
				SignalList.SendSignal(LISTEN, n, time + Math.log(1 - slump.nextDouble()) / (-(1.0 / TS)), n);
			}

			//update nbr of runs + sanity check
			runs++;
			System.out.println(runs);
		}
		
		w.close();

		System.out.println("Throughput " + ((double)totalSuccess/(double)runs) / (double)100000);
		double ts = (double) totalSends / runs;
		System.out.println("Transmissions sent " + ts);
		double tb = (double) totalBlocked / runs;
		System.out.println("Transmissions blocked " + tb);

		System.out.println("Colision probability " + tb / ts);
	}
}