package GUI;

import javax.swing.JFrame;

public class window {
	private int pxlWidth = 408;
	private int pxlHeight = 288;
	private int tileWidth = 17;
	private int tileHeight = 12;
	private JFrame frame;
	private int tilesize = 24;
	
	public window() {
		System.out.println("GUI element created");
	}
	
	public void setPxlWidth(int pxlWidth) {
		// Set the frame's width using pixels as a unit.
		// Will force a GUI reset
		pxlWidth = confirm(pxlWidth);
		this.pxlWidth = pxlWidth;
		this.tileWidth = pxlWidth/tilesize;
		GUIreset();
	}
	

	
	public void setPxlWidth(int pxlWidth, boolean doRedraw) {
		// Set the frame's width using pixels as a unit.
		// May force a GUI reset depending on the given parameter (which is recommended)
		pxlWidth = confirm(pxlWidth);
		this.pxlWidth = pxlWidth;
		this.tileWidth = pxlWidth/tilesize;
		if (doRedraw) {
			GUIreset();
		}
	}
	
	public void setPxlHeight(int pxlHeight) {
		// Set the frame's height using pixels as a unit.
		// Will force a GUI reset
		pxlHeight = confirm(pxlHeight);
		this.pxlHeight = pxlHeight;
		this.tileHeight = pxlHeight/tilesize;
		GUIreset();
	}
	
	public void setPxlHeight(int pxlHeight, boolean doRedraw) {
		// Set the frame's width using pixels as a unit.
		// May force a GUI reset depending on the given parameter (which is recommended)
		pxlHeight = confirm(pxlHeight);
		this.pxlHeight = pxlHeight;
		this.tileHeight = pxlHeight/tilesize;
		if (doRedraw) {
			GUIreset();
		}
	}
	
	public void setTileWidth(int tileWidth) {
		// Set the frame's width using tiles as a unit (X * this.tilesize).
		// Will force a GUI reset
		this.tileWidth = tileWidth;
		setPxlWidth(tileWidth*tilesize);
	}
	
	public void setTileWidth(int tileWidth, boolean doRedraw) {
		// Set the frame's width using tiles as a unit (X * this.tilesize).
		// May force a GUI reset depending on the given parameter (which is recommended)
		this.tileWidth = tileWidth;
		setPxlWidth(tileWidth*tilesize, doRedraw);
	}
	
	public void setTileHeight(int tileHeight) {
		// Set the frame's height using tiles as a unit (X * this.tilesize).
		// Will force a GUI reset
		this.tileHeight = tileHeight;
		setPxlHeight(tileHeight*tilesize);
	}
	
	public void setTileHeight(int tileHeight, boolean doRedraw) {
		// Set the frame's width using tiles as a unit (X * this.tilesize).
		// May force a GUI reset depending on the given parameter (which is recommended)
		this.tileHeight = tileHeight;
		setPxlHeight(tileHeight*tilesize, doRedraw);
	}
	
	public void setTilesize(int tilesize) {
		// Set the frame's tile size, using pixels as a unit.
		// Will force a GUI reset
		this.tilesize = tilesize;
		setPxlWidth(tileWidth*tilesize, false);
		setPxlHeight(tileHeight*tilesize, true);	// force full redraw
	}
	
	public void setTilesize(int tilesize, boolean doRedraw) {
		// Set the frame's tile size, using pixels as a unit.
		// May force a GUI reset depending on the given parameter
		this.tilesize = tilesize;
		setPxlWidth(tileWidth*tilesize, false);
		setPxlHeight(tileHeight*tilesize, doRedraw);	// force full redraw
	}
	
	public int getTileWidth() {
		return this.tileWidth;
	}
	
	public int getTileHeight() {
		return this.tileHeight;
	}
	
	public int getTileSize() {
		return this.tilesize;
	}
	
	public int confirm(int value) {
		// Check if a given value can be divided by this.tilesize. If not, paste an error message.
		// Faulty values will be corrected to the next smaller (fitting) value.
		int temp = value%tilesize;
		if (temp!=0) {
			System.out.println("Input was adapted to fit parameters. ("+value+" -> "+(value-temp)+")");
		}
		return value - temp;
	}
	
	public void GUIreset() {
		// This method forces the JFrame element to be removed and then recreated
		// Effectively this will do a hard GUI reset, fixing any temporary bug
		if (this.frame!=null) {
			// close old window so a new one can be opened
		}
		createWindow();
		// force full redraw
	}
	
	private void createWindow() {
		// Pretty obvious, it *creates window*
		System.out.println("A new window is being created");
		JFrame frame = new JFrame("WalrusKingdom "+this.pxlWidth+"x"+this.pxlHeight);
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add("Center", new TileArea(this.pxlWidth, this.pxlHeight, getTileSize()));
		frame.pack();
	    frame.setVisible(true);
	    this.frame = frame;
	}
}
