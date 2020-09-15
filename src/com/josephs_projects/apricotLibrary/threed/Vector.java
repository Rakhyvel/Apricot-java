package com.josephs_projects.apricotLibrary.threed;

public class Vector {
	double[] elements;

	public Vector(int size) {
		elements = new double[size];
	}

	public Vector(double... elements) {
		this.elements = elements;
	}

	public Vector(Vector v) {
		this.elements = v.elements;
	}

	public double x() {
		return elements[0];
	}

	public double y() {
		return elements[1];
	}

	public double z() {
		return elements[2];
	}

	public double w() {
		return elements[3];
	}

	public int size() {
		return elements.length;
	}

	public double dot(double... ds) {
		double retval = 0;
		for (int i = 0; i < ds.length; i++) {
			retval += ds[i] * elements[i];
		}
		return retval;
	}

	public double dot(Vector h) {
		return dot(h.elements);
	}

	public void add(double... ds) {
		for (int i = 0; i < ds.length; i++) {
			elements[i] += ds[i];
		}
	}

	public Vector add(Vector v) {
		Vector retval = new Vector(v.elements.length);
		for (int i = 0; i < retval.elements.length; i++) {
			retval.elements[i] = v.elements[i] + elements[i];
		}
		return retval;
	}

	public Vector sub(Vector v) {
		Vector retval = new Vector(v.elements.length);
		for (int i = 0; i < retval.elements.length; i++) {
			retval.elements[i] = elements[i] - v.elements[i];
		}
		return retval;
	}

	public void mult(double... ds) {
		for (int i = 0; i < ds.length; i++) {
			elements[i] *= ds[i];
		}
	}

	public Vector cross(Vector b) {
		Vector retval = new Vector(4);
		retval.elements[0] = y() * b.z() - z() * b.y();
		retval.elements[1] = z() * b.x() - x() * b.z();
		retval.elements[2] = x() * b.y() - y() * b.x();
		retval.elements[3] = 1;
		return retval;
	}

	public Vector normalize() {
		Vector retval = new Vector(4);
		double mag = Math.sqrt(x() * x() + y() * y() + z() * z());
		retval.elements[0] = x() / mag;
		retval.elements[1] = y() / mag;
		retval.elements[2] = z() / mag;
		retval.elements[3] = 1;
		return retval;
	}

	@Override
	public String toString() {
		String retval = "[";
		for (double d : elements) {
			retval += d + ", ";
		}
		retval = retval.substring(0, retval.length() - 2);
		retval += "]";
		return retval;
	}
}
