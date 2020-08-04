package kaliphobos.walruskingdom.general;

/** A class used for debugging outputs, logging and various mathematical operations.
 * All 'General' classes contain generalized multiple purpose code, meant to be used not only in this project.
 * 
 * @author KaliPhobos
 *
 */
public class debug {
	private static long initialTimestamp;
	private static long lastTimestamp;
	public static void setInitialTimestamp(long timestamp) {
		initialTimestamp = timestamp;
		lastTimestamp = timestamp;
	}
	
	/** returns a 2D integer array as a string */
	public static String toString(int[][] data) {
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
	
	/** returns an integer array as a string */
	public static String toString(int[] data) {
		StringBuffer sb= new StringBuffer();
		for (int x=0; x<data.length; x++) {
			sb.append(data[x]);
			sb.append(", ");
		}
		return sb.toString();
	}
	
	/** creates a debug output which also includes a timestamp as well as the time passed since the method's last call. */
	public static void DebugLog(String message) {
		long time = System.currentTimeMillis();
		System.out.println((time-initialTimestamp)/1000.0 + "s (+" + (time-lastTimestamp)/1000.0 + "s)   " + message);
		lastTimestamp = System.currentTimeMillis();
	}
}
