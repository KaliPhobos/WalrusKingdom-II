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
	private Map map;
	MapTile[] data;
	
	/** Main constructor. Will use fallback values for width (8) and height (6) */
	public Screen() {
		this.width = 8;
		this.height = 6;
		this.data = new MapTile[height*width];
		System.out.println("Default Screen Object initialized");
	}
	
	/** Specialized constructor, allows setting the screen resolution (in tiles) */
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.data = new MapTile[height*width];
		System.out.println("Screen Object initialized as "+getHeight()+"x"+getWidth()+" (height x width)");
	}
	
	/** Will return a single tile given it's coordinates or a blank one should there be a border issue */
	public MapTile getTile(int x, int y) {
		// System.out.println("Get Tile: x:"+x+" y:"+y+" tile:"+(y*getWidth()+x));
		int pos = y*getWidth()+x;
		if (pos<0 || pos>=this.data.length) {
			// return a solid black block, basically the supposedly never visible void
			// WHY IS THIS NOT WORKING?
			System.out.println("x:"+x+" y:"+y+" is off the map, going dark");
			return new MapTile(new Tile(0, 0, true), new Tile(0, 0, true));
		} else {
			return this.data[pos];
		}
	}
	
	/** Synchronization method. Will clone visible parts of the world map
	 * (plus a buffer of 2 tiles for smooth scrolling) to the screen buffer
	 * which will then be directly pasted to the graphics output*/
	private void update() {
		MapTile[] mapData = map.getData();
		for (int y=this.screenPosY; y<this.screenPosY+getHeight(); y++) {	// iterates over lines
			System.arraycopy(mapData, map.getWidth()*y+screenPosX, this.data, (y-this.screenPosY)*getWidth(), getWidth());
		}
	}
	
	/** Overwrite the currently used map */
	public void setMap(Map map) {
		this.map = map;
		update();
	}
	
	/** Debugging method. Will paste the game's current screen buffer to the console */
	public String preview() {
		StringBuffer sb = new StringBuffer();
		sb.append("Background:\n");
		for (int y=1; y<getHeight()-1; y++) {
			for (int x=1; x<getWidth()-1; x++) {
				sb.append(getTile(x, y).getBackground().getId());
				sb.append(", ");
			}
			sb.append("\n");
		}
		sb.append("Foreground:\n");
		for (int y=1; y<getHeight()-1; y++) {
			for (int x=1; x<getWidth()-1; x++) {
				sb.append(getTile(x, y).getBackground().getId());
				sb.append(", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	// will rewrite this later, just use it for the time being
	public void walk(int x, int y) {
		this.screenPosX+=x;
		this.screenPosY+=y;
		System.out.println("x:"+this.screenPosX+" y:"+this.screenPosY);
		update();
	}
}
