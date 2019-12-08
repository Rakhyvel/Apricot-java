package com.josephs_projects.library.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private int[] pixels;
	private final int WIDTH;

	public SpriteSheet(String path, int width) {
		pixels = loadImageArray(path);
		WIDTH = width;
	}

	/**
	 * Loads an image for the spritesheet
	 * 
	 * @param path
	 *            The path to the image
	 * @return The loaded image
	 */
	private int[] loadImageArray(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource(path));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if (img == null) {
			return pixels;
		}
		return img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
	}

	/**
	 * Returns a square subimage from the spritesheet
	 * 
	 * @param x
	 *            The x coordinate of the subimage
	 * @param y
	 *            The y coordinate of the subimage
	 * @param width
	 *            The width (and height) of the subimage
	 * @return The subimage
	 */
	public int[] getSubset(int x, int y, int width) {
		int[] temp = new int[width * width];
		int m = ((y * width) * WIDTH) + (x * width);
		int n = WIDTH - width;
		for (int i = 0; i < width * width; i++, m++) {
			if (i % width == 0) {
				m += n;
			}
			temp[i] = pixels[m - n];
		}
		return temp;
	}
}
