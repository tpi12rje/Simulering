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

		Random slump = new Random();
		int runs = 0;
		int xC;
		int yC;
		int index = 0;

		// Change to file name for each simulation with new variables
		SimpleFileWriter w = new SimpleFileWriter("Test.txt", false);

		// The same file is used for all simulations, however, NBR is the amount of
		// Sensors being created, i.e. how many lines from the configurations file are
		// read for each simulation
		FileReader reader = new FileReader("10kSensors.txt"); // Double check
		Scanner scan = new Scanner(reader);

		String temp;
		String[] lines;
		String[] coordinates = new String[NBR * 2];

		// Scan a line in the configuration-file and put it in the lines-array by
		// splitting x- and y-coordinates. Then these are copied into the
		// coordinates-array. This is done for all lines or until a sufficient number of
		// lines have been read. All even positions in the coordinates-array are
		// x-coordinates, and all the uneven positions are y-coordinates.
		do {
			temp = scan.nextLine();
			lines = temp.split(" ");
			coordinates[index] = lines[0];
			coordinates[index + 1] = lines[1];
			index += 2;
		} while (scan.hasNextLine() && index < (NBR * 2));

		scan.close();

		while (runs < 3) {
			// The signal list is started and actSignal is declared. actSignal is the latest
			// signal that has been fetched from the
			// signal list in the main loop below.
			Signal actSignal;
			new SignalList();

			// Initiate a gateway
			Gateway gate = new Gateway();

			// Create n number of sensors as specified in class Global
			for (int i = 0; i < (NBR * 2); i += 2) {
				xC = Integer.parseInt(coordinates[i]);
				yC = Integer.parseInt(coordinates[i + 1]);
				Node node = new Node(xC, yC, gate);
				// Create a new signal to initiate the sensor with an awake event with
				// exponential distribution
				// SignalList.SendSignal(AWAKE, node, time + Math.log(1.0 - slump.nextDouble())
				// / (1.0 / -TS), node);
				// SignalList.SendSignal(AWAKE, node, time + (2/(1/TS)*slump.nextDouble()),
				// node);
				SignalList.SendSignal(AWAKE, node, time +  (lambda) * slump.nextDouble(), node);

			}

			// This is the main loop
			while (time < 100000) {
				actSignal = SignalList.FetchSignal();
				time = actSignal.arrivalTime;
				actSignal.destination.TreatSignal(actSignal);
				// Sanity check

			}

			// Write the result into a file with help of the file writer
			w.print("Successfull " + Integer.toString(nbrSuccess) + " ");
			w.print("Sent " + Integer.toString(nbrSends) + " ");
			w.print(Double.toString(((double) nbrSuccess / (double) nbrSends)) + "% ");
			w.print("Blocked " + Integer.toString(nbrBlocked) + " ");
			w.print(Double.toString(time) + " ");
			w.print(Integer.toString(NBR) + " ");
			w.print(Integer.toString(RADIUS) + " ");

			w.println("");

			// Before next run, declare the values again
			time = 0;
			nbrSuccess = 0;
			nbrSends = 0;
			nbrBlocked = 0;
			signals = new ArrayList<Signal>();

			// This simulation is over, runs is updated
			runs++;
			// Sanity check
			System.out.println(runs);
		}

		w.close();
	}
}