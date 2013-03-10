package uk.co.marshmallow_zombies.rj2dgl.tilesets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import uk.co.marshmallow_zombies.rj2dgl.framework.Path;

public class Map {

	private int width, height;
	private List<Tileset> tilesets;
	private List<Layer> layers;

	public Map(int w, int h) {
		this.width = w;
		this.height = h;
		this.tilesets = new ArrayList<Tileset>();
		this.layers = new ArrayList<Layer>();
	}

	public void addTileset(Tileset tileset) {
		tilesets.add(tileset);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void addLayer(Layer layer) {
		layers.add(layer);
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

			// Get map's tilesets
			NodeList mapTilesets = mapElement.getElementsByTagName("tileset");
			for (int i = 0; i < mapTilesets.getLength(); i++) {
				Element tilesetElement = (Element) mapTilesets.item(i);

				int firstgid = Integer.valueOf(tilesetElement.getAttribute("firstgid"));
				String name = tilesetElement.getAttribute("name");
				int tilewidth = Integer.valueOf(tilesetElement.getAttribute("width"));
				int tileheight = Integer.valueOf(tilesetElement.getAttribute("height"));

				Tileset tileset = new Tileset(firstgid, name, tilewidth, tileheight);

				// Get tileset's images
				NodeList tilesetImages = tilesetElement.getElementsByTagName("image");
				for (int j = 0; j < tilesetImages.getLength(); j++) {
					Element imageElement = (Element) tilesetImages.item(j);

					String source = imageElement.getAttribute("source");

					String path = Path.getDirectoryName(file.getAbsolutePath());
					path = String.format("%s/%s", path, source);

					tileset.setImage(ImageIO.read(new File(path)));
				}
			}

			NodeList mapLayers = mapElement.getChildNodes();
			for (int i = 0; i < mapLayers.getLength(); i++) {
				if (!mapLayers.item(i).getNodeName().equalsIgnoreCase("layer")
						|| !mapLayers.item(i).getNodeName().equalsIgnoreCase("objectgroup")) {
					continue;
				}

				if (mapLayers.item(i).getNodeName().equalsIgnoreCase("layer")) {
					Element layerElement = (Element) mapLayers.item(i);

					String name = layerElement.getAttribute("name");
					int width = Integer.valueOf(layerElement.getAttribute("width"));
					int height = Integer.valueOf(layerElement.getAttribute("height"));

					TileLayer layer = new TileLayer(name, width, height);

					NodeList layerTiles = ((Element) mapElement.getElementsByTagName("data").item(0))
							.getElementsByTagName("tile");
					for (int j = 0; j < layerTiles.getLength(); j++) {
						Element tileElement = (Element) layerTiles.item(i);

						int gid = Integer.valueOf(tileElement.getAttribute("gid"));

						if (gid == 0) {
							layer.addTile(Tile.EMPTY);
						} else {
							// i = x + (y * w)
							// x = i % w
							// y = i / w

							Tileset tileset = map.getTilesetByGID(gid);
							int tilewidth = tileset.getTileWidth();
							int tileheight = tileset.getTileHeight();
							BufferedImage image = tileset.getImage();

							int imagewidth = image.getWidth() / tilewidth;
							int tgid = gid % tileset.getFirstGID();

							int x = tgid % imagewidth;
							int y = tgid / imagewidth;

							image = image.getSubimage(x, y, tilewidth, tileheight);

							Tile tile = new Tile(gid, image);
							layer.addTile(tile);
						}
					}

					map.addLayer(layer);
				} else if (mapLayers.item(i).getNodeName().equalsIgnoreCase("objectgroup")) {

				}
			}
		} catch (Exception e) {
			map = null;
		}

		return map;
	}

	public Tileset getTilesetByGID(int gid) {
		for (int i = 0; i < tilesets.size(); i++) {
			Tileset tileset = tilesets.get(i);

			int firstgid, lastgid;
			firstgid = tileset.getFirstGID();

			int width = tileset.getImage().getWidth();
			int height = tileset.getImage().getHeight();
			int tilewidth = tileset.getTileWidth();
			int tileheight = tileset.getTileHeight();

			lastgid = (width / tilewidth) * (height / tileheight);

			if (gid >= firstgid && gid <= lastgid) {
				return tileset;
			}
		}

		return null;
	}
};