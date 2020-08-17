package kaliphobos.walruskingdom.gui;

import javax.swing.JFrame;

import kaliphobos.walruskingdom.general.debug;
import kaliphobos.walruskingdom.screen.Screen;

/** THe main instance for any graphical output, basically manages the JFrame and anything it touches
 * 
 * @author KaliPhobos
 *
 */
public class window {
	private int pxlWidth = 408;
	private int pxlHeight = 288;
	private int tileWidth = 17;
	private int tileHeight = 12;
	private JFrame frame;
	private int tilesize = 24;
	private TileArea tileArea;
	
	public window() {
		System.out.println("GUI element created w/ default resolution");
	}
	
	public window(int resolution) {
		System.out.println("GUI element created with x"+resolution+" resolution");
		this.tilesize = resolution;
	}
	
	/** Set the frame's width using pixels as a unit.
	 * Will force a GUI reset
	 * 
	 * @param pxlWidth the new width in pixels
	 */
	public void setPxlWidth(int pxlWidth) {
		pxlWidth = confirm(pxlWidth);
		this.pxlWidth = pxlWidth;
		this.tileWidth = pxlWidth/tilesize;
		GUIreset();
	}
	

	/** Set the frame's width using pixels as a unit.
	 * May force a GUI reset depending on the given parameter (which is recommended)
	 * 
	 * @param pxlWidth the new width in pixels
	 * @param doRedraw determines whether to reset the GUI or not
	 */
	public void setPxlWidth(int pxlWidth, boolean doRedraw) {
		pxlWidth = confirm(pxlWidth);
		this.pxlWidth = pxlWidth;
		this.tileWidth = pxlWidth/tilesize;
		if (doRedraw) {
			GUIreset();
		}
	}
	
	/** Set the frame's height using pixels as a unit.
	 * Will force a GUI reset
	 * 
	 * @param pxlHeight the new height in pixels
	 */
	public void setPxlHeight(int pxlHeight) {
		pxlHeight = confirm(pxlHeight);
		this.pxlHeight = pxlHeight;
		this.tileHeight = pxlHeight/tilesize;
		GUIreset();
	}
	
	/** Set the frame's height using pixels as a unit.
	 * May force a GUI reset depending on the given parameter (which is recommended)
	 * 
	 * @param pxlHeight the new height in pixels
	 * @param doRedraw determines whether to reset the GUI or not
	 */
	public void setPxlHeight(int pxlHeight, boolean doRedraw) {
		pxlHeight = confirm(pxlHeight);
		this.pxlHeight = pxlHeight;
		this.tileHeight = pxlHeight/tilesize;
		if (doRedraw) {
			GUIreset();
		}
	}
	
