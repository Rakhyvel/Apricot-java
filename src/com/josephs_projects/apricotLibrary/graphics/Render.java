package com.josephs_projects.apricotLibrary.graphics;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.josephs_projects.apricotLibrary.Apricot;

/**
 * Render is used to quickly draw graphics to the screen and is faster than
 * using AWT's Graphics class
 * 
 * @author Joseph Shimel
 *
 */
public class Render {
	int width;
	int height;
	BufferedImage img;
	int[] pixels;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		img = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	/**
	 * Calls all objects to render to pixels int array, draws pixel array to screen.
	 */
	public void render(Graphics g, JFrame frame) {
		AffineTransform at = new AffineTransform();
		double scale = frame.getWidth() / (double) img.getWidth();
		BufferedImage after = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
		at.scale(scale, scale);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		after = scaleOp.filter(img, after);

		g.drawImage(after, 0, 0, null);
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
	} // drawRect()

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
					pixels[id] = 50 << 24 | 230 << 16 | 230 << 8 | 230;
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
		float invTwoFiftyFive = 1 / 255.0f;

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
			float alpha = ((image[(int) i] >> 24 & 255) * invTwoFiftyFive) * opacity;
			// Pixel is transparent, skip to next pixel
			if (alpha <= 0)
				continue;

			// Calculate position of pixels
			double x1 = (i % w) - halfW; // X coord of pixel on screen, centered around origin
			double y1 = i / w - halfH; // Y coord of pixel on screen, centered around origin
			double x2 = (int) (x1 + x); // X coord of pixel on screen, centered around image center
			double y2 = (int) (y1 + y); // Y coord of pixel on screen, centered around image center

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
	} // drawImage()

	/**
	 * Draws a String to the screen
	 * 
	 * @param text     Text to draw to screen
	 * @param x        X coordinate of the text to draw on screen
	 * @param y        Y coordinate of text to draw on screen
	 * @param font     Font to use
	 * @param color    Color of text
	 * @param centered Whether text is centered
	 */
	public void drawString(String text, int x, int y, Font font, int color, boolean centered) {
		int carriage = 0;
		int letter;
		int length = font.getStringWidth(text);
		int[] letterImage;
		if (centered)
			x -= length / 2;

		for (int i = 0; i < text.length(); i++) {
			letter = text.charAt(i);
			letterImage = Apricot.image.overlayBlend(color, font.getLetter(letter));
			drawImage(x + carriage + font.getSize() / 2, y, font.getSize(), letterImage, 1, 0);
			carriage += font.getKern(letter);
		}
	}

	/**
	 * Returns the 32bit color equivalent
	 * 
	 * @param a Alpha channel, ranges 0-255
	 * @param r Red channel, ranges 0-255
	 * @param g Green channel, ranges 0-255
	 * @param b Blue channel, ranges 0-255
	 * @return 32bit integer color
	 */
	public int color(int a, int r, int g, int b) {
		a = Math.max(0, Math.min(255, a));
		r = Math.max(0, Math.min(255, r));
		g = Math.max(0, Math.min(255, g));
		b = Math.max(0, Math.min(255, b));
		
		return a << 24 | r << 16 | g << 8 | b;
	}
}