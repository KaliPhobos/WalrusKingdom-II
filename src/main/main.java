package main;

import javax.swing.JFrame;

import Screen.GUI;
import Screen.Map;
import Screen.Screen;

public class main {
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setTileWidth(31, false);		// game resolution
		gui.setTileHeight(17, false);
		gui.setTilesize(24, false);				// size of tiles used in the game (24px)
		
		
		
		Map townMap = new Map(80, 40);
		Screen previewScreen = new Screen(gui.getTileWidth(), gui.getTileHeight());
		previewScreen.update(townMap);
		
		System.out.println(previewScreen.preview());
	}
}
