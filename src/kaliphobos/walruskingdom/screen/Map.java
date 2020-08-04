package kaliphobos.walruskingdom.screen;

/** Contains a 2D array of MapTile elements - a combination of a foreground and background Tile item
 * 
 * @author KaliPhobos
 *
 */
public class Map {
	private MapTile[][] data;
	private int width;
	private int height;
	
	public Map() {
		this.width = 100;
		this.height = 100;
		this.data = new MapTile[this.width][this.height];
		for (int x=0; x<this.data.length; x++) {
			for (int y=0; y<this.data[x].length; y++) {
				this.data[x][y] = new MapTile(new Tile(x+y, x+y, false), new Tile(x*y, x*y, false));
			}
		}
		System.out.println("Map Object initialized as "+this.width+"x"+this.height);
	}
	
	public Map(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.data = new MapTile[this.width][this.height];
		for (int x=0; x<this.data.length; x++) {
			for (int y=0; y<this.data[x].length; y++) {
				this.data[x][y] = new MapTile(new Tile(x+y, x+y, false), new Tile(x*y, x*y, false));
			}
		}
		System.out.println("Map Object initialized as "+this.width+"x"+this.height);
	}
	
	
	public MapTile[][] getData() {
		return this.data;
	}
	
}
