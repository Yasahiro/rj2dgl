package uk.co.marshmallow_zombies.rj2dgl.tilesets;

import java.awt.image.BufferedImage;

public class Tileset {

	private String name;
	private BufferedImage image;
	private int firstgid, tilewidth, tileheight;

	Tileset(int firstgid, String name, int tilewidth, int tileheight) {
		this.firstgid = firstgid;
		this.name = name;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
	}

	public int getFirstGID() {
		return firstgid;
	}

	public int getTileWidth() {
		return tilewidth;
	}

	public int getTileHeight() {
		return tileheight;
	}

	public String getName() {
		return name;
	}

	void setImage(BufferedImage image) {
		this.image = image;
	}

	BufferedImage getImage() {
		return image;
	}

};
