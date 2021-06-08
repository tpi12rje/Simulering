import java.util.*;

public class ConfigWriter {

	public static void main(String[] args) {
		Random slump = new Random();
		int xC;
		int yC;
		String coord;
		
		SimpleFileWriter w10 = new SimpleFileWriter("10kSensors.txt", false);
		
		Set s10 = new TreeSet();	// Remove 5000 5000
		for (int i = 0; i < 10000; i++) {
			xC = slump.nextInt(10001);
			yC = slump.nextInt(10001);
			coord = Integer.toString(xC) + " " + Integer.toString(yC);
			while (!s10.add(coord)) {
				xC = slump.nextInt(10001);
				yC = slump.nextInt(10001);
				coord = Integer.toString(xC) + " " + Integer.toString(yC);
			}
			w10.println(coord);
		}	
		
		
		
		w10.close();
	}
}