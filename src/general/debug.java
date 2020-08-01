package general;

public class debug {
	
	public String toString(int[][] data) {
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
	
	public String toString(int[] data) {
		// returns an int array as a string
		StringBuffer sb= new StringBuffer();
		for (int x=0; x<data.length; x++) {
			sb.append(data[x]);
			sb.append(", ");
		}
		return sb.toString();
	}
}
