package com.josephs_projects.library.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Image {

	public static int[] loadImage(String path) {
		Image image = new Image();
		return image.loadImageInterface(path);
	}
	
	/**
	 * Takes a path to an image, its width and height, and returns the image as
	 * an integer array using 32 bit color
	 * 
	 * @param path
	 *            The path to the image, relative to the src folder
	 * @param width
	 *            The width of the image
	 * @param height
	 *            The height of the image
	 * @return The loaded image
	 */
	private int[] loadImageInterface(String path) {
		BufferedImage image = null;
		try {
			InputStream iw = getClass().getResourceAsStream(path);
			image = ImageIO.read(iw);
			iw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
	}
}
