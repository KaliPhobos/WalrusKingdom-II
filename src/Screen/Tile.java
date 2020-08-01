package Screen;

public class Tile {
	// A single tile, given a fixed id
	private final int id;
	private boolean isSolid;
	private int pictureId;
//	private final Trigger TileTrigger;
	
	public Tile(int id, int picId, boolean isSolid) {
		this.id = id;
		this.pictureId = picId;
		this.isSolid = isSolid;
//		this.TileTrigger = null;
	}
	
/*	public Tile(int id, int picId, boolean isSolid, Trigger trigger) {
		this.id = id;
		this.pictureId = picId;
		this.isSolid = isSolid;
		this.TileTrigger = trigger;
	}*/
	
	public int getId() {
		return this.id;
	}
	
	public boolean isSolid() {
		return this.isSolid;
	}
	
	public int getPictureId() {
		return this.pictureId;
	}
}
