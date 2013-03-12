package uk.co.marshmallow_zombies.tmxloader;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import uk.co.marshmallow_zombies.rj2dgl.framework.Path;

public class Map implements IPropertiesHolder {

	private HashMap<String, String> properties;

	private int width, height, tilewidth, tileheight;
	private List<Tileset> tilesets;
	private List<Layer> layers;
	private Color backgroundColor;

	public Map(int w, int h, int tilewidth, int tileheight, Color backgroundColor) {
		this.width = w;
		this.height = h;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
		this.properties = new HashMap<String, String>();
		this.tilesets = new ArrayList<Tileset>();
		this.layers = new ArrayList<Layer>();
		this.backgroundColor = backgroundColor;

		System.out.printf("New map ready with size %dx%d\n", w, h);
	}

	/**
	 * Adds a tileset to the map.
	 * 
	 * @param tileset
	 *            The {@code Tileset} to add
	 */
	void addTileset(Tileset tileset) {
		tilesets.add(tileset);
	}

	/**
	 * Adds a property to the map.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param value
	 *            The value of the property.
	 */
	void addProperty(String name, String value) {
		properties.put(name, value);
	}

	public MapObject[] getCollisionObject(Rectangle rect) {
		List<MapObject> objects = new ArrayList<MapObject>();

		// Iterate through the layers
		for (int i = 0; i < layers.size(); i++) {
			// Get the layer
			Layer layer = layers.get(i);

			// Is it an object group?
			if (layer instanceof ObjectGroup) {
				// We want all the objects
				MapObject[] layerObjects = ((ObjectGroup) layer).getObjects();
				for (int j = 0; j < layerObjects.length; j++) {
					MapObject object = layerObjects[j];
					// Does it have a GID?
					if (object.hasGID()) {
						// We need the tile width/height
						Tileset tileset = getTilesetByGID(object.getGID());
						int tilewidth = tileset.getTileWidth();
						int tileheight = tileset.getTileHeight();
						int objectx1 = object.getX();
						int objecty1 = object.getY();
						int objectx2 = tilewidth;
						int objecty2 = tileheight;

						Rectangle rectangle = new Rectangle(objectx1, objecty1, objectx2, objecty2);

						if (rect.intersects(rectangle)) {
							objects.add(object);
						}
					} else {
						int objectx1 = object.getX();
						int objecty1 = object.getY();
						int objectx2 = object.getWidth();
						int objecty2 = object.getHeight();

						Rectangle rectangle = new Rectangle(objectx1, objecty1, objectx2, objecty2);

						if (rect.intersects(rectangle)) {
							objects.add(object);
						}
					}
				}
			}
		}

		return objects.toArray(new MapObject[objects.size()]);
	}

	public MapObject getObjectAtPoint(int x, int y, String layerName) {
		// Iterate through the layers
		for (int i = 0; i < layers.size(); i++) {
			// Get the layer
			Layer layer = layers.get(i);

			// Is it an object group?
			if (layer instanceof ObjectGroup) {
				// Does the name match?
				if (layer.getName().equals(layerName)) {
					// We want all the objects
					MapObject[] objects = ((ObjectGroup) layer).getObjects();
					for (int j = 0; j < objects.length; j++) {
						MapObject object = objects[j];

						// Does it have a GID?
						if (object.hasGID()) {
							// We need the tile width/height
							Tileset tileset = getTilesetByGID(object.getGID());
							int tilewidth = tileset.getTileWidth();
							int tileheight = tileset.getTileHeight();

							// Scan by tile region
							if (x >= object.getX() && x <= object.getX() + tilewidth && y >= object.getY()
									&& y <= object.getY() + tileheight)
								return object;
						} else {
							// Scan by object region
							if (x >= object.getX() && x <= object.getX() + object.getWidth() && y >= object.getY()
									&& y <= object.getY() + object.getHeight())
								return object;
						}
					}
				}
			}
		}

		return null;
	}

