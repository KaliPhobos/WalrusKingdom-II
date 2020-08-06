package kaliphobos.walruskingdom.screen;

/** The "virtual" screen which represents the currently visible section of the world map
 *  Meant to be used for temporary map overlays which are not to be stored to the map itself
 *  THIS ARRAY IS ACHITECTURALLY MESSED UP - ACCESS VIA ARRAY[Y][X]
 *  I AM SERIOUSLY SORRY ABOUT THIS MESS D:
 * 
 * @author KaliPhobos
 *
 */
public class Screen {
	
	private int width;
	private int height;
	private int screenPosX = 10;
	private int screenPosY = 10;
	MapTile[][] data;
	
	/** Main constructor. Will use fallback values for width (8) and height (6) */
	public Screen() {
		this.width = 8;
		this.height = 6;
		this.data = new MapTile[height][width];
		System.out.println("Default Screen Object initialized");
	}
	
	/** Specialized constructor, allows setting the screen resolution (in tiles) */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.data = new MapTile[height][width];
		System.out.println("Screen Object initialized as "+this.height+"x"+this.width+" (height x width)");
	}
	
	/** Synchronization method. Will clone visible parts of the world map
	 * (plus a buffer of 2 tiles for smooth scrolling) to the screen buffer
	 * which will then be directly pasted to the graphics output*/
	public void update(Map map) {
		MapTile[] mapData = map.getData();
		System.out.println("Out screen object has an official size of "+this.height+"x"+this.width+" (height x width)");
		System.out.println("Actual size: "+this.data.length+"x"+this.data[0].length);
		for (int y=this.screenPosY; y<this.screenPosY+this.height; y++) {	// iterates over lines
			System.out.println("copy from map's startposition "+(map.getWidth()*y+screenPosY)+" to screen["+(y-this.screenPosY)+"][x] - copy a total of "+(this.width)+" items");
			System.arraycopy(mapData, map.getWidth()*y+screenPosY, this.data[y-this.screenPosY], 0, this.width);
		}
	}
	
	/** Debugging method. Will paste the game's current screen buffer to the console */
	public String preview() {
		StringBuffer sb = new StringBuffer();
		sb.append("Background:\n");
		for (int y=0; y<this.data.length; y++) {
			for (int x=0; x<this.data[0].length; x++) {
				sb.append(this.data[y][x].getBackground().getId());
				sb.append(", ");
			}
			sb.append("\n");
		}
		sb.append("Foreground:\n");
		for (int y=0; y<this.data.length; y++) {
			for (int x=0; x<this.data[0].length; x++) {
				sb.append(this.data[y][x].getForeground().getId());
				sb.append(", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
