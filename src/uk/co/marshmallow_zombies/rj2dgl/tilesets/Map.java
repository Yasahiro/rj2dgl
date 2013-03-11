package uk.co.marshmallow_zombies.rj2dgl.tilesets;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import uk.co.marshmallow_zombies.rj2dgl.framework.Path;

public class Map implements IPropertiesHolder {

	private int width, height, tilewidth, tileheight;
	private List<Tileset> tilesets;
	private List<Layer> layers;
	private HashMap<String, String> properties;
	private Color backgroundColor;

	public Map(int w, int h, int tilewidth, int tileheight, Color backgroundColor) {
		this.width = w;
		this.height = h;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
		this.tilesets = new ArrayList<Tileset>();
		this.layers = new ArrayList<Layer>();
		this.properties = new HashMap<String, String>();
		this.backgroundColor = backgroundColor;

		System.out.printf("New map ready with size %dx%d\n", w, h);
	}

	void addTileset(Tileset tileset) {
		tilesets.add(tileset);
	}

	void addProperty(String name, String value) {
		properties.put(name, value);
	}

	void addLayer(Layer layer) {
		layers.add(layer);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getTileHeight() {
		return tileheight;
	}

	public int getTileWidth() {
		return tilewidth;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Layer[] getLayers() {
		return layers.toArray(new Layer[layers.size()]);
	}

	public String getPropertyString(String name) {
		try {
			return properties.get(name);
		} catch (Exception e) {
			return "";
		}
	}

	public int getPropertyInteger(String name) {
		try {
			return Integer.valueOf(properties.get(name));
		} catch (Exception e) {
			return 0;
		}
	}

	public float getPropertyFloat(String name) {
		try {
			return Float.valueOf(properties.get(name));
		} catch (Exception e) {
			return 0f;
		}
	}

	public boolean getPropertyBool(String name) {
		try {
			return Boolean.valueOf(properties.get(name));
		} catch (Exception e) {
			return false;
		}
	}

	public static Map load(String mapFile) {
		Map map = null;

		try {
			File file = new File(mapFile);

			System.out.printf("Determined mapfile: %s\n", file.getAbsolutePath());

			/*
			 * DocumentBuilderBuilder would've made a much better name. And for
			 * that matter, where's my DocumentBuilderBuilderBuilder?
			 */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(file);

			Element mapElement = document.getDocumentElement();
			mapElement.normalize();

			Color backgroundColor = Color.BLACK;
			if (mapElement.hasAttribute("backgroundcolor")) {
				String color = mapElement.getAttribute("backgroundcolor");
				backgroundColor = hex2Rgb(color);
			}
			int w = Integer.valueOf(mapElement.getAttribute("width"));
			int h = Integer.valueOf(mapElement.getAttribute("height"));
			int maptilewidth = Integer.valueOf(mapElement.getAttribute("tilewidth"));
			int maptileheight = Integer.valueOf(mapElement.getAttribute("tileheight"));

			map = new Map(w, h, maptilewidth, maptileheight, backgroundColor);

			// Get map's tilesets
			NodeList mapProperties = mapElement.getElementsByTagName("properties");
			System.out.printf("Loading properties\n", mapProperties.getLength());

			for (int i = 0; i < mapProperties.getLength(); i++) {
				Element propertiesElement = (Element) mapProperties.item(i);
				NodeList propertiesProperties = propertiesElement.getElementsByTagName("property");

				for (int j = 0; j < propertiesProperties.getLength(); j++) {
					Element propertyElement = (Element) mapProperties.item(i);
					NamedNodeMap properties = propertyElement.getAttributes();
					for (int k = 0; k < properties.getLength(); k++) {
						String name = properties.item(k).getNodeName();
						String value = properties.item(k).getNodeValue();

						map.addProperty(name, value);
					}
				}
			}

			// Get map's tilesets
			NodeList mapTilesets = mapElement.getElementsByTagName("tileset");

			System.out.printf("Found %d tilesets\n", mapTilesets.getLength());

			for (int i = 0; i < mapTilesets.getLength(); i++) {
				Element tilesetElement = (Element) mapTilesets.item(i);

				int firstgid = Integer.valueOf(tilesetElement.getAttribute("firstgid"));
				String name = tilesetElement.getAttribute("name");
				int tilewidth = Integer.valueOf(tilesetElement.getAttribute("tilewidth"));
				int tileheight = Integer.valueOf(tilesetElement.getAttribute("tileheight"));

				Tileset tileset = new Tileset(firstgid, name, tilewidth, tileheight);

				// Get tileset's images
				NodeList tilesetImages = tilesetElement.getElementsByTagName("image");
				for (int j = 0; j < tilesetImages.getLength(); j++) {
					Element imageElement = (Element) tilesetImages.item(j);

					String source = imageElement.getAttribute("source");

					String path = Path.getDirectoryName(file.getAbsolutePath());
					path = String.format("%s/%s", path, source);
					File imageFile = new File(path);

					tileset.setImage(ImageIO.read(imageFile));
				}

				map.addTileset(tileset);
			}

			NodeList mapLayers = mapElement.getChildNodes();
			for (int i = 0; i < mapLayers.getLength(); i++) {
				if (mapLayers.item(i).getNodeName().equalsIgnoreCase("layer")) {
					Element layerElement = (Element) mapLayers.item(i);

					String name = layerElement.getAttribute("name");
					int width = Integer.valueOf(layerElement.getAttribute("width"));
					int height = Integer.valueOf(layerElement.getAttribute("height"));

					TileLayer layer = new TileLayer(name, width, height);

					NodeList layerData = ((Element) mapElement).getElementsByTagName("data");
					NodeList layerTiles = ((Element) layerData.item(0)).getElementsByTagName("tile");

					for (int j = 0; j < layerTiles.getLength(); j++) {
						Element tileElement = (Element) layerTiles.item(j);

						int gid = Integer.valueOf(tileElement.getAttribute("gid"));

						if (gid == 0) {
							layer.addTile(Tile.EMPTY);
						} else {
							Tileset tileset = map.getTilesetByGID(gid);
							int tilewidth = tileset.getTileWidth();
							int tileheight = tileset.getTileHeight();
							BufferedImage image = tileset.getImage();

							int tilegid = gid % tileset.getFirstGID();

							// Useful calculation to turn INDEX into
							// X/Y-coordinates
							// and vice-versa
							// i = x + (y * w)
							// x = i % w
							// y = i / w
							int x = tilegid % (image.getWidth() / tileheight);
							int y = tilegid / (image.getHeight() / tileheight);

							image = image.getSubimage(x * tilewidth, y * tileheight, tilewidth, tileheight);

							Tile tile = new Tile(gid, image);
							layer.addTile(tile);
						}
					}
					
					map.addLayer(layer);
				} else if (mapLayers.item(i).getNodeName().equalsIgnoreCase("objectgroup")) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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

			lastgid = firstgid + ((width / tilewidth) * (height / tileheight));

			if (gid >= firstgid && gid <= lastgid) {
				return tileset;
			}
		}

		return null;
	}

	static Color hex2Rgb(String colorStr) {
		if (!colorStr.startsWith("#")) {
			colorStr = String.format("#%s", colorStr);
		}

		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}
};