package kaliphobos.walruskingdom.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/** The drawing field used for any graphics output
 * 
 * @author KaliPhobos
 *
 */
public class TileArea extends Component {
	public static BufferedImage m_image;
	private int screenWidth, screenHeight;
	private int tileSize;
	private BufferedImage bufImage;
	private TileSource tileSource;
	private Graphics2D g;
	private BufferedImage[] tiles;
	
	public TileArea(int width, int height, int tileSize) {
		this.tileSize = tileSize;
		screenWidth = width;
		screenHeight = height;
		this.tileSource = new TileSource("/kaliphobos/walruskingdom/assets/tiles_x24.png", this.tileSize);
		this.bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void initialize(int numOfTiles) {
		this.tiles = new BufferedImage[numOfTiles];
		for (int count=0; count<tileSize; count++) {
			this.tiles[count] = this.tileSource.getTile((count%10)*(tileSize+1), ((count-(count%10))/10)*(tileSize+1), tileSize, tileSize);
		}
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(screenWidth, screenHeight);
    }
 
	public void paint(Graphics g) {
		g.drawImage(this.bufImage, 0, 0, null);
	}
	
	/** updates the BufferedImage's source file to a new one */
	public void setTileSource(TileSource tileSource) {
		this.tileSource = tileSource;
	}
	
	/** Copies an area of the tile source to the screen.
	 * 
	 * @param screenx On-Screen X position
	 * @param screeny On-Screen Y position
	 * @param tilex Source image x position
	 * @param tiley Source image y position
	 */
	public void drawTile(int id, int screenx, int screeny) {
		Graphics2D g = this.bufImage.createGraphics();
	    g.drawImage(tiles[id], screenx, screeny, this.tileSize, this.tileSize, null);
	}
	
	/** Copies an area of the tile source to the screen.
	 * 
	 * @param screenx On-Screen X position
	 * @param screeny On-Screen Y position
	 * @param tilex Source image x position
	 * @param tiley Source image y position
	 * @param tilewidth Image width in pixels
	 * @param tileheight Image height in pixels
	 */
	public void drawTile(int screenx, int screeny, int tilex, int tiley, int tilewidth, int tileheight) {
		Graphics2D g = this.bufImage.createGraphics();
		g.drawImage(this.tileSource.getTile(tilex, tiley, tilewidth, tileheight), screenx, screeny, tilewidth, tileheight, null);
		g.drawImage(this.bufImage, 0, 0, null);
	}
}