package dinosws.walruskingdom.engine;

import dinosws.walruskingdom.visual.GameWindow;

/** An interface that game screens implement to be loaded into a game window. */
public interface GameScreen {
	/** Called, when the screen is loaded or resumed. */
	public void onEnable(GameWindow window);
	
	/** Called, when the screen is unloaded or paused. */
	public void onDisable(GameWindow window);
	
	/** Updates the screen state and gives opportunity to re-draw the graphics. */
	public void onUpdate(GameWindow window, int delta);
	
	/** The title of the game screen. */
	public String getTitle();
}
