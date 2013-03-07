package uk.co.marshmallow_zombies.rj2dgl.tilesets;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Map {

	public Map() {

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

			document.getDocumentElement().normalize();

		} catch (Exception e) {
			map = null;
		}

		return map;
	}
};