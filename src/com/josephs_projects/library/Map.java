package com.josephs_projects.library;

import java.util.Random;

public class Map {
	Random rand = new Random();
	private float[][] mountain;
	private int width;
	private int height;

	public Map(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		
		mountain = perlinNoise(depth, 1f);
		for (int i2 = depth+1; i2 < 9; i2++) {
			int denominator = 1 << (i2 + 1);
			float[][] tempMountain = perlinNoise(i2, 2f / denominator);
			for (int i = 0; i < width * height; i++) {
				int x = i % width;
				int y = i / width;
				mountain[x][y] = mountain[x][y] + tempMountain[x][y];
			}
		}
	}

	public float[][] perlinNoise(int frequency, float amplitude) {
		float[][] noise = new float[width][height];
		if (frequency > 0 && frequency < 9) {
			int wavelength = 1 << (-frequency + 9);
			int width = (this.width / wavelength) + 1;
			int height = (this.height / wavelength) + 1;
			for (int i = 0; i < width * height; i++) {
				int x = (i % width) * wavelength;
				int y = (i / width) * wavelength;
				if (frequency <= 2) {
					noise[x][y] = rand.nextFloat() + 0.1f;
				} else {
					noise[x][y] = ((2 * rand.nextFloat() - 1f) * amplitude);
				}
			}
			for (int i = 0; i < this.width * this.height; i++) {
				int x = i % this.width;
				int y = i / this.width;
				int x1 = (int) (x / wavelength) * wavelength;
				int y1 = (int) (y / wavelength) * wavelength;
				if (x != x1 || y != y1) {
					noise[x][y] = bicosineInterpolation(x1, y1, wavelength, noise, x, y);
				}
			}
		}
		return noise;
	}

	float bicosineInterpolation(int x1, int y1, int p, float[][] noise, int x2, int y2) {
		// (x1,y1)- coords of top left corner of box
		// p- length/heigth pf box
		// (x3,y3)- coords of point
		if (x1 + p > width || y1 + p > height) {
			return 1f;
		}
		float topInterpolation = cosineInterpolation(x1, noise[(int) x1][(int) y1], x1 + p,
				noise[(int) (x1 + p)][(int) y1], x2);
		float bottomInterpolation = cosineInterpolation(x1, noise[(int) x1][(int) (y1 + p)], x1 + p,
				noise[(int) (x1 + p)][(int) (y1 + p)], x2);
		return cosineInterpolation(y1, topInterpolation, y1 + p, bottomInterpolation, y2);
	}

	float cosineInterpolation(int x1, float y1, int x2, float y2, int m) {
		double xDiff = (x2 - x1);
		double mu2 = (1 - Math.cos((m / xDiff - x1 / xDiff) * Math.PI)) / 2;
		double y3 = (y1 * (1 - mu2) + y2 * mu2);
		return (float) y3;
	}

	float transform(float r) {
		double verticalStretch = 0.536713074275;
		double newHeight;
		if (r > 1) {
			newHeight = 2 * (r - 1) * (r - 1) + 1;
		} else {
			newHeight = verticalStretch * Math.tan(1.5 * r - .75) + 0.5;
		}
		return (float) newHeight;
	}

	float bilinearInterpolation(int x1, int y1, int p, float[][] noise, int x2, int y2) {
		// (x1,y1)- coords of top left corner of box
		// p- length/heigth pf box
		// (x3,y3)- coords of point
		if (x1 + p > 1025 || y1 + p > 513) {
			return 1.5f;
		}
		float topInterpolation = linearInterpolation(x1, noise[(int) x1][(int) y1], x1 + p,
				noise[(int) (x1 + p)][(int) y1], x2);
		float bottomInterpolation = linearInterpolation(x1, noise[(int) x1][(int) (y1 + p)], x1 + p,
				noise[(int) (x1 + p)][(int) (y1 + p)], x2);
		return linearInterpolation(y1, topInterpolation, y1 + p, bottomInterpolation, y2);
	}

	float linearInterpolation(int x1, float y1, int x2, float y2, int m) {
		return (y2 - y1) / (x2 - x1) * (m - x1) + y1;
	}
	
	public float[][] getArray() {
		return mountain;
	}
	
	public void setArray(float[][] mountain) {
		this.mountain = mountain;
	}

	// getArray(...): returns the float value at a given coordinate
	/**
	 * @param x coordiate on the map
	 * @param y coordiate on the map
	 * @return The float value of the given position on the terrain
	 */
	public float getPlot(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			return mountain[x][y];
		return -1;
	}
	
	public int[] getImage() {
		int[] image = new int[width * height];
		for(int i = 0; i < width*height; i++) {
			int x = (i % width);
			int y = (i / width);
			image[i] = getColor(mountain[x][y]);
		}
		return image;
	}

	/**
	 * @param value The float value from 0f-1f
	 * @return A color based on the depth, 0 being a deep blue, 0.5f being coast,
	 *         and 1 being green plains.
	 */
	int getColor(float value) {
		int blue = 0;
		int green = 0;
		int red = 0;

		if (value < .495f) {
			blue = (int) (510 * value);
			red = (int) (760 * value * value - 6);
			green = (int) (1040 * value * value - 6);
		} else if (value < .5) {
			blue = (int) (173);
			green = (int) (225);
			red = (int) (238);
		} else if (value < 1) {
			blue = (int) (730 * (value - 1) * (value - 1) - 9);
			green = (int) (-290 * (value - 1) + 80);
			red = (int) (1000 * (value - 1) * (value - 1) - 12);
		} else {
			blue = (int) (value * value * value * 85);
			green = (int) (value * value * value * 85);
			red = (int) (value * value * value * 85);
		}

//		 blue = (int) (value * 255);
//		 green = (int) (value * 255);
//		 red = (int) (value * 255);

		if (blue < 0)
			blue = 0;
		if (green < 0)
			green = 0;
		if (red < 0)
			red = 0;
		if (blue > 255)
			blue = 255;
		if (green > 255)
			green = 255;
		if (red > 255)
			red = 255;
		return 255 << 24 | red << 16 | green << 8 | blue;
	}
}
