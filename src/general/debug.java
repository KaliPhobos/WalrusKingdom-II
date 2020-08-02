package general;

public class debug {
	private static long initialTimestamp;
	private static long lastTimestamp;
	public static void setInitialTimestamp(long timestamp) {
		initialTimestamp = timestamp;
		lastTimestamp = timestamp;
	}
	
	public static String toString(int[][] data) {
		// returns a 2D int array as a string
		StringBuffer sb= new StringBuffer();
		for (int y=0; y<data[0].length; y++) {
			for (int x=0; x<data.length; x++) {
				sb.append(data[x][y]);
				sb.append(", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static String toString(int[] data) {
		// returns an int array as a string
		StringBuffer sb= new StringBuffer();
		for (int x=0; x<data.length; x++) {
			sb.append(data[x]);
			sb.append(", ");
		}
		return sb.toString();
	}
	
	public static void DebugLog(String message) {
		long time = System.currentTimeMillis();
		System.out.println((time-initialTimestamp)/1000.0 + "s (+" + (time-lastTimestamp)/1000.0 + "s)   " + message);
		lastTimestamp = System.currentTimeMillis();
	}
}
