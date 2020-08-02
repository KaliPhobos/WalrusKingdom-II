package main;

import GUI.window;
import Screen.Map;
import Screen.Screen;
import General.debug;

public class main {

	public static void main(String[] args) {
		debug.setInitialTimestamp(System.currentTimeMillis());
		window gui = new window();
		gui.setTileWidth(31, false);		// game resolution
		gui.setTileHeight(17, false);
		gui.setTilesize(24, true);				// size of tiles used in the game (24px)
		
		
		Map townMap = new Map(80, 40);
		Screen previewScreen = new Screen(gui.getTileWidth(), gui.getTileHeight());
		previewScreen.update(townMap);
		
		System.out.println(previewScreen.preview());
		
	}
}
