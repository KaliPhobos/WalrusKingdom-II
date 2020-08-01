package Screen;

public class MapTile {
	// A combination of a foreground and background Tile item, making up the game map
	private Tile foregroundTile;
	private Tile backgroundTile;
	
	public MapTile(Tile foreground, Tile background) {
		this.foregroundTile = foreground;
		this.backgroundTile = background;
	}
	
	public Tile getForeground() {
		return this.foregroundTile;
	}
	public Tile getBackground() {
		return this.backgroundTile;
	}
}
