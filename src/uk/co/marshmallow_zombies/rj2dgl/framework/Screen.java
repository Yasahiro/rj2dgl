package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import uk.co.marshmallow_zombies.rj2dgl.framework.Window.Panel;

public class Screen {

	private Panel c;
	private int w, h;

	Screen(Panel c) {
		this.c = c;
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;

		c.setPreferredSize(new Dimension(w, h));
		Container parent = c.getParent().getParent().getParent(); // Ancestry is
																	// fun!
		Window window = (Window) parent;

		c.canvas = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		window.pack();
		window.centre(false);
		window.pack();
	}

	public int[] getSize() {
		return new int[] { w, h };
	}

	public void clear(Color color) {
		Graphics2D g2d = (Graphics2D) c.canvas.getGraphics();
		g2d.setBackground(color);
		g2d.clearRect(0, 0, w, h);
	}

};
