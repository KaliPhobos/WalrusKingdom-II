package kaliphobos.walruskingdom.screen;

import kaliphobos.walruskingdom.general.debug;

/** Contains a one dimensional list of MapTile elements.
 * Each MapTile element is a combination of two Tile items for both fore- and background.
 * Since the data structure is one dimensional, you can access item[x][y] as item[x*this.width+y]
 * 
 * @author KaliPhobos
 *
 */
public class Map {
	private MapTile[] data;
	private int width;
	private int height;
	
	public Map() {
		this.width = 100;
		this.height = 100;
		this.data = new MapTile[this.width*this.height];
		for (int y=0; y<this.height; y++) {
			for (int x=0; x<this.width; x++) {
				int id = (y*this.width+x)%26;
				this.data[y*this.width+x] = new MapTile(new Tile(id, id, false), new Tile(id, id, false));
			}
		}
		debug.DebugLog("Map Object initialized as "+this.width+"x"+this.height);
	}
	
	public Map(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.data = new MapTile[this.width*this.height];
		for (int x=0; x<this.width-1; x++) {
			for (int y=0; y<this.height-1; y++) {
				int id = (y*this.width+x)%26;
				this.data[y*this.width+x] = new MapTile(new Tile(id, id, false), new Tile(id, id, false));
			}
		}
		debug.DebugLog("Map Object initialized as "+this.width+"x"+this.height);
	}
	
	/** Will return the entire map's raw data as a MapTile[] */
	public MapTile[] getData() {
		return this.data;
	}
	
	/** Will return the map's width */
	public int getWidth() {
		return this.width;
	}
	
	/** Will return the map's height */
	public int getHeight() {
		return this.height;
	}
	
}
