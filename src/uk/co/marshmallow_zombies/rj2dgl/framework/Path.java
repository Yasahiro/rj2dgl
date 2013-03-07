package uk.co.marshmallow_zombies.rj2dgl.framework;

import java.io.File;

public class Path {

	public static String getDirectoryName(String path) {
		File file = new File(path);
		String absolutePath = file.getAbsolutePath();
		String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

		return filePath;
	}

};
