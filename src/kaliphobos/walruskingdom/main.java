package kaliphobos.walruskingdom;

import kaliphobos.walruskingdom.general.debug;
import kaliphobos.walruskingdom.general.files;
import kaliphobos.walruskingdom.gui.window;
import kaliphobos.walruskingdom.screen.Map;
import kaliphobos.walruskingdom.screen.Screen;

/** The game's main class
 * 
 * @author KaliPhobos
 *
 */
public class main {

	/** The game's main method. */
	public static void main(String[] args) {
		int resolution = 48;
		debug.setInitialTimestamp(System.currentTimeMillis());
		
		Map townMap = new Map(100, 75);

		window gui = new window(resolution);
		gui.setTileWidth(16, false);		// game resolution
		gui.setTileHeight(12, false);
		gui.setTilesize(resolution, true);				// size of tiles used in the game (24px)
		
		
		// Increase screen size by 2 tiles to create a buffer which is necessary for smooth scrolling
		Screen previewScreen = new Screen(gui.getTileWidth()+2, gui.getTileHeight()+2);
		previewScreen.setMap(townMap);
		
		debug.DebugLog(previewScreen.preview());

		

		
		while (true) {
			
			int xOffset = 24;
			int yOffset = 24;
			for (yOffset=resolution; yOffset>=0; yOffset--) {
				xOffset = yOffset;
				gui.pasteToScreen(previewScreen, xOffset, yOffset);
				gui.refresh();
				debug.DebugLog("FRAME");
			}
			previewScreen.walk(1, 1);
			gui.refresh();
		}
		
		


	}
}
