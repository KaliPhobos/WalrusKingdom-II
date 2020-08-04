package kaliphobos.walruskingdom.screen;

/** A single tile, given a fixed id
 * 
 * @author KaliPhobos
 *
 */
public class Tile {
	private final int id;
	private boolean isSolid;
	private int pictureId;
//	private final Trigger TileTrigger;
	
	/** Default constructor
	 * 
	 * @param id the tile's id which it will be called by (to be used it world map data)
	 * @param picId the tile's position in the given assets file. Usually equals the tile's id
	 * @param isSolid determines whether the tile may be entered by any character.
	 */
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
