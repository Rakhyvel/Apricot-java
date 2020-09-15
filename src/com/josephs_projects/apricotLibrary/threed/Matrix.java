package com.josephs_projects.apricotLibrary.threed;

public class Matrix {
	double[][] elements;
	int rows, cols;
	
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		elements = new double[rows][cols];
	}
	
	public Matrix(Vector ... vectors) {
		this.rows = vectors.length;
		this.cols = vectors[0].size();
		elements = new double[rows][cols];
		
		for(int i = 0; i < rows; i++) {
			elements[i] = vectors[i].elements;
		}
	}
	
	public Vector mult(Vector v) {
		double[] retval = new double[cols];
		for(int i = 0; i < rows; i++) {
			retval[i] = v.dot(elements[i]);
		}
		return new Vector(retval);
	}
	
	public void T() {
		double[][] newMatrix = new double[cols][rows];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				newMatrix[j][i] = elements[i][j];
			}
		}
		elements = newMatrix;
	}
}
