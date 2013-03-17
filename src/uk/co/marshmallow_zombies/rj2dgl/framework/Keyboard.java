package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.util.List;

/**
 * The {@code Keyboard} class acts as a middle-man between the game and the
 * keyboard states.
 * 
 * @author Oliver Davenport (12033278)
 */
public class Keyboard {

	// The keyboard listening class
	private static KeyboardInputListener listener;

	/**
	 * Begins the keyboard listener.
	 */
	static void listen() {
		// Instantiate the listener
		listener = new KeyboardInputListener();
	}

	/**
	 * Gets the keyboard listener used by this instance.
	 * 
	 * @return Returns the {@code KeyboardInputListener} used by the instance.
	 */
	static KeyboardInputListener getListener() {
		// Return the listener
		return listener;
	}

	/**
	 * Gets the state of the keyboard.
	 * 
	 * @return Returns a {@code KeyboardState} instance which represents the
	 *         current state of the keyboard.
	 */
	public static KeyboardState getState() {
		try {
			// Collect the list of pressed keys
			List<Keys> list = listener.getKeyList();

			// Cast the lsit to an array
			Keys[] keys = list.toArray(new Keys[list.size()]);

			// Create a new state object for the keyboard
			KeyboardState state = new KeyboardState(keys);

			// Return the state
			return state;
		} catch (Exception e) {
			return null;
		}
	}

};
