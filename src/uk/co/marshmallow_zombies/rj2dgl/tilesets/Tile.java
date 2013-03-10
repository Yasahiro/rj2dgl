package uk.co.marshmallow_zombies.rj2dgl.tilesets;

import java.awt.image.BufferedImage;

public class Tile {

	private int gid;
	private BufferedImage image;

	Tile(int gid, BufferedImage image) {
		this.gid = gid;
		this.image = image;
	}

	public int getGID() {
		return gid;
	}

	public BufferedImage getImage() {
		return image;
	}

	public static final Tile EMPTY = new Tile(0, null);

};
