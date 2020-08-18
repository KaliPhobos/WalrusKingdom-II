package dinosws.walruskingdom;

import java.awt.Dimension;

import dinosws.walruskingdom.screen.NotificationScreen;
import dinosws.walruskingdom.screen.TestScreen;
import dinosws.walruskingdom.visual.GameWindow;
import kaliphobos.walruskingdom.general.debug;
import kaliphobos.walruskingdom.gui.window;
import kaliphobos.walruskingdom.screen.Map;
import kaliphobos.walruskingdom.screen.Screen;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//oldMain();
		TestScreen screen = new TestScreen();
		GameWindow window = new GameWindow("WalrusKindgom-II", screen, new Dimension(800, 600), null, false);
		window.setDisplayingTitle(true);
		window.setDisplayingStats(true);
		window.setUpdateInterval(40);
		window.showWindow();
	}
	
	public static void oldMain() {
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
			int xOffset = resolution/2;
			int yOffset = resolution/2;
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
