package uk.co.marshmallow_zombies.tmxloader;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MapObject implements IPropertiesHolder {

	private HashMap<String, String> properties;

	private BufferedImage image;
	private String name;
	private String type;
	private int gid;
	private int x, y;
	private int width, height;

	private ObjectGroup parentObjectGroup;

	MapObject(ObjectGroup parent, String name, String type, int gid, int x, int y, int width, int height) {
		this.name = name;
		this.type = type;
		this.gid = gid;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.properties = new HashMap<String, String>();

		this.parentObjectGroup = parent;
	}

	MapObject(ObjectGroup parent, String name, String type, int x, int y, int width, int height) {
		this(parent, name, type, -1, x, y, width, height);
	}

	MapObject(ObjectGroup parent, String name, String type, int gid, int x, int y) {
		this(parent, name, type, gid, x, y, -1, -1);
	}

	void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public ObjectGroup getParentObjectGroup() {
		return parentObjectGroup;
	}

	public boolean hasGID() {
		return gid > 0;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getGID() {
		return gid;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	void addProperty(String name, String value) {
		properties.put(name, value);
	}

	@Override
	public String getPropertyString(String name) {
		try {
			return properties.get(name);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public int getPropertyInteger(String name) {
		try {
			return Integer.valueOf(properties.get(name));
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public float getPropertyFloat(String name) {
		try {
			return Float.valueOf(properties.get(name));
		} catch (Exception e) {
			return 0f;
		}
	}

	@Override
	public boolean getPropertyBool(String name) {
		try {
			return Boolean.valueOf(properties.get(name));
		} catch (Exception e) {
			return false;
		}
	}

};
