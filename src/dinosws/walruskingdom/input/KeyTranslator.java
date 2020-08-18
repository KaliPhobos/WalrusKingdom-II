package dinosws.walruskingdom.input;

/** A translator class that translates key commands to AWT keys and vice versa. */
public class KeyTranslator {
	/** The KeyCommand-indexed array of key mappings. */
	private final int[] keyMap;
	
	/** The constructor. */
	public KeyTranslator() {
		// Allocate the mapping array
		keyMap = new int[KeyCommand.count()];
		
		// Restore the defaults
		restoreDefaults();
	}
	
	/** Restores the key mapping to its defaults. */
	public void restoreDefaults() {
		// Fetch the list of key command enum values
		KeyCommand[] values = KeyCommand.values();
		
		// Iterate over the list and change the mapping
		for (int i = 0; i < values.length; i++) {
			// Get the enum value
			KeyCommand value = values[i];
			
			// Store the mapping
			keyMap[value.ordinal()] = value.defaultKey;
		}
	}
	
	/** Gets the key belonging to a key command. */
	public int getKey(KeyCommand command) {
		// Sanity check the command
		if (command == null)
			return -1;
		
		// Return the key
		return keyMap[command.ordinal()];
	}
	
	/** Sets the key belonging to a key command. */
	public void setKey(KeyCommand command, int key) {
		// Sanity check the command
		if (command == null)
			return;
		
		// Set the key
		keyMap[command.ordinal()] = key;
	}
	
	/** Returns the first key command matching the input key code. */
	public KeyCommand find(int key) {
		// Iterate over the list and return the matching key
		for (int i = 0; i < keyMap.length; i++)
			if (keyMap[i] == key)
				return KeyCommand.fromOrdinal(i);
		
		// Otherwise, return failure
		return null;
	}
}
