package kaliphobos.walruskingdom.screen;

/** The "virtual" screen which represents the currently visible section of the world map
 *  Meant to be used for temporary map overlays which are not to be stored to the map itself
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
		this.data = new MapTile[width][height];
		System.out.println("Default Screen Object initialized");
	}
	
	/** Specialized constructor, allows setting the screen resolution (in tiles) */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.data = new MapTile[width][height];
		System.out.println("Screen Object initialized as "+this.width+"x"+this.height);
	}
	
	/** Synchronization method. Will clone visible parts of the world map to the screen buffer */
	public void update(Map map) {
		MapTile[][] mapData = map.getData();
		for (int x=this.screenPosX; x<(this.screenPosX+this.width); x++) {
			// Copy Map data to screen buffer
			System.arraycopy(mapData[x], this.screenPosY, this.data[x-this.screenPosX], 0, this.height);
		}
	}
	
	/** Debugging method. Will paste the game's current screen buffer to the console */
	public String preview() {
		StringBuffer sb = new StringBuffer();
		sb.append("Background:\n");
		for (int y=0; y<this.data[0].length; y++) {
			for (int x=0; x<this.data.length; x++) {
				sb.append(this.data[x][y].getBackground().getId());
				sb.append(", ");
			}
			sb.append("\n");
		}
		sb.append("Foreground:\n");
		for (int y=0; y<this.data[0].length; y++) {
			for (int x=0; x<this.data.length; x++) {
				sb.append(this.data[x][y].getForeground().getId());
				sb.append(", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
