package uk.co.marshmallow_zombies.tmxloader;

import java.util.ArrayList;
import java.util.List;

public class ObjectGroup extends Layer {

	private List<MapObject> objects;

	ObjectGroup(int index, String name, int width, int height) {
		super(index, name, width, height);
		this.objects = new ArrayList<MapObject>();
	}

	public void addObject(MapObject object) {
		objects.add(object);
	}

	public MapObject[] getObjects() {
		return objects.toArray(new MapObject[objects.size()]);
	}

};
