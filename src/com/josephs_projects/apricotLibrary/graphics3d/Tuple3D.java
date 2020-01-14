package com.josephs_projects.apricotLibrary.graphics3d;

import com.josephs_projects.apricotLibrary.Tuple;

public class Tuple3D {
	public double x, y, z;
	
	public Tuple3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Tuple3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Tuple3D(Tuple3D t) {
		this.x = t.x;
		this.y = t.y;
		this.z = t.z;
	}
	
	public Tuple3D cross(Tuple3D t) {
		Tuple3D retval = new Tuple3D();
		retval.x = y * t.z - z * t.y;
		retval.y = z * t.x - x * t.z;
		retval.z = x * t.y - y * t.x;
		return retval;
	}
	
	public double dot(Tuple3D t) {
		return t.x * x + t.y * y + t.z * z;
	}
	
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);	
	}
	
	public Tuple3D normalize() {
		Tuple3D retval = new Tuple3D();
		double magnitude = magnitude();
		retval.x = x / magnitude;
		retval.y = y / magnitude;
		retval.z = z / magnitude;
		return retval;
	}
	
	public Tuple3D add(Tuple3D t) {
		return new Tuple3D(x + t.x, y + t.y, z + t.z);
	}
	
	public Tuple3D sub(Tuple3D t) {
		return new Tuple3D(x - t.x, y - t.y, z - t.z);
	}
	
	public Tuple3D scalar(double scalar) {
		return new Tuple3D(x * scalar, y * scalar, z * scalar);
	}
	
	public double distSquared(Tuple3D t) {
		return (x - t.x) * (x - t.x) + (y - t.y) * (y - t.y) + (z - t.z) * (z - t.z); 
	}
	
	public void copy(Tuple3D t) {
		this.x = t.x;
		this.y = t.y;
		this.z = t.z;
	}
	
	public Tuple3D multMatrix(double[][] matrix) {
		Tuple3D retval = new Tuple3D();
		
		for(int i = 0; i < 3; i++) {
			retval.x += x * matrix[i][0];
			retval.y += y * matrix[i][1];
			retval.z += z * matrix[i][2];
		}
		
		if(matrix.length <= 3)
			return retval;
		
		for(int i = 3; i < matrix.length; i++) {
			retval.x += matrix[i][0];
			retval.y += matrix[i][1];
			retval.z += matrix[i][2];
		}
		
		return retval;
	}
	
	public Tuple flat() {
		return new Tuple(x,y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public double distFromPlane(Tuple3D point, Tuple3D normal) {
		return normal.x * x + normal.y * y + normal.z * z - normal.dot(point);
	}
}
