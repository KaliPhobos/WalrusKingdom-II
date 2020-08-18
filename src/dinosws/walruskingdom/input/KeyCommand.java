package dinosws.walruskingdom.input;

import java.awt.event.KeyEvent;

/** An enum representing the various, supported keyboard key commands. */
public enum KeyCommand {
	/** The up-key. */
	UP(KeyEvent.VK_W),
	
	/** The down-key. */
	DOWN(KeyEvent.VK_S),
	
	/** The left-key. */
	LEFT(KeyEvent.VK_A),
	
	/** The right-key. */
	RIGHT(KeyEvent.VK_D),
	
	/** The (inter-)action-key. */
	ACTION(KeyEvent.VK_SPACE),
	
	/** The menu-key. */
	MENU(KeyEvent.VK_ESCAPE);
	
	/** The default AWT key mapped to this command. */
	public final int defaultKey;
	
	/** Internal constructor. */
	private KeyCommand(int defaultKey) {
		// Copy the value
		this.defaultKey = defaultKey;
	}
	
	/** The list of all element types, indexed by their ordinal. */
	private static final KeyCommand[] types = values();
	
	/** Returns the number of elements in the enum. */
	public static int count()
	{
		return types.length;
	}
	
	/** Returns the element type matching the given ordinal. */
	public static KeyCommand fromOrdinal(int ordinal)
	{
		// Sanity check the input
		if (ordinal >= types.length || ordinal < 0)
			return null;
		
		// Return the matching enum value
		return types[ordinal];
	}
}
