package dinosws.walruskingdom.engine;

/** An interface that all game engines should follow. */
public interface GameEngine {
	/** Called, when the engine is loaded or resumed. */
	public void onEnable();
	
	/** Called, when the engine is unloaded or paused. */
	public void onDisable();
	
	/** The title of the game. */
	public String getTitle();
}
