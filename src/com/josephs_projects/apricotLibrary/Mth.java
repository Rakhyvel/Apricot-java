package com.josephs_projects.apricotLibrary;

public class Mth {
	static double accuracy = 100;
	static double[] cosine = new double[(int) (Math.PI * 2 * accuracy)];
	static {
		for(int i = 0; i < cosine.length; i++) {
			cosine[i] = Math.cos(i / accuracy);
		}
	}
	
	public static double cos(double rad) {
		return cosine[(int)(rad * accuracy)];
	}
}
