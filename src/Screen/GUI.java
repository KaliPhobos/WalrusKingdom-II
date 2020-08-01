package Screen;

import javax.swing.JFrame;

public class GUI {
	private int pxlWidth = 408;
	private int pxlHeight = 288;
	private int tileWidth = 17;
	private int tileHeight = 12;
	private JFrame frame;
	private int tilesize = 24;
	
	public GUI() {
		System.out.println("GUI element created");
	}
	
	public void setPxlWidth(int pxlWidth) {
		this.pxlWidth = pxlWidth;
		this.tileWidth = pxlWidth/tilesize;
		GUIreset();
	}
	public void setPxlWidth(int pxlWidth, boolean doRedraw) {
		this.pxlWidth = pxlWidth;
		this.tileWidth = pxlWidth/tilesize;
		if (doRedraw) {
			GUIreset();
		}
	}
	public void setPxlHeight(int pxlHeight) {
		this.pxlHeight = pxlHeight;
		this.tileHeight = pxlHeight/tilesize;
		GUIreset();
	}
	public void setPxlHeight(int pxlHeight, boolean doRedraw) {
		this.pxlHeight = pxlHeight;
		this.tileHeight = pxlHeight/tilesize;
		if (doRedraw) {
			GUIreset();
		}
	}
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
		setPxlWidth(tileWidth*tilesize);
	}
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
		setPxlHeight(tileHeight*tilesize);
	}
	public void setTilesize(int tilesize) {
		this.tilesize = tilesize;
		setPxlWidth(tileWidth*tilesize, false);
		setPxlHeight(tileHeight*tilesize, true);	// force full redraw
	}
	public void GUIreset() {
		// This method forces the JFrame element to be removed and then recreated
		// Effectively this will do a hard GUI reset, fixing any temporary bug
		if (this.frame!=null) {
			// close old window so a new one can be opened
		}
		this.frame = createWindow();
		// force full redraw
	}
	
	
	
	private JFrame createWindow() {
		// Pretty obvious, it *creates window*
		JFrame frame = new JFrame("WalrusKingdom "+this.pxlWidth+"x"+this.pxlHeight);
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(this.pxlWidth, this.pxlHeight);		// set window size
	    return frame;
	}
}
