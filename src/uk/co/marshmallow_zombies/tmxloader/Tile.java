package uk.co.marshmallow_zombies.tmxloader;

import java.awt.image.BufferedImage;

public class Tile {

	private int gid;
	private BufferedImage image;
	private TileLayer parentLayer;

	Tile(TileLayer parent, int gid, BufferedImage image) {
		this.gid = gid;
		this.image = image;

		this.parentLayer = parent;
	}

	Tile(int gid, BufferedImage image) {
		this(null, gid, image);
	}

	void setParentLayer(TileLayer parent) {
		parentLayer = parent;
	}

	public TileLayer getParentLayer() {
		return parentLayer;
	}

	public int getGID() {
		return gid;
	}

	public BufferedImage getImage() {
		return image;
	}

	public static final Tile EMPTY = new Tile(0, null);

};
