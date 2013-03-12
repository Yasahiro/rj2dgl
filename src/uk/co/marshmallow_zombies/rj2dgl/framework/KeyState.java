package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of key states for use with {@code KeyboardState} calls.
 * 
 * @author Oliver Davenport (12033278)
 */
public enum KeyState {

	/**
	 * Key is released.
	 */
	UP(0),

	/**
	 * Key is pressed.
	 */
	DOWN(1);

	// The state in its integer form
	private final int state;
	// A variable to map the integer values to their enum state values
	private static final Map<Integer, KeyState> map = new HashMap<Integer, KeyState>();

	static {
		// Iterate through all key states
		for (KeyState state : KeyState.values()) {
			// Add the state to the map
			map.put(state.getValue(), state);
		}
	}

	/**
	 * Initialises a new {@code KeyState} with the passed state in its integer
	 * form.
	 * 
	 * @param value
	 *            The state in its integer form.
	 */
	KeyState(int value) {
		// Store the state passed into the constructor
		this.state = value;
	}

	/**
	 * Gets the state in its integer form.
	 * 
	 * @return Returns an {@code int} which represents the state.
	 */
	public int getValue() {
		// Return the state
		return this.state;
	}

	/**
	 * Determines the state which is represented by {@code value}.
	 * 
	 * @param value
	 *            The integer form of a {@code KeyState}.
	 * @return Returns a {@code KeyState} which was represented by {@code value}
	 *         .
	 */
	public static KeyState fromInt(int value) {
		// Determine the state from the value passed
		KeyState state = map.get(value);

		if (state == null)
			// Return the default value if the passed value was null
			return KeyState.UP;

		// Return the state
		return state;
	}

};
