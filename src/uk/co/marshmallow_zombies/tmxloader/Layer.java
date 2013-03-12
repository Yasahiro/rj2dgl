package uk.co.marshmallow_zombies.tmxloader;

import java.util.HashMap;

public class Layer implements IPropertiesHolder {

	private HashMap<String, String> properties;

	private float opacity = 1f;
	private int index = -1;
	private String name;
	private int width, height;

	Layer(int index, String name, int width, int height) {
		this.index = index;
		this.name = name;
		this.width = width;
		this.height = height;
		this.properties = new HashMap<String, String>();
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

	void setOpacity(float v) {
		opacity = v;
	}

	public float getOpacity() {
		return opacity;
	}

	public int getLayerIndex() {
		return index;
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
