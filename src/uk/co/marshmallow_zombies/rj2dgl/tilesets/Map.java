package uk.co.marshmallow_zombies.rj2dgl.tilesets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Map {

	private int width, height;
	private List<Tileset> tilesets;

	public Map(int w, int h) {
		this.width = w;
		this.height = h;
		this.tilesets = new ArrayList<Tileset>();
	}

	public void addTileset(Tileset tileset) {
		tilesets.add(tileset);
	}

	public static Map load(String mapFile) {
		Map map = null;

		try {
			File file = new File(mapFile);

			/*
			 * DocumentBuilderBuilder would've made a much better name. And for
			 * that matter, where's my DocumentBuilderBuilderBuilder?
			 */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(file);

			Element mapElement = document.getDocumentElement();
			mapElement.normalize();

			int w = Integer.valueOf(mapElement.getAttribute("width"));
			int h = Integer.valueOf(mapElement.getAttribute("height"));

			map = new Map(w, h);

			// Get tilesets
			NodeList mapTilesets = mapElement.getElementsByTagName("tileset");
			for (int i = 0; i < mapTilesets.getLength(); i++) {
				Element tilesetElement = (Element) mapTilesets.item(i);
				
				
			}

		} catch (Exception e) {
			map = null;
		}

		return map;
	}
};