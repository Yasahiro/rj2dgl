package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

class KeyboardInputListener implements KeyListener {

	private List<Keys> keyList;

	KeyboardInputListener() {
		keyList = new ArrayList<Keys>();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		Keys key = Keys.fromInt(arg0.getKeyCode());

		if (!keyList.contains(key))
			keyList.add(key);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		Keys key = Keys.fromInt(arg0.getKeyCode());

		if (keyList.contains(key))
			keyList.remove(key);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	List<Keys> getKeyList() {
		return keyList;
	}

};
