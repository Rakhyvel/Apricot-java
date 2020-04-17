package com.josephs_projects.apricotLibrary;

/**
 * This class holds some useful methods for generating noise maps, and
 * interpolating between points.
 * 
 * @author Joseph Shimel
 *
 */
public class NoiseMap {

	/**
	 * @param width     Width of the random map. Must be one more than a power of 2
	 * @param height    Height of the random map. Must be one more than a power of 2
	 * @param frequency Frequency of noise
	 * @param amplitude Amplitude of noise. This is to be set internally. Will have
	 *                  no real affect if used by user
	 * @return A random map at specified of values from +amplitude to -amplitude
	 */
	public float[][] generate(int size, int seed, double frequency, float... amplitude) {
		if (seed != -1) {
			Apricot.rand.setSeed(seed);
		}
		float freq = (float) Math.pow(2, frequency);
		float[][] retval = new float[size][size];
		int cellSize = (int) (size / freq);

		// Base case:
		// Check to see if cells are too small to be pixelized
		if (cellSize <= 8)
			return retval;

		float amp = 1;
		if (amplitude.length == 1) {
			amp = amplitude[0];
		}

		// Populate corners of cells with random values
		for (int i = 0; i < freq * freq; i++) {
			int x = (int) (i % freq) * cellSize;
			int y = (int) (i / freq) * cellSize;
			retval[x][y] = (float) ((2 * Math.random() - 1) * amp);
		}

		// Interpolate each cell, and add finer detail map
		float[][] finerDetailMap = generate(size, seed, frequency + 1, amp * 0.5f);
		for (int i = 0; i < size * size; i++) {
			int x = i % size; // x coord of point
			int y = i / size; // y coord of point
			int x1 = (int) ((x / cellSize) * cellSize); // x coord of point's cell
			int y1 = (int) ((y / cellSize) * cellSize); // y coord of point's cell

			// Don't interpolate on cell-corners
			if ((x == x1) && (y == y1))
				continue;

			retval[x][y] = bicosineInterpolation(x1, y1, cellSize, retval, x, y, size, size) + finerDetailMap[x][y];
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
		// Check to see if edge case
		int x3 = x1 + p;
		int y3 = y1 + p;
		if (x3 >= width - 1) {
			x3 = width - 1;
		}
		if (y3 >= height - 1) {
			y3 = height - 1;
		}

		// Generate interpolations for bottom and top of cell
		float topInterpolation = cosineInterpolation(x1, noise[x1][y1], x1 + p, noise[x3][y1], x2);
		// top-left -> top-right
		float bottomInterpolation = cosineInterpolation(x1, noise[x1][y3], x1 + p, noise[x3][y3], x2);
		// bottom-left -> bottom-right

		// Return interpolation between bottom and top interpolation
		return cosineInterpolation(y1, topInterpolation, y1 + p, bottomInterpolation, y2);
	}

	/**
	 * Based on Paul Bourke's wonderful resource on interpolation.
	 * http://paulbourke.net/miscellaneous/interpolation/
	 * 
	 * @param x1 X coordinate of initial point
	 * @param y1 Y coordinate of initial point
	 * @param x2 X coordinate of final point.
	 * @param y2 Y coordinate of final point.
	 * @param m  X coordinate of point to be interpolated
	 * @return The Y coordinate of the point
	 */
	public float cosineInterpolation(int x1, float y1, int x2, float y2, int x) {
		double xDiff = (x2 - x1);
		double mu2 = (1 - Math.cos((x - x1) * Math.PI / xDiff)) / 2;
		double retval = (y1 * (1 - mu2) + y2 * mu2);
		return (float) retval;
	}

	/**
	 * v = (v - min) / (max - min)
	 * 
	 * @param mountain Noise map to be normalized
	 * @param width    Width of map
	 * @param height   Height of map
	 * @return Map after being normalized to fit between 0-1
	 */
	public float[][] normalize(float[][] mountain) {
		int width = mountain.length;
		int height = mountain[0].length;
		float maxValue = (float) Double.NEGATIVE_INFINITY;
		float minValue = (float) Double.POSITIVE_INFINITY;
		float average = 0;
		int number = 0;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				maxValue = Math.max(mountain[x][y], maxValue);
				minValue = Math.min(mountain[x][y], minValue);
			}
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				mountain[x][y] = (mountain[x][y] - minValue) / (maxValue - minValue);
				average += mountain[x][y];
				number++;
			}
		}

		average = 0.5f - (average / number);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				mountain[x][y] = mountain[x][y] += average;
			}
		}

		return mountain;
	}
}
