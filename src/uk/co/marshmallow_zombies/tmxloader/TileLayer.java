package uk.co.marshmallow_zombies.tmxloader;

import java.util.ArrayList;
import java.util.List;

public class TileLayer extends Layer {

	private List<Tile> tiles;

	TileLayer(int index, String name, int width, int height) {
		super(index, name, width, height);
		this.tiles = new ArrayList<Tile>();
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public Tile[] getTiles() {
		return tiles.toArray(new Tile[tiles.size()]);
	}

};
