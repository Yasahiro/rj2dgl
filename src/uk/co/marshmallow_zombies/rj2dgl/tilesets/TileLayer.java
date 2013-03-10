package uk.co.marshmallow_zombies.rj2dgl.tilesets;

import java.util.List;

public class TileLayer extends Layer {

	private List<Tile> tiles;
	private String name;
	private int width, height;

	TileLayer(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

};
