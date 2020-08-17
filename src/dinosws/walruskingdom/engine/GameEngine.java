package dinosws.walruskingdom.engine;

import dinosws.walruskingdom.visual.GameWindow;

/** An interface that game engines implement to be loaded into a game window. */
public interface GameEngine {
	/** Called, when the engine is loaded or resumed. */
	public void onEnable(GameWindow window);
	
	/** Called, when the engine is unloaded or paused. */
	public void onDisable(GameWindow window);
	
	/** Updates the engine state and gives opportunity to re-draw the graphics. */
	public void onUpdate(GameWindow window, int delta);
	
	/** The title of the game. */
	public String getTitle();
}
