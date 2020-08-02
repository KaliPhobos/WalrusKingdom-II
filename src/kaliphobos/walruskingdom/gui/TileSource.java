package GUI;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import kaliphobos.walruskingdom.general.debug;

public class TileSource extends Component {
	private BufferedImage bufImage;
	private int tileSize;
	
	public TileSource(String filename, int tileSize) {
		// Reads the source image file used for map tiles in the game
		this.tileSize = tileSize;
		try {
			this.bufImage = ImageIO.read(TileSource.class.getResourceAsStream(filename));
		}
		catch (Exception e) {		// Unable to read source data
			JOptionPane.showMessageDialog(null, "Harambe died for our sins.");
			e.printStackTrace();
		}
	}

	public static int getXPos(int id) {
		// Determines the accurate x position for a given tile ID inside a 10 tile wide source image
		return (id%10);
	}
	
	public static int getYPos(int id) {
		// Determines the accurate y position for a given tile ID inside a 10 tile wide source image
		return (id-(id%10))/10;
	}
	
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
