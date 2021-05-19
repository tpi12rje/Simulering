//import java.util.*;
//
//public class ConfigWriter {
//
//	public static void main(String[] args) {
//		Random slump = new Random();
//		int xC;
//		int yC;
//		String coord;
//		
//		SimpleFileWriter w10 = new SimpleFileWriter("10kSensors.txt", false);
//		
//		Set s10 = new TreeSet();	// Remove 50000 50000
//		String gateCoord = Integer.toString(50000) + " " + Integer.toString(50000);
//		s10.add(gateCoord);
//		for (int i = 0; i < 10000; i++) {
//			xC = slump.nextInt(100001);
//			yC = slump.nextInt(100001);
//			coord = Integer.toString(xC) + " " + Integer.toString(yC);
//			while (!s10.add(coord)) {
//				xC = slump.nextInt(100001);
//				yC = slump.nextInt(100001);
//				coord = Integer.toString(xC) + " " + Integer.toString(yC);
//			}
//			w10.println(coord);
//		}	
//		
//		
//		
//		w10.close();
//	}
//}
