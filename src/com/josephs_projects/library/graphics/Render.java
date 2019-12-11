package com.josephs_projects.library.graphics;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Render extends Canvas {

	private static final long serialVersionUID = 1L;
	int width;
	int height;
	BufferedImage img;
	int[] pixels;

	public Render(int x, int y) {
		width = x;
		height = y;
		img = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Calls all objects to render to pixels int array, draws pixel array to screen.
	 */
	public void render(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * Draws a rectangle, with opacity
	 * 
	 * @param x     The x coordinate of the top left corner of the rectangle
	 * @param y     The y coordinate of the top left corner of the rectangle
	 * @param w     The width of the rectangle
	 * @param h     The height of the rectangle
	 * @param color The color of the rectangle
	 * @param alpha The opacity of the rectangle
	 */
	public void drawRect(int x, int y, int w, int h, int color) {
		// Rejecting rectangles that could not have pixels on screen
		if (x + w < 0)
			return;
		if (x > width)
			return;
		if (y + h < 0)
			return;
		if (y > height)
			return;

		float alpha = ((color >> 24) & 255) / 255.0f;
		for (int i = 0; i < w * h; i++) {
			int x1 = i % w + x;
			int y1 = i / w;
			int id = (y1 + y) * (width + 1) + x1;

			// Left bounds
			// Continue if x coord of rect is less than 0 and the x of this pixel isn't
			// enough to make it up
			if (x < 0 && (i % w) < -x)
				continue;

			// Right bounds
			// Continue if x coord is off screen, wrap to next pixel.
			if (x1 >= width) {
				i += w - (x1 - x) - 1;
				continue;
			}

			// Reject pixel if it's out of screen's bounds
			if (id < 0 || id >= width * height)
				continue;

			int r = ((color >> 16) & 255), g = ((color >> 8) & 255), b = (color & 255);
			int newColor = (int) (r * alpha) << 16 | (int) (g * alpha) << 8 | (int) (b * alpha);

			r = (pixels[id] >> 16) & 255;
			g = (pixels[id] >> 8) & 255;
			b = pixels[id] & 255;
			int newColor2 = (int) (r * (1 - alpha)) << 16 | (int) (g * (1 - alpha)) << 8 | (int) (b * (1 - alpha));
			pixels[id] = newColor + newColor2;
		}
	}

	public void drawRectBorders(int x, int y, int w, int h, int color, int borders) {
		float alpha = ((color >> 24) & 255) / 255.0f;
		for (int i = 0; i < w * h; i++) {
			int x1 = i % w;
			int y1 = i / w;
			int id = (y1 + y) * (width + 1) + x1 + x;
			if (x >= 0 && x < 1025 && id > 0 && id < 1025 * 513) {
				int r = ((color >> 16) & 255), g = ((color >> 8) & 255), b = (color & 255);
				int newColor = (int) (r * alpha) << 16 | (int) (g * alpha) << 8 | (int) (b * alpha);
				r = (pixels[id] >> 16) & 255;
				g = (pixels[id] >> 8) & 255;
				b = pixels[id] & 255;
				int newColor2 = (int) (r * (1 - alpha)) << 16 | (int) (g * (1 - alpha)) << 8 | (int) (b * (1 - alpha));
				// bottom | right | top | left
				if ((((borders & 1) == 1) && x1 < 2) || (((borders & 2) == 2) && y1 < 2)
						|| (((borders & 4) == 4) && x1 > w - 3) || (((borders & 8) == 8) && y1 > h - 3)) {
					pixels[id] = 230 << 16 | 230 << 8 | 230;
				} else {
					pixels[id] = newColor + newColor2;
				}
			}
		}
	}

	/**
	 * Draws an image
	 * 
	 * @param x     The x coordinate of the top left corner of the image
	 * @param y     The y coordinate of the top right corner of the image
	 * @param w     The width of the image
	 * @param image The image
	 */
	public void drawImage(int x, int y, int w, int[] image, float opacity, float rotate) {
		// Initializing some variables
		int h = 1, r, g, b;
		double cos = 0, sin = 0;

		// Finding the height of the image
		if (w > 0) {
			h = image.length / w;
		}

		// Rejecting images that could not have pixels on the screen
		if (x + w < 0)
			return;
		if (x - w > width)
			return;
		if (y + h < 0)
			return;
		if (y - h > height)
			return;

		// Doing division once since division is slow
		float halfW = w / 2;
		float halfH = h / 2;
		float deltaI = 1;

		// Doing cos/sin only if there is rotation since those ops are slow
		if (rotate != 0) {
			deltaI = 0.49f;
			cos = Math.cos(-rotate);
			sin = Math.sin(-rotate);
		}

		// For every pixel...
		for (float i = 0; i < image.length; i += deltaI) {
			// Find alpha of pixel
			float alpha = ((image[(int) i] >> 24 & 255) / 255.0f) * opacity;
			// Pixel is transparent, skip to next pixel
			if (alpha <= 0)
				continue;

			// Calculate position of pixels
			double x1 = (i % w) - halfW; // X coord of pixel on screen, centered around origin
			double y1 = i / w - halfH;   // Y coord of pixel on screen, centered around origin
			double x2 = (int) (x1 + x);  // X coord of pixel on screen, centered around image center
			double y2 = (int) (y1 + y);  // Y coord of pixel on screen, centered around image center
			
			// Rotate pixels if rotation, since rotation is slow
			if (rotate != 0) {
				x2 = (int) (x1 * cos - y1 * sin + x);
				y2 = (int) (x1 * sin + y1 * cos + y);
			}
			// Find the position relative to screen
			int id = (int) (x2 + y2 * (width + 1) + 0.5);

			// Left bounds
			// Continue if x coord of rect is less than 0 and the x of this pixel isn't
			// enough to make it up
			if (x - halfW < 0 && (i % w) < -(x - halfW))
				continue;

			// Right bounds
			// Continue if x coord is off screen, wrap to next pixel.
			if (x2 >= width) {
				i += w - (i % w) - 1;
				continue;
			}

			// Invalid pixel position, skip to next pixel
			if (id <= 0 || id >= width * height)
				continue;

			// Finding alpha
			r = ((image[(int) i] >> 16) & 255);
			g = ((image[(int) i] >> 8) & 255);
			b = (image[(int) i] & 255);
			int newColor = (int) (r * alpha) << 16 | (int) (g * alpha) << 8 | (int) (b * alpha);

			// Finding alpha
			r = ((pixels[id] >> 16) & 255);
			g = ((pixels[id] >> 8) & 255);
			b = (pixels[id] & 255);
			int newColor2 = (int) (r * (1 - alpha)) << 16 | (int) (g * (1 - alpha)) << 8 | (int) (b * (1 - alpha));

			// Finally adding colors to pixel array
			pixels[id] = newColor + newColor2;
		}
	}

	public void drawString(String label, int x, int y, Font font, int color, boolean centered) {
		int carriage = 0;
		int letter;
		int length = font.getStringWidth(label);
		int[] letterImage;
		for (int i = 0; i < label.length(); i++) {
			letter = label.charAt(i);
			if (letter == 7) {
				letterImage = getScreenBlend(250 << 16 | 250 << 8, font.getLetter(letter));
			} else {
				letterImage = getScreenBlend(color, font.getLetter(letter));
			}
			if (centered) {
				drawImage(x + carriage - length / 2 + font.getSize() / 2, y, font.getSize(), letterImage, 1, 0);
			} else {
				drawImage(x + carriage + font.getSize() / 2, y, font.getSize(), letterImage, 1, 0);
			}
			carriage += font.getKern(letter);
		}
	}

	public static int[] getScreenBlend(int color, int[] img) {
		int r, g, b, a;
		float screen, alpha;
		int[] img2 = new int[img.length];
		for (int i = 0; i < img.length; i++) {
			alpha = ((img[i] >> 24 & 255) / 255.0f);
			if (alpha > 0.9f) {
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
		}
		return img2;
	}
}