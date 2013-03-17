package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The state of the keyboard at the time the instance was initialised.
 * 
 * @author Oliver Davenport (12033278)
 */
public class KeyboardState {

	// List of keys currently active in this state
	Keys[] keys;

	/**
	 * Initialises a new {@code KeyboardState}.
	 * 
	 * @param keys
	 *            Array of parameter list of keys to initialise as pressed.
	 */
	public KeyboardState(Keys... keys) {
		// Store the keys
		this.keys = keys;
	}

	/**
	 * Gets the state of the key passed.
	 * 
	 * @param key
	 *            The key whose state should be determined.
	 * @return Returns a {@code KeyState} which represents the key's current
	 *         state.
	 */
	public KeyState getKeyState(Keys key) {
		// Return the state based on whether or not the key is down
		return isKeyDown(key) ? KeyState.DOWN : KeyState.UP;
	}

	/**
	 * Gets an array of keys that are currently pressed.
	 * 
	 * @return Returns a {@code Keys} array containing all of the keys currently
	 *         pressed.
	 */
	public Keys[] getPressedKeys() {
		// Return the array of keys that were declared as pressed on this state
		return this.keys;
	}

	/**
	 * Determines whether a key is currently pressed.
	 * 
	 * @param key
	 *            The key to check.
	 * @return {@code true} if the key is pressed, otherwise {@code false}.
	 */
	public boolean isKeyDown(Keys key) {
		// Convert the array into a list, so we can use the "contains" method
		List<Keys> keys = new ArrayList<Keys>(Arrays.asList(this.keys));

		// Returns whether or not the array contains the key
		return keys.contains(key);
	}

	/**
	 * DEtermines whether a key is currently released.
	 * 
	 * @param key
	 *            The key to check.
	 * @return {@code true} if the key is released, otherwise {@code false}.
	 */
	public boolean isKeyUp(Keys key) {
		// Return the opposite of what isKeyDown would return.
		// Because that's really all we're doing here...
		return !isKeyDown(key);
	}

};
