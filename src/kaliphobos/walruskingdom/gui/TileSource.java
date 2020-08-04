package kaliphobos.walruskingdom.gui;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import kaliphobos.walruskingdom.general.debug;

/** Manages the BufferedImage's source files and id-to-tile conversions
 * 
 * @author c4ooo@CodeWalrus, KaliPhobos
 *
 */
public class TileSource extends Component {
	private BufferedImage bufImage;
	private int tileSize;
	
	/** Reads the source image file used for map tiles in the game */
	public TileSource(String filename, int tileSize) {
		this.tileSize = tileSize;
		try {
			this.bufImage = ImageIO.read(TileSource.class.getResourceAsStream(filename));
		}
		catch (Exception e) {		// Unable to read source data
			JOptionPane.showMessageDialog(null, "Harambe died for our sins.");
			e.printStackTrace();
		}
	}
	
	/** Determines the accurate x position for a given tile ID inside a 10 tile wide source image */
	public static int getXPos(int id) {
		return (id%10);
	}
	
	/** Determines the accurate y position for a given tile ID inside a 10 tile wide source image */
	public static int getYPos(int id) {
		return (id-(id%10))/10;
	}
	
	/** Returns a tile from the instance's default tile source with it's default tile resolution */
	public BufferedImage getTile(int tilex, int tiley) {
		if (this.bufImage == null) {
			return null;
		}
		try {
			return this.bufImage.getSubimage(tilex*(this.tileSize+1)+1, tiley*(this.tileSize+1)+1, this.tileSize, this.tileSize);
		}
		catch(Exception e){
			debug.DebugLog("Couldnt load tile subimage.If BLOCKSIZE==" + this.tileSize + " is correct, better check the sourcefile.");
			return new BufferedImage(0, 0, 0);
		}
	}
	public BufferedImage getTile(int tileXmin, int tileYmin, int tileXdim, int tileYdim) {
		if (this.bufImage == null) {
			return null;
		}
		try {
			return this.bufImage.getSubimage(tileXmin, tileYmin, tileXdim-tileXmin, tileYdim-tileYmin);
		}
		catch(Exception e){
			debug.DebugLog("Couldnt load tile subimage. If BLOCKSIZE==" + this.tileSize + " is correct, better check the sourcefile.");
			return new BufferedImage(0, 0, 0);
		}
	}
}