	public MapObject getObjectAtPoint(int x, int y, int layerIndex) {
		// Iterate through the layers
		for (int i = 0; i < layers.size(); i++) {
			// Get the layer
			Layer layer = layers.get(i);

			// Is it an object group?
			if (layer instanceof ObjectGroup) {
				// Does the name match?
				if (layer.getLayerIndex() == layerIndex) {
					// We want all the objects
					MapObject[] objects = ((ObjectGroup) layer).getObjects();
					for (int j = 0; j < objects.length; j++) {
						MapObject object = objects[j];

						// Does it have a GID?
						if (object.hasGID()) {
							// We need the tile width/height
							Tileset tileset = getTilesetByGID(object.getGID());
							int tilewidth = tileset.getTileWidth();
							int tileheight = tileset.getTileHeight();

							// Scan by tile region
							if (x >= object.getX() && x <= object.getX() + tilewidth && y >= object.getY()
									& y >= object.getY() + tileheight)
								return object;
						} else {
							// Scan by object region
							if (x >= object.getX() && x <= object.getX() + object.getWidth() && y >= object.getY()
									&& y <= object.getY() + object.getHeight())
								return object;
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Adds a layer to the map.
	 * 
	 * @param layer
	 *            The {@code Layer} to add.
	 */
	void addLayer(Layer layer) {
		layers.add(layer);
	}

	/**
	 * Gets the map height in tiles.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the map width in tiles.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the tile height.
	 */
	public int getTileHeight() {
		return tileheight;
	}

	/**
	 * Gets the tile width.
	 */
	public int getTileWidth() {
		return tilewidth;
	}

	/**
	 * Gets the map background color.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Gets the top-most layer on the map.
	 * 
	 * @return Returns the latest loaded {@code Layer}.
	 */
	public Layer getTopLayer() {
		return layers.get(layers.size() - 1);
	}

	/**
	 * Gets all the layers in the map.
	 * 
	 * @return Returns an array of type {@code Layer} representing the layers on
	 *         the map.
	 */
	public Layer[] getLayers() {
		return layers.toArray(new Layer[layers.size()]);
	}

	/**
	 * Gets a string property.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return Returns a {@code String} representing the property's value.
	 */
	public String getPropertyString(String name) {
		try {
			return properties.get(name);
		} catch (Exception e) {
			// No such property
			return "";
		}
	}

	/**
	 * Gets an integer property.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return Returns an {@code int} representing the property's value.
	 */
	public int getPropertyInteger(String name) {
		try {
			return Integer.valueOf(properties.get(name));
		} catch (Exception e) {
			// Wasn't an integer
			return 0;
		}
	}

	/**
	 * Gets a float property.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return Returns a {@code float} representing the property's value.
	 */
	public float getPropertyFloat(String name) {
		try {
			return Float.valueOf(properties.get(name));
		} catch (Exception e) {
			// Wasn't a float
			return 0f;
		}
	}

	/**
	 * Gets a boolean property.
	 * 
	 * @param name
	 *            The name of the property.
	 * @return Returns a {@code boolean} representing the property's value.
	 */
	public boolean getPropertyBool(String name) {
		try {
			return Boolean.valueOf(properties.get(name));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Loads a non-encoded TMX map from file.
	 * 
	 * @param mapFile
	 *            The file to read.
	 * @return Returns a {@code Map} representing the map file.
	 */
	public static Map load(File mapFile) {
		Map map = null;

		try {
			System.out.printf("Determined mapfile: %s\n", mapFile.getAbsolutePath());

			// Get the document and root element
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(mapFile);
			Element mapElement = document.getRootElement();

			// Get map attributes
			Color backgroundColor = new Color(128, 128, 128);
			String color = mapElement.getAttributeValue("backgroundcolor");
			if (color != null && !color.isEmpty())
				backgroundColor = hex2Rgb(color);
			int w = Integer.valueOf(mapElement.getAttributeValue("width"));
			int h = Integer.valueOf(mapElement.getAttributeValue("height"));
			int maptilewidth = Integer.valueOf(mapElement.getAttributeValue("tilewidth"));
			int maptileheight = Integer.valueOf(mapElement.getAttributeValue("tileheight"));
			int layerIndex = 0;

			// Create a map
			map = new Map(w, h, maptilewidth, maptileheight, backgroundColor);

			List<Element> mapProperties = mapElement.getChildren("properties");

			for (int p = 0; p < mapProperties.size(); p++) {
				Element propertiesElement = (Element) mapProperties.get(p);
				List<Element> propertiesProperties = propertiesElement.getChildren("property");
				for (int l = 0; l < propertiesProperties.size(); l++) {
					Element propertyElement = (Element) propertiesProperties.get(l);

					String propertyName = propertyElement.getAttributeValue("name");
					String propertyValue = propertyElement.getAttributeValue("value");

					map.addProperty(propertyName, propertyValue);
				}
			}

			if (mapProperties.size() == 0)
				System.out.printf("Map has no custom properties\n", mapProperties.size());
			else
				System.out.printf("Loading %d map properties\n", mapProperties.size());

			// Get map's tilesets
			List<Element> mapTilesets = mapElement.getChildren("tileset");

			for (int i = 0; i < mapTilesets.size(); i++) {
				Element tilesetElement = (Element) mapTilesets.get(i);

				int firstgid = Integer.valueOf(tilesetElement.getAttributeValue("firstgid"));
				String name = tilesetElement.getAttributeValue("name");
				int tilewidth = Integer.valueOf(tilesetElement.getAttributeValue("tilewidth"));
				int tileheight = Integer.valueOf(tilesetElement.getAttributeValue("tileheight"));

				Tileset tileset = new Tileset(firstgid, name, tilewidth, tileheight);

				// Get tileset's images
				List<Element> tilesetImages = tilesetElement.getChildren("image");
				for (int j = 0; j < tilesetImages.size(); j++) {
					Element imageElement = (Element) tilesetImages.get(j);

					String source = imageElement.getAttributeValue("source");

					String path = Path.getDirectoryName(mapFile.getAbsolutePath());
					path = String.format("%s/%s", path, source);
					File imageFile = new File(path);

					tileset.setImage(ImageIO.read(imageFile));
				}

				map.addTileset(tileset);
			}

			System.out.printf("Loaded %d tilesets\n", mapTilesets.size());

			int layerCount = 0, objectGroupCount = 0;

			// Now we need layers
			List<Element> mapChildren = mapElement.getChildren();
			for (int i = 0; i < mapChildren.size(); i++) {
				// Get the current element
				Element mapChild = mapChildren.get(i);

				// If we're working with a <layer>
				if (mapChild.getName().equalsIgnoreCase("layer")) {
					layerCount++;

					// Get the attributes (name, width, height)
					String name = mapChild.getAttributeValue("name");
					int width = Integer.valueOf(mapChild.getAttributeValue("width"));
					int height = Integer.valueOf(mapChild.getAttributeValue("height"));
					String stropacity = mapChild.getAttributeValue("opacity");
					float opacity = 1f;

					if (stropacity != null && !stropacity.isEmpty())
						opacity = Float.valueOf(stropacity);

					List<Element> layerProperties = mapElement.getChildren("properties");

					for (int p = 0; p < layerProperties.size(); p++) {
						Element propertiesElement = (Element) layerProperties.get(p);
						List<Element> propertiesProperties = propertiesElement.getChildren("property");

						for (int l = 0; l < propertiesProperties.size(); l++) {
							Element propertyElement = (Element) propertiesProperties.get(l);

							String propertyName = propertyElement.getAttributeValue("name");
							String propertyValue = propertyElement.getAttributeValue("value");

							map.addProperty(propertyName, propertyValue);
						}
					}

					// Get the <data>/<tile> children
					TileLayer layer = new TileLayer(++layerIndex, name, width, height);
					Element layerData = mapChild.getChildren("data").get(0);
					List<Element> layerTiles = layerData.getChildren("tile");

					layer.setOpacity(opacity);

					for (int t = 0; t < layerTiles.size(); t++) {
						// Get the current <tile>
						Element tileElement = layerTiles.get(t);

						// Get attributes (gid)
						int gid = Integer.valueOf(tileElement.getAttributeValue("gid"));

						if (gid == 0) {
							Tile tile = Tile.EMPTY;
							tile.setParentLayer(layer);
							layer.addTile(tile);
						} else {
							// Get the image
							BufferedImage image = map.getImageByGID(gid);

							// Create a new tile and add it
							Tile tile = new Tile(layer, gid, image);
							layer.addTile(tile);
						}
					}

					map.addLayer(layer);
				} else if (mapChild.getName().equalsIgnoreCase("objectgroup")) {
					objectGroupCount++;

					// Get the attributes (name, width, height)
					String name = mapChild.getAttributeValue("name");
					int width = Integer.valueOf(mapChild.getAttributeValue("width"));
					int height = Integer.valueOf(mapChild.getAttributeValue("height"));
					String stropacity = mapChild.getAttributeValue("opacity");
					float opacity = 1f;

					if (stropacity != null && !stropacity.isEmpty())
						opacity = Float.valueOf(stropacity);

					List<Element> layerProperties = mapElement.getChildren("properties");

					for (int p = 0; p < layerProperties.size(); p++) {
						Element propertiesElement = (Element) layerProperties.get(p);
						List<Element> propertiesProperties = propertiesElement.getChildren("property");

						for (int l = 0; l < propertiesProperties.size(); l++) {
							Element propertyElement = (Element) propertiesProperties.get(l);

							String propertyName = propertyElement.getAttributeValue("name");
							String propertyValue = propertyElement.getAttributeValue("value");
							map.addProperty(propertyName, propertyValue);
						}
					}

					ObjectGroup objectgroup = new ObjectGroup(++layerIndex, name, width, height);
					objectgroup.setOpacity(opacity);

					// Get the <object> children
					List<Element> layerObjects = mapChild.getChildren("object");
					for (int j = 0; j < layerObjects.size(); j++) {
						Element layerObject = layerObjects.get(j);

						// Get <object> attributes
						String strObjectName = layerObject.getAttributeValue("name");
						String strObjectType = layerObject.getAttributeValue("type");
						String strObjectGID = layerObject.getAttributeValue("gid");
						String strObjectX = layerObject.getAttributeValue("x");
						String strObjectY = layerObject.getAttributeValue("y");
						String strObjectWidth = layerObject.getAttributeValue("width");
						String strObjectHeight = layerObject.getAttributeValue("height");

						// Convert known attributes
						int objectX = Integer.valueOf(strObjectX);
						int objectY = Integer.valueOf(strObjectY);

						// And fill in ones we don't know
						int objectGID = -1;
						int objectWidth = -1;
						int objectHeight = -1;

						// Now check if they exist
						if (strObjectGID != null && !strObjectGID.isEmpty())
							// object gid
							objectGID = Integer.valueOf(strObjectGID);
						if (strObjectWidth != null && !strObjectWidth.isEmpty())
							// object width
							objectWidth = Integer.valueOf(strObjectWidth);
						if (strObjectHeight != null && !strObjectHeight.isEmpty())
							// object height
							objectHeight = Integer.valueOf(strObjectHeight);

						// And now we can create the object
						MapObject object = new MapObject(objectgroup, strObjectName, strObjectType, objectGID, objectX,
								objectY, objectWidth, objectHeight);

						if (object.hasGID()) {
							int gid = object.getGID();

							// Get and set the image
							BufferedImage image = map.getImageByGID(gid);
							object.setImage(image);
						}

						List<Element> objectProperties = layerObject.getChildren("properties");
						for (int p = 0; p < objectProperties.size(); p++) {
							Element propertiesElement = (Element) objectProperties.get(p);
							List<Element> propertiesProperties = propertiesElement.getChildren("property");

							for (int l = 0; l < propertiesProperties.size(); l++) {
								Element propertyElement = (Element) propertiesProperties.get(l);

								String propertyName = propertyElement.getAttributeValue("name");
								String propertyValue = propertyElement.getAttributeValue("value");
								if (propertyName != null && propertyValue != null)
									object.addProperty(propertyName, propertyValue);
							}
						}

						// Add it to the layer
						objectgroup.addObject(object);
					}

					map.addLayer(objectgroup);
				}
			}
			System.out.printf("Loaded %d layers and %d objectgroups\n", layerCount, objectGroupCount);
		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}

		System.out.printf("Map fully loaded\n");

		return map;
	}

	BufferedImage getImageByGID(int gid) {
		// Get the tileset that owns 'gid'
		Tileset tileset = getTilesetByGID(gid);

		// Get the tile width, tile height and image of the
		// tileset
		int tileWidth = tileset.getTileWidth();
		int tileHeight = tileset.getTileHeight();
		BufferedImage image = tileset.getImage();

		// Get the GID relative to the tileset
		int firstgid = tileset.getFirstGID();
		int tileGID = gid % firstgid;
		if (firstgid == 1) {
			tileGID = gid / firstgid;
		}

		// Useful calculation to turn INDEX into
		// X/Y-coordinates
		// and vice-versa
		// i = x + (y * w)
		// x = i % w
		// y = i / w
		int x = tileGID % (image.getWidth() / tileHeight);
		int y = tileGID / (image.getHeight() / tileHeight);

		// Crop the image
		return image.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
	}

	Tileset getTilesetByGID(int gid) {
		for (int i = 0; i < tilesets.size(); i++) {
			Tileset tileset = tilesets.get(i);
			int firstgid, lastgid;
			firstgid = tileset.getFirstGID();

			int width = tileset.getImage().getWidth();
			int height = tileset.getImage().getHeight();
			int tilewidth = tileset.getTileWidth();
			int tileheight = tileset.getTileHeight();

			lastgid = firstgid + ((width / tilewidth) * (height / tileheight)) - 1;

			if (gid >= firstgid && gid <= lastgid) {
				return tileset;
			}
		}

		return null;
	}

	/**
	 * Converts a string hexadecimal color into a {@code java.awt.Color}.
	 * 
	 * @param colorStr
	 *            The hexadecimal color (is acceptable with or without a '#')
	 * @return Returns a {@code Color} which best represents {@code colorStr}.
	 */
	static Color hex2Rgb(String colorStr) {
		if (!colorStr.startsWith("#")) {
			colorStr = String.format("#%s", colorStr);
		}

		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}
};