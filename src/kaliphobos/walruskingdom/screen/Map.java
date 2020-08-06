package kaliphobos.walruskingdom.screen;

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
				this.data[y*this.width+x] = new MapTile(new Tile(y*this.width+x, y*this.width+x, false), new Tile(y*this.width+x, y*this.width+x, false));
			}
		}
		System.out.println("Map Object initialized as "+this.width+"x"+this.height);
	}
	
	public Map(final int width, final int height) {
		this.width = width;
		this.height = height;
		this.data = new MapTile[this.width*this.height];
		for (int x=0; x<this.width-1; x++) {
			for (int y=0; y<this.height-1; y++) {
				System.out.println("x="+x+" y="+y);
				this.data[y*this.width+x] = new MapTile(new Tile(y*this.width+x, y*this.width+x, false), new Tile(y*this.width+x, y*this.width+x, false));

			}
		}
		System.out.println("Map Object initialized as "+this.width+"x"+this.height);
	}
	
	public MapTile[] getData() {
		return this.data;
	}
	
	public int getWidth() {
		return this.width;
	}
	
}