	/** Set the frame's width using tiles as a unit (X * this.tilesize).
	 * Will force a GUI reset
	 * 
	 * @param tileWidth the new width in tiles
	 */
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
		setPxlWidth(tileWidth*tilesize);
	}
	
	/** Set the frame's width using tiles as a unit (X * this.tilesize).
	 * May force a GUI reset depending on the given parameter (which is recommended)
	 * 
	 * @param tileWidth the new width in tiles
	 * @param doRedraw determines whether to reset the GUI or not
	 */
	public void setTileWidth(int tileWidth, boolean doRedraw) {
		this.tileWidth = tileWidth;
		setPxlWidth(tileWidth*tilesize, doRedraw);
	}
	
	/** Set the frame's height using tiles as a unit (X * this.tilesize).
	 * Will force a GUI reset
	 * 
	 * @param tileHeight the new height in tiles
	 */
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
		setPxlHeight(tileHeight*tilesize);
	}
	
	/** Set the frame's width using tiles as a unit (X * this.tilesize).
	 * May force a GUI reset depending on the given parameter (which is recommended)
	 * 
	 * @param tileHeight the new height in tiles
	 * @param doRedraw determines whether to reset the GUI or not
	 */
	public void setTileHeight(int tileHeight, boolean doRedraw) {
		this.tileHeight = tileHeight;
		setPxlHeight(tileHeight*tilesize, doRedraw);
	}
	
	/** Set the frame's tile size, using pixels as a unit.
	 * Will force a GUI reset
	 * 
	 * @param tilesize the new tile size in pixels
	 */
	public void setTilesize(int tilesize) {
		this.tilesize = tilesize;
		setPxlWidth(tileWidth*tilesize, false);
		setPxlHeight(tileHeight*tilesize, true);	// force full redraw
	}
	
	/** Set the frame's tile size, using pixels as a unit.
	 * May force a GUI reset depending on the given parameter
	 * 
	 * @param tilesize the new tile size in pixels
	 * @param doRedraw determines whether to reset the GUI or not
	 */
	public void setTilesize(int tilesize, boolean doRedraw) {
		this.tilesize = tilesize;
		setPxlWidth(tileWidth*tilesize, false);
		setPxlHeight(tileHeight*tilesize, doRedraw);	// force full redraw
	}
	
	/** Boring getter */
	public int getTileWidth() {
		return this.tileWidth;
	}
	
	/** Boring getter */
	public int getTileHeight() {
		return this.tileHeight;
	}
	
	/** Boring getter */
	public int getTileSize() {
		return this.tilesize;
	}
	
	/** Check if a given value can be divided by this.tilesize. If not, paste an error message.
	 * Faulty values will be corrected to the next smaller (fitting) value.
	 * 
	 * @param value the given value which will be checked & corrected
	 * @return the corrected value
	 */
	public int confirm(int value) {
		int temp = value%tilesize;
		if (temp!=0) {
			debug.DebugLog("Input was adapted to fit parameters. ("+value+" -> "+(value-temp)+")");
		}
		return value - temp;
	}
	
	/** This method forces the JFrame element to be removed and then recreated
	 * Effectively this will do a hard GUI reset, fixing any temporary bug
	 * Please avoid using this excessively as full rendering eats up quite some resources
	 */
	public void GUIreset() {
		if (this.frame!=null) {
			// close old window so a new one can be opened
		}
		createWindow();
		tileArea.initialize(200);
		// force full redraw
	}
	
	/** Creates a new instance of the window element 
	 * Multiple instances may be used to try multiple resolutions and texturepacks at once
	 */
	private void createWindow() {
		debug.DebugLog("A new window is being created");
		JFrame frame = new JFrame("WalrusKingdom "+this.pxlWidth+"x"+this.pxlHeight);
		frame.setIgnoreRepaint(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.tileArea = new TileArea(this.pxlWidth, this.pxlHeight, getTileSize());
		frame.add("Center", this.tileArea);
		frame.pack();
		frame.setVisible(true);
	    this.frame = frame;
	}
	
	public void pasteToScreen(Screen screen) {
		refresh();
		for (int y=1; y<screen.getHeight()-3; y++) {
			for (int x=1; x<screen.getWidth()-3; x++) {
				int id = screen.getTile(x, y).getBackground().getPictureId();
				this.tileArea.drawTile(getTileSize()*x, getTileSize()*y, (id%10)*(getTileSize()+1), ((id-(id%10))/10)*(getTileSize()+1), getTileSize(), getTileSize());
				// this.tileArea.drawTile(id, getTileSize()*x, getTileSize()*y, getTileSize(), getTileSize());
				refresh();
			}
		}
	}
	
	public void pasteToScreen(Screen screen, int xOffset, int yOffset) {
		int id = 0;
		int x = 0;
		int y = 0;

		// center tiles
		for (y=1; y<screen.getHeight()-2; y++) {
			for (x=1; x<screen.getWidth()-2; x++) {
				id = screen.getTile(x, y).getBackground().getPictureId();
				this.tileArea.drawTile(id, getTileSize()*(x-1)+xOffset, getTileSize()*(y-1)+yOffset);
			}
		}

		// top tiles
		y = 0;
		if (yOffset != getTileSize()) {
			// Render cut-off tiles only if offset does not equal tileSize
			for (x=1; x<screen.getWidth()-2; x++) {
				if (yOffset != 0) {
					// Tile height 0px if there is no offset
					id = screen.getTile(x, y).getBackground().getPictureId();
					this.tileArea.drawTile(getTileSize()*(x-1)+xOffset, 0, tileArea.getX(id), tileArea.getY(id)+getTileSize()-yOffset, getTileSize(), yOffset);
				}
			}
		} else {
			// Render bottom line as full tiles if offset equals tileSize (faster)
			for (x=1; x<screen.getWidth()-2; x++) {
				id = screen.getTile(x, y).getBackground().getPictureId();
				this.tileArea.drawTile(id, getTileSize()*(x-1)+xOffset, 0);
			}
		}
		
		// top right tile
		if (xOffset != 0 && yOffset != 0) {
			x = this.tileWidth-1;
			y = 0;
			id = screen.getTile(x, y).getBackground().getPictureId();
			if (xOffset == getTileSize() && yOffset == getTileSize()) {
				// Render top right tile as full tile if offsets equal tileSize (faster)
				this.tileArea.drawTile(id, getTileSize()*x, getTileSize()*y);
			} else {
				// SOMETHING IS WRONG HERE
				this.tileArea.drawTile(getTileSize()*x+xOffset, 0, tileArea.getX(id)+getTileSize(), tileArea.getY(id)+getTileSize()-yOffset, xOffset, yOffset);
			}

		}

		// right tiles
		if (xOffset !=0) {
			if (xOffset == getTileSize()) {
				// Render right tiles as full tiles (faster)
				for (y=1; y<screen.getHeight()-2; y++) {
					id = screen.getTile(x, y).getBackground().getPictureId();
					this.tileArea.drawTile(id, this.pxlWidth-getTileSize(), getTileSize()*(y-1)+yOffset);
				}
			} else {
				// Render cut-off partial tiles for right border
				x = getTileWidth()-1;
				for (y=1; y<screen.getHeight()-2; y++) {
					id = screen.getTile(x-3, y).getBackground().getPictureId();
					//id = 162;
					this.tileArea.drawTile(getTileSize()*x+xOffset, getTileSize()*y+yOffset, tileArea.getX(id), tileArea.getY(id), getTileSize()-xOffset, getTileSize());
					
				}
			}
			id = screen.getTile(x, y).getBackground().getPictureId();
			//this.tileArea.drawTile(getTileSize()*(x-1)+xOffset, 0, getX(id), getY(id)+getTileSize()-yOffset, getTileSize(), yOffset);
		}

		refresh();
	}
	
	/** Will refresh the on-screen graphics, only to be called once per intended frame. */
	public void refresh() {
		this.frame.repaint();
	}
}
