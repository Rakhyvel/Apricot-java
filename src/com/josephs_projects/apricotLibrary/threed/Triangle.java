package com.josephs_projects.apricotLibrary.threed;

public class Triangle {
	Vector v0;
	Vector v1;
	Vector v2;
	Vector edge1;
	Vector edge2;
	Vector normal;

	private static final double EPSILON = 0.0000001;

	public Triangle(Vector v0, Vector v1, Vector v2) {
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;

		edge1 = v0.sub(v1);
		edge2 = v0.sub(v2);
		normal = edge1.cross(edge2).normalize();
	}

	public Triangle mult(Matrix mat) {
		return new Triangle(mat.mult(v0), mat.mult(v1), mat.mult(v2));
	}

	// Idk maybe this is better since it doesn't create a new triangle/vector for each thingy
	public Triangle mult(Matrix ... mats) {
		Vector v02 = new Vector(v0), v12 = new Vector(v1), v22 = new Vector(v2);
		for(Matrix mat : mats) {
			v02 = mat.mult(v02);
			v12 = mat.mult(v12);
			v22 = mat.mult(v22);
		}
		return new Triangle(v02, v12, v22);
	}

	public boolean intersects(Ray r) {
		Vector h;
		Vector s;
		Vector q;
		double a, f, u, v;

		h = r.vector.cross(edge2);
		a = edge1.dot(h);

		if (a > -EPSILON && a < EPSILON) {
			return false; // This ray is parallel to the triangle
		}

		f = 1.0 / a;
		s = v0.sub(r.origin);
		u = f * s.dot(h);
		if (u < 0 || u > 1) {
			return false;
		}

		q = s.cross(edge1);
		v = f * r.vector.dot(q);
		if (v < 0 || u + v > 1) {
			return false;
		}

		return true;
	}
	
	public int[] getXCoords() {
		return new int[] {(int)v0.x(), (int)v1.x(), (int)v2.x()};
	}
	
	public int[] getYCoords() {
		return new int[] {(int)v0.y(), (int)v1.y(), (int)v2.y()};
	}
}
