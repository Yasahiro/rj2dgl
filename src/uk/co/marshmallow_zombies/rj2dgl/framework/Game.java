package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.awt.Graphics;

public class Game {

	protected Window window;
	protected ResourceController res = new ResourceController();
	protected Screen screen;
	protected boolean running = false;

	public Game() {
		window = new Window(this);
		screen = new Screen(window.panel);
//		screen = new Screen();
	}
	
	public void exit() {
		running = false;
	}

	protected void init() {
		running = true;
		window.init();
		window.setVisible(true);
		
		window.panel.startRepaintThread();
	}
	
	protected void start() {
		
	}
	
	protected void stop() {
		
	}
	
	protected void tick(long delta) {
		
	}

	protected void render(Graphics g, long delta) {

	}

};
