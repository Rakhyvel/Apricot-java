package com.josephs_projects.apricotLibrary.graphics;

import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;

/**
 * Render is used to quickly draw graphics to the screen and is faster than
 * using AWT's Graphics class
 * 
 * @author Joseph Shimel
 *
 */
public class Render {
	BufferedImage img;
	BufferedImage after = img;
	int[] pixels;
	public final int width;
	public int height;
	public int topEdge;
	public int bottomEdge;
	public double scale = 1;

	public Render(int width) {
		img = new BufferedImage(width + 1, width + 1, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		this.width = width;
	}

	/**
	 * Calls all objects to render to pixels int array, draws pixel array to screen.
	 */
	public void render(Graphics g, JFrame frame) {
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				calcDimensions(frame);
			}
		});
		BufferedImage after = new BufferedImage(frame.getContentPane().getWidth(), frame.getContentPane().getWidth(),
				BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(scale, scale);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		after = scaleOp.filter(img, after);

		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		

		g.drawImage(after, 0, frame.getContentPane().getHeight() / 2 + (int) (-frame.getContentPane().getWidth()) / 2,
				null);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] /= 2;
		}
	}
	
	public void calcDimensions(JFrame frame) {
		scale = frame.getContentPane().getWidth() / (double) img.getWidth();
		height = (int) (frame.getContentPane().getHeight() / scale);
		topEdge = (width - height) / 2;
		bottomEdge = width - topEdge;
	}

	public void plot(int x, int y, int color) {
		int id = y * (width + 1) + x;
		// Reject pixel if it's out of screen's bounds
		if (id < 0 || id >= width * width)
			return;
		try {
			pixels[id] = Apricot.color.alphaBlend(color, pixels[id]);
		} catch (Exception e) {

		}
	}

	public void drawLine(int x1, int y1, int x2, int y2, int color) {
		int d = 0;

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		int dx2 = 2 * dx; // slope scaling factors to
		int dy2 = 2 * dy; // avoid floating point

		int ix = x1 < x2 ? 1 : -1; // increment direction
		int iy = y1 < y2 ? 1 : -1;

		int x = x1;
		int y = y1;

		if (dx >= dy) {
			while (x != x2) {
				if (x < 0)
					break;
				if (y < topEdge)
					break;
				if (x > width)
					break;
				if (y > bottomEdge)
					break;
				plot(x, y, color);
				x += ix;
				d += dy2;
				if (d > dx) {
					y += iy;
					d -= dx2;
				}
			}
		} else {
			while (y != y2) {
				if (x < 0)
					break;
				if (y < topEdge)
					break;
				if (x > width)
					break;
				if (y > bottomEdge)
					break;
				plot(x, y, color);
				y += iy;
				d += dx2;
				if (d > dy) {
					x += ix;
					d -= dy2;
				}
			}
		}
	}

	double[] drawScanLine(int x1, int x2, int y, int color, double[] zBuffer, double z) {
		x1 = Math.max(0, Math.min(800, x1));
		x2 = Math.max(0, Math.min(800, x2));
		for (int x = Math.min(x1, x2); x < Math.max(x1, x2); x++) {
			if (zBuffer[x + y * width] > z) {
				plot(x, y, color);
				zBuffer[x + y * width] = z;
			}
		}
		return zBuffer;
	}

	public void drawTriangle(Tuple v1, Tuple v2, Tuple v3, int color) {
		drawLine((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y, color);
		drawLine((int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y, color);
		drawLine((int) v3.x, (int) v3.y, (int) v1.x, (int) v1.y, color);
	}

	public double[] fillTriangle(Tuple v1, Tuple v2, Tuple v3, int color, double[] zBuffer, double z) {
		ArrayList<Tuple> tups = new ArrayList<>();
		tups.add(v1);
		tups.add(v2);
		tups.add(v3);
		tups.sort(new Comparator<Tuple>() {
			public int compare(Tuple arg0, Tuple arg1) {
				return (int) (arg0.y - arg1.y);
			}
		});
		Tuple top = tups.get(0);
		Tuple middle = tups.get(1);
		Tuple bottom = tups.get(2);
		Tuple split = new Tuple(top.x + ((middle.y - top.y) / (bottom.y - top.y)) * (bottom.x - top.x), middle.y);
		zBuffer = fillTopTriangle(middle, split, bottom, color, zBuffer, z);
		zBuffer = fillBottomTriangle(top, split, middle, color, zBuffer, z);
		return zBuffer;
	}

	double[] fillTopTriangle(Tuple v1, Tuple v2, Tuple v3, int color, double[] zBuffer, double z) {
		double invslope1 = (v3.x - v1.x) / (v3.y - v1.y);
		double invslope2 = (v3.x - v2.x) / (v3.y - v2.y);

		double curx1 = v3.x;
		double curx2 = v3.x;

		for (double scanlineY = v3.y; scanlineY >= v1.y; scanlineY--) {
			drawScanLine((int) Math.round(curx1), (int) Math.round(curx2), (int) Math.round(scanlineY), color, zBuffer,
					z);
			curx1 -= invslope1;
			curx2 -= invslope2;

			if (scanlineY > height)
				return zBuffer;
			if (scanlineY < 0)
				return zBuffer;
		}
		return zBuffer;
	}

	double[] fillBottomTriangle(Tuple v1, Tuple v2, Tuple v3, int color, double[] zBuffer, double z) {
		double invslope1 = (v2.x - v1.x) / (v2.y - v1.y);
		double invslope2 = (v3.x - v1.x) / (v3.y - v1.y);

		double curx1 = v1.x;
		double curx2 = v1.x;

		for (double scanlineY = v1.y; scanlineY <= v3.y; scanlineY++) {
			zBuffer = drawScanLine((int) Math.round(curx1), (int) Math.round(curx2), (int) Math.round(scanlineY), color,
					zBuffer, z);
			curx1 += invslope1;
			curx2 += invslope2;
			if (invslope1 < 0) {
				if (curx1 < v2.x)
					return zBuffer;
			} else {
				if (curx1 > v2.x)
					return zBuffer;
			}
			if (invslope2 < 0) {
				if (curx2 < v3.x)
					return zBuffer;
			} else {
				if (curx2 > v3.x)
					return zBuffer;
			}
			if (scanlineY >= height) {
				return zBuffer;
			}
		}
		return zBuffer;
	}

	public void drawRect(int x, int y, int width, int height, int color, int thickness) {
		for (int i = 0; i < thickness; i++) {
			drawLine(x + i, y + i, x + width - i, y + i, color);
			drawLine(x + i, y + i, x + i, y + height - i, color);
			drawLine(x + width - i, y + i, x + width - i, y + height - i, color);
			drawLine(x + i, y + height - i, x + width - i + 1, y + height - i, color);
		}
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
	public void fillRect(int x, int y, int w, int h, int color) {
		// Rejecting rectangles that could not have pixels on screen
		if (x + w < 0)
			return;
		if (x > width)
			return;
		if (y + h < topEdge)
			return;
		if (y > bottomEdge)
			return;

		for (int i = 0; i < w * h; i++) {
			int x1 = i % w + x;
			int y1 = i / w + y;

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

			plot(x1, y1, color);
		}
	} // End of drawRect()

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
		int h = 1;
		double cos = 0, sin = 0;
		int imageAlpha = (int)(opacity * 255) << 24;

		// Finding the height of the image
		if (w > 0) {
			h = image.length / w;
		}

		// Rejecting images that could not have pixels on the screen
		if (x + w < 0)
			return;
		if (x - w > width)
			return;
		if (y + h < topEdge)
			return;
		if (y - h > bottomEdge)
			return;

		// Doing division once since division is slow
		float halfW = w / 2;
		float halfH = h / 2;
		float deltaI = 1;

		// Doing cos/sin only if there is rotation since those ops are slow
		if (rotate != 0) {
			deltaI = 0.49f;
			cos = Math.cos(rotate);
			sin = Math.sin(rotate);
		}

		// For every pixel...
		for (float i = 0; i < image.length; i += deltaI) {
			if (Apricot.color.getAlpha(image[(int) i]) == 0)
				continue;

			// Calculate position of pixels
			double x1 = (i % w) - halfW; // X coord of pixel on screen, centered around (0,0) origin
			double y1 = i / w - halfH; // Y coord of pixel on screen, centered around (0,0) origin
			double x2 = (int) (x1 + x); // X coord of pixel on screen, centered around image center
			double y2 = (int) (y1 + y); // Y coord of pixel on screen, centered around image center

			// Rotate pixels if rotation, since rotation is slow
			if (rotate != 0) {
				x2 = (int) (x1 * cos - y1 * sin + x);
				y2 = (int) (x1 * sin + y1 * cos + y);
			}

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

			plot((int) x2, (int) y2, imageAlpha | (image[(int) i] & 16777215));
		}
	} // End of drawImage()

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
}