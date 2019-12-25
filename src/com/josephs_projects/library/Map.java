package com.josephs_projects.library;

/**
 * This class holds some useful methods for generating noise maps, and
 * interpolating between points.
 * 
 * @author Joseph Shimel
 *
 */
public class Map {

	public float[][] generateMap(int width, int height, int depth) {
		float[][] mountain = generateNoiseMap(width, height, depth, 1f);

		// Make many maps, increase the detail, and add them all together
		for (int i2 = depth; i2 < 9; i2++) {
			int denominator = 1 << (i2 + 1);
			float[][] tempMountain = generateNoiseMap(width, height, i2, 2f / denominator);
			for (int i = 0; i < width * height; i++) {
				int x = i % width;
				int y = i / width;
				mountain[x][y] = mountain[x][y] + tempMountain[x][y];
			}
		}

		return normalizeNoiseMap(mountain, width, height);
	}

	/**
	 * @param width     Width of the random map. Must be one more than a power of 2
	 * @param height    Height of the random map. Must be one more than a power of 2
	 * @param frequency Frequency of noise
	 * @param amplitude Amplitude of noise
	 * @return A random map at specified of values from +amplitude to -amplitude
	 */
	public float[][] generateNoiseMap(int width, int height, int frequency, float amplitude) {
		float[][] retval = new float[width][height];
		// Cast out bad freq ranges
		if (frequency <= 0 || frequency >= 9)
			return retval;

		int wavelength = 1 << (-frequency + 9);
		int cellWidth = (width / wavelength) + 1;
		int cellHeight = (height / wavelength) + 1;

		// Populate corners of cells with random values
		for (int i = 0; i < cellWidth * cellHeight; i++) {
			int x = (i % cellWidth) * wavelength;
			int y = (i / cellWidth) * wavelength;
			retval[x][y] = ((2 * Registrar.rand.nextFloat() - 1f) * amplitude);
		}

		// Interpolate each cell
		for (int i = 0; i < width * height; i++) {
			int x = i % width;
			int y = i / width;
			int x1 = (int) (x / wavelength) * wavelength;
			int y1 = (int) (y / wavelength) * wavelength;

			// Don't interpolate on cell-corners
			if (x == x1 && y == y1)
				continue;

			retval[x][y] = bicosineInterpolation(x1, y1, wavelength, retval, x, y, width, height);
		}

		return retval;
	}

	/**
	 * Interpolates value of a point within a cell based on the cosine interpolation
	 * of the four corners of that local cell.
	 * 
	 * @param x1     X coordinate of the top-left corner of the local cell
	 * @param y1     Y coordinate of the top-left corner of the local cell
	 * @param p      Size of local cell
	 * @param noise  Noise map so far
	 * @param x2     X coordinate of the point within the local cell
	 * @param y2     Y coordinate of the point within the local cell
	 * @param width  Width of full map
	 * @param height Height of full map
	 * @return The value the point within the local cell should based on the values
	 *         of the four corners of the local cell
	 */
	public float bicosineInterpolation(int x1, int y1, int p, float[][] noise, int x2, int y2, int width, int height) {
		// (x1,y1)- coords of top left corner of local cell
		// p- length/heigth of local cell
		// (x3,y3)- coords of point within cell to interpolate
		if (x1 + p > width || y1 + p > height) {
			return 1f;
		}
		// Generate interpolations for bottom and top of cell
		float topInterpolation = cosineInterpolation(x1, noise[(int) x1][(int) y1], x1 + p,
				noise[(int) (x1 + p)][(int) y1], x2);
		float bottomInterpolation = cosineInterpolation(x1, noise[(int) x1][(int) (y1 + p)], x1 + p,
				noise[(int) (x1 + p)][(int) (y1 + p)], x2);

		// Return interpolation between bottom and top interpolation
		return cosineInterpolation(y1, topInterpolation, y1 + p, bottomInterpolation, y2);
	}

	/**
	 * Based on Paul Bourke's wonderful resource on interpolation.
	 * http://paulbourke.net/miscellaneous/interpolation/
	 * 
	 * @param x1 X coordinate of new point. Must be between 0-x2
	 * @param y1 Y coordinate of new point.
	 * @param x2 X coordinate of edge point.
	 * @param y2 Y coordinate of edge point.
	 * @param m  Slope
	 * @return
	 */
	public float cosineInterpolation(int x1, float y1, int x2, float y2, int m) {
		double xDiff = (x2 - x1);
		double mu2 = (1 - Math.cos((m / xDiff - x1 / xDiff) * Math.PI)) / 2;
		double y3 = (y1 * (1 - mu2) + y2 * mu2);
		return (float) y3;
	}

	/**
	 * v = (v - min) / (max - min)
	 * 
	 * @param mountain Noise map to be normalized
	 * @param width    Width of map
	 * @param height   Height of map
	 * @return Map after being normalized to fit between 0-1
	 */
	public float[][] normalizeNoiseMap(float[][] mountain, int width, int height) {
		float maxValue = mountain[0][0];
		float minValue = mountain[0][0];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (mountain[x][y] > maxValue) {
					maxValue = mountain[x][y];
				}
				if (mountain[x][y] < minValue) {
					minValue = mountain[x][y];
				}
			}
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				mountain[x][y] = (mountain[x][y] - minValue) / (maxValue - minValue);
			}
		}

		return mountain;
	}
}
