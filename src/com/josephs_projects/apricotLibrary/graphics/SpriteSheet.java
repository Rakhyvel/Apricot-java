package com.josephs_projects.apricotLibrary.graphics;

import com.josephs_projects.apricotLibrary.Apricot;

/**
 * This class can be used to represent sprite sheets
 * 
 * @author Joseph Shimel
 *
 */
public class SpriteSheet {

	private int[] pixels;
	private final int WIDTH;

	public SpriteSheet(String path, int width) {
		pixels = Apricot.image.loadImage(path);
		WIDTH = width;
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
