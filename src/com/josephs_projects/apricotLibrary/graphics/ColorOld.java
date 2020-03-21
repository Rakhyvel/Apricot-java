package com.josephs_projects.apricotLibrary.graphics;

public class ColorOld {	
	public int white = argb(255, 255, 255, 255);
	public int yellow = argb(255, 255, 255, 0);
	public int magenta = argb(255, 255, 0, 255);
	public int red = argb(255, 255, 0, 0);
	public int cyan = argb(255, 0, 255, 255);
	public int green = argb(255, 0, 255, 0);
	public int blue = argb(255, 0, 0, 255);
	public int black = argb(255, 0, 0, 0);
	public int gray = argb(255, 128, 128, 128);
	/**
	 * Returns the 32bit color equivalent
	 * 
	 * @param a Alpha channel, ranges 0-255
	 * @param r Red channel, ranges 0-255
	 * @param g Green channel, ranges 0-255
	 * @param b Blue channel, ranges 0-255
	 * @return 32bit integer color
	 */
	public int argb(int a, int r, int g, int b) {
		a = Math.max(0, Math.min(255, a));
		r = Math.max(0, Math.min(255, r));
		g = Math.max(0, Math.min(255, g));
		b = Math.max(0, Math.min(255, b));

		return a << 24 | r << 16 | g << 8 | b;
	}

	/**
	 * Returns 32 bit color equivalent
	 * 
	 * @param a Alpha (transparency) of color
	 * @param hue Hue of color
	 * @param saturation Saturation of color
	 * @param value Value of color
	 * @return The 32 bit encoded color
	 */
	public int ahsv(int a, double hue, double saturation, double value) {
		hue += 360;
		hue = hue % 360;
		saturation = Math.max(0, Math.min(1, saturation));
		value = Math.max(0, Math.min(1, value));
		a = Math.max(0, Math.min(255, a));
		double c = value * saturation;
		double x = c * (1 - Math.abs(((hue / 60) % 2) - 1));
		double m = value - c;
		double r = 0, g = 0, b = 0;

		if (hue >= 0 && hue < 60) {
			r = c;
			g = x;
			b = 0;
		} else if (hue >= 60 && hue < 120) {
			r = x;
			g = c;
			b = 0;
		} else if (hue >= 120 && hue < 180) {
			r = 0;
			g = c;
			b = x;
		} else if (hue >= 180 && hue < 240) {
			r = 0;
			g = x;
			b = c;
		} else if (hue >= 240 && hue < 300) {
			r = x;
			g = 0;
			b = c;
		} else if (hue >= 300 && hue < 360) {
			r = c;
			g = 0;
			b = x;
		}
		return a << 24 | (int) ((r + m) * 255) << 16 | (int) ((g + m) * 255) << 8 | (int) ((b + m) * 255);
	}
	
	public int grayScale(float value) {
		int gray = (int)(value * 255);
		return 255 << 24 | gray << 16 | gray << 8 | gray;
	}

	public double getValue(int r, int g, int b) {
		double r1 = r / 255.0;
		double g1 = g / 255.0;
		double b1 = b / 255.0;
		return Math.max(r1, Math.max(g1, b1));
	}

	public double getSaturation(int r, int g, int b) {
		double r1 = r / 255.0;
		double g1 = g / 255.0;
		double b1 = b / 255.0;
		double cMax = Math.max(r1, Math.max(g1, b1));
		double cMin = Math.min(r1, Math.min(g1, b1));
		if (cMax == 0)
			return 0;
		return ((cMax - cMin) / cMax);
	}

	public double getHue(int r, int g, int b) {
		double r1 = r / 255.0;
		double g1 = g / 255.0;
		double b1 = b / 255.0;
		double cMax = Math.max(r1, Math.max(g1, b1));
		double cMin = Math.min(r1, Math.min(g1, b1));
		double hue = 0;
		if (cMax - cMin == 0) {
			return 0;
		}
		if (cMax == r1) {
			hue = 60 * (((g1 - b1) / (cMax - cMin)) % 6);
		} else if (cMax == g1) {
			hue = 60 * (((b1 - r1) / (cMax - cMin)) + 2);
		} else {
			hue = 60 * (((r1 - g1) / (cMax - cMin)) + 4);
		}
		if (hue < 0) {
			hue += 360;
		}
		return hue;
	}
	
	public int scalar(int color, double scalar) {
		int a = (color >> 24) & 255;
		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = (color >> 0) & 255;
		
		r = (int) Math.max(0, Math.min(255, r * scalar));
		g = (int) Math.max(0, Math.min(255, g * scalar));
		b = (int) Math.max(0, Math.min(255, b * scalar));
		
		return a << 24 | r << 16 | g << 8 | b;
	}
	
	int add(int color1, int color2) {
		// Finding coordinates of first color
		int a1 = (color1 >> 24) & 255;
		int r1 = (color1 >> 16) & 255;
		int g1 = (color1 >> 8) & 255;
		int b1 = (color1 >> 0) & 255;

		// Finding coordiantes of second color
		int a2 = (color2 >> 24) & 255;
		int r2 = (color2 >> 16) & 255;
		int g2 = (color2 >> 8) & 255;
		int b2 = (color2 >> 0) & 255;
		
		// Adding them all together
		int a = Math.min(255, a1 + a2);
		int r = Math.min(255, r1 + r2);
		int g = Math.min(255, g1 + g2);
		int b = Math.min(255, b1 + b2);
		
		return a << 24 | r << 16 | g << 8 | b;
	}
	
	int gamma(int color, double gamma) {
		int a = (color >> 24) & 255;
		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = (color >> 0) & 255;
		
		r = (int) Math.max(0, Math.min(255, Math.pow(r, gamma)));
		g = (int) Math.max(0, Math.min(255, Math.pow(g, gamma)));
		b = (int) Math.max(0, Math.min(255, Math.pow(b, gamma)));
		return a << 24 | r << 16 | g << 8 | b;
	}
	
	/**
	 * Retrieves the opacity of a color.
	 * 
	 * @param color Color to retrieve opacity from
	 * @return Opacity of color, a value from 0.0D to 1.0D
	 */
	double getAlpha(int color) {
		return (255 & (color >> 24)) / 255.0;
	}
	
	public int alphaBlend(int src, int dest) {
		double srcA = getAlpha(src);
		double destA = getAlpha(dest);
		int retval = add(scalar(src, srcA), scalar(dest, destA * (1 - srcA)));
		return retval;
	}
	
	public String toStringARGB(int color) {
		int a = (color >> 24) & 255;
		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = (color >> 0) & 255;
		
		return "ARGB(" + a + ", " + r + ", " + g + ", " + b + ")";
	}
	
	public String toStringHSV(int color) {
		int a = (color >> 24) & 255;
		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = (color >> 0) & 255;
		
		double hue = getHue(r, g, b);
		double saturation = getSaturation(r, g, b);
		double value = getValue(r, g, b);
		
		return "AHSV(" + a + ",\t" + hue + ",\t" + saturation + ",\t" + value + ")";
	}
	
	public int posterize(int color, int level) {
		int a = (color >> 24) & 255;
		int r = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int b = (color >> 0) & 255;
		
		int hue = (int) getHue(r, g, b);
		double saturation = getSaturation(r, g, b);
		double value = getValue(r, g, b);
		
		value = (int)(value * level) / (double)level;
		
		return ahsv(a, hue, saturation, value);
	}
}
