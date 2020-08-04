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
	
	public TileArea(int width, int height, int tileSize) {
		this.tileSize = tileSize;
		screenWidth = width;
		screenHeight = height;
		this.tileSource = new TileSource("", this.tileSize);
		this.bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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
	
	/** draws a regular tile used inside the game (map) */
	public void drawTile(int tilex, int tiley, int x, int y) {
		Graphics2D g = this.bufImage.createGraphics();
	    g.drawImage(this.tileSource.getTile(tilex, tiley), x, y, this.tileSize, this.tileSize, null);
	}
}