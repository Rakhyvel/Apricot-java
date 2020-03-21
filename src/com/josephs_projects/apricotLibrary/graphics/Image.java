package com.josephs_projects.apricotLibrary.graphics;

import java.awt.Color;
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
	public BufferedImage loadImage(String path) throws IOException {
		InputStream iw = getClass().getResourceAsStream(path);
		return ImageIO.read(iw);
	}

	/**
	 * Converts a gray image to a colored image
	 * 
	 * @param color Color to overlay
	 * @param img   Gray image
	 * @return An integer array that represents the new image
	 */
	public int[] overlayBlend(int color, int[] img) {
		int r, g, b;
		int r2 = 0, g2 = 0, b2 = 0;
		float screen, alpha;
		int[] img2 = new int[img.length];

		r = ((color >> 16) & 255);
		g = ((color >> 8) & 255);
		b = (color & 255);

		for (int i = 0; i < img.length; i++) {
			alpha = ((img[i] >> 24 & 255) / 255.0f);
			screen = (img[i] & 255) / 255.0f;

			// Setting new colors
			if (screen <= 0.5f) {
				screen *= 2;
				r2 = (int) (r * screen);
				g2 = (int) (g * screen);
				b2 = (int) (b * screen);
			} else {
				r2 = (int) (255 - 2 * (255 - r) * (1 - screen));
				g2 = (int) (255 - 2 * (255 - g) * (1 - screen));
				b2 = (int) (255 - 2 * (255 - b) * (1 - screen));
			}
			// Recombining colors
			img2[i] = ((int) (255 * alpha) << 24) | (int) (r2 * alpha) << 16 | (int) (g2 * alpha) << 8
					| (int) (b2 * alpha);
		}
		return img2;
	}
	
	public void overlayBlend(BufferedImage image, Color color) {
		int r, g, b;
		int r2 = 0, g2 = 0, b2 = 0;
		float screen, alpha;

		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();

		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				alpha = ((image.getRGB(x, y) >> 24 & 255) / 255.0f);
				screen = (image.getRGB(x, y) & 255) / 255.0f;

				// Setting new colors
				if (screen <= 0.5f) {
					screen *= 2;
					r2 = (int) (r * screen);
					g2 = (int) (g * screen);
					b2 = (int) (b * screen);
				} else {
					r2 = (int) (255 - 2 * (255 - r) * (1 - screen));
					g2 = (int) (255 - 2 * (255 - g) * (1 - screen));
					b2 = (int) (255 - 2 * (255 - b) * (1 - screen));
				}
				// Recombining colors
				int newColor = ((int) (255 * alpha) << 24) | (int) (r2 * alpha) << 16 | (int) (g2 * alpha) << 8
						| (int) (b2 * alpha);
				image.setRGB(x, y, newColor);
			}
		}
	}
	
	public BufferedImage overlayBlendOutOfPlace(BufferedImage image, Color color) {
		BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int r, g, b;
		int r2 = 0, g2 = 0, b2 = 0;
		float screen, alpha;

		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();

		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				alpha = ((image.getRGB(x, y) >> 24 & 255) / 255.0f);
				screen = (image.getRGB(x, y) & 255) / 255.0f;

				// Setting new colors
				if (screen <= 0.5f) {
					screen *= 2;
					r2 = (int) (r * screen);
					g2 = (int) (g * screen);
					b2 = (int) (b * screen);
				} else {
					r2 = (int) (255 - 2 * (255 - r) * (1 - screen));
					g2 = (int) (255 - 2 * (255 - g) * (1 - screen));
					b2 = (int) (255 - 2 * (255 - b) * (1 - screen));
				}
				// Recombining colors
				int newColor = ((int) (255 * alpha) << 24) | (int) (r2 * alpha) << 16 | (int) (g2 * alpha) << 8
						| (int) (b2 * alpha);
				temp.setRGB(x, y, newColor);
			}
		}
		return temp;
	}
}
