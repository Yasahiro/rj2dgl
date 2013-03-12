package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.util.List;

public class Keyboard {

	static KeyboardInputListener listener;

	static void listen() {
		listener = new KeyboardInputListener();
	}	

	public static KeyState getKeyState(Keys key) {
		return isKeyDown(key) ? KeyState.DOWN : KeyState.UP;
	}

	public static Keys[] getPressedKeys() {
		Keys[] keys;
		List<Keys> list = listener.getKeyList();
		keys = list.toArray(new Keys[list.size()]);
		return keys;
	}

	public static boolean isKeyDown(Keys key) {
		List<Keys> keys = listener.getKeyList();
		return keys.contains(key);
	}

	public static boolean isKeyUp(Keys key) {
		return !isKeyDown(key);
	}

};
