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

		
		
		/*
		String jsonData = files.ReadFromFile("/kaliphobos/walruskingdom/assets/Maps.json");
		System.out.println(jsonData);
		files.readJSON(jsonData);
		*/
		while (true) {
			for (int xOffset=48; xOffset>0; xOffset--) {
				gui.pasteToScreen(previewScreen, xOffset, 0);
				gui.refresh();
				debug.DebugLog("FRAME");
			}
			previewScreen.walk(1, 0);
		}
		
		


	}
}
