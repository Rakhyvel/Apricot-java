package com.josephs_projects.apricotLibrary.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * This class is used to load images as integer arrays of 32 bit color
 * 
 * @author Joseph Shimel
 *
 */
public class Image {
	/**
	 * Takes a path to an image, its width and height, and returns the image as an
	 * integer array using 32 bit color
	 * 
	 * @param path The path to the image, relative to the src folder
	 * @return The loaded image
	 */	
	public int[] loadImage(String path) {
		BufferedImage image = null;
		try (InputStream iw = getClass().getResourceAsStream(path)){
			image = ImageIO.read(iw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
	}
	
	/**
	 * Converts a gray image to a colored image
	 * 
	 * @param color Color to overlay
	 * @param img Gray image
	 * @return An integer array that represents the new image
	 */
	public int[] overlayBlend(int color, int[] img) {
		int r, g, b, a;
		float screen, alpha;
		int[] img2 = new int[img.length];
		for (int i = 0; i < img.length; i++) {
			alpha = ((img[i] >> 24 & 255) / 255.0f);
			if (alpha < 0.9f)
				continue;
			
			// Finding and splitting starting colors
			screen = (img[i] & 255) / 255.0f;
			a = ((color >> 24) & 255);
			r = ((color >> 16) & 255);
			g = ((color >> 8) & 255);
			b = (color & 255);

			// Setting new colors
			if (screen <= 0.5f) {
				screen *= 2;
				r = (int) (r * screen);
				g = (int) (g * screen);
				b = (int) (b * screen);
			} else {
				r = (int) (255 - 2 * (255 - r) * (1 - screen));
				g = (int) (255 - 2 * (255 - g) * (1 - screen));
				b = (int) (255 - 2 * (255 - b) * (1 - screen));
			}
			// Recombining colors
			img2[i] = (a << 24) | (int) (r * alpha) << 16 | (int) (g * alpha) << 8 | (int) (b * alpha);
		}
		return img2;
	} // overlayBlend()
}
