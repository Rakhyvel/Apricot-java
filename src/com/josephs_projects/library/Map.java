package com.josephs_projects.library;

import com.josephs_projects.library.graphics.Image;

public class Map {
	private float[][] mountain;
	private double[] temperature;
	private double[] precipitation;
	private int width;
	private int height;
	Image img = new Image();
	public int[] biomes = Image.loadImage("/res/biomes.png");

	public Map(int width, int height, int depth) {
		this.width = width;
		this.height = height;

//		String seed = "Joseph Shimel";
//		rand.setSeed(seed.hashCode());
		mountain = perlinNoise(depth, 1f);
		for (int i2 = depth + 1; i2 < 9; i2++) {
			int denominator = 1 << (i2 + 1);
			float[][] tempMountain = perlinNoise(i2, 2f / denominator);
			for (int i = 0; i < width * height; i++) {
				int x = i % width;
				int y = i / width;
				mountain[x][y] = mountain[x][y] + tempMountain[x][y];
			}
		}

		temperature = generateTempMap();
		precipitation = generatePrecipitation();
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
					noise[x][y] = Registrar.rand.nextFloat() + 0.1f;
					if(x == 0 || y == 0 || x == this.width-1 || y == this.height-1)
						noise[x][y] = Registrar.rand.nextFloat()/4.0f;
				} else {
					noise[x][y] = ((2 * Registrar.rand.nextFloat() - 1f) * amplitude);
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

	public float getPlot(Tuple point) {
		int x = (int) point.getX();
		int y = (int) point.getY();
		if (x >= 0 && x < width && y >= 0 && y < height)
			return mountain[x][y];
		return -1;
	}

	public double[] generateTempMap() {
		double[] retval = new double[width * width * 2];
		retval = createLattitudeGradient(retval);
		retval = findAltitudinalGradient(retval);
		return retval;
	}

	public double[] createLattitudeGradient(double[] tempMap) {
		// 25 degrees farenheit at poles, 110 degrees farenheit at equator
		for (int i = 0; i < tempMap.length; i++) {
			int y = (i / width);
			tempMap[i] = -(170.0 / (double) height) * Math.abs(y - height / 2.0) + 110;
		}
		return tempMap;
	}

	public double[] findAltitudinalGradient(double[] tempMap) {
		for (int i = 0; i < tempMap.length; i++) {
			int x = (i % width);
			int y = (i / width);
			float z = getPlot(x, y);
			if (z <= 0.5)
				continue;
			tempMap[i] = tempMap[i] * (1 - (z - 0.5));
		}
		return tempMap;
	}

	public double getTemp(Tuple point) {
		int x = (int) point.getX();
		int y = (int) point.getY();
		if (x >= 0 && x < width && y >= 0 && y < height)
			return temperature[y * width + x];
		return -1;
	}

	public double getPrecipitation(Tuple point) {
		int x = (int) point.getX();
		int y = (int) point.getY();
		if (x >= 0 && x < width && y >= 0 && y < height)
			return precipitation[y * width + x];
		return -1;
	}

	public double[] generatePrecipitation() {
		double[] retval = new double[width * height];
		for (int y = 0; y < height; y++) {
			double precipitation = 50;
			for(int x = width - 1; x >= 0; x--) {
				if(mountain[x][y] < 0.5) {
					precipitation++;
				} else if (precipitation > 0){
					if(x == 0)
						continue;
					if(mountain[x][y] < mountain[x-1][y]) {
						float diff = 0.1f * (mountain[x-1][y] - mountain[x][y]);
						diff = (float)Math.sqrt(diff);
						precipitation *= -diff/(diff + 1.0) + 1;
					}
					
					retval[x + y * width] = (99 - precipitation);
					retval[x + y * width] *= mountain[x][y];
					retval[x + y * width] = Math.max(0, Math.min(99, (retval[x + y * width])));
				}
			}
		}
		return retval;
	}

	public int[] getImage() {
		int[] image = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			int x = (i % width);
			int y = (i / width);
			image[i] = getColor(mountain[x][y], temperature[i], precipitation[i]);
		}
		return image;
	}

	/**
	 * @param value The float value from 0f-1f
	 * @return A color based on the depth, 0 being a deep blue, 0.5f being coast,
	 *         and 1 being green plains.
	 */
	int getColor(float value, double temperature, double precip) {
		if (value < 0.5)
			return 255 << 24 | 105 << 8 | 148;
		temperature = Math.max(0, Math.min(85, temperature - 25));
		
		int x = (int) ((temperature / 85.0) * 77);
		int y = (int) ((precip / 100.0) * 9);
		return biomes[x + y * 77];
	}
	
	public int getColorIndex(int index) {
		int x = (index % width);
		int y = (index / width);
		float value = mountain[x][y];
		double temperature = this.temperature[index];
		double precip = precipitation[index];
		if (value < 0.5)
			return 255 << 24 | 105 << 8 | 148;
		temperature = Math.max(0, Math.min(85, temperature - 25));
		
		x = (int) ((temperature / 85.0) * 77);
		y = (int) ((precip / 100.0) * 9);
		return Math.max(0, x + y * 77);
	}
}
